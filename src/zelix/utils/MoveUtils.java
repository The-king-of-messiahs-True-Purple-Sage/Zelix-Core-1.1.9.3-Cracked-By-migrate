/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.MobEffects
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 */
package zelix.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zelix.utils.Wrapper;

public class MoveUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static double defaultSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(MobEffects.field_76424_c)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(MobEffects.field_76424_c).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static void strafe(double speed) {
        float a = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180);
        float l = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) - 4.712389f;
        float r = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) + 4.712389f;
        float rf = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) + 0.5969026f;
        float lf = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) + -0.5969026f;
        float lb = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) - 2.3876104f;
        float rb = Wrapper.INSTANCE.mc().thePlayer.rotationYaw * ((float)Math.PI / 180) - -2.3876104f;
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindForward.isPressed()) {
            if (Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed()) {
                Wrapper.INSTANCE.mc().thePlayer.motionX -= (double)MathHelper.func_76126_a((float)lf) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ += (double)MathHelper.func_76134_b((float)lf) * speed;
            } else if (Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed()) {
                Wrapper.INSTANCE.mc().thePlayer.motionX -= (double)MathHelper.func_76126_a((float)rf) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ += (double)MathHelper.func_76134_b((float)rf) * speed;
            } else {
                Wrapper.INSTANCE.mc().thePlayer.motionX -= (double)MathHelper.func_76126_a((float)a) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ += (double)MathHelper.func_76134_b((float)a) * speed;
            }
        } else if (Wrapper.INSTANCE.mc().gameSettings.keyBindBack.isPressed()) {
            if (Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed()) {
                Wrapper.INSTANCE.mc().thePlayer.motionX -= (double)MathHelper.func_76126_a((float)lb) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ += (double)MathHelper.func_76134_b((float)lb) * speed;
            } else if (Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed()) {
                Wrapper.INSTANCE.mc().thePlayer.motionX -= (double)MathHelper.func_76126_a((float)rb) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ += (double)MathHelper.func_76134_b((float)rb) * speed;
            } else {
                Wrapper.INSTANCE.mc().thePlayer.motionX += (double)MathHelper.func_76126_a((float)a) * speed;
                Wrapper.INSTANCE.mc().thePlayer.motionZ -= (double)MathHelper.func_76134_b((float)a) * speed;
            }
        } else if (Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindForward.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindBack.isPressed()) {
            Wrapper.INSTANCE.mc().thePlayer.motionX += (double)MathHelper.func_76126_a((float)l) * speed;
            Wrapper.INSTANCE.mc().thePlayer.motionZ -= (double)MathHelper.func_76134_b((float)l) * speed;
        } else if (Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindForward.isPressed() && !Wrapper.INSTANCE.mc().gameSettings.keyBindBack.isPressed()) {
            Wrapper.INSTANCE.mc().thePlayer.motionX += (double)MathHelper.func_76126_a((float)r) * speed;
            Wrapper.INSTANCE.mc().thePlayer.motionZ -= (double)MathHelper.func_76134_b((float)r) * speed;
        }
    }

    public static void setMotion(double speed) {
        double forward = Wrapper.INSTANCE.mc().thePlayer.movementInput.field_192832_b;
        double strafe = Wrapper.INSTANCE.mc().thePlayer.movementInput.moveStrafe;
        float yaw = Wrapper.INSTANCE.mc().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Wrapper.INSTANCE.mc().thePlayer.motionX = 0.0;
            Wrapper.INSTANCE.mc().thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            Wrapper.INSTANCE.mc().thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            Wrapper.INSTANCE.mc().thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static boolean checkTeleport(double x, double y, double z, double distBetweenPackets) {
        double distx = Wrapper.INSTANCE.mc().thePlayer.posX - x;
        double disty = Wrapper.INSTANCE.mc().thePlayer.posY - y;
        double distz = Wrapper.INSTANCE.mc().thePlayer.posZ - z;
        double dist = Math.sqrt(Wrapper.INSTANCE.mc().thePlayer.getDistanceSq(x, y, z));
        double distanceEntreLesPackets = distBetweenPackets;
        double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1L;
        double xtp = Wrapper.INSTANCE.mc().thePlayer.posX;
        double ytp = Wrapper.INSTANCE.mc().thePlayer.posY;
        double ztp = Wrapper.INSTANCE.mc().thePlayer.posZ;
        int i = 1;
        while ((double)i < nbPackets) {
            AxisAlignedBB bb;
            double xdi = (x - Wrapper.INSTANCE.mc().thePlayer.posX) / nbPackets;
            double ydi = (y - Wrapper.INSTANCE.mc().thePlayer.posY) / nbPackets;
            double zdi = (z - Wrapper.INSTANCE.mc().thePlayer.posZ) / nbPackets;
            if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb = new AxisAlignedBB((xtp += xdi) - 0.3, ytp += ydi, (ztp += zdi) - 0.3, xtp + 0.3, ytp + 1.8, ztp + 0.3)).isEmpty()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public static boolean isOnGround(double height) {
        return !Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().thePlayer.func_174813_aQ().func_72317_d(0.0, -height, 0.0)).isEmpty();
    }

    public static int getJumpEffect() {
        if (Wrapper.INSTANCE.mc().thePlayer.isPotionActive(MobEffects.field_76430_j)) {
            return Wrapper.INSTANCE.mc().thePlayer.getActivePotionEffect(MobEffects.field_76430_j).getAmplifier() + 1;
        }
        return 0;
    }

    public static int getSpeedEffect() {
        if (Wrapper.INSTANCE.mc().thePlayer.isPotionActive(MobEffects.field_76424_c)) {
            return Wrapper.INSTANCE.mc().thePlayer.getActivePotionEffect(MobEffects.field_76424_c).getAmplifier() + 1;
        }
        return 0;
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return Minecraft.getMinecraft().theWorld.func_180495_p(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }

    public static Block getBlockAtPosC(double x, double y, double z) {
        EntityPlayerSP inPlayer = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.func_180495_p(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }

    public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = (double)block.func_177958_n() + 0.5 - Wrapper.INSTANCE.mc().thePlayer.posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.func_177952_p() + 0.5 - Wrapper.INSTANCE.mc().thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.func_177956_o() + 0.5;
        double d1 = Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.func_76133_a((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static boolean isBlockAboveHead() {
        AxisAlignedBB bb = new AxisAlignedBB(Wrapper.INSTANCE.mc().thePlayer.posX - 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight(), Wrapper.INSTANCE.mc().thePlayer.posZ + 0.3, Wrapper.INSTANCE.mc().thePlayer.posX + 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + 2.5, Wrapper.INSTANCE.mc().thePlayer.posZ - 0.3);
        return !Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb).isEmpty();
    }

    public static boolean isCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(Wrapper.INSTANCE.mc().thePlayer.posX - 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + 2.0, Wrapper.INSTANCE.mc().thePlayer.posZ + 0.3, Wrapper.INSTANCE.mc().thePlayer.posX + 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + 3.0, Wrapper.INSTANCE.mc().thePlayer.posZ - 0.3);
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.3 + dist, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(-0.3 - dist, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.0, 0.0, 0.3 + dist)).isEmpty()) {
            return true;
        }
        return !Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.0, 0.0, -0.3 - dist)).isEmpty();
    }

    public static boolean isRealCollidedH(double dist) {
        AxisAlignedBB bb = new AxisAlignedBB(Wrapper.INSTANCE.mc().thePlayer.posX - 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + 0.5, Wrapper.INSTANCE.mc().thePlayer.posZ + 0.3, Wrapper.INSTANCE.mc().thePlayer.posX + 0.3, Wrapper.INSTANCE.mc().thePlayer.posY + 1.9, Wrapper.INSTANCE.mc().thePlayer.posZ - 0.3);
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.3 + dist, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(-0.3 - dist, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        if (!Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.0, 0.0, 0.3 + dist)).isEmpty()) {
            return true;
        }
        return !Wrapper.INSTANCE.mc().theWorld.func_184144_a((Entity)Wrapper.INSTANCE.mc().thePlayer, bb.func_72317_d(0.0, 0.0, -0.3 - dist)).isEmpty();
    }

    public static boolean isMoving() {
        return Wrapper.INSTANCE.mc().thePlayer != null && (Wrapper.INSTANCE.mc().thePlayer.movementInput.field_192832_b != 0.0f || Wrapper.INSTANCE.mc().thePlayer.movementInput.moveStrafe != 0.0f);
    }

    public static void strafeHYT(float speed) {
        if (!MoveUtils.isMoving()) {
            return;
        }
        double yaw = MoveUtils.getDirection();
        Wrapper.INSTANCE.mc().thePlayer.motionX = -Math.sin(yaw) * (double)speed;
        Wrapper.INSTANCE.mc().thePlayer.motionZ = Math.cos(yaw) * (double)speed;
    }

    public static double getDirection() {
        float rotationYaw = Wrapper.INSTANCE.mc().thePlayer.rotationYaw;
        if (Wrapper.INSTANCE.mc().thePlayer.field_191988_bg < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (Wrapper.INSTANCE.mc().thePlayer.field_191988_bg < 0.0f) {
            forward = -0.5f;
        } else if (Wrapper.INSTANCE.mc().thePlayer.field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Wrapper.INSTANCE.mc().thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (Wrapper.INSTANCE.mc().thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static void strafe() {
        MoveUtils.strafe(MoveUtils.getSpeed());
    }

    public static float getSpeed() {
        return (float)Math.sqrt(Wrapper.INSTANCE.mc().thePlayer.motionX * Wrapper.INSTANCE.mc().thePlayer.motionX + Wrapper.INSTANCE.mc().thePlayer.motionZ * Wrapper.INSTANCE.mc().thePlayer.motionZ);
    }
}

