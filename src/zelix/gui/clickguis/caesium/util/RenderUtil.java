/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.caesium.util;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class RenderUtil {
    public static void drawRect(float x, float y, float width, float height, int color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        RenderUtil.glColor(color);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glVertex2f((float)(x + width), (float)(y + height));
        GL11.glVertex2f((float)(x + width), (float)y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void relativeRect(float left, float top, float right, float bottom, int color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.func_178180_c();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        RenderUtil.glColor(color);
        worldRenderer.func_181668_a(7, DefaultVertexFormats.POSITION);
        worldRenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181675_d();
        worldRenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181675_d();
        worldRenderer.func_181662_b((double)right, (double)top, 0.0).func_181675_d();
        worldRenderer.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
        int sections = 1920;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushAttrib((int)8192);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
        }
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawHorizontalGradient(float x, float y, float width, float height, int leftColor, int rightColor) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        RenderUtil.glColor(leftColor);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + height));
        RenderUtil.glColor(rightColor);
        GL11.glVertex2f((float)(x + width), (float)(y + height));
        GL11.glVertex2f((float)(x + width), (float)y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawVerticalGradient(float x, float y, float width, float height, int topColor, int bottomColor) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        RenderUtil.glColor(topColor);
        GL11.glVertex2f((float)x, (float)(y + height));
        GL11.glVertex2f((float)(x + width), (float)(y + height));
        RenderUtil.glColor(bottomColor);
        GL11.glVertex2f((float)(x + width), (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)color);
    }

    public static void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        RenderUtil.drawRect(startX, y, endX + 1, y + 1, color);
    }

    public static void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        RenderUtil.drawRect(x, startY + 1, x + 1, endY, color);
    }

    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }
}

