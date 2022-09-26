/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.lang.reflect.Field;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;

public final class TimerHack
extends Hack {
    private final SliderSetting speed = new SliderSetting("Speed", 2.0, 0.1, 20.0, 0.1, SliderSetting.ValueDisplay.DECIMAL);

    public TimerHack() {
        super("Timer", "Changes the speed of almost everything.");
        this.setCategory(Category.OTHER);
        this.addSetting(this.speed);
    }

    @Override
    public String getRenderName() {
        return this.getName() + " [" + this.speed.getValueString() + "]";
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.setTickLength(50.0f);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        this.setTickLength(50.0f / this.speed.getValueF());
    }

    private void setTickLength(float tickLength) {
        try {
            Field fTimer = mc.getClass().getDeclaredField(wurst.isObfuscated() ? "field_71428_T" : "timer");
            fTimer.setAccessible(true);
            if ("1.12.2".equals("1.10.2")) {
                Field fTimerSpeed = Timer.class.getDeclaredField(wurst.isObfuscated() ? "field_74278_d" : "timerSpeed");
                fTimerSpeed.setAccessible(true);
                fTimerSpeed.setFloat(fTimer.get(mc), 50.0f / tickLength);
            } else {
                Field fTickLength = Timer.class.getDeclaredField(wurst.isObfuscated() ? "field_194149_e" : "tickLength");
                fTickLength.setAccessible(true);
                fTickLength.setFloat(fTimer.get(mc), tickLength);
            }
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

