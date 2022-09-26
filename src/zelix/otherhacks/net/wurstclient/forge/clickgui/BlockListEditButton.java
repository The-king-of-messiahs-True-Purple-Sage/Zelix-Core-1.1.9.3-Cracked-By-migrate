/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.EditBlockListScreen;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.BlockListSetting;

public final class BlockListEditButton
extends Component {
    private final BlockListSetting setting;
    private int buttonWidth;

    public BlockListEditButton(BlockListSetting setting) {
        this.setting = setting;
        FontRenderer fr = WMinecraft.getFontRenderer();
        this.buttonWidth = fr.getStringWidth("Edit...");
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        if (mouseX < this.getX() + this.getWidth() - this.buttonWidth - 4) {
            return;
        }
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new EditBlockListScreen(Minecraft.getMinecraft().currentScreen, this.setting));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        boolean hBox;
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int x3 = x2 - this.buttonWidth - 4;
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        boolean hText = hovering && mouseX < x3;
        boolean bl = hBox = hovering && mouseX >= x3;
        if (hText) {
            gui.setTooltip(this.setting.getDescription());
        }
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)(hBox ? opacity * 1.5f : opacity));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        FontRenderer fr = WMinecraft.getFontRenderer();
        String text = this.setting.getName() + ": " + this.setting.getBlockNames().size();
        fr.drawString(text, x1, y1 + 2, 0xF0F0F0);
        fr.drawString("Edit...", x3 + 2, y1 + 2, 0xF0F0F0);
        GL11.glDisable((int)3553);
    }

    @Override
    public int getDefaultWidth() {
        FontRenderer fr = WMinecraft.getFontRenderer();
        String text = this.setting.getName() + ": " + this.setting.getBlockNames().size();
        return fr.getStringWidth(text) + this.buttonWidth + 6;
    }

    @Override
    public int getDefaultHeight() {
        return 11;
    }
}

