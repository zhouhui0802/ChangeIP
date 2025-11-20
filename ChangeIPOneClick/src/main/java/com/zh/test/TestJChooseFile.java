package com.zh.test;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestJChooseFile {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        frame.add(chooser);

        int result = chooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectFile = chooser.getSelectedFile();

            try(BufferedReader reader = new BufferedReader(new FileReader(selectFile))){

                String line;
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        frame.setBounds(100, 100, 450, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
