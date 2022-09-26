/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Flight
extends Hack {
    Queue<CPacketPlayer> packets = new LinkedList<CPacketPlayer>();
    List<CPacketPlayer> p = new ArrayList<CPacketPlayer>();
    private boolean isfly;
    private int stage = 0;
    private int maxstage = 5;
    private boolean jump = false;
    Minecraft mc = Wrapper.INSTANCE.mc();
    private int timer = 0;
    private double y = 0.0;
    private NumberValue motionY;
    private NumberValue motionXZ;
    private NumberValue delay;
    private NumberValue flytimer;
    private BooleanValue XYZ;
    boolean send = false;
    public ModeValue mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("Dynamic", false), new Mode("Hypixel", false), new Mode("HYT", false));
    int ticks = 0;

    public Flight() {
        super("Flight", HackCategory.MOVEMENT);
        this.motionY = new NumberValue("MotionY", 0.5, 0.42, 6.0);
        this.motionXZ = new NumberValue("MotionXZ", 1.0, 0.0, 10.0);
        this.delay = new NumberValue("Delay", 2.0, 0.0, 10.0);
        this.flytimer = new NumberValue("FlyTimer", 1.0, 0.1, 10.0);
        this.XYZ = new BooleanValue("AntiKick", true);
        this.addValue(this.mode, this.motionY, this.motionXZ, this.delay, this.flytimer, this.XYZ);
    }

    @Override
    public String getDescription() {
        return "Allows you to you fly.";
    }

    @Override
    public void onEnable() {
        this.ticks = 0;
        this.y = Wrapper.INSTANCE.player().posY;
        this.timer = 999;
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (this.mode.getMode("Hypixel").isToggled()) {
            player.motionY = 0.0;
            player.setSprinting(true);
            player.onGround = true;
            ++this.ticks;
            if (this.ticks == 2 || this.ticks == 4 || this.ticks == 6 || this.ticks == 8 || this.ticks == 10 || this.ticks == 12 || this.ticks == 14 || this.ticks == 16 || this.ticks == 18 || this.ticks == 20) {
                player.setPosition(player.posX, player.posY + 1.28E-9, player.posZ);
            }
            if (this.ticks == 20) {
                this.ticks = 0;
            }
        } else if (this.mode.getMode("Simple").isToggled()) {
            player.capabilities.isFlying = true;
        } else if (this.mode.getMode("Dynamic").isToggled()) {
            if (((Boolean)this.XYZ.getValue()).booleanValue() && this.mc.thePlayer.ticksExisted % 4 == 0) {
                this.mc.thePlayer.motionY = -0.04f;
            }
            float flyspeed = ((Double)this.flytimer.getValue()).floatValue();
            player.jumpMovementFactor = 0.4f;
            player.motionX = 0.0;
            player.motionY = 0.0;
            player.motionZ = 0.0;
            player.jumpMovementFactor *= flyspeed * 3.0f;
            if (Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
                if (((Boolean)this.XYZ.getValue()).booleanValue()) {
                    this.mc.thePlayer.motionY = this.mc.thePlayer.ticksExisted % 20 == 0 ? (double)-0.04f : (double)flyspeed;
                } else {
                    player.motionY += (double)flyspeed;
                }
            }
        } else if (this.mode.getMode("HYT").isToggled()) {
            if (!this.isfly) {
                ++this.timer;
            }
            if ((double)this.timer > (Double)this.delay.getValue()) {
                this.timer = 0;
                this.move();
                this.isfly = true;
                this.stage = 0;
            }
            if (this.stage >= 1) {
                this.isfly = false;
                if (this.packets != null && !this.packets.isEmpty()) {
                    Wrapper.INSTANCE.sendPacket((Packet)this.packets.poll());
                }
            }
        }
        super.onClientTick(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof CPacketPlayer && this.mode.getMode("HYT").isToggled()) {
            ChatUtils.message("Packets:" + String.valueOf(this.packets.size()));
            this.packets.add((CPacketPlayer)packet);
            ++this.stage;
            return !this.isfly;
        }
        return true;
    }

    @Override
    public void onDisable() {
        if (this.mode.getMode("Simple").isToggled()) {
            Wrapper.INSTANCE.player().capabilities.isFlying = false;
        }
        if (this.mode.getMode("HYT").isToggled() && this.packets != null && !this.packets.isEmpty()) {
            Wrapper.INSTANCE.sendPacket((Packet)this.packets.poll());
        }
        this.packets.clear();
        super.onDisable();
    }

    private void move() {
        if (Wrapper.INSTANCE.player().posY <= this.y) {
            Wrapper.INSTANCE.player().motionY = (Double)this.motionY.getValue();
        } else {
            double dir = (double)(Wrapper.INSTANCE.player().rotationYaw / 180.0f) * Math.PI;
            if (Wrapper.INSTANCE.player().motionY < 0.0) {
                Wrapper.INSTANCE.player().motionY = -0.05;
            }
            Wrapper.INSTANCE.player().motionX = -Math.sin(dir) * (Double)this.motionXZ.getValue();
            Wrapper.INSTANCE.player().motionZ = Math.cos(dir) * (Double)this.motionXZ.getValue();
        }
    }
}

