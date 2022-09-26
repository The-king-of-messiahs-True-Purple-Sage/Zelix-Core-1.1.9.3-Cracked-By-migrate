/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.EnemyManager;
import zelix.managers.FriendManager;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;

public class Tracers
extends Hack {
    public Tracers() {
        super("Tracers", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Traces a line to the players.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityLivingBase) || object instanceof EntityArmorStand) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            this.render(entity, event.getPartialTicks());
        }
        super.onRenderWorldLast(event);
    }

    void render(EntityLivingBase entity, float ticks) {
        if (ValidUtils.isValidEntity(entity) || entity == Wrapper.INSTANCE.player()) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            String ID = Utils.getPlayerName(player);
            if (EnemyManager.enemysList.contains(ID)) {
                RenderUtils.drawTracer((Entity)entity, 0.8f, 0.3f, 0.0f, 1.0f, ticks);
                return;
            }
            if (FriendManager.friendsList.contains(ID)) {
                RenderUtils.drawTracer((Entity)entity, 0.0f, 0.7f, 1.0f, 1.0f, ticks);
                return;
            }
        }
        if (HackManager.getHack("Targets").isToggledValue("Murder")) {
            if (Utils.isMurder(entity)) {
                RenderUtils.drawTracer((Entity)entity, 1.0f, 0.0f, 0.8f, 1.0f, ticks);
                return;
            }
            if (Utils.isDetect(entity)) {
                RenderUtils.drawTracer((Entity)entity, 0.0f, 0.0f, 1.0f, 0.5f, ticks);
                return;
            }
        }
        if (entity.isInvisible()) {
            RenderUtils.drawTracer((Entity)entity, 0.0f, 0.0f, 0.0f, 0.5f, ticks);
            return;
        }
        if (entity.hurtTime > 0) {
            RenderUtils.drawTracer((Entity)entity, 1.0f, 0.0f, 0.0f, 1.0f, ticks);
            return;
        }
        RenderUtils.drawTracer((Entity)entity, 1.0f, 1.0f, 1.0f, 0.5f, ticks);
    }
}

