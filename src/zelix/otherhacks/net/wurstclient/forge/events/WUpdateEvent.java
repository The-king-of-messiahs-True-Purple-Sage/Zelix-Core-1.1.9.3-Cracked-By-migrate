/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package zelix.otherhacks.net.wurstclient.forge.events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class WUpdateEvent
extends Event {
    private final EntityPlayerSP player;

    public WUpdateEvent(EntityPlayerSP player) {
        this.player = player;
    }

    public EntityPlayerSP getPlayer() {
        return this.player;
    }
}

