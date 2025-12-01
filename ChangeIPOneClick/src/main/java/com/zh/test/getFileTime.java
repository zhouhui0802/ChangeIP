package com.zh.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getFileTime {
    public static void main(String[] args) {
        File file=new File("D://dist");
        //System.out.println(new Date(file.lastModified()));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        File[] fs = file.listFiles();
        int i=0;
        for(File f:fs) {
            String filename=f.getName();
            //System.out.println(filename);
            //System.out.println(sdf.format(new Date(f.lastModified())).toString());
            StringBuilder sb=new StringBuilder();
            String testFile=sdf.format(new Date(f.lastModified())).toString();
            for(String splitStrings: testFile.split("-")){
                //System.out.println(splitStrings);
                sb.append(splitStrings);
            }
            testFile=sb.toString();
            Integer fileTimeInt=Integer.parseInt(testFile);
            System.out.println(fileTimeInt);
            i++;
        }

        //System.out.println("i的数值为"+i);

        String a="09";
        String b="08";
        int tempA=Integer.parseInt(a);
        int tempB=Integer.parseInt(b);
        if(tempA>=tempB){
            System.out.println("测试成功");
        }

        file=new File("D://Test");
        file.delete();
    }
}
