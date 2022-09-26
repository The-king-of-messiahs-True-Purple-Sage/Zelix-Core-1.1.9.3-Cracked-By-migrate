/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.tenacity.Frame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.utils.tenacityutils.render.RenderUtil;

public class tenacity {
    public ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    public int width = 335;
    public int height = 235;
    public boolean onMoving = false;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    public int x = this.scaledResolution.getScaledWidth() / 2 - this.width / 2;
    public int y = this.scaledResolution.getScaledHeight() / 2 - this.height / 2;

    public void initialize() {
        Minecraft mc = Minecraft.getMinecraft();
        if (OpenGlHelper.shadersSupported) {
            if (mc.entityRenderer.getShaderGroup() != null) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (tenacity.isHovered(this.x, this.y, this.x + this.width, this.y + this.height, mouseX, mouseY) && mouseButton == 0) {
            this.onMoving = true;
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.onMoving = false;
    }

    public void render(int mouseX, int mouseY) {
        if (tenacity.isHovered(this.x, this.y, this.x + this.width, this.y + this.height, mouseX, mouseY) && Mouse.isButtonDown((int)0) && this.onMoving) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = mouseX - this.x;
                this.moveY = mouseY - this.y;
            } else {
                this.x = (int)((float)mouseX - this.moveX);
                this.y = (int)((float)mouseY - this.moveY);
                float f = this.y + 20;
            }
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        RenderUtil.renderRoundedRect(this.x, this.y, this.width, this.height, 10.0f, -14869217);
        FontLoaders.default20.drawStringWithShadow("Zelix CloudConfig", this.x + 14, this.y + 19, -1);
        RenderUtil.drawBorderedRect(this.x + 14, this.y + 37, 254.0f, 52.0f, 1.0f, -15526892, -15526892);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }
}

