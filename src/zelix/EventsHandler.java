/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.entity.layers.LayerRenderer
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.client.event.GuiContainerEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.ProjectileImpactEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.EntityItemPickupEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Keyboard
 */
package zelix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zelix.eventapi.EventManager;
import zelix.eventapi.EventTarget;
import zelix.eventapi.event.EventPlayerPost;
import zelix.eventapi.event.EventPlayerPre;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.hack.Hack;
import zelix.hack.hacks.AntiBot;
import zelix.hack.hacks.ClickGui;
import zelix.hack.hacks.GhostMode;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.cape.CustomLayerCape;
import zelix.utils.system.Connection;

public class EventsHandler {
    private boolean initialized = false;
    private boolean flag;
    private boolean flag1;
    public String map = "";
    public WorldClient upworld = null;

    public EventsHandler() {
        EventManager.register(this);
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled() || Wrapper.INSTANCE.world() == null) continue;
            suc &= hack.onPacket(packet, side);
        }
        return suc;
    }

    @SubscribeEvent
    public void onGuiContainer(GuiContainerEvent event) {
        if (Utils.nullCheck()) {
            return;
        }
        try {
            HackManager.onGuiContainer(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderBlockOverlayEvent event) {
        if (Utils.nullCheck() || Wrapper.INSTANCE.mcSettings().hideGUI) {
            return;
        }
        try {
            HackManager.onRender3D(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (Utils.nullCheck()) {
            return;
        }
        try {
            FileManager.saveHacks();
            HackManager.onGuiOpen(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (Utils.nullCheck()) {
            return;
        }
        try {
            HackManager.onMouse(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Utils.nullCheck()) {
            return;
        }
        try {
            int key = Keyboard.getEventKey();
            if (key == 0 || key == -1) {
                return;
            }
            if (Keyboard.getEventKeyState()) {
                HackManager.onKeyPressed(key);
            }
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onCameraSetup(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onItemPickup(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onProjectileImpact(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onAttackEntity(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onPlayerTick(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (event.getEntity() != null && !this.map.contains(event.getEntity().getName() + ";")) {
            event.getRenderer().func_177094_a((LayerRenderer)new CustomLayerCape(event.getRenderer()));
            this.map = this.map + event.getEntity().getName() + ";";
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Utils.nullCheck()) {
            AntiBot.bots.clear();
            this.initialized = false;
            return;
        }
        try {
            if (!this.initialized) {
                new Connection(this);
                ClickGui.setColor();
                this.initialized = true;
            }
            if (!Minecraft.getMinecraft().theWorld.equals(this.upworld)) {
                this.upworld = Minecraft.getMinecraft().theWorld;
                this.map = "";
            }
            if (!(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen)) {
                HackManager.getHack("ClickGui").setToggled(false);
            }
            if (!GhostMode.enabled) {
                HackManager.onClientTick(event);
            }
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onLivingUpdate(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled || Wrapper.INSTANCE.mcSettings().hideGUI) {
            return;
        }
        try {
            HackManager.onRenderWorldLast(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onRenderGameOverlay(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onLeftClickBlock(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onInputUpdateEvent(InputUpdateEvent event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onInputUpdate(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @EventTarget
    public void onPlayerEventPre(EventPlayerPre event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onPlayerEventPre(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @EventTarget
    public void onPlayerEventPost(EventPlayerPost event) {
        if (Utils.nullCheck() || GhostMode.enabled) {
            return;
        }
        try {
            HackManager.onPlayerEventPost(event);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}

