package com.tafleo.view.menu;

import com.tafleo.view.ViewImpl;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialog extends ViewImpl {

    private JFrame frame;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JButton btnNewButton;
    private String chatNumber;
    private JFrame frmTt;

    public Dialog(String chatNumber, JFrame frmTt) {
        this.chatNumber = chatNumber;
        this.frmTt = frmTt;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dialog() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("账号注册");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 414, 272);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblNewLabel = new JLabel("恭喜你，账号注册成功！");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(116, 52, 182, 19);
        frame.getContentPane().add(lblNewLabel);
        lblNewLabel_1 = new JLabel(chatNumber);

        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(170, 134, 75, 24);
        frame.getContentPane().add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("你的账号");
        lblNewLabel_2.setBounds(175, 109, 55, 15);
        frame.getContentPane().add(lblNewLabel_2);

        btnNewButton = new JButton("前去登录");
        btnNewButton.setBounds(155, 190, 93, 23);
        frame.getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                if (chatNumber != null && !chatNumber.isEmpty())
                    frmTt.setVisible(true);
            }
        });
        frame.setVisible(true);
    }
}

