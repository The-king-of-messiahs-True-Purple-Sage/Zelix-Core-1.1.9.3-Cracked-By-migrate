/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package zelix.utils.hooks.visual;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import zelix.hack.hacks.ClickGui;
import zelix.hack.hacks.KillAura;
import zelix.hack.hacks.Scaffold;
import zelix.managers.FontManager;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.GLUtils;

public class RenderUtils {
    private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    public static TimerUtils splashTimer = new TimerUtils();
    public static int splashTickPos = 0;
    public static boolean isSplash = false;
    public static FontManager fontManager = new FontManager();

    public static String DF(float value, int maxvalue) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(maxvalue);
        return df.format(value);
    }

    public static void glColor(int red, int green, int blue, int alpha) {
        GL11.glColor4f((float)((float)red / 255.0f), (float)((float)green / 255.0f), (float)((float)blue / 255.0f), (float)((float)alpha / 255.0f));
    }

    public static void glColor(Color color) {
        RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private static void glColor(int hex) {
        RenderUtils.glColor(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, hex >> 24 & 0xFF);
    }

    public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.POSITION);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.draw();
    }

    public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, int radius, int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        RenderUtils.drawRect(xPosition + (double)radius, yPosition + (double)radius, xPosition + width - (double)radius, yPosition + height - (double)radius, color);
        RenderUtils.drawRect(xPosition, yPosition + (double)radius, xPosition + (double)radius, yPosition + height - (double)radius, color);
        RenderUtils.drawRect(xPosition + width - (double)radius, yPosition + (double)radius, xPosition + width, yPosition + height - (double)radius, color);
        RenderUtils.drawRect(xPosition + (double)radius, yPosition, xPosition + width - (double)radius, yPosition + (double)radius, color);
        RenderUtils.drawRect(xPosition + (double)radius, yPosition + height - (double)radius, xPosition + width - (double)radius, yPosition + height, color);
        RenderUtils.drawFilledCircle(xPosition + (double)radius, yPosition + (double)radius, radius, color, 1);
        RenderUtils.drawFilledCircle(xPosition + (double)radius, yPosition + height - (double)radius, radius, color, 2);
        RenderUtils.drawFilledCircle(xPosition + width - (double)radius, yPosition + (double)radius, radius, color, 3);
        RenderUtils.drawFilledCircle(xPosition + width - (double)radius, yPosition + height - (double)radius, radius, color, 4);
    }

    public static void drawFilledCircle(double x, double y, double r, int c, int id) {
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable((int)259);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)9);
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
        GL11.glDisable((int)259);
    }

    public static void drawStringWithRect(String string, int x, int y, int colorString, int colorRect, int colorRect2) {
        RenderUtils.drawBorderedRect(x - 2, y - 2, x + Wrapper.INSTANCE.fontRenderer().getStringWidth(string) + 2, y + 10, 1.0f, colorRect, colorRect2);
        Wrapper.INSTANCE.fontRenderer().drawString(string, x, y, colorString);
    }

    public static void drawNode(AxisAlignedBB bb) {
        double midX = (bb.field_72340_a + bb.field_72336_d) / 2.0;
        double midY = (bb.field_72338_b + bb.field_72337_e) / 2.0;
        double midZ = (bb.field_72339_c + bb.field_72334_f) / 2.0;
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
    }

    public static void drawSplash(String text) {
        ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
        RenderUtils.drawStringWithRect(text, sr.getScaledWidth() + 2 - splashTickPos, sr.getScaledHeight() - 10, ClickGui.getColor(), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.0f), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f));
        if (splashTimer.isDelay(10L)) {
            splashTimer.setLastMS();
            if (isSplash) {
                if (++splashTickPos == Wrapper.INSTANCE.fontRenderer().getStringWidth(text) + 10) {
                    isSplash = false;
                }
            } else if (splashTickPos > 0) {
                --splashTickPos;
            }
        }
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        RenderUtils.drawRect((int)x, (int)y, (int)x2, (int)y2, col2);
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glLineWidth((float)l1);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public static void drawTracer(Entity entity, float red, float green, float blue, float alpha, float ticks) {
        double renderPosX = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
        double renderPosY = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
        double renderPosZ = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
        double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)ticks - renderPosX;
        double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)ticks + (double)(entity.height / 2.0f) - renderPosY;
        double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)ticks - renderPosZ;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        Vec3d eyes = null;
        eyes = KillAura.facingCam != null ? new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(KillAura.facingCam[1]))).func_178785_b(-((float)Math.toRadians(KillAura.facingCam[0]))) : (Scaffold.facingCam != null ? new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Scaffold.facingCam[1]))).func_178785_b(-((float)Math.toRadians(Scaffold.facingCam[0]))) : new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Wrapper.INSTANCE.player().rotationPitch))).func_178785_b(-((float)Math.toRadians(Wrapper.INSTANCE.player().rotationYaw))));
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)eyes.field_72450_a, (double)((double)Wrapper.INSTANCE.player().getEyeHeight() + eyes.field_72448_b), (double)eyes.field_72449_c);
        GL11.glVertex3d((double)xPos, (double)yPos, (double)zPos);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glDepthMask((boolean)true);
    }

    public static void drawTracer(BlockPos entity, float red, float green, float blue, float alpha, float ticks) {
        double renderPosX = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
        double renderPosY = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
        double renderPosZ = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
        double xPos = (double)((float)entity.func_177958_n() * ticks) - renderPosX;
        double yPos = (double)((float)entity.func_177956_o() * ticks) - renderPosY;
        double zPos = (double)((float)entity.func_177952_p() * ticks) - renderPosZ;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        Vec3d eyes = null;
        eyes = KillAura.facingCam != null ? new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(KillAura.facingCam[1]))).func_178785_b(-((float)Math.toRadians(KillAura.facingCam[0]))) : (Scaffold.facingCam != null ? new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Scaffold.facingCam[1]))).func_178785_b(-((float)Math.toRadians(Scaffold.facingCam[0]))) : new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Wrapper.INSTANCE.player().rotationPitch))).func_178785_b(-((float)Math.toRadians(Wrapper.INSTANCE.player().rotationYaw))));
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)eyes.field_72450_a, (double)((double)Wrapper.INSTANCE.player().getEyeHeight() + eyes.field_72448_b), (double)eyes.field_72449_c);
        GL11.glVertex3d((double)xPos, (double)yPos, (double)zPos);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glDepthMask((boolean)true);
    }

    public static void drawESP(Entity entity, float colorRed, float colorGreen, float colorBlue, float colorAlpha, float ticks) {
        try {
            double renderPosX = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
            double renderPosY = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
            double renderPosZ = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)ticks - renderPosX;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)ticks + (double)(entity.height / 2.0f) - renderPosY;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)ticks - renderPosZ;
            float playerViewY = Wrapper.INSTANCE.mc().getRenderManager().playerViewY;
            float playerViewX = Wrapper.INSTANCE.mc().getRenderManager().playerViewX;
            boolean thirdPersonView = Wrapper.INSTANCE.mc().getRenderManager().options.thirdPersonView == 2;
            GL11.glPushMatrix();
            GlStateManager.translate((double)xPos, (double)yPos, (double)zPos);
            GlStateManager.func_187432_a((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((float)(thirdPersonView ? -1 : 1) * playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)1.0f);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glColor4f((float)colorRed, (float)colorGreen, (float)colorBlue, (float)colorAlpha);
            GL11.glBegin((int)1);
            GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
            GL11.glVertex3d((double)-0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
            GL11.glVertex3d((double)0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)-0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.5, (double)0.5, (double)0.0);
            GL11.glEnd();
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Wrapper.INSTANCE.mc().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawBlockESP(BlockPos pos, float red, float green, float blue) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        double renderPosX = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
        double renderPosY = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
        double renderPosZ = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
        GL11.glTranslated((double)(-renderPosX), (double)(-renderPosY), (double)(-renderPosZ));
        GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.3f);
        RenderUtils.drawSolidBox();
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.7f);
        RenderUtils.drawOutlinedBox();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.draw();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        tessellator.draw();
        vertexbuffer.func_181668_a(1, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        tessellator.draw();
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.func_178180_c();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
        vb.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        ts.draw();
    }

    public static void drawSolidBox() {
        RenderUtils.drawSolidBox(DEFAULT_AABB);
    }

    public static void drawSolidBox(AxisAlignedBB bb) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glEnd();
    }

    public static void drawOutlinedBox() {
        RenderUtils.drawOutlinedBox(DEFAULT_AABB);
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glEnd();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexBuffer = tessellator.func_178180_c();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        tessellator.draw();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        tessellator.draw();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        tessellator.draw();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        tessellator.draw();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        tessellator.draw();
        vertexBuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f);
        vertexBuffer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f);
        tessellator.draw();
    }

    public static void drawOutlineBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.draw();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        tessellator.draw();
        vertexbuffer.func_181668_a(1, DefaultVertexFormats.POSITION);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        tessellator.draw();
    }

    public static void drawTri(double x1, double y1, double x2, double y2, double x3, double y3, double width, Color c) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GLUtils.glColor(c);
        GL11.glLineWidth((float)((float)width));
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
    }

    public static void drawHLine(float par1, float par2, float par3, int color) {
        if (par2 < par1) {
            float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        RenderUtils.drawRect(par1, par3, par2 + 1.0f, par3 + 1.0f, color);
    }

    public static void drawRect(double d, double e, double f, double g, int color) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        RenderUtils.color(color);
        GL11.glBegin((int)7);
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
    }

    public static void color(int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
    }

    public static void drawVLine(float par1, float par2, float par3, int color) {
        if (par3 < par2) {
            float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        RenderUtils.drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, color);
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        float var5;
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
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GLUtils.glColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }
}

