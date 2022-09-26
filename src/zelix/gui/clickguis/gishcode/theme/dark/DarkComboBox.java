/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.ComboBox;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.utils.hooks.visual.GLUtils;

public class DarkComboBox
extends ComponentRenderer {
    public DarkComboBox(Theme theme) {
        super(ComponentType.COMBO_BOX, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        ComboBox comboBox = (ComboBox)component2;
        Dimension area = comboBox.getDimension();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glTranslated((double)(1 * comboBox.getX()), (double)(1 * comboBox.getY()), (double)0.0);
        int maxWidth = 0;
        for (String element : comboBox.getElements()) {
            maxWidth = Math.max(maxWidth, this.theme.getFontRenderer().getStringWidth(element));
        }
        int extendedHeight = 0;
        if (comboBox.isSelected()) {
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length - 1; ++i) {
                extendedHeight += this.theme.getFontRenderer().FONT_HEIGHT + 2;
            }
            extendedHeight += 2;
        }
        comboBox.setDimension(new Dimension(maxWidth + 8 + this.theme.getFontRenderer().FONT_HEIGHT, this.theme.getFontRenderer().FONT_HEIGHT));
        GLUtils.glColor(new Color(2, 2, 2, 40));
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)area.width, (double)0.0);
        GL11.glVertex2d((double)area.width, (double)(area.height + extendedHeight));
        GL11.glVertex2d((double)0.0, (double)(area.height + extendedHeight));
        GL11.glEnd();
        Point mouse = new Point(mouseX, mouseY);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)(Mouse.isButtonDown((int)0) ? 0.5f : 0.3f));
        if (GLUtils.isHovered(comboBox.getX(), comboBox.getY(), area.width, area.height, mouseX, mouseY)) {
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)0.0, (double)0.0);
            GL11.glVertex2d((double)area.width, (double)0.0);
            GL11.glVertex2d((double)area.width, (double)area.height);
            GL11.glVertex2d((double)0.0, (double)area.height);
            GL11.glEnd();
        } else if (comboBox.isSelected() && mouse.x >= comboBox.getX() && mouse.x <= comboBox.getX() + area.width) {
            int offset = area.height;
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length; ++i) {
                if (i == comboBox.getSelectedIndex()) continue;
                int height = this.theme.getFontRenderer().FONT_HEIGHT + 2;
                if ((comboBox.getSelectedIndex() != 0 ? i == 0 : i == 1) || (comboBox.getSelectedIndex() == elements.length - 1 ? i == elements.length - 2 : i == elements.length - 1)) {
                    ++height;
                }
                if (mouse.y >= comboBox.getY() + offset && mouse.y <= comboBox.getY() + offset + height) {
                    GL11.glBegin((int)7);
                    GL11.glVertex2d((double)0.0, (double)offset);
                    GL11.glVertex2d((double)0.0, (double)(offset + height));
                    GL11.glVertex2d((double)area.width, (double)(offset + height));
                    GL11.glVertex2d((double)area.width, (double)offset);
                    GL11.glEnd();
                    break;
                }
                offset += height;
            }
        }
        int height = this.theme.getFontRenderer().FONT_HEIGHT + 4;
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.3f);
        GL11.glBegin((int)4);
        if (comboBox.isSelected()) {
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 2.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 3.0), (double)(2.0 * (double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + 2.0 * (double)height / 3.0), (double)(2.0 * (double)height / 3.0));
        } else {
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 3.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + 2.0 * (double)height / 3.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 2.0), (double)(2.0 * (double)height / 3.0));
        }
        GL11.glEnd();
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        if (comboBox.isSelected()) {
            GL11.glBegin((int)1);
            GL11.glVertex2d((double)2.0, (double)area.height);
            GL11.glVertex2d((double)(area.width - 2), (double)area.height);
            GL11.glEnd();
        }
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)(maxWidth + 4), (double)2.0);
        GL11.glVertex2d((double)(maxWidth + 4), (double)(area.height - 2));
        GL11.glEnd();
        GL11.glBegin((int)2);
        if (comboBox.isSelected()) {
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 2.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 3.0), (double)(2.0 * (double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + 2.0 * (double)height / 3.0), (double)(2.0 * (double)height / 3.0));
        } else {
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 3.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + 2.0 * (double)height / 3.0), (double)((double)height / 3.0));
            GL11.glVertex2d((double)((double)(maxWidth + 4) + (double)height / 2.0), (double)(2.0 * (double)height / 3.0));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        String text = comboBox.getSelectedElement();
        this.theme.getFontRenderer().drawString(text, 2, area.height / 2 - this.theme.getFontRenderer().FONT_HEIGHT / 4, -1);
        if (comboBox.isSelected()) {
            int offset = area.height + 2;
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length; ++i) {
                if (i == comboBox.getSelectedIndex()) continue;
                this.theme.getFontRenderer().drawString(elements[i], 2, offset, -1);
                offset += this.theme.getFontRenderer().FONT_HEIGHT + 2;
            }
        }
        GL11.glEnable((int)2884);
        GL11.glDisable((int)3042);
        GL11.glTranslated((double)(-1 * comboBox.getX()), (double)(-1 * comboBox.getY()), (double)0.0);
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
        ComboBox comboBox = (ComboBox)component2;
    }
}

