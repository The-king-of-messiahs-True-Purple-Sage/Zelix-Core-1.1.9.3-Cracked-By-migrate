/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.gui.clickguis.gishcode.elements;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.base.Container;
import zelix.gui.clickguis.gishcode.listener.ComboBoxListener;

public class ComboBox
extends Container {
    public ArrayList<ComboBoxListener> listeners = new ArrayList();
    private String[] elements;
    private int selectedIndex;
    private boolean selected;

    public ComboBox(int xPos, int yPos, int width, int height, Component component2, String text, String ... elements) {
        super(xPos, yPos, width, height, ComponentType.COMBO_BOX, component2, text);
        this.elements = elements;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (this.isMouseOver(x, y)) {
            if (buttonID == 1) {
                boolean bl = this.selected = !this.selected;
            }
            if (buttonID == 0) {
                int offset = this.getDimension().height + 2;
                String[] elements = this.getElements();
                for (int i = 0; i < elements.length; ++i) {
                    if (i == this.getSelectedIndex()) continue;
                    if (y >= offset && y <= offset + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) {
                        this.setSelectedIndex(i);
                        this.setSelected(false);
                        break;
                    }
                    offset += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
                }
            }
        }
    }

    public String[] getElements() {
        return this.elements;
    }

    public void setElements(String[] elements) {
        this.elements = elements;
        this.selectedIndex = 0;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        for (ComboBoxListener listener : this.listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public String getSelectedElement() {
        return this.elements[this.selectedIndex];
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        for (ComboBoxListener listener : this.listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public ArrayList<ComboBoxListener> getListeners() {
        return this.listeners;
    }

    public void addListeners(ComboBoxListener listeners) {
        this.listeners.add(listeners);
    }
}

