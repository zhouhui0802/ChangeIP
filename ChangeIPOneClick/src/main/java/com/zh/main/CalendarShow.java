package com.zh.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CalendarShow extends JFrame implements ActionListener {


    private JButton button1,button2,button3;

    private JPanel pCenter = new JPanel(new GridLayout(6,7));
    private JPanel pNorth = new JPanel(new FlowLayout());
    private JPanel pSouth= new JPanel(new GridLayout(1,3));

    private Date current;
    private JLabel label;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JLabel yMonth,selectTime;

    public static String timeStamp;
    public String toTimeStammp;
    CalendarShow(){
        current=new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM");

        this.setLayout(new BorderLayout());
        this.setSize(494,293);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        button1=new JButton("上月");
        button2=new JButton("下月");
        pNorth.add(button1);
        pNorth.add(button2);
        button1.addActionListener(this);
        button2.addActionListener(this);

        this.add(pNorth,BorderLayout.NORTH);


        yMonth =new JLabel();
        selectTime=new JLabel();


        JPanel calendarPanel=new JPanel(new BorderLayout());
        JPanel week=new JPanel(new GridLayout(1,7));
        String[] weekNo = {"日","一","二","三","四","五","六"};
        for(int i=0;i<weekNo.length;i++){
            JLabel w=new JLabel(weekNo[i],JLabel.CENTER);
            week.add(w);
        }

        calendarPanel.add(week,BorderLayout.NORTH);
        //此处先修饰了一下
        PrintCalendar(current);

        calendarPanel.add(pCenter,BorderLayout.CENTER);

        this.add(calendarPanel,BorderLayout.CENTER);

        selectTime.setText("获取需要的时间");
        button3=new JButton("确认选择时间");
        selectTime.setForeground(Color.RED);
        pSouth.add(selectTime);
        pSouth.add(yMonth);
        pSouth.add(button3);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ChangeIP.timeStamp=timeStamp;
                //System.out.println(ChangeIP.timeStamp);
                toTimeStammp=timeStamp;
                //timeStamp=null;
                //System.out.println(toTimeStammp);
            }
        });

        this.add(pSouth,BorderLayout.SOUTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void PrintCalendar(Date date){
        pCenter.removeAll();

        yMonth.setText("当前时间："+dateFormat.format(date).subSequence(0,4)+"年"+dateFormat.format(date).substring(5)+"月");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,1);
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK)-1;

        int dayMonth=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i=0;i<weekDay;i++){
            pCenter.add(new JLabel());
        }

        for(int i=1;i<=dayMonth;i++){
            JButton day=new JButton(String.valueOf(i));
            day.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("被点击了"+day.getText());
                    /*tempButtonNumber++;
                    if(tempButtonNumber%2!=0){
                        day.setBackground(Color.red);
                    }else{
                        day.setBackground(Color.green);
                    }*/
                    timeStamp=(String)(dateFormat.format(date).subSequence(0,4)+"-"+dateFormat.format(date).substring(5)+"-"+day.getText());
                    selectTime.setText("获取需要的时间"+timeStamp);
                    toTimeStammp=timeStamp;
                }
            });

            pCenter.add(day);
        }

        int c =42 -dayMonth-weekDay;
        for(int i=0;i<c;i++){
            pCenter.add(new JLabel());
        }

        pCenter.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==button1){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.MONTH,-1);
            current=calendar.getTime();
            PrintCalendar(current);
        }else if(e.getSource()==button2){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.MONTH,1);
            current=calendar.getTime();
            PrintCalendar(current);
        }
    }

    public static void main(String[] args) {
        new CalendarShow();
    }
}

