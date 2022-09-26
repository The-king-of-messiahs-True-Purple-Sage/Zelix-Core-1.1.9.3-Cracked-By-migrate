/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.Timer
 *  net.minecraftforge.client.event.InputUpdateEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Timer;
import net.minecraftforge.client.event.InputUpdateEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.MoveUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Regen
extends Hack {
    Float ticks = Float.valueOf(0.98f);
    Minecraft mc = Wrapper.INSTANCE.mc();
    ModeValue mode = new ModeValue("Mode", new Mode("New", true), new Mode("Old", false));

    public Regen() {
        super("Regen", HackCategory.PLAYER);
    }

    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
        super.onEnable();
    }

    @Override
    public void onInputUpdate(InputUpdateEvent event) {
        Utils.nullCheck();
        if (this.mode.getMode("New").isToggled()) {
            if (this.mc.thePlayer.ticksExisted % 5 == 0) {
                this.setTickLength(50.0f / this.ticks.floatValue());
                for (int i = 0; i < 10; ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(true));
                }
            } else if (MoveUtils.isMoving()) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(this.mc.thePlayer.onGround));
            }
        } else if (this.mode.getMode("Old").isToggled()) {
            if (MoveUtils.isMoving() || !this.mc.thePlayer.onGround) {
                return;
            }
            for (int i = 0; i < 9; ++i) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(this.mc.thePlayer.onGround));
            }
            this.setTickLength(111.111115f);
        }
    }

    private void setTickLength(float tickLength) {
        try {
            Field fTimer = Minecraft.getMinecraft().getClass().getDeclaredField(Mapping.timer);
            fTimer.setAccessible(true);
            Field fTickLength = Timer.class.getDeclaredField(Mapping.tickLength);
            fTickLength.setAccessible(true);
            fTickLength.setFloat(fTimer.get(Minecraft.getMinecraft()), tickLength);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

