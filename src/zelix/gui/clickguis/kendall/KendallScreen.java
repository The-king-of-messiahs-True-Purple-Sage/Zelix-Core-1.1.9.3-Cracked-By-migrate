/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.kendall;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import zelix.Core;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.kendall.components.Component;
import zelix.gui.clickguis.kendall.components.impls.KendallButton;
import zelix.gui.clickguis.kendall.components.impls.KendallMode;
import zelix.gui.clickguis.kendall.components.impls.KendallOption;
import zelix.gui.clickguis.kendall.components.impls.KendallSIlder;
import zelix.gui.clickguis.kendall.frame.KendallFrame;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.utils.tenacityutils.render.ColorUtil;
import zelix.utils.tenacityutils.render.RenderUtil;
import zelix.utils.visual.RenderUtils;

public class KendallScreen
extends GuiScreen {
    public ArrayList<KendallFrame> frames = new ArrayList();
    public boolean onMoving = false;
    public ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    public double x = 0.0;
    public double y = 0.0;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    private final FontRenderer fr = Wrapper.INSTANCE.fontRenderer();
    public boolean isVIsableSW = false;
    public KendallButton targetbt = null;
    private ResourceLocation res = new ResourceLocation("blur.png");

    protected ArrayList<KendallFrame> getFrames() {
        return this.frames;
    }

    protected void addFrame(KendallFrame frame) {
        if (!this.frames.contains(frame)) {
            this.frames.add(frame);
        }
    }

    public KendallScreen() {
        int x = 20;
        for (HackCategory category : HackCategory.values()) {
            this.addFrame(new KendallFrame(category, x, 20));
            x += 110;
        }
    }

    public void initGui() {
        for (KendallFrame frame : this.frames) {
            frame.initialize();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (KendallScreen.isHovered((float)this.x, (float)this.y, (float)(this.x + (double)this.width), (float)(this.y + 25.0), mouseX, mouseY) && mouseButton == 0) {
            this.onMoving = true;
        }
        if (this.isVIsableSW && this.targetbt != null) {
            for (Component cp : this.targetbt.components) {
                cp.mouseClicked(mouseX, mouseY, mouseButton);
            }
            return;
        }
        for (KendallFrame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.onMoving = false;
        for (KendallFrame frame : this.frames) {
            frame.mouseReleased(mouseX, mouseY, state);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.isVIsableSW && keyCode == 1) {
            this.isVIsableSW = false;
            return;
        }
        super.keyTyped(typedChar, keyCode);
        for (KendallFrame frame : this.frames) {
            frame.keyTyped(typedChar, keyCode);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sR = new ScaledResolution(this.mc);
        this.fr.drawString("Thanks For Using!", 2, sR.getScaledHeight() - this.fr.FONT_HEIGHT, Panel.fontColor);
        for (KendallFrame frame : this.frames) {
            frame.render(mouseX, mouseY);
        }
        if (ClickGui.KendallMyGod.isVIsableSW) {
            this.isVIsableSW = false;
            this.ShowSettingsWindow(mouseX, mouseY);
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.mc.entityRenderer.stopUseShader();
        Core.fileManager.saveClickGui_Kendall();
        Core.fileManager.saveHacks();
    }

    public void ShowSettingsWindow(int mouseX, int mouseY) {
        if (this.targetbt == null) {
            this.isVIsableSW = false;
            return;
        }
        this.isVIsableSW = true;
        this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        double width = 250.0;
        double height = 34 + 29 * this.targetbt.components.size() - 2;
        if (this.x <= 0.0 && this.y <= 0.0) {
            this.x = (double)(this.scaledResolution.getScaledWidth() / 2) - width / 2.0;
            this.y = (double)(this.scaledResolution.getScaledHeight() / 2) - height / 2.0;
        }
        if (KendallScreen.isHovered((float)this.x, (float)this.y, (float)(this.x + width), (float)(this.y + 25.0), mouseX, mouseY) && Mouse.isButtonDown((int)0) && this.onMoving) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = (float)((double)mouseX - this.x);
                this.moveY = (float)((double)mouseY - this.y);
            } else {
                this.x = (int)((float)mouseX - this.moveX);
                this.y = (int)((float)mouseY - this.moveY);
                float f = (float)(this.y + 20.0);
            }
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        float alphaAnimation = 1.0f;
        RenderUtil.drawRect2(this.x, this.y, width, height, ColorUtil.applyOpacity(new Color(10, 10, 10), (float)(0.8 * (double)alphaAnimation)).getRGB());
        if (!ClickGui.language.getMode("Chinese").isToggled()) {
            FontLoaders.default18.drawString(this.targetbt.mod.getRenderName(), (float)this.x + 10.0f, (float)this.y + 12.0f, -657929);
        } else {
            Wrapper.INSTANCE.fontRenderer().drawString(this.targetbt.mod.getRenderName(), (int)((float)this.x + 10.0f), (int)((float)this.y + 12.0f), -657929);
        }
        float ssl = (float)this.x;
        float hqc = (float)this.y + 24.0f;
        for (Component sjh : this.targetbt.components) {
            if (sjh instanceof KendallMode) {
                KendallMode fuckssl = (KendallMode)sjh;
                fuckssl.render(ssl, hqc, mouseX, mouseY);
            }
            if (sjh instanceof KendallSIlder) {
                KendallSIlder fuckrcx = (KendallSIlder)sjh;
                fuckrcx.render(ssl, hqc, mouseX, mouseY);
            }
            if (sjh instanceof KendallOption) {
                KendallOption fuckhqc = (KendallOption)sjh;
                fuckhqc.render(ssl, hqc, mouseX, mouseY);
            }
            hqc += 29.0f;
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public void renderBLUR(int x, int y, int width, int height) {
        RenderUtils.drawImage(this.res, x, y, width, height);
    }
}

