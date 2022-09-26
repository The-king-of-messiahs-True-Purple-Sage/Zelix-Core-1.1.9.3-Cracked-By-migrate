/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class Suicide
extends Hack {
    public NumberValue damage = new NumberValue("Damage", 0.35, 0.0125, 0.5);

    public Suicide() {
        super("Suicide", HackCategory.COMBAT);
        this.addValue(this.damage);
    }

    @Override
    public String getDescription() {
        return "Kills you.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Wrapper.INSTANCE.player().isDead) {
            this.toggle();
        }
        Utils.selfDamage((Double)this.damage.getValue());
        super.onClientTick(event);
    }
}

