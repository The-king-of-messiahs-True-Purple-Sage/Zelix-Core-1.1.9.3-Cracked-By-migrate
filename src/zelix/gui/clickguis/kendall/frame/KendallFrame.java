/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.kendall.frame;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.gui.clickguis.kendall.components.impls.KendallButton;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.font.FontLoaders;

public class KendallFrame {
    public float x = 0.0f;
    public float y = 0.0f;
    public HackCategory category;
    public boolean onMoving = false;
    private ArrayList<KendallButton> buttons = new ArrayList();
    public float moveX = 0.0f;
    public float moveY = 0.0f;

    public KendallFrame(HackCategory category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
    }

    public void initialize() {
        float y2 = this.y + 20.0f;
        this.buttons.clear();
        for (Hack hack : HackManager.getModulesInType(this.category)) {
            this.buttons.add(new KendallButton(hack, this.x, y2, this));
            y2 += 18.0f;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (OpenGlHelper.shadersSupported) {
            if (mc.entityRenderer.getShaderGroup() != null) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (KendallButton bt : this.buttons) {
            bt.onClick(mouseX, mouseY, mouseButton);
        }
        if (KendallFrame.isHovered(this.x, this.y, this.x + 100.0f, this.y + 20.0f, mouseX, mouseY) && mouseButton == 0) {
            for (KendallFrame frame : ClickGui.KendallMyGod.frames) {
                if (!frame.onMoving) continue;
                System.out.println(frame.category.name() + " MOVING");
                return;
            }
            this.onMoving = true;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.onMoving = false;
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public void render(int mouseX, int mouseY) {
        if (KendallFrame.isHovered(this.x, this.y, this.x + 100.0f, this.y + 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0) && this.onMoving) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = (float)mouseX - this.x;
                this.moveY = (float)mouseY - this.y;
            } else {
                this.x = (float)mouseX - this.moveX;
                this.y = (float)mouseY - this.moveY;
                float y2 = this.y + 20.0f;
                for (KendallButton bt : this.buttons) {
                    bt.y = y2;
                    y2 += 18.0f;
                }
            }
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        if (this.x < 0.0f) {
            this.x = 0.0f;
        } else if (this.y < 0.0f) {
            this.y = 0.0f;
        }
        KendallButton.drawColor = -1;
        RenderUtil.drawRoundRect_up(this.x, this.y, (double)this.x + 100.0, (double)this.y + 20.0, 2, -15329770);
        FontLoaders.default14.drawStringWithShadow(this.category.name(), this.x + 8.0f, this.y + 8.0f, KendallButton.drawColor);
        for (KendallButton bt : this.buttons) {
            if (bt.equals(this.buttons.get(this.buttons.size() - 1))) {
                bt.onRender(mouseX, mouseY, true);
                continue;
            }
            bt.onRender(mouseX, mouseY, false);
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }
}

