package com.tafleo.server.file.samll;

import com.tafleo.common.Common;
import com.tafleo.server.ServerImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileServerRunnable extends ServerImpl implements Runnable {
    private Socket socket;//插座
    private Socket socket1;//插座
    private InputStream is;//输入流
    private InputStream is1;//输入流
    private String IM;//IP和文件名字符串
    private String fromIP;//服务器接收客户端IP
    private String fileName;//文件名
    private OutputStream os;//输出流
    private boolean flag;
    private String morePath;


    public FileServerRunnable(Socket socket, Socket socket1) {
        this.socket = socket;
        this.socket1 = socket1;
    }

    @Override
    public void acceptFile() {
        //3.读取客户端的消息
        try {
            is = socket.getInputStream();
            is1 = socket1.getInputStream();
            byte[] buffer = new byte[Max];
            byte[] buffer1 = new byte[Max];
            int len, len1;

            //接收信息字符串
            while ((len = is.read(buffer)) != -1) {
                IM = new String(buffer, 0, len);
                morePath = IM.substring(0, IM.indexOf("^"));
                flag = Boolean.parseBoolean(IM.substring(IM.indexOf("^") + 1, IM.indexOf("*")));
                fromIP = IM.substring(IM.indexOf("*") + 1, IM.indexOf("#"));
                fileName = IM.substring(IM.indexOf("#") + 1);
                time();
            }
            File file1 = new File("res//userList");
            File file = new File("res//messageList//" + morePath);
            //判断文件夹是否存在
            if (!file1.exists()) {
                file1.mkdirs();
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            if (flag) {
                os = new FileOutputStream("res//userList//" + fileName);
            } else {
                os = new FileOutputStream("res//messageList//" + morePath + "//" + fileName);
            }
            while ((len1 = is1.read(buffer1)) != -1) {
                //输出文件
                os.write(buffer1, 0, len1);
                os.flush();
            }
            System.out.println("文件接收成功！");
            os.close();
            is1.close();
            is.close();
            //接收文件二进制
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        acceptFile();
    }
}