/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class FastUse
extends Hack {
    Number ticks = 0.49;
    BooleanValue onGround;
    ModeValue mode = new ModeValue("Mode", new Mode("HytTimer", true));
    public static Boolean eated = false;

    public FastUse() {
        super("FastUse", HackCategory.PLAYER);
        this.onGround = new BooleanValue("OnGround", true);
        this.addValue(this.mode, this.onGround);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
        super.onDisable();
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        block1: {
            block2: {
                if (!this.mode.getMode("HytTimer").isToggled() || !Wrapper.INSTANCE.player().func_184587_cr() || Wrapper.INSTANCE.player().func_184218_aH()) break block1;
                if (!((Boolean)this.onGround.getValue()).booleanValue()) break block2;
                if (!Wrapper.INSTANCE.player().func_184587_cr() || Wrapper.INSTANCE.player().func_184218_aH()) break block1;
                this.setTickLength(50.0f / this.ticks.floatValue());
                Object check = null;
                int n3 = 2;
                int j = 0;
                while (j < n3) {
                    int it = j++;
                    boolean n4 = false;
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(Wrapper.INSTANCE.player().onGround));
                }
                break block1;
            }
            this.setTickLength(50.0f / this.ticks.floatValue());
            int n3 = 2;
            int j = 0;
            if (j >= n3) break block1;
            int it = j;
            boolean n4 = false;
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(Wrapper.INSTANCE.player().onGround));
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

