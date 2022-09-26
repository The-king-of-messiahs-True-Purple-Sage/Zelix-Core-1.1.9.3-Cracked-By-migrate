/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class WallHack
extends Hack {
    public WallHack() {
        super("WallHack", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "The skin of the entities around you glows.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GlStateManager.clear((int)256);
        RenderHelper.enableStandardItemLighting();
        Iterator<Entity> iterator = Utils.getEntityList().iterator();
        while (iterator.hasNext()) {
            Entity object;
            Entity entity = object = iterator.next();
            this.render(entity, event.getPartialTicks());
        }
        super.onRenderWorldLast(event);
    }

    void render(Entity entity, float ticks) {
        Entity ent = this.checkEntity(entity);
        if (ent == null || ent == Wrapper.INSTANCE.player()) {
            return;
        }
        if (ent == Wrapper.INSTANCE.mc().getRenderViewEntity() && Wrapper.INSTANCE.mcSettings().thirdPersonView == 0) {
            return;
        }
        Wrapper.INSTANCE.mc().entityRenderer.disableLightmap();
        Wrapper.INSTANCE.mc().getRenderManager().func_188388_a(ent, ticks, false);
        Wrapper.INSTANCE.mc().entityRenderer.enableLightmap();
    }

    Entity checkEntity(Entity e) {
        Entity entity = null;
        Hack targets = HackManager.getHack("Targets");
        if (targets.isToggledValue("Players") && e instanceof EntityPlayer) {
            entity = e;
        } else if (targets.isToggledValue("Mobs") && e instanceof EntityLiving) {
            entity = e;
        } else if (e instanceof EntityItem) {
            entity = e;
        } else if (e instanceof EntityArrow) {
            entity = e;
        }
        return entity;
    }
}

