package com.zh.test;

import java.io.*;

public class testIO {
    public static void main(String[] args) {
        BufferedReader br=null;
        BufferedWriter writer=null;
        try{
            //修改为绝对路径或者相对路径
            String pastName="D:\\test.txt";
            String pastName1="D:\\test1.txt";

            br=new BufferedReader(new FileReader(pastName));
            writer=new BufferedWriter(new FileWriter(pastName1,true));
            String line;
            while((line=br.readLine())!=null){

                /*
                if(line.contains(pastField.getText())){
                    // System.out.println(line);
                    line=line.replace(pastField.getText(),ipField.getText().trim());
                    writer.write("aaaaa");
                    writer.newLine();
                    continue;
                }
                 */
                System.out.println(line);
                writer.write(line+"test");
                writer.newLine();
            }

            File file=new File(pastName);
            File file1=new File(pastName1);
            if(file1.renameTo(file)){
                System.out.println("reset done");
            }

        }catch(IOException exception){
            exception.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.flush();
                    writer.close();
                }
                if(br!=null){
                    br.close();
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println("done");

    }
}
