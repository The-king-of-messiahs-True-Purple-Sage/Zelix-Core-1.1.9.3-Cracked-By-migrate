/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.Astolfo.ClickGui;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.Astolfo.ClickGui.Limitation;
import zelix.hack.HackCategory;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class ValueButton {
    public Value value;
    public String name;
    public boolean custom;
    public boolean change;
    public int x;
    public int y;
    public double opacity;
    public HackCategory category;
    public ModeValue priority;
    public ModeValue mode;
    public ModeValue rotations;
    int press = 0;

    public ValueButton(HackCategory category, Value value, int x, int y) {
        this.category = category;
        this.custom = false;
        this.opacity = 0.0;
        this.value = value;
        this.x = x;
        this.y = y;
        this.name = "";
        if (this.value instanceof BooleanValue) {
            this.change = (Boolean)((BooleanValue)this.value).getValue();
        } else if (this.value instanceof ModeValue) {
            this.name = "" + ((ModeValue)this.value).getValue();
        } else if (value instanceof NumberValue) {
            NumberValue v = (NumberValue)value;
            this.name = String.valueOf(this.name + (double)((Double)v.getValue()).intValue());
        }
        this.opacity = 0.0;
    }

    public void render(int mouseX, int mouseY, Limitation limitation) {
        if (!this.custom) {
            double d = mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.kiona18.getStringHeight(this.value.getName()) + 6 ? (this.opacity + 10.0 < 200.0 ? (this.opacity = this.opacity + 10.0) : 200.0) : (this.opacity = this.opacity - 6.0 > 0.0 ? (this.opacity = this.opacity - 6.0) : 0.0);
            if (this.value instanceof BooleanValue) {
                this.change = (Boolean)((BooleanValue)this.value).getValue();
            } else if (this.value instanceof ModeValue) {
                this.name = "" + ((ModeValue)this.value).getSelectMode().getName();
            } else if (this.value instanceof NumberValue) {
                NumberValue v = (NumberValue)this.value;
                this.name = "" + (double)((Number)v.getValue()).intValue();
                if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) - 10 && mouseY < this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) + 2 && Mouse.isButtonDown((int)0)) {
                    double min = v.getMin();
                    double max = v.getMax();
                    double inc = ((Number)v.getValue()).doubleValue();
                    double valAbs = (double)mouseX - ((double)this.x + 1.0);
                    double perc = valAbs / 68.0;
                    perc = Math.min(Math.max(0.0, perc), 1.0);
                    double valRel = (max - min) * perc;
                    double val = min + valRel;
                    val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                    v.setValue(val);
                }
            }
            int staticColor = this.category.name().equals("COMBAT") ? new Color(231, 76, 60).getRGB() : (this.category.name().equals("VISUAL") ? new Color(54, 1, 205).getRGB() : (this.category.name().equals("MOVEMENTF") ? new Color(45, 203, 113).getRGB() : (this.category.name().equals("PLAYER") ? new Color(141, 68, 173).getRGB() : (this.category.name().equals("ANOTHER") ? new Color(38, 154, 255).getRGB() : new Color(38, 154, 255).getRGB()))));
            GL11.glEnable((int)3089);
            limitation.cut();
            Gui.drawRect((int)(this.x - 10), (int)(this.y - 4), (int)(this.x + 80), (int)(this.y + 11), (int)new Color(39, 39, 39).getRGB());
            if (this.value instanceof BooleanValue) {
                FontLoaders.kiona14.drawString(this.value.getName(), this.x - 7, this.y + 2, (Boolean)((BooleanValue)this.value).getValue() != false ? new Color(255, 255, 255).getRGB() : new Color(108, 108, 108).getRGB());
            }
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            if (this.value instanceof ModeValue) {
                FontLoaders.kiona14.drawString(this.value.getName(), this.x - 7, this.y + 3, new Color(255, 255, 255).getRGB());
                FontLoaders.kiona14.drawString(this.name, this.x + 77 - FontLoaders.kiona14.getStringWidth(this.name), this.y + 3, new Color(182, 182, 182).getRGB());
            }
            if (this.value instanceof NumberValue) {
                NumberValue v = (NumberValue)this.value;
                double render = 82.0f * (((Number)v.getValue()).floatValue() - ((Number)v.getMin()).floatValue()) / (((Number)v.getMax()).floatValue() - ((Number)v.getMin()).floatValue());
                Gui.drawRect((int)(this.x - 8), (int)(this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) + 2), (int)(this.x + 78), (int)(this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) - 9), (int)new Color(50, 50, 50, 180).getRGB());
                Gui.drawRect((int)(this.x - 8), (int)(this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) + 2), (int)((int)((double)(this.x - 4) + render)), (int)(this.y + FontLoaders.kiona14.getStringHeight(this.value.getName()) - 9), (int)staticColor);
            }
            if (this.value instanceof NumberValue) {
                FontLoaders.kiona14.drawString(this.value.getName(), this.x - 7, this.y, new Color(255, 255, 255).getRGB());
                FontLoaders.kiona14.drawString(this.name, this.x + FontLoaders.kiona14.getStringWidth(this.value.getName()), this.y, -1);
            }
            GL11.glDisable((int)3089);
        }
    }

    public void key(char typedChar, int keyCode) {
    }

    private boolean isHovering(int n, int n2) {
        boolean b = n >= this.x && n <= this.x - 7 && n2 >= this.y && n2 <= this.y + FontLoaders.kiona18.getStringHeight(this.value.getName());
        return b;
    }

    public void click(int mouseX, int mouseY, int button) {
        if (!this.custom && mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.kiona18.getStringHeight(this.value.getName())) {
            if (this.value instanceof BooleanValue) {
                BooleanValue v = (BooleanValue)this.value;
                v = (BooleanValue)this.value;
                v.setValue((Boolean)v.getValue() == false);
                return;
            }
            if (this.value instanceof ModeValue) {
                ++this.press;
                ModeValue m = (ModeValue)this.value;
                ArrayList<Mode> modes = new ArrayList<Mode>();
                for (Mode mode2 : m.getModes()) {
                    modes.add(mode2);
                }
                String t1 = null;
                if (this.press <= modes.size()) {
                    ((Mode)modes.get(this.press - 1)).setToggled(true);
                    t1 = ((Mode)modes.get(this.press - 1)).getName();
                } else {
                    this.press = 0;
                }
                for (Mode mode3 : m.getModes()) {
                    if (mode3.getName() == t1) continue;
                    mode3.setToggled(false);
                }
            }
        }
    }

    private class mode {
        private mode() {
        }
    }
}

