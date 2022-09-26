/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.ReflectionHelper;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class AutoClicker
extends Hack {
    TimerUtils time = new TimerUtils();
    int delay;
    static ModeValue mode;
    static BooleanValue OnClick;
    static BooleanValue inventoryFill;
    static BooleanValue weaponOnly;
    static NumberValue CPS;
    static NumberValue RandomMS;
    private Method playerMouseInput = ReflectionHelper.findMethod(GuiScreen.class, null, new String[]{"func_73864_a", "mouseClicked"}, Integer.TYPE, Integer.TYPE, Integer.TYPE);

    public AutoClicker() {
        super("AutoClicker", HackCategory.COMBAT);
        mode = new ModeValue("Mode", new Mode("Left", true), new Mode("Right", false), new Mode("Both", false));
        OnClick = new BooleanValue("OnClick", true);
        inventoryFill = new BooleanValue("InvFill", true);
        weaponOnly = new BooleanValue("Weapon", true);
        CPS = new NumberValue("CPS", 9.0, 1.0, 20.0);
        RandomMS = new NumberValue("RandomMS", 5.0, 0.0, 250.0);
        this.addValue(mode, OnClick, inventoryFill, weaponOnly, CPS, RandomMS);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.time.isDelay(this.delay)) {
            if (((Boolean)OnClick.getValue()).booleanValue()) {
                if (mode.getMode("Left").isToggled()) {
                    if (Mouse.isButtonDown((int)0)) {
                        this.click();
                    }
                } else if (mode.getMode("Right").isToggled()) {
                    if (Mouse.isButtonDown((int)1)) {
                        this.click();
                    }
                } else if (Mouse.isButtonDown((int)1) || Mouse.isButtonDown((int)0)) {
                    this.click();
                }
            } else {
                this.click();
            }
        }
    }

    private void click() {
        this.delay = (int)Math.round(1000.0 / (Double)CPS.getValue());
        int random = (int)(Math.random() * (Double)RandomMS.getValue());
        this.delay += random;
        this.time.setLastMS();
        if (mode.getMode("Left").isToggled()) {
            this.clickMouse();
        } else if (mode.getMode("Right").isToggled()) {
            AutoClicker.rightClickMouse();
        } else {
            if (Mouse.isButtonDown((int)0)) {
                this.clickMouse();
            }
            if (Mouse.isButtonDown((int)1)) {
                AutoClicker.rightClickMouse();
            }
        }
    }

    public void doInventoryClick() {
        if (((Boolean)inventoryFill.getValue()).booleanValue() && (Wrapper.INSTANCE.mc().currentScreen instanceof GuiInventory || Wrapper.INSTANCE.mc().currentScreen instanceof GuiChest)) {
            this.inInvClick(Wrapper.INSTANCE.mc().currentScreen);
        }
    }

    private void inInvClick(GuiScreen guiScreen) {
        int mouseInGUIPosX = Mouse.getX() * guiScreen.width / Wrapper.INSTANCE.mc().displayWidth;
        int mouseInGUIPosY = guiScreen.height - Mouse.getY() * guiScreen.height / Wrapper.INSTANCE.mc().displayHeight - 1;
        try {
            this.playerMouseInput.invoke((Object)guiScreen, mouseInGUIPosX, mouseInGUIPosY, 0);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
    }

    private void clickMouse() {
        if (Wrapper.INSTANCE.mc().currentScreen != null || !Wrapper.INSTANCE.mc().inGameHasFocus) {
            this.doInventoryClick();
            return;
        }
        if (((Boolean)weaponOnly.getValue()).booleanValue() && !Utils.isPlayerHoldingWeapon()) {
            return;
        }
        int leftClickCounter = 0;
        Minecraft mc = Minecraft.getMinecraft();
        if (leftClickCounter <= 0 && Minecraft.getMinecraft().objectMouseOver != null && !Wrapper.INSTANCE.mc().thePlayer.func_184838_M()) {
            switch (Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a) {
                case ENTITY: {
                    Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().objectMouseOver.field_72308_g);
                    break;
                }
                case BLOCK: {
                    BlockPos blockpos = Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a();
                    if (!Wrapper.INSTANCE.mc().theWorld.func_175623_d(blockpos)) {
                        Wrapper.INSTANCE.mc().playerController.func_180511_b(blockpos, Wrapper.INSTANCE.mc().objectMouseOver.field_178784_b);
                        break;
                    }
                }
                case MISS: {
                    Wrapper.INSTANCE.mc().thePlayer.func_184821_cY();
                    ForgeHooks.onEmptyLeftClick((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer);
                }
            }
            Wrapper.INSTANCE.mc().thePlayer.func_184609_a(EnumHand.MAIN_HAND);
        }
    }

    public static void rightClickMouse() {
        Minecraft mc = Minecraft.getMinecraft();
        if (!Wrapper.INSTANCE.mc().playerController.getIsHittingBlock() && !Wrapper.INSTANCE.mc().thePlayer.func_184838_M()) {
            if (Wrapper.INSTANCE.mc().objectMouseOver == null) {
                // empty if block
            }
            for (EnumHand enumhand : EnumHand.values()) {
                ItemStack itemstack = Wrapper.INSTANCE.mc().thePlayer.func_184586_b(enumhand);
                if (Wrapper.INSTANCE.mc().objectMouseOver != null) {
                    switch (Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a) {
                        case ENTITY: {
                            if (Wrapper.INSTANCE.mc().playerController.func_187102_a((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().objectMouseOver.field_72308_g, Wrapper.INSTANCE.mc().objectMouseOver, enumhand) == EnumActionResult.SUCCESS) {
                                return;
                            }
                            if (Wrapper.INSTANCE.mc().playerController.func_187097_a((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().objectMouseOver.field_72308_g, enumhand) != EnumActionResult.SUCCESS) break;
                            return;
                        }
                        case BLOCK: {
                            BlockPos blockpos = Wrapper.INSTANCE.mc().objectMouseOver.func_178782_a();
                            if (Wrapper.INSTANCE.mc().theWorld.func_180495_p(blockpos).func_185904_a() == Material.air) break;
                            int i = itemstack.func_190916_E();
                            EnumActionResult enumactionresult = Wrapper.INSTANCE.mc().playerController.func_187099_a(Wrapper.INSTANCE.mc().thePlayer, Wrapper.INSTANCE.mc().theWorld, blockpos, Wrapper.INSTANCE.mc().objectMouseOver.field_178784_b, Wrapper.INSTANCE.mc().objectMouseOver.field_72307_f, enumhand);
                            if (enumactionresult != EnumActionResult.SUCCESS) break;
                            Wrapper.INSTANCE.mc().thePlayer.func_184609_a(enumhand);
                            if (!itemstack.func_190926_b() && (itemstack.func_190916_E() != i || Wrapper.INSTANCE.mc().playerController.isInCreativeMode())) {
                                Wrapper.INSTANCE.mc().entityRenderer.itemRenderer.func_187460_a(enumhand);
                            }
                            return;
                        }
                    }
                }
                if (itemstack.func_190926_b() && (Wrapper.INSTANCE.mc().objectMouseOver == null || Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a == RayTraceResult.Type.MISS)) {
                    ForgeHooks.onEmptyClick((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, (EnumHand)enumhand);
                }
                if (itemstack.func_190926_b() || Wrapper.INSTANCE.mc().playerController.func_187101_a((EntityPlayer)Wrapper.INSTANCE.mc().thePlayer, (World)Wrapper.INSTANCE.mc().theWorld, enumhand) != EnumActionResult.SUCCESS) continue;
                Wrapper.INSTANCE.mc().entityRenderer.itemRenderer.func_187460_a(enumhand);
                return;
            }
        }
    }
}

