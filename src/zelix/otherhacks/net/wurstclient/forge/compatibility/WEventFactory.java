/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.otherhacks.net.wurstclient.forge.compatibility;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.events.WChatOutputEvent;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;

public class WEventFactory {
    @SubscribeEvent
    public void onPlayerPreTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        EntityPlayer player = event.player;
        if (player != WMinecraft.getPlayer()) {
            return;
        }
        if (!player.worldObj.isRemote) {
            return;
        }
        MinecraftForge.EVENT_BUS.post((Event)new WUpdateEvent((EntityPlayerSP)player));
    }

    @SubscribeEvent
    public void onClientSentMessage(ClientChatEvent event) {
        WChatOutputEvent event2 = new WChatOutputEvent(event.getOriginalMessage());
        if (MinecraftForge.EVENT_BUS.post((Event)event2)) {
            event.setCanceled(true);
        }
        event.setMessage(event2.getMessage());
    }
}

