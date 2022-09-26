/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.math.BlockPos
 */
package zelix.hack.hacks.automine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import zelix.utils.Wrapper;

public class BlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static Block getBlock(BlockPos pos) {
        return Wrapper.INSTANCE.mc().theWorld.func_180495_p(pos).getBlock();
    }

    public static int getBlockMeta(BlockPos pos) {
        IBlockState blockState = Wrapper.INSTANCE.mc().theWorld.func_180495_p(pos);
        return blockState.getBlock().getMetaFromState(blockState);
    }

    public static void faceBlockClient(BlockPos blockPos) {
        double diffX = (double)blockPos.func_177958_n() + 0.5 - Wrapper.INSTANCE.mc().thePlayer.posX;
        double diffY = (double)blockPos.func_177956_o() + 0.0 - (Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight());
        double diffZ = (double)blockPos.func_177952_p() + 0.5 - Wrapper.INSTANCE.mc().thePlayer.posZ;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        Wrapper.INSTANCE.mc().thePlayer.rotationYaw += BlockUtils.wrapDegrees(yaw - Wrapper.INSTANCE.mc().thePlayer.rotationYaw);
        Wrapper.INSTANCE.mc().thePlayer.rotationPitch += BlockUtils.wrapDegrees(pitch - Wrapper.INSTANCE.mc().thePlayer.rotationPitch);
    }

    public static float getNeaestPlayerBlockDistance(double posX, double posY, double posZ) {
        return BlockUtils.getBlockDistance((float)(Wrapper.INSTANCE.mc().thePlayer.posX - posX), (float)(Wrapper.INSTANCE.mc().thePlayer.posY + 1.0 - posY), (float)(Wrapper.INSTANCE.mc().thePlayer.posZ - posZ));
    }

    public static float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return (float)Math.sqrt((xDiff - 0.5f) * (xDiff - 0.5f) + (yDiff - 0.5f) * (yDiff - 0.5f) + (zDiff - 0.5f) * (zDiff - 0.5f));
    }

    public static float wrapDegrees(float value) {
        if ((value %= 360.0f) >= 180.0f) {
            value -= 360.0f;
        }
        if (value < -180.0f) {
            value += 360.0f;
        }
        return value;
    }
}

