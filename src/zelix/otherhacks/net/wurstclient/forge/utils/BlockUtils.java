/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WPlayerController;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WVec3d;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class BlockUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static IBlockState getState(BlockPos pos) {
        return WMinecraft.getWorld().func_180495_p(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return BlockUtils.getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getIdFromBlock((Block)BlockUtils.getBlock(pos));
    }

    public static String getName(Block block) {
        return "" + Block.blockRegistry.func_177774_c((Object)block);
    }

    public static Material getMaterial(BlockPos pos) {
        return BlockUtils.getState(pos).func_185904_a();
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        return BlockUtils.getState(pos).func_185900_c((IBlockAccess)WMinecraft.getWorld(), pos).func_186670_a(pos);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockUtils.getBlock(pos).canCollideCheck(BlockUtils.getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return BlockUtils.getState(pos).func_185903_a((EntityPlayer)WMinecraft.getPlayer(), (World)WMinecraft.getWorld(), pos);
    }

    public static void placeBlockSimple(BlockPos pos) {
        Field rightClickDelayTimer;
        int i;
        EnumFacing side = null;
        EnumFacing[] sides = EnumFacing.values();
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.func_72436_e(posVec);
        Vec3d[] hitVecs = new Vec3d[sides.length];
        for (i = 0; i < sides.length; ++i) {
            hitVecs[i] = posVec.func_178787_e(new Vec3d(sides[i].func_176730_m()).func_186678_a(0.5));
        }
        for (i = 0; i < sides.length; ++i) {
            if (!BlockUtils.canBeClicked(pos.func_177972_a(sides[i])) || WMinecraft.getWorld().func_147447_a(eyesPos, hitVecs[i], false, true, false) != null) continue;
            side = sides[i];
            break;
        }
        if (side == null) {
            for (i = 0; i < sides.length; ++i) {
                if (!BlockUtils.canBeClicked(pos.func_177972_a(sides[i])) || distanceSqPosVec > eyesPos.func_72436_e(hitVecs[i])) continue;
                side = sides[i];
                break;
            }
        }
        if (side == null) {
            return;
        }
        Vec3d hitVec = hitVecs[side.ordinal()];
        RotationUtils.faceVectorPacket(hitVec);
        if (RotationUtils.getAngleToLastReportedLookVec(hitVec) > 1.0) {
            return;
        }
        try {
            rightClickDelayTimer = mc.getClass().getDeclaredField(ForgeWurst.getForgeWurst().isObfuscated() ? "field_71467_ac" : "rightClickDelayTimer");
            rightClickDelayTimer.setAccessible(true);
            if (rightClickDelayTimer.getInt(mc) > 0) {
                return;
            }
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        WPlayerController.processRightClickBlock(pos.func_177972_a(side), side.getOpposite(), hitVec);
        WMinecraft.getPlayer().sendQueue.addToSendQueue((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        try {
            rightClickDelayTimer = mc.getClass().getDeclaredField(ForgeWurst.getForgeWurst().isObfuscated() ? "field_71467_ac" : "rightClickDelayTimer");
            rightClickDelayTimer.setAccessible(true);
            rightClickDelayTimer.setInt(mc, 4);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean breakBlockSimple(BlockPos pos) {
        int i;
        EnumFacing side = null;
        EnumFacing[] sides = EnumFacing.values();
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d relCenter = BlockUtils.getState(pos).func_185900_c((IBlockAccess)WMinecraft.getWorld(), pos).func_189972_c();
        Vec3d center = new Vec3d((Vec3i)pos).func_178787_e(relCenter);
        Vec3d[] hitVecs = new Vec3d[sides.length];
        for (i = 0; i < sides.length; ++i) {
            Vec3i dirVec = sides[i].func_176730_m();
            Vec3d relHitVec = new Vec3d(WVec3d.getX(relCenter) * (double)dirVec.func_177958_n(), WVec3d.getY(relCenter) * (double)dirVec.func_177956_o(), WVec3d.getZ(relCenter) * (double)dirVec.func_177952_p());
            hitVecs[i] = center.func_178787_e(relHitVec);
        }
        for (i = 0; i < sides.length; ++i) {
            if (WMinecraft.getWorld().func_147447_a(eyesPos, hitVecs[i], false, true, false) != null) continue;
            side = sides[i];
            break;
        }
        if (side == null) {
            double distanceSqToCenter = eyesPos.func_72436_e(center);
            for (int i2 = 0; i2 < sides.length; ++i2) {
                if (eyesPos.func_72436_e(hitVecs[i2]) >= distanceSqToCenter) continue;
                side = sides[i2];
                break;
            }
        }
        if (side == null) {
            side = sides[0];
        }
        RotationUtils.faceVectorPacket(hitVecs[side.ordinal()]);
        if (!BlockUtils.mc.playerController.func_180512_c(pos, side)) {
            return false;
        }
        WMinecraft.getPlayer().sendQueue.addToSendQueue((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        return true;
    }

    public static void breakBlocksPacketSpam(Iterable<BlockPos> blocks) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        NetHandlerPlayClient connection = WMinecraft.getPlayer().sendQueue;
        block0: for (BlockPos pos : blocks) {
            Vec3d posVec = new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.func_72436_e(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3d hitVec = posVec.func_178787_e(new Vec3d(side.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) >= distanceSqPosVec) continue;
                connection.addToSendQueue((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                connection.addToSendQueue((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                continue block0;
            }
        }
    }
}

