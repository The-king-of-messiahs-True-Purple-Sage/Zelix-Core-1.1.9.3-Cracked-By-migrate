/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.kendall.components.impls;

import java.awt.Color;
import java.util.ArrayList;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.gui.clickguis.kendall.components.Component;
import zelix.gui.clickguis.kendall.components.impls.KendallMode;
import zelix.gui.clickguis.kendall.components.impls.KendallOption;
import zelix.gui.clickguis.kendall.components.impls.KendallSIlder;
import zelix.gui.clickguis.kendall.frame.KendallFrame;
import zelix.hack.Hack;
import zelix.hack.hacks.ClickGui;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.BooleanValue;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class KendallButton {
    public float x;
    public float y;
    public boolean showSettings;
    public Hack mod;
    public KendallFrame parent;
    public static int drawColor;
    public Color color = new Color(-1);
    public ArrayList<Component> components = new ArrayList();
    int r1 = new Color(-1).getRed();
    int r2 = new Color(-16746051).getRed();
    public int task = 10;
    int i = 0;

    public KendallButton(Hack cheat, float x, float y, KendallFrame parent) {
        this.x = x;
        this.y = y;
        this.mod = cheat;
        this.parent = parent;
        this.init();
    }

    public void init() {
        this.components.clear();
        for (Value v : this.mod.getValues()) {
            if (v instanceof BooleanValue) {
                this.components.add(new KendallOption((BooleanValue)v, this));
            }
            if (v instanceof NumberValue) {
                this.components.add(new KendallSIlder((NumberValue)v, this));
            }
            if (!(v instanceof ModeValue)) continue;
            this.components.add(new KendallMode((ModeValue)v, this));
        }
    }

    public void onRender(int mouseX, int mouseY, boolean last) {
        this.x = this.parent.x;
        this.color = new Color(this.r1, this.color.getGreen(), this.color.getBlue());
        if (this.mod.isToggled()) {
            if (last) {
                RenderUtil.drawRoundRect_down(this.x, this.y, (double)this.x + 100.0, (double)this.y + 18.0, 2, ColorUtils.rainbow(this.task));
            } else {
                RenderUtil.drawBorderedRect(this.x, this.y, this.x + 100.0f, this.y + 18.0f, 0.0f, 0, ColorUtils.rainbow(this.task));
            }
            if (!ClickGui.language.getMode("Chinese").isToggled()) {
                FontLoaders.default14.drawStringWithShadow(this.mod.getRenderName(), this.x + 10.0f, this.y + 7.0f, new Color(drawColor).getRGB());
            } else {
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(this.mod.getRenderName(), this.x + 10.0f, this.y + 5.0f, new Color(drawColor).getRGB());
            }
        } else {
            if (last) {
                RenderUtil.drawRoundRect_down(this.x, this.y, (double)this.x + 100.0, (double)this.y + 18.0, 2, KendallButton.isButtonHovered(this.x, this.y, this.x + 90.0f, this.y + 16.0f, mouseX, mouseY) ? -14869217 : -15329770);
            } else {
                RenderUtil.drawBorderedRect(this.x, this.y, this.x + 100.0f, this.y + 18.0f, 0.0f, 0, KendallButton.isButtonHovered(this.x, this.y, this.x + 90.0f, this.y + 16.0f, mouseX, mouseY) ? -14869217 : -15329770);
            }
            if (!ClickGui.language.getMode("Chinese").isToggled()) {
                FontLoaders.default14.drawStringWithShadow(this.mod.getRenderName(), this.x + 10.0f, this.y + 7.0f, -855638017);
            } else {
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(this.mod.getRenderName(), this.x + 10.0f, this.y + 5.0f, new Color(drawColor).getRGB());
            }
        }
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        System.out.println("x,u = " + mouseButton);
        if (mouseButton == 0 && KendallButton.isButtonHovered(this.x, this.y, this.x + 90.0f, this.y + 16.0f, mouseX, mouseY)) {
            this.mod.setToggled(!this.mod.isToggled());
        }
        if (mouseButton == 1 && KendallFrame.isHovered(this.x, this.y, this.x + 90.0f, this.y + 16.0f, mouseX, mouseY)) {
            ClickGui.KendallMyGod.isVIsableSW = true;
            ClickGui.KendallMyGod.targetbt = this;
        }
    }

    public static boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }
}

