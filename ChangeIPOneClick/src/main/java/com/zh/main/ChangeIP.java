package com.zh.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.ProcessHandle;
import java.net.Inet4Address;
import java.util.Optional;

public class ChangeIP {

        public static String timeStamp;
        public static JButton dateOK;
        static CalendarShow cs;
        public static boolean isLive=true;
        public static ProcessBuilder pbFront;
        public static Process processFront;
        public static Runtime rt;

        public static void main(String[] args) {



        //开头的标题
        JFrame frame=new JFrame("IP地址修改");

        //小程序的北部标题
        Panel panel=new Panel();
        panel.add(new JLabel("一键更改配置IP地址"));
        frame.add(panel,BorderLayout.NORTH);

        //专门放置  用户操作按钮  小程序的中部按钮
        Panel panel1=new Panel();
        panel1.setLayout(new GridLayout(8,3,2,2));

        JComboBox combo=new JComboBox();
        combo.addItem("更改文件如下");
        combo.addItem("test_fusion_seat.json");
        combo.addItem("CH_server4.json");
        combo.addItem("test_rpc_LoadDataClient.json");

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
                    String readFilePath=root+"\\backend\\bin\\conf\\test_fusion_seat.json";
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
                String readAndWriteFilePath=root+"\\backend\\bin\\conf\\test_fusion_seat.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\backend\\bin\\conf\\CH_server4.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\backend\\bin\\conf\\test_rpc_LoadDataClient.json";
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
        JLabel cacheLabel=new JLabel("清理软件缓存");
        dateOK=new JButton("确认时间");
        JButton cacheButton=new JButton("确认清除");
        panel1.add(cacheLabel);
        panel1.add(dateOK);
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
        panel1.add(cacheButton);
        cacheButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("aaaaa");
                cs.dispose();
                isLive=false;
            }
        });

        //开始调用HY的bat程序  前端
        JButton frontStart=new JButton("开启前端");
        frontStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String root=System.getProperty("user.dir");
                String path=root+"\\front\\startex.bat";
                //System.out.println(path);
                try{


                    //方案三  可以执行

                    String cmd="cmd.exe /k start "+path;
                    rt=Runtime.getRuntime();
                    processFront=rt.exec(cmd);
                    //System.out.println("aaaaa");


                    //方案4  最好可以直接获取到该pid
                    /*
                    pbFront=new ProcessBuilder("cmd.exe","/k","start", path);
                    processFront=pbFront.start();
                    */


                    //ProcessHandle handle=processFront.toHandle();
                    //System.out.println(handle.pid());


                }catch(Exception exception){
                    exception.printStackTrace();
                }

            }
        });
        JButton hideFront = new JButton("隐藏前端CMD");
        hideFront.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton frontRestart=new JButton("重启前端");
        frontRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String root=System.getProperty("user.dir");
                String path=root+"\\front\\startex.bat";
                try{
                    //pbFront=new ProcessBuilder("cmd.exe","/c","start", path);
                    //processFront=pbFront.start();

                    //ProcessHandle handle=processFront.toHandle();
                    //ProcessHandle totalHandle=ProcessHandle.current();



                    // 遍历 CMD 进程的所有子进程（bat 脚本内启动的程序）
                    /*
                    handle.children().forEach(childHandle -> {
                        long childPid = childHandle.pid();
                        Optional<String> childName = childHandle.info().command();
                        System.out.println("CMD 子进程 PID：" + childPid + "，进程名：" + childName.orElse("未知"));
                    });
                    */



                    /*
                    Runtime rt=Runtime.getRuntime();
                    System.out.println(handle.pid());
                    rt.exec("cmd /c Taskkill /PID"+handle.pid()+" /T /F");
                    */
                    //再次重启
                    //processFront=pbFront.start();

                    /*
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String s=null;
                    if((s=stdInput.readLine())!=null){
                        System.out.println(s);
                        int index=s.lastIndexOf(" ");
                        System.out.println(index);
                        String sc=s.substring(index,s.length());
                        System.out.println(sc);
                        rt.exec("cmd /c Taskkill /PID"+sc+" /T /F");
                    }

                     */


                    //Runtime rt=Runtime.getRuntime();
                    processFront=rt.exec("cmd /c netstat -ano | findstr 3007");

                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(processFront.getInputStream()));
                    String s=null;
                    if((s=stdInput.readLine())!=null){
                        System.out.println(s);
                        int index=s.lastIndexOf(" ");
                        System.out.println(index);
                        String sc=s.substring(index,s.length());
                        System.out.println(sc);
                        rt.exec("cmd /c Taskkill /PID"+sc+" /T /F");
                    }

                    String cmd="cmd.exe /k start "+path;
                    processFront=rt.exec(cmd);

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        panel1.add(frontStart);
        panel1.add(hideFront);
        panel1.add(frontRestart);

        //开始调用HY的bat程序  后端
        JButton backStart=new JButton("开启后端");
        backStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String root=System.getProperty("user.dir");
                String path=root+"\\backend\\C++backend.bat";
                //System.out.println(path);
                try{


                    //方案三  可以执行

                    String cmd="cmd.exe /k start "+path;
                    rt=Runtime.getRuntime();
                    processFront=rt.exec(cmd);
                    //System.out.println("aaaaa");


                    //方案4  最好可以直接获取到该pid
                    /*
                    pbFront=new ProcessBuilder("cmd.exe","/k","start", path);
                    processFront=pbFront.start();
                    */


                    //ProcessHandle handle=processFront.toHandle();
                    //System.out.println(handle.pid());


                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        JButton hideBack = new JButton("隐藏后端CMD");
        JButton backRestart=new JButton("重启后端");
        panel1.add(backStart);
        panel1.add(hideBack);
        panel1.add(backRestart);

        //开始调用HY的bat程序  总体调用
        JButton allStart=new JButton("一键开启");
        allStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String root=System.getProperty("user.dir");
                String pathBack=root+"\\backend\\C++backend.bat";
                String pathFront=root+"\\front\\startex.bat";
                //System.out.println(path);
                try{


                    //方案三  可以执行

                    String cmdBack="cmd.exe /k start "+pathBack;
                    String cmdFront="cmd.exe /k start "+pathFront;
                    rt=Runtime.getRuntime();
                    processFront=rt.exec(cmdBack);
                    processFront=rt.exec(cmdFront);
                    //System.out.println("aaaaa");


                    //方案4  最好可以直接获取到该pid
                    /*
                    pbFront=new ProcessBuilder("cmd.exe","/k","start", path);
                    processFront=pbFront.start();
                    */


                    //ProcessHandle handle=processFront.toHandle();
                    //System.out.println(handle.pid());


                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        JButton hideAll = new JButton("隐藏全部CMD");
        JButton allRestart=new JButton("一键重启");
        allRestart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");

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
        panel1.add(allStart);
        panel1.add(hideAll);
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


}


class dataRefresh extends Thread{

    public CalendarShow currentMonth;

    public dataRefresh(CalendarShow currentMonth){
        this.currentMonth=currentMonth;
    }
    @Override
    public void run(){
        while(ChangeIP.isLive){

           System.out.println(currentMonth.toTimeStammp);
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
