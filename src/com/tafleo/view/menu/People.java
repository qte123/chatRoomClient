package com.tafleo.view.menu;

import com.tafleo.client.file.big.SendFile;
import com.tafleo.client.message.MessageClient;
import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.Message;
import com.tafleo.pojo.User;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class People extends ViewImpl {
    private JFrame frame;
    private JPanel panel;
    private User user;
    private JEditorPane editorPane;
    private JEditorPane editorPane_1;
    private JScrollPane scrollPane;
    private List<User> userList;
    private String last;

    public People(User user, List<User> userList) {
        this.user = user;
        this.userList = userList;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    people();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void people() throws IOException {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setTitle("TT聊天室（群聊室）");
        frame.setBounds(100, 100, 727, 527);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 26, 588, 317);
        frame.getContentPane().add(scrollPane);

        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        scrollPane.setViewportView(editorPane);

        JButton btnNewButton = new JButton("发送表情");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new ChatGif(editorPane_1);
            }
        });
        btnNewButton.setBounds(10, 353, 93, 23);
        frame.getContentPane().add(btnNewButton);
        File file = new File("res//peopleMessage");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file + "//" + user.getChatNumber());
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = new File("res//peopleMessage//" + user.getChatNumber() + ".txt");
        if (!file2.exists()) {
            OutputStream os = new FileOutputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
            os.write("<html><body></body></html>".getBytes());
            os.close();
        }
        InputStream is = new FileInputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
        int len;
        last = "";
        byte[] bytes = new byte[Max];
        while ((len = is.read(bytes)) != -1) {
            last = new String(bytes, 0, len);
        }
        String lastText123 = getHtmlText(last);
        JButton btnNewButton_1 = new JButton("发送文件");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                message.setFromChatNumber(user.getChatNumber());
                message.setToChatNumber("111111");
                message.setDate(new Date());
                message.setIfDelete(0);
                message.setType(1);
                message.setAddDate(new Date());
                for (int i = 0; i < userList.size(); i++) {
                    User u = userList.get(i);
                    new Thread(new SendFile(message, u, false)).start();
                }
            }
        });
        btnNewButton_1.setBounds(113, 353, 93, 23);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_3 = new JButton("发送");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String htmlText = editorPane_1.getText();
                String htmlText1 = getHtmlText(htmlText);
                editorPane_1.setText("");
                if (htmlText1 != null && !htmlText1.isEmpty()) {
                    String lastHtml = editorPane.getText();
                    String lastText = getHtmlText(lastHtml);
                    String time = time();
                    String img = "<img src=\"file:" + user.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + user.getUsername() + "：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    String text1 = lastText + "<center>" + time + "</center>" + img + htmlText1;
                    String html = getHtml(text1);
                    editorPane.setText(html);
                    try {
                        OutputStream peopleMessage = new FileOutputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
                        String htext = getHtmlText(html);
                        String and = getHtml(lastText123 + htext);
                        peopleMessage.write(and.getBytes());
                        peopleMessage.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Message message = new Message();
                    message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                    message.setMessage(htmlText1);
                    message.setFromChatNumber(user.getChatNumber());
                    message.setToChatNumber("111111");
                    message.setDate(new Date());
                    message.setIfDelete(0);
                    message.setType(0);
                    message.setAddDate(new Date());
                    String jsonMessage = JSONUtil.entityToJSON(message);
                    new Thread(new MessageClient("255.255.255.255", jsonMessage)).start();
                }
            }
        });
        btnNewButton_3.setBounds(608, 391, 93, 51);
        frame.getContentPane().add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("关闭");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        btnNewButton_4.setBounds(608, 455, 93, 23);
        frame.getContentPane().add(btnNewButton_4);

        panel = new JPanel();
        panel.setBounds(608, 26, 93, 319);
        panel.setBorder(UIManager.getBorder("TitledBorder.border"));
        frame.getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 1));


        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 386, 588, 92);
        frame.getContentPane().add(scrollPane_1);
        editorPane_1 = new JEditorPane();
        editorPane_1.setContentType("text/html");
        editorPane_1.setText("<html><body></body></html>");
        editorPane_1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER && !e.isControlDown()) {
                    KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
                    editorPane_1.getInputMap().put(enter, "none");
                    String htmlText = editorPane_1.getText();
                    String htmlText1 = getHtmlText(htmlText);
                    editorPane_1.setText("");
                    if (htmlText1 != null && !htmlText1.isEmpty()) {
                        String lastHtml = editorPane.getText();
                        String lastText = getHtmlText(lastHtml);
                        String time = time();
                        String img = "<img src=\"file:" + user.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + user.getUsername() + "：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                        String text1 = lastText + "<center>" + time + "</center>" + img + htmlText1;
                        String html = getHtml(text1);
                        editorPane.setText(html);
                        try {
                            OutputStream peopleMessage = new FileOutputStream("res//peopleMessage//" + user.getChatNumber() + ".txt");
                            String htext = getHtmlText(html);
                            String and = getHtml(lastText123 + htext);
                            peopleMessage.write(and.getBytes());
                            peopleMessage.close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Message message = new Message();
                        message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                        message.setMessage(htmlText1);
                        message.setFromChatNumber(user.getChatNumber());
                        message.setToChatNumber("111111");
                        message.setDate(new Date());
                        message.setIfDelete(0);
                        message.setType(0);
                        message.setAddDate(new Date());
                        String jsonMessage = JSONUtil.entityToJSON(message);
                        new Thread(new MessageClient("255.255.255.255", jsonMessage)).start();
                    }
                }
                if (e.isControlDown() && e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String htmlText2 = getHtmlText(editorPane_1.getText());
                    htmlText2 = htmlText2 + "<br>";
                    String html = getHtml(htmlText2);
                    editorPane_1.setText(html);
                }
            }
        });
        scrollPane_1.setViewportView(editorPane_1);
        JLabel lblNewLabel_3 = new JLabel("接收文件存放位置：res/file");
        lblNewLabel_3.setBounds(216, 357, 178, 15);
        frame.getContentPane().add(lblNewLabel_3);
        JButton btnNewButton_2 = new JButton("近期记录");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new History(user);
            }
        });
        btnNewButton_2.setBounds(477, 353, 93, 23);
        frame.getContentPane().add(btnNewButton_2);
        editorPane.setEditable(false);
        //frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public void setEditorPane(JEditorPane editorPane) {
        this.editorPane = editorPane;
    }

    public JEditorPane getEditorPane_1() {
        return editorPane_1;
    }

    public void setEditorPane_1(JEditorPane editorPane_1) {
        this.editorPane_1 = editorPane_1;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
}
