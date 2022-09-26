/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;

public class GuiLabel
implements GuiComponent {
    private String text;

    public GuiLabel(String text) {
        this.text = text;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        Panel.fR.drawStringWithShadow(this.text, (float)(posX + width / 2 - Panel.fR.getStringWidth(this.text) / 2), (float)(posY + 2), Panel.fontColor);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text) + 4;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 2;
    }
}

