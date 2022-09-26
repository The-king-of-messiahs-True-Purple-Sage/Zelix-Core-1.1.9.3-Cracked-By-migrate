/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package zelix.hack.hacks;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zelix.eventapi.event.EventPlayerPre;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.AutoShield;
import zelix.managers.FontManager;
import zelix.managers.HackManager;
import zelix.utils.EntityUtils;
import zelix.utils.RayCastUtils;
import zelix.utils.RotationUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.ValidUtils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class KillAura
extends Hack {
    public ModeValue priority;
    public ModeValue mode;
    public BooleanValue walls;
    public BooleanValue autoDelay;
    public BooleanValue packetReach;
    public NumberValue minCPS;
    public NumberValue maxCPS;
    public NumberValue packetRange;
    public NumberValue range;
    public NumberValue FOV;
    public BooleanValue rotate;
    boolean send = false;
    public TimerUtils timer;
    public static EntityLivingBase target;
    public List<EntityLivingBase> targets;
    public static float[] facingCam;
    public float[] facing = null;
    Vec3d randomCenter = null;
    boolean phaseOne = false;
    boolean phaseTwo = false;
    boolean phaseThree = false;
    boolean fakephaseOne;
    boolean fakephaseTwo;
    boolean fakephaseThree = false;
    public static boolean blockstate;
    FontManager fontManager = new FontManager();

    public KillAura() {
        super("KillAura", HackCategory.COMBAT);
        this.priority = new ModeValue("Priority", new Mode("Closest", true), new Mode("Health", false));
        this.mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("AAC", false), new Mode("Legit", false), new Mode("Multi", false), new Mode("Fake", false));
        this.walls = new BooleanValue("ThroughWalls", false);
        this.autoDelay = new BooleanValue("AutoDelay", false);
        this.rotate = new BooleanValue("Rotate", false);
        this.packetReach = new BooleanValue("PacketReach", false);
        this.packetRange = new NumberValue("PacketRange", 10.0, 1.0, 100.0);
        this.minCPS = new NumberValue("MinCPS", 4.0, 1.0, 30.0);
        this.maxCPS = new NumberValue("MaxCPS", 8.0, 1.0, 30.0);
        this.range = new NumberValue("Range", 3.4, 1.0, 7.0);
        this.FOV = new NumberValue("FOV", 180.0, 1.0, 180.0);
        this.addValue(this.mode, this.priority, this.walls, this.autoDelay, this.packetReach, this.minCPS, this.maxCPS, this.packetRange, this.range, this.FOV, this.rotate);
        this.timer = new TimerUtils();
    }

    @Override
    public String getDescription() {
        return "Attacks the entities around you.";
    }

    @Override
    public void onEnable() {
        facingCam = null;
        if (this.mode.getMode("AAC").isToggled() || this.mode.getMode("Fake").isToggled()) {
            this.facing = null;
            this.randomCenter = null;
            this.phaseOne = false;
            this.phaseTwo = false;
            this.phaseThree = false;
            this.fakephaseOne = false;
            this.fakephaseTwo = false;
            this.fakephaseThree = false;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        facingCam = null;
        target = null;
        AutoShield.block(false);
        blockstate = false;
        super.onDisable();
    }

    @Override
    public void onPlayerEventPre(EventPlayerPre event) {
        Utils.nullCheck();
        if (this.mode.getMode("Fake").isToggled()) {
            this.rotate.setValue(false);
            this.killAuraUpdate();
            this.FakekillAuraAttack(target, event);
        }
        if (((Boolean)this.rotate.getValue()).booleanValue() && target != null) {
            event.setPitch(Utils.getRotationsNeeded((Entity)target)[1]);
            event.setYaw(Utils.getRotationsNeeded((Entity)target)[0]);
            Wrapper.INSTANCE.player().setRotationYawHead(event.getYaw());
            Wrapper.INSTANCE.player().renderYawOffset = event.getYaw();
        }
        super.onPlayerEventPre(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
            Field field = ReflectionHelper.findField(CPacketPlayer.class, (String[])new String[]{"rotating", "field_149481_i"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (target != null) {
                    Field field1 = ReflectionHelper.findField(CPacketPlayer.class, (String[])new String[]{"pitch", "field_149473_f"});
                    Field field2 = ReflectionHelper.findField(CPacketPlayer.class, (String[])new String[]{"yaw", "field_149476_e"});
                    if (!field1.isAccessible()) {
                        field1.setAccessible(true);
                    }
                    if (!field2.isAccessible()) {
                        field2.setAccessible(true);
                    }
                }
                if (field.getBoolean(packet)) {
                    RotationUtils.serverRotation = new RotationUtils.Rotation(((CPacketPlayer)packet).func_186999_a(0.0f), ((CPacketPlayer)packet).func_186998_b(0.0f));
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return super.onPacket(packet, side);
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
        if (target != null) {
            float health = target.getHealth();
            if (health > 20.0f) {
                health = 20.0f;
            }
            int red = (int)Math.abs(health * 5.0f * 0.01f * 0.0f + (1.0f - health * 5.0f * 0.01f) * 255.0f);
            int green = (int)Math.abs(health * 5.0f * 0.01f * 255.0f + (1.0f - health * 5.0f * 0.01f) * 0.0f);
            Color customColor = new Color(red, green, 0).brighter();
            Wrapper.INSTANCE.fontRenderer().drawString("\u00a74\u2764\u00a7f", sr.getScaledWidth() / 2 - Wrapper.INSTANCE.fontRenderer().getStringWidth("\u00a74\u2764\u00a7f"), sr.getScaledHeight() / 2 - 15, customColor.getRGB());
            this.fontManager.getFont("SFM 7").drawString("" + (int)target.getHealth(), sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - 15, customColor.getRGB());
            Wrapper.INSTANCE.fontRenderer().drawString(target.getName(), sr.getScaledWidth() / 2 - Wrapper.INSTANCE.fontRenderer().getStringWidth("\u00a74\u2764\u00a7f"), sr.getScaledHeight() / 2 - 24, -1);
        }
        super.onRenderGameOverlay(event);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (!this.mode.getMode("AAC").isToggled() || event.getEntity() != Wrapper.INSTANCE.player() || target == null) {
            return;
        }
        float yaw = Utils.getRotationsNeeded((Entity)target)[0] - 180.0f;
        float pitch = Utils.getRotationsNeeded((Entity)target)[1] + 10.0f;
        facingCam = new float[]{Utils.getRotationsNeeded((Entity)target)[0], pitch};
        event.setYaw(yaw);
        event.setPitch(pitch);
        super.onCameraSetup(event);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            this.phaseOne();
            this.phaseTwo();
            this.phaseFour();
        } else if (this.mode.getMode("Simple").isToggled()) {
            this.killAuraUpdate();
            this.killAuraAttack(target);
        } else if (this.mode.getMode("Multi").isToggled()) {
            this.MultiUpdate();
        }
        super.onClientTick(event);
    }

    void MultiUpdate() {
        int r3;
        int r2;
        ArrayList entities;
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        WorldClient world = Wrapper.INSTANCE.world();
        double rangeSq = Math.pow((Double)this.range.getValue(), 2.0);
        Stream<EntityLivingBase> stream = world.loadedEntityList.parallelStream().filter(e -> e instanceof EntityLivingBase).map(e -> (EntityLivingBase)e).filter(e -> !e.isDead && e.getHealth() > 0.0f).filter(e -> EntityUtils.getDistanceSq((Entity)player, (Entity)e) <= rangeSq).filter(e -> e != player);
        Hack h = HackManager.getHack("Targets");
        if (!h.isToggledValue("Players")) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer));
        }
        if (!h.isToggledValue("Mobs")) {
            stream = stream.filter(e -> !(e instanceof IMob) && !(e instanceof EntityAnimal));
        }
        if ((entities = stream.collect(Collectors.toCollection(() -> new ArrayList()))).isEmpty()) {
            return;
        }
        int CPS = Utils.random(((Double)this.minCPS.getValue()).intValue(), ((Double)this.maxCPS.getValue()).intValue());
        int r1 = Utils.random(1, 50);
        if (this.timer.hasReached((1000 + (r1 - (r2 = Utils.random(1, 60)) + (r3 = Utils.random(1, 70)))) / CPS)) {
            for (Entity entity : entities) {
                Wrapper.INSTANCE.controller().attackEntity((EntityPlayer)player, entity);
            }
            player.func_184609_a(EnumHand.MAIN_HAND);
            this.timer.reset();
        }
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            this.phaseThree(event);
        }
        super.onPlayerTick(event);
    }

    void phaseOne() {
        if (target != null) {
            this.randomCenter = Utils.getRandomCenter(target.func_174813_aQ());
            this.facing = Utils.getSmoothNeededRotations(this.randomCenter, 100.0f, 100.0f);
        }
        this.killAuraUpdate();
        if (target != null) {
            this.phaseOne = true;
        }
    }

    void fakephaseOne(EventPlayerPre evnt) {
        if (target != null) {
            this.randomCenter = Utils.getRandomCenter(target.func_174813_aQ());
            this.facing = Utils.getSmoothNeededRotations(this.randomCenter, 100.0f, 100.0f);
        }
        this.killAuraUpdate();
        if (target != null) {
            this.fakephaseOne = true;
        }
    }

    void phaseTwo() {
        if (target == null || this.randomCenter == null || !this.phaseOne) {
            return;
        }
        if (this.facing[0] == Utils.getNeededRotations(this.randomCenter)[0]) {
            this.phaseOne = false;
            this.phaseTwo = true;
        }
    }

    void fakephaseTwo(EventPlayerPre event) {
        if (target == null || this.randomCenter == null || !this.fakephaseOne) {
            return;
        }
        if (this.facing[0] == Utils.getNeededRotations(this.randomCenter)[0]) {
            this.fakephaseOne = false;
            this.fakephaseTwo = true;
        }
    }

    void phaseThree(TickEvent.PlayerTickEvent event) {
        if (target == null || this.facing == null || event.player != Wrapper.INSTANCE.player()) {
            return;
        }
        if (KillAura.target.hurtTime <= KillAura.target.maxHurtTime) {
            event.player.rotationYaw = this.facing[0];
            event.player.rotationPitch = this.facing[1];
            Wrapper.INSTANCE.player().rotationYawHead = this.facing[0];
        }
        if (!this.phaseTwo) {
            return;
        }
        event.player.rotationYaw = this.facing[0];
        event.player.rotationPitch = this.facing[1];
        Wrapper.INSTANCE.player().rotationYawHead = this.facing[0];
        this.phaseTwo = false;
        this.phaseThree = true;
    }

    void fakephaseThree(EventPlayerPre event) {
        if (target == null || this.facing == null) {
            return;
        }
        if (KillAura.target.hurtTime <= KillAura.target.maxHurtTime) {
            event.setYaw(this.facing[0]);
            event.setPitch(this.facing[1]);
            Wrapper.INSTANCE.player().rotationYawHead = this.facing[0];
            Wrapper.INSTANCE.player().setRotationYawHead(event.getYaw());
            Wrapper.INSTANCE.player().renderYawOffset = event.getYaw();
        }
        if (!this.fakephaseTwo) {
            return;
        }
        event.setYaw(this.facing[0]);
        event.setPitch(this.facing[1]);
        Wrapper.INSTANCE.player().setRotationYawHead(event.getYaw());
        Wrapper.INSTANCE.player().renderYawOffset = event.getYaw();
        Wrapper.INSTANCE.player().rotationYawHead = this.facing[0];
        this.fakephaseTwo = false;
        this.fakephaseThree = true;
    }

    void phaseFour() {
        if (target == null || this.randomCenter == null || !this.phaseThree || this.facing[0] != Utils.getNeededRotations(this.randomCenter)[0]) {
            facingCam = null;
            return;
        }
        Entity rayCastEntity = RayCastUtils.rayCast(((Double)this.range.getValue()).floatValue() + 1.0f, this.facing[0], this.facing[1]);
        this.killAuraAttack(rayCastEntity == null ? target : (EntityLivingBase)rayCastEntity);
    }

    void fakephaseFour(EventPlayerPre event) {
        if (target == null || this.randomCenter == null || !this.fakephaseThree) {
            facingCam = null;
            return;
        }
        event.setPitch(Utils.getRotationsNeeded((Entity)target)[1]);
        event.setYaw(Utils.getRotationsNeeded((Entity)target)[0]);
        this.killAuraAttack(target);
    }

    void killAuraUpdate() {
        try {
            for (Object object : Wrapper.INSTANCE.world().loadedEntityList) {
                EntityLivingBase entity;
                if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase)object)) continue;
                target = entity;
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            // empty catch block
        }
    }

    public void FakekillAuraAttack(EntityLivingBase entity, EventPlayerPre event) {
        if (entity == null) {
            AutoShield.block(false);
            blockstate = false;
            return;
        }
        event.setPitch(Utils.getRotationsNeeded((Entity)target)[1]);
        event.setYaw(Utils.getRotationsNeeded((Entity)target)[0]);
        Wrapper.INSTANCE.player().setRotationYawHead(event.getYaw());
        Wrapper.INSTANCE.player().renderYawOffset = event.getYaw();
        if (((Boolean)this.autoDelay.getValue()).booleanValue()) {
            if (Wrapper.INSTANCE.player().func_184825_o(0.0f) == 1.0f) {
                this.processAttack(entity);
                target = null;
            }
        } else {
            int r3;
            int r2;
            int CPS = Utils.random(((Double)this.minCPS.getValue()).intValue(), ((Double)this.maxCPS.getValue()).intValue());
            int r1 = Utils.random(1, 50);
            if (this.timer.isDelay((1000 + (r1 - (r2 = Utils.random(1, 60)) + (r3 = Utils.random(1, 70)))) / CPS)) {
                this.processAttack(entity);
                this.timer.setLastMS();
                facingCam = null;
                target = null;
                this.phaseThree = false;
            }
        }
    }

    public void killAuraAttack(EntityLivingBase entity) {
        if (entity == null) {
            AutoShield.block(false);
            blockstate = false;
            return;
        }
        if (((Boolean)this.autoDelay.getValue()).booleanValue()) {
            if (Wrapper.INSTANCE.player().func_184825_o(0.0f) == 1.0f) {
                this.processAttack(entity);
                target = null;
            }
        } else {
            int r3;
            int r2;
            int CPS = Utils.random(((Double)this.minCPS.getValue()).intValue(), ((Double)this.maxCPS.getValue()).intValue());
            int r1 = Utils.random(1, 50);
            if (this.timer.isDelay((1000 + (r1 - (r2 = Utils.random(1, 60)) + (r3 = Utils.random(1, 70)))) / CPS)) {
                this.processAttack(entity);
                this.timer.setLastMS();
                facingCam = null;
                this.phaseThree = false;
                this.fakephaseThree = false;
                target = null;
            }
        }
    }

    public void BMultiAttack(EntityLivingBase entity) {
        if (entity == null) {
            AutoShield.block(false);
            blockstate = false;
            return;
        }
        if (((Boolean)this.autoDelay.getValue()).booleanValue()) {
            if (Wrapper.INSTANCE.player().func_184825_o(0.0f) == 1.0f) {
                this.processAttack(entity);
                target = null;
            }
        } else {
            int r3;
            int r2;
            int CPS = Utils.random(((Double)this.minCPS.getValue()).intValue(), ((Double)this.maxCPS.getValue()).intValue());
            int r1 = Utils.random(1, 50);
            if (this.timer.isDelay((1000 + (r1 - (r2 = Utils.random(1, 60)) + (r3 = Utils.random(1, 70)))) / CPS)) {
                this.processAttack(entity);
                this.timer.setLastMS();
                facingCam = null;
                target = null;
                this.phaseThree = false;
            }
        }
    }

    private boolean hasSword() {
        return Wrapper.INSTANCE.player().inventory.getCurrentItem() != null && Wrapper.INSTANCE.player().inventory.getCurrentItem().getItem() instanceof ItemSword;
    }

    public void processAttack(EntityLivingBase entity) {
        AutoShield.block(false);
        if (this.hasSword()) {
            this.unBlock();
        }
        if (!this.isInAttackRange(entity) || !ValidUtils.isInAttackFOV(entity, ((Double)this.FOV.getValue()).intValue())) {
            return;
        }
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        float sharpLevel = EnchantmentHelper.getModifierForCreature((ItemStack)player.func_184614_ca(), (EnumCreatureAttribute)entity.getCreatureAttribute());
        if (((Boolean)this.packetReach.getValue()).booleanValue()) {
            double posX = entity.posX - 3.5 * Math.cos(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            double posZ = entity.posZ - 3.5 * Math.sin(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, entity.posY, posZ, Utils.getYaw((Entity)entity), Utils.getPitch((Entity)entity), player.onGround));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY, player.posZ, player.onGround));
        } else if (((Boolean)this.autoDelay.getValue()).booleanValue() || this.mode.getMode("Simple").isToggled()) {
            Utils.attack((Entity)entity);
        } else {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
        }
        Utils.swingMainHand();
        if (sharpLevel > 0.0f) {
            player.onEnchantmentCritical((Entity)entity);
        }
        AutoShield.block(true);
    }

    public void MultiprocessAttack(EntityLivingBase entity) {
        AutoShield.block(false);
        blockstate = false;
        if (!this.isInAttackRange(entity) || !ValidUtils.isInAttackFOV(entity, ((Double)this.FOV.getValue()).intValue())) {
            return;
        }
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        float sharpLevel = EnchantmentHelper.getModifierForCreature((ItemStack)player.func_184614_ca(), (EnumCreatureAttribute)entity.getCreatureAttribute());
        if (((Boolean)this.packetReach.getValue()).booleanValue()) {
            double posX = entity.posX - 3.5 * Math.cos(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            double posZ = entity.posZ - 3.5 * Math.sin(Math.toRadians(Utils.getYaw((Entity)entity) + 90.0f));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, entity.posY, posZ, Utils.getYaw((Entity)entity), Utils.getPitch((Entity)entity), player.onGround));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY, player.posZ, player.onGround));
        } else if (((Boolean)this.autoDelay.getValue()).booleanValue() || this.mode.getMode("Multi").isToggled()) {
            Utils.attack((Entity)entity);
        } else {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
        }
        Utils.swingMainHand();
        if (sharpLevel > 0.0f) {
            player.onEnchantmentCritical((Entity)entity);
        }
    }

    boolean isPriority(EntityLivingBase entity) {
        return this.priority.getMode("Closest").isToggled() && ValidUtils.isClosest(entity, target) || this.priority.getMode("Health").isToggled() && ValidUtils.isLowHealth(entity, target);
    }

    boolean isInAttackRange(EntityLivingBase entity) {
        return ((Boolean)this.packetReach.getValue()).booleanValue() ? entity.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) <= ((Double)this.packetRange.getValue()).floatValue() : entity.getDistanceToEntity((Entity)Wrapper.INSTANCE.player()) <= ((Double)this.range.getValue()).floatValue();
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
        if (entity.deathTime > 0) {
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
        if (!this.isInAttackRange(entity)) {
            return false;
        }
        if (!ValidUtils.isTeam(entity)) {
            return false;
        }
        if (!ValidUtils.pingCheck(entity)) {
            return false;
        }
        if (!((Boolean)this.walls.getValue()).booleanValue() && !Wrapper.INSTANCE.player().canEntityBeSeen((Entity)entity)) {
            return false;
        }
        return this.isPriority(entity);
    }

    public void doBlock() {
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mc().gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
    }

    public static boolean getBlockState() {
        return blockstate;
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }

    private void unBlock() {
        blockstate = false;
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
    }

    public static EntityLivingBase getTarget() {
        return target;
    }

    static {
        facingCam = null;
    }
}

