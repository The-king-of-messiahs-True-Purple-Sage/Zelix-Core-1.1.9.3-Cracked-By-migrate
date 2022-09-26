/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;

public final class FullbrightHack
extends Hack {
    public FullbrightHack() {
        super("Fullbright", "Allows you to see in the dark.");
        this.setCategory(Category.RENDER);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        if (this.isEnabled()) {
            if (FullbrightHack.mc.gameSettings.gammaSetting < 16.0f) {
                FullbrightHack.mc.gameSettings.gammaSetting = Math.min(FullbrightHack.mc.gameSettings.gammaSetting + 0.5f, 16.0f);
            }
            return;
        }
        if (FullbrightHack.mc.gameSettings.gammaSetting > 0.5f) {
            FullbrightHack.mc.gameSettings.gammaSetting = Math.max(FullbrightHack.mc.gameSettings.gammaSetting - 0.5f, 0.5f);
        } else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
}

