package com.tafleo.server.file.big;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ReceiveRunnable extends ServerImpl implements Runnable {
    private Socket socket;
    private JFrame frame;
    private Container contentPanel;
    private JProgressBar progressbar;
    private DataInputStream dis;
    private DataOutputStream dos;
    private RandomAccessFile rad;
    private JLabel label;

    public ReceiveRunnable(Socket socket, User user) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            frame = new JFrame("接收文件");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dis.readUTF();
            int permit = JOptionPane.showConfirmDialog(frame, "是否接收文件", "文件传输请求：", JOptionPane.YES_NO_OPTION);
            if (permit == JOptionPane.YES_OPTION) {
                String filename = "res//file//" + dis.readUTF();
                dos.writeUTF("ok");
                dos.flush();
                File file = new File(filename + ".temp");

                rad = new RandomAccessFile(filename + ".temp", "rw");

//获得文件大小
                long size = 0;
                if (file.exists() && file.isFile()) {
                    size = file.length();
                }

                dos.writeLong(size);//发送已接收的大小
                dos.flush();
                long allSize = dis.readLong();
                String rsp = dis.readUTF();

                int barSize = (int) (allSize / 1024);
                int barOffset = (int) (size / 1024);

//传输界面
                frame.setSize(300, 120);
                contentPanel = frame.getContentPane();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                progressbar = new JProgressBar();//进度条

                label = new JLabel(filename + " 接收中");
                contentPanel.add(label);

                progressbar.setOrientation(JProgressBar.HORIZONTAL);
                progressbar.setMinimum(0);
                progressbar.setMaximum(barSize);
                progressbar.setValue(barOffset);
                progressbar.setStringPainted(true);
                progressbar.setPreferredSize(new Dimension(150, 20));
                progressbar.setBorderPainted(true);
                progressbar.setBackground(Color.pink);

                JButton cancel = new JButton("取消");

                JPanel barPanel = new JPanel();
                barPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                barPanel.add(progressbar);
                barPanel.add(cancel);

                contentPanel.add(barPanel);

                cancel.addActionListener(new ReceiveRunnable.CancelActionListener());

                frame.setDefaultCloseOperation(
                        JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

//接收文件
                if (rsp.equals("ok")) {
                    rad.seek(size);
                    int length;
                    byte[] buf = new byte[1024];
                    while ((length = dis.read(buf, 0, buf.length)) != -1) {
                        rad.write(buf, 0, length);
                        progressbar.setValue(++barOffset);
                    }
                    System.out.println("FileReceive end...");
                }

                label.setText(filename + " 结束接收");


                dis.close();
                dos.close();
                rad.close();
                frame.dispose();
//文件重命名
                if (barOffset >= barSize) {
                    file.renameTo(new File(filename));
                }
            } else {
                dis.close();
                dos.close();
                frame.dispose();
            }

        } catch (IOException e) {
// TODO Auto-generated catch block
            label.setText(" 已取消接收，连接关闭！");
        } finally {
            frame.dispose();
        }
    }

    class CancelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                dis.close();
                dos.close();
                rad.close();
                JOptionPane.showMessageDialog(frame, "已取消接收，连接关闭！", "提示：", JOptionPane.INFORMATION_MESSAGE);
                label.setText(" 取消接收,连接关闭");
            } catch (IOException e1) {

            }
        }
    }

}
