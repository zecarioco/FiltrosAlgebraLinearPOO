package model;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;

public class ExtFilters{
    public static class Filters{
        private static HashMap<String, double[][]> Kernels = new HashMap<>();

        static{
            Kernels.put("GaussianBlur", new double[][]{{0.0947416582101747, 0.1183180127031206, 0.0947416582101747},
                                                           {0.1183180127031206, 0.14776131634681883, 0.1183180127031206},
                                                           {0.0947416582101747, 0.1183180127031206, 0.0947416582101747}
                                                    });

            Kernels.put("EdgeDetection", new double[][]{{-1.0, -1.0, -1.0},
                                                            {-1.0, 8.0, -1.0},
                                                            {-1.0, -1.0, -1.0}
                                                            });

            Kernels.put("Realce", new double[][]{{0.0, -1.0, 0.0},
                                                     {-1.0, 5.3, -1.0},
                                                     {0.0, -1.0, 0.0}
                                                    });
            
            Kernels.put("HorizontalEdgeDetection", new double[][]{{-1.0, -2.0, -1.0},
                                                                        {0.0, 0.0, 0.0},
                                                                        {1.0, 2.0, 1.0}
                                                    });

            Kernels.put("Identity", new double[][]{{0.0, 0.0, 0.0},
                                                        {0.0, 1.0, 0.0},
                                                        {0.0, 0.0, 0.0}
            });


            Kernels.put("VerticalEdgeDetection", new double[][]{{-1.0, 0.0, 1.0},
                                                                    {-2.0, 0.0, 2.0},
                                                                    {-1.0, 0.0, 1.0}
                                                    });
        }

        public static BufferedImage GaussianBlur(BufferedImage GaussImg, int intensidade){
            BufferedImage newImg = GaussImg;
            for(int i = 0; i < intensidade; i++){
                newImg = ImageConvolution.applyConvolution(newImg, Kernels.get("GaussianBlur"));
            }

            return newImg;
        }

        public static BufferedImage Highlight(BufferedImage HighImg){
            return ImageConvolution.applyConvolution(HighImg, Kernels.get("Realce"));
        }

        public static BufferedImage Identity(BufferedImage IdImg){
            return ImageConvolution.applyConvolution(IdImg, Kernels.get("Identity"));
        }

        public static BufferedImage Median(BufferedImage MedImg) {
            MedImg = ImageConvolution.padImage(MedImg, MedImg.getType());
            int type = MedImg.getType();
            BufferedImage output = new BufferedImage(MedImg.getWidth(), MedImg.getHeight(), type);
            int width = MedImg.getWidth();
            int height = MedImg.getHeight();
            
            for(int k = 0; k < 3; k++){
                for (int j = 1; j < height - 1; j++) {
                    for (int i = 1; i < width - 1; i++) {
                        int m = kernelMedian(MedImg, i, j);
                        output.setRGB(i, j, m);
                    }
                }
            }
            
            output = ImageConvolution.cropImage(output, output.getType());
            return output;
        }
        
        private static int kernelMedian(BufferedImage image, int x, int y) {
            ArrayList<Integer> reds = new ArrayList<>();
            ArrayList<Integer> greens = new ArrayList<>();
            ArrayList<Integer> blues = new ArrayList<>();
            ArrayList<Integer> alphas = new ArrayList<>();
            
            for (int ky = -1; ky <= 1; ky++) {
                for (int kx = -1; kx <= 1; kx++) {
                    int pixel = image.getRGB(x + kx, y + ky);
                    alphas.add((pixel >> 24) & 0xFF); // Alpha
                    reds.add((pixel >> 16) & 0xFF);  // Red
                    greens.add((pixel >> 8) & 0xFF); // Green
                    blues.add(pixel & 0xFF);        // Blue
                }
            }
            
            // Find median for each color component
            Collections.sort(alphas);
            Collections.sort(reds);
            Collections.sort(greens);
            Collections.sort(blues);
            
            int medianAlpha = alphas.get(alphas.size() / 2);
            int medianRed = reds.get(reds.size() / 2);
            int medianGreen = greens.get(greens.size() / 2);
            int medianBlue = blues.get(blues.size() / 2);
            
            // Combine the median components into a single integer
            return (medianAlpha << 24) | (medianRed << 16) | (medianGreen << 8) | medianBlue;
        }        

        public static BufferedImage EdgeDetection(BufferedImage EdgImg){
            return ImageConvolution.applyConvolution(EdgImg, Kernels.get("EdgeDetection"));
        }

        public static BufferedImage VerticalEdgeDetection(BufferedImage VerImg){
            return ImageConvolution.applyConvolution(VerImg, Kernels.get("VerticalEdgeDetection"));
        }

        public static BufferedImage HorizontalEdgeDetection(BufferedImage HorImg){
            return ImageConvolution.applyConvolution(HorImg, Kernels.get("HorizontalEdgeDetection"));
        }

        public static BufferedImage Blend(BufferedImage image1, BufferedImage image2, double alpha){
            try {
                if(alpha < 0.0 || alpha > 1.0){
                    throw new InputMismatchException("Alpha deve estar entre 0.0 e 1.0.");
                }
                double beta = 1 - alpha;
                int width = image1.getWidth();
                int height = image1.getHeight();

                BufferedImage resizedImage2 = resizeImage(image2, width, height);

                BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb1 = image1.getRGB(x, y);
                        int rgb2 = resizedImage2.getRGB(x, y);

                        int a1 = (rgb1 >> 24) & 0xff;
                        int r1 = (rgb1 >> 16) & 0xff;
                        int g1 = (rgb1 >> 8) & 0xff;
                        int b1 = rgb1 & 0xff;

                        int a2 = (rgb2 >> 24) & 0xff;
                        int r2 = (rgb2 >> 16) & 0xff;
                        int g2 = (rgb2 >> 8) & 0xff;
                        int b2 = rgb2 & 0xff;

                        int a = (int) (alpha * a1 + beta * a2);
                        int r = (int) (alpha * r1 + beta * r2);
                        int g = (int) (alpha * g1 + beta * g2);
                        int b = (int) (alpha * b1 + beta * b2);

                        int rgb = (a << 24)| (r << 16) | (g << 8) | b;

                        resultImage.setRGB(x, y, rgb);
                    }
                }
                
                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g.dispose();

            return resizedImage;
        }

        public static BufferedImage Grayscale(BufferedImage GryImg) {
            int width = GryImg.getWidth();
            int height = GryImg.getHeight();
            BufferedImage outputImage = new BufferedImage(width, height, GryImg.getType());
    
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = GryImg.getRGB(x, y);
    
                    // Extracting RGB components
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
    
                    // Applying grayscale conversion
                    int gray = (int) (red * 0.2126 + green * 0.7152 + blue * 0.0722);
    
                    // Combining back to a single pixel
                    int newPixel = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                    outputImage.setRGB(x, y, newPixel);
                }
            }
    
            return outputImage;
        }
    }
}