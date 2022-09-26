/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.EntityFakePlayer;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class KillauraHack
extends Hack {
    private final SliderSetting range = new SliderSetting("Range", 5.0, 1.0, 10.0, 0.05, SliderSetting.ValueDisplay.DECIMAL);
    private final EnumSetting<Priority> priority = new EnumSetting("Priority", "Determines which entity will be attacked first.\n\u00a7lDistance\u00a7r - Attacks the closest entity.\n\u00a7lAngle\u00a7r - Attacks the entity that requires\nthe least head movement.\n\u00a7lHealth\u00a7r - Attacks the weakest entity.", (Enum[])Priority.values(), (Enum)Priority.ANGLE);
    private final CheckboxSetting filterPlayers = new CheckboxSetting("Filter players", "Won't attack other players.", false);
    private final CheckboxSetting filterSleeping = new CheckboxSetting("Filter sleeping", "Won't attack sleeping players.", false);
    private final SliderSetting filterFlying = new SliderSetting("Filter flying", "Won't attack players that\nare at least the given\ndistance above ground.", 0.0, 0.0, 2.0, 0.05, v -> v == 0.0 ? "off" : SliderSetting.ValueDisplay.DECIMAL.getValueString(v));
    private final CheckboxSetting filterMonsters = new CheckboxSetting("Filter monsters", "Won't attack zombies, creepers, etc.", false);
    private final CheckboxSetting filterPigmen = new CheckboxSetting("Filter pigmen", "Won't attack zombie pigmen.", false);
    private final CheckboxSetting filterEndermen = new CheckboxSetting("Filter endermen", "Won't attack endermen.", false);
    private final CheckboxSetting filterAnimals = new CheckboxSetting("Filter animals", "Won't attack pigs, cows, etc.", false);
    private final CheckboxSetting filterBabies = new CheckboxSetting("Filter babies", "Won't attack baby pigs,\nbaby villagers, etc.", false);
    private final CheckboxSetting filterPets = new CheckboxSetting("Filter pets", "Won't attack tamed wolves,\ntamed horses, etc.", false);
    private final CheckboxSetting filterVillagers = new CheckboxSetting("Filter villagers", "Won't attack villagers.", false);
    private final CheckboxSetting filterGolems = new CheckboxSetting("Filter golems", "Won't attack iron golems,\nsnow golems and shulkers.", false);
    private final CheckboxSetting filterInvisible = new CheckboxSetting("Filter invisible", "Won't attack invisible entities.", false);
    private EntityLivingBase target;

    public KillauraHack() {
        super("Killaura", "Automatically attacks entities around you.");
        this.setCategory(Category.COMBAT);
        this.addSetting(this.range);
        this.addSetting(this.priority);
        this.addSetting(this.filterPlayers);
        this.addSetting(this.filterSleeping);
        this.addSetting(this.filterFlying);
        this.addSetting(this.filterMonsters);
        this.addSetting(this.filterPigmen);
        this.addSetting(this.filterEndermen);
        this.addSetting(this.filterAnimals);
        this.addSetting(this.filterBabies);
        this.addSetting(this.filterPets);
        this.addSetting(this.filterVillagers);
        this.addSetting(this.filterGolems);
        this.addSetting(this.filterInvisible);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.target = null;
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        World world = player.worldObj;
        if (player.func_184825_o(0.0f) < 1.0f) {
            return;
        }
        double rangeSq = Math.pow(this.range.getValue(), 2.0);
        Stream<EntityLivingBase> stream = world.loadedEntityList.parallelStream().filter(e -> e instanceof EntityLivingBase).map(e -> (EntityLivingBase)e).filter(e -> !e.isDead && e.getHealth() > 0.0f).filter(e -> player.getDistanceSqToEntity((Entity)e) <= rangeSq).filter(e -> e != player).filter(e -> !(e instanceof EntityFakePlayer));
        if (this.filterPlayers.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer));
        }
        if (this.filterSleeping.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer) || !((EntityPlayer)e).isPlayerSleeping());
        }
        if (this.filterFlying.getValue() > 0.0) {
            stream = stream.filter(e -> {
                if (!(e instanceof EntityPlayer)) {
                    return true;
                }
                AxisAlignedBB box = e.func_174813_aQ();
                box = box.func_111270_a(box.func_72317_d(0.0, -this.filterFlying.getValue(), 0.0));
                return world.func_184143_b(box);
            });
        }
        if (this.filterMonsters.isChecked()) {
            stream = stream.filter(e -> !(e instanceof IMob));
        }
        if (this.filterPigmen.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityPigZombie));
        }
        if (this.filterEndermen.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityEnderman));
        }
        if (this.filterAnimals.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityAnimal) && !(e instanceof EntityAmbientCreature) && !(e instanceof EntityWaterMob));
        }
        if (this.filterBabies.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityAgeable) || !((EntityAgeable)e).isChild());
        }
        if (this.filterPets.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityTameable) || !((EntityTameable)e).isTamed()).filter(e -> !(e instanceof AbstractHorse) || !((AbstractHorse)e).func_110248_bS());
        }
        if (this.filterVillagers.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityVillager));
        }
        if (this.filterGolems.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityGolem));
        }
        if (this.filterInvisible.isChecked()) {
            stream = stream.filter(e -> !e.isInvisible());
        }
        this.target = stream.min(this.priority.getSelected().comparator).orElse(null);
        if (this.target == null) {
            return;
        }
        RotationUtils.faceVectorPacket(this.target.func_174813_aQ().func_189972_c());
        KillauraHack.mc.playerController.attackEntity((EntityPlayer)player, (Entity)this.target);
        player.func_184609_a(EnumHand.MAIN_HAND);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.target == null) {
            return;
        }
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        AxisAlignedBB box = new AxisAlignedBB(BlockPos.field_177992_a);
        float p = (this.target.getMaxHealth() - this.target.getHealth()) / this.target.getMaxHealth();
        float red = p * 2.0f;
        float green = 2.0f - red;
        GL11.glTranslated((double)this.target.posX, (double)this.target.posY, (double)this.target.posZ);
        GL11.glTranslated((double)0.0, (double)0.05, (double)0.0);
        GL11.glScaled((double)this.target.width, (double)this.target.height, (double)this.target.width);
        GL11.glTranslated((double)-0.5, (double)0.0, (double)-0.5);
        if (p < 1.0f) {
            GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
            GL11.glScaled((double)p, (double)p, (double)p);
            GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
        }
        GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.25f);
        GL11.glBegin((int)7);
        RenderUtils.drawSolidBox(box);
        GL11.glEnd();
        GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(box);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private static enum Priority {
        DISTANCE("Distance", e -> mc.thePlayer.getDistanceSqToEntity((Entity)e)),
        ANGLE("Angle", e -> RotationUtils.getAngleToLookVec(e.func_174813_aQ().func_189972_c())),
        HEALTH("Health", e -> e.getHealth());

        private final String name;
        private final Comparator<EntityLivingBase> comparator;

        private Priority(String name, ToDoubleFunction<EntityLivingBase> keyExtractor) {
            this.name = name;
            this.comparator = Comparator.comparingDouble(keyExtractor);
        }

        public String toString() {
            return this.name;
        }
    }
}

