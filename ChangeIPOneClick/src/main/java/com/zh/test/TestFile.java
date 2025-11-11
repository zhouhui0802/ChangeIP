package com.zh.test;

import java.io.File;

public class TestFile {
    public static void main(String[] args) {
        File file=new File("D:\\test.txt");
        File file1=new File("D:\\test2.txt");

        if(file1.renameTo(file)){
            System.out.println("rest done");
        }
    }
}
