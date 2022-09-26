/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package zelix.utils.visual;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.utils.visual.Colors;

public class GuiRenderUtils {
    public static void drawRect(float x, float y, float width, float height, Color color) {
        GuiRenderUtils.drawRect(x, y, width, height, color.getRGB());
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        GuiRenderUtils.enableRender2D();
        GuiRenderUtils.setColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glEnd();
        GuiRenderUtils.disableRender2D();
    }

    public static void enableRender2D() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.0f);
    }

    public static void disableRender2D() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static void setColor(int colorHex) {
        float alpha = (float)(colorHex >> 24 & 0xFF) / 255.0f;
        float red = (float)(colorHex >> 16 & 0xFF) / 255.0f;
        float green = (float)(colorHex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(colorHex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
        double angleRadians;
        int i;
        if (color == 0xFFFFFF) {
            color = Colors.WHITE.c;
        }
        if (borderColor == 0xFFFFFF) {
            borderColor = Colors.WHITE.c;
        }
        if (edgeRadius < 0.0f) {
            edgeRadius = 0.0f;
        }
        if (edgeRadius > width / 2.0f) {
            edgeRadius = width / 2.0f;
        }
        if (edgeRadius > height / 2.0f) {
            edgeRadius = height / 2.0f;
        }
        GuiRenderUtils.drawRect(x + edgeRadius, y + edgeRadius, width - edgeRadius * 2.0f, height - edgeRadius * 2.0f, color);
        GuiRenderUtils.drawRect(x + edgeRadius, y, width - edgeRadius * 2.0f, edgeRadius, color);
        GuiRenderUtils.drawRect(x + edgeRadius, y + height - edgeRadius, width - edgeRadius * 2.0f, edgeRadius, color);
        GuiRenderUtils.drawRect(x, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0f, color);
        GuiRenderUtils.drawRect(x + width - edgeRadius, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0f, color);
        GuiRenderUtils.enableRender2D();
        RenderUtil.color(color);
        GL11.glBegin((int)6);
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        int vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 90) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 270) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)i / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        RenderUtil.color(borderColor);
        GL11.glLineWidth((float)borderWidth);
        GL11.glBegin((int)3);
        centerX = x + edgeRadius;
        centerY = y + edgeRadius;
        for (i = vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f); i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)(x + edgeRadius), (double)y);
        GL11.glVertex2d((double)(x + width - edgeRadius), (double)y);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)(i + 90) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)(x + width), (double)(y + edgeRadius));
        GL11.glVertex2d((double)(x + width), (double)(y + height - edgeRadius));
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)i / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)(x + width - edgeRadius), (double)(y + height));
        GL11.glVertex2d((double)(x + edgeRadius), (double)(y + height));
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)(i + 270) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)x, (double)(y + height - edgeRadius));
        GL11.glVertex2d((double)x, (double)(y + edgeRadius));
        GL11.glEnd();
        GuiRenderUtils.disableRender2D();
    }
}

