/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.AStarCustomPathFinder;
import zelix.utils.TimerUtils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class TpAura
extends Hack {
    private TimerUtils cps = new TimerUtils();
    public static TimerUtils timer = new TimerUtils();
    private ArrayList<Vec3d> path = new ArrayList();
    private List<Vec3d>[] test = new ArrayList[50];
    public int ticks;
    public NumberValue delay = new NumberValue("Delay", 4.0, 0.0, 20.0);
    public NumberValue range = new NumberValue("Range", 10.0, 10.0, 100.0);
    public BooleanValue players = new BooleanValue("Players", true);
    public BooleanValue mobs;
    public BooleanValue atkinv = new BooleanValue("Invisible", true);
    public BooleanValue animals;
    public NumberValue maxtarget;
    public NumberValue dashDistance;

    public TpAura() {
        super("TpAura", HackCategory.COMBAT);
        this.mobs = new BooleanValue("Mobs", true);
        this.animals = new BooleanValue("Animals", false);
        this.maxtarget = new NumberValue("MaxTarget", 3.0, 1.0, 10.0);
        this.dashDistance = new NumberValue("DashDistance", 5.0, 2.0, 10.0);
        this.addValue(this.players, this.animals, this.mobs, this.atkinv, this.delay, this.dashDistance, this.maxtarget, this.range);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ++this.ticks;
        if (this.cps.hasReached(((Double)this.delay.getValue()).intValue() * 100) && this.getTargets().size() > 0) {
            this.test = new ArrayList[50];
            for (int i = 0; i < (this.getTargets().size() > ((Double)this.maxtarget.getValue()).intValue() ? ((Double)this.maxtarget.getValue()).intValue() : this.getTargets().size()); ++i) {
                EntityLivingBase T = this.getTargets().get(i);
                Vec3d topFrom = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
                Vec3d to = new Vec3d(T.posX, T.posY, T.posZ);
                this.path = this.computePath(topFrom, to);
                this.test[i] = this.path;
                if (!ValidUtils.isTeam(T)) continue;
                for (Vec3d pathElm : this.path) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(pathElm.field_72450_a, pathElm.field_72448_b + 0.1451, pathElm.field_72449_c, true));
                }
                Wrapper.INSTANCE.player().func_184609_a(EnumHand.MAIN_HAND);
                Wrapper.INSTANCE.controller().attackEntity((EntityPlayer)Wrapper.INSTANCE.player(), (Entity)T);
                Collections.reverse(this.path);
                this.cps.reset();
                for (Vec3d pathElm : this.path) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(pathElm.field_72450_a, pathElm.field_72448_b, pathElm.field_72449_c + 0.1451, true));
                }
            }
        }
    }

    public List<EntityLivingBase> getTargets() {
        ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (Object o : Wrapper.INSTANCE.world().getLoadedEntityList()) {
            EntityLivingBase entity;
            if (!(o instanceof EntityLivingBase) || (entity = (EntityLivingBase)o) == null || entity.isDead || Wrapper.INSTANCE.player().isDead || !(entity instanceof EntityLivingBase) || entity == Wrapper.INSTANCE.player() || !((double)Wrapper.INSTANCE.player().getDistanceToEntity((Entity)entity) <= (Double)this.range.getValue()) || entity.isPlayerSleeping()) continue;
            if (entity instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && !entity.isInvisible()) {
                targets.add(entity);
            } else if (entity instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && entity.isInvisible() && !((Boolean)this.atkinv.getValue()).booleanValue()) {
                targets.remove(entity);
            } else if (entity instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && entity.isInvisible() && ((Boolean)this.atkinv.getValue()).booleanValue()) {
                targets.add(entity);
            }
            if (entity instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue() && !entity.isInvisible()) {
                targets.add(entity);
            } else if (entity instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue() && entity.isInvisible() && !((Boolean)this.atkinv.getValue()).booleanValue()) {
                targets.remove(entity);
            } else if (entity instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue() && entity.isInvisible() && ((Boolean)this.atkinv.getValue()).booleanValue()) {
                targets.add(entity);
            }
            if (entity instanceof EntityMob && ((Boolean)this.mobs.getValue()).booleanValue() && !entity.isInvisible()) {
                targets.add(entity);
                continue;
            }
            if (entity instanceof EntityMob && ((Boolean)this.mobs.getValue()).booleanValue() && entity.isInvisible() && !((Boolean)this.atkinv.getValue()).booleanValue()) {
                targets.remove(entity);
                continue;
            }
            if (!(entity instanceof EntityMob) || !((Boolean)this.mobs.getValue()).booleanValue() || !entity.isInvisible() || !((Boolean)this.atkinv.getValue()).booleanValue()) continue;
            targets.add(entity);
        }
        targets.sort((o1, o2) -> (int)(o1.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) * 1000.0f - o2.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) * 1000.0f));
        return targets;
    }

    private ArrayList<Vec3d> computePath(Vec3d topFrom, Vec3d to) {
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3d lastLoc = null;
        Vec3d lastDashLoc = null;
        ArrayList<Vec3d> path = new ArrayList<Vec3d>();
        ArrayList<Vec3d> pathFinderPath = pathfinder.getPath();
        for (Vec3d pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.func_72441_c(0.5, 0.0, 0.5));
                }
                path.add(pathElm.func_72441_c(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.func_72436_e(lastDashLoc) > (Double)this.dashDistance.getValue() * (Double)this.dashDistance.getValue()) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.field_72450_a, pathElm.field_72450_a);
                    double smallY = Math.min(lastDashLoc.field_72448_b, pathElm.field_72448_b);
                    double smallZ = Math.min(lastDashLoc.field_72449_c, pathElm.field_72449_c);
                    double bigX = Math.max(lastDashLoc.field_72450_a, pathElm.field_72450_a);
                    double bigY = Math.max(lastDashLoc.field_72448_b, pathElm.field_72448_b);
                    double bigZ = Math.max(lastDashLoc.field_72449_c, pathElm.field_72449_c);
                    int x = (int)smallX;
                    block1: while ((double)x <= bigX) {
                        int y = (int)smallY;
                        while ((double)y <= bigY) {
                            int z = (int)smallZ;
                            while ((double)z <= bigZ) {
                                if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break block1;
                                }
                                ++z;
                            }
                            ++y;
                        }
                        ++x;
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.func_72441_c(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }
}

