/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityEndermite
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityGiantZombie
 *  net.minecraft.entity.monster.EntityGuardian
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.monster.EntitySnowman
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.entity.passive.EntityRabbit
 *  net.minecraft.entity.passive.EntitySheep
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.AntiBot;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Hitbox
extends Hack {
    public NumberValue width;
    public NumberValue height;
    public BooleanValue walls;
    public static NumberValue expand;
    public static BooleanValue extra;
    public static BooleanValue nofire;
    public static NumberValue extraV;
    public ModeValue mode = new ModeValue("Mode", new Mode("Basic", true), new Mode("Box", false), new Mode("Raven", false));
    private static RayTraceResult mv;
    NumberValue multiplier;
    NumberValue FOV;
    BooleanValue showbox;
    int getInput = 2;

    public Hitbox() {
        super("HitBox", HackCategory.COMBAT);
        this.width = new NumberValue("Width", 1.0, 0.6, 5.0);
        this.height = new NumberValue("Height", 2.2, 1.8, 5.0);
        expand = new NumberValue("Expand", 0.1, 1.0, 2.0);
        extra = new BooleanValue("Extra", false);
        nofire = new BooleanValue("NoFire", false);
        extraV = new NumberValue("ExtraExpand", 0.0, 0.0, 15.0);
        this.FOV = new NumberValue("FOV", 90.0, 1.0, 180.0);
        this.multiplier = new NumberValue("Multiplier", 1.2, 1.0, 5.0);
        this.showbox = new BooleanValue("ShowBox", true);
        this.walls = new BooleanValue("ThroughWalls", false);
        this.addValue(extra, this.width, this.height, expand, extraV, nofire, this.multiplier, this.showbox, this.walls, this.mode);
    }

    @Override
    public String getDescription() {
        return "Change size hit box of entity.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("Basic").isToggled()) {
            for (Entity object : Utils.getEntityList()) {
                EntityLivingBase entity;
                if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase)object)) continue;
                if (((Boolean)nofire.getValue()).booleanValue()) {
                    entity.setFire(0);
                }
                Utils.setEntityBoundingBoxSize((Entity)entity, ((Double)this.width.getValue()).floatValue(), ((Double)this.height.getValue()).floatValue());
            }
        } else if (this.mode.getMode("Box").isToggled()) {
            List loadedEntityList = Wrapper.INSTANCE.world().loadedEntityList;
            for (int i = 0; i < loadedEntityList.size(); ++i) {
                Entity e = (Entity)loadedEntityList.get(i);
                if (!this.isValidEntity(e)) continue;
                if (((Boolean)nofire.getValue()).booleanValue()) {
                    e.setFire(0);
                }
                e.width = (float)((Boolean)extra.getValue() != false ? 0.6 + (Double)expand.getValue() + (Double)extraV.getValue() : 0.6 + (Double)expand.getValue());
            }
        } else if (this.mode.getMode("Raven").isToggled()) {
            List loadedEntityList = Wrapper.INSTANCE.world().loadedEntityList;
            for (int i = 0; i < loadedEntityList.size(); ++i) {
                Entity e = (Entity)loadedEntityList.get(i);
                if (!this.isValidEntity(e)) continue;
                e.height = ((Double)this.multiplier.getValue()).floatValue();
                e.width = ((Double)this.multiplier.getValue()).floatValue();
            }
        }
        super.onClientTick(event);
    }

    @Override
    public void onDisable() {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)object;
            EntitySize entitySize = this.getEntitySize((Entity)entity);
            Utils.setEntityBoundingBoxSize((Entity)entity, entitySize.width, entitySize.height);
        }
        super.onDisable();
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (this.mode.getMode("Raven").isToggled()) {
            Utils.nullCheck();
            Hack click = HackManager.getHack("AutoClicker");
            if (click != null && !click.isToggled()) {
                return;
            }
            if (click != null && click.isToggled() && Mouse.isButtonDown((int)0) && mv != null) {
                Minecraft.getMinecraft().objectMouseOver = mv;
            }
        }
        super.onPlayerTick(event);
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.mode.getMode("Raven").isToggled() && ((Boolean)this.showbox.getValue()).booleanValue()) {
            for (Entity en : Wrapper.INSTANCE.world().loadedEntityList) {
                if (en == Wrapper.INSTANCE.player() || !(en instanceof EntityLivingBase) || ((EntityLivingBase)en).deathTime != 0 || en instanceof EntityArmorStand || en.isInvisible()) continue;
                this.rh(en, Color.WHITE, event.getPartialTicks());
            }
        }
        super.onRenderWorldLast(event);
    }

    private void rh(Entity e, Color c, float partialTicks) {
        if (e instanceof EntityLivingBase) {
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)partialTicks - Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)partialTicks - Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)partialTicks - Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
            float ex = (float)((double)e.getCollisionBorderSize() * (double)this.getInput);
            AxisAlignedBB bbox = e.func_174813_aQ().func_72321_a((double)ex, (double)ex, (double)ex);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.field_72340_a - e.posX + x, bbox.field_72338_b - e.posY + y, bbox.field_72339_c - e.posZ + z, bbox.field_72336_d - e.posX + x, bbox.field_72337_e - e.posY + y, bbox.field_72334_f - e.posZ + z);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)2.0f);
            GL11.glColor3d((double)c.getRed(), (double)c.getGreen(), (double)c.getBlue());
            RenderGlobal.func_189697_a((AxisAlignedBB)axis, (float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), (float)c.getAlpha());
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
    }

    @Override
    public void onMouse(MouseEvent event) {
        Utils.nullCheck();
        if (event.getButton() == 0 && event.isButtonstate() && mv != null) {
            Wrapper.INSTANCE.mc().objectMouseOver = mv;
        }
        super.onMouse(event);
    }

    public EntitySize getEntitySize(Entity entity) {
        EntitySize entitySize = new EntitySize(0.6f, 1.8f);
        if (entity instanceof EntitySpider) {
            entitySize = new EntitySize(1.4f, 0.9f);
        }
        if (entity instanceof EntityBat) {
            entitySize = new EntitySize(0.5f, 0.9f);
        }
        if (entity instanceof EntityChicken) {
            entitySize = new EntitySize(0.5f, 0.9f);
        }
        if (entity instanceof EntityCow) {
            entitySize = new EntitySize(0.9f, 1.4f);
        }
        if (entity instanceof EntitySheep) {
            entitySize = new EntitySize(0.9f, 1.4f);
        }
        if (entity instanceof EntityEnderman) {
            entitySize = new EntitySize(0.6f, 2.9f);
        }
        if (entity instanceof EntityGhast) {
            entitySize = new EntitySize(4.0f, 4.0f);
        }
        if (entity instanceof EntityEndermite) {
            entitySize = new EntitySize(0.4f, 0.3f);
        }
        if (entity instanceof EntityGiantZombie) {
            entitySize = new EntitySize(3.6000001f, 10.799999f);
        }
        if (entity instanceof EntityWolf) {
            entitySize = new EntitySize(0.6f, 0.85f);
        }
        if (entity instanceof EntityGuardian) {
            entitySize = new EntitySize(0.85f, 0.85f);
        }
        if (entity instanceof EntitySquid) {
            entitySize = new EntitySize(0.8f, 0.8f);
        }
        if (entity instanceof EntityDragon) {
            entitySize = new EntitySize(16.0f, 8.0f);
        }
        if (entity instanceof EntityRabbit) {
            entitySize = new EntitySize(0.4f, 0.5f);
        }
        return entitySize;
    }

    public Entity entity() {
        Entity e = null;
        if (Wrapper.INSTANCE.mc().thePlayer.worldObj != null) {
            for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                e = (Entity)o;
            }
        }
        return e;
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (ValidUtils.isValidEntity(entity)) {
            return false;
        }
        if (entity == Wrapper.INSTANCE.player()) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (!ValidUtils.isFriendEnemy(entity)) {
            return false;
        }
        if (!ValidUtils.isTeam(entity)) {
            return false;
        }
        if (!ValidUtils.isBot(entity)) {
            return false;
        }
        if (((Boolean)this.walls.getValue()).booleanValue() && Wrapper.INSTANCE.player().canEntityBeSeen((Entity)entity)) {
            return false;
        }
        return entity.canBeCollidedWith();
    }

    private boolean isValidEntity(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if (entity.isDead || ((EntityLivingBase)entity).getHealth() <= 0.0f || !entity.canBeCollidedWith()) {
                return false;
            }
            Hack targets = HackManager.getHack("Targets");
            if (!(entity == Wrapper.INSTANCE.player() || Wrapper.INSTANCE.player().isDead || entity instanceof EntityArmorStand || entity instanceof EntitySnowman)) {
                if (targets.isToggled() && entity instanceof EntityPlayer && targets.isToggledValue("Players")) {
                    if (!Wrapper.INSTANCE.player().canEntityBeSeen(entity) && !((Boolean)this.walls.getValue()).booleanValue()) {
                        return false;
                    }
                    if (targets.isToggled() && entity.isInvisible() && !targets.isToggledValue("Invisibles")) {
                        return false;
                    }
                    return !AntiBot.isBot(entity) && ValidUtils.isFriendEnemy((EntityLivingBase)entity);
                }
                if ((entity instanceof EntityMob || entity instanceof EntitySlime) && targets.isToggledValue("Mobs") && targets.isToggled()) {
                    if (!Wrapper.INSTANCE.player().canEntityBeSeen(entity) && !((Boolean)this.walls.getValue()).booleanValue()) {
                        return false;
                    }
                    return !AntiBot.isBot(entity);
                }
                if ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && targets.isToggledValue("Mobs") && targets.isToggled()) {
                    if (!Wrapper.INSTANCE.player().canEntityBeSeen(entity) && !((Boolean)this.walls.getValue()).booleanValue()) {
                        return false;
                    }
                    return !AntiBot.isBot(entity);
                }
            }
        }
        return false;
    }

    class EntitySize {
        public float width;
        public float height;

        public EntitySize(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}

