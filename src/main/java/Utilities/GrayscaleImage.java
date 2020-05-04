package Utilities;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class GrayscaleImage {
    public static BufferedImage getGrayscaleImage(BufferedImage image) {
        try {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);

            image = op.filter(image, null);
            return image;
        } catch (Exception e) {}
        return null;
    }
}
