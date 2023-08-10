package com.tafleo.server.file.samll;

import com.tafleo.server.ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//服务器
public class FileServer extends ServerImpl implements Runnable {

    public FileServer() {
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket;
            ServerSocket serverSocket1;
            Socket socket;
            Socket socket1;
            //1.我有一个地址
            serverSocket = new ServerSocket(SERVER_PORT);
            serverSocket1 = new ServerSocket(FILE_FROM_PORT);
            //2.等待客户端连接过来
            while (true) {
                //3.多线程生成独立的会话
                socket = serverSocket.accept();
                socket1 = serverSocket1.accept();
                new Thread(new FileServerRunnable(socket, socket1)).start();
                System.out.println(Thread.currentThread().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           /* //关闭资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }

    }
}