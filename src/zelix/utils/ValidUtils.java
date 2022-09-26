/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 */
package zelix.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import zelix.hack.Hack;
import zelix.hack.hacks.AntiBot;
import zelix.managers.EnemyManager;
import zelix.managers.FriendManager;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class ValidUtils {
    public static boolean isLowHealth(EntityLivingBase entity, EntityLivingBase entityPriority) {
        return entityPriority == null || entity.getHealth() < entityPriority.getHealth();
    }

    public static boolean isClosest(EntityLivingBase entity, EntityLivingBase entityPriority) {
        return entityPriority == null || Wrapper.INSTANCE.player().getDistanceToEntity((Entity)entity) < Wrapper.INSTANCE.player().getDistanceToEntity((Entity)entityPriority);
    }

    public static boolean isInAttackFOV(EntityLivingBase entity, int fov) {
        return Utils.getDistanceFromMouse(entity) <= fov;
    }

    public static boolean isInAttackRange(EntityLivingBase entity, float range) {
        return entity.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) <= range;
    }

    public static boolean isValidEntity(EntityLivingBase e) {
        Hack targets = HackManager.getHack("Targets");
        if (targets.isToggled()) {
            if (targets.isToggledValue("Players") && e instanceof EntityPlayer) {
                return !targets.isToggledValue("Sleeping") && e.isPlayerSleeping();
            }
            if (targets.isToggledValue("Mobs") && e instanceof EntityLiving) {
                return false;
            }
        }
        return true;
    }

    public static boolean pingCheck(EntityLivingBase entity) {
        Hack hack = HackManager.getHack("AntiBot");
        if (hack.isToggled() && hack.isToggledValue("PingCheck") && entity instanceof EntityPlayer) {
            return Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfo(entity.getUniqueID()) != null && Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfo(entity.getUniqueID()).getResponseTime() > 5;
        }
        return true;
    }

    public static boolean isBot(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            Hack hack = HackManager.getHack("AntiBot");
            return hack.isToggled() && AntiBot.isBot((Entity)player);
        }
        return false;
    }

    public static boolean isFriendEnemy(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            String ID = Utils.getPlayerName(player);
            if (FriendManager.friendsList.contains(ID)) {
                return false;
            }
            if (HackManager.getHack("Enemys").isToggled() && !EnemyManager.enemysList.contains(ID)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTeam(EntityLivingBase entity) {
        Hack teams = HackManager.getHack("Teams");
        if (teams.isToggled() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (teams.isToggledMode("Base") && player.getTeam() != null && Wrapper.INSTANCE.player().getTeam() != null && player.getTeam().isSameTeam(Wrapper.INSTANCE.player().getTeam())) {
                return false;
            }
            if (teams.isToggledMode("ArmorColor") && !Utils.checkEnemyColor(player)) {
                return false;
            }
            if (teams.isToggledMode("NameColor") && !Utils.checkEnemyNameColor((EntityLivingBase)player)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInvisible(EntityLivingBase entity) {
        Hack targets = HackManager.getHack("Targets");
        return targets.isToggledValue("Invisibles") || !entity.isInvisible();
    }

    public static boolean isNoScreen() {
        return Utils.screenCheck();
    }
}

