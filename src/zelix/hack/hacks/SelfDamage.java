/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.value.NumberValue;

public class SelfDamage
extends Hack {
    public NumberValue damage = new NumberValue("Damage", 0.0625, 0.0125, 0.35);

    public SelfDamage() {
        super("SelfDamage", HackCategory.COMBAT);
        this.addValue(this.damage);
    }

    @Override
    public String getDescription() {
        return "Deals damage to you (useful for bypassing AC).";
    }

    @Override
    public void onEnable() {
        Utils.selfDamage((Double)this.damage.getValue());
        this.toggle();
        super.onEnable();
    }
}

