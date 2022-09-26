/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public class EntityFakePlayer
extends EntityOtherPlayerMP {
    public EntityFakePlayer() {
        super((World)WMinecraft.getWorld(), WMinecraft.getPlayer().getGameProfile());
        this.copyLocationAndAnglesFrom((Entity)WMinecraft.getPlayer());
        this.field_71071_by.copyInventory(WMinecraft.getPlayer().inventory);
        this.func_184212_Q().func_187227_b(EntityPlayer.field_184827_bp, WMinecraft.getPlayer().func_184212_Q().func_187225_a(EntityPlayer.field_184827_bp));
        this.field_70759_as = WMinecraft.getPlayer().rotationYawHead;
        this.field_70761_aq = WMinecraft.getPlayer().renderYawOffset;
        this.field_71094_bP = this.posX;
        this.field_71095_bQ = this.posY;
        this.field_71085_bR = this.posZ;
        WMinecraft.getWorld().addEntityToWorld(this.getEntityId(), (Entity)this);
    }

    public void resetPlayerPosition() {
        WMinecraft.getPlayer().setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }

    public void despawn() {
        WMinecraft.getWorld().removeEntityFromWorld(this.getEntityId());
    }
}

