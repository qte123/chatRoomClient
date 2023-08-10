package com.tafleo.view;

import javax.swing.*;

public interface View {
    //列表打印
    public void listPrint();
    //图片比例放大缩小
    public ImageIcon pic(String file, int width, int height);
    //gif图片地址搜索
    public String getGifFile(int num);

}
