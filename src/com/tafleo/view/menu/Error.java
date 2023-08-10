package com.tafleo.view.menu;

import com.tafleo.view.ViewImpl;

import java.awt.*;

import javax.swing.*;

public class Error extends ViewImpl {

    private JFrame frame;
    private JLabel lblNewLabel;
    private int number;

    public Error(int number) {
        this.number=number;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    error();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void error() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("错误警告");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 305, 194);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        lblNewLabel = new JLabel();
        if(number==0){
            lblNewLabel.setText("      注册失败");
        }
        if (number==1) {
            lblNewLabel.setText("无法连接远程服务器");
        }
        if (number==2){
            lblNewLabel.setText("文件过大，发送失败");
        }
        if (number==3){
            lblNewLabel.setText("没有查到指定用户");
        }
        if (number==4){
            lblNewLabel.setText("被服务器强制下线");
        }
        if (number==5){
            lblNewLabel.setText("被服务器删除用户");
        }
        if (number==6){
            lblNewLabel.setText("该账号已经登录");
        }
        if (number==7){
            lblNewLabel.setText("密码修改失败");
        }
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(74, 70, 177, 19);
        frame.getContentPane().add(lblNewLabel);
        frame.setVisible(true);
    }

}
