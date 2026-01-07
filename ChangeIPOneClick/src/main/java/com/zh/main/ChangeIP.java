package com.zh.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.ProcessHandle;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

// 开始处理线程，做好做到不断地刷新
public class ChangeIP {


        public static JButton dateOK;
        static CalendarShow cs;

        // 判断日历的线程，随时获取点击的日历的数据
        public static boolean isLive=true;
        public static ProcessBuilder pbFront;

        //用于处理启动bat跟vbs的脚本
        public static Process processFront;
        public static Runtime rt;

        //获取文件所在地址
        public static String selectedFile=null;

        //需要实时绘画
        public static JFrame frame;

        public static JButton startModify;

        public static int singalNumber=0;

        public static int SingalMonitor=0;

        public static void main(String[] args) {

        //开头的标题
        frame=new JFrame("HY Operation Mini Program");


        //小程序的北部标题
        Panel panel=new Panel();
        panel.add(new JLabel("User Interface"));
        frame.add(panel,BorderLayout.NORTH);
        //  ------ 上面的代码基本不用动



        //专门放置  用户操作按钮  小程序的中部布置
        Panel panel1=new Panel();
        //设置Center的布局
        panel1.setLayout(new GridLayout(5,3,2,2));

        //第一行，处理获取IP地址
        JComboBox combo=new JComboBox();
        combo.addItem("files modified as follows");
        combo.addItem("mediamtx.yml");
        combo.addItem("test_fusion_seat.json");
        combo.addItem("CH_server4.json");
        combo.addItem("test_rpc_LoadDataClient.json");
        combo.addItem("MapTilesServer.json");
        combo.addItem("server_setting.js");
        combo.addItem("serviceConfig.js");

        JLabel ipLabel=new JLabel("Current computer IP");
        panel1.add(ipLabel);

        JTextField ipField=new JTextField(20);
        panel1.add(ipField);


        JButton ipButton=new JButton("Get IP");
        panel1.add(ipButton);
        ipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("test");
                try{
                    Inet4Address ip4= (Inet4Address) Inet4Address.getLocalHost();
                    System.out.println(ip4.getHostAddress());
                    ipField.setText(ip4.getHostAddress());
                }catch(Exception exception){
                    exception.printStackTrace();
                }

            }
        });
        // 第一行功能结束

        //第二行，输入原IP的地址
        JButton changeIP=new JButton("One-click change");
        //changeIP.setEnabled(false);

        /*   这个第二行军帽不需要
        JLabel pastLabel=new JLabel("Enter original IP");
        panel1.add(pastLabel);

        JTextField pastField=new JTextField(20);
        panel1.add(pastField);

        JButton confirmButton=new JButton("Confirm IP");
        panel1.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br=null;
                try{
                    //修改为绝对路径或者相对路径
                    // 文件地址，不管绝对相对，直接从根路径开始找，一直到jar包路径
                    String root=System.getProperty("user.dir");
                    String readFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                    System.out.println(readFilePath);
                    br=new BufferedReader(new FileReader(readFilePath));

                    String line;

                    if(pastField.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"IP is empty","title",JOptionPane.ERROR_MESSAGE);
                    }else{
                        int temp=0;
                        while((line=br.readLine())!=null){
                            if(line.contains(pastField.getText())){
                                // System.out.println(line);
                                confirmButton.setText("Original IP exists");
                                confirmButton.setEnabled(false);

                                changeIP.setEnabled(true);
                                temp=1;
                                break;
                            }
                        }

                        if(temp==0){
                            JOptionPane.showMessageDialog(null,"The IP does not exist, please re-enter.","title",JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }catch(IOException exception){
                    exception.printStackTrace();
                }finally {
                    try{
                        if(br!=null){
                            br.close();
                        }
                    }catch(IOException exception){
                        exception.printStackTrace();
                    }

                }
            }
        });
        //第二行功能结束
        */

        //第三行，显示修改IP的文件列表，并且修改
        JLabel showFile=new JLabel("Change file list");
        panel1.add(showFile);
        panel1.add(combo);
        panel1.add(changeIP);
        changeIP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String pastFieldId="127.0.0.1";
                //调用文件
                //不管绝对或者相对，都从放置的文件夹中找
                String root=System.getProperty("user.dir");
                //System.out.println("root=" +root);
                //三个后端
                String readAndWriteFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\MapTilesServer.json";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\CH_server4.json";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\test_rpc_LoadDataClient.json";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                //两个前端
                readAndWriteFilePath=root+"\\server_setting.js";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                readAndWriteFilePath=root+"\\dist\\static\\config\\serviceConfig.js";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                //还有一个推流
                readAndWriteFilePath=root+"\\mediamtx_v1.9.3_windows_amd64\\mediamtx.yml";
                readAndWriteFile(readAndWriteFilePath,pastFieldId,ipField.getText().trim());

                changeIP.setText("IP successfully switched");
                changeIP.setEnabled(false);
            }
        });
        //第三行功能结束


        //第四行 寻找需要修改的文件
        JLabel showNeedFile=new JLabel("Files need modified");
        JButton modifyNeedFile=new JButton("Find the file");

        modifyNeedFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowChooserFile();
            }
        });
        startModify=new JButton("Monitor link");   //这个按钮暂时还没用
        startModify.setBackground(Color.RED);
        startModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String root=System.getProperty("user.dir");
                String pathVBSData=root+"\\HYData.vbs";
                //System.out.println(path);
                try{


                    //processFront=rt.exec(cmdHY);
                    Runtime.getRuntime().exec("cscript //NoLogo " + pathVBSData);


                }catch(Exception exception){
                    exception.printStackTrace();
                }

            }
        });
        panel1.add(showNeedFile);
        panel1.add(modifyNeedFile);
        panel1.add(startModify);
        //第四行功能结束

        //第五行软件功能，清除软件缓存
        JButton cacheLabel=new JButton("cache location");
        cacheLabel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser=new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showSaveDialog(null);

                if(returnVal == JFileChooser.APPROVE_OPTION){
                    selectedFile=chooser.getSelectedFile().getAbsolutePath();
                    System.out.println(selectedFile);
                }

                cacheLabel.setText(selectedFile);
            }
        });
        dateOK=new JButton("Confirm time");
        dataRefresh datarefresh;
        dateOK.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    cs=new CalendarShow();
                    isLive=true;
                    dataRefresh datarefresh=new dataRefresh(cs);
                    datarefresh.start();

                }
        });
        JButton cacheButton=new JButton("Confirm Clear");
        cacheButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //获取需要的时间
                Integer time=acquireFileTime(ChangeIP.dateOK.getText());

                File file=new File(selectedFile);

                deleteDir(file,time);

                //System.out.println("确认清除");
                cacheLabel.setText("Clear cache location");

            }
        });
        panel1.add(cacheLabel);
        panel1.add(dateOK);
        panel1.add(cacheButton);
        cacheButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("aaaaa");
                cs.dispose();
                isLive=false;
            }
        });
        //第五行功能结束

        //第六行软件功能，一键重启以及CMD开启
        JButton  allSatrtLabel=new JButton("Debug click");
        allSatrtLabel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {



                String root=System.getProperty("user.dir");
                String pathBack=root+"\\C++backend.bat";
                String pathFront=root+"\\startex.bat";
                String pathIntoBigData=root+"\\IntoBigdata.bat";
                String pathMos=root+"\\mosquittostart.bat";
                String pathHY=root+"\\HYCMD.bat";
                //System.out.println(path);
                try{



                    //方案三  可以执行   cmd /c "你的命令" >nul 2>&1

                    String cmdBack="cmd.exe /k start /b "+pathBack;
                    String cmdFront="cmd.exe /k start /b "+pathFront;  // 其中的/b是隐藏黑色的窗口
                    String cmdIntoBigData="cmd.exe /k start /b "+pathIntoBigData;
                    String cmdMos="cmd.exe /k start /b "+pathMos;

                    String cmdHY="cmd.exe /k start /b "+pathHY;

                    rt=Runtime.getRuntime();

                    processFront=rt.exec(cmdHY);


                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });



        JButton allStart=new JButton("One-click shutdown");
        allStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //kill 后端线程
                    Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
                    Runtime.getRuntime().exec("taskkill /f /im test_loader.exe");
                    Runtime.getRuntime().exec("taskkill /f /im mosquitto.exe");
                    Runtime.getRuntime().exec("taskkill /f /im mediamtx.exe");
                    Runtime.getRuntime().exec("taskkill /f /im information_fusion_system.exe"); //杀掉前端进程

                    //找到前端进程
                    Runtime rt=Runtime.getRuntime();
                    Process proc=rt.exec("cmd /c netstat -ano | findstr 3007");

                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String s=null;
                    if((s=stdInput.readLine())!=null){
                        System.out.println(s);
                        int index=s.lastIndexOf(" ");
                        System.out.println(index);
                        String sc=s.substring(index,s.length());
                        System.out.println(sc);
                        rt.exec("Taskkill /PID"+sc+" /T /F");

                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });   //关闭程序，杀死进程

        JButton allRestart=new JButton("One-click restart");
        allRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //再次开启程序


                String root=System.getProperty("user.dir");
                String pathBack=root+"\\C++backend.bat";
                String pathFront=root+"\\startex.bat";
                String pathIntoBigData=root+"\\IntoBigdata.bat";
                String pathMos=root+"\\mosquittostart.bat";
                String pathHY=root+"\\HY.bat";
                String pathVBS=root+"\\HY.vbs";
                //System.out.println(path);
                try{




                    //方案三  可以执行

                    String cmdBack="cmd.exe /k start /b "+pathBack;
                    String cmdFront="cmd.exe /k start /b "+pathFront;  // 其中的/b是隐藏黑色的窗口
                    String cmdIntoBigData="cmd.exe /k start /b "+pathIntoBigData;
                    String cmdMos="cmd.exe /k start /b "+pathMos;

                    String cmdHY="cmd.exe /k start /b "+pathHY;

                    //rt=Runtime.getRuntime();
                    /*
                    processFront=rt.exec(cmdMos);
                    processFront=rt.exec(cmdBack);
                    processFront=rt.exec(cmdFront);
                    Thread.sleep(10000);
                    processFront=rt.exec(cmdIntoBigData);
                    */
                    //processFront=rt.exec(cmdHY);
                    Runtime.getRuntime().exec("cscript //NoLogo " + pathVBS);


                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        panel1.add(allSatrtLabel);
        panel1.add(allStart);
        panel1.add(allRestart);
        //第六行功能结束


        //最终的中部界面
        frame.add(panel1,BorderLayout.CENTER);


        // 加入南部按钮
        Panel panel2=new Panel();
        panel2.setLayout(new GridLayout(1,2,2,2));
        panel2.add(new JButton("OK"));
        panel2.add(new JButton("Cancel"));
        frame.add(panel2,BorderLayout.SOUTH);

        // 设置窗口为居中
        setFrameCenter(frame,480,240);

        //关闭小程序窗口
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //开启刷新线程
        linkBigDataMonitor linkBigDataMonitor=new linkBigDataMonitor();
        linkBigDataMonitor.start();

    }

    //之后会将所有的方法放到专门的utils包下面


    //文件读取以及修改
    public static void readAndWriteFile(String fileName,String oldText,String newText){
        BufferedReader br=null;
        BufferedWriter writer=null;
        try{
            br=new BufferedReader(new FileReader(fileName));

            String line;
            StringBuilder content=new StringBuilder();
            while((line=br.readLine())!=null){

                line=line.replace(oldText,newText);
                content.append(line).append(System.lineSeparator());
            }
            br.close();
            writer=new BufferedWriter(new FileWriter(fileName));
            writer.write(content.toString());
            writer.close();

        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    //获取文件的时间，并且转换为Integer，并且比较
    public static Integer acquireFileTime(String timeText){

            StringBuilder sb=new StringBuilder();
            for(String splitStrings: timeText.split("-")) {
                //System.out.println(splitStrings);
                Integer temp=Integer.parseInt(splitStrings);
                if(temp<10){
                    splitStrings="0"+temp;
                    System.out.println(splitStrings);
                }
                sb.append(splitStrings);
            }

            return Integer.parseInt(sb.toString());
    }

    //删除文件以及文件夹
    public static void deleteDir(File files,Integer appTime) {
        //获取File对象中的所有文件，并将其放在数组中
        //File files=new File(filesTemp);
        File[] listFiles = files.listFiles();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        //循环遍历数组
        for (File file: listFiles) {
            //如果是目录文件，则递归调用删除方法

            if (file.isDirectory()) {
                deleteDir(file,appTime);
                if(file.length()==0){
                    file.delete();
                }
            }

            //这是获取缓存的时间
            String testFile=sdf.format(new Date(file.lastModified())).toString();
            Integer fileTimeInt=acquireFileTime(testFile);

            if(appTime>fileTimeInt){
                //System.out.println(f.getName());
                file.delete();
            }

        }
        //删除文件夹内所有文件后，再删除文件夹
        //files.delete();
    }

    //设置窗体居中
    public static void setFrameCenter(JFrame frame,int x,int y){

        frame.setSize(x,y);
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        int locationX=(int)(screenSize.getWidth()/2-frame.getWidth()/2);
        int locationY=(int)(screenSize.getHeight()/2-frame.getHeight()/2);
        frame.setLocation(locationX,locationY);
    }

    //判断进程是否存货
    public static int isProcessRunning(String processName) {

        HashMap<Integer,Integer> singalMap=new HashMap();
        try{
            Process process = Runtime.getRuntime().exec(getCommand(processName));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while((line=reader.readLine())!=null){
                if(line.contains(processName)){
                    //System.out.println(line);
                    String[] parts = line.trim().split("\\s+");
                    int pid=Integer.parseInt(parts[1]);
                    //return true;
                    singalMap.put(pid,1);

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        singalNumber=singalMap.size();
        return singalNumber;

    }

    private static String getCommand(String processName){
        String os=System.getProperty("os.name").toLowerCase();

        if(os.contains("win")){
            return "tasklist";
        }else if(os.contains("nix") || os.contains("nux") || os.contains("mac")){
            return "ps -ef";
        }

        throw new UnsupportedOperationException("Unsupported operating system");
    }
}

// 这个线程启动，获取日历组件的时间列表
class dataRefresh extends Thread{

    public CalendarShow currentMonth;

    public dataRefresh(CalendarShow currentMonth){
        this.currentMonth=currentMonth;
    }
    @Override
    public void run(){
        while(ChangeIP.isLive){

            //System.out.println(currentMonth.toTimeStammp);
            ChangeIP.dateOK.setText(currentMonth.toTimeStammp);
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class linkBigDataMonitor extends Thread{

    @Override
    public void run(){
        //boolean flag=false;
        while(true){
            if(ChangeIP.isProcessRunning("test_loader")>=4){


                ChangeIP.SingalMonitor=ChangeIP.isProcessRunning("test_loader")-3;


                ChangeIP.startModify.setBackground(Color.GRAY);
                ChangeIP.startModify.setText( ChangeIP.SingalMonitor+" 路数据接入");
            }else{


                ChangeIP.SingalMonitor=0;
                ChangeIP.startModify.setBackground(Color.RED);
                ChangeIP.startModify.setText(ChangeIP.SingalMonitor+" 路数据接入");


                /*
                String root=System.getProperty("user.dir");
                String pathIntoBigData=root+"\\IntoBigdata.bat";
                try{


                    //方案三  可以执行

                    String cmdIntoBigData="cmd.exe /k start /b "+pathIntoBigData;

                    Runtime.getRuntime().exec(cmdIntoBigData);


                }catch(Exception exception){
                    exception.printStackTrace();
                }

                *
                 */


            }

            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            ChangeIP.frame.repaint();

        }
    }
}
