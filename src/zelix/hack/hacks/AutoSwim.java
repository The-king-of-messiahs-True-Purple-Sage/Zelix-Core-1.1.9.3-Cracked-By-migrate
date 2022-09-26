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
import zelix.value.Mode;
import zelix.value.ModeValue;

public class AutoSwim
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Jump", true), new Mode("Dolphin", false), new Mode("Fish", false));

    public AutoSwim() {
        super("AutoSwim", HackCategory.PLAYER);
        this.addValue(this.mode);
    }

    @Override
    public String getDescription() {
        return "Jumps automatically when you in water.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInLava()) {
            return;
        }
        if (Wrapper.INSTANCE.player().isSneaking() || Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
            return;
        }
        if (this.mode.getMode("Jump").isToggled()) {
            Wrapper.INSTANCE.player().jump();
        } else if (this.mode.getMode("Dolphin").isToggled()) {
            Wrapper.INSTANCE.player().motionY += (double)0.04f;
        } else if (this.mode.getMode("Fish").isToggled()) {
            Wrapper.INSTANCE.player().motionY += (double)0.02f;
        }
        super.onClientTick(event);
    }
}

