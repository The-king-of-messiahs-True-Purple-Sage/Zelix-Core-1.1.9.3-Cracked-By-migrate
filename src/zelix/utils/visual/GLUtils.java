/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL15
 */
package zelix.utils.visual;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class GLUtils {
    private static final Random random = new Random();
    private static final Tessellator tessellator = Tessellator.getInstance();
    public static List<Integer> vbos = new ArrayList<Integer>();
    public static List<Integer> textures = new ArrayList<Integer>();

    public static void glScissor(int[] rect) {
        GLUtils.glScissor(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
    }

    public static void glScissor(float x, float y, float x1, float y1) {
        int scaleFactor = GLUtils.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)scaleFactor)), (int)((int)((float)Minecraft.getMinecraft().displayHeight - y1 * (float)scaleFactor)), (int)((int)((x1 - x) * (float)scaleFactor)), (int)((int)((y1 - y) * (float)scaleFactor)));
    }

    public static int getScaleFactor() {
        int scaleFactor = 1;
        boolean isUnicode = Minecraft.getMinecraft().isUnicode();
        int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        while (scaleFactor < guiScale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        return scaleFactor;
    }

    public static int getMouseX() {
        return Mouse.getX() * GLUtils.getScreenWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static int getMouseY() {
        return GLUtils.getScreenHeight() - Mouse.getY() * GLUtils.getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1;
    }

    public static int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth / GLUtils.getScaleFactor();
    }

    public static int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight / GLUtils.getScaleFactor();
    }

    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }

    public static int genVBO() {
        int id = GL15.glGenBuffers();
        vbos.add(id);
        GL15.glBindBuffer((int)34962, (int)id);
        return id;
    }

    public static int getTexture() {
        int textureID = GL11.glGenTextures();
        textures.add(textureID);
        return textureID;
    }

    public static int applyTexture(int texId, File file, int filter, int wrap) throws IOException {
        GLUtils.applyTexture(texId, ImageIO.read(file), filter, wrap);
        return texId;
    }

    public static int applyTexture(int texId, BufferedImage image, int filter, int wrap) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer((int)(image.getWidth() * image.getHeight() * 4));
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
            }
        }
        buffer.flip();
        GLUtils.applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
        return texId;
    }

    public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {
        GL11.glBindTexture((int)3553, (int)texId);
        GL11.glTexParameteri((int)3553, (int)10241, (int)filter);
        GL11.glTexParameteri((int)3553, (int)10240, (int)filter);
        GL11.glTexParameteri((int)3553, (int)10242, (int)wrap);
        GL11.glTexParameteri((int)3553, (int)10243, (int)wrap);
        GL11.glPixelStorei((int)3317, (int)1);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)width, (int)height, (int)0, (int)6408, (int)5121, (ByteBuffer)pixels);
        GL11.glBindTexture((int)3553, (int)0);
        return texId;
    }

    public static void cleanup() {
        GL15.glBindBuffer((int)34962, (int)0);
        GL11.glBindTexture((int)3553, (int)0);
        for (int vbo : vbos) {
            GL15.glDeleteBuffers((int)vbo);
        }
        for (int texture : textures) {
            GL11.glDeleteTextures((int)texture);
        }
    }

    public static void drawBorderRect(float x, float y, float x1, float y1, float borderSize) {
        GLUtils.drawBorder(borderSize, x, y, x1, y1);
        GLUtils.drawRect(x, y, x1, y1);
    }

    public static void drawBorder(float size, float x, float y, float x1, float y1) {
        GL11.glLineWidth((float)size);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
        BufferBuilder vertexBuffer = tessellator.func_178180_c();
        vertexBuffer.func_181668_a(2, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)x, (double)y1, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)x1, (double)y1, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)x1, (double)y, 0.0).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    public static void drawRect(float x, float y, float w, float h) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
        BufferBuilder vertexBuffer = tessellator.func_178180_c();
        vertexBuffer.func_181668_a(7, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b((double)x, (double)h, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)w, (double)h, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)w, (double)y, 0.0).func_181675_d();
        vertexBuffer.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRect(int x, int y, int w, int h, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        vertexbuffer.func_181668_a(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.func_181662_b((double)x + (double)w, (double)y, 0.0).func_181666_a(f1, f2, f3, f).func_181675_d();
        vertexbuffer.func_181662_b((double)x, (double)y, 0.0).func_181666_a(f1, f2, f3, f).func_181675_d();
        vertexbuffer.func_181662_b((double)x, (double)y + (double)h, 0.0).func_181666_a(f5, f6, f7, f4).func_181675_d();
        vertexbuffer.func_181662_b((double)x + (double)w, (double)y + (double)h, 0.0).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void glColor(float red, float green, float blue, float alpha) {
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void glColor(Color color) {
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void glColor(int color) {
        GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)(color >> 24 & 0xFF) / 255.0f));
    }

    public static Color getHSBColor(float hue, float sturation, float luminance) {
        return Color.getHSBColor(hue, sturation, luminance);
    }

    public static Color getRandomColor(int saturationRandom, float luminance) {
        float hue = random.nextFloat();
        float saturation = ((float)random.nextInt(saturationRandom) + (float)saturationRandom) / (float)saturationRandom + (float)saturationRandom;
        return GLUtils.getHSBColor(hue, saturation, luminance);
    }

    public static Color getRandomColor() {
        return GLUtils.getRandomColor(1000, 0.6f);
    }
}

