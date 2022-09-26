/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;

public final class Checkbox
extends Component {
    private final CheckboxSetting setting;

    public Checkbox(CheckboxSetting setting) {
        this.setting = setting;
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.setting.setChecked(!this.setting.isChecked());
        } else if (mouseButton == 1) {
            this.setting.setChecked(this.setting.isCheckedByDefault());
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        boolean hovering;
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int x3 = x1 + 11;
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean bl = hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        if (hovering && mouseX >= x3) {
            gui.setTooltip(this.setting.getDescription());
        }
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hovering ? opacity * 1.5f : opacity));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glEnd();
        if (this.setting.isChecked()) {
            double xc1 = (double)x1 + 2.5;
            double xc2 = (double)x1 + 3.5;
            double xc3 = (double)x1 + 4.5;
            double xc4 = (double)x1 + 7.5;
            double xc5 = (double)x1 + 8.5;
            double yc1 = (double)y1 + 2.5;
            double yc2 = (double)y1 + 3.5;
            double yc3 = (double)y1 + 5.5;
            double yc4 = (double)y1 + 6.5;
            double yc5 = (double)y1 + 8.5;
            GL11.glColor4f((float)0.0f, (float)(hovering ? 1.0f : 0.85f), (float)0.0f, (float)1.0f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xc2, (double)yc3);
            GL11.glVertex2d((double)xc3, (double)yc4);
            GL11.glVertex2d((double)xc3, (double)yc5);
            GL11.glVertex2d((double)xc1, (double)yc4);
            GL11.glVertex2d((double)xc4, (double)yc1);
            GL11.glVertex2d((double)xc5, (double)yc2);
            GL11.glVertex2d((double)xc3, (double)yc5);
            GL11.glVertex2d((double)xc3, (double)yc4);
            GL11.glEnd();
            GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xc2, (double)yc3);
            GL11.glVertex2d((double)xc3, (double)yc4);
            GL11.glVertex2d((double)xc4, (double)yc1);
            GL11.glVertex2d((double)xc5, (double)yc2);
            GL11.glVertex2d((double)xc3, (double)yc5);
            GL11.glVertex2d((double)xc1, (double)yc4);
            GL11.glEnd();
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        FontRenderer fr = WMinecraft.getFontRenderer();
        fr.drawString(this.setting.getName(), x3 + 2, y1 + 2, 0xF0F0F0);
        GL11.glDisable((int)3553);
    }

    @Override
    public int getDefaultWidth() {
        return WMinecraft.getFontRenderer().getStringWidth(this.setting.getName()) + 13;
    }

    @Override
    public int getDefaultHeight() {
        return 11;
    }
}

