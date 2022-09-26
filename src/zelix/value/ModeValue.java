/*
 * Decompiled with CFR 0.152.
 */
package zelix.value;

import zelix.value.Mode;
import zelix.value.Value;

public class ModeValue
extends Value<Mode> {
    private Mode[] modes;
    private String modeName;

    public Mode[] getModes() {
        return this.modes;
    }

    public ModeValue(String modeName, Mode ... modes) {
        super(modeName, null);
        this.modeName = modeName;
        this.modes = modes;
    }

    public ModeValue(String modeName, String[] modes) {
        super(modeName, null);
        this.modeName = modeName;
        for (int i = 0; i < modes.length; ++i) {
            this.modes[i] = i == 0 ? new Mode(modes[i], true) : new Mode(modes[i], false);
        }
    }

    public Mode getMode(String name) {
        Mode m = null;
        for (Mode mode2 : this.modes) {
            if (!mode2.getName().equals(name)) continue;
            m = mode2;
        }
        return m;
    }

    public Mode getSelectMode() {
        Mode m = null;
        for (Mode mode2 : this.modes) {
            if (!mode2.isToggled()) continue;
            m = mode2;
        }
        return m;
    }

    public String getModeName() {
        return this.modeName;
    }
}

