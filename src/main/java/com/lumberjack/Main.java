package com.lumberjack;

import com.lumberjack.PDI.ImageChanger;
import com.lumberjack.Services.SystemServices;
import com.lumberjack.Services.UI;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        BufferedImage image = SystemServices.SelectImage();
        if(image == null){
            JOptionPane.showMessageDialog(null, "Error reading the image file.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        BufferedImage grayImage = ImageChanger.convertToGrayscale(image);
        UI ui = new UI();
        ui.SelectNoise(grayImage);
    }
}