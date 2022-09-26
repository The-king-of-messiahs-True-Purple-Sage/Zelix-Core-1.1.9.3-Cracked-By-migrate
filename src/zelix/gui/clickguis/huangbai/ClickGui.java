/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.huangbai;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import zelix.Core;
import zelix.gui.clickguis.huangbai.ClickGuiRender;
import zelix.gui.clickguis.huangbai.Colors2;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.HGLUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.utils.resourceloader.Strings;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class ClickGui
extends GuiScreen {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution sr = new ScaledResolution(mc);
    HackCategory currentModuleType = HackCategory.VISUAL;
    Hack currentModule = HackManager.getModulesInType(this.currentModuleType).get(0);
    public static float startX = sr.getScaledWidth() / 2 - 225;
    public static float startY = sr.getScaledHeight() / 2 - 175;
    public static int moduleStart = 0;
    public static int valueStart = 0;
    boolean previousmouse = true;
    boolean mouse;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    boolean bind = false;
    float hue;
    public static int alpha;
    public static int alphe;
    int time = 0;
    int press = 0;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int i;
        FontRenderer fontc = Wrapper.INSTANCE.fontRenderer();
        sr = new ScaledResolution(mc);
        if (alpha < 255) {
            alpha += 5;
        }
        if (this.hue > 255.0f) {
            this.hue = 0.0f;
        }
        float h = this.hue;
        float h2 = this.hue + 85.0f;
        float h3 = this.hue + 170.0f;
        if (h > 255.0f) {
            h = 0.0f;
        }
        if (h2 > 255.0f) {
            h2 -= 255.0f;
        }
        if (h3 > 255.0f) {
            h3 -= 255.0f;
        }
        Color color33 = Color.getHSBColor(h / 255.0f, 0.9f, 1.0f);
        Color color332 = Color.getHSBColor(h2 / 255.0f, 0.9f, 1.0f);
        Color color333 = Color.getHSBColor(h3 / 255.0f, 0.9f, 1.0f);
        int color1 = color33.getRGB();
        int color2 = color332.getRGB();
        int color3 = color333.getRGB();
        int color4 = new Color(255, 255, 255, alpha).getRGB();
        this.hue = (float)((double)this.hue + 0.1);
        HGLUtils.drawWindow(startX, startY, startX + 400.0f, startY + 300.0f, 0.5f, Colors2.getColor(90, alpha), Colors2.getColor(0, alpha));
        HGLUtils.drawWindow(startX + 1.0f, startY + 1.0f, startX + 400.0f - 1.0f, startY + 300.0f - 1.0f, 1.0f, Colors2.getColor(90, alpha), Colors2.getColor(61, alpha));
        HGLUtils.drawWindow((double)startX + 2.5, (double)startY + 2.5, (double)(startX + 400.0f) - 2.5, (double)(startY + 300.0f) - 2.5, 0.5f, Colors2.getColor(61, alpha), Colors2.getColor(0, alpha));
        HGLUtils.drawWindow(startX + 3.0f, startY + 3.0f, startX + 400.0f - 3.0f, startY + 300.0f - 3.0f, 0.5f, Colors2.getColor(27, alpha), Colors2.getColor(61, alphe));
        if (alpha >= 55) {
            ClickGuiRender.drawGradientSideways(startX + 3.0f, startY + 3.0f, startX + 200.0f, (double)startY + 4.0, color1, color2);
            ClickGuiRender.drawGradientSideways(startX + 200.0f, startY + 3.0f, startX + 400.0f - 3.1f, (double)startY + 4.0, color2, color3);
        }
        ClickGuiRender.drawRect(startX + 98.0f, startY + 100.0f, startX + 290.0f, startY + 108.0f, new Color(30, 30, 30, alpha).getRGB());
        ClickGuiRender.drawRect(startX + 100.0f, startY + 40.0f, startX + 350.0f, startY + 277.0f, new Color(35, 35, 35, alpha).getRGB());
        ClickGuiRender.drawRect(startX + 200.0f, startY + 100.0f, startX + 350.0f, startY + 277.0f, new Color(37, 37, 37, alpha).getRGB());
        ClickGuiRender.drawRect(startX + 190.0f, startY + 40.0f, startX + 390.0f, startY + 277.0f, new Color(40, 40, 40, alpha).getRGB());
        FontLoaders.default18.drawString("ZELIX", (int)(startX + 10.0f), (int)startY + 15, new Color(180, 180, 180, alpha).getRGB());
        FontLoaders.default18.drawString("1.1.6", (int)startX + 57, (int)startY + 14, new Color(180, 180, 180, alpha).getRGB());
        FontLoaders.default18.drawString("Hello, " + Core.UN + " !", (int)(startX + 430.0f - 100.0f - (float)FontLoaders.default18.getStringWidth("Hello, " + Core.UN + " !")), (int)startY + 15, new Color(200, 200, 200, alpha).getRGB());
        int m = Mouse.getDWheel();
        if (this.isCategoryHovered(startX + 100.0f, startY + 40.0f, startX + 200.0f, startY + 315.0f, mouseX, mouseY)) {
            if (m < 0 && moduleStart < HackManager.getModulesInType(this.currentModuleType).size() - 1) {
                ++moduleStart;
            }
            if (m > 0 && moduleStart > 0) {
                --moduleStart;
            }
        }
        if (this.isCategoryHovered(startX + 200.0f, startY + 50.0f, startX + 430.0f, startY + 315.0f, mouseX, mouseY)) {
            if (m < 0 && valueStart < this.currentModule.getValues().size() - 1) {
                ++valueStart;
            }
            if (m > 0 && valueStart > 0) {
                --valueStart;
            }
        }
        float mY = startY - 4.2f;
        for (i = 0; i < HackManager.getModulesInType(this.currentModuleType).size(); ++i) {
            Hack module = HackManager.getModulesInType(this.currentModuleType).get(i);
            if (mY > startY + 250.0f) break;
            if (i < moduleStart) continue;
            if (!module.isToggled()) {
                ClickGuiRender.drawRect(startX + 100.0f, mY + 45.0f, startX + 100.0f, mY + 70.0f, this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? new Color(60, 60, 60, alpha).getRGB() : new Color(35, 35, 35, alpha).getRGB());
                ClickGuiRender.drawFilledCircle(startX + (float)(this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? 112 : 110), mY + 58.0f, 3.0, new Color(70, 70, 70, alpha).getRGB(), 5);
                Wrapper.INSTANCE.fontRenderer().drawString(module.getRenderName(), (int)startX + (this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? 122 : 120), (int)mY + 55, new Color(175, 175, 175, alpha).getRGB());
            } else {
                ClickGuiRender.drawRect(startX + 100.0f, mY + 45.0f, startX + 190.0f, mY + 70.0f, this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? new Color(60, 60, 60, alpha).getRGB() : new Color(35, 35, 35, alpha).getRGB());
                ClickGuiRender.drawFilledCircle(startX + (float)(this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? 112 : 110), mY + 58.0f, 3.0, new Color(100, 255, 100, alpha).getRGB(), 5);
                Wrapper.INSTANCE.fontRenderer().drawString(module.getRenderName(), (int)startX + (this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) ? 122 : 120), (int)mY + 55, new Color(255, 255, 255, alpha).getRGB());
            }
            if (this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY)) {
                if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                    if (module.isToggled()) {
                        module.setToggled(false);
                        module.onDisable();
                    } else {
                        module.setToggled(true);
                        module.onEnable();
                    }
                    this.previousmouse = true;
                }
                if (!this.previousmouse && Mouse.isButtonDown((int)1)) {
                    this.previousmouse = true;
                }
            }
            if (!Mouse.isButtonDown((int)0)) {
                this.previousmouse = false;
            }
            if (this.isSettingsButtonHovered(startX + 100.0f, mY + 45.0f, startX + 200.0f, mY + 70.0f, mouseX, mouseY) && Mouse.isButtonDown((int)1)) {
                for (Hack hack : HackManager.getModulesInType(this.currentModuleType)) {
                }
                this.currentModule = module;
                valueStart = 0;
            }
            mY += 25.0f;
        }
        mY = startY + 12.0f;
        fontc.drawString(this.currentModule.getDescription(), (int)startX + 200, (int)mY + 36, new Color(170, 170, 170).getRGB());
        for (i = 0; i < this.currentModule.getValues().size() && !(mY > startY + 220.0f); ++i) {
            float x;
            if (i < valueStart) continue;
            Value value = this.currentModule.getValues().get(i);
            if (value instanceof NumberValue) {
                x = startX + 300.0f;
                double render = 68.0f * (((Double)((NumberValue)value).getValue()).floatValue() - ((NumberValue)value).getMin().floatValue()) / (((NumberValue)value).getMax().floatValue() - ((NumberValue)value).getMin().floatValue());
                ClickGuiRender.drawRect(x + 2.0f, mY + 52.0f, (float)((double)x + 75.0), mY + 53.0f, this.isButtonHovered(x, mY + 45.0f, x + 100.0f, mY + 57.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0) ? new Color(80, 80, 80, alpha).getRGB() : new Color(30, 30, 30, alpha).getRGB());
                ClickGuiRender.drawRect(x + 2.0f, mY + 52.0f, (float)((double)x + render + 6.5), mY + 53.0f, new Color(35, 35, 255, alpha).getRGB());
                ClickGuiRender.drawFilledCircle((float)((double)x + render + 2.0) + 3.0f, (double)mY + 52.25, 1.5, new Color(35, 35, 255, alpha).getRGB(), 5);
                Wrapper.INSTANCE.fontRenderer().drawString(value.getRenderName(), (int)startX + 200, (int)mY + 50, new Color(175, 175, 175, alpha).getRGB());
                Wrapper.INSTANCE.fontRenderer().drawString(value.getValue().toString(), (int)startX + 300 - Wrapper.INSTANCE.fontRenderer().getStringWidth(value.getValue().toString()), (int)mY + 50, new Color(255, 255, 255, alpha).getRGB());
                if (!Mouse.isButtonDown((int)0)) {
                    this.previousmouse = false;
                }
                if (this.isButtonHovered(x, mY + 45.0f, x + 100.0f, mY + 57.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                    if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                        render = ((NumberValue)value).getMin();
                        double max = ((NumberValue)value).getMax();
                        double inc = 0.01;
                        double valAbs = (double)mouseX - ((double)x + 1.0);
                        double d = valAbs / 68.0;
                        d = Math.min(Math.max(0.0, d), 1.0);
                        double valRel = (max - render) * d;
                        double val = render + valRel;
                        val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                        value.setValue(val);
                    }
                    if (!Mouse.isButtonDown((int)0)) {
                        this.previousmouse = false;
                    }
                }
                mY += 20.0f;
                FileManager.saveHacks();
            }
            if (value instanceof BooleanValue) {
                x = startX + 300.0f;
                int xx = 30;
                int x2x = 45;
                Wrapper.INSTANCE.fontRenderer().drawString(value.getRenderName(), (int)startX + 200, (int)mY + 50, new Color(175, 175, 175, alpha).getRGB());
                if (((Boolean)value.getValue()).booleanValue()) {
                    ClickGuiRender.drawRect(x + (float)xx, mY + 50.0f, x + (float)x2x, mY + 59.0f, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB());
                    ClickGuiRender.drawFilledCircle(x + (float)xx, (double)mY + 54.5, 4.5, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB(), 10);
                    ClickGuiRender.drawFilledCircle(x + (float)x2x, (double)mY + 54.5, 4.5, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB(), 10);
                    ClickGuiRender.drawFilledCircle(x + (float)x2x, (double)mY + 54.5, 5.0, new Color(35, 35, 255, alpha).getRGB(), 10);
                } else {
                    ClickGuiRender.drawRect(x + (float)xx, mY + 50.0f, x + (float)x2x, mY + 59.0f, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB());
                    ClickGuiRender.drawFilledCircle(x + (float)xx, (double)mY + 54.5, 4.5, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB(), 10);
                    ClickGuiRender.drawFilledCircle(x + (float)x2x, (double)mY + 54.5, 4.5, this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(20, 20, 20, alpha).getRGB(), 10);
                    ClickGuiRender.drawFilledCircle(x + (float)xx, (double)mY + 54.5, 5.0, new Color(56, 56, 56, alpha).getRGB(), 10);
                }
                if (this.isCheckBoxHovered(x + (float)xx - 5.0f, mY + 50.0f, x + (float)x2x + 6.0f, mY + 59.0f, mouseX, mouseY)) {
                    if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                        this.previousmouse = true;
                        this.mouse = true;
                    }
                    if (this.mouse) {
                        value.setValue((Boolean)value.getValue() == false);
                        this.mouse = false;
                    }
                }
                if (!Mouse.isButtonDown((int)0)) {
                    this.previousmouse = false;
                }
                mY += 20.0f;
                FileManager.saveHacks();
            }
            if (value instanceof ModeValue) {
                x = startX + 300.0f;
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(((ModeValue)value).getRenderName(), startX + 200.0f, mY + 52.0f, new Color(175, 175, 175, alpha).getRGB());
                ClickGuiRender.drawRect(x + 5.0f, mY + 45.0f, x + 75.0f, mY + 65.0f, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB());
                ClickGuiRender.drawRect(x + 2.0f, mY + 48.0f, x + 78.0f, mY + 62.0f, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB());
                ClickGuiRender.drawFilledCircle(x + 5.0f, mY + 48.0f, 3.0, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB(), 5);
                ClickGuiRender.drawFilledCircle(x + 5.0f, mY + 62.0f, 3.0, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB(), 5);
                ClickGuiRender.drawFilledCircle(x + 75.0f, mY + 48.0f, 3.0, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB(), 5);
                ClickGuiRender.drawFilledCircle(x + 75.0f, mY + 62.0f, 3.0, this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) ? new Color(80, 80, 80, alpha).getRGB() : new Color(56, 56, 56, alpha).getRGB(), 5);
                if (((ModeValue)value).getSelectMode() != null) {
                    Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(((ModeValue)value).getSelectMode().getName(), x + 40.0f - (float)(Wrapper.INSTANCE.fontRenderer().getStringWidth(((ModeValue)value).getSelectMode().getName()) / 2), mY + 53.0f, new Color(255, 255, 255, alpha).getRGB());
                }
                if (this.isStringHovered(x, mY + 45.0f, x + 75.0f, mY + 65.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0) && !this.previousmouse) {
                    ++this.press;
                    ModeValue modeValue = (ModeValue)value;
                    ArrayList<Mode> modes = new ArrayList<Mode>();
                    String t1 = null;
                    for (Mode mode2 : modeValue.getModes()) {
                        modes.add(mode2);
                    }
                    if (this.press <= modes.size()) {
                        ((Mode)modes.get(this.press - 1)).setToggled(true);
                        t1 = ((Mode)modes.get(this.press - 1)).getName();
                    } else {
                        this.press = 0;
                    }
                    for (Mode mode2 : modeValue.getModes()) {
                        if (mode2.getName() == t1) continue;
                        mode2.setToggled(false);
                    }
                    this.previousmouse = true;
                }
                mY += 25.0f;
            }
            FileManager.saveHacks();
        }
        float x = startX + 300.0f;
        float yyy = startY + 200.0f;
        if ((this.isHovered(startX, startY, startX + 450.0f, startY + 50.0f, mouseX, mouseY) || this.isHovered(startX, startY + 315.0f, startX + 450.0f, startY + 350.0f, mouseX, mouseY) || this.isHovered(startX + 430.0f, startY, startX + 450.0f, startY + 350.0f, mouseX, mouseY)) && Mouse.isButtonDown((int)0)) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = (float)mouseX - startX;
                this.moveY = (float)mouseY - startY;
            } else {
                startX = (float)mouseX - this.moveX;
                startY = (float)mouseY - this.moveY;
            }
            this.previousmouse = true;
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        if (this.isHovered(sr.getScaledWidth() / 2 - 40, 0.0f, sr.getScaledWidth() / 2 + 40, 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            alpha = 0;
            alphe = 0;
        } else {
            alphe = 121;
        }
        int j = 59;
        int l = 40;
        float k = startY + 10.0f;
        float xx = startX + 5.0f;
        for (int i2 = 0; i2 < HackCategory.values().length; ++i2) {
            HackCategory[] iterator = HackCategory.values();
            if (iterator[i2] == this.currentModuleType) {
                float typey = k + 5.0f + (float)j + (float)(i2 * l);
                ClickGuiRender.drawRect(xx + 4.0f, typey, xx + 25.0f, typey + 2.0f, color4);
            }
            String[] Hack2 = new String[HackCategory.values().length];
            for (int n = 0; n < HackCategory.values().length; ++n) {
                Hack2[n] = zelix.hack.hacks.ClickGui.language.getMode("Chinese").isToggled() ? Strings.Chinese_HackCategory.split("=")[n] : iterator[n].toString();
            }
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(Hack2[i2], xx + (float)(this.isCategoryHovered(xx + 8.0f, k - 10.0f + (float)j + (float)(i2 * l), xx + 80.0f, k + 20.0f + (float)j + (float)(i2 * l), mouseX, mouseY) ? 27 : 25), k + 50.0f + (float)(l * i2), new Color(255, 255, 255, alpha).getRGB());
            try {
                if (!this.isCategoryHovered(xx + 8.0f, k - 10.0f + (float)j + (float)(i2 * l), xx + 80.0f, k + 20.0f + (float)j + (float)(i2 * l), mouseX, mouseY) || !Mouse.isButtonDown((int)0)) continue;
                this.currentModuleType = iterator[i2];
                this.currentModule = HackManager.getModulesInType(this.currentModuleType).get(0);
                moduleStart = 0;
                valueStart = 0;
                for (int x1 = 0; x1 < this.currentModule.getValues().size(); ++x1) {
                    Value object = this.currentModule.getValues().get(x1);
                }
                for (Hack hack : HackManager.getModulesInType(this.currentModuleType)) {
                }
                continue;
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void initGui() {
        for (int i = 0; i < this.currentModule.getValues().size(); ++i) {
            Value object = this.currentModule.getValues().get(i);
        }
        for (Hack hack : HackManager.getModulesInType(this.currentModuleType)) {
        }
        super.initGui();
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (this.bind) {
            this.currentModule.setKey(keyCode);
            if (keyCode == 1) {
                this.currentModule.setKey(0);
            }
            this.bind = false;
        } else if (keyCode == 1) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)null);
            if (Wrapper.INSTANCE.mc().currentScreen == null) {
                Wrapper.INSTANCE.mc().setIngameFocus();
            }
            return;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        float x = startX + 220.0f;
        float mY = startY + 30.0f;
        for (int i = 0; i < this.currentModule.getValues().size() && !(mY > startY + 350.0f); ++i) {
            if (i < valueStart) continue;
            Value value = this.currentModule.getValues().get(i);
            if (value instanceof NumberValue) {
                mY += 20.0f;
            }
            if (value instanceof BooleanValue) {
                mY += 20.0f;
            }
            if (!(value instanceof ModeValue)) continue;
            mY += 25.0f;
        }
        float x1 = startX + 300.0f;
        float yyy = startY + 200.0f;
        if (this.isHovered(x1 + 2.0f, yyy + 40.0f, x1 + 78.0f, yyy + 70.0f, mouseX, mouseY)) {
            this.bind = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public void onGuiClosed() {
        alpha = 0;
    }

    static {
        alphe = 121;
    }
}

