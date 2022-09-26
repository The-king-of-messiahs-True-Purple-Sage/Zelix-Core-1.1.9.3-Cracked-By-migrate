/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;

public class ArmorHUD
extends Hack {
    public BooleanValue damage = new BooleanValue("Damage", true);
    public BooleanValue extraInfo = new BooleanValue("ExtraInfo", false);
    private int offHandHeldItemCount;
    private int armourCompress;
    private int armourSpacing;

    public ArmorHUD() {
        super("ArmorHUD", HackCategory.VISUAL);
        this.addValue(this.damage, this.extraInfo);
    }

    @Override
    public String getDescription() {
        return "Show armor in game overlay.";
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        ScaledResolution resolution = new ScaledResolution(Wrapper.INSTANCE.mc());
        RenderItem itemRender = Wrapper.INSTANCE.mc().func_175599_af();
        GlStateManager.enableTexture2D();
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 55 - (Wrapper.INSTANCE.player().isInWater() ? 10 : 0);
        for (ItemStack is : Wrapper.INSTANCE.inventory().armorInventory) {
            ++iteration;
            if (is.func_190926_b()) continue;
            int x = i - 90 + (9 - iteration) * this.armourSpacing + this.armourCompress;
            GlStateManager.enableDepth();
            itemRender.field_77023_b = 200.0f;
            itemRender.func_180450_b(is, x, y);
            itemRender.func_180453_a(Minecraft.getMinecraft().fontRendererObj, is, x, y, "");
            itemRender.field_77023_b = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(s, (float)(x + 19 - 2 - Wrapper.INSTANCE.fontRenderer().getStringWidth(s)), (float)(y + 9), 0xFFFFFF);
            if (((Boolean)this.damage.getValue()).booleanValue()) {
                float green = ((float)is.getMaxDamage() - (float)is.getItemDamage()) / (float)is.getMaxDamage();
                float red = 1.0f - green;
                int dmg = 100 - (int)(red * 100.0f);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(dmg + "", (float)(x + 8 - Wrapper.INSTANCE.fontRenderer().getStringWidth(dmg + "") / 2), (float)(y - 11), 0xFFFFFF);
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
        if (((Boolean)this.extraInfo.getValue()).booleanValue()) {
            for (ItemStack is : Wrapper.INSTANCE.inventory().field_184439_c) {
                Item helfInOffHand = Wrapper.INSTANCE.player().func_184592_cb().getItem();
                this.offHandHeldItemCount = this.getItemsOffHand(helfInOffHand);
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear((int)256);
                GlStateManager.enableBlend();
                GlStateManager.pushAttrib();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableDepth();
                Wrapper.INSTANCE.mc().func_175599_af().func_180450_b(is, 572, y);
                itemRender.func_180453_a(Minecraft.getMinecraft().fontRendererObj, is, 572, y, String.valueOf(this.offHandHeldItemCount));
                GlStateManager.enableDepth();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                GlStateManager.disableBlend();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
            }
        }
        if (((Boolean)this.extraInfo.getValue()).booleanValue()) {
            Item currentHeldItem = Wrapper.INSTANCE.inventory().getCurrentItem().getItem();
            int currentHeldItemCount = Wrapper.INSTANCE.inventory().getCurrentItem().func_190916_E();
            ItemStack stackHeld = new ItemStack(currentHeldItem, 1);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear((int)256);
            GlStateManager.enableBlend();
            GlStateManager.pushAttrib();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableDepth();
            Wrapper.INSTANCE.mc().func_175599_af().func_180450_b(stackHeld, 556, y);
            itemRender.func_180453_a(Minecraft.getMinecraft().fontRendererObj, stackHeld, 556, y, String.valueOf(currentHeldItemCount));
            GlStateManager.enableDepth();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.disableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        if (((Boolean)this.extraInfo.getValue()).booleanValue()) {
            this.armourCompress = 14;
            this.armourSpacing = 17;
        } else {
            this.armourCompress = 2;
            this.armourSpacing = 20;
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    int getItemsOffHand(Item i) {
        return Wrapper.INSTANCE.inventory().field_184439_c.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::func_190916_E).sum();
    }
}

