/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;

public class AntiWeb
extends Hack {
    public AntiWeb() {
        super("AntiWeb", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Does not change walking speed in web.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        try {
            Field isInWeb = Entity.class.getDeclaredField(Mapping.isInWeb);
            isInWeb.setAccessible(true);
            isInWeb.setBoolean(Wrapper.INSTANCE.player(), false);
        }
        catch (Exception ex) {
            this.setToggled(false);
        }
        super.onClientTick(event);
    }
}

