package com.tafleo.run;

import com.tafleo.common.ClientBase;
import com.tafleo.view.menu.Login;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class Run extends ClientBase {
    public Run() {
        new Login();
    }

    static {
        try {
            File file = new File("res//address");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file1 = new File("res//address//ServerIP.txt");
            if (!file1.exists()) {
                OutputStream os = new FileOutputStream(file1);
                os.write("192.168.3.6".getBytes());
                ServerIP = "192.168.3.6";
            } else {
                InputStream is = new FileInputStream(file1);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    ServerIP = new String(bytes, 0, len);
                }
                if ("".equals(ServerIP)) ServerIP = "192.168.3.6";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Run();
    }
}
