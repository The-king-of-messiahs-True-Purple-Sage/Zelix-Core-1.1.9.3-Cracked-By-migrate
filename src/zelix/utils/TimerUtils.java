/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.utils;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class TimerUtils {
    private long lastMS = 0L;
    private long prevMS = 0L;

    public boolean isDelay(long delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long lastMS) {
        this.lastMS = lastMS;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int d) {
        return 1000 / d;
    }

    public boolean hasReached(float f) {
        return (float)(this.getCurrentMS() - this.lastMS) >= f;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {
        return (float)(this.getTime() - this.prevMS) >= milliSec;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public static void settimer(float timerSpeed) {
        try {
            Field field = ReflectionHelper.findField(Minecraft.class, (String[])new String[]{"timer", "field_71428_T"});
            field.setAccessible(true);
            Field field2 = ReflectionHelper.findField(Timer.class, (String[])new String[]{"timerSpeed", "field_74278_d"});
            field2.setAccessible(true);
            field2.setFloat(field.get(Minecraft.getMinecraft()), timerSpeed);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

