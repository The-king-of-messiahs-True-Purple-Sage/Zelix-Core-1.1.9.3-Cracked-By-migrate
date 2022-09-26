/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3i
 */
package zelix.hack.hacks.xray.reference.block;

import net.minecraft.util.math.Vec3i;

public class BlockInfo
extends Vec3i {
    public int[] color;
    public double alpha;

    public BlockInfo(int x, int y, int z, int[] color, double alpha) {
        super(x, y, z);
        this.color = color;
        this.alpha = alpha;
    }

    public BlockInfo(Vec3i pos, int[] c, double alpha) {
        this(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), c, alpha);
    }
}

