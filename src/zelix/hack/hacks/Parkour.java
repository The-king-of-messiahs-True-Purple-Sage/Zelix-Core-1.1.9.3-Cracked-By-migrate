/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class Parkour
extends Hack {
    public Parkour() {
        super("Parkour", HackCategory.PLAYER);
    }

    @Override
    public String getDescription() {
        return "Jump when reaching a block's edge.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Utils.isBlockEdge((EntityLivingBase)Wrapper.INSTANCE.player()) && !Wrapper.INSTANCE.player().isSneaking()) {
            Wrapper.INSTANCE.player().jump();
        }
        super.onClientTick(event);
    }
}

