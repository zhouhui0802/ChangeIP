package com.zh.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Inet4Address;

public class ChangeIP {

    public static String timeStamp;
    public static JButton dateOK;
    static CalendarShow cs;
    public static boolean isLive=true;
    public static void main(String[] args) {



        //开头的标题
        JFrame frame=new JFrame("IP地址修改");

        //小程序的北部标题
        Panel panel=new Panel();
        panel.add(new JLabel("一键更改配置IP地址"));
        frame.add(panel,BorderLayout.NORTH);

        //专门放置  用户操作按钮  小程序的中部按钮
        Panel panel1=new Panel();
        panel1.setLayout(new GridLayout(5,3,2,2));

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
                String readAndWriteFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\CH_server4.json";
                readAndWriteFile(readAndWriteFilePath,pastField.getText(),ipField.getText().trim());

                readAndWriteFilePath=root+"\\bin\\conf\\test_rpc_LoadDataClient.json";
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

        frame.add(panel1,BorderLayout.CENTER);

        // 加入南部按钮
        Panel panel2=new Panel();
        panel2.setLayout(new GridLayout(1,2,2,2));
        panel2.add(new JButton("确定"));
        panel2.add(new JButton("取消"));

        // 设置窗口为居中
        frame.setSize(400,200);
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
