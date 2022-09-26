/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WVec3d;

@Mod.EventBusSubscriber
public final class RotationUtils {
    public static Vec3d getEyesPos() {
        return new Vec3d(WMinecraft.getPlayer().posX, WMinecraft.getPlayer().posY + (double)WMinecraft.getPlayer().getEyeHeight(), WMinecraft.getPlayer().posZ);
    }

    public static Vec3d getClientLookVec() {
        EntityPlayerSP player = WMinecraft.getPlayer();
        float f = MathHelper.func_76134_b((float)(-player.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f1 = MathHelper.func_76126_a((float)(-player.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f2 = -MathHelper.func_76134_b((float)(-player.rotationPitch * ((float)Math.PI / 180)));
        float f3 = MathHelper.func_76126_a((float)(-player.rotationPitch * ((float)Math.PI / 180)));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    private static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double diffX = WVec3d.getX(vec) - WVec3d.getX(eyesPos);
        double diffY = WVec3d.getY(vec) - WVec3d.getY(eyesPos);
        double diffZ = WVec3d.getZ(vec) - WVec3d.getZ(eyesPos);
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.func_76142_g((float)yaw), MathHelper.func_76142_g((float)pitch)};
    }

    public static double getAngleToLookVec(Vec3d vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        EntityPlayerSP player = WMinecraft.getPlayer();
        float currentYaw = MathHelper.func_76142_g((float)player.rotationYaw);
        float currentPitch = MathHelper.func_76142_g((float)player.rotationPitch);
        float diffYaw = currentYaw - needed[0];
        float diffPitch = currentPitch - needed[1];
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static double getAngleToLastReportedLookVec(Vec3d vec) {
        float lastReportedPitch;
        float lastReportedYaw;
        float[] needed = RotationUtils.getNeededRotations(vec);
        EntityPlayerSP player = WMinecraft.getPlayer();
        try {
            Field yawField = EntityPlayerSP.class.getDeclaredField(ForgeWurst.getForgeWurst().isObfuscated() ? "field_175164_bL" : "lastReportedYaw");
            yawField.setAccessible(true);
            lastReportedYaw = MathHelper.func_76142_g((float)yawField.getFloat(player));
            Field pitchField = EntityPlayerSP.class.getDeclaredField(ForgeWurst.getForgeWurst().isObfuscated() ? "field_175165_bM" : "lastReportedPitch");
            pitchField.setAccessible(true);
            lastReportedPitch = MathHelper.func_76142_g((float)pitchField.getFloat(player));
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        float diffYaw = lastReportedYaw - needed[0];
        float diffPitch = lastReportedPitch - needed[1];
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static void faceVectorPacket(Vec3d vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;
        float preYaw = pl.rotationYaw;
        float prePitch = pl.rotationPitch;
        pl.rotationYaw = rotations[0];
        pl.rotationPitch = rotations[1];
        try {
            Method onUpdateWalkingPlayer = pl.getClass().getDeclaredMethod(ForgeWurst.getForgeWurst().isObfuscated() ? "func_175161_p" : "onUpdateWalkingPlayer", new Class[0]);
            onUpdateWalkingPlayer.setAccessible(true);
            onUpdateWalkingPlayer.invoke((Object)pl, new Object[0]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        pl.rotationYaw = preYaw;
        pl.rotationPitch = prePitch;
    }

    public static void faceVectorForWalking(Vec3d vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        EntityPlayerSP player = WMinecraft.getPlayer();
        player.rotationYaw = needed[0];
        player.rotationPitch = 0.0f;
    }
}

