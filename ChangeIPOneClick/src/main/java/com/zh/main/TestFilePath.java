package com.zh.main;

import java.io.*;

public class TestFilePath {
    public static void main(String[] args) {
        BufferedReader br=null;
        try{
            //修改为绝对路径或者相对路径
            br=new BufferedReader(new FileReader("D:\\test.txt"));
            System.out.println(TestFilePath.class.getClassLoader().getResource("zhouhuiTest.txt").getPath());
            String line;
            StringBuilder content=new StringBuilder();
            while((line=br.readLine())!=null){

                System.out.println(line);
            }
            br.close();


        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
