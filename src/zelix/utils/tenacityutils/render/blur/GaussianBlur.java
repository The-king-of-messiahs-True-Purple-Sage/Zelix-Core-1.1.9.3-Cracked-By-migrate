/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL20
 */
package zelix.utils.tenacityutils.render.blur;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import zelix.utils.ManagerUtil.Utils;
import zelix.utils.ManagerUtil.misc.MathUtils;
import zelix.utils.tenacityutils.render.RenderUtil;
import zelix.utils.tenacityutils.render.ShaderUtil;

public class GaussianBlur
implements Utils {
    public static ShaderUtil blurShader = new ShaderUtil("Tenacity/Shaders/gaussian.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public static void setupUniforms(float dir1, float dir2, float radius) {
        blurShader.setUniformi("textureIn", 0);
        blurShader.setUniformf("texelSize", 1.0f / (float)GaussianBlur.mc.displayWidth, 1.0f / (float)GaussianBlur.mc.displayHeight);
        blurShader.setUniformf("direction", dir1, dir2);
        blurShader.setUniformf("radius", radius);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer((int)256);
        int i = 0;
        while ((float)i <= radius) {
            weightBuffer.put(MathUtils.calculateGaussianValue(i, radius / 2.0f));
            ++i;
        }
        weightBuffer.rewind();
        GL20.glUniform1((int)blurShader.getUniform("weights"), (FloatBuffer)weightBuffer);
    }

    public static void renderBlur(float radius) {
        GlStateManager.enableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        framebuffer = RenderUtil.createFrameBuffer(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(1.0f, 0.0f, radius);
        RenderUtil.bindTexture(GaussianBlur.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();
        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(0.0f, 1.0f, radius);
        RenderUtil.bindTexture(GaussianBlur.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        blurShader.unload();
        RenderUtil.resetColor();
        GlStateManager.bindTexture((int)0);
    }
}

