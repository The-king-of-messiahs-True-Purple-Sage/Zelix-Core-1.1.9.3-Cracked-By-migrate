/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class AutoWalk
extends Hack {
    public AutoWalk() {
        super("AutoWalk", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Automatic walking.";
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), (boolean)false);
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), (boolean)true);
        super.onClientTick(event);
    }
}

