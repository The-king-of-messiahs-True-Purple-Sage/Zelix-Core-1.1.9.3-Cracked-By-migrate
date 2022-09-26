/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components.listeners;

import java.util.ArrayList;
import zelix.gui.clickguis.caesium.components.GuiComponent;

public abstract class ComponentListener {
    private final ArrayList<GuiComponent> components = new ArrayList();

    protected void add(GuiComponent component2) {
        this.components.add(component2);
    }

    public void clearComponents() {
        this.components.clear();
    }

    public ArrayList<GuiComponent> getComponents() {
        return this.components;
    }

    public abstract void addComponents();
}

