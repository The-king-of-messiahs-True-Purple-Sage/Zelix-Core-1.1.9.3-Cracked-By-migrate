/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.AutoShield;
import zelix.utils.RobotUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Trigger
extends Hack {
    public BooleanValue autoDelay = new BooleanValue("AutoDelay", true);
    public BooleanValue advanced = new BooleanValue("Advanced", false);
    public ModeValue mode = new ModeValue("Mode", new Mode("Click", true), new Mode("Attack", false));
    public NumberValue minCPS = new NumberValue("MinCPS", 4.0, 1.0, 20.0);
    public NumberValue maxCPS = new NumberValue("MaxCPS", 8.0, 1.0, 20.0);
    public EntityLivingBase target;
    public TimerUtils timer;

    public Trigger() {
        super("Trigger", HackCategory.COMBAT);
        this.addValue(this.mode, this.advanced, this.autoDelay, this.minCPS, this.maxCPS);
        this.timer = new TimerUtils();
    }

    @Override
    public String getDescription() {
        return "Automatically attacks the entity you're looking at.";
    }

    @Override
    public void onDisable() {
        this.target = null;
        AutoShield.block(false);
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.updateTarget();
        this.attackTarget(this.target);
        super.onClientTick(event);
    }

    void attackTarget(EntityLivingBase target) {
        if (this.check(target)) {
            if (((Boolean)this.autoDelay.getValue()).booleanValue()) {
                if (Wrapper.INSTANCE.player().func_184825_o(0.0f) == 1.0f) {
                    this.processAttack(target, false);
                }
            } else {
                int currentCPS = Utils.random(((Double)this.minCPS.getValue()).intValue(), ((Double)this.maxCPS.getValue()).intValue());
                if (this.timer.isDelay(1000 / currentCPS)) {
                    this.processAttack(target, true);
                    this.timer.setLastMS();
                }
            }
            return;
        }
        AutoShield.block(false);
    }

    public void processAttack(EntityLivingBase entity, boolean packet) {
        AutoShield.block(false);
        float sharpLevel = EnchantmentHelper.getModifierForCreature((ItemStack)Wrapper.INSTANCE.player().func_184614_ca(), (EnumCreatureAttribute)this.target.getCreatureAttribute());
        if (this.mode.getMode("Click").isToggled()) {
            RobotUtils.clickMouse(0);
        } else {
            if (packet) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)this.target));
            } else {
                Utils.attack((Entity)this.target);
            }
            Utils.swingMainHand();
            if (sharpLevel > 0.0f) {
                Wrapper.INSTANCE.player().onEnchantmentCritical((Entity)this.target);
            }
        }
        AutoShield.block(true);
    }

    void updateTarget() {
        RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
        if (object == null) {
            return;
        }
        EntityLivingBase entity = null;
        if (this.target != entity) {
            this.target = null;
        }
        if (object.field_72313_a == RayTraceResult.Type.ENTITY) {
            if (object.field_72308_g instanceof EntityLivingBase) {
                this.target = entity = (EntityLivingBase)object.field_72308_g;
            }
        } else if (object.field_72313_a != RayTraceResult.Type.ENTITY && ((Boolean)this.advanced.getValue()).booleanValue()) {
            entity = this.getClosestEntity();
        }
        if (entity != null) {
            this.target = entity;
        }
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
        if (((Boolean)this.advanced.getValue()).booleanValue()) {
            if (!ValidUtils.isInAttackFOV(entity, 50)) {
                return false;
            }
            if (!ValidUtils.isInAttackRange(entity, 4.7f)) {
                return false;
            }
        }
        if (!ValidUtils.isTeam(entity)) {
            return false;
        }
        if (!ValidUtils.pingCheck(entity)) {
            return false;
        }
        return Wrapper.INSTANCE.player().canEntityBeSeen((Entity)entity);
    }
}

