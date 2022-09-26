/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.client.shader.Shader
 *  net.minecraft.client.shader.ShaderGroup
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Timer
 */
package zelix.gui.clickguis.kendall;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import zelix.utils.Mappings;
import zelix.utils.ReflectionHelper;
import zelix.utils.Wrapper;

public class BLur {
    private static ShaderGroup blurShader;
    private static Framebuffer buffer;
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;

    private static void reinitShader() {
        blurShader.createBindFramebuffers(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        buffer = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, true);
        buffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static void draw(int x, int y, int width, int height, int blur, float radius) {
        try {
            blurShader = new ShaderGroup(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().getResourceManager(), Minecraft.getMinecraft().getFramebuffer(), new ResourceLocation("shaders/post/blur.json"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
            BLur.reinitShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        List listShaders = (List)ReflectionHelper.getPrivateValue(ShaderGroup.class, blurShader, "listShaders");
        ((Shader)listShaders.get(0)).getShaderManager().getShaderUniform("BlurXY").set((float)x, (float)(factor3 - y - height));
        ((Shader)listShaders.get(1)).getShaderManager().getShaderUniform("BlurXY").set((float)x, (float)(factor3 - y - height));
        ((Shader)listShaders.get(0)).getShaderManager().getShaderUniform("BlurCoord").set((float)width, (float)height);
        ((Shader)listShaders.get(1)).getShaderManager().getShaderUniform("BlurCoord").set((float)width, (float)height);
        ((Shader)listShaders.get(0)).getShaderManager().getShaderUniform("Radius").set(radius);
        ((Shader)listShaders.get(1)).getShaderManager().getShaderUniform("Radius").set(radius);
        Timer timer = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), Mappings.timer);
        blurShader.loadShaderGroup(timer.field_194147_b);
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
    }

    static {
        lastScale = 0;
        lastScaleWidth = 0;
        lastScaleHeight = 0;
    }
}

