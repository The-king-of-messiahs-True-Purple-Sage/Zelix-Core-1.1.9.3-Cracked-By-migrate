/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package zelix.hack.hacks;

import net.minecraft.util.math.BlockPos;

class EBlockPos
extends BlockPos {
    private int x;
    private int y;
    private int z;

    public EBlockPos() {
        super(0, 0, 0);
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int func_177958_n() {
        return this.x;
    }

    public int func_177956_o() {
        return this.y;
    }

    public int func_177952_p() {
        return this.z;
    }
}

