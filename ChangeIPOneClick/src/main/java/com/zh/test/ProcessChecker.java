package com.zh.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ProcessChecker {

    public static boolean isProcessRunning(String processName) {

        try{
            Process process = Runtime.getRuntime().exec(getCommand(processName));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            HashMap<Integer, Integer> map = new HashMap<>();

            while((line=reader.readLine())!=null){
                if(line.contains(processName)){
                    String[] parts = line.trim().split("\\s+");
                    int pid=Integer.parseInt(parts[1]);
                    System.out.println(pid);

                    map.put(pid, 0);
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
