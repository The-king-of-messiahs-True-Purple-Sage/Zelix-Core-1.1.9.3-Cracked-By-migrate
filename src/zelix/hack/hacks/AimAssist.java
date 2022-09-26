/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class AimAssist
extends Hack {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static NumberValue speed;
    public static NumberValue compliment;
    public static NumberValue fov;
    public static NumberValue distance;
    public static NumberValue extradistance;
    public static ModeValue priority;
    public static BooleanValue clickAim;
    public static BooleanValue weaponOnly;
    public static BooleanValue breakBlocks;
    public static BooleanValue blatantMode;
    public static BooleanValue walls;
    public EntityLivingBase target;

    public AimAssist() {
        super("AimAssist", HackCategory.COMBAT);
        Value[] valueArray = new Value[1];
        priority = new ModeValue("Priority", new Mode("Closest", true), new Mode("Health", false));
        valueArray[0] = priority;
        this.addValue(valueArray);
        Value[] valueArray2 = new Value[1];
        speed = new NumberValue("Speed 1", 45.0, 5.0, 100.0);
        valueArray2[0] = speed;
        this.addValue(valueArray2);
        Value[] valueArray3 = new Value[1];
        compliment = new NumberValue("Speed 2", 15.0, 2.0, 97.0);
        valueArray3[0] = compliment;
        this.addValue(valueArray3);
        Value[] valueArray4 = new Value[1];
        fov = new NumberValue("FOV", 90.0, 15.0, 360.0);
        valueArray4[0] = fov;
        this.addValue(valueArray4);
        Value[] valueArray5 = new Value[1];
        distance = new NumberValue("Distance", 4.5, 1.0, 10.0);
        valueArray5[0] = distance;
        this.addValue(valueArray5);
        Value[] valueArray6 = new Value[1];
        extradistance = new NumberValue("Extra-Distance", 0.0, 0.0, 100.0);
        valueArray6[0] = extradistance;
        this.addValue(valueArray6);
        Value[] valueArray7 = new Value[1];
        clickAim = new BooleanValue("ClickAim", true);
        valueArray7[0] = clickAim;
        this.addValue(valueArray7);
        Value[] valueArray8 = new Value[1];
        walls = new BooleanValue("ThroughWalls", false);
        valueArray8[0] = walls;
        this.addValue(valueArray8);
        Value[] valueArray9 = new Value[1];
        breakBlocks = new BooleanValue("Break blocks", true);
        valueArray9[0] = breakBlocks;
        this.addValue(valueArray9);
        Value[] valueArray10 = new Value[1];
        weaponOnly = new BooleanValue("Weapon only", false);
        valueArray10[0] = weaponOnly;
        this.addValue(valueArray10);
        Value[] valueArray11 = new Value[1];
        blatantMode = new BooleanValue("Blatant mode", false);
        valueArray11[0] = blatantMode;
        this.addValue(valueArray11);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Block bl;
        BlockPos p;
        this.getTarget();
        if (((Boolean)breakBlocks.getValue()).booleanValue() && AimAssist.mc.objectMouseOver != null && (p = AimAssist.mc.objectMouseOver.func_178782_a()) != null && (bl = AimAssist.mc.theWorld.func_180495_p(p).getBlock()) != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof Block) {
            return;
        }
        if (!((Boolean)weaponOnly.getValue()).booleanValue() || Utils.isPlayerHoldingWeapon()) {
            if (((Boolean)clickAim.getValue()).booleanValue()) {
                if (Mouse.isButtonDown((int)0) && this.target != null) {
                    if (((Boolean)blatantMode.getValue()).booleanValue()) {
                        Utils.Player.aim((Entity)this.target, 0.0f, false);
                    } else {
                        double n = Utils.Player.fovFromEntity((Entity)this.target);
                        if (n > 1.0 || n < -1.0) {
                            double complimentSpeed = n * (ThreadLocalRandom.current().nextDouble((Double)compliment.getValue() - 1.47328, (Double)compliment.getValue() + 2.48293) / 100.0);
                            double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble((Double)speed.getValue() - 4.723847, (Double)speed.getValue());
                            float val = (float)(-(complimentSpeed + n / (101.0 - (double)((float)ThreadLocalRandom.current().nextDouble((Double)speed.getValue() - 4.723847, (Double)speed.getValue())))));
                            AimAssist.mc.thePlayer.rotationYaw += val;
                        }
                    }
                }
            } else if (this.target != null) {
                if (((Boolean)blatantMode.getValue()).booleanValue()) {
                    Utils.Player.aim((Entity)this.target, 0.0f, false);
                } else {
                    double n = Utils.Player.fovFromEntity((Entity)this.target);
                    if (n > 1.0 || n < -1.0) {
                        double complimentSpeed = n * (ThreadLocalRandom.current().nextDouble((Double)compliment.getValue() - 1.47328, (Double)compliment.getValue() + 2.48293) / 100.0);
                        double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble((Double)speed.getValue() - 4.723847, (Double)speed.getValue());
                        float val = (float)(-(complimentSpeed + n / (101.0 - (double)((float)ThreadLocalRandom.current().nextDouble((Double)speed.getValue() - 4.723847, (Double)speed.getValue())))));
                        AimAssist.mc.thePlayer.rotationYaw += val;
                    }
                }
            }
        }
        this.target = null;
    }

    public void getTarget() {
        for (Entity object : Utils.getEntityList()) {
            EntityLivingBase entity;
            if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase)object)) continue;
            this.target = entity;
        }
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
        if (!ValidUtils.isInAttackFOV(entity, ((Double)fov.getValue()).intValue())) {
            return false;
        }
        if (!ValidUtils.isInAttackRange(entity, ((Double)distance.getValue()).floatValue() + ((Double)extradistance.getValue()).floatValue())) {
            return false;
        }
        if (!ValidUtils.isTeam(entity)) {
            return false;
        }
        if (!ValidUtils.pingCheck(entity)) {
            return false;
        }
        if (!this.isPriority(entity)) {
            return false;
        }
        return (Boolean)walls.getValue() != false || Wrapper.INSTANCE.player().canEntityBeSeen((Entity)entity);
    }

    boolean isPriority(EntityLivingBase entity) {
        return priority.getMode("Closest").isToggled() && ValidUtils.isClosest(entity, this.target) || priority.getMode("Health").isToggled() && ValidUtils.isLowHealth(entity, this.target);
    }
}

