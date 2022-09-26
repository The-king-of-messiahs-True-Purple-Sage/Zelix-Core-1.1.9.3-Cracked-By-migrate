/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.IImageBuffer
 */
package zelix.hack.hacks.skinchanger.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.IImageBuffer;

public class CapeBuffer
implements IImageBuffer {
    public BufferedImage parseUserSkin(BufferedImage img) {
        int imageHeight;
        int imageWidth = 64;
        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        for (imageHeight = 32; imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {
        }
        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return imgNew;
    }

    public void skinAvailable() {
    }
}

