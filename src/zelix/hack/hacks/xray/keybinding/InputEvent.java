/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 */
package zelix.hack.hacks.xray.keybinding;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiSelectionScreen;
import zelix.hack.hacks.xray.keybinding.KeyBindings;
import zelix.hack.hacks.xray.xray.AntiAntiXray;
import zelix.hack.hacks.xray.xray.Controller;

public class InputEvent {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class) && XRay.mc.currentScreen == null && XRay.mc.theWorld != null) {
            if (KeyBindings.keyBind_keys[0].isPressed()) {
                Controller.toggleDrawOres();
            } else if (KeyBindings.keyBind_keys[1].isPressed()) {
                XRay.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
            } else if (KeyBindings.keyBind_keys[2].isPressed()) {
                assert (Minecraft.getMinecraft().thePlayer != null);
                Minecraft.getMinecraft().thePlayer.func_146105_b((ITextComponent)new TextComponentString("\u00a76[ \u00a7a\uff01 \u00a76] \u00a7fRefreshing blocks..."), true);
                AntiAntiXray.revealNewBlocks(Configuration.radius_x, Configuration.radius_y, Configuration.radius_z, Configuration.delay);
            } else if (KeyBindings.keyBind_keys[3].isPressed()) {
                Minecraft.getMinecraft().gameSettings.gammaSetting = 114514.0f;
                for (int cx = -2; cx <= 2; ++cx) {
                    for (int cy = 0; cy <= 2; ++cy) {
                        for (int cz = -2; cz <= 2; ++cz) {
                            assert (Minecraft.getMinecraft().thePlayer != null);
                            BlockPos b2r = Minecraft.getMinecraft().thePlayer.func_180425_c();
                            Block s = Block.getBlockFromItem((Item)Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem());
                            IBlockState b = Blocks.air.getDefaultState();
                            if (s != null) {
                                b = s.getDefaultState();
                            }
                            Minecraft.getMinecraft().thePlayer.worldObj.func_175656_a(b2r.func_177982_a(cx, cy, cz), b);
                        }
                    }
                }
            } else if (KeyBindings.keyBind_keys[4].isPressed()) {
                Configuration.freeze = !Configuration.freeze;
                Minecraft.getMinecraft().thePlayer.func_146105_b((ITextComponent)new TextComponentString("\u00a76[ \u00a7a\uff01 \u00a76] \u00a7fFreeze now " + (Configuration.freeze ? "opened" : "closed")), true);
            }
        }
    }
}

