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

public class FastLadder
extends Hack {
    public FastLadder() {
        super("FastLadder", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Allows you to climb up ladders faster.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Wrapper.INSTANCE.player().isOnLadder() || Wrapper.INSTANCE.player().field_191988_bg == 0.0f && Wrapper.INSTANCE.player().moveStrafing == 0.0f) {
            return;
        }
        Wrapper.INSTANCE.player().motionY = 0.169;
        super.onClientTick(event);
    }
}

