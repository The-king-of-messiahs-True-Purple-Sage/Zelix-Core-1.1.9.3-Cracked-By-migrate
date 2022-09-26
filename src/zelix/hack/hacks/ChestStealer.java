/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.Slot
 *  net.minecraft.network.play.server.SPacketWindowItems
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 */
package zelix.hack.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.NumberValue;

public class ChestStealer
extends Hack {
    public NumberValue delay = new NumberValue("Delay", 4.0, 0.0, 20.0);
    public SPacketWindowItems packet;
    public int ticks;

    public ChestStealer() {
        super("ChestStealer", HackCategory.PLAYER);
        this.addValue(this.delay);
        this.ticks = 0;
    }

    @Override
    public String getDescription() {
        return "Steals all stuff from chest.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.IN && packet instanceof SPacketWindowItems) {
            this.packet = (SPacketWindowItems)packet;
        }
        return true;
    }

    boolean isContainerEmpty(Container container) {
        int slotAmount;
        boolean temp = true;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 35;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            temp = false;
        }
        return temp;
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (!Wrapper.INSTANCE.mc().inGameHasFocus && this.packet != null && player.openContainer.windowId == this.packet.func_148911_c() && Wrapper.INSTANCE.mc().currentScreen instanceof GuiChest) {
            if (!this.isContainerEmpty(player.openContainer)) {
                for (int i = 0; i < player.openContainer.inventorySlots.size() - 36; ++i) {
                    Slot slot = player.openContainer.getSlot(i);
                    if (!slot.getHasStack() || slot.getStack() == null || this.ticks < ((Double)this.delay.getValue()).intValue()) continue;
                    Wrapper.INSTANCE.controller().func_187098_a(player.openContainer.windowId, i, 1, ClickType.QUICK_MOVE, (EntityPlayer)player);
                    this.ticks = 0;
                }
                ++this.ticks;
            } else {
                player.closeScreen();
                this.packet = null;
            }
        }
    }
}

