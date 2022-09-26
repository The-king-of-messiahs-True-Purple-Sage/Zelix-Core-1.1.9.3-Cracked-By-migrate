/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package zelix.managers;

import java.awt.Font;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import zelix.utils.hooks.visual.font.render.TTFFontRenderer;
import zelix.utils.hooks.visual.font.render.TextureData;

public class FontManager {
    private ResourceLocation darrow = new ResourceLocation("SF-UI-Display-Regular.otf");
    private TTFFontRenderer defaultFont;
    private FontManager instance;
    private HashMap<String, TTFFontRenderer> fonts = new HashMap();

    public FontManager getInstance() {
        return this.instance;
    }

    public TTFFontRenderer getFont(String key) {
        return this.fonts.getOrDefault(key, this.defaultFont);
    }

    public FontManager() {
        this.instance = this;
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(8);
        ConcurrentLinkedQueue textureQueue = new ConcurrentLinkedQueue();
        this.defaultFont = new TTFFontRenderer(executorService, textureQueue, new Font("Verdana", 0, 18));
        try {
            Font myFont;
            InputStream istream;
            ResourceLocation font;
            for (int i : new int[]{6, 7, 8, 10, 11, 12, 14}) {
                font = new ResourceLocation("rsaaaaaa", "Suffer-through-2.ttf");
                font.getResourcePath();
                istream = this.getClass().getResourceAsStream(font.getResourcePath());
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("SFR " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{6, 7, 8, 9, 11}) {
                font = new ResourceLocation("rsaaaaaa", "the-antter-2.ttf");
                istream = this.getClass().getResourceAsStream(font.getResourcePath());
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("SFB " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{6, 7, 8, 9, 11, 12}) {
                font = new ResourceLocation("rsaaaaaa", "SF-UI-Display-Bold.otf");
                font.getResourcePath();
                istream = this.getClass().getResourceAsStream(font.getResourcePath());
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("SFM " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{17, 10, 9, 8, 7, 6}) {
                InputStream istream2 = this.getClass().getResourceAsStream("assets/shadow/SF-UI-Display-Light.otf");
                Font myFont2 = Font.createFont(0, istream2);
                myFont2 = myFont2.deriveFont(0, i);
                this.fonts.put("SFL " + i, new TTFFontRenderer(executorService, textureQueue, myFont2));
            }
            for (int i : new int[]{19}) {
                font = new ResourceLocation("rsaaaaaa", "Jigsaw-Regular.otf");
                font.getResourcePath();
                istream = this.getClass().getResourceAsStream(font.getResourcePath());
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("JIGR " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            this.fonts.put("Verdana 12", new TTFFontRenderer(executorService, textureQueue, new Font("Verdana", 0, 12)));
            this.fonts.put("Verdana Bold 16", new TTFFontRenderer(executorService, textureQueue, new Font("Verdana Bold", 0, 16)));
            this.fonts.put("Verdana Bold 20", new TTFFontRenderer(executorService, textureQueue, new Font("Verdana Bold", 0, 20)));
        }
        catch (Exception exception) {
            // empty catch block
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                TextureData textureData = (TextureData)textureQueue.poll();
                GlStateManager.bindTexture((int)textureData.getTextureId());
                GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
                GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)textureData.getWidth(), (int)textureData.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)textureData.getBuffer());
            }
        }
    }
}

