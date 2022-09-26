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
import zelix.utils.Wrapper;

public class AntiRain
extends Hack {
    public AntiRain() {
        super("AntiRain", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Stops rain.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Wrapper.INSTANCE.world().setRainStrength(0.0f);
        super.onClientTick(event);
    }
}

