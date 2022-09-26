/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.SoundEvents
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.client.event.GuiContainerEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.ProjectileImpactEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.EntityItemPickupEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.Core;
import zelix.eventapi.event.EventPlayerPost;
import zelix.eventapi.event.EventPlayerPre;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.gui.clickguis.gishcode.theme.dark.DarkTheme;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.AimAssist;
import zelix.hack.hacks.AntiAfk;
import zelix.hack.hacks.AntiBot;
import zelix.hack.hacks.AntiFall;
import zelix.hack.hacks.AntiHunger;
import zelix.hack.hacks.AntiItemLag;
import zelix.hack.hacks.AntiRain;
import zelix.hack.hacks.AntiSneak;
import zelix.hack.hacks.AntiWeb;
import zelix.hack.hacks.ArmorHUD;
import zelix.hack.hacks.AttackSpeed;
import zelix.hack.hacks.AutoArmor;
import zelix.hack.hacks.AutoClicker;
import zelix.hack.hacks.AutoEat;
import zelix.hack.hacks.AutoShield;
import zelix.hack.hacks.AutoSoup;
import zelix.hack.hacks.AutoSprint;
import zelix.hack.hacks.AutoStep;
import zelix.hack.hacks.AutoSwim;
import zelix.hack.hacks.AutoTool;
import zelix.hack.hacks.AutoTotem;
import zelix.hack.hacks.BedNuker;
import zelix.hack.hacks.Blink;
import zelix.hack.hacks.BlinkAttack;
import zelix.hack.hacks.BlockOverlay;
import zelix.hack.hacks.BowAimBot;
import zelix.hack.hacks.ChestESP;
import zelix.hack.hacks.ChestStealer;
import zelix.hack.hacks.Cilp;
import zelix.hack.hacks.ClickGui;
import zelix.hack.hacks.CloudConfig;
import zelix.hack.hacks.CommandFrame;
import zelix.hack.hacks.CommandGetter;
import zelix.hack.hacks.Criticals;
import zelix.hack.hacks.Disabler;
import zelix.hack.hacks.Disconnect;
import zelix.hack.hacks.EnemyInfo;
import zelix.hack.hacks.Enemys;
import zelix.hack.hacks.EntityESP;
import zelix.hack.hacks.FakeCreative;
import zelix.hack.hacks.Fall;
import zelix.hack.hacks.FastBow;
import zelix.hack.hacks.FastBreak;
import zelix.hack.hacks.FastLadder;
import zelix.hack.hacks.FastPlace;
import zelix.hack.hacks.FastUse;
import zelix.hack.hacks.FireballReturn;
import zelix.hack.hacks.Flight;
import zelix.hack.hacks.FreeCam;
import zelix.hack.hacks.Ghost;
import zelix.hack.hacks.GhostMode;
import zelix.hack.hacks.Glide;
import zelix.hack.hacks.Glowing;
import zelix.hack.hacks.HighJump;
import zelix.hack.hacks.Hitbox;
import zelix.hack.hacks.InteractClick;
import zelix.hack.hacks.ItemESP;
import zelix.hack.hacks.ItemTeleport;
import zelix.hack.hacks.KillAura;
import zelix.hack.hacks.LongJump;
import zelix.hack.hacks.NightVision;
import zelix.hack.hacks.NoSlow;
import zelix.hack.hacks.NoSwing;
import zelix.hack.hacks.Nuker;
import zelix.hack.hacks.PacketFilter;
import zelix.hack.hacks.Parkour;
import zelix.hack.hacks.PickupFilter;
import zelix.hack.hacks.PlayerRadar;
import zelix.hack.hacks.PluginsGetter;
import zelix.hack.hacks.PortalGodMode;
import zelix.hack.hacks.Profiler;
import zelix.hack.hacks.Rage;
import zelix.hack.hacks.Reach;
import zelix.hack.hacks.Regen;
import zelix.hack.hacks.SafeWalk;
import zelix.hack.hacks.Scaffold;
import zelix.hack.hacks.Search;
import zelix.hack.hacks.SelfDamage;
import zelix.hack.hacks.SelfDestruct;
import zelix.hack.hacks.SelfKick;
import zelix.hack.hacks.ServerCrasher;
import zelix.hack.hacks.SkinChanger;
import zelix.hack.hacks.SkinStealer;
import zelix.hack.hacks.Speed;
import zelix.hack.hacks.Spider;
import zelix.hack.hacks.SuperKick;
import zelix.hack.hacks.Targets;
import zelix.hack.hacks.Teams;
import zelix.hack.hacks.Teleport;
import zelix.hack.hacks.Timer;
import zelix.hack.hacks.TpAura;
import zelix.hack.hacks.Tracers;
import zelix.hack.hacks.Trajectories;
import zelix.hack.hacks.Trigger;
import zelix.hack.hacks.Velocity;
import zelix.hack.hacks.WallHack;
import zelix.hack.hacks.external.External;
import zelix.hack.hacks.hud.HUD;
import zelix.hack.hacks.xray.AntiAntiXrayMod;
import zelix.managers.GuiManager;
import zelix.utils.Wrapper;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.Value;

