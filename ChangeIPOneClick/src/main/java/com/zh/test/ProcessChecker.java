package com.zh.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessChecker {

    public static boolean isProcessRunning(String processName) {

        try{
            Process process = Runtime.getRuntime().exec(getCommand(processName));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while((line=reader.readLine())!=null){
                if(line.contains(processName)){
                    System.out.println(line);
                    //return true;
                }



            }
        }catch(Exception e){
            e.printStackTrace();
        }



        return false;
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
