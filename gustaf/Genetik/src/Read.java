import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Read {
    static boolean[][] array;

    public Read(String file, int pixels) {
        array = new boolean[pixels][pixels];
        try {
            toBlack(file);
            pixelate(file, pixels, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void pixelate(String file, int numxpixels, int numypixels) throws IOException {
        BufferedImage image = ImageIO.read(new File(file));
        int xpixels = image.getWidth() / numxpixels;
        int ypixels = image.getHeight() / numypixels;
        for (int i = 0; i < numxpixels; i++) {
            for (int j = 0; j < numypixels; j++) {
                int black = 0;
                for (int k = i*xpixels; k < i*xpixels + xpixels; k++) {
                    for (int l = j*ypixels; l < j*ypixels + ypixels; l++) {
                        if(image.getRGB(k,l) == -1) {
                            black++;
                        } else {
                            black--;
                        }
                    }
                }
                int rgb;
                if(black > 0) {
                    rgb = -1;
                } else {
                    rgb = -16777216;
                    array[i][j] = true;
                }
                for (int k = i*xpixels; k < i*xpixels + xpixels; k++) {
                    for (int l = j*ypixels; l < j*ypixels+ypixels; l++) {
                        image.setRGB(k,l,rgb);
                    }
                }
            }
        }
        ImageIO.write(image, "jpg", new File((numxpixels + file)));
    }

    public static void toBlack(String file) throws IOException {
        BufferedImage coloredImage = ImageIO.read(new File(file));
        BufferedImage blackNWhite = new BufferedImage(coloredImage.getWidth(),coloredImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphics = blackNWhite.createGraphics();
        graphics.drawImage(coloredImage, 0, 0, null);

        ImageIO.write(blackNWhite, "jpg", new File(file));
    }
}
