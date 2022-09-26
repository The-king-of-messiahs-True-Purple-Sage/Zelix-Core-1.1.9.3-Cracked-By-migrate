/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.PlayerControllerUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class NukerHack
extends Hack {
    private final SliderSetting range = new SliderSetting("Range", 5.0, 1.0, 6.0, 0.05, SliderSetting.ValueDisplay.DECIMAL);
    private final EnumSetting<Mode> mode = new EnumSetting("Mode", (Enum[])Mode.values(), (Enum)Mode.NORMAL);
    private final ArrayDeque<Set<BlockPos>> prevBlocks = new ArrayDeque();
    private BlockPos currentBlock;
    private float progress;
    private float prevProgress;
    private int id;

    public NukerHack() {
        super("Nuker", "Automatically breaks blocks around you.");
        this.setCategory(Category.BLOCKS);
        this.addSetting(this.range);
        this.addSetting(this.mode);
    }

    @Override
    public String getRenderName() {
        return this.mode.getSelected().getRenderName(this);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (this.currentBlock != null) {
            try {
                PlayerControllerUtils.setIsHittingBlock(true);
                NukerHack.mc.playerController.resetBlockRemoving();
                this.currentBlock = null;
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        this.prevBlocks.clear();
        this.id = 0;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        this.currentBlock = null;
        Vec3d eyesPos = RotationUtils.getEyesPos().func_178786_a(0.5, 0.5, 0.5);
        BlockPos eyesBlock = new BlockPos(RotationUtils.getEyesPos());
        double rangeSq = Math.pow(this.range.getValue(), 2.0);
        int blockRange = (int)Math.ceil(this.range.getValue());
        Stream<BlockPos> stream = StreamSupport.stream(BlockPos.func_177980_a((BlockPos)eyesBlock.func_177982_a(blockRange, blockRange, blockRange), (BlockPos)eyesBlock.func_177982_a(-blockRange, -blockRange, -blockRange)).spliterator(), true);
        List blocks = stream.filter(pos -> eyesPos.func_72436_e(new Vec3d((Vec3i)pos)) <= rangeSq).filter(pos -> BlockUtils.canBeClicked(pos)).filter(this.mode.getSelected().getValidator(this)).sorted(Comparator.comparingDouble(pos -> eyesPos.func_72436_e(new Vec3d((Vec3i)pos)))).collect(Collectors.toList());
        if (player.capabilities.isCreativeMode) {
            void var10_10;
            Stream stream2 = blocks.parallelStream();
            for (Set<BlockPos> set : this.prevBlocks) {
                Stream<BlockPos> stream3 = var10_10.filter(pos -> !set.contains(pos));
            }
            List<BlockPos> blocks2 = var10_10.collect(Collectors.toList());
            this.prevBlocks.addLast(new HashSet(blocks2));
            while (this.prevBlocks.size() > 5) {
                this.prevBlocks.removeFirst();
            }
            if (!blocks2.isEmpty()) {
                this.currentBlock = (BlockPos)blocks2.get(0);
            }
            NukerHack.mc.playerController.resetBlockRemoving();
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
            BlockUtils.breakBlocksPacketSpam(blocks2);
            return;
        }
        for (BlockPos pos2 : blocks) {
            if (!BlockUtils.breakBlockSimple(pos2)) continue;
            this.currentBlock = pos2;
            break;
        }
        if (this.currentBlock == null) {
            NukerHack.mc.playerController.resetBlockRemoving();
        }
        if (this.currentBlock != null && BlockUtils.getHardness(this.currentBlock) < 1.0f) {
            try {
                this.prevProgress = this.progress;
                this.progress = PlayerControllerUtils.getCurBlockDamageMP();
                if (!(this.progress < this.prevProgress)) return;
                this.prevProgress = this.progress;
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                this.setEnabled(false);
                throw new RuntimeException(reflectiveOperationException);
            }
        } else {
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
        }
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.worldObj.isRemote) {
            return;
        }
        if (this.mode.getSelected() == Mode.ID) {
            this.id = BlockUtils.getId(event.getPos());
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.currentBlock == null) {
            return;
        }
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        AxisAlignedBB box = new AxisAlignedBB(BlockPos.field_177992_a);
        float p = this.prevProgress + (this.progress - this.prevProgress) * event.getPartialTicks();
        float red = p * 2.0f;
        float green = 2.0f - red;
        GL11.glTranslated((double)this.currentBlock.func_177958_n(), (double)this.currentBlock.func_177956_o(), (double)this.currentBlock.func_177952_p());
        if (p < 1.0f) {
            GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
            GL11.glScaled((double)p, (double)p, (double)p);
            GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
        }
        GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.25f);
        GL11.glBegin((int)7);
        RenderUtils.drawSolidBox(box);
        GL11.glEnd();
        GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(box);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private static enum Mode {
        NORMAL("Normal", n -> n.getName(), (n, p) -> true),
        ID("ID", n -> "IDNuker [" + ((NukerHack)n).id + "]", (n, p) -> BlockUtils.getId(p) == ((NukerHack)n).id),
        FLAT("Flat", n -> "FlatNuker", (n, p) -> p.func_177956_o() >= WMinecraft.getPlayer().func_180425_c().func_177956_o()),
        SMASH("Smash", n -> "SmashNuker", (n, p) -> BlockUtils.getHardness(p) >= 1.0f);

        private final String name;
        private final Function<NukerHack, String> renderName;
        private final BiPredicate<NukerHack, BlockPos> validator;

        private Mode(String name, Function<NukerHack, String> renderName, BiPredicate<NukerHack, BlockPos> validator) {
            this.name = name;
            this.renderName = renderName;
            this.validator = validator;
        }

        public String toString() {
            return this.name;
        }

        public String getRenderName(NukerHack n) {
            return this.renderName.apply(n);
        }

        public Predicate<BlockPos> getValidator(NukerHack n) {
            return p -> this.validator.test(n, (BlockPos)p);
        }
    }
}

