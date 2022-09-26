/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.NumberValue;

public class HighJump
extends Hack {
    NumberValue High;
    Minecraft mc = Wrapper.INSTANCE.mc();

    public HighJump() {
        super("HighJump", HackCategory.MOVEMENT);
        this.High = new NumberValue("Height", 2.0, 1.1, 10.0);
        this.addValue(this.High);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (this.mc.thePlayer.hurtTime > 0 && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.motionY += (double)(0.42f * ((Double)this.High.value).floatValue());
            ChatUtils.message("[AutoDisable] HighJump");
            HackManager.getHack("HighJump").toggle();
        }
        super.onPlayerTick(e);
    }
}

