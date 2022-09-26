/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.util.vector.Vector3f
 */
package zelix.utils;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class PlayerUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float getDirection() {
        float yaw = PlayerUtil.mc.thePlayer.rotationYaw;
        if (PlayerUtil.mc.thePlayer.field_191988_bg < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (PlayerUtil.mc.thePlayer.field_191988_bg < 0.0f) {
            forward = -0.5f;
        } else if (PlayerUtil.mc.thePlayer.field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= (float)Math.PI / 180;
    }

    public static void forward(double length) {
        double yaw = Math.toRadians(PlayerUtil.mc.thePlayer.rotationYaw);
        PlayerUtil.mc.thePlayer.setPosition(PlayerUtil.mc.thePlayer.posX + -Math.sin(yaw) * length, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }

    public static void toFwd(double speed) {
        float yaw = PlayerUtil.mc.thePlayer.rotationYaw * ((float)Math.PI / 180);
        PlayerUtil.mc.thePlayer.motionX -= (double)MathHelper.func_76126_a((float)yaw) * speed;
        PlayerUtil.mc.thePlayer.motionZ += (double)MathHelper.func_76134_b((float)yaw) * speed;
    }

    public static void setSpeed(double speed) {
        PlayerUtil.mc.thePlayer.motionX = -Math.sin(PlayerUtil.getDirection()) * speed;
        PlayerUtil.mc.thePlayer.motionZ = Math.cos(PlayerUtil.getDirection()) * speed;
    }

    public static double getSpeed() {
        return Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return PlayerUtil.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0, inPlayer.posZ));
    }

    public static Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.func_180495_p(pos).getBlock();
    }

    public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
        return PlayerUtil.getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }

    public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        double d;
        ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
        Minecraft mc = Minecraft.getMinecraft();
        double posX = tpX - mc.thePlayer.posX;
        double posY = tpY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() + 1.1);
        double posZ = tpZ - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0 / Math.PI);
        double tmpX = mc.thePlayer.posX;
        double tmpY = mc.thePlayer.posY;
        double tmpZ = mc.thePlayer.posZ;
        double steps = 1.0;
        for (d = speed; d < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            steps += 1.0;
        }
        for (d = speed; d < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            tmpX = mc.thePlayer.posX - Math.sin(PlayerUtil.getDirection(yaw)) * d;
            tmpZ = mc.thePlayer.posZ + Math.cos(PlayerUtil.getDirection(yaw)) * d;
            positions.add(new Vector3f((float)tmpX, (float)(tmpY -= (mc.thePlayer.posY - tpY) / steps), (float)tmpZ));
        }
        positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
        return positions;
    }

    public static float getDirection(float yaw) {
        if (Minecraft.getMinecraft().thePlayer.field_191988_bg < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.field_191988_bg < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.getMinecraft().thePlayer.field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= (float)Math.PI / 180;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return MathHelper.func_76133_a((double)(d0 * d0 + d1 * d1 + d2 * d2));
    }

    public static boolean MovementInput() {
        return PlayerUtil.mc.gameSettings.keyBindForward.isPressed() || PlayerUtil.mc.gameSettings.keyBindLeft.isPressed() || PlayerUtil.mc.gameSettings.keyBindRight.isPressed() || PlayerUtil.mc.gameSettings.keyBindBack.isPressed();
    }
}

