/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Teams
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Base", false), new Mode("ArmorColor", false), new Mode("NameColor", true));

    public Teams() {
        super("Teams", HackCategory.ANOTHER);
        this.addValue(this.mode);
    }

    @Override
    public String getDescription() {
        return "Ignore if player in your team.";
    }
}

