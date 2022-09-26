/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.Astolfo.ClickGui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.Core;
import zelix.gui.clickguis.Astolfo.ClickGui.Button;
import zelix.gui.clickguis.Astolfo.ClickGui.Limitation;
import zelix.gui.clickguis.Astolfo.ClickGui.ValueButton;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.hooks.visual.font.FontLoaders;

public class Window {
    public HackCategory category;
    public ArrayList<Button> buttons = Lists.newArrayList();
    public boolean drag;
    public boolean extended;
    public int x;
    public int y;
    public int expand;
    public int dragX;
    public int dragY;
    public int max;
    public int scroll;
    public int scrollTo;
    public double angel;
    int staticColor;
    public int totalY;
    int offset;

    public Window(HackCategory category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.max = 120;
        int y2 = y + 25;
        for (Hack c : Core.hackManager.getHacks()) {
            if (c.getCategory() != category) continue;
            this.buttons.add(new Button(category, c, x + 5, y2));
            y2 += 15;
        }
        for (Button b2 : this.buttons) {
            b2.setParent(this);
        }
    }

    public void render(int mouseX, int mouseY) {
        int current = 0;
        int iY = this.y + 20;
        this.totalY = 15;
        for (Button b3 : this.buttons) {
            b3.y = iY - this.offset;
            iY += 15;
            this.totalY += 15;
            if (b3.expand) {
                for (ValueButton v : b3.buttons) {
                    current += 15;
                    this.totalY += 15;
                }
            }
            current += 15;
        }
        int height = 15 + current;
        if (height > 316) {
            height = 316;
        }
        if (this.extended) {
            this.expand = height;
            this.angel = 180.0;
        } else {
            this.expand = 0;
            this.angel = 0.0;
        }
        boolean isOnPanel = mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + this.expand;
        boolean bl = isOnPanel;
        if (isOnPanel) {
            this.runWheel(height);
        }
        if (this.category.name().equals("COMBAT")) {
            this.staticColor = new Color(231, 76, 60).getRGB();
        } else if (this.category.name().equals("VISUAL")) {
            this.staticColor = new Color(54, 1, 205).getRGB();
        } else if (this.category.name().equals("MOVEMENT")) {
            this.staticColor = new Color(45, 203, 113).getRGB();
        } else if (this.category.name().equals("PLAYER")) {
            this.staticColor = new Color(141, 68, 173).getRGB();
        } else if (this.category.name().equals("ANOTHER")) {
            this.staticColor = new Color(38, 154, 255).getRGB();
        }
        if (this.expand > 0) {
            RenderUtil.rectangleBordered((double)this.x - 0.5, (double)this.y - 0.5, (double)this.x + 90.5, (double)this.y + 1.5 + (double)this.expand, 1.0, this.staticColor, this.staticColor);
            RenderUtil.rectangleBordered(this.x, this.y, this.x + 90, (double)this.y + 1.0 + (double)this.expand, 1.0, new Color(25, 25, 25).getRGB(), new Color(25, 25, 25).getRGB());
        }
        RenderUtil.drawGradientRect(this.x, this.y, this.x + 90, this.y + 17, new Color(25, 25, 25).getRGB(), new Color(25, 25, 25).getRGB());
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        if (this.category.name().equals("COMBAT")) {
            FontLoaders.kiona16.drawString("combat", this.x + 61, this.y + 6, new Color(220, 220, 220).getRGB());
        }
        if (this.category.name().equals("VISUAL")) {
            FontLoaders.kiona16.drawString("visual", this.x + 69, this.y + 6, new Color(220, 220, 220).getRGB());
        }
        if (this.category.name().equals("MOVEMENT")) {
            FontLoaders.kiona16.drawString("movement", this.x + 50, this.y + 6, new Color(220, 220, 220).getRGB());
        }
        if (this.category.name().equals("PLAYER")) {
            FontLoaders.kiona16.drawString("player", this.x + 67, this.y + 6, new Color(220, 220, 220).getRGB());
        }
        if (this.category.name().equals("ANOTHER")) {
            FontLoaders.kiona16.drawString("other", this.x + 69, this.y + 6, new Color(220, 220, 220).getRGB());
        }
        if (this.expand > 0) {
            for (Button b2 : this.buttons) {
                b2.render(mouseX, mouseY, new Limitation(this.x, this.y + 16, this.x + 90, this.y + this.expand));
            }
        }
        if (this.drag) {
            if (!Mouse.isButtonDown((int)0)) {
                this.drag = false;
            }
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
            this.buttons.get((int)0).y = this.y + 22 - this.offset;
            for (Button b4 : this.buttons) {
                b4.x = this.x + 5;
            }
        }
    }

    public static void doGlScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor((int)(x * scaleFactor), (int)(mc.displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
    }

    protected void runWheel(int height) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (this.totalY - height <= 0) {
                return;
            }
            if (wheel < 0) {
                if (this.offset < this.totalY - height) {
                    this.offset += 20;
                    if (this.offset < 0) {
                        this.offset = 0;
                    }
                }
            } else if (wheel > 0) {
                this.offset -= 20;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
    }

    public void key(char typedChar, int keyCode) {
        this.buttons.forEach(b2 -> b2.key(typedChar, keyCode));
    }

    public void mouseScroll(int mouseX, int mouseY, int amount) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17 + this.expand) {
            this.scrollTo = (int)((float)this.scrollTo - (float)(amount / 120 * 28));
        }
    }

    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17) {
            if (button == 1) {
                boolean bl = this.extended = !this.extended;
            }
            if (button == 0) {
                this.drag = true;
                this.dragX = mouseX - this.x;
                this.dragY = mouseY - this.y;
            }
        }
        if (this.extended) {
            this.buttons.stream().filter(b2 -> b2.y < this.y + this.expand).forEach(b2 -> b2.click(mouseX, mouseY, button));
        }
    }
}

