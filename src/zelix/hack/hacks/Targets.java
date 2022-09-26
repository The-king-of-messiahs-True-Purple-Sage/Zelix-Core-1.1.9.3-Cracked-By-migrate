/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.value.BooleanValue;

public class Targets
extends Hack {
    public BooleanValue players;
    public BooleanValue mobs;
    public BooleanValue invisibles;
    public BooleanValue murder;
    public BooleanValue sleeping;

    public Targets() {
        super("Targets", HackCategory.ANOTHER);
        this.setShow(false);
        this.setToggled(true);
        this.players = new BooleanValue("Players", true);
        this.mobs = new BooleanValue("Mobs", false);
        this.invisibles = new BooleanValue("Invisibles", false);
        this.murder = new BooleanValue("Murder", false);
        this.sleeping = new BooleanValue("Sleeping", false);
        this.addValue(this.players, this.mobs, this.invisibles, this.murder, this.sleeping);
    }

    @Override
    public String getDescription() {
        return "Manage targets for hacks.";
    }
}

