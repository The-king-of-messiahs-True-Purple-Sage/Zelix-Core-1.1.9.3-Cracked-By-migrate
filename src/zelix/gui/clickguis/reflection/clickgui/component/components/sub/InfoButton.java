/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.reflection.clickgui.component.components.sub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.components.Button;
import zelix.hack.Hack;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.EnumChatFormatting;

public class InfoButton
extends Component {
    private boolean hovered;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private Hack mod;

    public InfoButton(Button button, Hack mod, int offset) {
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + this.parent.parent.getWidth() * 1), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -14540254 : -15658735));
        GL11.glPushMatrix();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Info", (float)((this.parent.parent.getX() + 7) * 2), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            ChatUtils.message((Object)((Object)EnumChatFormatting.ITALIC) + this.mod.getDescription() + ".");
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}

