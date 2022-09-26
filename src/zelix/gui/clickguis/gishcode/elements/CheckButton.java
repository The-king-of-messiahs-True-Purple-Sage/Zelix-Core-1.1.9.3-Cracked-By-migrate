/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.elements;

import java.util.ArrayList;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.listener.CheckButtonClickListener;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class CheckButton
extends Component {
    public ArrayList<CheckButtonClickListener> listeners = new ArrayList();
    private boolean enabled = false;
    private ModeValue modeValue = null;

    public CheckButton(int xPos, int yPos, int width, int height, Component component2, String text, boolean enabled, ModeValue modeValue) {
        super(xPos, yPos, width, height, ComponentType.CHECK_BUTTON, component2, text);
        this.enabled = enabled;
        this.modeValue = modeValue;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (this.modeValue != null) {
            for (Mode mode2 : this.modeValue.getModes()) {
                mode2.setToggled(false);
            }
            this.enabled = true;
        } else {
            this.enabled = !this.enabled;
        }
        for (CheckButtonClickListener listener : this.listeners) {
            listener.onCheckButtonClick(this);
        }
    }

    public ArrayList<CheckButtonClickListener> getListeners() {
        return this.listeners;
    }

    public void addListeners(CheckButtonClickListener listener) {
        this.listeners.add(listener);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public ModeValue getModeValue() {
        return this.modeValue;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

