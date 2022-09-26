/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;

public class Enemys
extends Hack {
    public Enemys() {
        super("Enemys", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Target only in enemy list.";
    }
}

