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
import zelix.utils.hooks.visual.ChatUtils;

public class AutoPath
extends Hack {
    public boolean pathsetting = false;

    public AutoPath() {
        super("AutoPath", HackCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        ChatUtils.message("Start Setting The Path");
        ChatUtils.message("Please Walk And This Module Will Record Your Walking Path And Repeat");
        ChatUtils.message("After Walking, Just Type START To Continue");
        this.pathsetting = true;
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.pathsetting) {
            // empty if block
        }
        super.onClientTick(event);
    }
}

