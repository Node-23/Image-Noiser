package com.lumberjack.PDI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageChanger {

    public static BufferedImage convertToGrayscale(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int gray = (r + g + b) / 3;
                int grayRgb = (gray << 16) | (gray << 8) | gray;
                grayscaleImage.setRGB(x, y, grayRgb);
            }
        }
        return grayscaleImage;
    }

    public static BufferedImage addPepperAndSaltNoise(BufferedImage image, double noiseLevel) {
        Random random = new Random();
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int numNoisePixels = (int) (noiseLevel * width * height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noisyImage.setRGB(x, y, image.getRGB(x, y));
            }
        }

        for (int i = 0; i < numNoisePixels; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int value = random.nextBoolean() ? 0 : 255;
            noisyImage.setRGB(x, y, (value << 16) | (value << 8) | value);
        }

        return noisyImage;
    }

    public static BufferedImage addUniformNoise(BufferedImage image, int noiseIntensity) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        noisyImage.getGraphics().drawImage(image, 0, 0, null);

        Random random = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalPixel = image.getRGB(x, y);
                int originalIntensity = new Color(originalPixel).getRed();

                int noisyIntensity = addUniformeNoiseComponent(originalIntensity, noiseIntensity, random);

                noisyIntensity = Math.min(Math.max(noisyIntensity, 0), 255);

                Color noisyPixelColor = new Color(noisyIntensity, noisyIntensity, noisyIntensity);
                noisyImage.setRGB(x, y, noisyPixelColor.getRGB());
            }
        }

        return noisyImage;
    }

    private static int addUniformeNoiseComponent(int colorValue, int noiseIntensity, Random random) {
        int noise = random.nextInt(2 * noiseIntensity + 1) - noiseIntensity;
        return colorValue + noise;
    }

    public static BufferedImage addGaussianNoise(BufferedImage image, double noiseIntensity) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        noisyImage.getGraphics().drawImage(image, 0, 0, null);

        Random random = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalPixel = image.getRGB(x, y);
                int originalIntensity = new Color(originalPixel).getRed();

                int noisyIntensity = addGaussianNoiseComponent(originalIntensity, noiseIntensity, random);

                noisyIntensity = Math.min(Math.max(noisyIntensity, 0), 255);

                Color noisyPixelColor = new Color(noisyIntensity, noisyIntensity, noisyIntensity);
                noisyImage.setRGB(x, y, noisyPixelColor.getRGB());
            }
        }

        return noisyImage;
    }

    private static int addGaussianNoiseComponent(int intensityValue, double noiseIntensity, Random random) {
        double noise = random.nextGaussian() * noiseIntensity;
        int noisyIntensityValue = (int) (intensityValue + noise);

        return Math.min(Math.max(noisyIntensityValue, 0), 255);
    }
}
