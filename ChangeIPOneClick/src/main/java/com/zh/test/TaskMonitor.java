package com.zh.test;

public class TaskMonitor {

    public static void main(String[] args) {
        // String processName = "mediamtx.exe";
        String processName = "information_fusion_system.exe";

            if(ProcessChecker.isProcessRunning(processName)){
                System.out.println("线程正在运行");
            }else{
                System.out.println("线程已经停止");
            }


    }
}
