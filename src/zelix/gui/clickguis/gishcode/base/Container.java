/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.base;

import java.util.ArrayList;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;

public class Container
extends Component {
    private ArrayList<Component> components = new ArrayList();

    public Container(int xPos, int yPos, int width, int height, ComponentType componentType, Component component2, String text) {
        super(xPos, yPos, width, height, componentType, component2, text);
    }

    public void addComponent(Component c) {
        this.components.add(c);
    }

    public void removeCompoent(Component c) {
        this.components.remove(c);
    }

    public void renderChildren(int mouseX, int mouseY) {
        for (Component c : this.getComponents()) {
            c.render(mouseX, mouseY);
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }
}

