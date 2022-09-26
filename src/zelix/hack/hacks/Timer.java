/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.system.Mapping;
import zelix.value.NumberValue;

public class Timer
extends Hack {
    NumberValue ticks = new NumberValue("Ticks", 2.0, 1.0, 10.0);

    public Timer() {
        super("Timer", HackCategory.PLAYER);
        this.addValue(this.ticks);
    }

    @Override
    public void onEnable() {
        this.setTickLength(50.0f / ((Double)this.ticks.getValue()).floatValue());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.setTickLength(50.0f / ((Double)this.ticks.getValue()).floatValue());
        super.onClientTick(event);
    }

    private void setTickLength(float tickLength) {
        try {
            Field fTimer = Minecraft.getMinecraft().getClass().getDeclaredField(Mapping.timer);
            fTimer.setAccessible(true);
            Field fTickLength = net.minecraft.util.Timer.class.getDeclaredField(Mapping.tickLength);
            fTickLength.setAccessible(true);
            fTickLength.setFloat(fTimer.get(Minecraft.getMinecraft()), tickLength);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

