package com.tafleo.view.menu;

import com.tafleo.view.ViewImpl;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatGif extends ViewImpl {

    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JEditorPane editorPane_1;

    public ChatGif(JEditorPane editorPane_1) {
        this.editorPane_1 = editorPane_1;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    chatGif();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void iconPrint(int num) {
        if (num <= 147) {
            ImageIcon oldIcon = new ImageIcon(GIF + num + ".gif");
            Image img = oldIcon.getImage();
            Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(newImg);
            JLabel jb = new JLabel(icon);
            panel.add(jb);
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    String html = editorPane_1.getText();
                    String text = getHtmlText(html);
                    String img = "<img src=\"file:" + GIF + num + ".gif\" width=\"30\" height=\"30\">";
                    String message = text + img;
                    editorPane_1.setText(getHtml(message));
                    frame.dispose();
                }
            });
        }

    }

    public void chatGif() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(200, 100, 391, 163);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE + "icon.jpg"));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 1));

        scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new GridLayout(0, 15));
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                sum++;
                iconPrint(sum);
            }
        }
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
