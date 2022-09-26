/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.BlockButton
 *  net.minecraft.block.BlockCake
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.BlockDaylightDetector
 *  net.minecraft.block.BlockDoublePlant
 *  net.minecraft.block.BlockEndPortalFrame
 *  net.minecraft.block.BlockFlower
 *  net.minecraft.block.BlockLilyPad
 *  net.minecraft.block.BlockMushroom
 *  net.minecraft.block.BlockPressurePlate
 *  net.minecraft.block.BlockRedstoneComparator
 *  net.minecraft.block.BlockRedstoneRepeater
 *  net.minecraft.block.BlockRedstoneTorch
 *  net.minecraft.block.BlockReed
 *  net.minecraft.block.BlockSapling
 *  net.minecraft.block.BlockSign
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.block.BlockTripWire
 *  net.minecraft.block.BlockTripWireHook
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.PlayerControllerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Teleport
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Reach", true), new Mode("Flight", false));
    public BooleanValue math = new BooleanValue("Math", false);
    public boolean passPacket = false;
    private BlockPos teleportPosition = null;
    private boolean canDraw;
    private int delay;
    float reach = 0.0f;

    public Teleport() {
        super("Teleport", HackCategory.PLAYER);
        this.addValue(this.mode, this.math);
    }

    @Override
    public String getDescription() {
        return "Teleports you on selected block.";
    }

    @Override
    public void onEnable() {
        if (this.mode.getMode("Reach").isToggled()) {
            this.reach = (float)Wrapper.INSTANCE.player().getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.mode.getMode("Flight").isToggled()) {
            Wrapper.INSTANCE.player().noClip = false;
            this.passPacket = false;
            this.teleportPosition = null;
            return;
        }
        this.canDraw = false;
        PlayerControllerUtils.setReach((Entity)Wrapper.INSTANCE.player(), this.reach);
        super.onDisable();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && this.mode.getMode("Flight").isToggled() && (packet instanceof CPacketPlayer || packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation)) {
            return this.passPacket;
        }
        return true;
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("Flight").isToggled()) {
            RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
            if (object == null) {
                return;
            }
            EntityPlayerSP player = Wrapper.INSTANCE.player();
            GameSettings settings = Wrapper.INSTANCE.mcSettings();
            if (!this.passPacket) {
                if (settings.keyBindAttack.isKeyDown() && object.field_72313_a == RayTraceResult.Type.BLOCK) {
                    if (BlockUtils.isBlockMaterial(object.func_178782_a(), Blocks.air)) {
                        return;
                    }
                    this.teleportPosition = object.func_178782_a();
                    this.passPacket = true;
                }
                return;
            }
            player.noClip = false;
            if (settings.keyBindSneak.isKeyDown() && player.onGround) {
                if (((Boolean)this.math.getValue()).booleanValue()) {
                    double[] playerPosition = new double[]{Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ};
                    double[] blockPosition = new double[]{(float)this.teleportPosition.func_177958_n() + 0.5f, (double)this.teleportPosition.func_177956_o() + this.getOffset(BlockUtils.getBlock(this.teleportPosition), this.teleportPosition) + 1.0, (float)this.teleportPosition.func_177952_p() + 0.5f};
                    Utils.teleportToPosition(playerPosition, blockPosition, 0.25, 0.0, true, true);
                    Wrapper.INSTANCE.player().setPosition(blockPosition[0], blockPosition[1], blockPosition[2]);
                    this.teleportPosition = null;
                } else {
                    double x = this.teleportPosition.func_177958_n();
                    double y = this.teleportPosition.func_177956_o() + 1;
                    double z = this.teleportPosition.func_177952_p();
                    Wrapper.INSTANCE.player().setPosition(x, y, z);
                    for (int i = 0; i < 1; ++i) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, Wrapper.INSTANCE.player().onGround));
                    }
                }
            }
            return;
        }
        if ((!Mouse.isButtonDown((int)0) && Wrapper.INSTANCE.mc().inGameHasFocus || !Wrapper.INSTANCE.mc().inGameHasFocus) && Wrapper.INSTANCE.player().func_184605_cv() == 0) {
            PlayerControllerUtils.setReach((Entity)Wrapper.INSTANCE.player(), 100.0);
            this.canDraw = true;
        } else {
            this.canDraw = false;
            PlayerControllerUtils.setReach((Entity)Wrapper.INSTANCE.player(), this.reach);
        }
        if (this.teleportPosition != null && this.delay == 0 && Mouse.isButtonDown((int)1)) {
            if (((Boolean)this.math.getValue()).booleanValue()) {
                double[] playerPosition = new double[]{Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ};
                double[] blockPosition = new double[]{(float)this.teleportPosition.func_177958_n() + 0.5f, (double)this.teleportPosition.func_177956_o() + this.getOffset(BlockUtils.getBlock(this.teleportPosition), this.teleportPosition) + 1.0, (float)this.teleportPosition.func_177952_p() + 0.5f};
                Utils.teleportToPosition(playerPosition, blockPosition, 0.25, 0.0, true, true);
                Wrapper.INSTANCE.player().setPosition(blockPosition[0], blockPosition[1], blockPosition[2]);
                this.teleportPosition = null;
            } else {
                double x = this.teleportPosition.func_177958_n();
                double y = this.teleportPosition.func_177956_o() + 1;
                double z = this.teleportPosition.func_177952_p();
                Wrapper.INSTANCE.player().setPosition(x, y, z);
                for (int i = 0; i < 1; ++i) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, Wrapper.INSTANCE.player().onGround));
                }
            }
            this.delay = 5;
        }
        if (this.delay > 0) {
            --this.delay;
        }
        super.onClientTick(event);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!this.mode.getMode("Flight").isToggled()) {
            return;
        }
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        GameSettings settings = Wrapper.INSTANCE.mcSettings();
        if (!this.passPacket) {
            player.noClip = true;
            player.fallDistance = 0.0f;
            player.onGround = true;
            player.capabilities.isFlying = false;
            player.motionX = 0.0;
            player.motionY = 0.0;
            player.motionZ = 0.0;
            float speed = 0.5f;
            if (settings.keyBindJump.isKeyDown()) {
                player.motionY += (double)speed;
            }
            if (settings.keyBindSneak.isKeyDown()) {
                player.motionY -= (double)speed;
            }
            double d5 = player.rotationPitch + 90.0f;
            double d7 = player.rotationYaw + 90.0f;
            boolean flag4 = settings.keyBindForward.isKeyDown();
            boolean flag6 = settings.keyBindBack.isKeyDown();
            boolean flag8 = settings.keyBindLeft.isKeyDown();
            boolean flag10 = settings.keyBindRight.isKeyDown();
            if (flag4) {
                if (flag8) {
                    d7 -= 45.0;
                } else if (flag10) {
                    d7 += 45.0;
                }
            } else if (flag6) {
                d7 += 180.0;
                if (flag8) {
                    d7 += 45.0;
                } else if (flag10) {
                    d7 -= 45.0;
                }
            } else if (flag8) {
                d7 -= 90.0;
            } else if (flag10) {
                d7 += 90.0;
            }
            if (flag4 || flag8 || flag6 || flag10) {
                player.motionX = Math.cos(Math.toRadians(d7));
                player.motionZ = Math.sin(Math.toRadians(d7));
            }
        }
        super.onLivingUpdate(event);
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.mode.getMode("Flight").isToggled()) {
            if (this.teleportPosition == null) {
                return;
            }
            if (this.teleportPosition.func_177956_o() == new BlockPos((Entity)Wrapper.INSTANCE.player()).func_177977_b().func_177956_o()) {
                RenderUtils.drawBlockESP(this.teleportPosition, 1.0f, 0.0f, 1.0f);
                return;
            }
            RenderUtils.drawBlockESP(this.teleportPosition, 1.0f, 0.0f, 0.0f);
            return;
        }
        RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
        if (object == null) {
            return;
        }
        if (object.func_178782_a() != null && this.canDraw) {
            for (float offset = -2.0f; offset < 18.0f; offset += 1.0f) {
                double[] mouseOverPos = new double[]{object.func_178782_a().func_177958_n(), (float)object.func_178782_a().func_177956_o() + offset, object.func_178782_a().func_177952_p()};
                BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                Block blockBelow = BlockUtils.getBlock(blockBelowPos);
                if (!this.canRenderBox(mouseOverPos)) continue;
                RenderUtils.drawBlockESP(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]), 1.0f, 0.0f, 1.0f);
                if (Wrapper.INSTANCE.mc().inGameHasFocus) {
                    this.teleportPosition = blockBelowPos;
                    break;
                }
                this.teleportPosition = null;
            }
        } else if (object.field_72308_g != null) {
            for (float offset = -2.0f; offset < 18.0f; offset += 1.0f) {
                double[] mouseOverPos = new double[]{object.field_72308_g.posX, object.field_72308_g.posY + (double)offset, object.field_72308_g.posZ};
                BlockPos blockBelowPos = new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]);
                Block blockBelow = BlockUtils.getBlock(blockBelowPos);
                if (!this.canRenderBox(mouseOverPos)) continue;
                RenderUtils.drawBlockESP(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]), 1.0f, 0.0f, 1.0f);
                if (Wrapper.INSTANCE.mc().inGameHasFocus) {
                    this.teleportPosition = blockBelowPos;
                    break;
                }
                this.teleportPosition = null;
            }
        } else {
            this.teleportPosition = null;
        }
        super.onRenderWorldLast(event);
    }

    public boolean canRenderBox(double[] mouseOverPos) {
        boolean canTeleport = false;
        Block blockBelowPos = BlockUtils.getBlock(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2]));
        Block blockPos = BlockUtils.getBlock(new BlockPos(mouseOverPos[0], mouseOverPos[1], mouseOverPos[2]));
        Block blockAbovePos = BlockUtils.getBlock(new BlockPos(mouseOverPos[0], mouseOverPos[1] + 1.0, mouseOverPos[2]));
        boolean validBlockBelow = blockBelowPos.func_180646_a(BlockUtils.getState(new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])), (IBlockAccess)Wrapper.INSTANCE.world(), new BlockPos(mouseOverPos[0], mouseOverPos[1] - 1.0, mouseOverPos[2])) != null;
        boolean validBlock = this.isValidBlock(blockPos);
        boolean validBlockAbove = this.isValidBlock(blockAbovePos);
        if (validBlockBelow && validBlock && validBlockAbove) {
            canTeleport = true;
        }
        return canTeleport;
    }

    public double getOffset(Block block, BlockPos pos) {
        IBlockState state = BlockUtils.getState(pos);
        double offset = 0.0;
        if (block instanceof BlockSlab && !((BlockSlab)block).isDouble()) {
            offset -= 0.5;
        } else if (block instanceof BlockEndPortalFrame) {
            offset -= (double)0.2f;
        } else if (block instanceof BlockBed) {
            offset -= (double)0.44f;
        } else if (block instanceof BlockCake) {
            offset -= 0.5;
        } else if (block instanceof BlockDaylightDetector) {
            offset -= 0.625;
        } else if (block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater) {
            offset -= 0.875;
        } else if (block instanceof BlockChest || block == Blocks.ender_chest) {
            offset -= 0.125;
        } else if (block instanceof BlockLilyPad) {
            offset -= (double)0.95f;
        } else if (block == Blocks.snow_layer) {
            offset -= 0.875;
            offset += (double)(0.125f * (float)((Integer)state.getValue((IProperty)BlockSnow.LAYERS) - 1));
        } else if (this.isValidBlock(block)) {
            offset -= 1.0;
        }
        return offset;
    }

    public boolean isValidBlock(Block block) {
        return block == Blocks.portal || block == Blocks.snow_layer || block instanceof BlockTripWireHook || block instanceof BlockTripWire || block instanceof BlockDaylightDetector || block instanceof BlockRedstoneComparator || block instanceof BlockRedstoneRepeater || block instanceof BlockSign || block instanceof BlockAir || block instanceof BlockPressurePlate || block instanceof BlockTallGrass || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockDoublePlant || block instanceof BlockReed || block instanceof BlockSapling || block == Blocks.carrots || block == Blocks.wheat || block == Blocks.nether_wart || block == Blocks.potatoes || block == Blocks.pumpkin_stem || block == Blocks.melon_stem || block == Blocks.heavy_weighted_pressure_plate || block == Blocks.light_weighted_pressure_plate || block == Blocks.redstone_wire || block instanceof BlockTorch || block instanceof BlockRedstoneTorch || block == Blocks.lever || block instanceof BlockButton;
    }
}

