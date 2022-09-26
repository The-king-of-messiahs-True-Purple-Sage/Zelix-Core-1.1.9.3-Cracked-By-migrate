/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 */
package zelix.hack.hacks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class AutoTool
extends Hack {
    public AutoTool() {
        super("AutoTool", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Automatically switch to the best tools when mining or attacking";
    }

    @Override
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Utils.nullCheck();
        this.equipBestTool(Wrapper.INSTANCE.world().func_180495_p(event.getPos()));
    }

    @Override
    public void onAttackEntity(AttackEntityEvent event) {
        Utils.nullCheck();
        AutoTool.equipBestWeapon();
    }

    private void equipBestTool(IBlockState blockState) {
        int bestSlot = -1;
        double max = 0.0;
        for (int i = 0; i < 9; ++i) {
            int eff;
            float speed;
            ItemStack stack = Wrapper.INSTANCE.player().inventory.getStackInSlot(i);
            if (stack.func_190926_b() || !((speed = stack.func_150997_a(blockState)) > 1.0f) || !((double)(speed = (float)((double)speed + ((eff = EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185305_q, (ItemStack)stack)) > 0 ? Math.pow(eff, 2.0) + 1.0 : 0.0))) > max)) continue;
            max = speed;
            bestSlot = i;
        }
        if (bestSlot != -1) {
            AutoTool.equip(bestSlot);
        }
    }

    public static void equipBestWeapon() {
        int bestSlot = -1;
        double maxDamage = 0.0;
        for (int i = 0; i < 9; ++i) {
            double damage;
            ItemStack stack = Wrapper.INSTANCE.player().inventory.getStackInSlot(i);
            if (stack.func_190926_b()) continue;
            if (stack.getItem() instanceof ItemTool) {
                damage = (double)((ItemTool)stack.getItem()).getDamage(stack) + (double)EnchantmentHelper.getModifierForCreature((ItemStack)stack, (EnumCreatureAttribute)EnumCreatureAttribute.UNDEFINED);
                if (!(damage > maxDamage)) continue;
                maxDamage = damage;
                bestSlot = i;
                continue;
            }
            if (!(stack.getItem() instanceof ItemSword) || !((damage = (double)((ItemSword)stack.getItem()).getDamageVsEntity() + (double)EnchantmentHelper.getModifierForCreature((ItemStack)stack, (EnumCreatureAttribute)EnumCreatureAttribute.UNDEFINED)) > maxDamage)) continue;
            maxDamage = damage;
            bestSlot = i;
        }
        if (bestSlot != -1) {
            AutoTool.equip(bestSlot);
        }
    }

    private static void equip(int slot) {
        Wrapper.INSTANCE.player().inventory.currentItem = slot;
    }
}

