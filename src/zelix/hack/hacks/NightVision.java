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
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class NightVision
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Brightness", true), new Mode("Effect", false));

    public NightVision() {
        super("NightVision", HackCategory.VISUAL);
        this.addValue(this.mode);
    }

    @Override
    public String getDescription() {
        return "Gets you night vision.";
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.mode.getMode("Brightness").isToggled()) {
            Wrapper.INSTANCE.mcSettings().gammaSetting = 1.0f;
        } else {
            Utils.removeEffect(16);
        }
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("Brightness").isToggled()) {
            Wrapper.INSTANCE.mcSettings().gammaSetting = 10.0f;
        } else {
            Utils.addEffect(16, 1000, 3);
        }
        super.onClientTick(event);
    }
}

