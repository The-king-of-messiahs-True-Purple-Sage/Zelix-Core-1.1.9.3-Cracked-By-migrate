/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.config.Config$Type
 *  net.minecraftforge.common.config.ConfigManager
 *  net.minecraftforge.fml.client.event.ConfigChangedEvent$OnConfigChangedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.hack.hacks.xray;

import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.hack.hacks.xray.AntiAntiXrayMod;
import zelix.hack.hacks.xray.gui.GuiOverlay;
import zelix.hack.hacks.xray.keybinding.InputEvent;
import zelix.hack.hacks.xray.keybinding.KeyBindings;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.SimpleBlockData;
import zelix.hack.hacks.xray.store.BlockStore;
import zelix.hack.hacks.xray.store.GameBlockStore;
import zelix.hack.hacks.xray.store.JsonStore;
import zelix.hack.hacks.xray.xray.Controller;
import zelix.hack.hacks.xray.xray.Events;
import zelix.utils.hooks.visual.ChatUtils;

public class XRay {
    public static GameBlockStore gameBlockStore = new GameBlockStore();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static JsonStore blockStore = new JsonStore();
    public static XRay instance;
    public InputEvent InputEvent;
    public Events Events;
    public GuiOverlay GuiOverlay;

    public void onEnable() {
        ChatUtils.message("Loading Anti-AntiXray");
        if (AntiAntiXrayMod.isFirst) {
            KeyBindings.setup();
        }
        this.InputEvent = new InputEvent();
        MinecraftForge.EVENT_BUS.register((Object)this.InputEvent);
        this.Events = new Events();
        MinecraftForge.EVENT_BUS.register((Object)this.Events);
        this.GuiOverlay = new GuiOverlay();
        MinecraftForge.EVENT_BUS.register((Object)this.GuiOverlay);
        MinecraftForge.EVENT_BUS.register((Object)this);
        List<SimpleBlockData> data = blockStore.read();
        if (data.isEmpty()) {
            return;
        }
        HashMap<String, BlockData> map = BlockStore.getFromSimpleBlockList(data);
        Controller.getBlockStore().setStore(map);
        gameBlockStore.populate();
        instance = this;
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this.InputEvent);
        MinecraftForge.EVENT_BUS.unregister((Object)this.Events);
        MinecraftForge.EVENT_BUS.unregister((Object)this.GuiOverlay);
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Controller.shutdownExecutor();
        instance = null;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("xray")) {
            ConfigManager.sync((String)"xray", (Config.Type)Config.Type.INSTANCE);
        }
    }
}

