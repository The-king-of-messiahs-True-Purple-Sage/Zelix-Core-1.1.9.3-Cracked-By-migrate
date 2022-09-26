/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package zelix.gui.clickguis.caesium.components;

import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.clickguis.caesium.components.listeners.KeyListener;
import zelix.gui.clickguis.caesium.util.RenderUtil;

public class GuiGetKey
implements GuiComponent {
    private boolean wasChanged;
    private boolean allowChange;
    private int key;
    private int posX;
    private int posY;
    private int width;
    private String text;
    private ArrayList<KeyListener> keylisteners = new ArrayList();

    public GuiGetKey(String text, int key) {
        this.text = text;
        this.key = key;
        if (key < 0) {
            this.key = 0;
        }
    }

    public void addKeyListener(KeyListener listener) {
        this.keylisteners.add(listener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(posX, posY);
                break;
            }
        }
    }

    private void renderCaesium(int posX, int posY) {
        String keyString = Keyboard.getKeyName((int)this.key);
        this.wasChanged = this.allowChange ? !this.wasChanged : true;
        RenderUtil.drawVerticalGradient(posX, posY, this.width, 14.0f, new Color(Panel.color).darker().getRGB(), new Color(Panel.color).brighter().getRGB());
        Panel.fR.drawStringWithShadow(this.text + ":", (float)(posX + 3), (float)(posY + 3), Panel.fontColor);
        if (this.wasChanged) {
            Panel.fR.drawStringWithShadow(keyString, (float)(posX + this.width - Panel.fR.getStringWidth(keyString) - 3), (float)(posY + 3), Panel.fontColor);
        } else {
            Panel.fR.drawString(keyString, posX + this.width - Panel.fR.getStringWidth(keyString) - 3, posY + 3, Panel.fontColor);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        String keyString = Keyboard.getKeyName((int)this.key);
        int w = Panel.fR.getStringWidth(this.text);
        this.allowChange = RenderUtil.isHovered(this.posX, this.posY, this.width, 11, mouseX, mouseY);
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (this.allowChange) {
            for (KeyListener listener : this.keylisteners) {
                listener.keyChanged(keyCode);
            }
            this.key = keyCode;
            this.allowChange = false;
        }
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text + Keyboard.getKeyName((int)this.key)) + 15;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 4;
    }
}

