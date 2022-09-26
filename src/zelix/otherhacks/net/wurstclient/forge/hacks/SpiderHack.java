/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;

public final class SpiderHack
extends Hack {
    public SpiderHack() {
        super("Spider", "Allows you to climb up walls like a spider.");
        this.setCategory(Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        if (!player.isCollidedHorizontally) {
            return;
        }
        if (player.motionY < 0.2) {
            player.motionY = 0.2;
        }
    }
}

