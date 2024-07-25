package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageConvolution {
    private static Color media = Color.WHITE;

    public static BufferedImage applyConvolution(BufferedImage image, double[][] kernel) {
        int type = (image.getType() == BufferedImage.TYPE_INT_ARGB) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage paddedImage = padImage(image, type);
        BufferedImage outputImage = new BufferedImage(paddedImage.getWidth(), paddedImage.getHeight(), type);

        for (int y = 1; y < paddedImage.getHeight() - 1; y++) {
            for (int x = 1; x < paddedImage.getWidth() - 1; x++) {
                double[] rgb = applyKernel(paddedImage, x, y, kernel);
                int pixel;
                if (type == BufferedImage.TYPE_INT_ARGB) {
                    pixel = (toRGB(rgb[3]) << 24) | (toRGB(rgb[0]) << 16) | (toRGB(rgb[1]) << 8) | toRGB(rgb[2]);
                } else {
                    pixel = (toRGB(rgb[0]) << 16) | (toRGB(rgb[1]) << 8) | toRGB(rgb[2]);
                }
                outputImage.setRGB(x, y, pixel);
            }
        }

        outputImage = cropImage(outputImage, type);
        return outputImage;
    }

    // Aplicar o kernel a um pixel específico
    private static double[] applyKernel(BufferedImage image, int x, int y, double[][] kernel) {
        int type = image.getType();
        double[] result = (type == BufferedImage.TYPE_INT_ARGB) ? new double[4] : new double[3];

        for (int ky = -1; ky <= 1; ky++) {
            for (int kx = -1; kx <= 1; kx++) {
                int pixel = image.getRGB(x + kx, y + ky);
                double weight = kernel[ky + 1][kx + 1];
                result[0] += ((pixel >> 16) & 0xFF) * weight; // Red
                result[1] += ((pixel >> 8) & 0xFF) * weight;  // Green
                result[2] += (pixel & 0xFF) * weight;         // Blue
                if (type == BufferedImage.TYPE_INT_ARGB) {
                    result[3] += ((pixel >> 24) & 0xFF) * weight; // Alpha
                }
            }
        }

        return result;
    }

    public static int toRGB(double value) {
        return Math.min(255, Math.max(0, (int) Math.round(value)));
    }

    public static BufferedImage padImage(BufferedImage image, int type) {
        BufferedImage newSize = new BufferedImage(image.getWidth() + 2, image.getHeight() + 2, type);
        Graphics pad = newSize.getGraphics();

        pad.setColor(media);
        pad.fillRect(0, 0, newSize.getWidth(), newSize.getHeight());
        pad.drawImage(image, 1, 1, null);
        pad.dispose();

        return newSize;
    }

    public static BufferedImage cropImage(BufferedImage image, int type) {
        BufferedImage img = image.getSubimage(1, 1, image.getWidth() - 2, image.getHeight() - 2);
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), type);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return copyOfImage;
    }

    public static Color mediaRGB(BufferedImage image) {
        long sumR = 0;
        long sumG = 0;
        long sumB = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = pixel & 0xFF;

                sumR += r;
                sumG += g;
                sumB += b;
            }
        }

        int avgR = (int) (sumR / totalPixels);
        int avgG = (int) (sumG / totalPixels);
        int avgB = (int) (sumB / totalPixels);

        return new Color(avgR, avgG, avgB);
    }
}
