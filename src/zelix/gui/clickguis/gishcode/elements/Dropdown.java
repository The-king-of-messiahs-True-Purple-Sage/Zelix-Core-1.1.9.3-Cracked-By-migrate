/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.elements;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.base.Container;

public class Dropdown
extends Container {
    private boolean maximized = false;
    private int dropdownHeight;

    public Dropdown(int xPos, int yPos, int width, int dropdownHeight, Component component2, String text) {
        super(xPos, yPos, width, 0, ComponentType.DROPDOWN, component2, text);
        this.dropdownHeight = dropdownHeight;
    }

    @Override
    public void render(int x, int y) {
        int height = this.dropdownHeight;
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
        int height = this.dropdownHeight;
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
        if (x >= this.getX() && y >= this.getY() && x <= this.getX() + this.getDimension().width && y <= this.getY() + this.dropdownHeight) {
            if (buttonID == 1) {
                this.maximized = !this.maximized;
            }
        } else if (this.isMouseOver(x, y)) {
            for (Component component2 : this.getComponents()) {
                if (!component2.isMouseOver(x, y)) continue;
                component2.onMousePress(x, y, buttonID);
            }
        }
    }

    public boolean isMaximized() {
        return this.maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public int getDropdownHeight() {
        return this.dropdownHeight;
    }

    public void setDropdownHeight(int dropdownHeight) {
        this.dropdownHeight = dropdownHeight;
    }
}

