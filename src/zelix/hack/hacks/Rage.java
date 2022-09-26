/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class Rage
extends Hack {
    public TimerUtils timer = new TimerUtils();
    public NumberValue delay = new NumberValue("Delay", 0.0, 0.0, 1000.0);

    public Rage() {
        super("Rage", HackCategory.PLAYER);
        this.addValue(this.delay);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.timer.isDelay(((Double)this.delay.getValue()).longValue())) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Rotation((float)Utils.random(-160, 160), (float)Utils.random(-160, 160), true));
            this.timer.setLastMS();
        }
        super.onClientTick(event);
    }
}

