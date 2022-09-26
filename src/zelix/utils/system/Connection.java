/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelDuplexHandler
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelPipeline
 *  io.netty.channel.ChannelPromise
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 */
package zelix.utils.system;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import zelix.EventsHandler;
import zelix.eventapi.EventManager;
import zelix.eventapi.event.EventPlayerPre;
import zelix.utils.ReflectionHelper;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;

public class Connection
extends ChannelDuplexHandler {
    private EventsHandler eventHandler;

    public Connection(EventsHandler eventHandler) {
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = Wrapper.INSTANCE.mc().getNetHandler().getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", (ChannelHandler)this);
        }
        catch (Exception exception) {
            ChatUtils.error("Connection: Error on attaching");
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if ((Packet)packet instanceof CPacketPlayer.PositionRotation) {
            CPacketPlayer.PositionRotation packetPlayer = (CPacketPlayer.PositionRotation)packet;
            double x = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "x", "field_149479_a");
            double y = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "y", "field_149477_b");
            double z = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "z", "field_149478_c");
            float yaw = ((Float)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "yaw", "field_149476_e")).floatValue();
            float pitch = ((Float)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "pitch", "field_149473_f")).floatValue();
            boolean onGroud = (Boolean)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "onGround", "field_149474_g");
            EventPlayerPre eventPlayerPre = new EventPlayerPre(x, y, z, yaw, pitch, Wrapper.INSTANCE.player().isSneaking(), onGroud);
            EventManager.call(eventPlayerPre);
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getX()), "x", "field_149479_a");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getY()), "y", "field_149477_b");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getZ()), "z", "field_149478_c");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Float.valueOf(eventPlayerPre.getYaw()), "yaw", "field_149476_e");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Float.valueOf(eventPlayerPre.getPitch()), "pitch", "field_149473_f");
        }
        if (!this.eventHandler.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if ((Packet)packet instanceof CPacketPlayer.PositionRotation) {
            CPacketPlayer.PositionRotation packetPlayer = (CPacketPlayer.PositionRotation)packet;
            double x = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "x", "field_149479_a");
            double y = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "y", "field_149477_b");
            double z = (Double)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "z", "field_149478_c");
            float yaw = ((Float)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "yaw", "field_149476_e")).floatValue();
            float pitch = ((Float)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "pitch", "field_149473_f")).floatValue();
            boolean onGroud = (Boolean)ReflectionHelper.getPrivateValue(CPacketPlayer.class, packetPlayer, "onGround", "field_149474_g");
            EventPlayerPre eventPlayerPre = new EventPlayerPre(x, y, z, yaw, pitch, Wrapper.INSTANCE.player().isSneaking(), onGroud);
            EventManager.call(eventPlayerPre);
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getX()), "x", "field_149479_a");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getY()), "y", "field_149477_b");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Double.valueOf(eventPlayerPre.getZ()), "z", "field_149478_c");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Float.valueOf(eventPlayerPre.getYaw()), "yaw", "field_149476_e");
            ReflectionHelper.setPrivateValue(CPacketPlayer.class, packetPlayer, Float.valueOf(eventPlayerPre.getPitch()), "pitch", "field_149473_f");
        }
        if (!this.eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public void processPlayerPacket(CPacketPlayer.PositionRotation packetPlayer) {
    }

    public static enum Side {
        IN,
        OUT;

    }
}

