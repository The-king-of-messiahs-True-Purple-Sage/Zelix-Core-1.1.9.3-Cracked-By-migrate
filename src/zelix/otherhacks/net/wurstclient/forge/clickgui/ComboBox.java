/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import java.util.Arrays;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Popup;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;

public final class ComboBox
extends Component {
    private final EnumSetting setting;
    private final int popupWidth;
    private ComboBoxPopup popup;

    public ComboBox(EnumSetting setting) {
        this.setting = setting;
        FontRenderer fr = WMinecraft.getFontRenderer();
        this.popupWidth = Arrays.stream(setting.getValues()).mapToInt(v -> fr.getStringWidth(v.toString())).max().getAsInt();
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX < this.getX() + this.getWidth() - this.popupWidth - 15) {
            return;
        }
        if (mouseButton == 0) {
            if (this.popup != null && !this.popup.isClosing()) {
                this.popup.close();
                this.popup = null;
                return;
            }
            this.popup = new ComboBoxPopup(this);
            ClickGui gui = ForgeWurst.getForgeWurst().getGui();
            gui.addPopup(this.popup);
        } else if (mouseButton == 1 && (this.popup == null || this.popup.isClosing())) {
            this.setting.setSelected(((Enum)this.setting.getDefaultSelected()).toString());
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        double ya2;
        double ya1;
        boolean hBox;
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int x3 = x2 - 11;
        int x4 = x3 - this.popupWidth - 4;
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        boolean hText = hovering && mouseX < x4;
        boolean bl = hBox = hovering && mouseX >= x4;
        if (hText) {
            gui.setTooltip(this.setting.getDescription());
        }
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x4, (int)y2);
        GL11.glVertex2i((int)x4, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hBox ? opacity * 1.5f : opacity));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x4, (int)y1);
        GL11.glVertex2i((int)x4, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x4, (int)y1);
        GL11.glVertex2i((int)x4, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glEnd();
        double xa1 = x3 + 1;
        double xa2 = (double)(x3 + x2) / 2.0;
        double xa3 = x2 - 1;
        if (this.popup != null && !this.popup.isClosing()) {
            ya1 = (double)y2 - 3.5;
            ya2 = y1 + 3;
            GL11.glColor4f((float)(hBox ? 1.0f : 0.85f), (float)0.0f, (float)0.0f, (float)1.0f);
        } else {
            ya1 = (double)y1 + 3.5;
            ya2 = y2 - 3;
            GL11.glColor4f((float)0.0f, (float)(hBox ? 1.0f : 0.85f), (float)0.0f, (float)1.0f);
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
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        FontRenderer fr = WMinecraft.getFontRenderer();
        fr.drawString(this.setting.getName(), x1, y1 + 2, 0xF0F0F0);
        fr.drawString(((Enum)this.setting.getSelected()).toString(), x4 + 2, y1 + 2, 0xF0F0F0);
        GL11.glDisable((int)3553);
    }

    @Override
    public int getDefaultWidth() {
        return WMinecraft.getFontRenderer().getStringWidth(this.setting.getName()) + this.popupWidth + 17;
    }

    @Override
    public int getDefaultHeight() {
        return 11;
    }

    private static class ComboBoxPopup
    extends Popup {
        public ComboBoxPopup(ComboBox owner) {
            super(owner);
            this.setWidth(this.getDefaultWidth());
            this.setHeight(this.getDefaultHeight());
            this.setX(owner.getWidth() - this.getWidth());
            this.setY(owner.getHeight());
        }

        @Override
        public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
            if (mouseButton != 0) {
                return;
            }
            Enum[] values = ((ComboBox)this.getOwner()).setting.getValues();
            int yi1 = this.getY() - 11;
            for (Enum value : values) {
                if (value == ((ComboBox)this.getOwner()).setting.getSelected()) continue;
                int yi2 = (yi1 += 11) + 11;
                if (mouseY < yi1 || mouseY >= yi2) continue;
                ((ComboBox)this.getOwner()).setting.setSelected(value.toString());
                this.close();
                break;
            }
        }

        @Override
        public void render(int mouseX, int mouseY) {
            boolean hovering;
            ClickGui gui = ForgeWurst.getForgeWurst().getGui();
            float[] bgColor = gui.getBgColor();
            float[] acColor = gui.getAcColor();
            float opacity = gui.getOpacity();
            int x1 = this.getX();
            int x2 = x1 + this.getWidth();
            int y1 = this.getY();
            int y2 = y1 + this.getHeight();
            boolean bl = hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2;
            if (hovering) {
                gui.setTooltip(null);
            }
            GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2i((int)x1, (int)y1);
            GL11.glVertex2i((int)x1, (int)y2);
            GL11.glVertex2i((int)x2, (int)y2);
            GL11.glVertex2i((int)x2, (int)y1);
            GL11.glEnd();
            Enum[] values = ((ComboBox)this.getOwner()).setting.getValues();
            int yi1 = y1 - 11;
            for (Enum value : values) {
                if (value == ((ComboBox)this.getOwner()).setting.getSelected()) continue;
                int yi2 = (yi1 += 11) + 11;
                boolean hValue = hovering && mouseY >= yi1 && mouseY < yi2;
                GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hValue ? opacity * 1.5f : opacity));
                GL11.glBegin((int)7);
                GL11.glVertex2i((int)x1, (int)yi1);
                GL11.glVertex2i((int)x1, (int)yi2);
                GL11.glVertex2i((int)x2, (int)yi2);
                GL11.glVertex2i((int)x2, (int)yi1);
                GL11.glEnd();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
                FontRenderer fr = WMinecraft.getFontRenderer();
                fr.drawString(value.toString(), x1 + 2, yi1 + 2, 0xF0F0F0);
                GL11.glDisable((int)3553);
            }
        }

        @Override
        public int getDefaultWidth() {
            return ((ComboBox)this.getOwner()).popupWidth + 15;
        }

        @Override
        public int getDefaultHeight() {
            return (((ComboBox)this.getOwner()).setting.getValues().length - 1) * 11;
        }
    }
}

