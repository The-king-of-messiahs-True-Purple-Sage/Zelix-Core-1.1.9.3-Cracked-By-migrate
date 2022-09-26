/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.value.NumberValue;

public class AttackSpeed
extends Hack {
    NumberValue speed = new NumberValue("Ticks", 1.0, 0.0, 5.0);

    public AttackSpeed() {
        super("AttackSpeed", HackCategory.ANOTHER);
        this.addValue(this.speed);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft.getMinecraft().thePlayer.getEntityAttribute(SharedMonsterAttributes.field_188790_f).setBaseValue(((Double)this.speed.getValue()).doubleValue());
    }
}

