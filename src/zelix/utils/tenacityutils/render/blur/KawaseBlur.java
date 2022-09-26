/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.shader.Framebuffer
 */
package zelix.utils.tenacityutils.render.blur;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.shader.Framebuffer;
import zelix.utils.ManagerUtil.Utils;
import zelix.utils.tenacityutils.render.RenderUtil;
import zelix.utils.tenacityutils.render.ShaderUtil;

public class KawaseBlur
implements Utils {
    public static ShaderUtil kawaseDown = new ShaderUtil("Tenacity/Shaders/kawaseDown.frag");
    public static ShaderUtil kawaseUp = new ShaderUtil("Tenacity/Shaders/kawaseUp.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private static int currentIterations;
    private static final List<Framebuffer> framebufferList;

    public static void setupUniforms(float offset) {
        kawaseDown.setUniformf("offset", offset, offset);
        kawaseUp.setUniformf("offset", offset, offset);
    }

    private static void initFramebuffers(float iterations) {
        for (Framebuffer framebuffer : framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        framebufferList.clear();
        framebufferList.add(RenderUtil.createFrameBuffer(framebuffer));
        int i = 1;
        while ((float)i <= iterations) {
            Framebuffer framebuffer;
            framebuffer = new Framebuffer(KawaseBlur.mc.displayWidth, KawaseBlur.mc.displayHeight, false);
            framebufferList.add(RenderUtil.createFrameBuffer(framebuffer));
            ++i;
        }
    }

    public static void renderBlur(int iterations, int offset) {
        int i;
        if (currentIterations != iterations) {
            KawaseBlur.initFramebuffers(iterations);
            currentIterations = iterations;
        }
        KawaseBlur.renderFBO(framebufferList.get(1), KawaseBlur.mc.getFramebuffer().framebufferTexture, kawaseDown, offset);
        for (i = 1; i < iterations; ++i) {
            KawaseBlur.renderFBO(framebufferList.get(i + 1), KawaseBlur.framebufferList.get((int)i).framebufferTexture, kawaseDown, offset);
        }
        for (i = iterations; i > 1; --i) {
            KawaseBlur.renderFBO(framebufferList.get(i - 1), KawaseBlur.framebufferList.get((int)i).framebufferTexture, kawaseUp, offset);
        }
        mc.getFramebuffer().bindFramebuffer(true);
        RenderUtil.bindTexture(KawaseBlur.framebufferList.get((int)1).framebufferTexture);
        kawaseUp.init();
        kawaseUp.setUniformf("offset", offset, offset);
        kawaseUp.setUniformf("halfpixel", 0.5f / (float)KawaseBlur.mc.displayWidth, 0.5f / (float)KawaseBlur.mc.displayHeight);
        kawaseUp.setUniformi("inTexture", 0);
        ShaderUtil.drawQuads();
        kawaseUp.unload();
    }

    private static void renderFBO(Framebuffer framebuffer, int framebufferTexture, ShaderUtil shader, float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        shader.init();
        RenderUtil.bindTexture(framebufferTexture);
        shader.setUniformf("offset", offset, offset);
        shader.setUniformi("inTexture", 0);
        shader.setUniformf("halfpixel", 0.5f / (float)KawaseBlur.mc.displayWidth, 0.5f / (float)KawaseBlur.mc.displayHeight);
        ShaderUtil.drawQuads();
        shader.unload();
        framebuffer.unbindFramebuffer();
    }

    static {
        framebufferList = new ArrayList<Framebuffer>();
    }
}

