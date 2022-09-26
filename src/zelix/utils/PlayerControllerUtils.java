/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.GameType
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.utils;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;

public class PlayerControllerUtils {
    public static void setReach(Entity entity, double range) {
        Minecraft mc = Wrapper.INSTANCE.mc();
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (player == entity) {
            class RangePlayerController
            extends PlayerControllerMP {
                private float range = (float)Wrapper.INSTANCE.player().getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

                public RangePlayerController(Minecraft mcIn, NetHandlerPlayClient netHandler) {
                    super(mcIn, netHandler);
                }

                public float getBlockReachDistance() {
                    return this.range;
                }

                public void setBlockReachDistance(float range) {
                    this.range = range;
                }
            }
            if (!(Wrapper.INSTANCE.mc().playerController instanceof RangePlayerController)) {
                GameType gameType = (GameType)ReflectionHelper.getPrivateValue(PlayerControllerMP.class, (Object)Wrapper.INSTANCE.mc().playerController, (String[])new String[]{Mapping.currentGameType});
                NetHandlerPlayClient netClient = (NetHandlerPlayClient)ReflectionHelper.getPrivateValue(PlayerControllerMP.class, (Object)Wrapper.INSTANCE.mc().playerController, (String[])new String[]{Mapping.connection});
                RangePlayerController controller = new RangePlayerController(mc, netClient);
                boolean isFlying = player.capabilities.isFlying;
                boolean allowFlying = player.capabilities.allowFlying;
                controller.func_78746_a(gameType);
                player.capabilities.isFlying = isFlying;
                player.capabilities.allowFlying = allowFlying;
                Wrapper.INSTANCE.mc().playerController = controller;
            }
            ((RangePlayerController)Wrapper.INSTANCE.mc().playerController).setBlockReachDistance((float)range);
        }
    }

    public static void setIsHittingBlock(boolean isHittingBlock) {
        try {
            Field field = PlayerControllerMP.class.getDeclaredField(Mapping.isHittingBlock);
            field.setAccessible(true);
            field.setBoolean(Wrapper.INSTANCE.controller(), isHittingBlock);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void setBlockHitDelay(int blockHitDelay) {
        try {
            Field field = PlayerControllerMP.class.getDeclaredField(Mapping.blockHitDelay);
            field.setAccessible(true);
            field.setInt(Wrapper.INSTANCE.controller(), blockHitDelay);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static float getCurBlockDamageMP() {
        float getCurBlockDamageMP = 0.0f;
        try {
            Field field = PlayerControllerMP.class.getDeclaredField(Mapping.curBlockDamageMP);
            field.setAccessible(true);
            getCurBlockDamageMP = field.getFloat(Wrapper.INSTANCE.controller());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return getCurBlockDamageMP;
    }

    public static boolean isMoving2() {
        return Wrapper.INSTANCE.player().field_191988_bg != 0.0f || Wrapper.INSTANCE.player().moveStrafing != 0.0f;
    }

    public static boolean isMoving() {
        if (!Wrapper.INSTANCE.player().isCollidedHorizontally && !Wrapper.INSTANCE.player().isSneaking()) {
            return Wrapper.INSTANCE.player().movementInput.field_192832_b != 0.0f || Wrapper.INSTANCE.player().movementInput.moveStrafe != 0.0f;
        }
        return false;
    }
}

