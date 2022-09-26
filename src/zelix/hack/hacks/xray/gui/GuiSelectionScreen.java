/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.World
 *  net.minecraftforge.common.config.Config$Type
 *  net.minecraftforge.common.config.ConfigManager
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks.xray.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import org.lwjgl.input.Mouse;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiHelp;
import zelix.hack.hacks.xray.gui.manage.GuiAddBlock;
import zelix.hack.hacks.xray.gui.manage.GuiBlockListScrollable;
import zelix.hack.hacks.xray.gui.utils.GuiBase;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.BlockItem;
import zelix.hack.hacks.xray.utils.Utils;
import zelix.hack.hacks.xray.xray.Controller;

public class GuiSelectionScreen
extends GuiBase {
    private GuiTextField search;
    public RenderItem render;
    private String lastSearch = "";
    private static final int BUTTON_RADIUS = 0;
    private static final int BUTTON_ADD_BLOCK = 1;
    private static final int BUTTON_ADD_HAND = 4;
    private static final int BUTTON_ADD_LOOK = 5;
    private static final int BUTTON_HELP = 6;
    private static final int BUTTON_CLOSE = 7;
    private ArrayList<BlockData> itemList;
    private ArrayList<BlockData> originalList;

    public GuiSelectionScreen() {
        super(true);
        this.setSideTitle(I18n.format((String)"xray.single.tools", (Object[])new Object[0]));
        this.itemList = new ArrayList<BlockData>(Controller.getBlockStore().getStore().values());
        this.originalList = this.itemList;
    }

    public void initGui() {
        this.render = this.itemRender;
        this.buttonList.clear();
        this.search = new GuiTextField(150, this.getFontRender(), this.width / 2 - 137, this.height / 2 - 105, 202, 18);
        this.search.setCanLoseFocus(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 + 79, this.height / 2 - 60, 120, 20, I18n.format((String)"xray.input.add", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 79, this.height / 2 - 38, 120, 20, I18n.format((String)"xray.input.add_hand", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 79, this.height / 2 - 16, 120, 20, I18n.format((String)"xray.input.add_look", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 79, this.height / 2 + 58, 60, 20, I18n.format((String)"xray.single.help", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 79 + 62, this.height / 2 + 58, 59, 20, I18n.format((String)"xray.single.close", (Object[])new Object[0])));
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                Controller.incrementCurrentDist();
                break;
            }
            case 1: {
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiBlockListScrollable());
                break;
            }
            case 6: {
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiHelp());
                break;
            }
            case 4: {
                this.mc.thePlayer.closeScreen();
                ItemStack handItem = this.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND);
                if (!(handItem.getItem() instanceof ItemBlock)) {
                    Utils.sendMessage(this.mc.thePlayer, "[XRay] " + I18n.format((String)"xray.message.invalid_hand", (Object[])new Object[]{handItem.getDisplayName()}));
                    return;
                }
                IBlockState iBlockState = Utils.getStateFromPlacement((World)this.mc.theWorld, (EntityLivingBase)this.mc.thePlayer, handItem);
                this.mc.displayGuiScreen((GuiScreen)new GuiAddBlock(new BlockItem(Block.getStateId((IBlockState)iBlockState), handItem), null));
                break;
            }
            case 5: {
                this.mc.thePlayer.closeScreen();
                try {
                    RayTraceResult ray = this.mc.thePlayer.func_174822_a(100.0, 20.0f);
                    if (ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
                        IBlockState state = this.mc.theWorld.func_180495_p(ray.func_178782_a());
                        Block lookingAt = this.mc.theWorld.func_180495_p(ray.func_178782_a()).getBlock();
                        ItemStack lookingStack = lookingAt.getPickBlock(state, ray, (World)this.mc.theWorld, ray.func_178782_a(), (EntityPlayer)this.mc.thePlayer);
                        this.mc.thePlayer.closeScreen();
                        this.mc.displayGuiScreen((GuiScreen)new GuiAddBlock(new BlockItem(Block.getStateId((IBlockState)state), lookingStack), state));
                        break;
                    }
                    Utils.sendMessage(this.mc.thePlayer, "[XRay] " + I18n.format((String)"xray.message.nothing_infront", (Object[])new Object[0]));
                }
                catch (NullPointerException ex) {
                    Utils.sendMessage(this.mc.thePlayer, "[XRay] " + I18n.format((String)"xray.message.thats_odd", (Object[])new Object[0]));
                }
                break;
            }
            case 7: {
                this.mc.thePlayer.closeScreen();
            }
        }
        this.initGui();
    }

    @Override
    protected void keyTyped(char charTyped, int hex) throws IOException {
        super.keyTyped(charTyped, hex);
        this.search.textboxKeyTyped(charTyped, hex);
        this.updateSearch();
    }

    private void updateSearch() {
        if (this.search.getText().equals("")) {
            this.itemList = this.originalList;
            this.lastSearch = "";
            return;
        }
        if (this.lastSearch.equals(this.search.getText())) {
            return;
        }
        this.itemList = this.originalList.stream().filter(b -> b.getEntryName().toLowerCase().contains(this.search.getText().toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        this.lastSearch = this.search.getText();
    }

    public void updateScreen() {
        this.search.updateCursorCounter();
    }

    @Override
    public void mouseClicked(int x, int y, int mouse) throws IOException {
        super.mouseClicked(x, y, mouse);
        this.search.func_146192_a(x, y, mouse);
        if (mouse == 1) {
            // empty if block
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.search.drawTextBox();
        if (!this.search.isFocused() && this.search.getText().equals("")) {
            XRay.mc.fontRendererObj.drawStringWithShadow(I18n.format((String)"xray.single.search", (Object[])new Object[0]), (float)this.width / 2.0f - 130.0f, (float)this.height / 2.0f - 101.0f, Color.GRAY.getRGB());
        }
    }

    public void onGuiClosed() {
        ConfigManager.sync((String)"xray", (Config.Type)Config.Type.INSTANCE);
        XRay.blockStore.write(Controller.getBlockStore().getStore());
        Controller.requestBlockFinder(true);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
    }
}

