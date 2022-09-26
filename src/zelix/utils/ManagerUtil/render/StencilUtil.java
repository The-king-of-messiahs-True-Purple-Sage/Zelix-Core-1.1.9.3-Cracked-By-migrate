/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package zelix.utils.ManagerUtil.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class StencilUtil {
    static Minecraft mc = Minecraft.getMinecraft();

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            StencilUtil.setupFBO(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)framebuffer.depthBuffer);
        int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)StencilUtil.mc.displayWidth, (int)StencilUtil.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)stencilDepthBufferID);
    }

    public static void initStencilToWrite() {
        mc.getFramebuffer().bindFramebuffer(false);
        StencilUtil.checkSetupFBO(mc.getFramebuffer());
        GL11.glClear((int)1024);
        GL11.glEnable((int)2960);
        GL11.glStencilFunc((int)519, (int)1, (int)1);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
    }

    public static void readStencilBuffer(int ref) {
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glStencilFunc((int)514, (int)ref, (int)1);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
    }

    public static void uninitStencilBuffer() {
        GL11.glDisable((int)2960);
    }
}

