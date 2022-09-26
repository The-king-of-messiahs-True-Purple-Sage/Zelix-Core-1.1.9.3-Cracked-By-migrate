/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.BlockWorkbench
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class AutoEat
extends Hack {
    private int oldSlot;
    private int bestSlot;

    public AutoEat() {
        super("AutoEat", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Eat food automatically.";
    }

    @Override
    public void onEnable() {
        this.oldSlot = -1;
        this.bestSlot = -1;
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.oldSlot == -1) {
            if (!this.canEat()) {
                return;
            }
            float bestSaturation = 0.0f;
            for (int i = 0; i < 9; ++i) {
                ItemFood food;
                float saturation;
                ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
                if (!this.isFood(stack) || !((saturation = (food = (ItemFood)stack.getItem()).getSaturationModifier(stack)) > bestSaturation)) continue;
                bestSaturation = saturation;
                this.bestSlot = i;
            }
            if (this.bestSlot != -1) {
                this.oldSlot = Wrapper.INSTANCE.inventory().currentItem;
            }
        } else {
            if (!this.canEat()) {
                this.stop();
                return;
            }
            if (!this.isFood(Wrapper.INSTANCE.inventory().getStackInSlot(this.bestSlot))) {
                this.stop();
                return;
            }
            Wrapper.INSTANCE.inventory().currentItem = this.bestSlot;
            KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), (boolean)true);
        }
        super.onClientTick(event);
    }

    boolean canEat() {
        if (!Wrapper.INSTANCE.player().canEat(false)) {
            return false;
        }
        if (Wrapper.INSTANCE.mc().objectMouseOver != null) {
            Block block;
            Entity entity = Wrapper.INSTANCE.mc().objectMouseOver.field_72308_g;
            if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
                return false;
            }
            BlockPos pos = Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a();
            if (pos != null && ((block = BlockUtils.getBlock(pos)) instanceof BlockContainer || block instanceof BlockWorkbench)) {
                return false;
            }
        }
        return true;
    }

    boolean isFood(ItemStack stack) {
        return !Utils.isNullOrEmptyStack(stack) && stack.getItem() instanceof ItemFood;
    }

    void stop() {
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), (boolean)false);
        Wrapper.INSTANCE.inventory().currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
}

