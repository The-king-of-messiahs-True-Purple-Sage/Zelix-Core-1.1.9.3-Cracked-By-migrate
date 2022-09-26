/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.EntityFireball
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.RobotUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class FireballReturn
extends Hack {
    public NumberValue yaw = new NumberValue("Yaw", 25.0, 0.0, 50.0);
    public NumberValue pitch = new NumberValue("Pitch", 25.0, 0.0, 50.0);
    public NumberValue range = new NumberValue("Range", 10.0, 0.1, 10.0);
    public EntityFireball target;
    public TimerUtils timer;

    public FireballReturn() {
        super("FireballReturn", HackCategory.COMBAT);
        this.addValue(this.yaw, this.pitch, this.range);
        this.timer = new TimerUtils();
    }

    @Override
    public String getDescription() {
        return "Beats fireballs when they fly at you.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.updateTarget();
        this.attackTarget();
        super.onClientTick(event);
    }

    void updateTarget() {
        for (Entity object : Utils.getEntityList()) {
            EntityFireball entity;
            if (!(object instanceof EntityFireball) || !this.isInAttackRange(entity = (EntityFireball)object) || entity.isDead || entity.onGround || !entity.canAttackWithItem()) continue;
            this.target = entity;
        }
    }

    void attackTarget() {
        if (this.target == null) {
            return;
        }
        Utils.assistFaceEntity((Entity)this.target, ((Double)this.yaw.getValue()).floatValue(), ((Double)this.pitch.getValue()).floatValue());
        int currentCPS = Utils.random(4, 7);
        if (this.timer.isDelay(1000 / currentCPS)) {
            RobotUtils.clickMouse(0);
            this.timer.setLastMS();
            this.target = null;
        }
    }

    public boolean isInAttackRange(EntityFireball entity) {
        return entity.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) <= ((Double)this.range.getValue()).floatValue();
    }
}

