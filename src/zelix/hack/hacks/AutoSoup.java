/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemSoup
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class AutoSoup
extends Hack {
    NumberValue health = new NumberValue("Health", 5.0, 2.0, 9.5);
    private int oldSlot = -1;

    public AutoSoup() {
        super("AutoSoup", HackCategory.COMBAT);
        this.addValue(this.health);
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Utils.nullCheck();
        for (int i = 0; i < 36; ++i) {
            ItemStack stack = Wrapper.INSTANCE.player().inventory.getStackInSlot(i);
            if (stack == null || stack.getItem() != Items.bowl || i == 9) continue;
            ItemStack emptyBowlStack = Wrapper.INSTANCE.player().inventory.getStackInSlot(9);
            boolean swap = !emptyBowlStack.func_190926_b() && emptyBowlStack.getItem() != Items.bowl;
            Wrapper.INSTANCE.controller().func_187098_a(0, i < 9 ? i + 36 : i, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.INSTANCE.player());
            Wrapper.INSTANCE.controller().func_187098_a(0, 9, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.INSTANCE.player());
            if (!swap) continue;
            Wrapper.INSTANCE.controller().func_187098_a(0, i < 9 ? i + 36 : i, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.INSTANCE.player());
        }
        int soupInHotbar = this.findSoup(0, 9);
        if (soupInHotbar != -1) {
            if (!this.shouldEatSoup()) {
                this.stopIfEating();
                return;
            }
            if (this.oldSlot == -1) {
                this.oldSlot = Wrapper.INSTANCE.player().inventory.currentItem;
            }
            Wrapper.INSTANCE.player().inventory.currentItem = soupInHotbar;
            KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), (boolean)true);
            return;
        }
        this.stopIfEating();
        int soupInInventory = this.findSoup(9, 36);
        if (soupInInventory != -1) {
            Wrapper.INSTANCE.controller().func_187098_a(0, soupInInventory, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.INSTANCE.player());
        }
        super.onPlayerTick(event);
    }

    private int findSoup(int startSlot, int endSlot) {
        for (int i = startSlot; i < endSlot; ++i) {
            ItemStack stack = Wrapper.INSTANCE.player().inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemSoup)) continue;
            return i;
        }
        return -1;
    }

    private boolean shouldEatSoup() {
        return !(Wrapper.INSTANCE.player().getHealth() > ((Double)this.health.getValue()).floatValue() * 2.0f);
    }

    private void stopIfEating() {
        if (this.oldSlot == -1) {
            return;
        }
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), (boolean)false);
        Wrapper.INSTANCE.player().inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
}

