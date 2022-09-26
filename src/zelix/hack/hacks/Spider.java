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

public class Spider
extends Hack {
    public Spider() {
        super("Spider", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Allows you to climb up walls like a spider.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Wrapper.INSTANCE.player().isOnLadder() && Wrapper.INSTANCE.player().isCollidedHorizontally && Wrapper.INSTANCE.player().motionY < 0.2) {
            Wrapper.INSTANCE.player().motionY = 0.2;
        }
        super.onClientTick(event);
    }
}

