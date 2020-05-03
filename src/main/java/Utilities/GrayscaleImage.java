package Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import static com.sun.tools.doclint.Entity.and;

public class GrayscaleImage {
    public static BufferedImage getGrayscaleImage(BufferedImage image) {
        try {
//            int width = image.getWidth();
//            int height = image.getHeight();
//            for(int i=0; i<height; i++) {
//                for(int j=0; j<width; j++) {
//                    Color c = new Color(image.getRGB(j, i));
//                    int red = (int)(c.getRed() * 0.299);
//                    int green = (int)(c.getGreen() * 0.587);
//                    int blue = (int)(c.getBlue() *0.114);
//                    Color newColor = new Color(red+green+blue,
//                            red+green+blue,red+green+blue);
//                    image.setRGB(j,i,newColor.getRGB());
//                }
//            }
//            return image;
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);

//            BufferedImage bufferedImage = new BufferedImage(200, 200,
//                    BufferedImage.TYPE_BYTE_INDEXED);
            image = op.filter(image, null);
            return image;
        } catch (Exception e) {}
        return null;
    }

}
