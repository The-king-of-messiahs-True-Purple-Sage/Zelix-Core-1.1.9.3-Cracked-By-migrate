/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.opengl.GL20
 */
package zelix.utils.tenacityutils.render.blur;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import zelix.utils.ManagerUtil.Utils;
import zelix.utils.ManagerUtil.misc.MathUtils;
import zelix.utils.tenacityutils.render.RenderUtil;
import zelix.utils.tenacityutils.render.ShaderUtil;

public class BloomUtil
implements Utils {
    public static ShaderUtil gaussianBloom = new ShaderUtil("Tenacity/Shaders/bloom.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public static void renderBlur(int sourceTexture, int radius, int offset) {
        framebuffer = RenderUtil.createFrameBuffer(framebuffer);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc((int)516, (float)0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer((int)256);
        for (int i = 0; i <= radius; ++i) {
            weightBuffer.put(MathUtils.calculateGaussianValue(i, radius));
        }
        weightBuffer.rewind();
        RenderUtil.setAlphaLimit(0.0f);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        gaussianBloom.init();
        BloomUtil.setupUniforms(radius, offset, 0, weightBuffer);
        RenderUtil.bindTexture(sourceTexture);
        ShaderUtil.drawQuads();
        gaussianBloom.unload();
        framebuffer.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        gaussianBloom.init();
        BloomUtil.setupUniforms(radius, 0, offset, weightBuffer);
        GL13.glActiveTexture((int)34000);
        RenderUtil.bindTexture(sourceTexture);
        GL13.glActiveTexture((int)33984);
        RenderUtil.bindTexture(BloomUtil.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        gaussianBloom.unload();
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.bindTexture((int)0);
    }

    public static void setupUniforms(int radius, int directionX, int directionY, FloatBuffer weights) {
        gaussianBloom.setUniformi("inTexture", 0);
        gaussianBloom.setUniformi("textureToCheck", 16);
        gaussianBloom.setUniformf("radius", radius);
        gaussianBloom.setUniformf("texelSize", 1.0f / (float)BloomUtil.mc.displayWidth, 1.0f / (float)BloomUtil.mc.displayHeight);
        gaussianBloom.setUniformf("direction", directionX, directionY);
        GL20.glUniform1((int)gaussianBloom.getUniform("weights"), (FloatBuffer)weights);
    }
}

