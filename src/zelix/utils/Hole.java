/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package zelix.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Hole
extends Vec3i {
    private BlockPos blockPos;
    private boolean tall;
    private HoleTypes HoleType;

    public Hole(int x, int y, int z, BlockPos pos, HoleTypes p_Type) {
        super(x, y, z);
        this.blockPos = pos;
        this.SetHoleType(p_Type);
    }

    public Hole(int x, int y, int z, BlockPos pos, HoleTypes p_Type, boolean tall) {
        super(x, y, z);
        this.blockPos = pos;
        this.tall = true;
        this.SetHoleType(p_Type);
    }

    public boolean isTall() {
        return this.tall;
    }

    public BlockPos GetBlockPos() {
        return this.blockPos;
    }

    public HoleTypes GetHoleType() {
        return this.HoleType;
    }

    public void SetHoleType(HoleTypes holeType) {
        this.HoleType = holeType;
    }

    public static enum HoleTypes {
        None,
        Normal,
        Obsidian,
        Bedrock;

    }
}

