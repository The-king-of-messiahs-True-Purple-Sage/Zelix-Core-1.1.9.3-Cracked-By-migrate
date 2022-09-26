/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.EBlockPos;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class Search
extends Hack {
    public static List<BlockPos> toRender = new ArrayList<BlockPos>();
    public EBlockPos pos = new EBlockPos();
    public BooleanValue dia = new BooleanValue("Diamond", true);
    public BooleanValue gold = new BooleanValue("Gold", true);
    public BooleanValue iron = new BooleanValue("Iron", true);
    public BooleanValue lapis = new BooleanValue("Lapis", true);
    public BooleanValue emerald = new BooleanValue("Emerald", true);
    public BooleanValue coal = new BooleanValue("Coal", true);
    public BooleanValue redstone = new BooleanValue("Redstone", true);
    public BooleanValue bypass = new BooleanValue("Bypass", true);
    public BooleanValue limitEnabled = new BooleanValue("RenderLimitEnabled", true);
    public BooleanValue radiusOn = new BooleanValue("RadiusEnabled", true);
    public NumberValue depth = new NumberValue("Depth", 2.0, 1.0, 5.0);
    public NumberValue limit = new NumberValue("RenderLimit", 10.0, 5.0, 100.0);
    public NumberValue refresh_timer = new NumberValue("RefreshDelay", 5.0, 0.0, 50.0);
    public static NumberValue alpha = new NumberValue("Alpha", 0.25, 0.0, 1.0);
    public static NumberValue width = new NumberValue("LineWidth", 2.5, 1.0, 10.0);
    public NumberValue radius = new NumberValue("Radius", 10.0, 5.0, 100.0);
    private final Minecraft mc = Minecraft.getMinecraft();
    private final TimerUtils refresh = new TimerUtils();

    public Search() {
        super("Search", HackCategory.VISUAL);
        this.addValue(this.dia);
        this.addValue(this.gold);
        this.addValue(this.iron);
        this.addValue(this.lapis);
        this.addValue(this.emerald);
        this.addValue(this.coal);
        this.addValue(this.redstone);
        this.addValue(this.bypass);
        this.addValue(this.depth);
        this.addValue(this.radiusOn);
        this.addValue(this.radius);
        this.addValue(this.limitEnabled);
        this.addValue(this.limit);
        this.addValue(this.refresh_timer);
        this.addValue(alpha);
        this.addValue(width);
    }

    @Override
    public void onEnable() {
        toRender.clear();
        this.refresh.reset();
        Wrapper.INSTANCE.mc().renderGlobal.loadRenderers();
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (BlockPos blockPos : toRender) {
            Search.renderBlock(blockPos, event);
        }
        if (this.refresh.isDelay(((Double)this.refresh_timer.getValue()).longValue())) {
            WorldClient world = Wrapper.INSTANCE.mc().theWorld;
            EntityPlayerSP player = Wrapper.INSTANCE.mc().thePlayer;
            if (world != null && player != null) {
                int sx = (int)player.posX - ((Double)this.radius.getValue()).intValue();
                int sz = (int)player.posZ - ((Double)this.radius.getValue()).intValue();
                int endX = (int)player.posX + ((Double)this.radius.getValue()).intValue();
                int endZ = (int)player.posZ + ((Double)this.radius.getValue()).intValue();
                for (int x = sx; x <= endX; ++x) {
                    this.pos.setX(x);
                    for (int z = sz; z <= endZ; ++z) {
                        Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        if (!chunk.isLoaded()) continue;
                        this.pos.setZ(z);
                        for (int y = 0; y <= 255; ++y) {
                            BlockPos poss;
                            this.pos.setY(y);
                            IBlockState blockState = chunk.func_177435_g((BlockPos)this.pos);
                            Block block = blockState.getBlock();
                            if (block == Blocks.air || toRender.contains(poss = new BlockPos(x, y, z)) || !this.test(poss) || (double)toRender.size() > (Double)this.limit.getValue() && ((Boolean)this.limitEnabled.getValue()).booleanValue()) continue;
                            toRender.add(poss);
                        }
                    }
                }
                List<Object> list = toRender;
                toRender = list = list.stream().filter(this::test).collect(Collectors.toList());
                this.refresh.reset();
            }
        }
    }

    @Override
    public void onRender3D(RenderBlockOverlayEvent event) {
        if (!(toRender.contains((Object)this.pos) || !this.test(this.pos) || (double)toRender.size() > (Double)this.limit.getValue() && ((Boolean)this.limitEnabled.getValue()).booleanValue())) {
            toRender.add(this.pos);
        }
    }

    public boolean isTarget(BlockPos pos) {
        Block block = Wrapper.INSTANCE.mc().theWorld.func_180495_p(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return (Boolean)this.dia.getValue();
        }
        if (Blocks.lapis_ore.equals(block)) {
            return (Boolean)this.lapis.getValue();
        }
        if (Blocks.iron_ore.equals(block)) {
            return (Boolean)this.iron.getValue();
        }
        if (Blocks.gold_ore.equals(block)) {
            return (Boolean)this.gold.getValue();
        }
        if (Blocks.coal_ore.equals(block)) {
            return (Boolean)this.coal.getValue();
        }
        if (Blocks.emerald_ore.equals(block)) {
            return (Boolean)this.emerald.getValue();
        }
        if (Blocks.redstone_torch.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return (Boolean)this.redstone.getValue();
        }
        return false;
    }

    private Boolean oreTest(BlockPos origPos, Double depth) {
        ArrayList<BlockPos> posesNew = new ArrayList<BlockPos>();
        ArrayList<Object> posesLast = new ArrayList<BlockPos>(Collections.singletonList(origPos));
        ArrayList<BlockPos> finalList = new ArrayList<BlockPos>();
        int i = 0;
        while ((double)i < depth) {
            for (BlockPos blockPos2 : posesLast) {
                posesNew.add(blockPos2.func_177984_a());
                posesNew.add(blockPos2.func_177977_b());
                posesNew.add(blockPos2.func_177978_c());
                posesNew.add(blockPos2.func_177968_d());
                posesNew.add(blockPos2.func_177976_e());
                posesNew.add(blockPos2.func_177974_f());
            }
            for (BlockPos blockPos3 : posesNew) {
                if (!posesLast.contains(blockPos3)) continue;
                posesNew.remove(blockPos3);
            }
            posesLast = posesNew;
            finalList.addAll(posesNew);
            posesNew = new ArrayList();
            ++i;
        }
        List<Block> legitBlocks = Arrays.asList(Blocks.water, Blocks.lava, Blocks.flowing_lava, Blocks.air, Blocks.flowing_water, Blocks.fire);
        return finalList.stream().anyMatch(blockPos -> legitBlocks.contains(Wrapper.INSTANCE.mc().theWorld.func_180495_p(blockPos).getBlock()));
    }

    public boolean test(BlockPos pos1) {
        if (!this.isTarget(pos1)) {
            return false;
        }
        if (((Boolean)this.bypass.getValue()).booleanValue() && !this.oreTest(pos1, (double)((Double)this.depth.getValue())).booleanValue()) {
            return false;
        }
        if (((Boolean)this.radiusOn.getValue()).booleanValue()) {
            return !(Wrapper.INSTANCE.mc().thePlayer.getDistance((double)pos1.func_177958_n(), (double)pos1.func_177956_o(), (double)pos1.func_177952_p()) >= (Double)this.radius.getValue());
        }
        return true;
    }

    public static void renderBlock(BlockPos pos, RenderWorldLastEvent event) {
        double x = (double)pos.func_177958_n() - Wrapper.getRenderPosX();
        double y = (double)pos.func_177956_o() - Wrapper.getRenderPosY();
        double z = (double)pos.func_177952_p() - Wrapper.getRenderPosZ();
        float[] color = Search.getColor(pos);
        RenderUtils.drawTracer(pos, color[0], color[1], color[2], ((Double)alpha.getValue()).floatValue(), event.getPartialTicks());
        Search.drawOutlinedBlockESP(x, y, z, color[0], color[1], color[2], ((Double)alpha.getValue()).floatValue(), ((Double)width.getValue()).floatValue());
    }

    public static float[] getColor(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.func_180495_p(pos).getBlock();
        if (Blocks.diamond_ore.equals(block)) {
            return new float[]{0.0f, 1.0f, 1.0f};
        }
        if (Blocks.lapis_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 1.0f};
        }
        if (Blocks.iron_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 1.0f};
        }
        if (Blocks.gold_ore.equals(block)) {
            return new float[]{1.0f, 1.0f, 0.0f};
        }
        if (Blocks.coal_ore.equals(block)) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        if (Blocks.emerald_block.equals(block)) {
            return new float[]{0.0f, 1.0f, 0.0f};
        }
        if (Blocks.redstone_ore.equals(block) || Blocks.lit_redstone_ore.equals(block)) {
            return new float[]{1.0f, 0.0f, 0.0f};
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)lineWidth);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        Search.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.POSITION);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        tessellator.draw();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.POSITION);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        tessellator.draw();
        worldRenderer.func_181668_a(1, DefaultVertexFormats.POSITION);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        tessellator.draw();
    }
}

