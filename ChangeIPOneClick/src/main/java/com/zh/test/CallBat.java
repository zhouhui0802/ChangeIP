package com.zh.test;

public class CallBat {

    public static void main(String args[]){
        String root=System.getProperty("user.dir");

        try{
            Runtime rt=Runtime.getRuntime();

            Process proc=rt.exec("cmd /c start D:\\2222\\startex.bat");

            System.out.println("aaaa");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
