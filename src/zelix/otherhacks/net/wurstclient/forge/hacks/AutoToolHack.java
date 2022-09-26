/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;

public final class AutoToolHack
extends Hack {
    private final CheckboxSetting useSwords = new CheckboxSetting("Use swords", "Uses swords to break\nleaves, cobwebs, etc.", false);
    private final CheckboxSetting useHands = new CheckboxSetting("Use hands", "Uses an empty hand or a\nnon-damageable item when\nno applicable tool is found.", true);
    private final CheckboxSetting repairMode = new CheckboxSetting("Repair mode", "Won't use tools that are about to break.", false);

    public AutoToolHack() {
        super("AutoTool", "Automatically equips the fastest\napplicable tool in your hotbar\nwhen you try to break a block.");
        this.setCategory(Category.BLOCKS);
        this.addSetting(this.useSwords);
        this.addSetting(this.useHands);
        this.addSetting(this.repairMode);
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
    public void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event) {
        this.equipBestTool(event.getPos(), this.useSwords.isChecked(), this.useHands.isChecked(), this.repairMode.isChecked());
    }

    public void equipBestTool(BlockPos pos, boolean useSwords, boolean useHands, boolean repairMode) {
        boolean useFallback;
        EntityPlayerSP player = WMinecraft.getPlayer();
        if (player.capabilities.isCreativeMode) {
            return;
        }
        IBlockState state = BlockUtils.getState(pos);
        ItemStack heldItem = player.func_184614_ca();
        float bestSpeed = this.getDestroySpeed(heldItem, state);
        int bestSlot = -1;
        int fallbackSlot = -1;
        InventoryPlayer inventory = player.inventory;
        for (int slot = 0; slot < 9; ++slot) {
            float speed;
            if (slot == inventory.currentItem) continue;
            ItemStack stack = inventory.getStackInSlot(slot);
            if (fallbackSlot == -1 && !this.isDamageable(stack)) {
                fallbackSlot = slot;
            }
            if ((speed = this.getDestroySpeed(stack, state)) <= bestSpeed || !useSwords && stack.getItem() instanceof ItemSword || this.isTooDamaged(stack, repairMode)) continue;
            bestSpeed = speed;
            bestSlot = slot;
        }
        boolean bl = useFallback = this.isDamageable(heldItem) && (this.isTooDamaged(heldItem, repairMode) || useHands && this.getDestroySpeed(heldItem, state) <= 1.0f);
        if (bestSlot != -1) {
            inventory.currentItem = bestSlot;
            return;
        }
        if (!useFallback) {
            return;
        }
        if (fallbackSlot != -1) {
            inventory.currentItem = fallbackSlot;
            return;
        }
        if (this.isTooDamaged(heldItem, repairMode)) {
            inventory.currentItem = inventory.currentItem == 8 ? 0 : ++inventory.currentItem;
        }
    }

    private float getDestroySpeed(ItemStack stack, IBlockState state) {
        int efficiency;
        float speed;
        float f = speed = stack == null || stack.func_190926_b() ? 1.0f : stack.func_150997_a(state);
        if (speed > 1.0f && (efficiency = EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185305_q, (ItemStack)stack)) > 0 && stack != null && !stack.func_190926_b()) {
            speed += (float)(efficiency * efficiency + 1);
        }
        return speed;
    }

    private boolean isDamageable(ItemStack stack) {
        return stack != null && !stack.func_190926_b() && stack.getItem().isDamageable();
    }

    private boolean isTooDamaged(ItemStack stack, boolean repairMode) {
        return repairMode && stack.getMaxDamage() - stack.getItemDamage() <= 4;
    }
}

