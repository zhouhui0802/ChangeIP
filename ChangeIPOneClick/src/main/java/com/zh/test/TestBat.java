package com.zh.test;

public class TestBat {
    public static void main(String args[]){
        String root=System.getProperty("user.dir");

        try{
          ProcessBuilder processBuilder=new ProcessBuilder("tasklist");
          Process process= processBuilder.start();

          String os= System.getenv("OS");

          String command=null;

          if(os.startsWith("Windows")){
              command= "taskkill /F /PID "+process.pid();
          }

          ProcessBuilder processBuilder1=new ProcessBuilder(command);
          Process process1=processBuilder1.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
