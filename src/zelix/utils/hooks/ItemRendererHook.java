/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 */
package zelix.utils.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRendererHook
extends ItemRenderer {
    public ItemRendererHook(Minecraft mcIn) {
        super(mcIn);
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        super.renderItem(entityIn, heldStack, transform);
    }

    public void func_187462_a(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
        super.func_187462_a(entitylivingbaseIn, heldStack, transform, leftHanded);
    }

    public void renderItemInFirstPerson(float partialTicks) {
        super.renderItemInFirstPerson(partialTicks);
    }

    public void func_187457_a(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_) {
        super.func_187457_a(player, p_187457_2_, p_187457_3_, hand, p_187457_5_, stack, p_187457_7_);
    }

    public void renderOverlays(float partialTicks) {
        super.renderOverlays(partialTicks);
    }

    public void updateEquippedItem() {
        super.updateEquippedItem();
    }

    public void func_187460_a(EnumHand hand) {
        super.func_187460_a(hand);
    }
}

