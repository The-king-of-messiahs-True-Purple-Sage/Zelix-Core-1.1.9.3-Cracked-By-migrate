/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockCarpet
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.BlockLadder
 *  net.minecraft.block.BlockSkull
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.BlockUtils;
import zelix.utils.RobotUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Scaffold
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("AAC", false), new Mode("Simple", true), new Mode("GodBridge", false));
    public TimerUtils timer;
    public BlockData blockData;
    boolean isBridging = false;
    BlockPos blockDown = null;
    public static float[] facingCam = null;
    float startYaw = 0.0f;
    float startPitch = 0.0f;
    public int godBridgeTimer;

    public Scaffold() {
        super("Scaffold", HackCategory.PLAYER);
        this.addValue(this.mode);
        this.timer = new TimerUtils();
    }

    @Override
    public String getDescription() {
        return "Automatically places blocks below your feet.";
    }

    @Override
    public void onDisable() {
        HackManager.getHack("SafeWalk").setToggled(false);
        facingCam = null;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.blockDown = null;
        facingCam = null;
        this.isBridging = false;
        this.startYaw = 0.0f;
        this.startPitch = 0.0f;
        if (this.mode.getMode("AAC").isToggled() && Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown()) {
            KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindBack.getKeyCode(), (boolean)false);
        }
        HackManager.getHack("SafeWalk").setToggled(true);
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            this.AAC();
            this.godBridgeTimer = 0;
        } else if (this.mode.getMode("Simple").isToggled()) {
            this.Simple();
            this.godBridgeTimer = 0;
        } else if (this.mode.getMode("GodBridge").isToggled()) {
            this.GodBridge();
        }
        super.onClientTick(event);
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.blockDown != null) {
            RenderUtils.drawBlockESP(this.blockDown, 1.0f, 1.0f, 1.0f);
            if (this.mode.getMode("AAC").isToggled()) {
                BlockPos blockDown2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b();
                BlockPos blockDown3 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b();
                if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.EAST) {
                    blockDown2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177976_e();
                    blockDown3 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177985_f(2);
                } else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.NORTH) {
                    blockDown2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177968_d();
                    blockDown3 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177970_e(2);
                } else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.SOUTH) {
                    blockDown2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177978_c();
                    blockDown3 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177964_d(2);
                } else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.WEST) {
                    blockDown2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177974_f();
                    blockDown3 = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177965_g(2);
                }
                RenderUtils.drawBlockESP(blockDown2, 1.0f, 0.0f, 0.0f);
                RenderUtils.drawBlockESP(blockDown3, 1.0f, 0.0f, 0.0f);
            }
        }
        super.onRenderWorldLast(event);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (this.mode.getMode("AAC").isToggled() && event.getEntity() == Wrapper.INSTANCE.player()) {
            if (this.startYaw == 0.0f || this.startPitch == 0.0f) {
                return;
            }
            event.setYaw(this.startYaw);
            event.setPitch(this.startPitch - 70.0f);
            facingCam = new float[]{this.startYaw - 180.0f, this.startPitch - 70.0f};
        }
        super.onCameraSetup(event);
    }

    void GodBridge() {
        if (this.godBridgeTimer > 0) {
            --this.godBridgeTimer;
        }
        if (Wrapper.INSTANCE.world() == null || Wrapper.INSTANCE.player() == null) {
            return;
        }
        WorldClient world = Wrapper.INSTANCE.world();
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        RayTraceResult movingObjectPosition = player.func_174822_a((double)Wrapper.INSTANCE.controller().getBlockReachDistance(), 1.0f);
        boolean isKeyUseDown = false;
        int keyCode = Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode();
        isKeyUseDown = keyCode >= 0 ? Keyboard.isKeyDown((int)keyCode) : Mouse.isButtonDown((int)(keyCode + 100));
        if (movingObjectPosition != null && movingObjectPosition.field_72313_a == RayTraceResult.Type.BLOCK && movingObjectPosition.field_178784_b == EnumFacing.UP && isKeyUseDown) {
            ItemBlock itemblock;
            int i;
            ItemStack itemstack = player.inventory.getCurrentItem();
            int n = i = itemstack != null ? itemstack.getMaxStackSize() : 0;
            if (itemstack != null && itemstack.getItem() instanceof ItemBlock && !(itemblock = (ItemBlock)itemstack.getItem()).func_179222_a((World)world, movingObjectPosition.func_178782_a(), movingObjectPosition.field_178784_b, (EntityPlayer)player, itemstack)) {
                BlockPos blockPos = movingObjectPosition.func_178782_a();
                IBlockState blockState = world.func_180495_p(blockPos);
                AxisAlignedBB axisalignedbb = blockState.getBlock().func_180640_a(BlockUtils.getState(blockPos), (World)world, blockPos);
                if (axisalignedbb == null || world.func_175623_d(blockPos)) {
                    return;
                }
                Vec3d targetVec3 = null;
                Vec3d eyeVec3 = player.func_174824_e(1.0f);
                double x1 = axisalignedbb.field_72340_a;
                double x2 = axisalignedbb.field_72336_d;
                double y1 = axisalignedbb.field_72338_b;
                double y2 = axisalignedbb.field_72337_e;
                double z1 = axisalignedbb.field_72339_c;
                double z2 = axisalignedbb.field_72334_f;
                class Data
                implements Comparable<Data> {
                    public BlockPos blockPos;
                    public EnumFacing enumFacing;
                    public double cost;

                    public Data(BlockPos blockPos, EnumFacing enumFacing, double cost) {
                        this.blockPos = blockPos;
                        this.enumFacing = enumFacing;
                        this.cost = cost;
                    }

                    @Override
                    public int compareTo(Data data) {
                        return this.cost - data.cost > 0.0 ? -1 : (this.cost - data.cost < 0.0 ? 1 : 0);
                    }
                }
                ArrayList<Data> list = new ArrayList<Data>();
                if (!(x1 <= eyeVec3.field_72450_a && eyeVec3.field_72450_a <= x2 && y1 <= eyeVec3.field_72448_b && eyeVec3.field_72448_b <= y2 && z1 <= eyeVec3.field_72449_c && eyeVec3.field_72449_c <= z2)) {
                    double xCost = Math.abs(eyeVec3.field_72450_a - 0.5 * (axisalignedbb.field_72340_a + axisalignedbb.field_72336_d));
                    double yCost = Math.abs(eyeVec3.field_72448_b - 0.5 * (axisalignedbb.field_72338_b + axisalignedbb.field_72337_e));
                    double zCost = Math.abs(eyeVec3.field_72449_c - 0.5 * (axisalignedbb.field_72339_c + axisalignedbb.field_72334_f));
                    double sumCost = xCost + yCost + zCost;
                    if (eyeVec3.field_72450_a < x1) {
                        list.add(new Data(blockPos.func_177976_e(), EnumFacing.WEST, xCost));
                    } else if (eyeVec3.field_72450_a > x2) {
                        list.add(new Data(blockPos.func_177974_f(), EnumFacing.EAST, xCost));
                    }
                    if (eyeVec3.field_72449_c < z1) {
                        list.add(new Data(blockPos.func_177978_c(), EnumFacing.NORTH, zCost));
                    } else if (eyeVec3.field_72449_c > z2) {
                        list.add(new Data(blockPos.func_177968_d(), EnumFacing.SOUTH, zCost));
                    }
                    Collections.sort(list);
                    double border = 0.05;
                    double x = MathHelper.func_151237_a((double)eyeVec3.field_72450_a, (double)(x1 + border), (double)(x2 - border));
                    double y = MathHelper.func_151237_a((double)eyeVec3.field_72448_b, (double)(y1 + border), (double)(y2 - border));
                    double z = MathHelper.func_151237_a((double)eyeVec3.field_72449_c, (double)(z1 + border), (double)(z2 - border));
                    for (Data data : list) {
                        if (!world.func_175623_d(data.blockPos)) continue;
                        if (data.enumFacing == EnumFacing.WEST || data.enumFacing == EnumFacing.EAST) {
                            x = MathHelper.func_151237_a((double)eyeVec3.field_72450_a, (double)x1, (double)x2);
                        } else if (data.enumFacing == EnumFacing.UP || data.enumFacing == EnumFacing.DOWN) {
                            y = MathHelper.func_151237_a((double)eyeVec3.field_72448_b, (double)y1, (double)y2);
                        } else {
                            z = MathHelper.func_151237_a((double)eyeVec3.field_72449_c, (double)z1, (double)z2);
                        }
                        targetVec3 = new Vec3d(x, y, z);
                        break;
                    }
                    if (targetVec3 != null) {
                        double d0 = targetVec3.field_72450_a - eyeVec3.field_72450_a;
                        double d1 = targetVec3.field_72448_b - eyeVec3.field_72448_b;
                        double d2 = targetVec3.field_72449_c - eyeVec3.field_72449_c;
                        double d3 = MathHelper.func_76133_a((double)(d0 * d0 + d2 * d2));
                        float f = (float)(MathHelper.func_181159_b((double)d2, (double)d0) * 180.0 / Math.PI) - 90.0f;
                        float f1 = (float)(-(MathHelper.func_181159_b((double)d1, (double)d3) * 180.0 / Math.PI));
                        float f2 = player.rotationYaw;
                        float f3 = player.rotationPitch;
                        player.rotationYaw = f;
                        player.rotationPitch = f1;
                        RayTraceResult movingObjectPosition1 = player.func_174822_a((double)Wrapper.INSTANCE.controller().getBlockReachDistance(), 1.0f);
                        if (movingObjectPosition1.field_72313_a == RayTraceResult.Type.BLOCK && movingObjectPosition1.func_178782_a().func_177958_n() == blockPos.func_177958_n() && movingObjectPosition1.func_178782_a().func_177956_o() == blockPos.func_177956_o() && movingObjectPosition1.func_178782_a().func_177952_p() == blockPos.func_177952_p()) {
                            if (Wrapper.INSTANCE.controller().func_187099_a(player, Wrapper.INSTANCE.world(), blockPos, movingObjectPosition1.field_178784_b, movingObjectPosition1.field_72307_f, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
                                player.func_184609_a(EnumHand.MAIN_HAND);
                            }
                            if (itemstack != null) {
                                if (itemstack.getMaxStackSize() == 0) {
                                    player.inventory.mainInventory.set(player.inventory.currentItem, null);
                                } else if (itemstack.getMaxStackSize() != i || Wrapper.INSTANCE.controller().isInCreativeMode()) {
                                    Wrapper.INSTANCE.mc().entityRenderer.itemRenderer.func_187460_a(EnumHand.MAIN_HAND);
                                }
                            }
                        }
                        player.rotationYaw = f2;
                        player.rotationPitch = f3;
                        double targetPitch = 75.5;
                        double pitchDelta = 2.5;
                        if (targetPitch - pitchDelta < (double)player.rotationPitch && (double)player.rotationPitch < targetPitch + pitchDelta) {
                            double delta;
                            double mod = (double)player.rotationYaw % 45.0;
                            if (mod < 0.0) {
                                mod += 45.0;
                            }
                            if (mod < (delta = 5.0)) {
                                player.rotationYaw = (float)((double)player.rotationYaw - mod);
                                player.rotationPitch = (float)targetPitch;
                            } else if (45.0 - mod < delta) {
                                player.rotationYaw = (float)((double)player.rotationYaw + (45.0 - mod));
                                player.rotationPitch = (float)targetPitch;
                            }
                        }
                        ReflectionHelper.setPrivateValue(Minecraft.class, (Object)Wrapper.INSTANCE.mc(), (Object)new Integer(1), (String[])new String[]{"rightClickDelayTimer", "field_71467_ac"});
                        this.godBridgeTimer = 10;
                    }
                }
            }
        }
    }

    void AAC() {
        int newSlot;
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        int oldSlot = -1;
        if (!this.check()) {
            if (this.isBridging) {
                KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), (boolean)BlockUtils.isBlockMaterial(new BlockPos((Entity)player).func_177977_b(), Blocks.air));
                this.isBridging = false;
                if (oldSlot != -1) {
                    player.inventory.currentItem = oldSlot;
                }
            }
            this.startYaw = 0.0f;
            this.startPitch = 0.0f;
            facingCam = null;
            this.blockDown = null;
            return;
        }
        this.startYaw = Wrapper.INSTANCE.player().rotationYaw;
        this.startPitch = Wrapper.INSTANCE.player().rotationPitch;
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindRight.getKeyCode(), (boolean)false);
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindLeft.getKeyCode(), (boolean)false);
        this.blockDown = new BlockPos((Entity)player).func_177977_b();
        float r1 = new Random().nextFloat();
        if (r1 == 1.0f) {
            r1 -= 1.0f;
        }
        if ((newSlot = this.findSlotWithBlock()) == -1) {
            return;
        }
        oldSlot = player.inventory.currentItem;
        player.inventory.currentItem = newSlot;
        player.rotationPitch = Utils.updateRotation(player.rotationPitch, 82.0f - r1, 15.0f);
        int currentCPS = Utils.random(3, 4);
        if (this.timer.isDelay(1000 / currentCPS)) {
            RobotUtils.clickMouse(1);
            Utils.swingMainHand();
            this.timer.setLastMS();
        }
        this.isBridging = true;
        KeyBinding.setKeyBindState((int)Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), (boolean)BlockUtils.isBlockMaterial(new BlockPos((Entity)player).func_177977_b(), Blocks.air));
    }

    void Simple() {
        this.blockDown = new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b();
        if (!BlockUtils.getBlock(this.blockDown).func_149688_o(BlockUtils.getBlock(this.blockDown).getDefaultState()).isReplaceable()) {
            return;
        }
        int newSlot = this.findSlotWithBlock();
        if (newSlot == -1) {
            return;
        }
        int oldSlot = Wrapper.INSTANCE.inventory().currentItem;
        Wrapper.INSTANCE.inventory().currentItem = newSlot;
        Utils.placeBlockScaffold(this.blockDown);
        Wrapper.INSTANCE.inventory().currentItem = oldSlot;
    }

    public int findSlotWithBlock() {
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !(block = Block.getBlockFromItem((Item)stack.getItem()).getDefaultState().getBlock()).func_149730_j(BlockUtils.getBlock(this.blockDown).getDefaultState()) || block == Blocks.sand || block == Blocks.gravel) continue;
            return i;
        }
        return -1;
    }

    boolean check() {
        RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        ItemStack stack = player.inventory.getCurrentItem();
        if (object == null || stack == null) {
            return false;
        }
        if (object.field_72313_a != RayTraceResult.Type.BLOCK) {
            return false;
        }
        if (player.rotationPitch <= 70.0f || !player.onGround || player.isOnLadder() || player.isInLava() || player.isInWater()) {
            return false;
        }
        return Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown();
    }

    private boolean isPosSolid(BlockPos pos) {
        Block block = Wrapper.INSTANCE.world().func_180495_p(pos).getBlock();
        return (block.func_149688_o(null).isSolid() || !block.func_149751_l(null) || block.func_149686_d(null) || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.func_149688_o(null).isLiquid() && !(block instanceof BlockContainer);
    }

    private BlockData getBlockData(BlockPos pos) {
        if (this.isPosSolid(pos.func_177982_a(0, -1, 0))) {
            return new BlockData(pos.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.func_177982_a(1, 0, 0))) {
            return new BlockData(pos.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.func_177982_a(0, 0, 1))) {
            return new BlockData(pos.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.func_177982_a(0, 0, -1))) {
            return new BlockData(pos.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.func_177982_a(-1, 0, 0);
        if (this.isPosSolid(pos1.func_177982_a(0, -1, 0))) {
            return new BlockData(pos1.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos1.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos1.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos1.func_177982_a(1, 0, 0))) {
            return new BlockData(pos1.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos1.func_177982_a(0, 0, 1))) {
            return new BlockData(pos1.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos1.func_177982_a(0, 0, -1))) {
            return new BlockData(pos1.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.func_177982_a(1, 0, 0);
        if (this.isPosSolid(pos2.func_177982_a(0, -1, 0))) {
            return new BlockData(pos2.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos2.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.func_177982_a(1, 0, 0))) {
            return new BlockData(pos2.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.func_177982_a(0, 0, 1))) {
            return new BlockData(pos2.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.func_177982_a(0, 0, -1))) {
            return new BlockData(pos2.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.func_177982_a(0, 0, 1);
        if (this.isPosSolid(pos3.func_177982_a(0, -1, 0))) {
            return new BlockData(pos3.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos3.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.func_177982_a(1, 0, 0))) {
            return new BlockData(pos3.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.func_177982_a(0, 0, 1))) {
            return new BlockData(pos3.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.func_177982_a(0, 0, -1))) {
            return new BlockData(pos3.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.func_177982_a(0, 0, -1);
        if (this.isPosSolid(pos4.func_177982_a(0, -1, 0))) {
            return new BlockData(pos4.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos4.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.func_177982_a(1, 0, 0))) {
            return new BlockData(pos4.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.func_177982_a(0, 0, 1))) {
            return new BlockData(pos4.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.func_177982_a(0, 0, -1))) {
            return new BlockData(pos4.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos19 = pos.func_177982_a(-2, 0, 0);
        if (this.isPosSolid(pos1.func_177982_a(0, -1, 0))) {
            return new BlockData(pos1.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos1.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos1.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos1.func_177982_a(1, 0, 0))) {
            return new BlockData(pos1.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos1.func_177982_a(0, 0, 1))) {
            return new BlockData(pos1.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos1.func_177982_a(0, 0, -1))) {
            return new BlockData(pos1.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos29 = pos.func_177982_a(2, 0, 0);
        if (this.isPosSolid(pos2.func_177982_a(0, -1, 0))) {
            return new BlockData(pos2.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos2.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.func_177982_a(1, 0, 0))) {
            return new BlockData(pos2.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.func_177982_a(0, 0, 1))) {
            return new BlockData(pos2.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.func_177982_a(0, 0, -1))) {
            return new BlockData(pos2.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos39 = pos.func_177982_a(0, 0, 2);
        if (this.isPosSolid(pos3.func_177982_a(0, -1, 0))) {
            return new BlockData(pos3.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos3.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.func_177982_a(1, 0, 0))) {
            return new BlockData(pos3.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.func_177982_a(0, 0, 1))) {
            return new BlockData(pos3.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.func_177982_a(0, 0, -1))) {
            return new BlockData(pos3.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos49 = pos.func_177982_a(0, 0, -2);
        if (this.isPosSolid(pos4.func_177982_a(0, -1, 0))) {
            return new BlockData(pos4.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos4.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.func_177982_a(1, 0, 0))) {
            return new BlockData(pos4.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.func_177982_a(0, 0, 1))) {
            return new BlockData(pos4.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.func_177982_a(0, 0, -1))) {
            return new BlockData(pos4.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.func_177982_a(0, -1, 0);
        if (this.isPosSolid(pos5.func_177982_a(0, -1, 0))) {
            return new BlockData(pos5.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos5.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos5.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos5.func_177982_a(1, 0, 0))) {
            return new BlockData(pos5.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos5.func_177982_a(0, 0, 1))) {
            return new BlockData(pos5.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos5.func_177982_a(0, 0, -1))) {
            return new BlockData(pos5.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.func_177982_a(1, 0, 0);
        if (this.isPosSolid(pos6.func_177982_a(0, -1, 0))) {
            return new BlockData(pos6.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos6.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos6.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos6.func_177982_a(1, 0, 0))) {
            return new BlockData(pos6.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos6.func_177982_a(0, 0, 1))) {
            return new BlockData(pos6.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos6.func_177982_a(0, 0, -1))) {
            return new BlockData(pos6.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.func_177982_a(-1, 0, 0);
        if (this.isPosSolid(pos7.func_177982_a(0, -1, 0))) {
            return new BlockData(pos7.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos7.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos7.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos7.func_177982_a(1, 0, 0))) {
            return new BlockData(pos7.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos7.func_177982_a(0, 0, 1))) {
            return new BlockData(pos7.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos7.func_177982_a(0, 0, -1))) {
            return new BlockData(pos7.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.func_177982_a(0, 0, 1);
        if (this.isPosSolid(pos8.func_177982_a(0, -1, 0))) {
            return new BlockData(pos8.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos8.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos8.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos8.func_177982_a(1, 0, 0))) {
            return new BlockData(pos8.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos8.func_177982_a(0, 0, 1))) {
            return new BlockData(pos8.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos8.func_177982_a(0, 0, -1))) {
            return new BlockData(pos8.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.func_177982_a(0, 0, -1);
        if (this.isPosSolid(pos9.func_177982_a(0, -1, 0))) {
            return new BlockData(pos9.func_177982_a(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos9.func_177982_a(-1, 0, 0))) {
            return new BlockData(pos9.func_177982_a(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos9.func_177982_a(1, 0, 0))) {
            return new BlockData(pos9.func_177982_a(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos9.func_177982_a(0, 0, 1))) {
            return new BlockData(pos9.func_177982_a(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos9.func_177982_a(0, 0, -1))) {
            return new BlockData(pos9.func_177982_a(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    public static float[] getRotations(BlockPos block, EnumFacing face) {
        double x = (double)block.func_177958_n() + 0.5 - Wrapper.INSTANCE.player().posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.func_177952_p() + 0.5 - Wrapper.INSTANCE.player().posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.func_177956_o() + 0.5;
        double d1 = Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight() - y;
        double d3 = MathHelper.func_76133_a((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
}

