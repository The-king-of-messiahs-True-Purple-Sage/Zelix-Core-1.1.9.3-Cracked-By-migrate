/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Cursor
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.N3ro;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import zelix.Core;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.gui.clickguis.N3ro.Utils.TranslateUtil;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.utils.hooks.visual.font.CFontRenderer;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class GuiClickUI
extends GuiScreen {
    private static List<Hack> inSetting = new CopyOnWriteArrayList<Hack>();
    private static HackCategory currentCategory;
    private static int x;
    private static int y;
    private static int wheel;
    private boolean need2move = false;
    private int dragX = 0;
    private int dragY = 0;
    private TranslateUtil translate = new TranslateUtil(0.0f, 0.0f);
    CFontRenderer font1 = FontLoaders.default18;
    CFontRenderer font2 = FontLoaders.default16;
    CFontRenderer font3 = FontLoaders.default14;
    int press = 0;
    Cursor emptyCursor;

    public GuiClickUI() {
        this.translate.setX(0.0f);
        this.translate.setY(0.0f);
    }

    public void initGui() {
        super.initGui();
        if (x > this.width) {
            x = 30;
        }
        if (y > this.height) {
            y = 30;
        }
        this.need2move = false;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.need2move) {
            x = mouseX - this.dragX;
            y = mouseY - this.dragY;
        }
        if (!Mouse.isButtonDown((int)0) && this.need2move) {
            this.need2move = false;
        }
        String X = "Z";
        String F = "elix";
        RenderUtil.drawBorderedRect(x, y, x + 273, y + 198, 3.0f, new Color(20, 20, 20).getRGB(), GuiClickUI.getColor());
        RenderUtil.drawBorderedRect(x + 2, y + 2, x + 273 - 2, y + 198 - 2, 1.0f, GuiClickUI.getColor(), new Color(20, 20, 20).getRGB());
        Gui.drawRect((int)(x + 70), (int)(y + 35), (int)(x + 269), (int)(y + 195), (int)new Color(0, 0, 0).getRGB());
        FontLoaders.default30.drawStringWithShadow(X + F, x + 10, y + 8, new Color(180, 180, 180).getRGB());
        this.font2.drawStringWithShadow("1.1.6", x + 12, y + 24, new Color(180, 180, 180).getRGB());
        RenderUtil.drawGradientSideways(x + 70, y + 35, x + 80, y + 195, new Color(20, 20, 20).getRGB(), new Color(0, 0, 0, 0).getRGB());
        int cateY = 0;
        for (HackCategory category : HackCategory.values()) {
            int strX = x + 40;
            int strY = y + 55 + cateY;
            boolean hover = mouseX > x + 5 && mouseX < x + 65 && mouseY > strY && mouseY < strY + 20;
            FontLoaders.default20.drawCenteredStringWithShadow(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase(), strX - 1, strY + 6, category == currentCategory ? GuiClickUI.getColor() : new Color(hover ? 255 : 140, hover ? 255 : 140, hover ? 255 : 140).getRGB());
            cateY += 20;
        }
        int startX = x + 80 + 2;
        int startY = y + 9 + 2 + 28;
        int length = 185;
        float moduleY = this.translate.getY();
        RenderUtil.startGlScissor(startX, startY + 14, length, 140);
        for (Hack m : Core.hackManager.getHacks()) {
            if (m.getCategory() != currentCategory) continue;
            RenderUtil.drawRoundRect(startX, (float)startY + moduleY, startX + length, (float)startY + moduleY + 24.0f, 3, new Color(20, 20, 20).getRGB());
            this.font1.drawStringWithShadow(m.getName(), startX + 8, (float)(startY + 9) + moduleY, -1);
            RenderUtil.drawRoundRect(startX + length - 25, (float)startY + moduleY + 7.0f, startX + length - 5, (float)startY + moduleY + 17.0f, 5, new Color(0, 0, 0).getRGB());
            boolean onToggleButton = mouseX > startX + length - 25 && mouseX < startX + length - 5 && (float)mouseY > (float)startY + moduleY + 7.0f && (float)mouseY < (float)startY + moduleY + 17.0f;
            int left = m.isToggled() ? startX + length - 14 : startX + length - 24;
            int right = m.isToggled() ? startX + length - 6 : startX + length - 16;
            RenderUtil.drawRoundRect(left, (float)startY + moduleY + 8.0f, right, (float)startY + moduleY + 16.0f, 4, GuiClickUI.getColor());
            RenderUtil.drawRoundRect(startX + length - 24, (float)startY + moduleY + 8.0f, startX + length - 16, (float)startY + moduleY + 16.0f, 4, new Color(0, 0, 0, 150).getRGB());
            if (onToggleButton) {
                RenderUtil.drawRoundRect(startX + length - 25, (float)startY + moduleY + 7.0f, startX + length - 5, (float)startY + moduleY + 17.0f, 5, new Color(0, 0, 0, 100).getRGB());
            }
            boolean showSetting = inSetting.contains(m);
            int valueSizeY = m.getValues().size() * 20 + 5;
            float valueY = moduleY + 35.0f;
            if (showSetting) {
                RenderUtil.drawRect(startX + 3, (float)startY + moduleY + 24.0f, startX + length - 3, (float)startY + moduleY + 30.0f, new Color(30, 30, 30).getRGB());
                RenderUtil.drawRoundRect(startX + 3, (float)startY + moduleY + 24.0f, startX + length - 3, (float)startY + moduleY + 24.0f + (float)valueSizeY, 3, new Color(30, 30, 30).getRGB());
                for (Value setting : m.getValues()) {
                    Value s;
                    if (setting instanceof ModeValue) {
                        boolean hover;
                        s = (ModeValue)setting;
                        this.font2.drawStringWithShadow(s.getName(), startX + 10, (float)startY + valueY - 1.0f, -1);
                        RenderUtil.drawRoundRect(startX + length - 85, (float)startY + valueY - 4.0f, startX + length - 6, (float)startY + valueY + 8.0f, 3, new Color(10, 10, 10).getRGB());
                        int longValue = (startX + length - 6 - (startX + length - 85)) / 2;
                        if (((ModeValue)s).getSelectMode() != null && ((ModeValue)s).getSelectMode().getName() != null) {
                            this.font2.drawCenteredStringWithShadow(((ModeValue)s).getSelectMode().getName(), startX + length - 6 - longValue, (float)startY + valueY - 0.5f, GuiClickUI.getColor());
                        }
                        boolean bl = hover = mouseX > startX + length - 85 && mouseX < startX + length - 6 && (float)mouseY > (float)startY + valueY - 4.0f && (float)mouseY < (float)startY + valueY + 8.0f;
                        if (hover) {
                            RenderUtil.drawRoundRect(startX + length - 85, (float)startY + valueY - 4.0f, startX + length - 6, (float)startY + valueY + 8.0f, 3, new Color(0, 0, 0, 100).getRGB());
                        }
                    }
                    if (setting instanceof NumberValue) {
                        boolean hover;
                        s = (NumberValue)setting;
                        this.font2.drawStringWithShadow(s.getName(), startX + 10, (float)startY + valueY - 3.0f, -1);
                        double max = ((NumberValue)s).getMax();
                        double min = ((NumberValue)s).getMin();
                        double valn = (Double)s.getValue();
                        int longValue = startX + length - 6 - (startX + length - 83);
                        this.font3.drawStringWithShadow((Double)s.getValue() + "", startX + length - 84, (float)startY + valueY - 2.0f, -1);
                        RenderUtil.drawRoundRect(startX + length - 85, (float)startY + valueY + 5.0f, startX + length - 6, (float)startY + valueY + 7.0f, 1, new Color(10, 10, 10).getRGB());
                        RenderUtil.drawRoundRect(startX + length - 85, (float)startY + valueY + 5.0f, (double)(startX + length - 85) + (double)longValue * (valn - min) / (max - min) + 2.0, (float)startY + valueY + 7.0f, 1, GuiClickUI.getColor());
                        boolean bl = hover = mouseX > startX + length - 88 && mouseX < startX + length - 3 && (float)mouseY > (float)startY + valueY + 2.0f && (float)mouseY < (float)startY + valueY + 11.0f;
                        if (hover) {
                            RenderUtil.drawRoundRect(startX + length - 85, (float)startY + valueY + 5.0f, startX + length - 6, (float)startY + valueY + 7.0f, 1, new Color(0, 0, 0, 100).getRGB());
                            if (Mouse.isButtonDown((int)0)) {
                                double inc = 0.01;
                                double valAbs = mouseX - (startX + length - 85);
                                double perc = valAbs / ((double)longValue * Math.max(Math.min(valn / max, 0.0), 1.0));
                                perc = Math.min(Math.max(0.0, perc), 1.0);
                                double valRel = (max - min) * perc;
                                double val = min + valRel;
                                val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                                s.setValue(val);
                            }
                        }
                    }
                    if (setting instanceof BooleanValue) {
                        s = (BooleanValue)setting;
                        this.font2.drawStringWithShadow(s.getName(), startX + 10, (float)startY + valueY - 3.0f, -1);
                        boolean hover = mouseX > startX + length - 18 && mouseX < startX + length - 6 && (float)mouseY > (float)startY + valueY - 4.0f && (float)mouseY < (float)startY + valueY + 8.0f;
                        RenderUtil.drawRoundRect(startX + length - 18, (float)startY + valueY - 4.0f, startX + length - 6, (float)startY + valueY + 8.0f, 2, new Color(10, 10, 10).getRGB());
                        if (((Boolean)s.getValue()).booleanValue()) {
                            RenderUtil.drawRoundRect(startX + length - 17, (float)startY + valueY - 3.0f, startX + length - 7, (float)startY + valueY + 7.0f, 2, GuiClickUI.getColor());
                        }
                        if (hover) {
                            RenderUtil.drawRoundRect(startX + length - 18, (float)startY + valueY - 4.0f, startX + length - 6, (float)startY + valueY + 8.0f, 2, new Color(0, 0, 0, 100).getRGB());
                        }
                    }
                    valueY += 20.0f;
                }
            }
            moduleY += (float)(showSetting ? 26 + valueSizeY : 26);
        }
        RenderUtil.stopGlScissor();
        RenderUtil.drawGradientSidewaysV(x + 3, y + 35, x + 273 - 3, y + 45, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        int real = Mouse.getDWheel();
        float moduleHeight = moduleY - this.translate.getY();
        if (Mouse.hasWheel() && mouseX > startX && mouseY > startY && mouseX < startX + 270 && mouseY < startY + 237) {
            int i;
            if (real > 0 && wheel < 0) {
                for (i = 0; i < 5 && wheel < 0; wheel += 5, ++i) {
                }
            } else {
                for (i = 0; i < 5 && real < 0 && moduleHeight > 158.0f && (float)Math.abs(wheel) < moduleHeight - 154.0f; ++i) {
                    wheel -= 5;
                }
            }
        }
        this.translate.interpolate(0.0f, wheel, 0.15f);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean hover2top;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean bl = hover2top = mouseX > x && mouseX < x + 273 && mouseY > y && mouseY < y + 35;
        if (hover2top && mouseButton == 0) {
            this.dragX = mouseX - x;
            this.dragY = mouseY - y;
            this.need2move = true;
        } else {
            int cateY = 0;
            for (HackCategory category : HackCategory.values()) {
                boolean hover;
                int strX = x + 40;
                int strY = y + 55 + cateY;
                boolean bl2 = hover = mouseX > x + 5 && mouseX < x + 65 && mouseY > strY && mouseY < strY + 20;
                if (hover && mouseButton == 0) {
                    currentCategory = category;
                    wheel = 0;
                    this.translate.setY(0.0f);
                    break;
                }
                cateY += 20;
            }
            int startX = x + 80 + 2;
            int startY = y + 9 + 2 + 25;
            int length = 185;
            float moduleY = this.translate.getY();
            for (Hack m : Core.hackManager.getHacks()) {
                boolean onModuleRect;
                if (m.getCategory() != currentCategory) continue;
                boolean onToggleButton = mouseX > startX + length - 25 && mouseX < startX + length - 5 && (float)mouseY > (float)startY + moduleY + 7.0f && (float)mouseY < (float)startY + moduleY + 20.0f && mouseY < startY + 14 + 140 && mouseY > startY;
                boolean bl3 = onModuleRect = mouseX > startX && mouseX < startX + length && (float)mouseY > (float)startY + moduleY && (float)mouseY < (float)startY + moduleY + 28.0f && mouseY < startY + 14 + 140 && mouseY > startY;
                if (onToggleButton && mouseButton == 0) {
                    m.setToggled(!m.isToggled());
                }
                if (onModuleRect && mouseButton == 1) {
                    if (inSetting.contains(m)) {
                        inSetting.remove(m);
                    } else if (!m.getValues().isEmpty()) {
                        inSetting.add(m);
                    }
                }
                boolean showSetting = inSetting.contains(m);
                int valueSizeY = m.getValues().size() * 20 + 5;
                float valueY = moduleY + 35.0f;
                if (showSetting) {
                    RenderUtil.drawRect(startX + 3, (float)startY + moduleY + 24.0f, startX + length - 3, (float)startY + moduleY + 24.0f + (float)valueSizeY, new Color(30, 30, 30).getRGB());
                    for (Value setting : m.getValues()) {
                        boolean hover;
                        Value s;
                        if (setting instanceof ModeValue) {
                            s = (ModeValue)setting;
                            boolean bl4 = hover = mouseX > startX + length - 85 && mouseX < startX + length - 6 && (float)mouseY > (float)startY + valueY - 4.0f && (float)mouseY < (float)startY + valueY + 11.0f && mouseY < startY + 14 + 140 && mouseY > startY;
                            if (hover) {
                                String t1;
                                ArrayList<Mode> modes;
                                Value modeValue;
                                if (mouseButton == 1) {
                                    ++this.press;
                                    modeValue = s;
                                    modes = new ArrayList<Mode>();
                                    t1 = null;
                                    for (Mode mode2 : ((ModeValue)modeValue).getModes()) {
                                        modes.add(mode2);
                                    }
                                    if (this.press <= modes.size()) {
                                        ((Mode)modes.get(this.press - 1)).setToggled(true);
                                        t1 = ((Mode)modes.get(this.press - 1)).getName();
                                    } else {
                                        this.press = 0;
                                    }
                                    for (Mode mode2 : ((ModeValue)modeValue).getModes()) {
                                        if (mode2.getName() == t1) continue;
                                        mode2.setToggled(false);
                                    }
                                }
                                if (mouseButton == 0) {
                                    ++this.press;
                                    modeValue = s;
                                    modes = new ArrayList();
                                    t1 = null;
                                    for (Mode mode2 : ((ModeValue)modeValue).getModes()) {
                                        modes.add(mode2);
                                    }
                                    if (this.press <= modes.size()) {
                                        ((Mode)modes.get(this.press - 1)).setToggled(true);
                                        t1 = ((Mode)modes.get(this.press - 1)).getName();
                                    } else {
                                        this.press = 0;
                                    }
                                    for (Mode mode2 : ((ModeValue)modeValue).getModes()) {
                                        if (mode2.getName() == t1) continue;
                                        mode2.setToggled(false);
                                    }
                                }
                            }
                        }
                        if (setting instanceof BooleanValue) {
                            s = (BooleanValue)setting;
                            boolean bl5 = hover = mouseX > startX + length - 18 && mouseX < startX + length - 6 && (float)mouseY > (float)startY + valueY - 4.0f && (float)mouseY < (float)startY + valueY + 11.0f && mouseY < startY + 14 + 140 && mouseY > startY;
                            if (hover && (mouseButton == 0 || mouseButton == 2)) {
                                s.setValue((Boolean)s.getValue() == false);
                            }
                        }
                        valueY += 20.0f;
                    }
                }
                moduleY += (float)(showSetting ? 26 + valueSizeY : 26);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean hover2top;
        super.mouseReleased(mouseX, mouseY, state);
        int startY = y + 9 + 2 + 28;
        boolean bl = hover2top = mouseX > x + 1 && mouseX < x + 349 && mouseY > y + 1 && mouseY < y + 9 && mouseY < startY + 14 + 140 && mouseY > startY;
        if (hover2top && state == 0) {
            this.dragX = mouseX - x;
            this.dragY = mouseY - y;
            this.need2move = false;
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        ClickGui.memoriseX = x;
        ClickGui.memoriseY = y;
        ClickGui.memoriseWheel = wheel;
        ClickGui.memoriseML = inSetting;
        ClickGui.memoriseCatecory = currentCategory;
        try {
            Mouse.setNativeCursor(null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void setInSetting(List<Hack> moduleList) {
        inSetting = moduleList;
    }

    public static void setWheel(int state) {
        wheel = state;
    }

    public static void setX(int state) {
        x = state;
    }

    public static void setY(int state) {
        y = state;
    }

    public static void setCategory(HackCategory state) {
        currentCategory = state;
    }

    public static int getColor() {
        return new Color(((Double)ClickGui.red.getValue()).intValue(), ((Double)ClickGui.green.getValue()).intValue(), ((Double)ClickGui.blue.getValue()).intValue(), 255).getRGB();
    }
}

