/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package zelix.otherhacks.net.wurstclient.forge.compatibility;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public final class WPlayerController {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        WPlayerController.mc.playerController.func_187099_a(WMinecraft.getPlayer(), WMinecraft.getWorld(), pos, side, hitVec, EnumHand.MAIN_HAND);
    }
}

