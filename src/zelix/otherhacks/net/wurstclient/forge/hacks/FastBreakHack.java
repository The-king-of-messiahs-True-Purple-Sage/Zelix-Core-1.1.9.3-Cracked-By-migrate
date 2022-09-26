/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.PlayerControllerUtils;

public final class FastBreakHack
extends Hack {
    public FastBreakHack() {
        super("FastBreak", "Allows you to break blocks faster.");
        this.setCategory(Category.BLOCKS);
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
        try {
            PlayerControllerUtils.setBlockHitDelay(0);
        }
        catch (ReflectiveOperationException e) {
            this.setEnabled(false);
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event) {
        try {
            float progress = PlayerControllerUtils.getCurBlockDamageMP() + BlockUtils.getHardness(event.getPos());
            if (progress >= 1.0f) {
                return;
            }
        }
        catch (ReflectiveOperationException e) {
            this.setEnabled(false);
            throw new RuntimeException(e);
        }
        WMinecraft.getPlayer().sendQueue.addToSendQueue((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), FastBreakHack.mc.objectMouseOver.field_178784_b));
    }
}

