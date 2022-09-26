/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import zelix.otherhacks.net.wurstclient.forge.clickgui.Window;

public abstract class Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private Window parent;

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
    }

    public abstract void render(int var1, int var2, float var3);

    public abstract int getDefaultWidth();

    public abstract int getDefaultHeight();

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (this.x != x) {
            this.invalidateParent();
        }
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        if (this.y != y) {
            this.invalidateParent();
        }
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        if (this.width != width) {
            this.invalidateParent();
        }
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        if (this.height != height) {
            this.invalidateParent();
        }
        this.height = height;
    }

    public Window getParent() {
        return this.parent;
    }

    public void setParent(Window parent) {
        this.parent = parent;
    }

    private void invalidateParent() {
        if (this.parent != null) {
            this.parent.invalidate();
        }
    }
}

