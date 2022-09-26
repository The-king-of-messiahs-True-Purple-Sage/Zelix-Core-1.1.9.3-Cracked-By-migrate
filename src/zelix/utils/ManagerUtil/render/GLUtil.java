/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package zelix.utils.ManagerUtil.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GLUtil {
    public static void render(int mode2, Runnable render) {
        GL11.glBegin((int)mode2);
        render.run();
        GL11.glEnd();
    }

    public static void setup2DRendering(Runnable f) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        f.run();
        GL11.glEnable((int)3553);
        GlStateManager.disableBlend();
    }

    public static void rotate(float x, float y, float rotate, Runnable f) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)0.0f);
        GlStateManager.rotate((float)rotate, (float)0.0f, (float)0.0f, (float)-1.0f);
        GlStateManager.translate((float)(-x), (float)(-y), (float)0.0f);
        f.run();
        GlStateManager.popMatrix();
    }
}

