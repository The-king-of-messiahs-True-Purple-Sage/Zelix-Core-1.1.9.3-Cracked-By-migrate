/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import zelix.gui.clickguis.caesium.ClickGui;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.clickguis.caesium.components.GuiFrame;
import zelix.gui.clickguis.caesium.components.listeners.ComponentListener;
import zelix.gui.clickguis.caesium.util.RenderUtil;
import zelix.hack.Hack;

public class GuiButton
implements GuiComponent {
    public static int expandedID = -1;
    private int id;
    private Hack text;
    private ArrayList<ActionListener> clickListeners = new ArrayList();
    private ArrayList<GuiComponent> guiComponents = new ArrayList();
    private int width;
    private int posX;
    private int posY;

    public GuiButton(Hack text) {
        this.text = text;
        this.id = ++ClickGui.compID;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        int height = this.getHeight();
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(posX, posY, width, height);
                break;
            }
        }
    }

    private void renderCaesium(int posX, int posY, int width, int height) {
        RenderUtil.drawRect(posX, posY, posX + width - 1, posY + height, Panel.black100);
        int color = Panel.fontColor;
        if (this.text.isToggled()) {
            color = Panel.color;
        }
        Panel.fR.drawStringWithShadow(this.getText(), (float)(posX + width / 2 - Panel.fR.getStringWidth(this.getText()) / 2), (float)(posY + 2), color);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (GuiFrame.dragID == -1 && RenderUtil.isHovered(this.posX, this.posY, this.width, this.getHeight(), mouseX, mouseY)) {
            if (mouseButton == 1) {
                expandedID = expandedID != this.id ? this.id : -1;
            } else if (mouseButton == 0) {
                for (ActionListener listener : this.clickListeners) {
                    listener.actionPerformed(new ActionEvent(this, this.id, "click", System.currentTimeMillis(), 0));
                }
            }
        }
        if (expandedID == this.id) {
            for (GuiComponent component2 : this.guiComponents) {
                component2.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (expandedID == this.id) {
            for (GuiComponent component2 : this.guiComponents) {
                component2.keyTyped(keyCode, typedChar);
            }
        }
    }

    @Override
    public int getWidth() {
        return 5 + Panel.fR.getStringWidth(this.text.getRenderName());
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 3;
    }

    public String getText() {
        return this.text.getRenderName();
    }

    public int getButtonID() {
        return this.id;
    }

    public ArrayList<GuiComponent> getComponents() {
        return this.guiComponents;
    }

    public void addClickListener(ActionListener actionlistener) {
        this.clickListeners.add(actionlistener);
    }

    public void addExtendListener(ComponentListener listener) {
        listener.addComponents();
        this.guiComponents.addAll(listener.getComponents());
    }
}

