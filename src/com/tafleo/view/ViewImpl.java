package com.tafleo.view;

import com.tafleo.common.ViewBase;
import com.tafleo.view.View;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

public class ViewImpl extends ViewBase implements View {

    @Override
    public ImageIcon pic(String file, int width, int height) {
        ImageIcon oldIcon = new ImageIcon(file);
        Image img = oldIcon.getImage();
        Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(newImg);
        return icon;
    }

    @Override
    public String getGifFile(int num) {
        return "res//gif//"+num+".gif";
    }

    @Override
    public void listPrint() {

    }
}
