/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.gishcode;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zelix.command.Command;
import zelix.gui.clickguis.GuiTextField;
import zelix.gui.clickguis.Tooltip;
import zelix.gui.clickguis.gishcode.ClickGui;
import zelix.managers.CommandManager;
import zelix.managers.FileManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.hooks.visual.ColorUtils;

public class ClickGuiScreen
extends GuiScreen {
    public static final String AUTHOR_TEXT = "Just Remember Me! Zelix God!";
    public static ClickGui clickGui;
    public static int[] mouse;
    public static Tooltip tooltip;
    String title = "Just Remember Me! Zelix God!";
    ArrayList cmds = new ArrayList();
    GuiTextField console;

    public ClickGuiScreen() {
        this.cmds.clear();
        for (Command c : CommandManager.commands) {
            this.cmds.add(c.getCommand() + " - " + c.getDescription());
        }
    }

    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.console.mouseClicked(x, y, button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        tooltip = null;
        clickGui.render();
        if (tooltip != null) {
            tooltip.render();
        }
        int mainColor = zelix.hack.hacks.ClickGui.isLight ? ColorUtils.color(255, 255, 255, 255) : ColorUtils.color(0, 0, 0, 255);
        this.console.drawTextBox(zelix.hack.hacks.ClickGui.getColor(), mainColor);
        this.console.setTextColor(zelix.hack.hacks.ClickGui.getColor());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.console = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 0, 200, 14);
        this.console.setMaxStringLength(500);
        this.console.setText(this.title);
        this.console.setFocused(!Utils.isMoving((Entity)Wrapper.INSTANCE.player()));
        super.initGui();
    }

    public void updateScreen() {
        this.console.updateCursorCounter();
        clickGui.onUpdate();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.onGuiClosed();
    }

    void setTitle() {
        if (!this.console.getText().equals(AUTHOR_TEXT)) {
            this.title = "";
        }
    }

    private boolean handleKeyScroll(int key) {
        if (Utils.isMoving((Entity)Wrapper.INSTANCE.player())) {
            return false;
        }
        if (key == 17) {
            return clickGui.onMouseScroll(3);
        }
        if (key == 31) {
            return clickGui.onMouseScroll(-3);
        }
        return false;
    }

    private void handleKeyboard() {
        if (Keyboard.isCreated()) {
            Keyboard.enableRepeatEvents((boolean)true);
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (!this.handleKeyScroll(Keyboard.getEventKey())) {
                        this.console.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                    }
                    if (Keyboard.getEventKey() == 28) {
                        this.setTitle();
                        CommandManager.getInstance().runCommands("." + this.console.getText());
                        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)null);
                        FileManager.saveHacks();
                        FileManager.saveClickGui();
                        continue;
                    }
                    if (Keyboard.getEventKey() == 1) {
                        this.setTitle();
                        Wrapper.INSTANCE.mc().displayGuiScreen(null);
                        FileManager.saveHacks();
                        FileManager.saveClickGui();
                        continue;
                    }
                    clickGui.onkeyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    continue;
                }
                clickGui.onKeyRelease(Keyboard.getEventKey(), Keyboard.getEventCharacter());
            }
        }
    }

    private void handleMouse() {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                int mouseX = Mouse.getEventX() * scaledResolution.getScaledWidth() / Wrapper.INSTANCE.mc().displayWidth;
                int mouseY = scaledResolution.getScaledHeight() - Mouse.getEventY() * scaledResolution.getScaledHeight() / Wrapper.INSTANCE.mc().displayHeight - 1;
                if (Mouse.getEventButton() == -1) {
                    clickGui.onMouseScroll(Mouse.getEventDWheel() / 100 * 3);
                    clickGui.onMouseUpdate(mouseX, mouseY);
                    ClickGuiScreen.mouse[0] = mouseX;
                    ClickGuiScreen.mouse[1] = mouseY;
                    continue;
                }
                if (Mouse.getEventButtonState()) {
                    clickGui.onMouseClick(mouseX, mouseY, Mouse.getEventButton());
                    continue;
                }
                clickGui.onMouseRelease(mouseX, mouseY);
            }
        }
    }

    public void handleInput() throws IOException {
        try {
            int scale = Wrapper.INSTANCE.mc().gameSettings.guiScale;
            Wrapper.INSTANCE.mc().gameSettings.guiScale = 2;
            this.handleKeyboard();
            this.handleMouse();
            Wrapper.INSTANCE.mc().gameSettings.guiScale = scale;
            super.handleInput();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ChatUtils.error("Exception: handleInput");
        }
    }

    static {
        mouse = new int[2];
        tooltip = null;
    }
}

