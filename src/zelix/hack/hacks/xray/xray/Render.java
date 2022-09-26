/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 */
package zelix.hack.hacks.xray.xray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.reference.block.BlockInfo;
import zelix.hack.hacks.xray.utils.Utils;

public class Render {
    public static List<BlockInfo> ores = Collections.synchronizedList(new ArrayList());
    private static final int GL_FRONT_AND_BACK = 1032;
    private static final int GL_LINE = 6913;
    private static final int GL_FILL = 6914;
    private static final int GL_LINES = 1;

    public static void drawOres(float playerX, float playerY, float playerZ) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.func_178180_c();
        Profile.BLOCKS.apply();
        buffer.func_178969_c((double)(-playerX), (double)(-playerY), (double)(-playerZ));
        ores.forEach(b -> {
            buffer.func_181668_a(1, DefaultVertexFormats.POSITION_COLOR);
            Utils.renderBlockBounding(buffer, b, (int)b.alpha);
            tessellator.draw();
        });
        buffer.func_178969_c(0.0, 0.0, 0.0);
        Profile.BLOCKS.clean();
    }

    private static enum Profile {
        BLOCKS{

            @Override
            public void apply() {
                GlStateManager.disableTexture2D();
                GlStateManager.disableDepth();
                GlStateManager.depthMask((boolean)false);
                GlStateManager.func_187409_d((int)1032, (int)6913);
                GlStateManager.func_187401_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.enableBlend();
                GlStateManager.func_187441_d((float)((float)Configuration.outlineThickness));
            }

            @Override
            public void clean() {
                GlStateManager.func_187409_d((int)1032, (int)6914);
                GlStateManager.disableBlend();
                GlStateManager.enableDepth();
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableTexture2D();
            }
        }
        ,
        ENTITIES{

            @Override
            public void apply() {
            }

            @Override
            public void clean() {
            }
        };


        public abstract void apply();

        public abstract void clean();
    }
}

