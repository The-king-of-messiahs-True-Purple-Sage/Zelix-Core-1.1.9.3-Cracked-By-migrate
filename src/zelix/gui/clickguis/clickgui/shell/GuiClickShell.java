/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.clickgui.shell;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.Core;
import zelix.gui.Notification.Utils.AnimationUtils;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.Colors;
import zelix.utils.hooks.visual.HGLUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.CFontRenderer;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class GuiClickShell
extends GuiScreen {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution sr = new ScaledResolution(mc);
    public AnimationUtils animationUtils = new AnimationUtils();
    public static HackCategory currentModuleType;
    public static Hack currentModule;
    public static float startX;
    public static float startY;
    public static int moduleStart;
    public static int valueStart;
    boolean previousmouse = true;
    boolean mouse;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    boolean bind = false;
    float hue;
    public static int alpha;
    public static int alphe;
    int time = 0;
    int main_width = 450;
    int main_height = 296;
    int press = 0;
    private int modeIndex = 0;
    private boolean animate = false;
    private float target = startY + 60.0f;
    private float h = startY + 60.0f;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Value value;
        int x1;
        CFontRenderer font = FontLoaders.default16;
        sr = new ScaledResolution(mc);
        GL11.glPushMatrix();
        HGLUtils.drawRoundedRect2(startX, startY, startX + (float)this.main_width, startY + (float)this.main_height, 4.0f, Colors.getColor(29, 29, 31));
        RenderUtils.drawRect(startX + 101.0f, startY + 51.0f, startX + (float)this.main_width, startY + (float)this.main_height, Colors.getColor(38, 38, 41));
        FontLoaders.icon24.drawString("Zelix", startX + 10.0f, startY + 20.0f, Color.white.getRGB());
        GL11.glPopMatrix();
        RenderUtils.drawRect(startX, startY + 50.0f, startX + (float)this.main_width, startY + 51.0f, Colors.getColor(78, 78, 78));
        if (this.isCategoryHovered(startX + 110.0f, startY + 10.0f, startX + 160.0f, startY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            currentModuleType = HackCategory.COMBAT;
            currentModule = HackManager.getModulesInType(currentModuleType).get(0);
            moduleStart = 0;
            valueStart = 0;
            for (x1 = 0; x1 < currentModule.getValues().size(); ++x1) {
                value = currentModule.getValues().get(x1);
            }
        }
        if (currentModuleType == HackCategory.COMBAT) {
            FontLoaders.default20.drawString("COMBAT", startX + 113.0f, startY + 20.0f, Colors.getColor(245, 245, 247));
        } else {
            FontLoaders.default20.drawString("COMBAT", startX + 113.0f, startY + 20.0f, Colors.getColor(165, 165, 165));
        }
        if (this.isCategoryHovered(startX + 170.0f, startY + 10.0f, startX + 220.0f, startY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            currentModuleType = HackCategory.MOVEMENT;
            currentModule = HackManager.getModulesInType(currentModuleType).get(0);
            moduleStart = 0;
            valueStart = 0;
            for (x1 = 0; x1 < currentModule.getValues().size(); ++x1) {
                value = currentModule.getValues().get(x1);
            }
        }
        if (currentModuleType == HackCategory.MOVEMENT) {
            FontLoaders.default20.drawString("MOVEMENT", startX + 166.0f, startY + 20.0f, Colors.getColor(245, 245, 247));
        } else {
            FontLoaders.default20.drawString("MOVEMENT", startX + 166.0f, startY + 20.0f, Colors.getColor(165, 165, 165));
        }
        if (this.isCategoryHovered(startX + 230.0f, startY + 10.0f, startX + 280.0f, startY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            currentModuleType = HackCategory.PLAYER;
            currentModule = HackManager.getModulesInType(currentModuleType).get(0);
            moduleStart = 0;
            valueStart = 0;
            if (currentModule.getValues() != null) {
                for (x1 = 0; x1 < currentModule.getValues().size(); ++x1) {
                    value = currentModule.getValues().get(x1);
                }
            }
        }
        if (currentModuleType == HackCategory.PLAYER) {
            FontLoaders.default20.drawString("PLAYER", startX + 235.0f, startY + 20.0f, Colors.getColor(245, 245, 247));
        } else {
            FontLoaders.default20.drawString("PLAYER", startX + 235.0f, startY + 20.0f, Colors.getColor(165, 165, 165));
        }
        if (this.isCategoryHovered(startX + 290.0f, startY + 10.0f, startX + 340.0f, startY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            currentModuleType = HackCategory.ANOTHER;
            currentModule = HackManager.getModulesInType(currentModuleType).get(0);
            moduleStart = 0;
            valueStart = 0;
            for (x1 = 0; x1 < currentModule.getValues().size(); ++x1) {
                value = currentModule.getValues().get(x1);
            }
        }
        if (currentModuleType == HackCategory.ANOTHER) {
            FontLoaders.default20.drawString("CLIENT", startX + 292.0f, startY + 20.0f, Colors.getColor(245, 245, 247));
        } else {
            FontLoaders.default20.drawString("CLIENT", startX + 292.0f, startY + 20.0f, Colors.getColor(165, 165, 165));
        }
        if (this.isCategoryHovered(startX + 350.0f, startY + 10.0f, startX + 400.0f, startY + 40.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            currentModuleType = HackCategory.VISUAL;
            currentModule = HackManager.getModulesInType(currentModuleType).get(0);
            moduleStart = 0;
            valueStart = 0;
            if (currentModule.getValues() != null) {
                for (x1 = 0; x1 < currentModule.getValues().size(); ++x1) {
                    value = currentModule.getValues().get(x1);
                }
            }
        }
        if (currentModuleType == HackCategory.VISUAL) {
            FontLoaders.default20.drawString("VISUAL", startX + 352.0f, startY + 20.0f, Colors.getColor(245, 245, 247));
        } else {
            FontLoaders.default20.drawString("VISUAL", startX + 352.0f, startY + 20.0f, Colors.getColor(165, 165, 165));
        }
        if (currentModule == null) {
            return;
        }
        int m = Mouse.getDWheel();
        RenderUtils.drawRect(startX + 100.0f, startY + 51.0f, startX + 101.0f, startY + (float)this.main_height, Colors.getColor(78, 78, 78));
        if (this.isCategoryHovered(startX, startY + 51.0f, startX + 100.0f, startY + (float)this.main_height, mouseX, mouseY)) {
            if (HackManager.getModulesInType(currentModuleType) != null) {
                if (m < 0) {
                    if (moduleStart < HackManager.getModulesInType(currentModuleType).size() - 1) {
                        ++moduleStart;
                    }
                }
                if (m > 0 && moduleStart > 0) {
                    --moduleStart;
                }
            }
        }
        if (this.isCategoryHovered(startX + 102.0f, startY + 51.0f, startX + (float)this.main_width, startY + (float)this.main_height, mouseX, mouseY) && currentModule.getValues() != null) {
            if (m < 0 && valueStart < currentModule.getValues().size() - 1) {
                ++valueStart;
            }
            if (m > 0 && valueStart > 0) {
                --valueStart;
            }
        }
        float mY = startY + 6.0f;
        int i = 0;
        while (true) {
            if (i >= HackManager.getModulesInType(currentModuleType).size()) break;
            Hack module = HackManager.getModulesInType(currentModuleType).get(i);
            if (mY > startY + (float)this.main_height - 70.0f) break;
            if (i >= moduleStart) {
                if (!module.isToggled()) {
                    RenderUtils.drawRoundRect(startX + 400.0f, startY + 260.0f, startX + 440.0f, startY + 280.0f, 4, Colors.getColor(100, 100, 100));
                    GL11.glEnable((int)3042);
                    FontLoaders.default18.drawCenteredString(this.bind ? "Press" : Keyboard.getKeyName((int)module.getKey()), startX + 420.0f, startY + 270.0f, Colors.getColor(255, 255, 255));
                    GL11.glDisable((int)3042);
                    this.animate = false;
                    if (false) {
                        RenderUtils.drawRoundRect(startX + 12.0f, mY + 55.0f - 4.0f, startX + 14.0f, mY + 55.0f + 10.0f, 1, Colors.getColor(245, 245, 245));
                    }
                    GL11.glEnable((int)3042);
                    font.drawString(module.getName(), (int)startX + 20, (int)mY + 55, new Color(165, 165, 165).getRGB());
                    GL11.glDisable((int)3042);
                } else {
                    RenderUtils.drawRoundRect(startX + 400.0f, startY + 260.0f, startX + 440.0f, startY + 280.0f, 4, Colors.getColor(100, 100, 100));
                    GL11.glEnable((int)3042);
                    FontLoaders.default18.drawCenteredString(this.bind ? "Press" : Keyboard.getKeyName((int)module.getKey()), startX + 420.0f, startY + 270.0f, Colors.getColor(255, 255, 255));
                    GL11.glDisable((int)3042);
                    this.animate = false;
                    if (false) {
                        RenderUtils.drawRoundRect(startX + 12.0f, mY + 55.0f - 4.0f, startX + 14.0f, mY + 55.0f + 10.0f, 1, Colors.getColor(245, 245, 245));
                    }
                    GL11.glEnable((int)3042);
                    font.drawString(module.getName(), (int)startX + 20, (int)mY + 55, new Color(245, 245, 247).getRGB());
                    GL11.glDisable((int)3042);
                }
                if (this.isSettingsButtonHovered(startX, mY + 45.0f, startX + 100.0f, mY + 65.0f, mouseX, mouseY)) {
                    if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                        module.toggle();
                        Core.fileManager.saveHacks();
                        this.previousmouse = true;
                    }
                    if (!this.previousmouse && Mouse.isButtonDown((int)1)) {
                        this.previousmouse = true;
                    }
                }
                if (!Mouse.isButtonDown((int)0)) {
                    this.previousmouse = false;
                }
                if (Mouse.isButtonDown((int)0)) {
                    // empty if block
                }
                if (this.isHovered(startX + 400.0f, startY + 260.0f, startX + 440.0f, startY + 280.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                    this.bind = true;
                }
                if (this.isSettingsButtonHovered(startX, mY + 45.0f, startX + 100.0f, mY + 70.0f, mouseX, mouseY) && Mouse.isButtonDown((int)1)) {
                    this.animate = true;
                    this.target = mY + 54.0f;
                    currentModule = module;
                    valueStart = 0;
                }
                mY += 20.0f;
            }
            ++i;
        }
        mY = startY + 12.0f;
        this.animate = true;
        if (true) {
            this.h = this.animationUtils.animate(this.target, this.h, 0.1f);
            RenderUtils.drawRoundRect(startX + 12.0f, this.h - 4.0f, startX + 14.0f, this.h + 10.0f, 1, Colors.getColor(245, 245, 245));
            if (this.h == this.target) {
                this.animate = false;
            }
        }
        if (currentModule != null && currentModule.getDescription() != null) {
            Wrapper.INSTANCE.mc().fontRendererObj.drawString(currentModule.getDescription(), (int)startX + 110, (int)mY + 275, new Color(245, 245, 245).getRGB());
        }
        if (currentModule != null && currentModule.getValues() != null) {
            for (i = 0; i < currentModule.getValues().size() && !(mY > startY + 220.0f); ++i) {
                float x;
                if (i < valueStart) continue;
                Value value2 = currentModule.getValues().get(i);
                if (value2 instanceof NumberValue) {
                    x = startX + 150.0f;
                    double render = 200.0 * ((Double)((NumberValue)value2).getValue() - ((NumberValue)value2).getMin()) / (((NumberValue)value2).getMax() - ((NumberValue)value2).getMin());
                    RenderUtils.drawRoundRect(x + 2.0f, mY + 65.0f, x + 202.0f, mY + 70.0f, 3, this.isButtonHovered(x + 2.0f, mY + 65.0f, x + 202.0f, mY + 70.0f, mouseX, mouseY) ? Colors.getColor(120, 120, 120) : Colors.getColor(81, 81, 84));
                    RenderUtils.drawRoundRect(x + 2.0f, mY + 65.0f, (double)x + render + 6.5, mY + 70.0f, 3, Colors.getColor(245, 245, 245));
                    RenderUtils.drawRoundRect((double)x + render + 2.0 + 1.0, mY + 61.0f, (double)x + render + 2.0 + 5.0, mY + 65.0f + 9.0f, 2, Colors.getColor(245, 245, 245));
                    GL11.glPushMatrix();
                    GL11.glEnable((int)3042);
                    font.drawString(value2.getName(), (int)startX + 130, (int)mY + 50, new Color(245, 245, 245).getRGB());
                    font.drawString("" + value2.getValue(), (int)startX + 140 - font.getStringWidth("" + value2.getValue()), (int)mY + 65, new Color(245, 245, 245).getRGB());
                    GL11.glDisable((int)3042);
                    GL11.glPopMatrix();
                    if (!Mouse.isButtonDown((int)0)) {
                        this.previousmouse = false;
                    }
                    if (this.isButtonHovered(x + 2.0f, mY + 65.0f, x + 202.0f, mY + 70.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                        if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                            render = ((NumberValue)value2).getMin();
                            double max = ((NumberValue)value2).getMax();
                            double inc = 0.01;
                            double valAbs = (double)mouseX - ((double)x + 1.0);
                            double perc = valAbs / 200.0;
                            perc = Math.min(Math.max(0.0, perc), 1.0);
                            double valRel = (max - render) * perc;
                            double val = render + valRel;
                            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                            value2.setValue(val);
                        }
                        if (!Mouse.isButtonDown((int)0)) {
                            this.previousmouse = false;
                        }
                    }
                    mY += 30.0f;
                }
                if (value2 instanceof BooleanValue) {
                    x = startX + 180.0f;
                    int xx = 30;
                    int x2x = 45;
                    GL11.glEnable((int)3042);
                    font.drawString(value2.getName(), (int)startX + 150, (int)mY + 50, new Color(245, 245, 245).getRGB());
                    GL11.glDisable((int)3042);
                    BooleanValue S = (BooleanValue)value2;
                    if (((Boolean)S.getValue()).booleanValue()) {
                        RenderUtils.drawRoundRect(startX + 130.0f, mY + 46.0f, startX + 130.0f + 14.0f, mY + 50.0f + 10.0f, 2, Colors.getColor(0, 0, 0));
                        RenderUtils.drawRoundRect(startX + 130.0f + 1.0f, mY + 46.0f + 1.0f, startX + 130.0f + 14.0f - 1.0f, mY + 50.0f + 10.0f - 1.0f, 2, Colors.getColor(200, 200, 200));
                    } else {
                        RenderUtils.drawRoundRect(startX + 130.0f, mY + 46.0f, startX + 130.0f + 14.0f, mY + 50.0f + 10.0f, 2, Colors.getColor(0, 0, 0));
                    }
                    if (this.isCheckBoxHovered(startX + 130.0f, mY + 46.0f, startX + 130.0f + 14.0f, mY + 50.0f + 10.0f, mouseX, mouseY)) {
                        RenderUtils.drawRect(startX + 130.0f + 1.0f, mY + 46.0f + 1.0f, startX + 130.0f + 14.0f - 1.0f, mY + 50.0f + 10.0f - 1.0f, Colors.getColor(60, 60, 60));
                        if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                            this.previousmouse = true;
                            this.mouse = true;
                        }
                        if (this.mouse) {
                            S.setValue((Boolean)S.getValue() == false);
                            this.mouse = false;
                        }
                    }
                    if (!Mouse.isButtonDown((int)0)) {
                        this.previousmouse = false;
                    }
                    mY += 20.0f;
                }
                if (!(value2 instanceof ModeValue)) continue;
                x = startX + 130.0f;
                GL11.glEnable((int)3042);
                font.drawStringWithShadow(value2.getName(), startX + 130.0f, mY + 52.0f, new Color(245, 245, 245).getRGB());
                GL11.glDisable((int)3042);
                RenderUtils.drawRoundRect(x, mY + 62.0f, x + 100.0f, mY + 76.0f, 2, Colors.getColor(80, 80, 80));
                ModeValue M = (ModeValue)value2;
                if (M.getValue() != null) {
                    GL11.glEnable((int)3042);
                    font.drawStringWithShadow(((Mode)M.getValue()).getName(), x + 18.0f - (float)(font.getStringWidth(((Mode)M.getValue()).getName()) / 2), mY + 66.0f, new Color(220, 220, 220).getRGB());
                    GL11.glDisable((int)3042);
                }
                if (this.isStringHovered(x, mY + 62.0f, x + 100.0f, mY + 76.0f, mouseX, mouseY)) {
                    if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                        this.previousmouse = true;
                        this.mouse = true;
                    }
                    if (this.mouse) {
                        int maxIndex = M.getModes().length;
                        ++this.modeIndex;
                        if (this.modeIndex + 1 > maxIndex) {
                            this.modeIndex = 0;
                        }
                        M.setValue(M.getModes()[this.modeIndex]);
                        this.mouse = false;
                    }
                    if (!Mouse.isButtonDown((int)0)) {
                        this.previousmouse = false;
                    }
                }
                mY += 30.0f;
            }
            if ((this.isHovered(startX, startY, startX + 100.0f, startY + 50.0f, mouseX, mouseY) || this.isHovered(startX, startY + 315.0f, startX + 450.0f, startY + 350.0f, mouseX, mouseY) || this.isHovered(startX + 430.0f, startY, startX + 450.0f, startY + 350.0f, mouseX, mouseY)) && Mouse.isButtonDown((int)0)) {
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
            int j = 59;
            int l = 40;
            float k = startY + 10.0f;
            float f = startX + 5.0f;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        ClickGui.shell_x = startX;
        ClickGui.shell_y = startY;
        ClickGui.shell_category = currentModuleType;
        ClickGui.shell_module = currentModule;
        Core.fileManager.saveClickGui();
        Core.fileManager.saveHacks();
        try {
            Mouse.setNativeCursor(null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
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

    public void keyTyped(char typedChar, int keyCode) {
        if (this.bind) {
            currentModule.setKey(keyCode);
            if (keyCode == 1) {
                currentModule.setKey(0);
            }
            this.bind = false;
        } else if (keyCode == 1) {
            mc.displayGuiScreen((GuiScreen)null);
            if (GuiClickShell.mc.currentScreen == null) {
                mc.setIngameFocus();
            }
            return;
        }
    }

    public static void setMod(Hack mod) {
        currentModule = mod;
    }

    public static void setX(float shell_x) {
        startX = shell_x;
    }

    public static void setY(float shell_y) {
        startY = shell_y;
    }

    public static void setCategory(HackCategory state) {
        currentModuleType = state;
    }

    static {
        startX = 100.0f;
        startY = 40.0f;
        moduleStart = 0;
        valueStart = 0;
        alphe = 121;
    }
}

