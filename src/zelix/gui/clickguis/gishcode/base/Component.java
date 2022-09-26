/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.base;

import zelix.gui.clickguis.gishcode.ClickGui;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.base.Interactable;

public class Component
extends Interactable {
    private ComponentType componentType;
    private Component component;
    private String text;

    public Component(int xPos, int yPos, int width, int height, ComponentType componentType, Component component2, String text) {
        super(xPos, yPos, width, height);
        this.componentType = componentType;
        this.component = component2;
        this.text = text;
    }

    public void render(int x, int y) {
        ClickGui.getTheme().getRenderer().get((Object)this.componentType).drawComponent(this, x, y);
    }

    public void onUpdate() {
    }

    public ComponentType getComponentType() {
        return this.componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Component getComponent() {
        return this.component;
    }

    public void setComponent(Component component2) {
        this.component = component2;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

