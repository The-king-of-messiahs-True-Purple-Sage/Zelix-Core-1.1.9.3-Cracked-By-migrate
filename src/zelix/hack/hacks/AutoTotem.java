/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class AutoTotem
extends Hack {
    public BooleanValue swapWhileMoving = new BooleanValue("SwapWhileMoving", false);
    public NumberValue delay = new NumberValue("SwipeDelay", 2.0, 0.0, 20.0);
    private int timer;

    public AutoTotem() {
        super("AutoTotem", HackCategory.COMBAT);
        this.addValue(this.swapWhileMoving, this.delay);
    }

    @Override
    public String getDescription() {
        return "Automatically takes a totem in offhand.";
    }

    @Override
    public void onEnable() {
        this.timer = 0;
        super.onEnable();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketClickWindow) {
            this.timer = ((Double)this.delay.getValue()).intValue();
        }
        return true;
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        if (!(((Boolean)this.swapWhileMoving.getValue()).booleanValue() || Wrapper.INSTANCE.player().movementInput.field_192832_b == 0.0f && Wrapper.INSTANCE.player().movementInput.moveStrafe == 0.0f)) {
            return;
        }
        ItemStack offhand = Wrapper.INSTANCE.player().func_184582_a(EntityEquipmentSlot.OFFHAND);
        NonNullList inv = Wrapper.INSTANCE.inventory().mainInventory;
        for (int inventoryIndex = 0; inventoryIndex < inv.size(); ++inventoryIndex) {
            if (inv.get(inventoryIndex) == ItemStack.field_190927_a || offhand != null && offhand.getItem() == Items.field_190929_cY || ((ItemStack)inv.get(inventoryIndex)).getItem() != Items.field_190929_cY) continue;
            this.replace(inventoryIndex);
            break;
        }
        super.onClientTick(event);
    }

    public void replace(int inventoryIndex) {
        if (Wrapper.INSTANCE.player().openContainer instanceof ContainerPlayer) {
            Utils.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP);
            Utils.windowClick(0, 45, 0, ClickType.PICKUP);
            Utils.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP);
        }
    }
}

