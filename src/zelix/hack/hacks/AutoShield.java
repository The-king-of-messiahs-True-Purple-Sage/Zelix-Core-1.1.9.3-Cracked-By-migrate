/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.KillAura;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;

public class AutoShield
extends Hack {
    BooleanValue INTERACT = new BooleanValue("INTERACT", false);

    public AutoShield() {
        super("AutoShield", HackCategory.COMBAT);
        this.addValue(this.INTERACT);
    }

    @Override
    public String getDescription() {
        return "Manages your shield automatically.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Utils.screenCheck()) {
            this.blockByShield(false);
        }
        super.onClientTick(event);
    }

    @Override
    public void onDisable() {
        this.blockByShield(false);
        super.onDisable();
    }

    public void blockByShield(boolean state) {
        if (!(Wrapper.INSTANCE.player().func_184614_ca().getItem() instanceof ItemSword)) {
            return;
        }
        if (((Boolean)this.INTERACT.getValue()).booleanValue() && KillAura.getTarget() != null) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)KillAura.getTarget(), EnumHand.MAIN_HAND, new Vec3d((double)AutoShield.randomNumber(-50, 50) / 100.0, (double)AutoShield.randomNumber(0, 200) / 100.0, (double)AutoShield.randomNumber(-50, 50) / 100.0)));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketUseEntity((Entity)KillAura.getTarget(), EnumHand.MAIN_HAND));
        }
        if (KillAura.getTarget() != null) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            Wrapper.INSTANCE.player().func_184598_c(EnumHand.OFF_HAND);
        } else {
            KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mc().gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            Wrapper.INSTANCE.controller().onStoppedUsingItem((EntityPlayer)Wrapper.INSTANCE.player());
        }
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mc().gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
    }

    public static void block(boolean state) {
        AutoShield hack = (AutoShield)HackManager.getHack("AutoShield");
        if (hack.isToggled()) {
            hack.blockByShield(state);
        }
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }
}

