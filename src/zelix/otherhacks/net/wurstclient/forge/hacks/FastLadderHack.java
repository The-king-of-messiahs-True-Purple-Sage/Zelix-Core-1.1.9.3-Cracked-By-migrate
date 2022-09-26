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

public final class FastLadderHack
extends Hack {
    public FastLadderHack() {
        super("FastLadder", "Allows you to climb up ladders faster.");
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
        if (!player.isOnLadder() || !player.isCollidedHorizontally) {
            return;
        }
        if (player.movementInput.field_192832_b == 0.0f && player.movementInput.moveStrafe == 0.0f) {
            return;
        }
        if (player.motionY < 0.25) {
            player.motionY = 0.25;
        }
    }
}

