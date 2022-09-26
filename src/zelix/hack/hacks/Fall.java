/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockAir
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class Fall
extends Hack {
    Hack h = HackManager.getHack("Blink");
    TimerUtils timer = new TimerUtils();
    NumberValue height = new NumberValue("Height", 2.0, 1.0, 10.0);

    public Fall() {
        super("AntiFall", HackCategory.PLAYER);
        this.addValue(this.height);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        super.onClientTick(event);
    }

    @Override
    public void onDisable() {
        this.h.onDisable();
        this.h.setToggled(false);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        Utils.nullCheck();
        this.h.onEnable();
        this.h.setToggled(true);
        this.Jump();
        super.onEnable();
    }

    private Boolean isBlockBelow() {
        for (int i = (int)(Wrapper.INSTANCE.player().posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(Wrapper.INSTANCE.player().posX, (double)i, Wrapper.INSTANCE.player().posZ);
            if (Wrapper.INSTANCE.world().func_180495_p(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    void Jump() {
        Utils.nullCheck();
        Wrapper.INSTANCE.player().motionY = (Double)this.height.getValue();
    }
}

