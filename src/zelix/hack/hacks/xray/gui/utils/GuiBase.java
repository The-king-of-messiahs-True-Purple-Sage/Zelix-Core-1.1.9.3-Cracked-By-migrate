/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 */
package zelix.hack.hacks.xray.gui.utils;

import java.awt.Color;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiBase
extends GuiScreen {
    private boolean hasSide;
    private String sideTitle = "";
    private int backgroundWidth = 229;
    private int backgroundHeight = 235;

    public GuiBase(boolean hasSide) {
        this.hasSide = hasSide;
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        if (par2 == 1) {
            this.mc.thePlayer.closeScreen();
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void drawTexturedQuadFit(double x, double y, double width, double height, int[] color, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder tessellate = tessellator.func_178180_c();
        tessellate.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        if (color != null) {
            GlStateManager.color((float)((float)color[0] / 255.0f), (float)((float)color[1] / 255.0f), (float)((float)color[2] / 255.0f), (float)(alpha / 255.0f));
        }
        tessellate.func_181662_b(x + 0.0, y + height, 0.0).func_187315_a(0.0, 1.0).func_181675_d();
        tessellate.func_181662_b(x + width, y + height, 0.0).func_187315_a(1.0, 1.0).func_181675_d();
        tessellate.func_181662_b(x + width, y + 0.0, 0.0).func_187315_a(1.0, 0.0).func_181675_d();
        tessellate.func_181662_b(x + 0.0, y + 0.0, 0.0).func_187315_a(0.0, 0.0).func_181675_d();
        tessellator.draw();
    }

    public static void drawTexturedQuadFit(double x, double y, double width, double height, int[] color) {
        GuiBase.drawTexturedQuadFit(x, y, width, height, color, 255.0f);
    }

    public static void drawTexturedQuadFit(double x, double y, double width, double height, @Nullable Color color) {
        int[] nArray;
        if (color == null) {
            nArray = null;
        } else {
            int[] nArray2 = new int[3];
            nArray2[0] = color.getRed();
            nArray2[1] = color.getGreen();
            nArray = nArray2;
            nArray2[2] = color.getBlue();
        }
        GuiBase.drawTexturedQuadFit(x, y, width, height, nArray, 255.0f);
    }

    public void drawScreen(int x, int y, float f) {
        this.drawDefaultBackground();
        FontRenderer fr = this.mc.fontRendererObj;
        if (this.colorBackground() == null) {
            this.mc.renderEngine.bindTexture(new ResourceLocation("xray:textures/gui/bg.png"));
        } else {
            GlStateManager.disableTexture2D();
        }
        if (this.hasSide) {
            GuiBase.drawTexturedQuadFit((double)this.width / 2.0 + 60.0, (double)((float)this.height / 2.0f - 90.0f), 150.0, 180.0, this.colorBackground());
            GuiBase.drawTexturedQuadFit((double)((float)this.width / 2.0f - 150.0f), (double)((float)this.height / 2.0f - 118.0f), (double)this.backgroundWidth, (double)this.backgroundHeight, this.colorBackground());
            if (this.hasSideTitle()) {
                fr.drawStringWithShadow(this.sideTitle, (float)this.width / 2.0f + 80.0f, (float)this.height / 2.0f - 77.0f, 0xFFFF00);
            }
        }
        if (!this.hasSide) {
            GuiBase.drawTexturedQuadFit((double)((float)this.width / 2.0f - (float)this.backgroundWidth / 2.0f + 1.0f), (double)((float)this.height / 2.0f - (float)this.backgroundHeight / 2.0f), (double)this.backgroundWidth, (double)this.backgroundHeight, this.colorBackground());
        }
        if (this.colorBackground() != null) {
            GlStateManager.enableTexture2D();
        }
        if (this.hasTitle()) {
            if (this.hasSide) {
                fr.drawStringWithShadow(this.title(), (float)this.width / 2.0f - 138.0f, (float)this.height / 2.0f - 105.0f, 0xFFFF00);
            } else {
                fr.drawStringWithShadow(this.title(), (float)this.width / 2.0f - (float)this.backgroundWidth / 2.0f + 14.0f, (float)this.height / 2.0f - (float)this.backgroundHeight / 2.0f + 13.0f, 0xFFFF00);
            }
        }
        super.drawScreen(x, y, f);
    }

    public Color colorBackground() {
        return null;
    }

    public void mouseClicked(int x, int y, int mouse) throws IOException {
        super.mouseClicked(x, y, mouse);
    }

    public boolean hasTitle() {
        return false;
    }

    public String title() {
        return "";
    }

    private boolean hasSideTitle() {
        return !this.sideTitle.isEmpty();
    }

    protected void setSideTitle(String title) {
        this.sideTitle = title;
    }

    public void setSize(int width, int height) {
        this.backgroundWidth = width;
        this.backgroundHeight = height;
    }

    public FontRenderer getFontRender() {
        return this.mc.fontRendererObj;
    }
}

