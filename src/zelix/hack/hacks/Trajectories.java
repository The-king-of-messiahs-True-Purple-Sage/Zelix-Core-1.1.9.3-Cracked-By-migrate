/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemLingeringPotion
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;

public class Trajectories
extends Hack {
    public Trajectories() {
        super("Trajectories", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Predicts the flight path of arrows and throwable items.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        float yawAim = Wrapper.INSTANCE.player().rotationYaw;
        float pitchAim = Wrapper.INSTANCE.player().rotationPitch;
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack == null) {
            return;
        }
        Item item = stack.getItem();
        if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod)) {
            return;
        }
        boolean usingBow = player.inventory.getCurrentItem().getItem() instanceof ItemBow;
        double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)event.getPartialTicks() - (double)(MathHelper.func_76134_b((float)((float)Math.toRadians(yawAim))) * 0.16f);
        double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)event.getPartialTicks() + (double)player.getEyeHeight() - 0.1;
        double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)event.getPartialTicks() - (double)(MathHelper.func_76126_a((float)((float)Math.toRadians(yawAim))) * 0.16f);
        float arrowMotionFactor = usingBow ? 1.0f : 0.4f;
        float yaw = (float)Math.toRadians(yawAim);
        float pitch = (float)Math.toRadians(pitchAim);
        float arrowMotionX = -MathHelper.func_76126_a((float)yaw) * MathHelper.func_76134_b((float)pitch) * arrowMotionFactor;
        float arrowMotionY = -MathHelper.func_76126_a((float)pitch) * arrowMotionFactor;
        float arrowMotionZ = MathHelper.func_76134_b((float)yaw) * MathHelper.func_76134_b((float)pitch) * arrowMotionFactor;
        double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX = (float)((double)arrowMotionX / arrowMotion);
        arrowMotionY = (float)((double)arrowMotionY / arrowMotion);
        arrowMotionZ = (float)((double)arrowMotionZ / arrowMotion);
        if (usingBow) {
            float bowPower = (float)(72000 - player.func_184605_cv()) / 20.0f;
            if ((bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f) > 1.0f) {
                bowPower = 1.0f;
            }
            if (bowPower <= 0.1f) {
                bowPower = 1.0f;
            }
            arrowMotionX *= (bowPower *= 3.0f);
            arrowMotionY *= bowPower;
            arrowMotionZ *= bowPower;
        } else {
            arrowMotionX = (float)((double)arrowMotionX * 1.5);
            arrowMotionY = (float)((double)arrowMotionY * 1.5);
            arrowMotionZ = (float)((double)arrowMotionZ * 1.5);
        }
        GL11.glPushMatrix();
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)32925);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)1.8f);
        RenderManager renderManager = Wrapper.INSTANCE.mc().getRenderManager();
        double gravity = usingBow ? 0.05 : (item instanceof ItemPotion ? 0.4 : (item instanceof ItemFishingRod ? 0.15 : 0.03));
        Vec3d playerVector = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GL11.glBegin((int)3);
        for (int i = 0; i < 1000; ++i) {
            GL11.glVertex3d((double)(arrowPosX - renderManager.viewerPosX), (double)(arrowPosY - renderManager.viewerPosY), (double)(arrowPosZ - renderManager.viewerPosZ));
            arrowMotionX = (float)((double)arrowMotionX * 0.999);
            arrowMotionY = (float)((double)arrowMotionY * 0.999);
            arrowMotionZ = (float)((double)arrowMotionZ * 0.999);
            arrowMotionY = (float)((double)arrowMotionY - gravity * 0.1);
            if (Wrapper.INSTANCE.world().func_72933_a(playerVector, new Vec3d(arrowPosX += (double)arrowMotionX * 0.1, arrowPosY += (double)arrowMotionY * 0.1, arrowPosZ += (double)arrowMotionZ * 0.1)) != null) break;
        }
        GL11.glEnd();
        double renderX = arrowPosX - renderManager.viewerPosX;
        double renderY = arrowPosY - renderManager.viewerPosY;
        double renderZ = arrowPosZ - renderManager.viewerPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(renderX - 0.5, renderY - 0.5, renderZ - 0.5, renderX + 0.5, renderY + 0.5, renderZ + 0.5);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.15f);
        RenderUtils.drawColorBox(bb, 1.0f, 1.0f, 1.0f, 0.15f);
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)0.5);
        RenderUtils.drawSelectionBoundingBox(bb);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)32925);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
        super.onRenderWorldLast(event);
    }
}

