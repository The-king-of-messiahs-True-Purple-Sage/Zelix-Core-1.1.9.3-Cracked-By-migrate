/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 */
package zelix.utils;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import zelix.utils.Wrapper;

public class BlockData {
    public final BlockPos position;
    public final EnumFacing face;

    public BlockData(BlockPos add, EnumFacing up) {
        this.position = add;
        this.face = up;
    }

    public static BlockData getBlockData(BlockPos pos) {
        if (Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, -1, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(-1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, -1)).getBlock() != Blocks.air) {
            return new BlockData(pos.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        if (Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, 1)).getBlock() != Blocks.air) {
            return new BlockData(pos.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
}

