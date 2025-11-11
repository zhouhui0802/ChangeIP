package com.zh.main;

import java.io.*;

public class TextFileReplace {

    public static void main(String[] args) {

        String filePath="D:\\test.txt";
        String oldText="1111";
        String newText="zhouhui";

        replaceTextFile(filePath,oldText,newText);
    }

    public static void replaceTextFile(String filePath,String oldText,String newText) {
        try{
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();

            String line;

            while((line=reader.readLine())!=null){
                line=line.replace(oldText,newText);
                content.append(line).append(System.lineSeparator());
            }

            reader.close();

            //写回文本
            BufferedWriter writer=new BufferedWriter(new FileWriter(filePath));
            writer.write(content.toString());
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
