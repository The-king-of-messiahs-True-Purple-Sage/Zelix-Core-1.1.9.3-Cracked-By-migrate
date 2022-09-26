/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

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

public class Glowing
extends Hack {
    public Glowing() {
        super("Glowing", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Glows all entities around you.";
    }

    @Override
    public void onDisable() {
        for (Entity object : Utils.getEntityList()) {
            Entity entity = object;
            if (!entity.func_184202_aL()) continue;
            entity.func_184195_f(false);
        }
        super.onDisable();
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Entity object : Utils.getEntityList()) {
            Entity entity = object;
            if (this.checkEntity(entity) == null || entity == Wrapper.INSTANCE.player() || entity.func_184202_aL()) continue;
            entity.func_184195_f(true);
        }
        super.onRenderWorldLast(event);
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

