/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.world.World
 */
package zelix.hack.hacks.xray.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import zelix.hack.hacks.xray.reference.block.BlockInfo;

public class Utils {
    public static void sendMessage(EntityPlayerSP player, String message) {
        player.func_145747_a((ITextComponent)new TextComponentString(message));
    }

    public static IBlockState getStateFromPlacement(World world, EntityLivingBase player, ItemStack stack) {
        return Block.getBlockFromItem((Item)stack.getItem()).getStateForPlacement(world, player.func_180425_c(), EnumFacing.NORTH, 0.1f, 0.1f, 0.1f, stack.getMetadata(), player, player.func_184600_cs());
    }

    public static int clampColor(int c) {
        return c < 0 ? 0 : (c > 255 ? 255 : c);
    }

    public static void renderBlockBounding(BufferBuilder buffer, BlockInfo b, int opacity) {
        if (b == null) {
            return;
        }
        float size = 1.0f;
        int red = b.color[0];
        int green = b.color[1];
        int blue = b.color[2];
        int x = b.func_177958_n();
        int y = b.func_177956_o();
        int z = b.func_177952_p();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)((float)x + 1.0f), (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)((float)z + 1.0f)).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
        buffer.func_181662_b((double)x, (double)((float)y + 1.0f), (double)z).func_181669_b(red, green, blue, opacity).func_181675_d();
    }
}

