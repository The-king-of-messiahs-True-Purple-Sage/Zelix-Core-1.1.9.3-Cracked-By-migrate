/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.Astolfo.ClickGui;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.Astolfo.ClickGui.Limitation;
import zelix.gui.clickguis.Astolfo.ClickGui.ValueButton;
import zelix.hack.HackCategory;

public class ColorValueButton
extends ValueButton {
    public HackCategory category;
    private float[] hue = new float[]{0.0f};
    private int position;
    private int color = new Color(125, 125, 125).getRGB();

    public ColorValueButton(HackCategory category, int x, int y) {
        super(category, null, x, y);
        this.category = category;
        this.custom = true;
        this.position = -1111;
    }

    @Override
    public void render(int mouseX, int mouseY, Limitation limitation) {
        float[] huee = new float[]{this.hue[0]};
        GL11.glEnable((int)3089);
        limitation.cut();
        Gui.drawRect((int)(this.x - 10), (int)(this.y - 4), (int)(this.x + 80), (int)(this.y + 11), (int)new Color(0, 0, 0, 100).getRGB());
        for (int i = this.x - 7; i < this.x + 79; ++i) {
            Color color = Color.getHSBColor(huee[0] / 255.0f, 0.7f, 1.0f);
            if (mouseX > i - 1 && mouseX < i + 1 && mouseY > this.y - 6 && mouseY < this.y + 12 && Mouse.isButtonDown((int)0)) {
                this.color = color.getRGB();
                this.position = i;
            }
            if (this.color == color.getRGB()) {
                this.position = i;
            }
            Gui.drawRect((int)(i - 1), (int)this.y, (int)i, (int)(this.y + 8), (int)color.getRGB());
            float[] arrf = huee;
            arrf[0] = arrf[0] + 4.0f;
            if (!(huee[0] > 255.0f)) continue;
            huee[0] = huee[0] - 255.0f;
        }
        Gui.drawRect((int)this.position, (int)this.y, (int)(this.position + 1), (int)(this.y + 8), (int)-1);
        if (this.hue[0] > 255.0f) {
            this.hue[0] = this.hue[0] - 255.0f;
        }
        GL11.glDisable((int)3089);
    }

    @Override
    public void key(char typedChar, int keyCode) {
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
    }
}

