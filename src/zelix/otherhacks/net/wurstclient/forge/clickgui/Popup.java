/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;

public abstract class Popup {
    private final Component owner;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean closing;

    public Popup(Component owner) {
        this.owner = owner;
    }

    public abstract void handleMouseClick(int var1, int var2, int var3);

    public abstract void render(int var1, int var2);

    public abstract int getDefaultWidth();

    public abstract int getDefaultHeight();

    public Component getOwner() {
        return this.owner;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isClosing() {
        return this.closing;
    }

    public void close() {
        this.closing = true;
    }
}

