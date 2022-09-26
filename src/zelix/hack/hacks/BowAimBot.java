/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class BowAimBot
extends Hack {
    public BooleanValue walls = new BooleanValue("ThroughWalls", false);
    public NumberValue yaw = new NumberValue("Yaw", 22.0, 0.0, 50.0);
    public NumberValue FOV = new NumberValue("FOV", 90.0, 1.0, 180.0);
    public EntityLivingBase target;
    public float rangeAimVelocity = 0.0f;

    public BowAimBot() {
        super("BowAimBot", HackCategory.COMBAT);
        this.addValue(this.walls, this.yaw, this.FOV);
    }

    @Override
    public String getDescription() {
        return "Automatically aims your bow at the closest entity.";
    }

    @Override
    public void onDisable() {
        this.target = null;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ItemStack itemStack = Wrapper.INSTANCE.inventory().getCurrentItem();
        if (itemStack == null || !(itemStack.getItem() instanceof ItemBow)) {
            return;
        }
        if (!Wrapper.INSTANCE.mcSettings().keyBindUseItem.isKeyDown()) {
            return;
        }
        this.target = this.getClosestEntity();
        if (this.target == null) {
            return;
        }
        int rangeCharge = Wrapper.INSTANCE.player().func_184605_cv();
        this.rangeAimVelocity = rangeCharge / 20;
        this.rangeAimVelocity = (this.rangeAimVelocity * this.rangeAimVelocity + this.rangeAimVelocity * 2.0f) / 3.0f;
        this.rangeAimVelocity = 1.0f;
        if (this.rangeAimVelocity > 1.0f) {
            this.rangeAimVelocity = 1.0f;
        }
        double posX = this.target.posX - Wrapper.INSTANCE.player().posX;
        double posY = this.target.posY + (double)this.target.getEyeHeight() - 0.15 - Wrapper.INSTANCE.player().posY - (double)Wrapper.INSTANCE.player().getEyeHeight();
        double posZ = this.target.posZ - Wrapper.INSTANCE.player().posZ;
        double y2 = Math.sqrt(posX * posX + posZ * posZ);
        float g = 0.006f;
        float tmp = (float)((double)(this.rangeAimVelocity * this.rangeAimVelocity * this.rangeAimVelocity * this.rangeAimVelocity) - (double)g * ((double)g * (y2 * y2) + 2.0 * posY * (double)(this.rangeAimVelocity * this.rangeAimVelocity)));
        float pitch = (float)(-Math.toDegrees(Math.atan(((double)(this.rangeAimVelocity * this.rangeAimVelocity) - Math.sqrt(tmp)) / ((double)g * y2))));
        Utils.assistFaceEntity((Entity)this.target, ((Double)this.yaw.getValue()).floatValue(), 0.0f);
        Wrapper.INSTANCE.player().rotationPitch = pitch;
        super.onClientTick(event);
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (ValidUtils.isValidEntity(entity)) {
            return false;
        }
        if (!ValidUtils.isNoScreen()) {
            return false;
        }
        if (entity == Wrapper.INSTANCE.player()) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (ValidUtils.isBot(entity)) {
            return false;
        }
        if (!ValidUtils.isFriendEnemy(entity)) {
            return false;
        }
        if (!ValidUtils.isInvisible(entity)) {
            return false;
        }
        if (!ValidUtils.isInAttackFOV(entity, ((Double)this.FOV.getValue()).intValue())) {
            return false;
        }
        if (!ValidUtils.isTeam(entity)) {
            return false;
        }
        if (!ValidUtils.pingCheck(entity)) {
            return false;
        }
        return (Boolean)this.walls.getValue() != false || Wrapper.INSTANCE.player().canEntityBeSeen((Entity)entity);
    }

    EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (Entity o : Utils.getEntityList()) {
            EntityLivingBase entity;
            if (!(o instanceof EntityLivingBase) || o instanceof EntityArmorStand || !this.check(entity = (EntityLivingBase)o) || closestEntity != null && !(Wrapper.INSTANCE.player().getDistanceToEntity((Entity)entity) < Wrapper.INSTANCE.player().getDistanceToEntity((Entity)closestEntity))) continue;
            closestEntity = entity;
        }
        return closestEntity;
    }
}

