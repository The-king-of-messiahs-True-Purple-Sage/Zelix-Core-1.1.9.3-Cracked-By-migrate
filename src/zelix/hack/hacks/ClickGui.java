/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.Core;
import zelix.LoadClient;
import zelix.gui.clickguis.Astolfo.ClickGui.ClickUi;
import zelix.gui.clickguis.N3ro.GuiClickUI;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.clickgui.shell.GuiClickShell;
import zelix.gui.clickguis.kendall.KendallScreen;
import zelix.gui.clickguis.reflection.clickgui.ClickGuiManager;
import zelix.gui.clickguis.tenacity.TenacityScreen;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.GhostMode;
import zelix.managers.FileManager;
import zelix.utils.Cr4sh;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class ClickGui
extends Hack {
    public ModeValue theme;
    public static ModeValue language;
    public static int memoriseX;
    public static int memoriseY;
    public static int memoriseWheel;
    public static BooleanValue rainbow;
    public static BooleanValue shadow;
    public static BooleanValue tooltip;
    public static NumberValue red;
    public static NumberValue green;
    public static NumberValue blue;
    public static NumberValue alpha;
    public static Minecraft mc;
    private static int color;
    public static boolean isLight;
    public static List<Hack> memoriseML;
    public static HackCategory memoriseCatecory;
    public GuiClickShell guiClickShell;
    public static KendallScreen KendallMyGod;
    public static float shell_x;
    public static float shell_y;
    public static HackCategory shell_category;
    public static Hack shell_module;

    public ClickGui() {
        super("ClickGui", HackCategory.VISUAL);
        this.setKey(54);
        this.setShow(false);
        this.theme = new ModeValue("Theme", new Mode("Reflection", false), new Mode("Tenacity", false), new Mode("Kendall", true), new Mode("Shell", false), new Mode("Caesium", false), new Mode("Dark", false), new Mode("Light", false), new Mode("HuangBai", false), new Mode("N3ro", false), new Mode("Astolfo", false));
        language = new ModeValue("Language", new Mode("Chinese", false), new Mode("English", true));
        tooltip = new BooleanValue("Tooltip", true);
        shadow = new BooleanValue("Shadow", true);
        rainbow = new BooleanValue("Rainbow", true);
        red = new NumberValue("Red", 255.0, 0.0, 255.0);
        green = new NumberValue("Green", 255.0, 0.0, 255.0);
        blue = new NumberValue("Blue", 255.0, 0.0, 255.0);
        alpha = new NumberValue("Alpha", 255.0, 0.0, 255.0);
        this.addValue(this.theme, language, tooltip, shadow, rainbow, red, green, blue, alpha);
        this.setColor();
    }

    @Override
    public String getDescription() {
        return "Graphical user interface.";
    }

    public static int getColor() {
        return (Boolean)rainbow.getValue() != false ? ColorUtils.rainbow().getRGB() : color;
    }

    public static void setColor() {
        color = ColorUtils.color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue(), ((Double)alpha.getValue()).intValue());
    }

    @Override
    public void onEnable() {
        if (GhostMode.enabled) {
            return;
        }
        FileManager.saveHacks();
        Core.fileManager.loadClickGui();
        if (!LoadClient.isCheck) {
            new Cr4sh();
        }
        Boolean isN3ro = this.theme.getMode("N3ro").isToggled();
        Boolean isAstolfo = this.theme.getMode("Astolfo").isToggled();
        if (isN3ro.booleanValue()) {
            mc.displayGuiScreen((GuiScreen)new GuiClickUI());
            GuiClickUI.setX(memoriseX);
            GuiClickUI.setY(memoriseY);
            GuiClickUI.setWheel(memoriseWheel);
            GuiClickUI.setInSetting(memoriseML);
            if (memoriseCatecory != null) {
                GuiClickUI.setCategory(memoriseCatecory);
            }
        } else if (isAstolfo.booleanValue()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new ClickUi());
        } else if (this.theme.getMode("Dark").isToggled() || this.theme.getMode("Light").isToggled()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)Core.hackManager.getGui());
        } else if (this.theme.getMode("HuangBai").isToggled()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new zelix.gui.clickguis.huangbai.ClickGui());
        } else if (this.theme.getMode("Caesium").isToggled()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new Panel("Caesium", 22));
        } else if (this.theme.getMode("Reflection").isToggled()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new ClickGuiManager());
        } else if (this.theme.getMode("Kendall").isToggled()) {
            KendallMyGod = new KendallScreen();
            Core.fileManager.loadClickGui_Kendall();
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)KendallMyGod);
        } else if (this.theme.getMode("Tenacity").isToggled()) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new TenacityScreen());
        } else {
            if (this.guiClickShell == null) {
                this.guiClickShell = new GuiClickShell();
            }
            GuiClickShell.setX(shell_x);
            GuiClickShell.setY(shell_y);
            GuiClickShell.setCategory(shell_category);
            GuiClickShell.setMod(shell_module);
            mc.displayGuiScreen((GuiScreen)this.guiClickShell);
        }
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.setColor();
        isLight = this.theme.getMode("Light").isToggled();
        super.onClientTick(event);
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (((Boolean)shadow.getValue()).booleanValue()) {
            ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
            RenderUtils.drawRect(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f));
        }
        super.onRenderGameOverlay(event);
    }

    static {
        memoriseX = 30;
        memoriseY = 30;
        memoriseWheel = 0;
        mc = Wrapper.INSTANCE.mc();
        isLight = false;
        memoriseML = new CopyOnWriteArrayList<Hack>();
        memoriseCatecory = null;
        shell_y = 30.0f;
        shell_category = null;
        shell_module = null;
    }
}

