/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.InventoryEffectRenderer
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraft.util.DamageSource
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class AutoArmor
extends Hack {
    public BooleanValue useEnchantments = new BooleanValue("Enchantments", true);
    public BooleanValue swapWhileMoving = new BooleanValue("SwapWhileMoving", false);
    public NumberValue delay = new NumberValue("Delay", 2.0, 0.0, 20.0);
    private int timer;

    public AutoArmor() {
        super("AutoArmor", HackCategory.PLAYER);
        this.addValue(this.useEnchantments, this.swapWhileMoving, this.delay);
    }

    @Override
    public String getDescription() {
        return "Manages your armor automatically.";
    }

    @Override
    public void onEnable() {
        this.timer = 0;
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ItemArmor item;
        ItemStack stack;
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        if (Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer && !(Wrapper.INSTANCE.mc().currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        InventoryPlayer inventory = Wrapper.INSTANCE.inventory();
        if (!(((Boolean)this.swapWhileMoving.getValue()).booleanValue() || Wrapper.INSTANCE.player().movementInput.field_192832_b == 0.0f && Wrapper.INSTANCE.player().movementInput.moveStrafe == 0.0f)) {
            return;
        }
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (int type = 0; type < 4; ++type) {
            bestArmorSlots[type] = -1;
            stack = inventory.armorItemInSlot(type);
            if (Utils.isNullOrEmptyStack(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
            item = (ItemArmor)stack.getItem();
            bestArmorValues[type] = this.getArmorValue(item, stack);
        }
        for (int slot = 0; slot < 36; ++slot) {
            stack = inventory.getStackInSlot(slot);
            if (Utils.isNullOrEmptyStack(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
            item = (ItemArmor)stack.getItem();
            int armorType = item.armorType.func_188454_b();
            int armorValue = this.getArmorValue(item, stack);
            if (armorValue <= bestArmorValues[armorType]) continue;
            bestArmorSlots[armorType] = slot;
            bestArmorValues[armorType] = armorValue;
        }
        ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(types);
        for (int type : types) {
            ItemStack oldArmor;
            int slot = bestArmorSlots[type];
            if (slot == -1 || !Utils.isNullOrEmptyStack(oldArmor = inventory.armorItemInSlot(type)) && inventory.getFirstEmptyStack() == -1) continue;
            if (slot < 9) {
                slot += 36;
            }
            if (!Utils.isNullOrEmptyStack(oldArmor)) {
                Utils.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE);
            }
            Utils.windowClick(0, slot, 0, ClickType.QUICK_MOVE);
            break;
        }
        super.onClientTick(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketClickWindow) {
            this.timer = ((Double)this.delay.getValue()).intValue();
        }
        return true;
    }

    int getArmorValue(ItemArmor item, ItemStack stack) {
        int armorPoints = item.damageReduceAmount;
        int prtPoints = 0;
        int armorToughness = (int)item.field_189415_e;
        int armorType = item.getArmorMaterial().func_78044_b(EntityEquipmentSlot.LEGS);
        if (((Boolean)this.useEnchantments.getValue()).booleanValue()) {
            Enchantment protection = Enchantments.field_180310_c;
            int prtLvl = EnchantmentHelper.func_77506_a((Enchantment)protection, (ItemStack)stack);
            EntityPlayerSP player = Wrapper.INSTANCE.player();
            DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer)player);
            prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
        }
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }
}

