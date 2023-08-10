package com.tafleo.view.menu;

import com.tafleo.pojo.User;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Head extends ViewImpl {

    private JPanel panel;
    private String filePath;
    private JFrame frame;
    private User user;
    private JLabel lblNewLabel;

    public Head(User user, JLabel lblNewLabel) {
        this.user = user;
        this.lblNewLabel = lblNewLabel;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    head();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void iconPrint(int num) {
        if (num <= 97) {
            ImageIcon oldIcon = new ImageIcon(HEAD_PORTRAIT + "Qq (" + num + ").jpg");
            Image img = oldIcon.getImage();
            Image newImg = img.getScaledInstance(60, 60, java.awt.Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(newImg);
            JLabel jb = new JLabel(icon);
            panel.add(jb);
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    filePath = HEAD_PORTRAIT + "Qq (" + num + ").jpg";
                    user.setHeadPortraitURL(filePath);
                    lblNewLabel.setIcon(pic(filePath, 80, 80));
                    frame.dispose();
                }
            });
        }
    }

    private void head() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 443, 437);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new GridLayout(0, 7));
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 14; j++) {
                sum++;
                iconPrint(sum);
            }
        }
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}