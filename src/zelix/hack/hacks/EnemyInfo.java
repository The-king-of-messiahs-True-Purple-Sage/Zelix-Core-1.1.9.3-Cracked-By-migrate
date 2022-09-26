/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class EnemyInfo
extends Hack {
    private ResourceLocation hpbar = new ResourceLocation("healthbar.png");
    private boolean show;
    private String enemyNickname;
    private double enemyHp;
    private double enemyDistance;
    private Entity entity;
    private EntityPlayer entityPlayer;
    private int hpbarwidth;

    public EnemyInfo() {
        super("EnemyInfo", HackCategory.VISUAL);
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        RayTraceResult objectMouseOver = Wrapper.INSTANCE.mc().objectMouseOver;
        if (objectMouseOver != null) {
            if (objectMouseOver.field_72313_a == RayTraceResult.Type.ENTITY) {
                this.entity = objectMouseOver.field_72308_g;
                if (this.entity instanceof EntityPlayer) {
                    this.entityPlayer = (EntityPlayer)this.entity;
                    this.enemyNickname = this.entityPlayer.getName();
                    this.enemyHp = this.entityPlayer.getHealth();
                    this.enemyDistance = this.entityPlayer.getDistanceToEntity((Entity)Wrapper.INSTANCE.mc().thePlayer);
                    this.show = true;
                }
            } else {
                this.show = false;
            }
        } else {
            this.show = false;
        }
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        RenderGameOverlayEvent.ElementType elementType = event.getType();
        event.getType();
        if (!elementType.equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            return;
        }
        if (this.show && Wrapper.INSTANCE.mc().theWorld != null && Wrapper.INSTANCE.mc().thePlayer != null) {
            DecimalFormat decimalFormat = new DecimalFormat("###.#");
            FontRenderer fr = Wrapper.INSTANCE.mc().fontRendererObj;
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            Gui.drawRect((int)(sr.getScaledWidth() / 2 + 120), (int)(sr.getScaledHeight() / 2 + 25), (int)(sr.getScaledWidth() / 2 + 20), (int)(sr.getScaledHeight() / 2 + 90), (int)0x4F000000);
            fr.drawString(this.enemyNickname, sr.getScaledWidth() / 2 + 60, sr.getScaledHeight() / 2 + 30, -1);
            GL11.glPushMatrix();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            fr.drawString("HP: " + Math.round(this.enemyHp), (sr.getScaledWidth() / 2 + 60) * 2, (sr.getScaledHeight() / 2 + 40) * 2, -1);
            fr.drawString("Distanse: " + decimalFormat.format(this.enemyDistance), (sr.getScaledWidth() / 2 + 60) * 2, (sr.getScaledHeight() / 2 + 45) * 2, -1);
            GL11.glPopMatrix();
            EnemyInfo.drawEntityOnScreen(sr.getScaledWidth() / 2 + 40, sr.getScaledHeight() / 2 + 80, 25, 0.0f, 0.0f, (EntityLivingBase)this.entityPlayer);
            Wrapper.INSTANCE.mc().renderEngine.bindTexture(this.hpbar);
            Gui.drawScaledCustomSizeModalRect((int)(sr.getScaledWidth() / 2 + 20), (int)(sr.getScaledHeight() / 2 + 89), (float)0.0f, (float)0.0f, (int)256, (int)256, (int)(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 2 + 100), (int)1, (float)256.0f, (float)256.0f);
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        if (Wrapper.INSTANCE.mc().theWorld != null && Wrapper.INSTANCE.mc().thePlayer != null) {
            GlStateManager.enableColorMaterial();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)posX, (float)posY, (float)50.0f);
            GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
            GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            float f = ent.renderYawOffset;
            float f1 = ent.rotationYaw;
            float f2 = ent.rotationPitch;
            float f3 = ent.prevRotationYawHead;
            float f4 = ent.rotationYawHead;
            GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-((float)Math.atan(mouseY / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            ent.renderYawOffset = (float)Math.atan(mouseX / 40.0f) * 20.0f;
            ent.rotationYaw = (float)Math.atan(mouseX / 40.0f) * 40.0f;
            ent.rotationPitch = -((float)Math.atan(mouseY / 40.0f)) * 20.0f;
            ent.rotationYawHead = ent.rotationYaw;
            ent.prevRotationYawHead = ent.rotationYaw;
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.0f);
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0f);
            rendermanager.setRenderShadow(false);
            rendermanager.func_188391_a((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
            rendermanager.setRenderShadow(true);
            ent.renderYawOffset = f;
            ent.rotationYaw = f1;
            ent.rotationPitch = f2;
            ent.prevRotationYawHead = f3;
            ent.rotationYawHead = f4;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
        }
    }
}

