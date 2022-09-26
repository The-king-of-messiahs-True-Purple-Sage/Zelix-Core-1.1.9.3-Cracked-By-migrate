/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Window;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public final class HackButton
extends Component {
    private final Hack hack;
    private Window settingsWindow;

    public HackButton(Hack hack) {
        this.hack = hack;
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        if (!this.hack.getSettings().isEmpty() && mouseX > this.getX() + this.getWidth() - 12) {
            if (this.settingsWindow != null && !this.settingsWindow.isClosing()) {
                this.settingsWindow.close();
                this.settingsWindow = null;
                return;
            }
            this.settingsWindow = new Window(this.hack.getName() + " Settings");
            for (Setting setting : this.hack.getSettings().values()) {
                this.settingsWindow.add(setting.getComponent());
            }
            this.settingsWindow.setClosable(true);
            this.settingsWindow.setMinimizable(false);
            this.settingsWindow.pack();
            int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
            int x = this.getParent().getX() + this.getParent().getWidth() + 5;
            int y = this.getParent().getY() + 12 + this.getY() + scroll;
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            if (x + this.settingsWindow.getWidth() > sr.getScaledWidth()) {
                x = this.getParent().getX() - this.settingsWindow.getWidth() - 5;
            }
            if (y + this.settingsWindow.getHeight() > sr.getScaledHeight()) {
                y -= this.settingsWindow.getHeight() - 14;
            }
            this.settingsWindow.setX(x);
            this.settingsWindow.setY(y);
            ClickGui gui = ForgeWurst.getForgeWurst().getGui();
            gui.addWindow(this.settingsWindow);
            return;
        }
        this.hack.setEnabled(!this.hack.isEnabled());
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        boolean hSettings;
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        boolean settings = !this.hack.getSettings().isEmpty();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int x3 = settings ? x2 - 11 : x2;
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        boolean hHack = hovering && mouseX < x3;
        boolean bl = hSettings = hovering && mouseX >= x3;
        if (hHack) {
            gui.setTooltip(this.hack.getDescription());
        }
        if (this.hack.isEnabled()) {
            GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)(hHack ? opacity * 1.5f : opacity));
        } else {
            GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hHack ? opacity * 1.5f : opacity));
        }
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x3, (int)y1);
        if (settings) {
            GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hSettings ? opacity * 1.5f : opacity));
            GL11.glVertex2i((int)x3, (int)y1);
            GL11.glVertex2i((int)x3, (int)y2);
            GL11.glVertex2i((int)x2, (int)y2);
            GL11.glVertex2i((int)x2, (int)y1);
        }
        GL11.glEnd();
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        if (settings) {
            double ya2;
            double ya1;
            GL11.glBegin((int)1);
            GL11.glVertex2i((int)x3, (int)y1);
            GL11.glVertex2i((int)x3, (int)y2);
            GL11.glEnd();
            double xa1 = x3 + 1;
            double xa2 = (double)(x3 + x2) / 2.0;
            double xa3 = x2 - 1;
            if (this.settingsWindow != null && !this.settingsWindow.isClosing()) {
                ya1 = (double)y2 - 3.5;
                ya2 = y1 + 3;
                GL11.glColor4f((float)(hSettings ? 1.0f : 0.85f), (float)0.0f, (float)0.0f, (float)1.0f);
            } else {
                ya1 = (double)y1 + 3.5;
                ya2 = y2 - 3;
                GL11.glColor4f((float)0.0f, (float)(hSettings ? 1.0f : 0.85f), (float)0.0f, (float)1.0f);
            }
            GL11.glBegin((int)4);
            GL11.glVertex2d((double)xa1, (double)ya1);
            GL11.glVertex2d((double)xa3, (double)ya1);
            GL11.glVertex2d((double)xa2, (double)ya2);
            GL11.glEnd();
            GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xa1, (double)ya1);
            GL11.glVertex2d((double)xa3, (double)ya1);
            GL11.glVertex2d((double)xa2, (double)ya2);
            GL11.glEnd();
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        FontRenderer fr = WMinecraft.getFontRenderer();
        int fx = x1 + ((settings ? this.getWidth() - 11 : this.getWidth()) - fr.getStringWidth(this.hack.getName())) / 2;
        int fy = y1 + 2;
        fr.drawString(this.hack.getName(), fx, fy, 0xF0F0F0);
        GL11.glDisable((int)3553);
    }

    @Override
    public int getDefaultWidth() {
        int width = WMinecraft.getFontRenderer().getStringWidth(this.hack.getName()) + 2;
        if (!this.hack.getSettings().isEmpty()) {
            width += 11;
        }
        return width;
    }

    @Override
    public int getDefaultHeight() {
        return 11;
    }
}

