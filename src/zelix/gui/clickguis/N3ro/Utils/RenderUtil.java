/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.N3ro.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import zelix.utils.hooks.visual.RenderUtils;

public class RenderUtil {
    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtil.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtil.rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtil.rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtil.rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtil.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawRoundRect_up(double xPosition, double yPosition, double endX, double endY, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition, xPosition + width - (double)radius, yPosition + (double)radius, 0.0f, color, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width, yPosition + height, 0.0f, 0, color);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + (double)radius, radius, color, 1);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + (double)radius, radius, color, 3);
    }

    public static void drawCirCleBorder(double x, double y, double height, double width, int radius, int Color2) {
        RenderUtil.drawRoundRect_up(x, y, x + height, y + width / 2.0, radius, Color2);
        RenderUtil.drawRoundRect_down(x, y + width / 2.0, x + height, y + width, radius, Color2);
    }

    public static void drawRoundRect_down(double xPosition, double yPosition, double endX, double endY, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height - (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition, yPosition, xPosition + width, yPosition + (double)radius, 0.0f, 0, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width - (double)radius, yPosition + height, 0.0f, 0, color);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + height - (double)radius, radius, color, 2);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + height - (double)radius, radius, color, 4);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        R2DUtils.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        RenderUtil.glColor(topColor);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        RenderUtil.glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        R2DUtils.disableGL2D();
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        float var11 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var6 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.func_178180_c();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)var6, (float)var7, (float)var8, (float)var11);
        bufferBuilder.func_181668_a(7, DefaultVertexFormats.POSITION);
        bufferBuilder.func_181662_b(left, bottom, 0.0).func_181675_d();
        bufferBuilder.func_181662_b(right, bottom, 0.0).func_181675_d();
        bufferBuilder.func_181662_b(right, top, 0.0).func_181675_d();
        bufferBuilder.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawBorderedRect(double x, double y, double x2, double d, float l1, int col1, int col2) {
        RenderUtil.drawRect(x, y, x2, d, col2);
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glLineWidth((float)l1);
        GlStateManager.func_187447_r((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)d);
        GL11.glVertex2d((double)x2, (double)d);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)d);
        GL11.glVertex2d((double)x2, (double)d);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
    }

    public static void drawRect(double d, double e, double f, double g, int color) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        RenderUtil.color(color);
        GlStateManager.func_187447_r((int)7);
        GL11.glVertex2d((double)f, (double)e);
        GL11.glVertex2d((double)d, (double)e);
        GL11.glVertex2d((double)d, (double)g);
        GL11.glVertex2d((double)f, (double)g);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
    }

    public static void color(int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GlStateManager.func_187447_r((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
    }

    public static void startGlScissor(int x, int y, int width, int height) {
        int scaleFactor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)(x * scaleFactor), (int)(Minecraft.getMinecraft().displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)((height += 14) * scaleFactor));
    }

    public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, int BorderThincess, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition, xPosition + width - (double)radius, yPosition + (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width - (double)radius, yPosition + height, BorderThincess, color, color);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + (double)radius, radius, color, 1);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + height - (double)radius, radius, color, 2);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + (double)radius, radius, color, 3);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + height - (double)radius, radius, color, 4);
    }

    public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtil.drawRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, color);
        RenderUtil.drawRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height - (double)radius, color);
        RenderUtil.drawRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, color);
        RenderUtil.drawRect(xPosition + (double)radius, yPosition, xPosition + width - (double)radius, yPosition + (double)radius, color);
        RenderUtil.drawRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width - (double)radius, yPosition + height, color);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + (double)radius, radius, color, 1);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + height - (double)radius, radius, color, 2);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + (double)radius, radius, color, 3);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + height - (double)radius, radius, color, 4);
    }

    public static void drawFilledCircle(double x, double y, double r, int c, int id) {
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GlStateManager.func_187447_r((int)9);
        if (id == 1) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 0; i <= 90; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 2) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 90; i <= 180; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 3) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 270; i <= 360; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 4) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 180; i <= 270; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else {
            for (int i = 0; i <= 360; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2f((float)((float)(x - x2)), (float)((float)(y - y2)));
            }
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void stopGlScissor() {
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
    }

    public static void drawRoundRectShadow(double xPosition, double yPosition, double endX, double endY, int BorderThincess, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition, xPosition + width - (double)radius, yPosition + (double)radius, BorderThincess, color, color);
        RenderUtil.drawBorderedRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width - (double)radius, yPosition + height, BorderThincess, color, color);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + (double)radius, radius, color, 1);
        RenderUtil.drawFilledCircle(xPosition + (double)radius, yPosition + height - (double)radius, radius, color, 2);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + (double)radius, radius, color, 3);
        RenderUtil.drawFilledCircle(xPosition + width - (double)radius, yPosition + height - (double)radius, radius, color, 4);
        RenderUtils.drawBorderedRect(xPosition + (double)radius - 20.0, yPosition + (double)radius - 20.0, xPosition + width - (double)radius + 20.0, yPosition + height - (double)radius + 20.0, BorderThincess, 1058872607, 1058872607);
        RenderUtils.drawBorderedRect(xPosition - 20.0, yPosition + (double)radius - 20.0, xPosition + (double)radius + 20.0, yPosition + height - (double)radius + 20.0, BorderThincess, 1058872607, 1058872607);
        RenderUtils.drawBorderedRect(xPosition + width - (double)radius - 20.0, yPosition + (double)radius - 20.0, xPosition + width + 20.0, yPosition + height - (double)radius + 20.0, BorderThincess, 1058872607, 1058872607);
        RenderUtils.drawBorderedRect(xPosition + (double)radius - 20.0, yPosition - 20.0, xPosition + width - (double)radius + 20.0, yPosition + (double)radius + 20.0, BorderThincess, 1058872607, 1058872607);
        RenderUtils.drawBorderedRect(xPosition + (double)radius - 20.0, yPosition + height - (double)radius - 20.0, xPosition + width - (double)radius + 20.0, yPosition + height + 20.0, BorderThincess, 1058872607, 1058872607);
        RenderUtils.drawFilledCircle(xPosition + (double)radius - 20.0, yPosition + (double)radius, radius - 20, 1058872607, 1);
        RenderUtils.drawFilledCircle(xPosition + (double)radius - 20.0, yPosition + height - (double)radius - 20.0, radius, 1058872607, 2);
        RenderUtils.drawFilledCircle(xPosition + width - (double)radius - 20.0, yPosition + (double)radius - 20.0, radius, 1058872607, 3);
        RenderUtils.drawFilledCircle(xPosition + width - (double)radius - 20.0, yPosition + height - (double)radius - 20.0, radius, 1058872607, 4);
    }

    public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GlStateManager.func_187447_r((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
    }

    public static class R2DUtils {
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
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glHint((int)3154, (int)4352);
            GL11.glHint((int)3155, (int)4352);
        }

        public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
            R2DUtils.enableGL2D();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            R2DUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
            R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
            R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
            R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
            R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
            R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
            R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
            R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
            R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
            GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
            R2DUtils.disableGL2D();
            Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
        }

        public static void drawRect(double x2, double y2, double x1, double y1, int color) {
            R2DUtils.enableGL2D();
            R2DUtils.glColor(color, color, color, color);
            R2DUtils.drawRect(x2, y2, x1, y1);
            R2DUtils.disableGL2D();
        }

        private static void drawRect(double x2, double y2, double x1, double y1) {
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)x2, (double)y1);
            GL11.glVertex2d((double)x1, (double)y1);
            GL11.glVertex2d((double)x1, (double)y2);
            GL11.glVertex2d((double)x2, (double)y2);
            GL11.glEnd();
        }

        public static void drawHLine(float x, float y, float x1, int y1) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
        }

        public static void drawVLine(float x, float y, float x1, int y1) {
            if (x1 < y) {
                float var5 = y;
                y = x1;
                x1 = var5;
            }
            R2DUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
        }

        public static void drawHLine(float x, float y, float x1, int y1, int y2) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
        }

        public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
            R2DUtils.enableGL2D();
            GL11.glShadeModel((int)7425);
            GL11.glBegin((int)7);
            RenderUtil.glColor(topColor);
            GL11.glVertex2f((float)x, (float)y1);
            GL11.glVertex2f((float)x1, (float)y1);
            RenderUtil.glColor(bottomColor);
            GL11.glVertex2f((float)x1, (float)y);
            GL11.glVertex2f((float)x, (float)y);
            GL11.glEnd();
            GL11.glShadeModel((int)7424);
            R2DUtils.disableGL2D();
        }

        public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
            float red = 0.003921569f * (float)redRGB;
            float green = 0.003921569f * (float)greenRGB;
            float blue = 0.003921569f * (float)blueRGB;
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        }
    }
}

