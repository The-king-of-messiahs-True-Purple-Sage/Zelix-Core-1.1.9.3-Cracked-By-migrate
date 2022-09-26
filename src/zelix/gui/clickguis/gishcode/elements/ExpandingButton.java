/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.elements;

import java.util.ArrayList;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.base.Container;
import zelix.gui.clickguis.gishcode.listener.ComponentClickListener;
import zelix.hack.Hack;

public class ExpandingButton
extends Container {
    public ArrayList<ComponentClickListener> listeners = new ArrayList();
    private boolean enabled = false;
    private boolean maximized = false;
    private int buttonHeight;
    private Component component;
    public Hack hack;

    public ExpandingButton(int xPos, int yPos, int width, int buttonHeight, Component component2, String text) {
        super(xPos, yPos, width, 0, ComponentType.EXPANDING_BUTTON, component2, text);
        this.buttonHeight = buttonHeight;
        this.component = component2;
    }

    public ExpandingButton(int xPos, int yPos, int width, int buttonHeight, Component component2, String text, Hack hack) {
        super(xPos, yPos, width, 0, ComponentType.EXPANDING_BUTTON, component2, text);
        this.buttonHeight = buttonHeight;
        this.component = component2;
        this.hack = hack;
    }

    @Override
    public void render(int x, int y) {
        int height = this.buttonHeight;
        if (this.maximized) {
            for (Component component2 : this.getComponents()) {
                component2.setxPos(this.getX());
                component2.setyPos(this.getY() + height + 1);
                height += component2.getDimension().height;
                component2.getDimension().setSize(this.getDimension().width, component2.getDimension().height);
            }
        }
        this.getDimension().setSize(this.getDimension().width, height);
        super.render(x, y);
    }

    @Override
    public void onUpdate() {
        int height = this.buttonHeight;
        if (this.maximized) {
            for (Component component2 : this.getComponents()) {
                component2.setyPos(this.getY() + height + 1);
                height += component2.getDimension().height;
                component2.getDimension().setSize(this.getDimension().width, component2.getDimension().height);
            }
        }
        this.getDimension().setSize(this.getDimension().width, height);
    }

    @Override
    public void onKeyPressed(int key, char character) {
        for (Component component2 : this.getComponents()) {
            component2.onKeyPressed(key, character);
        }
    }

    @Override
    public void onKeyReleased(int key, char character) {
        for (Component component2 : this.getComponents()) {
            component2.onKeyReleased(key, character);
        }
    }

    @Override
    public void onMouseDrag(int x, int y) {
        if (this.isMouseOver(x, y)) {
            for (Component component2 : this.getComponents()) {
                if (!component2.isMouseOver(x, y)) continue;
                component2.onMouseDrag(x, y);
            }
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int buttonID) {
        if (this.isMouseOver(x, y)) {
            for (Component component2 : this.getComponents()) {
                if (!component2.isMouseOver(x, y)) continue;
                component2.onMouseRelease(x, y, buttonID);
            }
        }
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        block5: {
            block3: {
                block4: {
                    if (!this.isMouseOverButton(x, y)) break block3;
                    if (buttonID != 0) break block4;
                    this.enabled = !this.enabled;
                    for (ComponentClickListener listener : this.listeners) {
                        listener.onComponenetClick(this, buttonID);
                    }
                    break block5;
                }
                if (buttonID != 1) break block5;
                this.maximized = !this.maximized;
                break block5;
            }
            if (this.isMouseOver(x, y)) {
                for (Component component2 : this.getComponents()) {
                    if (!component2.isMouseOver(x, y)) continue;
                    component2.onMousePress(x, y, buttonID);
                }
            }
        }
    }

    public boolean isMouseOverButton(int x, int y) {
        return x >= this.getX() && y >= this.getY() && x <= this.getX() + this.getDimension().width && y <= this.getY() + this.buttonHeight;
    }

    public void addListner(ComponentClickListener listener) {
        this.listeners.add(listener);
    }

    public ArrayList<ComponentClickListener> getListeners() {
        return this.listeners;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isMaximized() {
        return this.maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public int getButtonHeight() {
        return this.buttonHeight;
    }

    public void setButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public Hack getMod() {
        return this.hack;
    }
}

