package com.zh.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CloseBat {
    public static void main(String args[]){
        String root=System.getProperty("user.dir");

        try{
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
                rt.exec("cmd /c Taskkill /PID"+sc+" /T /F");
            }

            //System.out.println("bbbb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
