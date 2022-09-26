/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class Disconnect
extends Hack {
    public NumberValue leaveHealth = new NumberValue("LeaveHealth", 4.0, 0.0, 20.0);

    public Disconnect() {
        super("Disconnect", HackCategory.COMBAT);
        this.addValue(this.leaveHealth);
    }

    @Override
    public String getDescription() {
        return "Automatically leaves the server when your health is low.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Wrapper.INSTANCE.player().getHealth() <= ((Double)this.leaveHealth.getValue()).floatValue()) {
            boolean flag = Wrapper.INSTANCE.mc().isIntegratedServerRunning();
            Wrapper.INSTANCE.world().sendQuittingDisconnectingPacket();
            Wrapper.INSTANCE.mc().loadWorld((WorldClient)null);
            if (flag) {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMainMenu());
            } else {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
            }
            this.setToggled(false);
        }
        super.onClientTick(event);
    }
}

