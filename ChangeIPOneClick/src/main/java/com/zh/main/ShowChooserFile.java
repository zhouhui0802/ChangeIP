package com.zh.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class ShowChooserFile extends JFrame {

    private JTextArea textArea;

    public File globalFile;
    public ShowChooserFile() {
        setTitle("选定文件修改界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("选择文件");
        JMenuItem openMenuItem = new JMenuItem("打开本地文件");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        //
        add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2,1,1));
        panel2.add(okButton);
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                try{
                    //修改为绝对路径或者相对路径
                    // 文件地址，不管绝对相对，直接从根路径开始找，一直到jar包路径
                    //String root=System.getProperty("user.dir");
                    //String readFilePath=root+"\\bin\\conf\\CH_server4.json";
                    //String writePath=readFilePath;
                    //br=new BufferedReader(new FileReader(readFilePath));

                    //String line;
                    //StringBuilder content=new StringBuilder();
                    //while((line=br.readLine())!=null){

                        //line=line.replace(pastField.getText(),ipField.getText().trim());
                        //content.append(line).append(System.lineSeparator());
                    //}
                    //br.close();
                    if(globalFile==null){
                        JOptionPane.showMessageDialog(null,"请先点击菜单中的选择文件按钮","标题",JOptionPane.ERROR_MESSAGE);
                    }
                    BufferedWriter writer=new BufferedWriter(new FileWriter(globalFile));
                    writer.write(textArea.getText());
                    writer.close();
                    System.out.println(writer);
                    if(writer!=null){
                        JOptionPane.showMessageDialog(null,"已经成功修改选择的文本内容","标题",JOptionPane.INFORMATION_MESSAGE);
                    }

                }catch(IOException exception){
                    exception.printStackTrace();
                }


            }
        });
        panel2.add(cancelButton);
        add(panel2,BorderLayout.SOUTH);

        setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();

        // 设置过滤器，只允许选择txt文件
        //FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("文本文件(*.txt)", "txt");
        //fileChooser.setFileFilter(txtFilter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            globalFile=selectedFile;
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
        ShowChooserFile sf = new ShowChooserFile();
        sf.show();
    }
}
