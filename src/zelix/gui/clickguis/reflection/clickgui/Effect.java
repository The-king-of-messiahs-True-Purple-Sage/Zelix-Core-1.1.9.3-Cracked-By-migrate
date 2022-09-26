/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 */
package zelix.gui.clickguis.reflection.clickgui;

import java.util.Random;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Effect {
    private int _x;
    private int _y;
    private int _fallingSpeed;
    private int _size;

    public Effect(int x, int y, int fallingSpeed, int size) {
        this._x = x;
        this._y = y;
        this._fallingSpeed = fallingSpeed;
        this._size = size;
    }

    public int getX() {
        return this._x;
    }

    public void setX(int x) {
        this._x = x;
    }

    public int getY() {
        return this._y;
    }

    public void setY(int _y) {
        this._y = _y;
    }

    public void Update(ScaledResolution res) {
        Gui.drawRect((int)this.getX(), (int)this.getY(), (int)(this.getX() + this._size), (int)(this.getY() + this._size), (int)-1714829883);
        this.setY(this.getY() + this._fallingSpeed);
        if (this.getY() > res.getScaledHeight() + 10 || this.getY() < -10) {
            this.setY(-10);
            Random rand = new Random();
            this._fallingSpeed = rand.nextInt(10) + 1;
            this._size = rand.nextInt(4) + 1;
        }
    }
}

