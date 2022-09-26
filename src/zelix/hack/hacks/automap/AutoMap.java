/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.automap;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import zelix.hack.Hack;
import zelix.hack.HackCategory;

public class AutoMap
extends Hack {
    public AutoMap() {
        super("AutoMap", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public static void main(String[] args) {
        AutoMap.getImagePixel("C:\\apple.png");
    }

    public static void getImagePixel(String image) {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        for (int i = minx; i < width; ++i) {
            for (int j = miny; j < height; ++j) {
                int pixel = bi.getRGB(i, j);
                rgb[0] = (pixel & 0xFF0000) >> 16;
                rgb[1] = (pixel & 0xFF00) >> 8;
                rgb[2] = pixel & 0xFF;
            }
        }
    }

    public static void getBlockID(int r, int g, int b) {
    }
}

