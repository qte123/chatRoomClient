package com.tafleo.server.file.big;

import com.tafleo.pojo.User;
import com.tafleo.server.ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ReceiveFile extends ServerImpl implements Runnable {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private User user;

    public ReceiveFile(User user) {
        try {
            this.user=user;
            serverSocket = new ServerSocket(BIG_FILE_PORT);//发送方和接收方的端口必须一致
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
      while (true){
          try{
              socket=serverSocket.accept();
          } catch (IOException e) {
              e.printStackTrace();
          }
          new Thread(new ReceiveRunnable(socket,user)).start();
      }
    }

}