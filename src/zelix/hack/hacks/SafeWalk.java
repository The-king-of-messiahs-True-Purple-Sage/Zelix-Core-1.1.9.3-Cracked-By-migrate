/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;

public class SafeWalk
extends Hack {
    BooleanValue sneak = new BooleanValue("Eagle", false);

    public SafeWalk() {
        super("SafeWalk", HackCategory.MOVEMENT);
        this.addValue(this.sneak);
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (this.getBlockUnderPlayer((EntityPlayer)player) instanceof BlockAir && player.onGround && player.func_70040_Z().field_72448_b < (double)-0.666f) {
            if (((Boolean)this.sneak.getValue()).booleanValue()) {
                KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mc().gameSettings.keyBindSneak.getKeyCode(), (boolean)true);
            } else {
                player.motionX = 0.0;
                player.motionZ = 0.0;
            }
        } else if (((Boolean)this.sneak.getValue()).booleanValue()) {
            KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mc().gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
        }
        super.onPlayerTick(event);
    }

    public Block getBlock(BlockPos pos) {
        return Wrapper.INSTANCE.world().func_180495_p(pos).getBlock();
    }

    public Block getBlockUnderPlayer(EntityPlayer player) {
        return this.getBlock(new BlockPos(player.posX, player.posY - 1.0, player.posZ));
    }
}

