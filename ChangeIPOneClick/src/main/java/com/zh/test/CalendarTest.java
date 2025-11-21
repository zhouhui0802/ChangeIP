package com.zh.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CalendarTest implements ActionListener {

    private JFrame jFrame;
    private JButton button1,button2;

    private JPanel pCenter = new JPanel(new GridLayout(6,7));
    private JPanel pNorth = new JPanel(new FlowLayout());
    private JPanel pSouth= new JPanel(new FlowLayout());

    private Date current;
    private JLabel label;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JLabel year,month,selectTime;

    public static String timeStamp;

    CalendarTest(){
        current=new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM");
        jFrame = new JFrame("日历");
        jFrame.setLayout(new BorderLayout());
        jFrame.setSize(494,293);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        button1=new JButton("上月");
        button2=new JButton("下月");
        pNorth.add(button1);
        pNorth.add(button2);
        button1.addActionListener(this);
        button2.addActionListener(this);

        jFrame.add(pNorth,BorderLayout.NORTH);


        year =new JLabel();
        month =new JLabel();
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

        jFrame.add(calendarPanel,BorderLayout.CENTER);

        selectTime.setText("获取需要的时间");
        pSouth.add(selectTime);
        pSouth.add(year);
        pSouth.add(month);

        jFrame.add(pSouth,BorderLayout.SOUTH);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void PrintCalendar(Date date){
        pCenter.removeAll();

        year.setText("当前日期"+dateFormat.format(date).subSequence(0,4)+"年");
        month.setText(dateFormat.format(date).substring(5)+"月");


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
                    System.out.println("被点击了"+day.getText());
                    timeStamp=(String)(dateFormat.format(date).subSequence(0,4)+"--"+dateFormat.format(date).substring(5)+"--"+day.getText());
                    selectTime.setText("获取需要的时间"+timeStamp);
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
        new CalendarTest();
    }
}
