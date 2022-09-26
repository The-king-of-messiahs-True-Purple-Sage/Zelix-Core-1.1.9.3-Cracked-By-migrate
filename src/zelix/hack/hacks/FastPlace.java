/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.ReflectionHelper;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class FastPlace
extends Hack {
    BooleanValue onBlock = new BooleanValue("BlockOnly", false);
    NumberValue speed = new NumberValue("Speed", 0.0, 0.0, 4.0);

    public FastPlace() {
        super("FastPlace", HackCategory.PLAYER);
        this.addValue(this.onBlock, this.speed);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (((Boolean)this.onBlock.getValue()).booleanValue()) {
            if (Wrapper.INSTANCE.player().func_184586_b(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock || Wrapper.INSTANCE.player().func_184586_b(EnumHand.OFF_HAND).getItem() instanceof ItemBlock) {
                Field field = ReflectionHelper.findField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.setInt(Wrapper.INSTANCE.mc(), ((Double)this.speed.getValue()).intValue());
                }
                catch (Exception exception) {}
            }
        } else {
            Field field = ReflectionHelper.findField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.setInt(Wrapper.INSTANCE.mc(), ((Double)this.speed.getValue()).intValue());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

