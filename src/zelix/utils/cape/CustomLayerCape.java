/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.client.renderer.entity.layers.LayerRenderer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.MathHelper
 */
package zelix.utils.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import zelix.Core;

public class CustomLayerCape
implements LayerRenderer<AbstractClientPlayer> {
    private static ResourceLocation cape;
    private final RenderPlayer playerRenderer;

    public CustomLayerCape(RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }

    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack;
        if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.func_175148_a(EnumPlayerModelParts.CAPE) && Core.capeManager.getCapeByName(entitylivingbaseIn.getName()) != null && (itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.CHEST)).getItem() != Items.field_185160_cR) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.playerRenderer.func_110776_a(Core.capeManager.getCapeByName(entitylivingbaseIn.getName()));
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.125f);
            double d0 = entitylivingbaseIn.field_71091_bM + (entitylivingbaseIn.field_71094_bP - entitylivingbaseIn.field_71091_bM) * (double)partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double)partialTicks);
            double d1 = entitylivingbaseIn.field_71096_bN + (entitylivingbaseIn.field_71095_bQ - entitylivingbaseIn.field_71096_bN) * (double)partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double)partialTicks);
            double d2 = entitylivingbaseIn.field_71097_bO + (entitylivingbaseIn.field_71085_bR - entitylivingbaseIn.field_71097_bO) * (double)partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double)partialTicks);
            float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
            double d3 = MathHelper.func_76126_a((float)(f * ((float)Math.PI / 180)));
            double d4 = -MathHelper.func_76134_b((float)(f * ((float)Math.PI / 180)));
            float f1 = (float)d1 * 10.0f;
            f1 = MathHelper.func_76131_a((float)f1, (float)-6.0f, (float)32.0f);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0f;
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0f;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            float f4 = entitylivingbaseIn.field_71107_bF + (entitylivingbaseIn.field_71109_bG - entitylivingbaseIn.field_71107_bF) * partialTicks;
            f1 += MathHelper.func_76126_a((float)((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f)) * 32.0f * f4;
            if (entitylivingbaseIn.isSneaking()) {
                f1 += 25.0f;
            }
            GlStateManager.rotate((float)(6.0f + f2 / 2.0f + f1), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)(f3 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)(-f3 / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.playerRenderer.getMainModel().renderCape(0.0625f);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}

