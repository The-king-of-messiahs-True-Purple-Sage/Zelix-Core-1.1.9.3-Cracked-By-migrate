/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.clickguis.caesium.util.RenderUtil;
import zelix.value.BooleanValue;

public class GuiToggleButton
implements GuiComponent {
    private BooleanValue text;
    private boolean toggled;
    private int posX;
    private int posY;
    private ArrayList<ActionListener> clickListeners = new ArrayList();

    public GuiToggleButton(BooleanValue text) {
        this.text = text;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(posX, posY);
                break;
            }
        }
    }

    private void renderCaesium(int posX, int posY) {
        RenderUtil.drawFilledCircle(posX + 8, posY + 7, 6.0f, new Color(Panel.color));
        if (!this.toggled) {
            RenderUtil.drawFilledCircle(posX + 8, posY + 7, 5.0f, new Color(Panel.grey40_240));
        }
        Panel.fR.drawStringWithShadow(this.text.getRenderName(), (float)(posX + 17), (float)(posY + 3), Panel.fontColor);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int width = Panel.fR.getStringWidth(this.text.getRenderName()) + 10;
        if (RenderUtil.isHovered(this.posX, this.posY + 2, width, this.getHeight(), mouseX, mouseY)) {
            this.toggled = !this.toggled;
            for (ActionListener listener : this.clickListeners) {
                listener.actionPerformed(new ActionEvent(this, this.hashCode(), "click", System.currentTimeMillis(), 0));
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text.getRenderName()) + 20;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 5;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public void addClickListener(ActionListener actionlistener) {
        this.clickListeners.add(actionlistener);
    }
}

