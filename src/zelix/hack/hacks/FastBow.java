/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArrow
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.hud.HUD;

public class FastBow
extends Hack {
    public FastBow() {
        super("FastBow", HackCategory.COMBAT);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (HUD.mc.thePlayer == null || HUD.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND) == null || HUD.mc.thePlayer.func_184586_b(EnumHand.OFF_HAND) == null) {
            return;
        }
        if (Mouse.isButtonDown((int)1) && (HUD.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND).getItem() instanceof ItemBow || HUD.mc.thePlayer.func_184586_b(EnumHand.OFF_HAND).getItem() instanceof ItemBow) && HUD.mc.thePlayer.onGround) {
            HUD.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND).getItem().func_77615_a(HUD.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND).getItem().func_190903_i(), (World)HUD.mc.theWorld, (EntityLivingBase)HUD.mc.thePlayer, 10);
        }
    }

    protected ItemStack findAmmo(EntityPlayer player) {
        if (this.isArrow(player.func_184586_b(EnumHand.OFF_HAND))) {
            return player.func_184586_b(EnumHand.OFF_HAND);
        }
        if (this.isArrow(player.func_184586_b(EnumHand.MAIN_HAND))) {
            return player.func_184586_b(EnumHand.MAIN_HAND);
        }
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (!this.isArrow(itemstack)) continue;
            return itemstack;
        }
        return ItemStack.field_190927_a;
    }

    protected boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ItemArrow;
    }
}

