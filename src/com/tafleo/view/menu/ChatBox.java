package com.tafleo.view.menu;

import com.tafleo.client.message.MessageClient;
import com.tafleo.client.sidebar.SidebarClient;
import com.tafleo.pojo.Message;
import com.tafleo.pojo.User;
import com.tafleo.client.file.big.SendFile;
import com.tafleo.util.JSONUtil;
import com.tafleo.view.ViewImpl;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

import java.io.*;
import java.util.Date;
import java.util.UUID;

public class ChatBox extends ViewImpl {

    private JFrame frame;
    private JPanel panel;
    private User me;
    private User user1;
    private JEditorPane editorPane;
    private JEditorPane editorPane_1;
    private JScrollPane scrollPane;

    private JPanel panel1;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private int chatStatus;
    private JLabel lblNewLabel_2;

    private JPanel panel2;
    private JLabel lblNewLabel1;
    private JLabel lblNewLabel_11;
    private int chatStatus1;
    private JLabel lblNewLabel_22;
    private String last;

    public ChatBox(User me, User user1) {
        this.me = me;
        this.user1 = user1;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    chatBox();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void chatBox() throws IOException {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WINDOW_ICON_FILE));
        frame.setTitle("TT聊天室");
        frame.setBounds(100, 100, 727, 527);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 26, 588, 317);
        frame.getContentPane().add(scrollPane);

        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        scrollPane.setViewportView(editorPane);
        File file = new File("res//messageList");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file + "//" + me.getChatNumber());
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2=new File("res//messageList//" + me.getChatNumber() + "//" + user1.getChatNumber() + ".txt");
        if (!file2.exists()){
            OutputStream os = new FileOutputStream("res//messageList//" + me.getChatNumber() + "//" + user1.getChatNumber() + ".txt");
            os.write("<html><body></body></html>".getBytes());
            os.close();
        }
        InputStream is = new FileInputStream("res//messageList//" + me.getChatNumber() + "//" + user1.getChatNumber() + ".txt");
        int len;
        last = "";
        byte[] bytes = new byte[Max];
        while ((len = is.read(bytes)) != -1) {
            last = new String(bytes, 0, len);
        }
        is.close();
        String lastText123 = getHtmlText(last);
        JButton btnNewButton = new JButton("发送表情");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new ChatGif(editorPane_1);
            }
        });
        btnNewButton.setBounds(10, 353, 93, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("发送文件");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                message.setFromChatNumber(me.getChatNumber());
                message.setToChatNumber(user1.getChatNumber());
                message.setDate(new Date());
                message.setIfDelete(0);
                message.setType(1);
                message.setAddDate(new Date());
                new Thread(new SendFile(message, user1, true)).start();
            }
        });
        btnNewButton_1.setBounds(113, 353, 93, 23);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("近期记录");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new History(me, user1);
                String s = JSONUtil.entityToJSON(me);
                String s1 = JSONUtil.entityToJSON(user1);
                new Thread(new SidebarClient("history", s + "%" + s1)).start();
            }
        });
        btnNewButton_2.setBounds(477, 353, 93, 23);
        frame.getContentPane().add(btnNewButton_2);

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
                    String img = "<img src=\"file:" + me.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + me.getUsername() + "：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    String text1 = lastText + "<center>" + time + "</center>" + img + htmlText1;
                    String html = getHtml(text1);
                    editorPane.setText(html);
                    try {
                        OutputStream messageList = new FileOutputStream("res//messageList//" + me.getChatNumber() + "//" + user1.getChatNumber() + ".txt");
                        String htext = getHtmlText(html);
                        String and = getHtml(lastText123 + htext);
                        messageList.write(and.getBytes());
                        messageList.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Message message = new Message();
                    message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                    message.setMessage(htmlText1);
                    message.setFromChatNumber(me.getChatNumber());
                    message.setToChatNumber(user1.getChatNumber());
                    message.setDate(new Date());
                    message.setIfDelete(0);
                    message.setType(0);
                    message.setAddDate(new Date());
                    String jsonMessage = JSONUtil.entityToJSON(message);
                    new Thread(new MessageClient(user1.getRecentIP(), jsonMessage)).start();
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
                        String img = "<img src=\"file:" + me.getHeadPortraitURL() + "\" width=\"30\" height=\"30\">&nbsp;" + me.getUsername() + "：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                        String text1 = lastText + "<center>" + time + "</center>" + img + htmlText1;
                        String html = getHtml(text1);
                        editorPane.setText(html);
                        try {
                            OutputStream messageList = new FileOutputStream("res//messageList//" + me.getChatNumber() + "//" + user1.getChatNumber() + ".txt");
                            String htext = getHtmlText(html);
                            String and = getHtml(lastText123 + htext);
                            messageList.write(and.getBytes());
                            messageList.close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        Message message = new Message();
                        message.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                        message.setMessage(htmlText1);
                        message.setFromChatNumber(me.getChatNumber());
                        message.setToChatNumber(user1.getChatNumber());
                        message.setDate(new Date());
                        message.setIfDelete(0);
                        message.setType(0);
                        message.setAddDate(new Date());
                        String jsonMessage = JSONUtil.entityToJSON(message);
                        new Thread(new MessageClient(user1.getRecentIP(), jsonMessage)).start();
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

        panel1 = new JPanel();
        panel1.setBorder(UIManager.getBorder("TitledBorder.border"));
        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                new Information(true, user1, null, null, null, null);
            }
        });
        panel.add(panel1);
        panel1.setLayout(null);

        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(pic(user1.getHeadPortraitURL(), 48, 48));
        lblNewLabel.setBounds(25, 40, 48, 48);
        panel1.add(lblNewLabel);

        lblNewLabel_1 = new JLabel(user1.getUsername());
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(25, 90, 54, 16);
        panel1.add(lblNewLabel_1);

        chatStatus = user1.getChatStatus();
        String s = chatStatus == 1 ? "在线" : "离线";
        lblNewLabel_2 = new JLabel(s);
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 10));
        lblNewLabel_2.setBounds(35, 110, 54, 15);
        panel1.add(lblNewLabel_2);

        panel2 = new JPanel();
        panel2.setBorder(UIManager.getBorder("TitledBorder.border"));
        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                new Information(true, me, null, null, null, null);
            }
        });
        panel.add(panel2);
        panel2.setLayout(null);

        lblNewLabel1 = new JLabel("");
        lblNewLabel1.setIcon(pic(me.getHeadPortraitURL(), 48, 48));
        lblNewLabel1.setBounds(25, 40, 48, 48);
        panel2.add(lblNewLabel1);

        lblNewLabel_11 = new JLabel(me.getUsername());
        lblNewLabel_11.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel_11.setBounds(25, 90, 54, 16);
        panel2.add(lblNewLabel_11);

        chatStatus1 = me.getChatStatus();
        String s1 = chatStatus1 == 1 ? "在线" : "离线";
        lblNewLabel_22 = new JLabel(s1);
        lblNewLabel_22.setFont(new Font("宋体", Font.PLAIN, 10));
        lblNewLabel_22.setBounds(35, 110, 54, 15);
        panel2.add(lblNewLabel_22);
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

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
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

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JLabel getLblNewLabel() {
        return lblNewLabel;
    }

    public void setLblNewLabel(JLabel lblNewLabel) {
        this.lblNewLabel = lblNewLabel;
    }

    public JLabel getLblNewLabel_1() {
        return lblNewLabel_1;
    }

    public void setLblNewLabel_1(JLabel lblNewLabel_1) {
        this.lblNewLabel_1 = lblNewLabel_1;
    }

    public int getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(int chatStatus) {
        this.chatStatus = chatStatus;
    }

    public JLabel getLblNewLabel_2() {
        return lblNewLabel_2;
    }

    public void setLblNewLabel_2(JLabel lblNewLabel_2) {
        this.lblNewLabel_2 = lblNewLabel_2;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public void setPanel2(JPanel panel2) {
        this.panel2 = panel2;
    }

    public JLabel getLblNewLabel1() {
        return lblNewLabel1;
    }

    public void setLblNewLabel1(JLabel lblNewLabel1) {
        this.lblNewLabel1 = lblNewLabel1;
    }

    public JLabel getLblNewLabel_11() {
        return lblNewLabel_11;
    }

    public void setLblNewLabel_11(JLabel lblNewLabel_11) {
        this.lblNewLabel_11 = lblNewLabel_11;
    }

    public int getChatStatus1() {
        return chatStatus1;
    }

    public void setChatStatus1(int chatStatus1) {
        this.chatStatus1 = chatStatus1;
    }

    public JLabel getLblNewLabel_22() {
        return lblNewLabel_22;
    }

    public void setLblNewLabel_22(JLabel lblNewLabel_22) {
        this.lblNewLabel_22 = lblNewLabel_22;
    }
}
