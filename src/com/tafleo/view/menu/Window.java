package com.tafleo.view.menu;

import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;

public class Window extends ViewImpl {
    private JFrame frmA;
    private int num;
    private JLabel lblNewLabel;

    public Window(int num) {
        this.num = num;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void window() {
        frmA = new JFrame();
        frmA.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frmA.setTitle("消息提醒");
        frmA.setResizable(false);
        frmA.setBounds(100, 100, 279, 181);
        frmA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmA.getContentPane().setLayout(null);
        if (num == 0) {
            lblNewLabel = new JLabel("请求被拒绝");
        }
        if (num == 1) {
            lblNewLabel = new JLabel("请求已同意");
        }

        lblNewLabel.setBounds(102, 68, 71, 15);        frmA.getContentPane().add(lblNewLabel);
        frmA.setVisible(true);
    }
}
