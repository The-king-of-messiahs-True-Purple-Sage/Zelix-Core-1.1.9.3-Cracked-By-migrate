/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.EnemyManager;
import zelix.managers.FriendManager;
import zelix.utils.MathUtils;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.BooleanValue;

public class Profiler
extends Hack {
    public BooleanValue armor = new BooleanValue("Armor", true);

    public Profiler() {
        super("Profiler", HackCategory.VISUAL);
        this.addValue(this.armor);
    }

    @Override
    public String getDescription() {
        return "Allows you to see armor of player or info of item.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            RenderManager renderManager = Wrapper.INSTANCE.mc().getRenderManager();
            double renderPosX = renderManager.viewerPosX;
            double renderPosY = renderManager.viewerPosY;
            double renderPosZ = renderManager.viewerPosZ;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialTicks() - renderPosX;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialTicks() - renderPosY;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialTicks() - renderPosZ;
            this.renderNameTag(entity, entity.getName(), xPos, yPos, zPos);
        }
        super.onRenderWorldLast(event);
    }

    void renderNameTag(EntityLivingBase entity, String tag, double x, double y, double z) {
        int health;
        if (entity instanceof EntityArmorStand || ValidUtils.isValidEntity(entity) || entity == Wrapper.INSTANCE.player()) {
            return;
        }
        int color = ColorUtils.color(200, 200, 200, 160);
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        y += entity.isSneaking() ? 0.5 : 0.7;
        float distance = player.getDistanceToEntity((Entity)entity) / 4.0f;
        if (distance < 1.6f) {
            distance = 1.6f;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            String ID = Utils.getPlayerName(entityPlayer);
            if (EnemyManager.enemysList.contains(ID)) {
                tag = "\u00a7c" + ID;
                color = ColorUtils.color(179, 20, 20, 160);
            }
            if (FriendManager.friendsList.contains(ID)) {
                tag = "\u00a73" + ID;
                color = ColorUtils.color(66, 147, 179, 160);
            }
            if (ValidUtils.isBot((EntityLivingBase)entityPlayer)) {
                tag = "\u00a7e" + ID;
                color = ColorUtils.color(200, 200, 0, 160);
            }
        }
        if ((double)(health = (int)entity.getHealth()) <= (double)entity.getMaxHealth() * 0.25) {
            tag = tag + "\u00a74";
        } else if ((double)health <= (double)entity.getMaxHealth() * 0.5) {
            tag = tag + "\u00a76";
        } else if ((double)health <= (double)entity.getMaxHealth() * 0.75) {
            tag = tag + "\u00a7e";
        } else if ((float)health <= entity.getMaxHealth()) {
            tag = tag + "\u00a72";
        }
        tag = String.valueOf(tag) + " " + Math.round(health);
        RenderManager renderManager = Wrapper.INSTANCE.mc().getRenderManager();
        float scale = distance;
        scale /= 30.0f;
        scale = (float)((double)scale * 0.3);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x), (float)((float)y + 1.4f), (float)((float)z));
        GL11.glNormal3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        Tessellator var14 = Tessellator.getInstance();
        BufferBuilder var15 = var14.func_178180_c();
        int width = fontRenderer.getStringWidth(tag) / 2;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.drawRect(-width - 2, -(fontRenderer.FONT_HEIGHT + 1), width + 2, 2.0f, color);
        fontRenderer.drawString(tag, (float)(MathUtils.getMiddle(-width - 2, width + 2) - width), (float)(-(fontRenderer.FONT_HEIGHT - 1)), Color.WHITE.getRGB(), true);
        if (entity instanceof EntityPlayer && ((Boolean)this.armor.getValue()).booleanValue()) {
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            GlStateManager.translate((float)0.0f, (float)1.0f, (float)0.0f);
            this.renderArmor(entityPlayer, 0, -(fontRenderer.FONT_HEIGHT + 1) - 20);
            GlStateManager.translate((float)0.0f, (float)-1.0f, (float)0.0f);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void renderArmor(EntityPlayer player, int x, int y) {
        InventoryPlayer items = player.inventory;
        ItemStack inHand = player.func_184614_ca();
        ItemStack boots = items.armorItemInSlot(0);
        ItemStack leggings = items.armorItemInSlot(1);
        ItemStack body = items.armorItemInSlot(2);
        ItemStack helm = items.armorItemInSlot(3);
        ItemStack[] stuff = null;
        stuff = inHand != null ? new ItemStack[]{inHand, helm, body, leggings, boots} : new ItemStack[]{helm, body, leggings, boots};
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (int j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null || i.getItem() == null) continue;
            stacks.add(i);
        }
        int width = 16 * stacks.size() / 2;
        x -= width;
        GlStateManager.disableDepth();
        for (ItemStack stack : stacks) {
            this.renderItem(stack, x, y);
            x += 16;
        }
        GlStateManager.enableDepth();
    }

    public void renderItem(ItemStack stack, int x, int y) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        RenderItem renderItem = Wrapper.INSTANCE.mc().func_175599_af();
        EnchantEntry[] enchants = new EnchantEntry[]{new EnchantEntry(Enchantments.field_180310_c, "Pro"), new EnchantEntry(Enchantments.field_92091_k, "Th"), new EnchantEntry(Enchantments.field_185302_k, "Shar"), new EnchantEntry(Enchantments.field_77334_n, "Fire"), new EnchantEntry(Enchantments.field_180313_o, "Kb"), new EnchantEntry(Enchantments.field_185307_s, "Unb"), new EnchantEntry(Enchantments.field_185309_u, "Pow"), new EnchantEntry(Enchantments.field_185312_x, "Inf"), new EnchantEntry(Enchantments.field_185310_v, "Punch")};
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        float scale1 = 0.3f;
        GlStateManager.translate((float)(x - 3), (float)(y + 10), (float)0.0f);
        GlStateManager.scale((float)0.3f, (float)0.3f, (float)0.3f);
        GlStateManager.popMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.field_77023_b = -100.0f;
        GlStateManager.disableDepth();
        renderItem.func_175042_a(stack, x, y);
        renderItem.func_180453_a(fontRenderer, stack, x, y, null);
        GlStateManager.enableDepth();
        EnchantEntry[] array = enchants;
        int length = enchants.length;
        for (int i = 0; i < length; ++i) {
            EnchantEntry enchant = array[i];
            int level = EnchantmentHelper.func_77506_a((Enchantment)enchant.getEnchant(), (ItemStack)stack);
            String levelDisplay = "" + level;
            if (level > 10) {
                levelDisplay = "10+";
            }
            if (level <= 0) continue;
            float scale2 = 0.32f;
            GlStateManager.translate((float)(x - 2), (float)(y + 1), (float)0.0f);
            GlStateManager.scale((float)0.42f, (float)0.42f, (float)0.42f);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            fontRenderer.drawString("\u00a7f" + enchant.getName() + " " + levelDisplay, (float)(20 - fontRenderer.getStringWidth("\u00a7f" + enchant.getName() + " " + levelDisplay) / 2), 0.0f, Color.WHITE.getRGB(), true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale((float)2.42f, (float)2.42f, (float)2.42f);
            GlStateManager.translate((float)(-x), (float)(-y), (float)0.0f);
            y += (int)((float)(fontRenderer.FONT_HEIGHT + 3) * 0.28f);
        }
        renderItem.field_77023_b = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    public static class EnchantEntry {
        private Enchantment enchant;
        private String name;

        public EnchantEntry(Enchantment enchant, String name) {
            this.enchant = enchant;
            this.name = name;
        }

        public Enchantment getEnchant() {
            return this.enchant;
        }

        public String getName() {
            return this.name;
        }
    }
}

