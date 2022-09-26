/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class ElytraFly2b2t
extends Hack {
    private double hoverTarget = -1.0;
    private boolean hoverState = false;
    BooleanValue hover = new BooleanValue("Hover", true);
    BooleanValue AutoOpen = new BooleanValue("AutoOpen", true);
    NumberValue speed = new NumberValue("Speed", 1.8, 0.0, 10.0);
    NumberValue downSpeed = new NumberValue("Down Speed", 2.0, 0.0, 10.0);
    NumberValue sinkSpeed = new NumberValue("Sink Speed", 0.001, 0.0, 10.0);

    public ElytraFly2b2t() {
        super("ElytraFly", HackCategory.MOVEMENT);
        this.addValue(this.hover, this.AutoOpen, this.sinkSpeed, this.speed, this.downSpeed);
    }
}

