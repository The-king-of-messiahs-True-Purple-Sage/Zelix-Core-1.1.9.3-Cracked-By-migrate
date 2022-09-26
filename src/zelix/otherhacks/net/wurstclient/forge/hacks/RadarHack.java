/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Radar;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Window;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.EntityFakePlayer;

public final class RadarHack
extends Hack {
    private final Window window;
    private final ArrayList<Entity> entities = new ArrayList();
    private final SliderSetting radius = new SliderSetting("Radius", "Radius in blocks.", 100.0, 1.0, 100.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final CheckboxSetting rotate = new CheckboxSetting("Rotate with player", true);
    private final CheckboxSetting filterPlayers = new CheckboxSetting("Filter players", "Won't show other players.", false);
    private final CheckboxSetting filterSleeping = new CheckboxSetting("Filter sleeping", "Won't show sleeping players.", false);
    private final CheckboxSetting filterMonsters = new CheckboxSetting("Filter monsters", "Won't show zombies, creepers, etc.", false);
    private final CheckboxSetting filterAnimals = new CheckboxSetting("Filter animals", "Won't show pigs, cows, etc.", false);
    private final CheckboxSetting filterInvisible = new CheckboxSetting("Filter invisible", "Won't show invisible entities.", false);

    public RadarHack() {
        super("Radar", "Shows the location of nearby entities.\n" + ChatFormatting.RED + "red" + ChatFormatting.RESET + " - players\n" + ChatFormatting.GOLD + "orange" + ChatFormatting.RESET + " - monsters\n" + ChatFormatting.GREEN + "green" + ChatFormatting.RESET + " - animals\n" + ChatFormatting.GRAY + "gray" + ChatFormatting.RESET + " - others\n");
        this.setCategory(Category.RENDER);
        this.addSetting(this.radius);
        this.addSetting(this.rotate);
        this.addSetting(this.filterPlayers);
        this.addSetting(this.filterSleeping);
        this.addSetting(this.filterMonsters);
        this.addSetting(this.filterAnimals);
        this.addSetting(this.filterInvisible);
        this.window = new Window("Radar");
        this.window.setPinned(true);
        this.window.setInvisible(true);
        this.window.add(new Radar(this));
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
        this.window.setInvisible(false);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.window.setInvisible(true);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        World world = player.worldObj;
        this.entities.clear();
        Stream<Entity> stream = world.loadedEntityList.parallelStream().filter(e -> !e.isDead && e != player).filter(e -> !(e instanceof EntityFakePlayer)).filter(e -> e instanceof EntityLivingBase).filter(e -> ((EntityLivingBase)e).getHealth() > 0.0f);
        if (this.filterPlayers.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer));
        }
        if (this.filterSleeping.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer) || !((EntityPlayer)e).isPlayerSleeping());
        }
        if (this.filterMonsters.isChecked()) {
            stream = stream.filter(e -> !(e instanceof IMob));
        }
        if (this.filterAnimals.isChecked()) {
            stream = stream.filter(e -> !(e instanceof EntityAnimal) && !(e instanceof EntityAmbientCreature) && !(e instanceof EntityWaterMob));
        }
        if (this.filterInvisible.isChecked()) {
            stream = stream.filter(e -> !e.isInvisible());
        }
        this.entities.addAll(stream.collect(Collectors.toList()));
    }

    public Window getWindow() {
        return this.window;
    }

    public Iterable<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    public double getRadius() {
        return this.radius.getValue();
    }

    public boolean isRotateEnabled() {
        return this.rotate.isChecked();
    }
}

