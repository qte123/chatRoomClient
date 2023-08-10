package com.tafleo.view.menu;

import com.tafleo.view.ViewImpl;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Toolkit;

public class Successful extends ViewImpl {

    private JFrame frame;

    public Successful() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    successful();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void successful() {
        frame = new JFrame();
        frame.setTitle("成功");
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 305, 194);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("密码修改成功");
        lblNewLabel.setBounds(109, 75, 84, 15);
        frame.getContentPane().add(lblNewLabel);
        frame.setVisible(true);
    }

}
