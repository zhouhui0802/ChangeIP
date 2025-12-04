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
import java.util.Optional;

// 开始处理线程，做好做到不断地刷新
public class ChangeIP {

        public static String timeStamp;

        public static JButton dateOK;
        static CalendarShow cs;
        public static boolean isLive=true;
        public static ProcessBuilder pbFront;
        public static Process processFront;
        public static Runtime rt;

        //获取文件所在地址
        public static String selectedFile=null;


        //默认应该是关闭进程
        public static int onOrOff=0;

        public static void main(String[] args) {



        //开头的标题
        JFrame frame=new JFrame("IP地址修改");

        //小程序的北部标题
        Panel panel=new Panel();
        panel.add(new JLabel("一键更改配置IP地址"));
        frame.add(panel,BorderLayout.NORTH);

        //专门放置  用户操作按钮  小程序的中部按钮
        Panel panel1=new Panel();
        panel1.setLayout(new GridLayout(6,3,2,2));

        JComboBox combo=new JComboBox();
        combo.addItem("更改文件如下");
        combo.addItem("mediamtx.yml");
        combo.addItem("test_fusion_seat.json");
        combo.addItem("CH_server4.json");
        combo.addItem("test_rpc_LoadDataClient.json");
        combo.addItem("MapTilesServer.json");
        combo.addItem("server_setting.js");
        combo.addItem("serviceConfig.js");

        JLabel ipLabel=new JLabel("当前电脑IP地址");
        panel1.add(ipLabel);

        JTextField ipField=new JTextField(20);
        panel1.add(ipField);

        //获取当前的IP地址
        JButton ipButton=new JButton("获取IP");
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

        // 输入原来的IP地址
        JButton changeIP=new JButton("一键更改");
        changeIP.setEnabled(false);


        JLabel pastLabel=new JLabel("输入原电脑IP");
        panel1.add(pastLabel);

        JTextField pastField=new JTextField(20);
        panel1.add(pastField);

        JButton confirmButton=new JButton("确认原IP");
        panel1.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br=null;
                try{
                    //修改为绝对路径或者相对路径
                    // 文件地址，不管绝对相对，直接从根路径开始找，一直到jar包路径
                    String root=System.getProperty("user.dir");
                    // System.out.println(root);
                    String readFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                    System.out.println(readFilePath);
                    br=new BufferedReader(new FileReader(readFilePath));

                    String line;
                    //System.out.println("数值是："+pastField.getText());
                    if(pastField.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"输入IP地址为空","标题",JOptionPane.ERROR_MESSAGE);
                    }else{
                        int temp=0;
                        while((line=br.readLine())!=null){
                            if(line.contains(pastField.getText())){
                                // System.out.println(line);
                                confirmButton.setText("原IP存在");
                                confirmButton.setEnabled(false);

                                changeIP.setEnabled(true);
                                temp=1;
                                break;
                            }
                        }

                        if(temp==0){
                            JOptionPane.showMessageDialog(null,"输入IP地址不存在，请重新输入","标题",JOptionPane.ERROR_MESSAGE);
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

        //显示修改IP地址的文件列表
        JLabel showFile=new JLabel("更改文件列表");
        panel1.add(showFile);
        panel1.add(combo);
        panel1.add(changeIP);
        changeIP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //调用文件
                //不管绝对或者相对，都从放置的文件夹中找
                String root=System.getProperty("user.dir");
                System.out.println("root=" +root);
                //三个后端
                String readAndWriteFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\MapTilesServer.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\CH_server4.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\test_rpc_LoadDataClient.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                //两个前端
                readAndWriteFilePath=root+"\\server_setting.js";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\dist\\static\\config\\serviceConfig.js";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                //还有一个推流
                readAndWriteFilePath=root+"\\mediamtx_v1.9.3_windows_amd64\\mediamtx.yml";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                changeIP.setText("IP地址切换成功");
                changeIP.setEnabled(false);
            }
        });


        //寻找需要修改的文件
        JLabel showNeedFile=new JLabel("需要修改的文件");
        JButton modifyNeedFile=new JButton("寻找该文件");

        modifyNeedFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowChooserFile();
            }
        });
        JButton startModify=new JButton("开始修改文件");
        panel1.add(showNeedFile);
        panel1.add(modifyNeedFile);
        panel1.add(startModify);

        //清楚软件的缓存
        JButton cacheLabel=new JButton("软件缓存位置");
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
        dateOK=new JButton("确认时间");
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
        JButton cacheButton=new JButton("确认清除");
        cacheButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //获取需要的时间
                Integer time=acquireFileTime(ChangeIP.dateOK.getText());


                File file=new File(selectedFile);



                deleteDir(file,time);

                //System.out.println("确认清除");
                cacheLabel.setText("清楚缓存位置");

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

        JButton  allSatrtLabel=new JButton("调试一键开启");
        allSatrtLabel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //杀死所有进程
                /*
                try {
                    //kill 后端线程
                    Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

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

                 */

                //再次开启程序


                String root=System.getProperty("user.dir");
                String pathBack=root+"\\C++backend.bat";
                String pathFront=root+"\\startex.bat";
                String pathIntoBigData=root+"\\IntoBigdata.bat";
                String pathMos=root+"\\mosquittostart.bat";
                String pathHY=root+"\\HYCMD.bat";
                //System.out.println(path);
                try{

                    //ProcessBuilder processBuilder=new ProcessBuilder();
                    /*
                    String pathVbs=root+"\\HY.vbs";
                    //processBuilder.command("csript",pathVbs);
                    //Process process=processBuilder.start();
                    Runtime.getRuntime().exec("cmd /c /b "+pathVbs);
                    */


                    //方案三  可以执行   cmd /c "你的命令" >nul 2>&1

                    String cmdBack="cmd.exe /k start /b "+pathBack;
                    String cmdFront="cmd.exe /k start /b "+pathFront;  // 其中的/b是隐藏黑色的窗口
                    String cmdIntoBigData="cmd.exe /k start /b "+pathIntoBigData;
                    String cmdMos="cmd.exe /k start /b "+pathMos;

                    String cmdHY="cmd.exe /k start /b "+pathHY;

                    rt=Runtime.getRuntime();
                    /*
                    processFront=rt.exec(cmdMos);
                    processFront=rt.exec(cmdBack);
                    processFront=rt.exec(cmdFront);
                    Thread.sleep(10000);
                    processFront=rt.exec(cmdIntoBigData);
                    */
                    processFront=rt.exec(cmdHY);


                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
/*        if(onOrOff==0){
            allSatrtLabel.setEnabled(false);
        }*/

        //开始调用HY的bat程序  总体调用
        JButton allStart=new JButton("一键关闭");
        allStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //kill 后端线程
                    Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
                    Runtime.getRuntime().exec("taskkill /f /im test_loader.exe");
                    Runtime.getRuntime().exec("taskkill /f /im mosquitto.exe");
                    Runtime.getRuntime().exec("taskkill /f /im mediamtx.exe");

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
        });

        JButton allRestart=new JButton("一键重启");
        allRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                /*
                //杀死所有进程
                try {
                    //kill 后端线程
                    Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

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


                 */
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

                    //ProcessBuilder processBuilder=new ProcessBuilder();
                    /*
                    String pathVbs=root+"\\HY.vbs";
                    //processBuilder.command("csript",pathVbs);
                    //Process process=processBuilder.start();
                    Runtime.getRuntime().exec("cmd /c /b "+pathVbs);
                    */


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



        //最终的中部界面
        frame.add(panel1,BorderLayout.CENTER);

        // 加入南部按钮
        Panel panel2=new Panel();
        panel2.setLayout(new GridLayout(1,2,2,2));
        panel2.add(new JButton("确定"));
        panel2.add(new JButton("取消"));

        // 设置窗口为居中
        frame.setSize(400,260);
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        int x=(int)(screenSize.getWidth()/2-frame.getWidth()/2);
        int y=(int)(screenSize.getHeight()/2-frame.getHeight()/2);

        //关闭小程序窗口

        frame.add(panel2,BorderLayout.SOUTH);
        frame.setLocation(x,y);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

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
            //System.out.println(fileTimeInt);
            //System.out.println("这是获取APP上的时间time: "+time);
            //System.out.println("这是获取文件的时间: "+fileTimeInt);
            if(appTime>fileTimeInt){
                //System.out.println(f.getName());
                file.delete();
            }



        }
        //删除文件夹内所有文件后，再删除文件夹
        //files.delete();

    }

}


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
//ip:127.0.0.1
//table:127.0.0.1
