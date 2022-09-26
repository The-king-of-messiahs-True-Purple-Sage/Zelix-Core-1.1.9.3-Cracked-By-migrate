/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 */
package zelix.hack.hacks;

import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;

public class Blink
extends Hack {
    Queue<CPacketPlayer> packets = new LinkedList<CPacketPlayer>();
    boolean send = false;

    public Blink() {
        super("Blink", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Allows you to move without sending it to the server.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
            ChatUtils.message("Packets:" + String.valueOf(this.packets.size()));
            this.send = false;
            this.packets.add((CPacketPlayer)packet);
            return this.send;
        }
        this.send = true;
        return this.send;
    }

    @Override
    public void onEnable() {
        if (Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() != null) {
            // empty if block
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        while (!this.packets.isEmpty()) {
            Wrapper.INSTANCE.sendPacket((Packet)this.packets.poll());
        }
        super.onDisable();
    }
}

