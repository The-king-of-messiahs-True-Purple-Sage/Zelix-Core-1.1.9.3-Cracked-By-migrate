/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package zelix.utils;

import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public final class BlockUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static IBlockState getState(BlockPos pos) {
        return Wrapper.INSTANCE.world().func_180495_p(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return BlockUtils.getState(pos).getBlock();
    }

    public static Material getMaterial(BlockPos pos) {
        return BlockUtils.getState(pos).func_185904_a();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockUtils.getBlock(pos).canCollideCheck(BlockUtils.getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return BlockUtils.getState(pos).func_185903_a((EntityPlayer)Wrapper.INSTANCE.player(), (World)Wrapper.INSTANCE.world(), pos);
    }

    public static boolean isBlockMaterial(BlockPos blockPos, Block block) {
        return BlockUtils.getBlock(blockPos) == Blocks.air;
    }

    public static boolean isBlockMaterial(BlockPos blockPos, Material material) {
        return BlockUtils.getState(blockPos).func_185904_a() == material;
    }

    public static boolean placeBlockLegit(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec;
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.getOpposite();
            if (eyesPos.func_72436_e(new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5)) >= eyesPos.func_72436_e(new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5)) || !BlockUtils.getBlock(neighbor).canCollideCheck(Wrapper.INSTANCE.world().func_180495_p(neighbor), false) || eyesPos.func_72436_e(hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5))) > 18.0625) continue;
            BlockUtils.faceVectorPacket(hitVec);
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        Utils.swingMainHand();
        return true;
    }

    public static boolean placeBlockSimple(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec;
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockUtils.getBlock(neighbor).canCollideCheck(BlockUtils.getState(neighbor), false) || eyesPos.func_72436_e(hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5))) > 36.0) continue;
            Wrapper.INSTANCE.controller().func_187099_a(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.world(), neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    public static void faceVectorPacket(Vec3d vec) {
        double diffX = vec.field_72450_a - Wrapper.INSTANCE.player().posX;
        double diffY = vec.field_72448_b - (Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight());
        double diffZ = vec.field_72449_c - Wrapper.INSTANCE.player().posZ;
        double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, dist)));
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new CPacketPlayer.Rotation(Wrapper.INSTANCE.player().rotationYaw + MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.player().rotationYaw)), Wrapper.INSTANCE.player().rotationPitch + MathHelper.func_76142_g((float)(pitch - Wrapper.INSTANCE.player().rotationPitch)), Wrapper.INSTANCE.player().onGround));
    }

    public static void faceBlockClient(BlockPos blockPos) {
        double diffX = (double)blockPos.func_177958_n() + 0.5 - Wrapper.INSTANCE.player().posX;
        double diffY = (double)blockPos.func_177956_o() + 0.0 - (Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight());
        double diffZ = (double)blockPos.func_177952_p() + 0.5 - Wrapper.INSTANCE.player().posZ;
        double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        Wrapper.INSTANCE.player().rotationYaw += MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.player().rotationYaw));
        Wrapper.INSTANCE.player().rotationPitch += MathHelper.func_76142_g((float)(pitch - Wrapper.INSTANCE.player().rotationPitch));
    }

    public static void faceBlockPacket(BlockPos blockPos) {
        double diffX = (double)blockPos.func_177958_n() + 0.5 - Wrapper.INSTANCE.player().posX;
        double diffY = (double)blockPos.func_177956_o() + 0.0 - (Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight());
        double diffZ = (double)blockPos.func_177952_p() + 0.5 - Wrapper.INSTANCE.player().posZ;
        double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new CPacketPlayer.Rotation(Wrapper.INSTANCE.player().rotationYaw + MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.player().rotationYaw)), Wrapper.INSTANCE.player().rotationPitch + MathHelper.func_76142_g((float)(pitch - Wrapper.INSTANCE.player().rotationPitch)), Wrapper.INSTANCE.player().onGround));
    }

    public static void faceBlockClientHorizontally(BlockPos blockPos) {
        double diffX = (double)blockPos.func_177958_n() + 0.5 - Wrapper.INSTANCE.player().posX;
        double diffZ = (double)blockPos.func_177952_p() + 0.5 - Wrapper.INSTANCE.player().posZ;
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        Wrapper.INSTANCE.player().rotationYaw += MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.player().rotationYaw));
    }

    public static float getPlayerBlockDistance(BlockPos blockPos) {
        return BlockUtils.getPlayerBlockDistance(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
    }

    public static float getPlayerBlockDistance(double posX, double posY, double posZ) {
        float xDiff = (float)(Wrapper.INSTANCE.player().posX - posX);
        float yDiff = (float)(Wrapper.INSTANCE.player().posY - posY);
        float zDiff = (float)(Wrapper.INSTANCE.player().posZ - posZ);
        return BlockUtils.getBlockDistance(xDiff, yDiff, zDiff);
    }

    public static float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return MathHelper.func_76129_c((float)((xDiff - 0.5f) * (xDiff - 0.5f) + (yDiff - 0.5f) * (yDiff - 0.5f) + (zDiff - 0.5f) * (zDiff - 0.5f)));
    }

    public static float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
        float xDiff = (float)(Wrapper.INSTANCE.player().posX - (double)blockPos.func_177958_n());
        float zDiff = (float)(Wrapper.INSTANCE.player().posZ - (double)blockPos.func_177952_p());
        return MathHelper.func_76129_c((float)((xDiff - 0.5f) * (xDiff - 0.5f) + (zDiff - 0.5f) * (zDiff - 0.5f)));
    }

    public static boolean breakBlockSimple(BlockPos pos) {
        int i;
        EnumFacing side = null;
        EnumFacing[] sides = EnumFacing.values();
        Vec3d eyesPos = Utils.getEyesPos();
        Vec3d relCenter = BlockUtils.getState(pos).func_185900_c((IBlockAccess)Wrapper.INSTANCE.world(), pos).func_189972_c();
        Vec3d center = new Vec3d((Vec3i)pos).func_178787_e(relCenter);
        Vec3d[] hitVecs = new Vec3d[sides.length];
        for (i = 0; i < sides.length; ++i) {
            Vec3i dirVec = sides[i].func_176730_m();
            Vec3d relHitVec = new Vec3d(relCenter.field_72450_a * (double)dirVec.func_177958_n(), relCenter.field_72448_b * (double)dirVec.func_177956_o(), relCenter.field_72449_c * (double)dirVec.func_177952_p());
            hitVecs[i] = center.func_178787_e(relHitVec);
        }
        for (i = 0; i < sides.length; ++i) {
            if (Wrapper.INSTANCE.world().func_147447_a(eyesPos, hitVecs[i], false, true, false) != null) continue;
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
        Utils.faceVectorPacket(hitVecs[side.ordinal()]);
        if (!Wrapper.INSTANCE.controller().func_180512_c(pos, side)) {
            return false;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        return true;
    }

    public static void breakBlocksPacketSpam(Iterable<BlockPos> blocks) {
        Vec3d eyesPos = Utils.getEyesPos();
        NetHandlerPlayClient connection = Wrapper.INSTANCE.player().sendQueue;
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

    public static LinkedList<BlockPos> findBlocksNearEntity(EntityLivingBase entity, int blockId, int blockMeta, int distance) {
        LinkedList<BlockPos> blocks = new LinkedList<BlockPos>();
        for (int x = (int)Wrapper.INSTANCE.player().posX - distance; x <= (int)Wrapper.INSTANCE.player().posX + distance; ++x) {
            for (int z = (int)Wrapper.INSTANCE.player().posZ - distance; z <= (int)Wrapper.INSTANCE.player().posZ + distance; ++z) {
                int height = Wrapper.INSTANCE.world().func_189649_b(x, z);
                for (int y = 0; y <= height; ++y) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    IBlockState blockState = Wrapper.INSTANCE.world().func_180495_p(blockPos);
                    if (blockId == -1 || blockMeta == -1) {
                        blocks.add(blockPos);
                        continue;
                    }
                    int id = Block.getIdFromBlock((Block)blockState.getBlock());
                    int meta = blockState.getBlock().getMetaFromState(blockState);
                    if (id != blockId || meta != blockMeta) continue;
                    blocks.add(blockPos);
                }
            }
        }
        return blocks;
    }

    public static boolean isInLiquid() {
        if (Wrapper.INSTANCE.mc().thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        int y = (int)Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().field_72338_b;
        for (int x = MathHelper.func_76128_c((double)Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().field_72340_a); x < MathHelper.func_76128_c((double)Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().field_72336_d) + 1; ++x) {
            for (int z = MathHelper.func_76128_c((double)Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().field_72339_c); z < MathHelper.func_76128_c((double)Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().field_72334_f) + 1; ++z) {
                Block block = Wrapper.INSTANCE.mc().theWorld.func_180495_p(new BlockPos(x, y, z)).getBlock();
                if (block == null || block == Blocks.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                inLiquid = true;
            }
        }
        return inLiquid;
    }

    public static BlockPos getHypixelBlockpos(String str) {
        int val = 89;
        if (str != null && str.length() > 1) {
            char[] chs = str.toCharArray();
            int lenght = chs.length;
            for (int i = 0; i < lenght; ++i) {
                val += chs[i] * str.length() * str.length() + str.charAt(0) + str.charAt(1);
            }
            val /= str.length();
        }
        return new BlockPos(val, -val % 255, val);
    }
}

