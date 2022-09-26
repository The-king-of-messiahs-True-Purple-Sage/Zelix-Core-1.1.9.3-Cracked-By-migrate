/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;

public class GhostMode
extends Hack {
    public static boolean enabled = false;

    public GhostMode() {
        super("GhostMode", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Disable all hacks.";
    }

    @Override
    public void onEnable() {
        if (this.getKey() == -1) {
            return;
        }
        enabled = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        enabled = false;
        super.onDisable();
    }
}