public class HackManager {
    private static Hack toggleHack = null;
    public static ArrayList<Hack> hacks;
    public static HashMap<Hack, Object> pluginhacks;
    private GuiManager guiManager;
    private ClickGuiScreen guiScreen;

    public HackManager() {
        hacks = new ArrayList();
        HackManager.addHack(new Disabler());
        HackManager.addHack(new Targets());
        HackManager.addHack(new Enemys());
        HackManager.addHack(new Teams());
        HackManager.addHack(new Glowing());
        HackManager.addHack(new Trajectories());
        HackManager.addHack(new EntityESP());
        HackManager.addHack(new ItemESP());
        HackManager.addHack(new ChestESP());
        HackManager.addHack(new Tracers());
        HackManager.addHack(new WallHack());
        HackManager.addHack(new FastUse());
        HackManager.addHack(new Flight());
        HackManager.addHack(new ServerCrasher());
        HackManager.addHack(new NightVision());
        HackManager.addHack(new Profiler());
        HackManager.addHack(new AntiBot());
        HackManager.addHack(new AntiItemLag());
        HackManager.addHack(new AimAssist());
        HackManager.addHack(new BowAimBot());
        HackManager.addHack(new Trigger());
        HackManager.addHack(new Criticals());
        HackManager.addHack(new KillAura());
        HackManager.addHack(new Velocity());
        HackManager.addHack(new AutoSprint());
        HackManager.addHack(new AutoArmor());
        HackManager.addHack(new InteractClick());
        HackManager.addHack(new Regen());
        HackManager.addHack(new FastPlace());
        HackManager.addHack(new ChestStealer());
        HackManager.addHack(new Glide());
        HackManager.addHack(new Nuker());
        HackManager.addHack(new AntiFall());
        HackManager.addHack(new Ghost());
        HackManager.addHack(new Blink());
        HackManager.addHack(new Scaffold());
        HackManager.addHack(new FastLadder());
        HackManager.addHack(new Speed());
        HackManager.addHack(new AutoStep());
        HackManager.addHack(new AntiSneak());
        HackManager.addHack(new FreeCam());
        HackManager.addHack(new BlockOverlay());
        HackManager.addHack(new PluginsGetter());
        HackManager.addHack(new Teleport());
        HackManager.addHack(new FireballReturn());
        HackManager.addHack(new SkinStealer());
        HackManager.addHack(new PlayerRadar());
        HackManager.addHack(new SkinChanger());
        HackManager.addHack(new Parkour());
        HackManager.addHack(new AntiRain());
        HackManager.addHack(new AntiWeb());
        HackManager.addHack(new Spider());
        HackManager.addHack(new AutoEat());
        HackManager.addHack(new AutoSwim());
        HackManager.addHack(new AutoTotem());
        HackManager.addHack(new AutoShield());
        HackManager.addHack(new Rage());
        HackManager.addHack(new SelfDamage());
        HackManager.addHack(new Hitbox());
        HackManager.addHack(new AntiAfk());
        HackManager.addHack(new FastBreak());
        HackManager.addHack(new Disconnect());
        HackManager.addHack(new GhostMode());
        HackManager.addHack(new SelfKick());
        HackManager.addHack(new PortalGodMode());
        HackManager.addHack(new PickupFilter());
        HackManager.addHack(new PacketFilter());
        HackManager.addHack(new FakeCreative());
        HackManager.addHack(new ArmorHUD());
        HackManager.addHack(new HUD());
        HackManager.addHack(new HighJump());
        HackManager.addHack(new ClickGui());
        HackManager.addHack(new CommandFrame());
        HackManager.addHack(new CommandGetter());
        HackManager.addHack(new TpAura());
        HackManager.addHack(new AutoTool());
        HackManager.addHack(new BedNuker());
        HackManager.addHack(new Cilp());
        HackManager.addHack(new LongJump());
        HackManager.addHack(new NoSlow());
        HackManager.addHack(new NoSwing());
        HackManager.addHack(new SafeWalk());
        HackManager.addHack(new Fall());
        HackManager.addHack(new AutoSoup());
        HackManager.addHack(new SuperKick());
        HackManager.addHack(new Timer());
        HackManager.addHack(new BlinkAttack());
        HackManager.addHack(new Search());
        HackManager.addHack(new Reach());
        HackManager.addHack(new AutoClicker());
        HackManager.addHack(new AntiHunger());
        HackManager.addHack(new CloudConfig());
        HackManager.addHack(new EnemyInfo());
        HackManager.addHack(new SelfDestruct());
        HackManager.addHack(new AttackSpeed());
        HackManager.addHack(new FastBow());
        HackManager.addHack(new AntiAntiXrayMod());
        HackManager.addHack(new ItemTeleport());
        if (Core.Love.equals("External")) {
            HackManager.addHack(new External());
        }
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public ClickGuiScreen getGui() {
        if (this.guiManager == null) {
            this.guiManager = new GuiManager();
            this.guiScreen = new ClickGuiScreen();
            ClickGuiScreen.clickGui = this.guiManager;
            this.guiManager.Init();
            this.guiManager.setTheme(new DarkTheme());
        }
        return this.guiManager;
    }

    public static Hack getHack(String name) {
        Hack hack = null;
        for (Hack h : HackManager.getHacks()) {
            if (!h.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) continue;
            hack = h;
        }
        return hack;
    }

    public static List<Hack> getSortedHacks() {
        ArrayList<Hack> list = new ArrayList<Hack>();
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled() || !hack.isShow()) continue;
            list.add(hack);
        }
        list.sort(new Comparator<Hack>(){

            @Override
            public int compare(Hack h1, Hack h2) {
                ModeValue modeValue;
                String s1 = h1.getRenderName();
                String s2 = h2.getRenderName();
                for (Value value : h1.getValues()) {
                    if (!(value instanceof ModeValue) || (modeValue = (ModeValue)value).getModeName().equals("Priority")) continue;
                    for (Mode mode2 : modeValue.getModes()) {
                        if (!mode2.isToggled()) continue;
                        s1 = s1 + " " + mode2.getName();
                    }
                }
                for (Value value : h2.getValues()) {
                    if (!(value instanceof ModeValue) || (modeValue = (ModeValue)value).getModeName().equals("Priority")) continue;
                    for (Mode mode2 : modeValue.getModes()) {
                        if (!mode2.isToggled()) continue;
                        s2 = s2 + " " + mode2.getName();
                    }
                }
                int cmp = Wrapper.INSTANCE.fontRenderer().getStringWidth(s2) - Wrapper.INSTANCE.fontRenderer().getStringWidth(s1);
                return cmp != 0 ? cmp : s2.compareTo(s1);
            }
        });
        return list;
    }

    public void sortModules() {
        hacks.sort((m1, m2) -> {
            if (m1.getName().toCharArray()[0] > m2.getName().toCharArray()[0]) {
                return 1;
            }
            return -1;
        });
    }

    public static List<Hack> getSortedHacks3() {
        ArrayList<Hack> list = new ArrayList<Hack>();
        for (Hack module : HackManager.getHacks()) {
            if (!module.isToggled() || !module.isShow()) continue;
            list.add(module);
        }
        list.sort(new Comparator<Hack>(){

            @Override
            public int compare(Hack h1, Hack h2) {
                ModeValue modeValue;
                String s1 = h1.getRenderName();
                String s2 = h2.getRenderName();
                for (Value value : h1.getValues()) {
                    if (!(value instanceof ModeValue) || !(modeValue = (ModeValue)value).getModeName().equals("Mode")) continue;
                    for (Mode mode2 : modeValue.getModes()) {
                        if (!mode2.isToggled()) continue;
                        s1 = s1 + " " + mode2.getName();
                    }
                }
                for (Value value : h2.getValues()) {
                    if (!(value instanceof ModeValue) || !(modeValue = (ModeValue)value).getModeName().equals("Mode")) continue;
                    for (Mode mode2 : modeValue.getModes()) {
                        if (!mode2.isToggled()) continue;
                        s2 = s2 + " " + mode2.getName();
                    }
                }
                int cmp = (int)(HUD.font.getFont("SFB 6").getWidth(s2) - HUD.font.getFont("SFB 6").getWidth(s1));
                return cmp != 0 ? cmp : s2.compareTo(s1);
            }
        });
        return list;
    }

    public static void addHack(Hack hack) {
        hacks.add(hack);
    }

    public static void addPluginHack(Hack hack, Object obj) {
        pluginhacks.put(hack, obj);
        hacks.add(hack);
    }

    public static void unregist(Hack hack, Object obj) {
        pluginhacks.get(obj);
        hacks.add(hack);
    }

    public static ArrayList<Hack> getHacks() {
        return hacks;
    }

    public static Hack getToggleHack() {
        return toggleHack;
    }

    public static void onKeyPressed(int key) {
        if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return;
        }
        for (Hack hack : HackManager.getHacks()) {
            if (hack.getKey() != key) continue;
            hack.toggle();
            Wrapper.INSTANCE.player().func_184185_a(SoundEvents.field_187885_gS, 0.15f, hack.isToggled() ? 0.7f : 0.6f);
            toggleHack = hack;
        }
    }

    public static void onGuiContainer(GuiContainerEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onGuiContainer(event);
        }
    }

    public static void onGuiOpen(GuiOpenEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onGuiOpen(event);
        }
    }

    public static void onRender3D(RenderBlockOverlayEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onRender3D(event);
        }
    }

    public static void onMouse(MouseEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onMouse(event);
        }
    }

    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onLeftClickBlock(event);
        }
    }

    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onCameraSetup(event);
        }
    }

    public static void onAttackEntity(AttackEntityEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onAttackEntity(event);
        }
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onProjectileImpact(event);
        }
    }

    public static void onItemPickup(EntityItemPickupEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onItemPickup(event);
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onPlayerTick(event);
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onClientTick(event);
        }
    }

    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onLivingUpdate(event);
        }
    }

    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onRenderWorldLast(event);
        }
    }

    public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onRenderGameOverlay(event);
        }
    }

    public static void onInputUpdate(InputUpdateEvent event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onInputUpdate(event);
        }
    }

    public static void onPlayerEventPre(EventPlayerPre event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onPlayerEventPre(event);
        }
    }

    public static void onPlayerEventPost(EventPlayerPost event) {
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled()) continue;
            hack.onPlayerEventPost(event);
        }
    }

    public static List<Hack> getModulesInType(HackCategory t) {
        ArrayList<Hack> output = new ArrayList<Hack>();
        ArrayList<Hack> hack = new ArrayList<Hack>();
        hack.addAll(hacks);
        for (Hack m : hacks) {
            if (m.getCategory() != t) continue;
            output.add(m);
        }
        return output;
    }
}

