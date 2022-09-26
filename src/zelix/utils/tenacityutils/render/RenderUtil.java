/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.GL11
 */
package zelix.utils.tenacityutils.render;

import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import zelix.utils.ManagerUtil.Utils;
import zelix.utils.ManagerUtil.render.GLUtil;

public class RenderUtil
implements Utils {
    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != RenderUtil.mc.displayWidth || framebuffer.framebufferHeight != RenderUtil.mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(RenderUtil.mc.displayWidth, RenderUtil.mc.displayHeight, true);
        }
        return framebuffer;
    }

    public static void drawBorderedRect(float x, float y, float width, float height, float outlineThickness, int rectColor, int outlineColor) {
        RenderUtil.drawRect2(x, y, width, height, rectColor);
        GL11.glEnable((int)2848);
        GLUtil.setup2DRendering(() -> {
            RenderUtil.color(outlineColor);
            GL11.glLineWidth((float)outlineThickness);
            float cornerValue = (float)((double)outlineThickness * 0.19);
            GLUtil.render(1, () -> {
                GL11.glVertex2d((double)x, (double)(y - cornerValue));
                GL11.glVertex2d((double)x, (double)(y + height + cornerValue));
                GL11.glVertex2d((double)(x + width), (double)(y + height + cornerValue));
                GL11.glVertex2d((double)(x + width), (double)(y - cornerValue));
                GL11.glVertex2d((double)x, (double)y);
                GL11.glVertex2d((double)(x + width), (double)y);
                GL11.glVertex2d((double)x, (double)(y + height));
                GL11.glVertex2d((double)(x + width), (double)(y + height));
            });
        });
        GL11.glDisable((int)2848);
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
        RenderUtil.drawGoodCircle(x + radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + radius, y + height - radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + height - radius, radius, color);
        RenderUtil.drawRect2(x + radius, y, width - radius * 2.0f, height, color);
        RenderUtil.drawRect2(x, y + radius, width, height - radius * 2.0f, color);
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int c, boolean dropShadow) {
        int color = c;
        if (dropShadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        RenderUtil.drawGoodCircle(x + radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + radius, y + height - radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + height - radius, radius, color);
        RenderUtil.drawRect2(x + radius, y, width - radius * 2.0f, height, color);
        RenderUtil.drawRect2(x, y + radius, width, height - radius * 2.0f, color);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        RenderUtil.resetColor();
        GLUtil.setup2DRendering(() -> GLUtil.render(7, () -> {
            RenderUtil.color(color);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glVertex2d((double)x, (double)(y + height));
            GL11.glVertex2d((double)(x + width), (double)(y + height));
            GL11.glVertex2d((double)(x + width), (double)y);
        }));
    }

    public static void scale(float x, float y, float scale, Runnable data) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)1.0f);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
        data.run();
        GL11.glPopMatrix();
    }

    public static void scaleStart(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)0.0f);
        GlStateManager.scale((float)scale, (float)scale, (float)1.0f);
        GlStateManager.translate((float)(-x), (float)(-y), (float)0.0f);
    }

    public static void scaleEnd() {
        GlStateManager.popMatrix();
    }

    public static void drawGoodCircle(double x, double y, float radius, int color) {
        RenderUtil.color(color);
        GLUtil.setup2DRendering(() -> {
            GL11.glEnable((int)2832);
            GL11.glHint((int)3153, (int)4354);
            GL11.glPointSize((float)(radius * (float)(2 * RenderUtil.mc.gameSettings.guiScale)));
            GLUtil.render(0, () -> GL11.glVertex2d((double)x, (double)y));
        });
    }

    public static void fakeCircleGlow(float posX, float posY, float radius, Color color, float maxAlpha) {
        RenderUtil.setAlphaLimit(0.0f);
        GL11.glShadeModel((int)7425);
        GLUtil.setup2DRendering(() -> GLUtil.render(6, () -> {
            RenderUtil.color(color.getRGB(), maxAlpha);
            GL11.glVertex2d((double)posX, (double)posY);
            RenderUtil.color(color.getRGB(), 0.0f);
            for (int i = 0; i <= 100; ++i) {
                double angle = (double)i * 0.06283 + 3.1415;
                double x2 = Math.sin(angle) * (double)radius;
                double y2 = Math.cos(angle) * (double)radius;
                GL11.glVertex2d((double)((double)posX + x2), (double)((double)posY + y2));
            }
        }));
        GL11.glShadeModel((int)7424);
        RenderUtil.setAlphaLimit(1.0f);
    }

    public static double animate(double endPoint, double current, double speed) {
        boolean shouldContinueAnimation;
        boolean bl = shouldContinueAnimation = endPoint > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        double factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : -factor);
    }

    public static void drawCircleNotSmooth(double x, double y, double radius, int color) {
        radius /= 2.0;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2884);
        RenderUtil.color(color);
        GL11.glBegin((int)6);
        for (double i = 0.0; i <= 360.0; i += 1.0) {
            double angle = i * 0.01745;
            GL11.glVertex2d((double)(x + radius * Math.cos(angle) + radius), (double)(y + radius * Math.sin(angle) + radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
    }

    public static void scissor(double x, double y, double width, double height, Runnable data) {
        GL11.glEnable((int)3089);
        RenderUtil.scissor(x, y, width, height);
        data.run();
        GL11.glDisable((int)3089);
    }

    public static void scissor(double x, double y, double width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        double scale = sr.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = ((double)sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        GL11.glScissor((int)((int)finalX), (int)((int)(finalY - finalHeight)), (int)((int)finalWidth), (int)((int)finalHeight));
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc((int)516, (float)((float)((double)limit * 0.01)));
    }

    public static void color(int color, float alpha) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.color((float)r, (float)g, (float)b, (float)alpha);
    }

    public static void color(int color) {
        RenderUtil.color(color, (float)(color >> 24 & 0xFF) / 255.0f);
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture((int)3553, (int)texture);
    }

    public static void resetColor() {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}

