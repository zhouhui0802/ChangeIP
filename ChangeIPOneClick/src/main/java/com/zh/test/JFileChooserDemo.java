package com.zh.test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class JFileChooserDemo extends JFrame {
    private JTextArea textArea;

    public JFileChooserDemo() {
        setTitle("JFileChooser 文本文件示例");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem openMenuItem = new JMenuItem("打开");

        openMenuItem.addActionListener(e -> openFile());

        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        //JPanel panel1=new JPanel();
        //panel1.add(scrollPane);
        add(scrollPane,BorderLayout.CENTER);



        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br=null;
                BufferedWriter writer=null;
                try{
                    //修改为绝对路径或者相对路径
                    // 文件地址，不管绝对相对，直接从根路径开始找，一直到jar包路径
                    String root=System.getProperty("user.dir");
                    String readFilePath=root+"\\bin\\conf\\test_fusion_seat.json";
                    String writePath=readFilePath;
                    br=new BufferedReader(new FileReader(readFilePath));

                    String line;
                    StringBuilder content=new StringBuilder();
                    while((line=br.readLine())!=null){

                        //line=line.replace(pastField.getText(),ipField.getText().trim());
                        //content.append(line).append(System.lineSeparator());
                    }
                    br.close();
                    writer=new BufferedWriter(new FileWriter(writePath));
                    writer.write(content.toString());
                    writer.close();

                }catch(IOException exception){
                    exception.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Cancel");
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2,1,1));
        panel2.add(okButton);
        panel2.add(cancelButton);
        add(panel2,BorderLayout.SOUTH);
        setVisible(true);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        // 设置过滤器，只允许选择txt文件
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("文本文件(*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "读取文件时发生错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JFileChooserDemo::new);
    }
}

