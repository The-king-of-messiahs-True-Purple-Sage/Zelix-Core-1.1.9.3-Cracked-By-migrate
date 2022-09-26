/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockGravel
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockOre
 *  net.minecraft.block.BlockSand
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.MovementInput
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks.automine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockSand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.Search;
import zelix.hack.hacks.automine.AutoMinePathFinder;
import zelix.hack.hacks.automine.BlockUtils;
import zelix.hack.hacks.automine.TimeHelper;
import zelix.hack.hacks.automine.Vec3;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.EnumChatFormatting;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class AutoMine
extends Hack {
    private Minecraft mc = Minecraft.getMinecraft();
    public static BooleanValue showPath = new BooleanValue("ShowPath", true);
    public static NumberValue delay = new NumberValue("DigDelay", 10.0, 0.0, 1000.0);
    public static NumberValue refreshdelay = new NumberValue("RefreshTime(Sec)", 1.0, 0.0, 60.0);
    public static NumberValue YCord = new NumberValue("YCord", 5.0, 3.0, 100.0);
    public static ModeValue mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("Stone", false));
    public BooleanValue dia = new BooleanValue("Diamond", true);
    public BooleanValue gold = new BooleanValue("Gold", false);
    public BooleanValue iron = new BooleanValue("Iron", false);
    public BooleanValue lapis = new BooleanValue("Lapis", false);
    public BooleanValue emerald = new BooleanValue("Emerald", false);
    public BooleanValue coal = new BooleanValue("Coal", false);
    public BooleanValue redstone = new BooleanValue("Redstone", false);
    public BooleanValue quartz = new BooleanValue("Quartz", false);
    private ArrayList<Vec3> path = new ArrayList();
    public static LinkedList<BlockPos> blocks = new LinkedList();
    public static BlockPos readyBreakBlock = null;
    public static volatile boolean start = false;
    public static boolean PathReady = false;
    public static boolean tryTwo = false;
    public static boolean shouldPlaceBlock = false;
    private TimeHelper timeHelper = new TimeHelper();
    private TimeHelper timeHelper2 = new TimeHelper();
    private TimeHelper timeHelper3 = new TimeHelper();
    private TimeHelper timeHelper4 = new TimeHelper();
    private int count = 0;
    public static String keyDown = "";

    public AutoMine() {
        super("AutoMine", HackCategory.PLAYER);
        this.addValue(mode);
        this.addValue(showPath, delay, YCord);
        this.addValue(this.dia);
        this.addValue(this.gold);
        this.addValue(this.iron);
        this.addValue(this.lapis);
        this.addValue(this.emerald);
        this.addValue(this.coal);
        this.addValue(this.redstone);
        this.addValue(this.quartz);
        this.addValue(refreshdelay);
    }

    @Override
    public void onEnable() {
        this.onEnableMod();
    }

    @Override
    public void onDisable() {
        this.onDisableMod();
    }

    public void onEnableMod() {
        if (!this.hasBlock()) {
            this.onDisableMod();
            ChatUtils.message((Object)((Object)EnumChatFormatting.RED) + "\u8bf7\u653e\u4e9b\u65b9\u5757\u5728\u7269\u54c1\u680f\uff0c\u4ee5\u4fbf\u4e0a\u5347\u65f6\u57ab\u811a\uff01");
            return;
        }
        this.resetAllOptions();
        if (HackManager.getHack("Search").isToggled()) {
            for (BlockPos pos2 : Search.toRender) {
                int blockPosY;
                Block bId = BlockUtils.getBlock(pos2);
                if (bId instanceof BlockAir || BlockUtils.getBlockMeta(pos2) != 0 || !this.isTarget(pos2)) continue;
                int x = pos2.func_177958_n();
                int y = pos2.func_177956_o();
                int z = pos2.func_177952_p();
                boolean canBreak = true;
                boolean isLiquid = false;
                if (BlockUtils.getBlock(new BlockPos(x, y + 1, z)) instanceof BlockGravel) {
                    canBreak = false;
                }
                if (!AutoMinePathFinder.isSafeToWalkOn(new BlockPos(x, y - 1, z)) && BlockUtils.getBlock(new BlockPos(x, y - 3, z)) instanceof BlockAir) {
                    canBreak = false;
                }
                for (blockPosY = 1; blockPosY <= 3; ++blockPosY) {
                    if (!(BlockUtils.getBlock(new BlockPos(x, y - blockPosY, z)) instanceof BlockLiquid)) continue;
                    canBreak = false;
                    isLiquid = true;
                }
                if (!isLiquid) {
                    for (blockPosY = 1; blockPosY <= 3; ++blockPosY) {
                        if (BlockUtils.getBlock(new BlockPos(x, y - blockPosY, z)) instanceof BlockLiquid || !AutoMinePathFinder.isSafeToWalkOn(new BlockPos(x, y - blockPosY, z))) continue;
                        canBreak = true;
                    }
                }
                block3: for (int x1 = x - 2; x1 <= x + 2; ++x1) {
                    for (int y1 = y; y1 <= y + 2; ++y1) {
                        for (int z1 = z - 2; z1 <= z + 2; ++z1) {
                            if (!(BlockUtils.getBlock(new BlockPos(x1, y1, z1)) instanceof BlockLiquid)) continue;
                            canBreak = false;
                            break block3;
                        }
                    }
                }
                if (!canBreak) continue;
                blocks.add(pos2);
            }
        } else {
            for (int i = (int)(Wrapper.INSTANCE.mc().thePlayer.posX - 50.0); i <= (int)(Wrapper.INSTANCE.mc().thePlayer.posX + 50.0); ++i) {
                for (int j = (int)(Wrapper.INSTANCE.mc().thePlayer.posZ - 50.0); j <= (int)(Wrapper.INSTANCE.mc().thePlayer.posZ + 50.0); ++j) {
                    for (int k = 5; k <= Wrapper.INSTANCE.mc().theWorld.getHeight(); ++k) {
                        int blockPosY;
                        BlockPos pos3 = new BlockPos(i, k, j);
                        Block bId = BlockUtils.getBlock(pos3);
                        if (bId instanceof BlockAir || BlockUtils.getBlockMeta(pos3) != 0 || !this.isTarget(pos3)) continue;
                        int x = pos3.func_177958_n();
                        int y = pos3.func_177956_o();
                        int z = pos3.func_177952_p();
                        boolean canBreak = true;
                        boolean isLiquid = false;
                        if (BlockUtils.getBlock(new BlockPos(x, y + 1, z)) instanceof BlockGravel) {
                            canBreak = false;
                        }
                        if (!AutoMinePathFinder.isSafeToWalkOn(new BlockPos(x, y - 1, z)) && BlockUtils.getBlock(new BlockPos(x, y - 3, z)) instanceof BlockAir) {
                            canBreak = false;
                        }
                        for (blockPosY = 1; blockPosY <= 3; ++blockPosY) {
                            if (!(BlockUtils.getBlock(new BlockPos(x, y - blockPosY, z)) instanceof BlockLiquid)) continue;
                            canBreak = false;
                            isLiquid = true;
                        }
                        if (!isLiquid) {
                            for (blockPosY = 1; blockPosY <= 3; ++blockPosY) {
                                if (BlockUtils.getBlock(new BlockPos(x, y - blockPosY, z)) instanceof BlockLiquid || !AutoMinePathFinder.isSafeToWalkOn(new BlockPos(x, y - blockPosY, z))) continue;
                                canBreak = true;
                            }
                        }
                        block11: for (int x1 = x - 2; x1 <= x + 2; ++x1) {
                            for (int y1 = y; y1 <= y + 2; ++y1) {
                                for (int z1 = z - 2; z1 <= z + 2; ++z1) {
                                    if (!(BlockUtils.getBlock(new BlockPos(x1, y1, z1)) instanceof BlockLiquid)) continue;
                                    canBreak = false;
                                    break block11;
                                }
                            }
                        }
                        if (!canBreak) continue;
                        blocks.add(pos3);
                    }
                }
            }
        }
        if (!blocks.isEmpty()) {
            blocks.sort(Comparator.comparingDouble(pos -> BlockUtils.getBlockDistance((float)(Wrapper.INSTANCE.mc().thePlayer.posX - (double)pos.func_177958_n()), (float)(Wrapper.INSTANCE.mc().thePlayer.posY - (double)pos.func_177956_o()), (float)(Wrapper.INSTANCE.mc().thePlayer.posZ - (double)pos.func_177952_p()))));
            start = true;
        }
        new Thread(){

            @Override
            public void run() {
                while (!start) {
                    if (Wrapper.INSTANCE.mc().theWorld != null) continue;
                    return;
                }
                if (Wrapper.INSTANCE.mc().theWorld == null) {
                    return;
                }
                readyBreakBlock = blocks.get(0);
                if (BlockUtils.getNeaestPlayerBlockDistance(readyBreakBlock.func_177958_n(), readyBreakBlock.func_177956_o(), readyBreakBlock.func_177952_p()) > 2.0f) {
                    Vec3 topFrom = new Vec3(Wrapper.INSTANCE.mc().thePlayer.posX, Wrapper.INSTANCE.mc().thePlayer.posY, Wrapper.INSTANCE.mc().thePlayer.posZ);
                    Vec3 to = new Vec3(readyBreakBlock.func_177958_n(), readyBreakBlock.func_177956_o(), readyBreakBlock.func_177952_p());
                    AutoMine.this.path = AutoMine.this.computePath(topFrom, to, 8);
                    if (((Vec3)AutoMine.this.path.get(AutoMine.this.path.size() - 1)).squareDistanceTo(to) > 0.25) {
                        tryTwo = true;
                        Vec3 twice = (Vec3)AutoMine.this.path.get(AutoMine.this.path.size() - 1);
                        for (Vec3 vec3 : AutoMine.this.computePath(twice, to, 0)) {
                            AutoMine.this.path.add(vec3);
                        }
                    }
                    tryTwo = false;
                }
                PathReady = true;
            }
        }.start();
    }

    public void onDisableMod() {
        this.resetAllOptions();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        block40: {
            block37: {
                Vec3 player;
                BlockPos pos;
                block39: {
                    int posZ;
                    int posY;
                    int posX;
                    block38: {
                        if (!start || !PathReady) break block37;
                        if (shouldPlaceBlock) {
                            Wrapper.INSTANCE.mc().thePlayer.rotationPitch = 90.0f;
                            this.placeBlock();
                        }
                        if (readyBreakBlock != null && BlockUtils.getBlock(readyBreakBlock) == Block.getBlockById((int)0) || Wrapper.INSTANCE.mc().thePlayer.isDead) {
                            new Thread(){

                                @Override
                                public void run() {
                                    try {
                                        AutoMine.this.onDisableMod();
                                        2.sleep(500L);
                                        AutoMine.this.onEnableMod();
                                        return;
                                    }
                                    catch (Exception exception) {
                                        return;
                                    }
                                }
                            }.start();
                            return;
                        }
                        if (Wrapper.INSTANCE.mc().thePlayer.motionX == 0.0 && (Wrapper.INSTANCE.mc().thePlayer.motionY == -0.1552320045166016 || Wrapper.INSTANCE.mc().thePlayer.motionY == -0.0784000015258789) && Wrapper.INSTANCE.mc().thePlayer.motionZ == 0.0) {
                            if (this.timeHelper2.hasReach(10000.0)) {
                                new Thread(){

                                    @Override
                                    public void run() {
                                        try {
                                            AutoMine.this.onDisableMod();
                                            3.sleep(1000L);
                                            AutoMine.this.onEnableMod();
                                            keyDown = "W";
                                            3.sleep(1000L);
                                            keyDown = "";
                                            return;
                                        }
                                        catch (Exception exception) {
                                            return;
                                        }
                                    }
                                }.start();
                            }
                        } else {
                            this.timeHelper2.reset();
                        }
                        if (!this.timeHelper4.hasReach((Double)delay.getValue())) {
                            return;
                        }
                        posX = (int)Wrapper.INSTANCE.mc().thePlayer.posX;
                        posY = (int)Wrapper.INSTANCE.mc().thePlayer.posY;
                        posZ = (int)Wrapper.INSTANCE.mc().thePlayer.posZ;
                        pos = Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a();
                        if (this.path.size() < 1) break block38;
                        Vec3 vec3 = new Vec3(posX, posY, posZ);
                        if (!(this.path.get(this.path.size() - 1).squareDistanceTo(vec3) < 1.51)) break block39;
                    }
                    for (int x1 = posX - 2; x1 <= posX + 2; ++x1) {
                        for (int y1 = posY - 1; y1 <= posY + 2; ++y1) {
                            for (int z1 = posZ - 2; z1 <= posZ + 2; ++z1) {
                                if (BlockUtils.getBlock(readyBreakBlock) != BlockUtils.getBlock(new BlockPos(x1, y1, z1))) continue;
                                BlockUtils.faceBlockClient(readyBreakBlock);
                                Wrapper.INSTANCE.mc().thePlayer.rotationYaw += 0.5f;
                                this.breakBlockWithHand(pos);
                                return;
                            }
                        }
                    }
                    if (BlockUtils.getNeaestPlayerBlockDistance(readyBreakBlock.func_177958_n(), readyBreakBlock.func_177956_o(), readyBreakBlock.func_177952_p()) < 2.0f) {
                        BlockUtils.faceBlockClient(readyBreakBlock);
                        Wrapper.INSTANCE.mc().thePlayer.rotationYaw += 0.5f;
                        this.breakBlockWithHand(pos);
                        return;
                    }
                }
                if (BlockUtils.getBlock(new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer)) instanceof BlockGravel) {
                    this.breakBlockWithHand(pos);
                    return;
                }
                Vec3 vec3 = this.path.get(this.count < this.path.size() - 1 ? this.count : this.path.size() - 1);
                if (vec3.squareDistanceTo(player = new Vec3(Wrapper.INSTANCE.mc().thePlayer.posX, Wrapper.INSTANCE.mc().thePlayer.posY + 0.5, Wrapper.INSTANCE.mc().thePlayer.posZ)) < 0.4) {
                    keyDown = "";
                    ++this.count;
                } else if (Wrapper.INSTANCE.mc().thePlayer.posY == vec3.getY()) {
                    AutoMine.faceEntity(vec3.getX(), vec3.getY(), vec3.getZ());
                    if (BlockUtils.getNeaestPlayerBlockDistance(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) < 1.0f && (Wrapper.INSTANCE.mc().objectMouseOver != null && BlockUtils.getBlock(Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a()) instanceof BlockSand || BlockUtils.getBlock(Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a()) instanceof BlockGravel)) {
                        keyDown = "";
                        this.timeHelper.reset();
                    } else if (this.timeHelper.hasReach(800.0)) {
                        keyDown = "W";
                    }
                    if ((double)BlockUtils.getNeaestPlayerBlockDistance(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) < 1.25) {
                        keyDown = "";
                        this.breakBlockWithHand(pos);
                    } else {
                        keyDown = "W";
                    }
                } else if (vec3.getY() > Wrapper.INSTANCE.mc().thePlayer.posY) {
                    keyDown = "W";
                    if (BlockUtils.getBlock(new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer).func_177981_b(2)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer).func_177977_b()) instanceof BlockAir) {
                        if (!this.hasBlock()) {
                            this.onDisableMod();
                            ChatUtils.message("\u00a7c\u7269\u54c1\u680f\u7684\u8e2e\u811a\u65b9\u5757\u6ca1\u4e86\uff01\uff01\uff01\u8d76\u7d27\u8865\u4e0a\uff01");
                            return;
                        }
                        shouldPlaceBlock = true;
                        keyDown = "SPACE";
                    } else if (!(BlockUtils.getBlock(new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer).func_177981_b(2)) instanceof BlockAir) && !(BlockUtils.getBlock(new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer).func_177977_b()) instanceof BlockAir)) {
                        AutoMine.faceEntity(vec3.getX(), vec3.getY() + 1.0, vec3.getZ());
                        if (BlockUtils.getNeaestPlayerBlockDistance(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) < 2.0f) {
                            this.breakBlockWithHand(pos);
                        }
                        keyDown = "";
                    }
                } else if (vec3.getY() < Wrapper.INSTANCE.mc().thePlayer.posY) {
                    keyDown = "";
                    AutoMine.faceEntity(vec3.getX(), vec3.getY() - 1.0, vec3.getZ());
                    if (BlockUtils.getNeaestPlayerBlockDistance(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) > 2.0f) {
                        if (this.timeHelper3.hasReach(1000.0)) {
                            keyDown = "W";
                        }
                    } else {
                        this.timeHelper3.reset();
                    }
                    if (BlockUtils.getNeaestPlayerBlockDistance(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()) < 2.0f) {
                        this.timeHelper3.reset();
                        this.breakBlockWithHand(pos);
                    }
                }
                if (shouldPlaceBlock) {
                    Wrapper.INSTANCE.mc().thePlayer.rotationPitch = 90.0f;
                    this.placeBlock();
                }
                break block40;
            }
            this.count = 0;
        }
    }

    @Override
    public void onInputUpdate(InputUpdateEvent event) {
        if (keyDown.equalsIgnoreCase("")) {
            return;
        }
        MovementInput input = event.getMovementInput();
        input.moveStrafe = 0.0f;
        input.field_192832_b = 0.0f;
        if (keyDown.equalsIgnoreCase("W")) {
            input.field_192832_b += 1.0f;
        }
        if (keyDown.equalsIgnoreCase("S")) {
            input.field_192832_b -= 1.0f;
        }
        if (keyDown.equalsIgnoreCase("A")) {
            input.moveStrafe += 1.0f;
        }
        if (keyDown.equalsIgnoreCase("D")) {
            input.moveStrafe -= 1.0f;
        }
        input.jump = keyDown.equalsIgnoreCase("SPACE");
        input.sneak = keyDown.equalsIgnoreCase("SHIFT");
        if (input.sneak) {
            input.moveStrafe = (float)((double)input.moveStrafe * 0.3);
            input.field_192832_b = (float)((double)input.field_192832_b * 0.3);
        }
        input.moveStrafe *= 5.0f;
        input.field_192832_b *= 5.0f;
    }

    public static void faceEntity(double xp, double yp, double zp) {
        Minecraft mc = Minecraft.getMinecraft();
        double x = xp - Wrapper.INSTANCE.mc().thePlayer.posX;
        double y = yp - Wrapper.INSTANCE.mc().thePlayer.posY;
        double z = zp - Wrapper.INSTANCE.mc().thePlayer.posZ;
        double d1 = Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight() - yp - 0.8;
        double d3 = Math.sqrt(x * x + z * z);
        float f = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float f1 = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        Wrapper.INSTANCE.mc().thePlayer.rotationYaw = f;
        Wrapper.INSTANCE.mc().thePlayer.rotationPitch = f1;
    }

    public void placeBlock() {
        BlockPos blockDown = new BlockPos((Entity)Wrapper.INSTANCE.mc().thePlayer).func_177977_b();
        if (!BlockUtils.getBlock(blockDown).func_149688_o(zelix.utils.BlockUtils.getState(blockDown)).isReplaceable()) {
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Wrapper.INSTANCE.mc().thePlayer.inventory.getStackInSlot(i);
            if (!(stack.getItem() instanceof ItemBlock) || ((ItemBlock)stack.getItem()).getBlock() instanceof BlockOre) continue;
            newSlot = i;
        }
        if (newSlot == -1) {
            return;
        }
        int oldSlot = Wrapper.INSTANCE.mc().thePlayer.inventory.currentItem;
        Wrapper.INSTANCE.mc().thePlayer.inventory.currentItem = newSlot;
        shouldPlaceBlock = true;
        Vec3d eyesPos = new Vec3d(Wrapper.INSTANCE.mc().thePlayer.posX, Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight(), Wrapper.INSTANCE.mc().thePlayer.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec;
            BlockPos neighbor = blockDown.func_177972_a(side);
            EnumFacing side2 = side.getOpposite();
            Vec3d vec3d = new Vec3d((Vec3i)blockDown);
            Vec3d vec3d2 = new Vec3d((Vec3i)neighbor);
            if (!(eyesPos.func_72436_e(vec3d.func_72441_c(0.5, 0.5, 0.5)) < eyesPos.func_72436_e(vec3d2.func_72441_c(0.5, 0.5, 0.5))) || !BlockUtils.getBlock(neighbor).canCollideCheck(Wrapper.INSTANCE.mc().theWorld.func_180495_p(neighbor), false) || !(eyesPos.func_72436_e(hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5))) <= 18.0625)) continue;
            Wrapper.INSTANCE.mc().thePlayer.func_184609_a(EnumHand.MAIN_HAND);
            Wrapper.INSTANCE.controller().func_187099_a(Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().theWorld, neighbor, side2, new Vec3d(hitVec.field_72450_a, hitVec.field_72448_b, hitVec.field_72449_c), EnumHand.MAIN_HAND);
            for (String fieldName : new String[]{"field_78781_i", "blockHitDelay"}) {
                try {
                    Field f = PlayerControllerMP.class.getDeclaredField(fieldName);
                    f.setAccessible(true);
                    f.set(Wrapper.INSTANCE.controller(), 4);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            shouldPlaceBlock = false;
        }
        Wrapper.INSTANCE.mc().thePlayer.inventory.currentItem = oldSlot;
    }

    public static void glColor(int color) {
        GL11.glColor4f((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)(color >> 24 & 0xFF) / 255.0f));
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
        if (Blocks.quartz_ore.equals(block)) {
            return (Boolean)this.quartz.getValue();
        }
        return false;
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!this.path.isEmpty() && start) {
            try {
                if (((Boolean)showPath.getValue()).booleanValue()) {
                    Search.renderBlock(readyBreakBlock, event);
                    GL11.glPushMatrix();
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glShadeModel((int)7425);
                    GL11.glDisable((int)3553);
                    GL11.glEnable((int)2848);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)2896);
                    GL11.glDepthMask((boolean)false);
                    GL11.glHint((int)3154, (int)4354);
                    AutoMine.glColor(-131);
                    GL11.glPushMatrix();
                    GL11.glLineWidth((float)2.5f);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glEnable((int)2848);
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2929);
                    GL11.glBegin((int)3);
                    for (Vec3 pos : this.path) {
                        GL11.glVertex3d((double)(pos.getX() - Wrapper.INSTANCE.mc().thePlayer.posX), (double)(pos.getY() - Wrapper.INSTANCE.mc().thePlayer.posY), (double)(pos.getZ() - Wrapper.INSTANCE.mc().thePlayer.posZ));
                    }
                    GL11.glEnd();
                    GL11.glEnable((int)2929);
                    GL11.glDisable((int)2848);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)3553);
                    GL11.glEnable((int)2896);
                    GL11.glPopMatrix();
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)2929);
                    GL11.glDisable((int)2848);
                    GL11.glEnable((int)3553);
                    GL11.glDisable((int)3042);
                    GL11.glPopMatrix();
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private ArrayList<Vec3> computePath(Vec3 topFrom, Vec3 to, int everyDistance) {
        AutoMinePathFinder pathfinder = new AutoMinePathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<Vec3>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > (double)(everyDistance * everyDistance)) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    int x = (int)smallX;
                    block1: while ((double)x <= bigX) {
                        int y = (int)smallY;
                        while ((double)y <= bigY) {
                            int z = (int)smallZ;
                            while ((double)z <= bigZ) {
                                if (!AutoMinePathFinder.checkPositionValidity(x, y, z, true)) {
                                    canContinue = false;
                                    break block1;
                                }
                                ++z;
                            }
                            ++y;
                        }
                        ++x;
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }

    private void breakBlockWithHand(BlockPos pos) {
        if (Wrapper.INSTANCE.mc().objectMouseOver != null) {
            if (Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a == RayTraceResult.Type.BLOCK) {
                Block block = BlockUtils.getBlock(Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a());
                if (block != null && block != Block.getBlockById((int)0)) {
                    float ChangSpeed = 1.0f;
                    int Item2 = -1;
                    for (int SelectItem = 0; SelectItem < 9; ++SelectItem) {
                        float speed;
                        ItemStack stack = Wrapper.INSTANCE.mc().thePlayer.inventory.getStackInSlot(SelectItem);
                        if (stack == null || !((speed = stack.getItem().func_150893_a(stack, Block.getStateById((int)Block.getIdFromBlock((Block)block)))) > ChangSpeed)) continue;
                        ChangSpeed = speed;
                        Item2 = SelectItem;
                    }
                    if (Item2 != -1) {
                        Wrapper.INSTANCE.mc().thePlayer.inventory.currentItem = Item2;
                    }
                }
            } else if (Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a == RayTraceResult.Type.ENTITY) {
                int itemIndex;
                NonNullList inventory = Wrapper.INSTANCE.mc().thePlayer.inventory.mainInventory;
                int currentBest = 0;
                int diamond = -1;
                int iron = -1;
                int stone = -1;
                int wood = -1;
                while (currentBest < 9) {
                    if (inventory.get(currentBest) == null) {
                        ++currentBest;
                        continue;
                    }
                    Item item = ((ItemStack)inventory.get(currentBest)).getItem();
                    if (item instanceof ItemSword) {
                        int id = Item.getIdFromItem((Item)item);
                        if (id == 276) {
                            diamond = currentBest;
                        }
                        if (id == 267) {
                            iron = currentBest;
                        }
                        if (id == 272) {
                            stone = currentBest;
                        }
                        if (id == 268) {
                            wood = currentBest;
                        }
                    }
                    ++currentBest;
                }
                int n = diamond >= 0 ? diamond : (iron >= 0 ? iron : (stone >= 0 ? stone : (itemIndex = wood >= 0 ? wood : -1)));
                if (itemIndex != -1) {
                    Wrapper.INSTANCE.mc().thePlayer.inventory.currentItem = itemIndex;
                }
            }
        }
        Wrapper.INSTANCE.mc().thePlayer.func_184609_a(EnumHand.MAIN_HAND);
        Wrapper.INSTANCE.controller().func_180512_c(pos, AutoMine.getDirectionFromEntityLiving(pos, (EntityLivingBase)Wrapper.INSTANCE.mc().thePlayer));
    }

    private boolean hasBlock() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Wrapper.INSTANCE.mc().thePlayer.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || Block.getBlockFromItem((Item)stack.getItem()) instanceof BlockOre) continue;
            return true;
        }
        return false;
    }

    private void resetAllOptions() {
        this.path.clear();
        blocks.clear();
        readyBreakBlock = null;
        start = false;
        PathReady = false;
        shouldPlaceBlock = false;
        this.count = 0;
        tryTwo = false;
        this.timeHelper.reset();
        this.timeHelper2.reset();
        this.timeHelper3.reset();
        this.timeHelper4.reset();
        keyDown = "";
    }

    public static EnumFacing getDirectionFromEntityLiving(BlockPos pos, EntityLivingBase placer) {
        if (Math.abs(placer.posX - (double)((float)pos.func_177958_n() + 0.5f)) < 2.0 && Math.abs(placer.posZ - (double)((float)pos.func_177952_p() + 0.5f)) < 2.0) {
            double d0 = placer.posY + (double)placer.getEyeHeight();
            if (d0 - (double)pos.func_177956_o() > 2.0) {
                return EnumFacing.UP;
            }
            if ((double)pos.func_177956_o() - d0 > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        return placer.getHorizontalFacing().getOpposite();
    }
}

