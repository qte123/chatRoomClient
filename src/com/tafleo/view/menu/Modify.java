package com.tafleo.view.menu;

import com.tafleo.client.action.ActionClient;
import com.tafleo.pojo.User;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modify extends ViewImpl {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private User user;
    private String chatNumber;
    private String oldPassword;
    private String newPassword;

    public Modify() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    user=new User();
                    modify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void modify() {
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setTitle("修改密码");
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("TT号：");
        lblNewLabel.setBounds(129, 69, 54, 15);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("旧密码：");
        lblNewLabel_1.setBounds(129, 105, 54, 15);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("新密码：");
        lblNewLabel_2.setBounds(129, 142, 54, 15);
        frame.getContentPane().add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(193, 66, 124, 21);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(193, 102, 124, 21);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(193, 139, 124, 21);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JButton btnNewButton = new JButton("确认修改");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                chatNumber = textField.getText();
                oldPassword = textField_1.getText();
                newPassword = textField_2.getText();
                if (!chatNumber.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty()) {
                    new Thread(new ActionClient("yesModify",chatNumber+"^"+oldPassword+"%"+newPassword)).start();
                }
            }
        });
        btnNewButton.setBounds(129, 186, 188, 23);
        frame.getContentPane().add(btnNewButton);
        frame.setVisible(true);
    }
}
