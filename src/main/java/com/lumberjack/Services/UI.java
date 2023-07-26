package com.lumberjack.Services;

import com.lumberjack.PDI.ImageChanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class UI extends JFrame{
    public void DisplayImageExample(BufferedImage image, Frame selectNoiseFrame) {
        setTitle("Image Viewer");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(image.getWidth(), image.getHeight());

        var imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));

        add(imageLabel);
        pack();

        var displayerFrame = this;

        displayerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int selectedOption = JOptionPane.showConfirmDialog(
                        displayerFrame,
                        "Do you want to save the image?",
                        "Save Image",
                        JOptionPane.YES_NO_OPTION
                );

                if (selectedOption == JOptionPane.YES_OPTION) {
                    SystemServices.SaveImage(image);
                }
                displayerFrame.dispose();
                selectNoiseFrame.setVisible(true);
            }
        });

        setVisible(true);
    }

    public void SelectNoise(BufferedImage image) {
        setTitle("Three Buttons Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);

        JButton saltAndPepper = new JButton("Salt and Pepper");
        JButton uniform = new JButton("Uniform");
        JButton gaussian = new JButton("Gaussian");
        var noiseFrame = this;


        JSlider noiseSlider = new JSlider(0, 100, 50);
        noiseSlider.setMajorTickSpacing(10);
        noiseSlider.setPaintTicks(true);
        noiseSlider.setPaintLabels(true);

        saltAndPepper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var ui = new UI();
                noiseFrame.setVisible(false);
                ui.DisplayImageExample(ImageChanger.addPepperAndSaltNoise(image, noiseSlider.getValue() * 0.01), noiseFrame);
            }
        });

        uniform.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var ui = new UI();
                noiseFrame.setVisible(false);
                ui.DisplayImageExample(ImageChanger.addUniformNoise(image, noiseSlider.getValue()), noiseFrame);
            }
        });

        gaussian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var ui = new UI();
                noiseFrame.setVisible(false);
                ui.DisplayImageExample(ImageChanger.addGaussianNoise(image, noiseSlider.getValue()), noiseFrame);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(5, 10, 5, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(saltAndPepper, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(uniform, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        add(gaussian, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        add(noiseSlider, constraints);

        pack();

        setVisible(true);
    }
}
