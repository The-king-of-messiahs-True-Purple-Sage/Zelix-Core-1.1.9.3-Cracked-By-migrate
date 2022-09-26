/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.utils.KeyBindingUtils;

public final class AutoWalkHack
extends Hack {
    public AutoWalkHack() {
        super("AutoWalk", "Makes you walk automatically.");
        this.setCategory(Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        KeyBindingUtils.resetPressed(AutoWalkHack.mc.gameSettings.keyBindForward);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        KeyBindingUtils.setPressed(AutoWalkHack.mc.gameSettings.keyBindForward, true);
    }
}

