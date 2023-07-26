package com.lumberjack.Services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SystemServices {
    public static BufferedImage SelectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                return ImageIO.read(selectedFile);
            }catch (IOException e){
                JOptionPane.showMessageDialog(null, "Error reading the image file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public static void SaveImage(BufferedImage image){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select where to save Image");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            String outputFileName = fileToSave.getAbsolutePath();
            if (!outputFileName.toLowerCase().endsWith(".png")) {
                outputFileName += ".png";
            }

            try {
                File outputFile = new File(outputFileName);
                ImageIO.write(image, "png", outputFile);
                JOptionPane.showMessageDialog(null, "Image has been saved successfully!", "Image Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.err.println("Error saving the image: " + e.getMessage());
            }
        } else {
            System.out.println("Save command canceled by the user.");
        }
    }
}
