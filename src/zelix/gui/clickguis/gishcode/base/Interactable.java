/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.base;

import java.awt.Dimension;

public class Interactable {
    private int xPos;
    private int yPos;
    private int yBase;
    private Dimension dimension;

    public Interactable(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = new Dimension(width, height);
    }

    public void onMousePress(int x, int y, int buttonID) {
    }

    public void onMouseRelease(int x, int y, int buttonID) {
    }

    public void onMouseDrag(int x, int y) {
    }

    public void onMouseScroll(int scroll) {
    }

    public boolean isMouseOver(int x, int y) {
        return x >= this.xPos && y >= this.yPos && x <= this.xPos + this.dimension.width && y <= this.yPos + this.dimension.height;
    }

    public void onKeyPressed(int key, char character) {
    }

    public void onKeyReleased(int key, char character) {
    }

    public int getX() {
        return this.xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getY() {
        return this.yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getyBase() {
        return this.yBase;
    }

    public void setyBase(int yBase) {
        this.yBase = yBase;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}

