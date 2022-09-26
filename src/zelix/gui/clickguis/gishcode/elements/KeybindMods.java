/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package zelix.gui.clickguis.gishcode.elements;

import org.lwjgl.input.Keyboard;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.hack.Hack;
import zelix.utils.Wrapper;

public class KeybindMods
extends Component {
    private Hack mod;
    private boolean editing;

    public KeybindMods(int xPos, int yPos, int width, int height, Component component2, Hack mod) {
        super(xPos, yPos, width, height, ComponentType.KEYBIND, component2, "");
        this.mod = mod;
    }

    @Override
    public void onUpdate() {
        if (Keyboard.getEventKeyState() && this.editing) {
            if (Keyboard.getEventKey() == 211) {
                this.mod.setKey(-1);
            } else {
                this.mod.setKey(Keyboard.getEventKey());
            }
            this.editing = false;
        }
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (x > this.getX() + Wrapper.INSTANCE.fontRenderer().getStringWidth("Key") + 6 && x < this.getX() + this.getDimension().width && y > this.getY() && y < this.getY() + this.getDimension().height) {
            this.editing = !this.editing;
        }
    }

    public Hack getMod() {
        return this.mod;
    }

    public boolean isEditing() {
        return this.editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}

