/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.lang.reflect.Field;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;

public final class FastPlaceHack
extends Hack {
    public FastPlaceHack() {
        super("FastPlace", "Allows you to place blocks 5 times faster.");
        this.setCategory(Category.BLOCKS);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        try {
            Field rightClickDelayTimer = mc.getClass().getDeclaredField(wurst.isObfuscated() ? "field_71467_ac" : "rightClickDelayTimer");
            rightClickDelayTimer.setAccessible(true);
            rightClickDelayTimer.setInt(mc, 0);
        }
        catch (ReflectiveOperationException e) {
            this.setEnabled(false);
            throw new RuntimeException(e);
        }
    }
}

