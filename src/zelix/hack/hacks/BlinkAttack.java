/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.world.World
 */
package zelix.hack.hacks;

import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Entity301;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;

public class BlinkAttack
extends Hack {
    public Entity301 entity301 = null;
    Queue<CPacketUseEntity> packets = new LinkedList<CPacketUseEntity>();
    boolean send = false;

    public BlinkAttack() {
        super("BlinkAttack", HackCategory.COMBAT);
    }

    @Override
    public String getDescription() {
        return "Allows you to move without sending it to the server.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketUseEntity) {
            ChatUtils.message("AttackPackets:" + String.valueOf(this.packets.size()));
            this.send = false;
            this.packets.add((CPacketUseEntity)packet);
            return this.send;
        }
        this.send = true;
        return this.send;
    }

    @Override
    public void onEnable() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.entity301 = new Entity301((World)Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.entity301.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.entity301.field_71071_by = Wrapper.INSTANCE.inventory();
            this.entity301.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.entity301.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.entity301.field_70759_as = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntityInWorld((Entity)this.entity301);
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        while (!this.packets.isEmpty()) {
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)this.packets.poll());
        }
        if (this.entity301 != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().removeEntity((Entity)this.entity301);
            this.entity301 = null;
        }
        super.onDisable();
    }
}

