/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.color.BlockColors
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer
 */
package zelix.otherhacks.net.wurstclient.forge.compatibility;

import java.util.Collections;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.hacks.XRayHack;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;

public class WForgeBlockModelRenderer
extends ForgeBlockModelRenderer {
    public WForgeBlockModelRenderer(BlockColors colors) {
        super(colors);
    }

    public boolean func_178267_a(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides) {
        if (ForgeWurst.getForgeWurst().getHax().xRayHack.isEnabled()) {
            if (!this.isVisible(blockStateIn.getBlock())) {
                return false;
            }
            blockStateIn.getBlock().setLightLevel(100.0f);
            checkSides = false;
        }
        return super.func_178267_a(blockAccessIn, modelIn, blockStateIn, blockPosIn, buffer, checkSides);
    }

    private boolean isVisible(Block block) {
        String name = BlockUtils.getName(block);
        int index = Collections.binarySearch(XRayHack.blocks.getBlockNames(), name);
        return index >= 0;
    }
}

