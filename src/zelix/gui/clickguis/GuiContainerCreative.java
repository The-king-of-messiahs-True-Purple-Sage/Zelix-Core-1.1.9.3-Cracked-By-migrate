/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.achievement.GuiStats
 *  net.minecraft.client.gui.inventory.CreativeCrafting
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.InventoryEffectRenderer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.CreativeSettings
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.HotbarSnapshot
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.client.util.ITooltipFlag$TooltipFlags
 *  net.minecraft.client.util.SearchTreeManager
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IContainerListener
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryBasic
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentTranslation
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;

@SideOnly(value=Side.CLIENT)
public class GuiContainerCreative
extends InventoryEffectRenderer {
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static final InventoryBasic basicInventory = new InventoryBasic("tmp", true, 45);
    private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasClicking;
    private GuiTextField searchField;
    private List<Slot> originalSlots;
    private Slot destroyItemSlot;
    private boolean clearSearch;
    private CreativeCrafting listener;
    private static int tabPage = 0;
    private int maxPages = 0;

    public GuiContainerCreative(EntityPlayer player) {
        super((Container)new ContainerCreative(player));
        player.openContainer = this.field_147002_h;
        this.allowUserInput = true;
        this.field_147000_g = 136;
        this.field_146999_f = 195;
    }

    public void updateScreen() {
    }

    protected void func_184098_a(@Nullable Slot slotIn, int slotId, int mouseButton, ClickType type) {
        this.clearSearch = true;
        boolean flag = type == ClickType.QUICK_MOVE;
        ClickType clickType = type = slotId == -999 && type == ClickType.PICKUP ? ClickType.THROW : type;
        if (slotIn == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && type != ClickType.QUICK_CRAFT) {
            InventoryPlayer inventoryplayer1 = Wrapper.INSTANCE.mc().thePlayer.inventory;
            if (!inventoryplayer1.getItemStack().func_190926_b()) {
                if (mouseButton == 0) {
                    Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(inventoryplayer1.getItemStack(), true);
                    Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
                    inventoryplayer1.setItemStack(ItemStack.field_190927_a);
                }
                if (mouseButton == 1) {
                    ItemStack itemstack6 = inventoryplayer1.getItemStack().splitStack(1);
                    Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(itemstack6, true);
                    Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(itemstack6);
                }
            }
        } else {
            if (slotIn != null && !slotIn.canTakeStack((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer)) {
                return;
            }
            if (slotIn == this.destroyItemSlot && flag) {
                for (int j = 0; j < Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.func_75138_a().size(); ++j) {
                    Wrapper.INSTANCE.mc().playerController.sendSlotPacket(ItemStack.field_190927_a, j);
                }
            } else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
                if (slotIn == this.destroyItemSlot) {
                    Wrapper.INSTANCE.mc().thePlayer.inventory.setItemStack(ItemStack.field_190927_a);
                } else if (type == ClickType.THROW && slotIn != null && slotIn.getHasStack()) {
                    ItemStack itemstack = slotIn.decrStackSize(mouseButton == 0 ? 1 : slotIn.getStack().getMaxStackSize());
                    ItemStack itemstack1 = slotIn.getStack();
                    Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(itemstack, true);
                    Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(itemstack);
                    Wrapper.INSTANCE.mc().playerController.sendSlotPacket(itemstack1, ((CreativeSlot)((CreativeSlot)slotIn)).slot.slotNumber);
                } else if (type == ClickType.THROW && !Wrapper.INSTANCE.mc().thePlayer.inventory.getItemStack().func_190926_b()) {
                    Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(Wrapper.INSTANCE.mc().thePlayer.inventory.getItemStack(), true);
                    Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(Wrapper.INSTANCE.mc().thePlayer.inventory.getItemStack());
                    Wrapper.INSTANCE.mc().thePlayer.inventory.setItemStack(ItemStack.field_190927_a);
                } else {
                    Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.func_184996_a(slotIn == null ? slotId : ((CreativeSlot)((CreativeSlot)slotIn)).slot.slotNumber, mouseButton, type, (EntityPlayer)Wrapper.INSTANCE.mc().thePlayer);
                    Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.detectAndSendChanges();
                }
            } else if (type != ClickType.QUICK_CRAFT && slotIn.inventory == basicInventory) {
                InventoryPlayer inventoryplayer = Wrapper.INSTANCE.mc().thePlayer.inventory;
                ItemStack itemstack5 = inventoryplayer.getItemStack();
                ItemStack itemstack7 = slotIn.getStack();
                if (type == ClickType.SWAP) {
                    if (!itemstack7.func_190926_b() && mouseButton >= 0 && mouseButton < 9) {
                        ItemStack itemstack10 = itemstack7.copy();
                        itemstack10.func_190920_e(itemstack10.getMaxStackSize());
                        Wrapper.INSTANCE.mc().thePlayer.inventory.setInventorySlotContents(mouseButton, itemstack10);
                        Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.detectAndSendChanges();
                    }
                    return;
                }
                if (type == ClickType.CLONE) {
                    if (inventoryplayer.getItemStack().func_190926_b() && slotIn.getHasStack()) {
                        ItemStack itemstack9 = slotIn.getStack().copy();
                        itemstack9.func_190920_e(itemstack9.getMaxStackSize());
                        inventoryplayer.setItemStack(itemstack9);
                    }
                    return;
                }
                if (type == ClickType.THROW) {
                    if (!itemstack7.func_190926_b()) {
                        ItemStack itemstack8 = itemstack7.copy();
                        itemstack8.func_190920_e(mouseButton == 0 ? 1 : itemstack8.getMaxStackSize());
                        Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(itemstack8, true);
                        Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(itemstack8);
                    }
                    return;
                }
                if (!itemstack5.func_190926_b() && !itemstack7.func_190926_b() && itemstack5.isItemEqual(itemstack7) && ItemStack.areItemStackTagsEqual((ItemStack)itemstack5, (ItemStack)itemstack7)) {
                    if (mouseButton == 0) {
                        if (flag) {
                            itemstack5.func_190920_e(itemstack5.getMaxStackSize());
                        } else if (itemstack5.func_190916_E() < itemstack5.getMaxStackSize()) {
                            itemstack5.func_190917_f(1);
                        }
                    } else {
                        itemstack5.func_190918_g(1);
                    }
                } else if (!itemstack7.func_190926_b() && itemstack5.func_190926_b()) {
                    inventoryplayer.setItemStack(itemstack7.copy());
                    itemstack5 = inventoryplayer.getItemStack();
                    if (flag) {
                        itemstack5.func_190920_e(itemstack5.getMaxStackSize());
                    }
                } else if (mouseButton == 0) {
                    inventoryplayer.setItemStack(ItemStack.field_190927_a);
                } else {
                    inventoryplayer.getItemStack().func_190918_g(1);
                }
            } else if (this.field_147002_h != null) {
                ItemStack itemstack3 = slotIn == null ? ItemStack.field_190927_a : this.field_147002_h.getSlot(slotIn.slotNumber).getStack();
                this.field_147002_h.func_184996_a(slotIn == null ? slotId : slotIn.slotNumber, mouseButton, type, (EntityPlayer)Wrapper.INSTANCE.mc().thePlayer);
                if (Container.getDragEvent((int)mouseButton) == 2) {
                    for (int k = 0; k < 9; ++k) {
                        Wrapper.INSTANCE.mc().playerController.sendSlotPacket(this.field_147002_h.getSlot(45 + k).getStack(), 36 + k);
                    }
                } else if (slotIn != null) {
                    ItemStack itemstack4 = this.field_147002_h.getSlot(slotIn.slotNumber).getStack();
                    Wrapper.INSTANCE.mc().playerController.sendSlotPacket(itemstack4, slotIn.slotNumber - this.field_147002_h.inventorySlots.size() + 9 + 36);
                    int i = 45 + mouseButton;
                    if (type == ClickType.SWAP) {
                        Wrapper.INSTANCE.mc().playerController.sendSlotPacket(itemstack3, i - this.field_147002_h.inventorySlots.size() + 9 + 36);
                    } else if (type == ClickType.THROW && !itemstack3.func_190926_b()) {
                        ItemStack itemstack2 = itemstack3.copy();
                        itemstack2.func_190920_e(mouseButton == 0 ? 1 : itemstack2.getMaxStackSize());
                        Wrapper.INSTANCE.mc().thePlayer.dropPlayerItemWithRandomChoice(itemstack2, true);
                        Wrapper.INSTANCE.mc().playerController.sendPacketDropItem(itemstack2);
                    }
                    Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.detectAndSendChanges();
                }
            }
        }
    }

    protected void updateActivePotionEffects() {
        int i = this.field_147003_i;
        super.updateActivePotionEffects();
        if (this.searchField != null && this.field_147003_i != i) {
            this.searchField.xPosition = this.field_147003_i + 82;
        }
    }

    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        this.searchField = new GuiTextField(0, this.fontRendererObj, this.field_147003_i + 82, this.field_147009_r + 6, 80, this.fontRendererObj.FONT_HEIGHT);
        this.searchField.setMaxStringLength(50);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setVisible(false);
        this.searchField.setTextColor(0xFFFFFF);
        int i = selectedTabIndex;
        selectedTabIndex = -1;
        this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[i]);
        this.listener = new CreativeCrafting(this.mc);
        Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.func_75132_a((IContainerListener)this.listener);
        int tabCount = CreativeTabs.creativeTabArray.length;
        if (tabCount > 12) {
            this.buttonList.add(new GuiButton(101, this.field_147003_i, this.field_147009_r - 50, 20, 20, "<"));
            this.buttonList.add(new GuiButton(102, this.field_147003_i + this.field_146999_f - 20, this.field_147009_r - 50, 20, 20, ">"));
            this.maxPages = (int)Math.ceil((double)(tabCount - 12) / 10.0);
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        if (Wrapper.INSTANCE.mc().thePlayer != null && Wrapper.INSTANCE.mc().thePlayer.inventory != null) {
            Wrapper.INSTANCE.mc().thePlayer.inventoryContainer.func_82847_b((IContainerListener)this.listener);
        }
        Keyboard.enableRepeatEvents((boolean)false);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!CreativeTabs.creativeTabArray[selectedTabIndex].hasSearchBar()) {
            if (GameSettings.isKeyDown((KeyBinding)Wrapper.INSTANCE.mc().gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
            } else {
                super.keyTyped(typedChar, keyCode);
            }
        } else {
            if (this.clearSearch) {
                this.clearSearch = false;
                this.searchField.setText("");
            }
            if (!this.func_146983_a(keyCode)) {
                if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
                    this.updateCreativeSearch();
                } else {
                    super.keyTyped(typedChar, keyCode);
                }
            }
        }
    }

    private void updateCreativeSearch() {
        ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.field_147002_h;
        guicontainercreative$containercreative.itemList.clear();
        CreativeTabs tab = CreativeTabs.creativeTabArray[selectedTabIndex];
        if (tab.hasSearchBar() && tab != CreativeTabs.tabAllSearch) {
            tab.func_78018_a(guicontainercreative$containercreative.itemList);
            if (!this.searchField.getText().isEmpty()) {
                String search = this.searchField.getText().toLowerCase(Locale.ROOT);
                Iterator itr = guicontainercreative$containercreative.itemList.iterator();
                while (itr.hasNext()) {
                    ItemStack stack = (ItemStack)itr.next();
                    boolean matches = false;
                    for (String line : stack.func_82840_a((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, (ITooltipFlag)(Wrapper.INSTANCE.mc().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL))) {
                        if (!TextFormatting.func_110646_a((String)line).toLowerCase(Locale.ROOT).contains(search)) continue;
                        matches = true;
                        break;
                    }
                    if (matches) continue;
                    itr.remove();
                }
            }
            this.currentScroll = 0.0f;
            guicontainercreative$containercreative.scrollTo(0.0f);
            return;
        }
        if (this.searchField.getText().isEmpty()) {
            for (Item item : Item.itemRegistry) {
                item.func_150895_a(CreativeTabs.tabAllSearch, guicontainercreative$containercreative.itemList);
            }
        } else {
            guicontainercreative$containercreative.itemList.addAll((Collection)Wrapper.INSTANCE.mc().func_193987_a(SearchTreeManager.field_194011_a).func_194038_a(this.searchField.getText().toLowerCase(Locale.ROOT)));
        }
        this.currentScroll = 0.0f;
        guicontainercreative$containercreative.scrollTo(0.0f);
    }

    protected void func_146979_b(int mouseX, int mouseY) {
        CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
        if (creativetabs != null && creativetabs.drawInForegroundOfTab()) {
            GlStateManager.disableBlend();
            this.fontRendererObj.drawString(I18n.format((String)creativetabs.getTranslatedTabLabel(), (Object[])new Object[0]), 8, 6, 268015649);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            int i = mouseX - this.field_147003_i;
            int j = mouseY - this.field_147009_r;
            for (CreativeTabs creativetabs : CreativeTabs.creativeTabArray) {
                if (!this.isMouseOverTab(creativetabs, i, j)) continue;
                return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            int i = mouseX - this.field_147003_i;
            int j = mouseY - this.field_147009_r;
            for (CreativeTabs creativetabs : CreativeTabs.creativeTabArray) {
                if (creativetabs == null || !this.isMouseOverTab(creativetabs, i, j)) continue;
                this.setCurrentCreativeTab(creativetabs);
                return;
            }
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    private boolean needsScrollBars() {
        if (CreativeTabs.creativeTabArray[selectedTabIndex] == null) {
            return false;
        }
        return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.field_147002_h).canScroll();
    }

    private void setCurrentCreativeTab(CreativeTabs tab) {
        if (tab == null) {
            return;
        }
        int i = selectedTabIndex;
        selectedTabIndex = tab.getTabIndex();
        ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.field_147002_h;
        this.field_147008_s.clear();
        guicontainercreative$containercreative.itemList.clear();
        if (tab == CreativeTabs.field_192395_m) {
            for (int j = 0; j < 9; ++j) {
                HotbarSnapshot hotbarsnapshot = Wrapper.INSTANCE.mc().field_191950_u.func_192563_a(j);
                if (hotbarsnapshot.isEmpty()) {
                    for (int k = 0; k < 9; ++k) {
                        if (k == j) {
                            ItemStack itemstack = new ItemStack(Items.paper);
                            itemstack.func_190925_c("CustomCreativeLock");
                            String s = GameSettings.getKeyDisplayString((int)Wrapper.INSTANCE.mc().gameSettings.keyBindsHotbar[j].getKeyCode());
                            String s1 = GameSettings.getKeyDisplayString((int)Wrapper.INSTANCE.mc().gameSettings.field_193629_ap.getKeyCode());
                            itemstack.setStackDisplayName(new TextComponentTranslation("inventory.hotbarInfo", new Object[]{s1, s}).func_150260_c());
                            guicontainercreative$containercreative.itemList.add((Object)itemstack);
                            continue;
                        }
                        guicontainercreative$containercreative.itemList.add((Object)ItemStack.field_190927_a);
                    }
                    continue;
                }
                guicontainercreative$containercreative.itemList.addAll((Collection)hotbarsnapshot);
            }
        } else if (tab != CreativeTabs.tabAllSearch) {
            tab.func_78018_a(guicontainercreative$containercreative.itemList);
        }
        if (tab == CreativeTabs.tabInventory) {
            Container container = Wrapper.INSTANCE.mc().thePlayer.inventoryContainer;
            if (this.originalSlots == null) {
                this.originalSlots = guicontainercreative$containercreative.inventorySlots;
            }
            guicontainercreative$containercreative.inventorySlots = Lists.newArrayList();
            for (int l = 0; l < container.inventorySlots.size(); ++l) {
                CreativeSlot slot = new CreativeSlot((Slot)container.inventorySlots.get(l), l);
                guicontainercreative$containercreative.inventorySlots.add(slot);
                if (l >= 5 && l < 9) {
                    int j1 = l - 5;
                    int l1 = j1 / 2;
                    int j2 = j1 % 2;
                    slot.xDisplayPosition = 54 + l1 * 54;
                    slot.yDisplayPosition = 6 + j2 * 27;
                    continue;
                }
                if (l >= 0 && l < 5) {
                    slot.xDisplayPosition = -2000;
                    slot.yDisplayPosition = -2000;
                    continue;
                }
                if (l == 45) {
                    slot.xDisplayPosition = 35;
                    slot.yDisplayPosition = 20;
                    continue;
                }
                if (l >= container.inventorySlots.size()) continue;
                int i1 = l - 9;
                int k1 = i1 % 9;
                int i2 = i1 / 9;
                slot.xDisplayPosition = 9 + k1 * 18;
                slot.yDisplayPosition = l >= 36 ? 112 : 54 + i2 * 18;
            }
            this.destroyItemSlot = new Slot((IInventory)basicInventory, 0, 173, 112);
            guicontainercreative$containercreative.inventorySlots.add(this.destroyItemSlot);
        } else if (i == CreativeTabs.tabInventory.getTabIndex()) {
            guicontainercreative$containercreative.inventorySlots = this.originalSlots;
            this.originalSlots = null;
        }
        if (this.searchField != null) {
            if (tab.hasSearchBar()) {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.searchField.setText("");
                this.searchField.width = tab.getSearchbarWidth();
                this.searchField.xPosition = this.field_147003_i + 171 - this.searchField.width;
                this.updateCreativeSearch();
            } else {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }
        this.currentScroll = 0.0f;
        guicontainercreative$containercreative.scrollTo(0.0f);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && this.needsScrollBars()) {
            int j = (((ContainerCreative)this.field_147002_h).itemList.size() + 9 - 1) / 9 - 5;
            if (i > 0) {
                i = 1;
            }
            if (i < 0) {
                i = -1;
            }
            this.currentScroll = (float)((double)this.currentScroll - (double)i / (double)j);
            this.currentScroll = MathHelper.func_76131_a((float)this.currentScroll, (float)0.0f, (float)1.0f);
            ((ContainerCreative)this.field_147002_h).scrollTo(this.currentScroll);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        boolean flag = Mouse.isButtonDown((int)0);
        int i = this.field_147003_i;
        int j = this.field_147009_r;
        int k = i + 175;
        int l = j + 18;
        int i1 = k + 14;
        int j1 = l + 112;
        if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1) {
            this.isScrolling = this.needsScrollBars();
        }
        if (!flag) {
            this.isScrolling = false;
        }
        this.wasClicking = flag;
        if (this.isScrolling) {
            this.currentScroll = ((float)(mouseY - l) - 7.5f) / ((float)(j1 - l) - 15.0f);
            this.currentScroll = MathHelper.func_76131_a((float)this.currentScroll, (float)0.0f, (float)1.0f);
            ((ContainerCreative)this.field_147002_h).scrollTo(this.currentScroll);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        int start = tabPage * 10;
        int end = Math.min(CreativeTabs.creativeTabArray.length, (tabPage + 1) * 10 + 2);
        if (tabPage != 0) {
            start += 2;
        }
        boolean rendered = false;
        for (CreativeTabs creativetabs : Arrays.copyOfRange(CreativeTabs.creativeTabArray, start, end)) {
            if (creativetabs == null || !this.renderCreativeInventoryHoveringText(creativetabs, mouseX, mouseY)) continue;
            rendered = true;
            break;
        }
        if (!rendered && !this.renderCreativeInventoryHoveringText(CreativeTabs.tabAllSearch, mouseX, mouseY)) {
            this.renderCreativeInventoryHoveringText(CreativeTabs.tabInventory, mouseX, mouseY);
        }
        if (this.destroyItemSlot != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.func_146978_c(this.destroyItemSlot.xDisplayPosition, this.destroyItemSlot.yDisplayPosition, 16, 16, mouseX, mouseY)) {
            this.drawCreativeTabHoveringText(I18n.format((String)"inventory.binSlot", (Object[])new Object[0]), mouseX, mouseY);
        }
        if (this.maxPages != 0) {
            String page = String.format("%d / %d", tabPage + 1, this.maxPages + 1);
            int width = this.fontRendererObj.getStringWidth(page);
            GlStateManager.disableLighting();
            this.field_73735_i = 300.0f;
            this.itemRender.field_77023_b = 300.0f;
            this.fontRendererObj.drawString(page, this.field_147003_i + this.field_146999_f / 2 - width / 2, this.field_147009_r - 44, -1);
            this.field_73735_i = 0.0f;
            this.itemRender.field_77023_b = 0.0f;
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.disableLighting();
        if (HackManager.getHack("FakeCreative").isToggledValue("ShowItemsID")) {
            this.renderCustomHoveredToolTip(mouseX, mouseY);
        } else {
            this.func_191948_b(mouseX, mouseY);
        }
    }

    private void renderCustomHoveredToolTip(int x, int y) {
        if (Wrapper.INSTANCE.mc().thePlayer.inventory.getItemStack().func_190926_b() && this.getSlotUnderMouse() != null && this.getSlotUnderMouse().getHasStack()) {
            ItemStack stack = this.getSlotUnderMouse().getStack();
            List textLines = this.func_191927_a(stack);
            textLines.add(0, Item.getIdFromItem((Item)stack.getItem()) + ":" + stack.getItem().getMetadata(stack));
            this.drawHoveringText(textLines, x, y);
        }
    }

    protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
        int start = tabPage * 10;
        int end = Math.min(CreativeTabs.creativeTabArray.length, (tabPage + 1) * 10 + 2);
        if (tabPage != 0) {
            start += 2;
        }
        for (CreativeTabs creativetabs1 : Arrays.copyOfRange(CreativeTabs.creativeTabArray, start, end)) {
            Wrapper.INSTANCE.mc().getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
            if (creativetabs1 == null || creativetabs1.getTabIndex() == selectedTabIndex) continue;
            this.drawTab(creativetabs1);
        }
        if (tabPage != 0) {
            if (creativetabs != CreativeTabs.tabAllSearch) {
                Wrapper.INSTANCE.mc().getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
                this.drawTab(CreativeTabs.tabAllSearch);
            }
            if (creativetabs != CreativeTabs.tabInventory) {
                Wrapper.INSTANCE.mc().getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
                this.drawTab(CreativeTabs.tabInventory);
            }
        }
        Wrapper.INSTANCE.mc().getTextureManager().bindTexture(creativetabs.getBackgroundImage());
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        this.searchField.drawTextBox();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int i = this.field_147003_i + 175;
        int j = this.field_147009_r + 18;
        int k = j + 112;
        Wrapper.INSTANCE.mc().getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
        if (creativetabs.shouldHidePlayerInventory()) {
            this.func_73729_b(i, j + (int)((float)(k - j - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }
        if ((creativetabs == null || creativetabs.getTabPage() != tabPage) && creativetabs != CreativeTabs.tabAllSearch && creativetabs != CreativeTabs.tabInventory) {
            return;
        }
        this.drawTab(creativetabs);
        if (creativetabs == CreativeTabs.tabInventory) {
            GuiInventory.drawEntityOnScreen((int)(this.field_147003_i + 88), (int)(this.field_147009_r + 45), (int)20, (float)(this.field_147003_i + 88 - mouseX), (float)(this.field_147009_r + 45 - 30 - mouseY), (EntityLivingBase)Wrapper.INSTANCE.mc().thePlayer);
        }
    }

    protected boolean isMouseOverTab(CreativeTabs tab, int mouseX, int mouseY) {
        if (tab.getTabPage() != tabPage && tab != CreativeTabs.tabAllSearch && tab != CreativeTabs.tabInventory) {
            return false;
        }
        int i = tab.getTabColumn();
        int j = 28 * i;
        int k = 0;
        if (tab.func_192394_m()) {
            j = this.field_146999_f - 28 * (6 - i) + 2;
        } else if (i > 0) {
            j += i;
        }
        k = tab.isTabInFirstRow() ? (k -= 32) : (k += this.field_147000_g);
        return mouseX >= j && mouseX <= j + 28 && mouseY >= k && mouseY <= k + 32;
    }

    protected boolean renderCreativeInventoryHoveringText(CreativeTabs tab, int mouseX, int mouseY) {
        int i = tab.getTabColumn();
        int j = 28 * i;
        int k = 0;
        if (tab.func_192394_m()) {
            j = this.field_146999_f - 28 * (6 - i) + 2;
        } else if (i > 0) {
            j += i;
        }
        k = tab.isTabInFirstRow() ? (k -= 32) : (k += this.field_147000_g);
        if (this.func_146978_c(j + 3, k + 3, 23, 27, mouseX, mouseY)) {
            this.drawCreativeTabHoveringText(I18n.format((String)tab.getTranslatedTabLabel(), (Object[])new Object[0]), mouseX, mouseY);
            return true;
        }
        return false;
    }

    protected void drawTab(CreativeTabs tab) {
        boolean flag = tab.getTabIndex() == selectedTabIndex;
        boolean flag1 = tab.isTabInFirstRow();
        int i = tab.getTabColumn();
        int j = i * 28;
        int k = 0;
        int l = this.field_147003_i + 28 * i;
        int i1 = this.field_147009_r;
        int j1 = 32;
        if (flag) {
            k += 32;
        }
        if (tab.func_192394_m()) {
            l = this.field_147003_i + this.field_146999_f - 28 * (6 - i);
        } else if (i > 0) {
            l += i;
        }
        if (flag1) {
            i1 -= 28;
        } else {
            k += 64;
            i1 += this.field_147000_g - 4;
        }
        GlStateManager.disableLighting();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableBlend();
        this.func_73729_b(l, i1, j, k, 28, 32);
        this.field_73735_i = 100.0f;
        this.itemRender.field_77023_b = 100.0f;
        i1 = i1 + 8 + (flag1 ? 1 : -1);
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        ItemStack itemstack = tab.getIconItemStack();
        this.itemRender.func_180450_b(itemstack, l += 6, i1);
        this.itemRender.func_175030_a(this.fontRendererObj, itemstack, l, i1);
        GlStateManager.disableLighting();
        this.itemRender.field_77023_b = 0.0f;
        this.field_73735_i = 0.0f;
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, Wrapper.INSTANCE.mc().thePlayer.func_146107_m()));
        }
        if (button.id == 101) {
            tabPage = Math.max(tabPage - 1, 0);
        } else if (button.id == 102) {
            tabPage = Math.min(tabPage + 1, this.maxPages);
        }
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    public static void handleHotbarSnapshots(Minecraft p_192044_0_, int p_192044_1_, boolean p_192044_2_, boolean p_192044_3_) {
        EntityPlayerSP entityplayersp = p_192044_0_.thePlayer;
        CreativeSettings creativesettings = p_192044_0_.field_191950_u;
        HotbarSnapshot hotbarsnapshot = creativesettings.func_192563_a(p_192044_1_);
        if (p_192044_2_) {
            for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
                ItemStack itemstack = ((ItemStack)hotbarsnapshot.get(i)).copy();
                entityplayersp.inventory.setInventorySlotContents(i, itemstack);
                p_192044_0_.playerController.sendSlotPacket(itemstack, 36 + i);
            }
            entityplayersp.inventoryContainer.detectAndSendChanges();
        } else if (p_192044_3_) {
            for (int j = 0; j < InventoryPlayer.getHotbarSize(); ++j) {
                hotbarsnapshot.set(j, (Object)entityplayersp.inventory.getStackInSlot(j).copy());
            }
            String s = GameSettings.getKeyDisplayString((int)p_192044_0_.gameSettings.keyBindsHotbar[p_192044_1_].getKeyCode());
            String s1 = GameSettings.getKeyDisplayString((int)p_192044_0_.gameSettings.field_193630_aq.getKeyCode());
            p_192044_0_.ingameGUI.func_175188_a((ITextComponent)new TextComponentTranslation("inventory.hotbarSaved", new Object[]{s1, s}), false);
            creativesettings.func_192564_b();
        }
    }

    @SideOnly(value=Side.CLIENT)
    static class LockedSlot
    extends Slot {
        public LockedSlot(IInventory p_i47453_1_, int p_i47453_2_, int p_i47453_3_, int p_i47453_4_) {
            super(p_i47453_1_, p_i47453_2_, p_i47453_3_, p_i47453_4_);
        }

        public boolean canTakeStack(EntityPlayer playerIn) {
            if (super.canTakeStack(playerIn) && this.getHasStack()) {
                return this.getStack().func_179543_a("CustomCreativeLock") == null;
            }
            return !this.getHasStack();
        }
    }

    @SideOnly(value=Side.CLIENT)
    class CreativeSlot
    extends Slot {
        private final Slot slot;

        public CreativeSlot(Slot p_i46313_2_, int index) {
            super(p_i46313_2_.inventory, index, 0, 0);
            this.slot = p_i46313_2_;
        }

        public ItemStack func_190901_a(EntityPlayer thePlayer, ItemStack stack) {
            this.slot.func_190901_a(thePlayer, stack);
            return stack;
        }

        public boolean isItemValid(ItemStack stack) {
            return this.slot.isItemValid(stack);
        }

        public ItemStack getStack() {
            return this.slot.getStack();
        }

        public boolean getHasStack() {
            return this.slot.getHasStack();
        }

        public void putStack(ItemStack stack) {
            this.slot.putStack(stack);
        }

        public void onSlotChanged() {
            this.slot.onSlotChanged();
        }

        public int getSlotStackLimit() {
            return this.slot.getSlotStackLimit();
        }

        public int getItemStackLimit(ItemStack stack) {
            return this.slot.getItemStackLimit(stack);
        }

        @Nullable
        public String getSlotTexture() {
            return this.slot.getSlotTexture();
        }

        public ItemStack decrStackSize(int amount) {
            return this.slot.decrStackSize(amount);
        }

        public boolean isHere(IInventory inv, int slotIn) {
            return this.slot.isHere(inv, slotIn);
        }

        public boolean canBeHovered() {
            return this.slot.canBeHovered();
        }

        public boolean canTakeStack(EntityPlayer playerIn) {
            return this.slot.canTakeStack(playerIn);
        }

        public ResourceLocation getBackgroundLocation() {
            return this.slot.getBackgroundLocation();
        }

        public void setBackgroundLocation(ResourceLocation texture) {
            this.slot.setBackgroundLocation(texture);
        }

        public void setBackgroundName(@Nullable String name) {
            this.slot.setBackgroundName(name);
        }

        @Nullable
        public TextureAtlasSprite getBackgroundSprite() {
            return this.slot.getBackgroundSprite();
        }

        public int getSlotIndex() {
            return this.slot.getSlotIndex();
        }

        public boolean isSameInventory(Slot other) {
            return this.slot.isSameInventory(other);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public static class ContainerCreative
    extends Container {
        public NonNullList<ItemStack> itemList = NonNullList.func_191196_a();

        public ContainerCreative(EntityPlayer player) {
            InventoryPlayer inventoryplayer = player.inventory;
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new LockedSlot((IInventory)basicInventory, i * 9 + j, 9 + j * 18, 18 + i * 18));
                }
            }
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot((IInventory)inventoryplayer, k, 9 + k * 18, 112));
            }
            this.scrollTo(0.0f);
        }

        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }

        public void scrollTo(float pos) {
            int i = (this.itemList.size() + 9 - 1) / 9 - 5;
            int j = (int)((double)(pos * (float)i) + 0.5);
            if (j < 0) {
                j = 0;
            }
            for (int k = 0; k < 5; ++k) {
                for (int l = 0; l < 9; ++l) {
                    int i1 = l + (k + j) * 9;
                    if (i1 >= 0 && i1 < this.itemList.size()) {
                        basicInventory.setInventorySlotContents(l + k * 9, (ItemStack)this.itemList.get(i1));
                        continue;
                    }
                    basicInventory.setInventorySlotContents(l + k * 9, ItemStack.field_190927_a);
                }
            }
        }

        public boolean canScroll() {
            return this.itemList.size() > 45;
        }

        public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
            Slot slot;
            if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size() && (slot = (Slot)this.inventorySlots.get(index)) != null && slot.getHasStack()) {
                slot.putStack(ItemStack.field_190927_a);
            }
            return ItemStack.field_190927_a;
        }

        public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
            return slotIn.yDisplayPosition > 90;
        }

        public boolean canDragIntoSlot(Slot slotIn) {
            return slotIn.inventory instanceof InventoryPlayer || slotIn.yDisplayPosition > 90 && slotIn.xDisplayPosition <= 162;
        }
    }
}

