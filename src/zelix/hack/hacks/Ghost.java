/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 */
package zelix.hack.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;

public class Ghost
extends Hack {
    public BooleanValue noWalls = new BooleanValue("NoWalls", true);

    public Ghost() {
        super("Ghost", HackCategory.PLAYER);
        this.addValue(this.noWalls);
    }

    @Override
    public String getDescription() {
        return "Allows you to pass through walls.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        boolean skip = true;
        if (((Boolean)this.noWalls.getValue()).booleanValue() && side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
            skip = false;
        }
        return skip;
    }

    @Override
    public void onDisable() {
        Wrapper.INSTANCE.player().noClip = false;
        if (((Boolean)this.noWalls.getValue()).booleanValue()) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().func_174813_aQ().field_72338_b, Wrapper.INSTANCE.player().posZ, Wrapper.INSTANCE.player().cameraYaw, Wrapper.INSTANCE.player().cameraPitch, Wrapper.INSTANCE.player().onGround));
        }
        super.onDisable();
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        float speed;
        Wrapper.INSTANCE.player().noClip = true;
        Wrapper.INSTANCE.player().fallDistance = 0.0f;
        Wrapper.INSTANCE.player().onGround = true;
        Wrapper.INSTANCE.player().capabilities.isFlying = false;
        Wrapper.INSTANCE.player().motionX = 0.0;
        Wrapper.INSTANCE.player().motionY = 0.0;
        Wrapper.INSTANCE.player().motionZ = 0.0;
        Wrapper.INSTANCE.player().jumpMovementFactor = speed = 0.2f;
        if (Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
            Wrapper.INSTANCE.player().motionY += (double)speed;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
            Wrapper.INSTANCE.player().motionY -= (double)speed;
        }
        super.onLivingUpdate(event);
    }
}

