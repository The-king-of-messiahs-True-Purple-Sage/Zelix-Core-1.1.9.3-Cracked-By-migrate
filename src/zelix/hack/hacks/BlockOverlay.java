/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;

public class BlockOverlay
extends Hack {
    public BlockOverlay() {
        super("BlockOverlay", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Show of selected block.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.mc().objectMouseOver == null) {
            return;
        }
        if (Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a == RayTraceResult.Type.BLOCK) {
            Block block = BlockUtils.getBlock(Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a());
            BlockPos blockPos = Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a();
            if (Block.getIdFromBlock((Block)block) == 0) {
                return;
            }
            RenderUtils.drawBlockESP(blockPos, 1.0f, 1.0f, 1.0f);
        }
        super.onRenderWorldLast(event);
    }
}

