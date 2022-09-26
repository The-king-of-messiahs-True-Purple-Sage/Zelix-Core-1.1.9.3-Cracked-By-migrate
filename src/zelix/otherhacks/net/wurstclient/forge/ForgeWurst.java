/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.common.reflect.TypeToken
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.EventBus
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.CommandList;
import zelix.otherhacks.net.wurstclient.forge.CommandProcessor;
import zelix.otherhacks.net.wurstclient.forge.HackList;
import zelix.otherhacks.net.wurstclient.forge.IngameHUD;
import zelix.otherhacks.net.wurstclient.forge.KeybindList;
import zelix.otherhacks.net.wurstclient.forge.KeybindProcessor;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WEventFactory;

public final class ForgeWurst {
    public static final String MODID = "forgewurst";
    public static final String VERSION = "0.11";
    private static ForgeWurst forgeWurst = null;
    private boolean obfuscated;
    private Path configFolder;
    private HackList hax;
    private CommandList cmds;
    private KeybindList keybinds;
    private ClickGui gui;
    private IngameHUD hud;
    private CommandProcessor cmdProcessor;
    private KeybindProcessor keybindProcessor;
    private Method register;

    public ForgeWurst() {
        forgeWurst = this;
        this.init();
    }

    public void init() {
        try {
            this.register = EventBus.class.getDeclaredMethod("register", Class.class, Object.class, Method.class, ModContainer.class);
            this.register.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String mcClassName = Minecraft.class.getName().replace(".", "/");
        FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
        this.obfuscated = !mcClassName.equals(remapper.unmap(mcClassName));
        this.configFolder = Minecraft.getMinecraft().mcDataDir.toPath().resolve("wurst");
        try {
            Files.createDirectories(this.configFolder, new FileAttribute[0]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hax = new HackList(this.configFolder.resolve("enabled-hacks.json"), this.configFolder.resolve("settings.json"));
        this.hax.loadEnabledHacks();
        this.hax.loadSettings();
        this.cmds = new CommandList();
        this.keybinds = new KeybindList(this.configFolder.resolve("keybinds.json"));
        this.keybinds.init();
        this.gui = new ClickGui(this.configFolder.resolve("windows.json"));
        this.gui.init(this.hax);
        this.hud = new IngameHUD(this.hax, this.gui);
        this.register(this.hud);
        this.cmdProcessor = new CommandProcessor(this.cmds);
        this.register(this.cmdProcessor);
        this.keybindProcessor = new KeybindProcessor(this.hax, this.keybinds, this.cmdProcessor);
        this.register(this.keybindProcessor);
        this.register(this.keybindProcessor);
        this.register(new WEventFactory());
    }

    public static ForgeWurst getForgeWurst() {
        return forgeWurst;
    }

    public boolean isObfuscated() {
        return this.obfuscated;
    }

    public HackList getHax() {
        return this.hax;
    }

    public CommandList getCmds() {
        return this.cmds;
    }

    public KeybindList getKeybinds() {
        return this.keybinds;
    }

    public ClickGui getGui() {
        return this.gui;
    }

    public void register(Object target) {
        boolean isStatic = target.getClass() == Class.class;
        Set supers = isStatic ? Sets.newHashSet((Object[])new Class[]{(Class)target}) : TypeToken.of(target.getClass()).getTypes().rawTypes();
        block5: for (Method method : (isStatic ? (Class<?>)target : target.getClass()).getMethods()) {
            if (isStatic && !Modifier.isStatic(method.getModifiers()) || !isStatic && Modifier.isStatic(method.getModifiers())) continue;
            for (Class cls : supers) {
                try {
                    Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (!real.isAnnotationPresent(SubscribeEvent.class)) continue;
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods must require a single argument.");
                    }
                    Class<?> eventType = parameterTypes[0];
                    if (!Event.class.isAssignableFrom(eventType)) {
                        throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes a argument that is not an Event " + eventType);
                    }
                    try {
                        this.register.invoke((Object)MinecraftForge.EVENT_BUS, eventType, target, real, Loader.instance().getMinecraftModContainer());
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    continue block5;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                }
            }
        }
    }
}

