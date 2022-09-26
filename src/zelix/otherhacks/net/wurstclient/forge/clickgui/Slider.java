/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.EditSliderScreen;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;

public final class Slider
extends Component {
    private final SliderSetting setting;
    private boolean dragging;

    public Slider(SliderSetting setting) {
        this.setting = setting;
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseY < this.getY() + 11) {
            return;
        }
        if (mouseButton == 0) {
            if (Keyboard.isKeyDown((int)29) || Keyboard.isKeyDown((int)157)) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new EditSliderScreen(Minecraft.getMinecraft().currentScreen, this.setting));
            } else {
                this.dragging = true;
            }
        } else if (mouseButton == 1) {
            this.setting.setValue(this.setting.getDefaultValue());
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        boolean hSlider;
        if (this.dragging) {
            if (Mouse.isButtonDown((int)0)) {
                double mousePercentage = (double)(mouseX - (this.getX() + 2)) / (double)(this.getWidth() - 4);
                double value = this.setting.getMin() + (this.setting.getMax() - this.setting.getMin()) * mousePercentage;
                this.setting.setValue(value);
            } else {
                this.dragging = false;
            }
        }
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int x3 = x1 + 2;
        int x4 = x2 - 2;
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int y3 = y1 + 11;
        int y4 = y3 + 4;
        int y5 = y2 - 4;
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        boolean bl = hSlider = hovering && mouseY >= y3 || this.dragging;
        if (hovering && mouseY < y3) {
            gui.setTooltip(this.setting.getDescription());
        }
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y4);
        GL11.glVertex2i((int)x2, (int)y4);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glVertex2i((int)x1, (int)y5);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y5);
        GL11.glVertex2i((int)x1, (int)y4);
        GL11.glVertex2i((int)x1, (int)y5);
        GL11.glVertex2i((int)x3, (int)y5);
        GL11.glVertex2i((int)x3, (int)y4);
        GL11.glVertex2i((int)x4, (int)y4);
        GL11.glVertex2i((int)x4, (int)y5);
        GL11.glVertex2i((int)x2, (int)y5);
        GL11.glVertex2i((int)x2, (int)y4);
        GL11.glEnd();
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hSlider ? opacity * 1.5f : opacity));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x3, (int)y4);
        GL11.glVertex2i((int)x3, (int)y5);
        GL11.glVertex2i((int)x4, (int)y5);
        GL11.glVertex2i((int)x4, (int)y4);
        GL11.glEnd();
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x3, (int)y4);
        GL11.glVertex2i((int)x3, (int)y5);
        GL11.glVertex2i((int)x4, (int)y5);
        GL11.glVertex2i((int)x4, (int)y4);
        GL11.glEnd();
        double percentage = (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
        double xk1 = (double)x1 + (double)(x2 - x1 - 8) * percentage;
        double xk2 = xk1 + 8.0;
        double yk1 = (double)y3 + 1.5;
        double yk2 = (double)y2 - 1.5;
        float f = (float)(2.0 * percentage);
        GL11.glColor4f((float)f, (float)(2.0f - f), (float)0.0f, (float)(hSlider ? 1.0f : 0.75f));
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)xk1, (double)yk1);
        GL11.glVertex2d((double)xk1, (double)yk2);
        GL11.glVertex2d((double)xk2, (double)yk2);
        GL11.glVertex2d((double)xk2, (double)yk1);
        GL11.glEnd();
        GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xk1, (double)yk1);
        GL11.glVertex2d((double)xk1, (double)yk2);
        GL11.glVertex2d((double)xk2, (double)yk2);
        GL11.glVertex2d((double)xk2, (double)yk1);
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        FontRenderer fr = WMinecraft.getFontRenderer();
        fr.drawString(this.setting.getName(), x1, y1 + 2, 0xF0F0F0);
        fr.drawString(this.setting.getValueString(), x2 - fr.getStringWidth(this.setting.getValueString()), y1 + 2, 0xF0F0F0);
        GL11.glDisable((int)3553);
    }

    @Override
    public int getDefaultWidth() {
        FontRenderer fr = WMinecraft.getFontRenderer();
        return fr.getStringWidth(this.setting.getName()) + fr.getStringWidth(this.setting.getValueString()) + 6;
    }

    @Override
    public int getDefaultHeight() {
        return 22;
    }
}

