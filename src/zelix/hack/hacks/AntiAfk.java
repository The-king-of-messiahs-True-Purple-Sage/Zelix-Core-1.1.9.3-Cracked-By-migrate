/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class AntiAfk
extends Hack {
    public NumberValue delay;
    public TimerUtils timer = new TimerUtils();

    public AntiAfk() {
        super("AntiAfk", HackCategory.ANOTHER);
        this.delay = new NumberValue("DelaySec", 10.0, 1.0, 100.0);
        this.addValue(this.delay);
    }

    @Override
    public String getDescription() {
        return "Prevents from being kicked for AFK.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay((long)(1000.0 * (Double)this.delay.getValue()))) {
            Wrapper.INSTANCE.player().jump();
            this.timer.setLastMS();
        }
        super.onClientTick(event);
    }
}

