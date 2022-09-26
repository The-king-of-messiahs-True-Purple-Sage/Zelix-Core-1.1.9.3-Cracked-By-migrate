/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

import java.awt.Color;
import java.util.ArrayList;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.clickguis.caesium.components.listeners.ComboListener;
import zelix.gui.clickguis.caesium.util.RenderUtil;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class GuiComboBox
implements GuiComponent {
    private ModeValue setting;
    private boolean extended;
    private int height;
    private int posX;
    private int posY;
    private int width;
    private ArrayList<ComboListener> comboListeners = new ArrayList();

    public GuiComboBox(ModeValue setting) {
        this.setting = setting;
    }

    public void addComboListener(ComboListener comboListener) {
        this.comboListeners.add(comboListener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium();
                break;
            }
        }
    }

    public void renderCaesium() {
        if (this.extended) {
            RenderUtil.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.grey40_240);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY, Panel.black195);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, new Color(0, 0, 0, 150).getRGB());
            Panel.fR.drawStringWithShadow(this.setting.getRenderName() + "  -", (float)(this.posX + this.width / 2 - Panel.fR.getStringWidth(this.setting.getRenderName() + "  -") / 2), (float)(this.posY + 2), Panel.fontColor);
            int innerHeight = Panel.fR.FONT_HEIGHT + 5;
            for (Mode comb : this.setting.getModes()) {
                String combo = comb.getName();
                if (this.setting.getSelectMode().getName().equalsIgnoreCase(combo)) {
                    Panel.fR.drawStringWithShadow(combo, (float)(this.posX + 10), (float)(this.posY + innerHeight), Panel.color);
                } else {
                    Panel.fR.drawStringWithShadow(combo, (float)(this.posX + 5), (float)(this.posY + innerHeight), Panel.fontColor);
                }
                innerHeight += Panel.fR.FONT_HEIGHT + 2;
            }
            this.height = innerHeight + 2;
        } else {
            RenderUtil.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.grey40_240);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY, Panel.black195);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.black195);
            Panel.fR.drawStringWithShadow(this.setting.getRenderName() + "  +", (float)(this.posX + this.width / 2 - Panel.fR.getStringWidth(this.setting.getRenderName() + "  +") / 2), (float)(this.posY + 2), Panel.fontColor);
            this.height = Panel.fR.FONT_HEIGHT + 4;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(this.posX, this.posY, this.width, Panel.fR.FONT_HEIGHT + 2, mouseX, mouseY)) {
            boolean bl = this.extended = !this.extended;
        }
        if (this.extended && RenderUtil.isHovered(this.posX, this.posY + Panel.fR.FONT_HEIGHT + 2, this.width, (Panel.fR.FONT_HEIGHT + 2) * this.setting.getModes().length, mouseX, mouseY) && mouseButton == 0) {
            int h = Panel.fR.FONT_HEIGHT + 2;
            for (int i = 1; i <= this.setting.getModes().length + 1; ++i) {
                if (!RenderUtil.isHovered(this.posX, this.posY + h * i, this.width, h * i, mouseX, mouseY)) continue;
                for (Mode m : this.setting.getModes()) {
                    if (m == this.setting.getModes()[i - 1]) {
                        m.setToggled(true);
                        continue;
                    }
                    m.setToggled(false);
                }
            }
            for (ComboListener comboListener : this.comboListeners) {
                comboListener.comboChanged(this.setting.getSelectMode().getName());
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public ModeValue getSetting() {
        return this.setting;
    }

    public void setSetting(ModeValue setting) {
        this.setting = setting;
    }
}

