/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.elements;

import java.util.ArrayList;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.listener.ComponentClickListener;
import zelix.hack.Hack;

public class Button
extends Component {
    public ArrayList<ComponentClickListener> listeners = new ArrayList();
    private Hack mod;
    private boolean enabled = false;

    public Button(int xPos, int yPos, int width, int height, Component component2, String text) {
        super(xPos, yPos, width, height, ComponentType.BUTTON, component2, text);
    }

    public Button(int xPos, int yPos, int width, int height, Component component2, String text, Hack mod) {
        super(xPos, yPos, width, height, ComponentType.BUTTON, component2, text);
        this.mod = mod;
    }

    public void addListeners(ComponentClickListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void onMousePress(int x, int y, int button) {
        if (button != 0) {
            return;
        }
        this.enabled = !this.enabled;
        for (ComponentClickListener listener : this.listeners) {
            listener.onComponenetClick(this, button);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<ComponentClickListener> getListeners() {
        return this.listeners;
    }

    public Hack getMod() {
        return this.mod;
    }
}

