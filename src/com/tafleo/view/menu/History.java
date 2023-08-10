package com.tafleo.view.menu;

import com.tafleo.pojo.User;
import com.tafleo.view.ViewImpl;

import java.awt.EventQueue;

import javax.jws.soap.SOAPBinding;
import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.*;

public class History extends ViewImpl {

    private JFrame frame;
    private JEditorPane editorPane;
    private User me;
    private User user;

    public History(User me) {
        this.me = me;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    history1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public History(User me, User user) {
        this.me = me;
        this.user = user;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    history();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void history1() throws Exception {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("历史记录");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 440, 295);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 414, 201);
        frame.getContentPane().add(scrollPane);


        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        InputStream is = new FileInputStream("res//peopleMessage//" + me.getChatNumber() + ".txt");
        int len;
        byte[] bytes = new byte[Max];
        String s = "";
        while ((len = is.read(bytes)) != -1) {
            s = new String(bytes, 0, len);
        }
        is.close();
        editorPane.setText(s);
        scrollPane.setViewportView(editorPane);

        JLabel lblNewLabel = new JLabel("历史记录");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        lblNewLabel.setBounds(176, 16, 93, 24);
        editorPane.setEditable(false);
        frame.getContentPane().add(lblNewLabel);
        frame.setVisible(true);
    }

    public void history() throws Exception {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("历史记录");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setBounds(100, 100, 440, 295);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 414, 201);
        frame.getContentPane().add(scrollPane);


        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");

        InputStream input = new FileInputStream("res//messageList//" + me.getChatNumber() + "//" + user.getChatNumber() + ".txt");
        int len;
        byte[] bytes = new byte[Max];
        String s1 = "";
        while ((len = input.read(bytes)) != -1) {
            s1 = new String(bytes, 0, len);
        }
        input.close();
        editorPane.setText(s1);
        System.out.println(s1);
        scrollPane.setViewportView(editorPane);

        JLabel lblNewLabel = new JLabel("历史记录");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        lblNewLabel.setBounds(176, 16, 93, 24);
        editorPane.setEditable(false);
        frame.getContentPane().add(lblNewLabel);
        frame.setVisible(true);
    }
}
