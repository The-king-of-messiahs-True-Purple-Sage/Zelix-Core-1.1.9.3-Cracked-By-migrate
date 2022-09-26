/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.client.gui.GuiScreen;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGuiScreen;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;

@Hack.DontSaveState
public final class ClickGuiHack
extends Hack {
    private final SliderSetting opacity = new SliderSetting("Opacity", 0.5, 0.15, 0.85, 0.01, SliderSetting.ValueDisplay.PERCENTAGE);
    private final SliderSetting maxHeight = new SliderSetting("Max height", "Maximum window height\n0 = no limit", 200.0, 0.0, 1000.0, 25.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting bgRed = new SliderSetting("BG red", "Background red", 64.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting bgGreen = new SliderSetting("BG green", "Background green", 64.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting bgBlue = new SliderSetting("BG blue", "Background blue", 64.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting acRed = new SliderSetting("AC red", "Accent red", 16.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting acGreen = new SliderSetting("AC green", "Accent green", 16.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);
    private final SliderSetting acBlue = new SliderSetting("AC blue", "Accent blue", 16.0, 0.0, 255.0, 1.0, SliderSetting.ValueDisplay.INTEGER);

    public ClickGuiHack() {
        super("ClickGUI", "");
        this.addSetting(this.opacity);
        this.addSetting(this.maxHeight);
        this.addSetting(this.bgRed);
        this.addSetting(this.bgGreen);
        this.addSetting(this.bgBlue);
        this.addSetting(this.acRed);
        this.addSetting(this.acGreen);
        this.addSetting(this.acBlue);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen((GuiScreen)new ClickGuiScreen(wurst.getGui()));
        this.setEnabled(false);
    }

    public float getOpacity() {
        return this.opacity.getValueF();
    }

    public int getMaxHeight() {
        return this.maxHeight.getValueI();
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight.setValue(maxHeight);
    }

    public float[] getBgColor() {
        return new float[]{(float)this.bgRed.getValueI() / 255.0f, (float)this.bgGreen.getValueI() / 255.0f, (float)this.bgBlue.getValueI() / 255.0f};
    }

    public float[] getAcColor() {
        return new float[]{(float)this.acRed.getValueI() / 255.0f, (float)this.acGreen.getValueI() / 255.0f, (float)this.acBlue.getValueI() / 255.0f};
    }
}

