/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFalling
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.HackList;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.KeyBindingUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.PlayerControllerUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

@Hack.DontSaveState
public final class TunnellerHack
extends Hack {
    private final EnumSetting<TunnelSize> size = new EnumSetting("Tunnel size", (Enum[])TunnelSize.values(), (Enum)TunnelSize.SIZE_3X3);
    private final SliderSetting limit = new SliderSetting("Limit", "Automatically stops once the tunnel\nhas reached the given length.\n\n0 = no limit", 0.0, 0.0, 1000.0, 1.0, v -> v == 0.0 ? "disabled" : (v == 1.0 ? "1 block" : (int)v + " blocks"));
    private final CheckboxSetting torches = new CheckboxSetting("Place torches", "Places just enough torches\nto prevent mobs from\nspawning inside the tunnel.", false);
    private BlockPos start;
    private EnumFacing direction;
    private int length;
    private Task[] tasks;
    private int[] displayLists = new int[5];
    private BlockPos currentBlock;
    private float progress;
    private float prevProgress;

    public TunnellerHack() {
        super("Tunneller", "Automatically digs a tunnel.\n\n" + ChatFormatting.RED + ChatFormatting.BOLD + "WARNING:" + ChatFormatting.RESET + " Although this bot will try to avoid\nlava and other dangers, there is no guarantee\nthat it won't die. Only send it out with gear\nthat you don't mind losing.");
        this.setCategory(Category.BLOCKS);
        this.addSetting(this.size);
        this.addSetting(this.limit);
        this.addSetting(this.torches);
    }

    @Override
    public String getRenderName() {
        if (this.limit.getValueI() == 0) {
            return this.getName();
        }
        return this.getName() + " [" + this.length + "/" + this.limit.getValueI() + "]";
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
        for (int i = 0; i < this.displayLists.length; ++i) {
            this.displayLists[i] = GL11.glGenLists((int)1);
        }
        EntityPlayerSP player = WMinecraft.getPlayer();
        this.start = new BlockPos((Entity)player);
        this.direction = player.getHorizontalFacing();
        this.length = 0;
        this.tasks = new Task[]{new DodgeLiquidTask(), new FillInFloorTask(), new PlaceTorchTask(), new DigTunnelTask(), new WalkForwardTask()};
        this.updateCyanList();
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (this.currentBlock != null) {
            try {
                PlayerControllerUtils.setIsHittingBlock(true);
                TunnellerHack.mc.playerController.resetBlockRemoving();
                this.currentBlock = null;
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        for (int displayList : this.displayLists) {
            GL11.glDeleteLists((int)displayList, (int)1);
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        KeyBinding[] bindings;
        void var6_8;
        Hack[] incompatibleHax;
        HackList hax = wurst.getHax();
        Hack[] hackArray = incompatibleHax = new Hack[]{hax.autoToolHack, hax.autoWalkHack, hax.flightHack, hax.nukerHack};
        int n = hackArray.length;
        boolean bl = false;
        while (var6_8 < n) {
            Hack hack = hackArray[var6_8];
            hack.setEnabled(false);
            ++var6_8;
        }
        GameSettings gs = TunnellerHack.mc.gameSettings;
        for (KeyBinding keyBinding : bindings = new KeyBinding[]{gs.keyBindForward, gs.keyBindBack, gs.keyBindLeft, gs.keyBindRight, gs.keyBindJump, gs.keyBindSneak}) {
            KeyBindingUtils.setPressed(keyBinding, false);
        }
        for (Task task : this.tasks) {
            if (!task.canRun()) continue;
            task.run();
            break;
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        for (int displayList : this.displayLists) {
            GL11.glCallList((int)displayList);
        }
        if (this.currentBlock != null) {
            float p = this.prevProgress + (this.progress - this.prevProgress) * event.getPartialTicks();
            float red = p * 2.0f;
            float green = 2.0f - red;
            GL11.glTranslated((double)this.currentBlock.func_177958_n(), (double)this.currentBlock.func_177956_o(), (double)this.currentBlock.func_177952_p());
            if (p < 1.0f) {
                GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
                GL11.glScaled((double)p, (double)p, (double)p);
                GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
            }
            AxisAlignedBB box2 = new AxisAlignedBB(BlockPos.field_177992_a);
            GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.25f);
            GL11.glBegin((int)7);
            RenderUtils.drawSolidBox(box2);
            GL11.glEnd();
            GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.5f);
            GL11.glBegin((int)1);
            RenderUtils.drawOutlinedBox(box2);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private void updateCyanList() {
        GL11.glNewList((int)this.displayLists[0], (int)4864);
        GL11.glPushMatrix();
        GL11.glTranslated((double)this.start.func_177958_n(), (double)this.start.func_177956_o(), (double)this.start.func_177952_p());
        GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawNode(new AxisAlignedBB(-0.25, -0.25, -0.25, 0.25, 0.25, 0.25));
        GL11.glEnd();
        RenderUtils.drawArrow(new Vec3d(this.direction.func_176730_m()).func_186678_a(0.25), new Vec3d(this.direction.func_176730_m()).func_186678_a(Math.max(0.5, (double)this.length)));
        GL11.glPopMatrix();
        GL11.glEndList();
    }

    private BlockPos offset(BlockPos pos, Vec3i vec) {
        return pos.func_177967_a(this.direction.rotateYCCW(), vec.func_177958_n()).func_177981_b(vec.func_177956_o());
    }

    private int getDistance(BlockPos pos1, BlockPos pos2) {
        return Math.abs(pos1.func_177958_n() - pos2.func_177958_n()) + Math.abs(pos1.func_177956_o() - pos2.func_177956_o()) + Math.abs(pos1.func_177952_p() - pos2.func_177952_p());
    }

    private static enum TunnelSize {
        SIZE_1X2("1x2", new Vec3i(0, 1, 0), new Vec3i(0, 0, 0), 4, 13),
        SIZE_3X3("3x3", new Vec3i(1, 2, 0), new Vec3i(-1, 0, 0), 4, 11);

        private final String name;
        private final Vec3i from;
        private final Vec3i to;
        private final int maxRange;
        private final int torchDistance;

        private TunnelSize(String name, Vec3i from, Vec3i to, int maxRange, int torchDistance) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.maxRange = maxRange;
            this.torchDistance = torchDistance;
        }

        public String toString() {
            return this.name;
        }
    }

    private class PlaceTorchTask
    extends Task {
        private BlockPos lastTorch;
        private BlockPos nextTorch;

        private PlaceTorchTask() {
            this.nextTorch = TunnellerHack.this.start;
        }

        @Override
        public boolean canRun() {
            if (!TunnellerHack.this.torches.isChecked()) {
                this.lastTorch = null;
                this.nextTorch = new BlockPos((Entity)WMinecraft.getPlayer());
                GL11.glNewList((int)TunnellerHack.this.displayLists[4], (int)4864);
                GL11.glEndList();
                return false;
            }
            if (this.lastTorch != null) {
                this.nextTorch = this.lastTorch.func_177967_a(TunnellerHack.this.direction, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).torchDistance);
            }
            GL11.glNewList((int)TunnellerHack.this.displayLists[4], (int)4864);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)0.5f);
            Vec3d torchVec = new Vec3d((Vec3i)this.nextTorch).func_72441_c(0.5, 0.0, 0.5);
            RenderUtils.drawArrow(torchVec, torchVec.func_72441_c(0.0, 0.5, 0.0));
            GL11.glEndList();
            BlockPos base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length);
            if (TunnellerHack.this.getDistance(TunnellerHack.this.start, base) <= TunnellerHack.this.getDistance(TunnellerHack.this.start, this.nextTorch)) {
                return false;
            }
            return Blocks.torch.func_176196_c((World)WMinecraft.getWorld(), this.nextTorch);
        }

        @Override
        public void run() {
            if (!this.equipTorch()) {
                ChatUtils.error("Out of torches.");
                TunnellerHack.this.setEnabled(false);
                return;
            }
            KeyBindingUtils.setPressed(mc.gameSettings.keyBindSneak, true);
            BlockUtils.placeBlockSimple(this.nextTorch);
            if (BlockUtils.getBlock(this.nextTorch) instanceof BlockTorch) {
                this.lastTorch = this.nextTorch;
            }
        }

        private boolean equipTorch() {
            for (int slot = 0; slot < 9; ++slot) {
                Block block;
                ItemStack stack = WMinecraft.getPlayer().inventory.getStackInSlot(slot);
                if (stack == null || stack.func_190926_b() || !(stack.getItem() instanceof ItemBlock) || !((block = Block.getBlockFromItem((Item)stack.getItem())) instanceof BlockTorch)) continue;
                WMinecraft.getPlayer().inventory.currentItem = slot;
                return true;
            }
            return false;
        }
    }

    private class DodgeLiquidTask
    extends Task {
        private final HashSet<BlockPos> liquids;
        private int disableTimer;

        private DodgeLiquidTask() {
            this.liquids = new HashSet();
            this.disableTimer = 60;
        }

        @Override
        public boolean canRun() {
            if (!this.liquids.isEmpty()) {
                return true;
            }
            BlockPos base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length);
            BlockPos from = TunnellerHack.this.offset(base, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).from);
            BlockPos to = TunnellerHack.this.offset(base, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).to);
            int maxY = Math.max(from.func_177956_o(), to.func_177956_o());
            for (BlockPos pos : BlockPos.func_177980_a((BlockPos)from, (BlockPos)to)) {
                BlockPos pos4;
                int maxOffset = Math.min(((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).maxRange, TunnellerHack.this.length);
                for (int i = 0; i <= maxOffset; ++i) {
                    BlockPos pos2 = pos.func_177967_a(TunnellerHack.this.direction.getOpposite(), i);
                    if (!(BlockUtils.getBlock(pos2) instanceof BlockLiquid)) continue;
                    this.liquids.add(pos2);
                }
                if (BlockUtils.getState(pos).func_185913_b()) continue;
                BlockPos pos3 = pos.func_177972_a(TunnellerHack.this.direction);
                if (BlockUtils.getBlock(pos3) instanceof BlockLiquid) {
                    this.liquids.add(pos3);
                }
                if (pos.func_177956_o() != maxY || !(BlockUtils.getBlock(pos4 = pos.func_177984_a()) instanceof BlockLiquid)) continue;
                this.liquids.add(pos4);
            }
            if (this.liquids.isEmpty()) {
                return false;
            }
            ChatUtils.error("The tunnel is flooded, cannot continue.");
            GL11.glNewList((int)TunnellerHack.this.displayLists[3], (int)4864);
            AxisAlignedBB box = new AxisAlignedBB(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
            for (BlockPos pos : this.liquids) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
                GL11.glBegin((int)1);
                RenderUtils.drawOutlinedBox(box);
                GL11.glEnd();
                GL11.glPopMatrix();
            }
            GL11.glEndList();
            return true;
        }

        @Override
        public void run() {
            BlockPos player = new BlockPos((Entity)WMinecraft.getPlayer());
            KeyBinding forward = mc.gameSettings.keyBindForward;
            Vec3d diffVec = new Vec3d((Vec3i)player.func_177973_b((Vec3i)TunnellerHack.this.start));
            Vec3d dirVec = new Vec3d(TunnellerHack.this.direction.func_176730_m());
            double dotProduct = diffVec.func_72430_b(dirVec);
            BlockPos pos1 = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, (int)dotProduct);
            if (!player.equals((Object)pos1)) {
                RotationUtils.faceVectorForWalking(this.toVec3d(pos1));
                KeyBindingUtils.setPressed(forward, true);
                return;
            }
            BlockPos pos2 = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, Math.max(0, TunnellerHack.this.length - 10));
            if (!player.equals((Object)pos2)) {
                RotationUtils.faceVectorForWalking(this.toVec3d(pos2));
                KeyBindingUtils.setPressed(forward, true);
                WMinecraft.getPlayer().setSprinting(true);
                return;
            }
            BlockPos pos3 = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length + 1);
            RotationUtils.faceVectorForWalking(this.toVec3d(pos3));
            KeyBindingUtils.setPressed(forward, false);
            WMinecraft.getPlayer().setSprinting(false);
            if (this.disableTimer > 0) {
                --this.disableTimer;
                return;
            }
            TunnellerHack.this.setEnabled(false);
        }

        private Vec3d toVec3d(BlockPos pos) {
            return new Vec3d((Vec3i)pos).func_72441_c(0.5, 0.5, 0.5);
        }
    }

    private class FillInFloorTask
    extends Task {
        private final ArrayList<BlockPos> blocks;

        private FillInFloorTask() {
            this.blocks = new ArrayList();
        }

        @Override
        public boolean canRun() {
            BlockPos player = new BlockPos((Entity)WMinecraft.getPlayer());
            BlockPos from = this.offsetFloor(player, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).from);
            BlockPos to = this.offsetFloor(player, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).to);
            this.blocks.clear();
            for (BlockPos pos : BlockPos.func_177980_a((BlockPos)from, (BlockPos)to)) {
                if (BlockUtils.getState(pos).func_185913_b()) continue;
                this.blocks.add(pos);
            }
            GL11.glNewList((int)TunnellerHack.this.displayLists[2], (int)4864);
            AxisAlignedBB box = new AxisAlignedBB(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)0.5f);
            for (BlockPos pos : this.blocks) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
                GL11.glBegin((int)1);
                RenderUtils.drawOutlinedBox(box);
                GL11.glEnd();
                GL11.glPopMatrix();
            }
            GL11.glEndList();
            return !this.blocks.isEmpty();
        }

        private BlockPos offsetFloor(BlockPos pos, Vec3i vec) {
            return pos.func_177967_a(TunnellerHack.this.direction.rotateYCCW(), vec.func_177958_n()).func_177977_b();
        }

        @Override
        public void run() {
            KeyBindingUtils.setPressed(mc.gameSettings.keyBindSneak, true);
            WMinecraft.getPlayer().motionX = 0.0;
            WMinecraft.getPlayer().motionZ = 0.0;
            Vec3d eyes = RotationUtils.getEyesPos().func_72441_c(-0.5, -0.5, -0.5);
            Comparator<BlockPos> comparator = Comparator.comparingDouble(p -> eyes.func_72436_e(new Vec3d((Vec3i)p)));
            BlockPos pos = this.blocks.stream().max(comparator).get();
            if (!this.equipSolidBlock(pos)) {
                ChatUtils.error("Found a hole in the tunnel's floor but don't have any blocks to fill it with.");
                TunnellerHack.this.setEnabled(false);
                return;
            }
            if (BlockUtils.getMaterial(pos).isReplaceable()) {
                BlockUtils.placeBlockSimple(pos);
            } else {
                wurst.getHax().autoToolHack.equipBestTool(pos, false, true, false);
                BlockUtils.breakBlockSimple(pos);
            }
        }

        private boolean equipSolidBlock(BlockPos pos) {
            for (int slot = 0; slot < 9; ++slot) {
                Block block;
                ItemStack stack = WMinecraft.getPlayer().inventory.getStackInSlot(slot);
                if (stack == null || stack.func_190926_b() || !(stack.getItem() instanceof ItemBlock) || !(block = Block.getBlockFromItem((Item)stack.getItem())).getDefaultState().func_185913_b() || block instanceof BlockFalling && BlockFalling.func_185759_i((IBlockState)BlockUtils.getState(pos.func_177977_b()))) continue;
                WMinecraft.getPlayer().inventory.currentItem = slot;
                return true;
            }
            return false;
        }
    }

    private class WalkForwardTask
    extends Task {
        private WalkForwardTask() {
        }

        @Override
        public boolean canRun() {
            BlockPos base;
            BlockPos player = new BlockPos((Entity)WMinecraft.getPlayer());
            return TunnellerHack.this.getDistance(player, base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length)) > 1;
        }

        @Override
        public void run() {
            BlockPos base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length);
            Vec3d vec = new Vec3d((Vec3i)base).func_72441_c(0.5, 0.5, 0.5);
            RotationUtils.faceVectorForWalking(vec);
            KeyBindingUtils.setPressed(mc.gameSettings.keyBindForward, true);
        }
    }

    private class DigTunnelTask
    extends Task {
        private int requiredDistance;

        private DigTunnelTask() {
        }

        @Override
        public boolean canRun() {
            BlockPos base;
            BlockPos player = new BlockPos((Entity)WMinecraft.getPlayer());
            int distance = TunnellerHack.this.getDistance(player, base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length));
            if (distance <= 1) {
                this.requiredDistance = ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).maxRange;
            } else if (distance > ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).maxRange) {
                this.requiredDistance = 1;
            }
            return distance <= this.requiredDistance;
        }

        @Override
        public void run() {
            BlockPos base = TunnellerHack.this.start.func_177967_a(TunnellerHack.this.direction, TunnellerHack.this.length);
            BlockPos from = TunnellerHack.this.offset(base, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).from);
            BlockPos to = TunnellerHack.this.offset(base, ((TunnelSize)((Object)TunnellerHack.this.size.getSelected())).to);
            ArrayList blocks = new ArrayList();
            BlockPos.func_177980_a((BlockPos)from, (BlockPos)to).forEach(blocks::add);
            Collections.reverse(blocks);
            GL11.glNewList((int)TunnellerHack.this.displayLists[1], (int)4864);
            AxisAlignedBB box = new AxisAlignedBB(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
            GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
            for (BlockPos pos : blocks) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
                GL11.glBegin((int)1);
                RenderUtils.drawOutlinedBox(box);
                GL11.glEnd();
                GL11.glPopMatrix();
            }
            GL11.glEndList();
            TunnellerHack.this.currentBlock = null;
            for (BlockPos pos : blocks) {
                if (!BlockUtils.canBeClicked(pos)) continue;
                TunnellerHack.this.currentBlock = pos;
                break;
            }
            if (TunnellerHack.this.currentBlock == null) {
                mc.playerController.resetBlockRemoving();
                TunnellerHack.this.progress = 1.0f;
                TunnellerHack.this.prevProgress = 1.0f;
                TunnellerHack.this.length++;
                if (TunnellerHack.this.limit.getValueI() == 0 || TunnellerHack.this.length < TunnellerHack.this.limit.getValueI()) {
                    TunnellerHack.this.updateCyanList();
                } else {
                    ChatUtils.message("Tunnel completed.");
                    TunnellerHack.this.setEnabled(false);
                }
                return;
            }
            wurst.getHax().autoToolHack.equipBestTool(TunnellerHack.this.currentBlock, false, true, false);
            BlockUtils.breakBlockSimple(TunnellerHack.this.currentBlock);
            if (WMinecraft.getPlayer().capabilities.isCreativeMode || BlockUtils.getHardness(TunnellerHack.this.currentBlock) >= 1.0f) {
                TunnellerHack.this.progress = 1.0f;
                TunnellerHack.this.prevProgress = 1.0f;
                return;
            }
            try {
                TunnellerHack.this.prevProgress = TunnellerHack.this.progress;
                TunnellerHack.this.progress = PlayerControllerUtils.getCurBlockDamageMP();
                if (TunnellerHack.this.progress < TunnellerHack.this.prevProgress) {
                    TunnellerHack.this.prevProgress = TunnellerHack.this.progress;
                }
            }
            catch (ReflectiveOperationException e) {
                TunnellerHack.this.setEnabled(false);
                throw new RuntimeException(e);
            }
        }
    }

    private static abstract class Task {
        private Task() {
        }

        public abstract boolean canRun();

        public abstract void run();
    }
}

