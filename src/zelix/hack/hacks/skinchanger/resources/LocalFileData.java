/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.IImageBuffer
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.util.ResourceLocation
 */
package zelix.hack.hacks.skinchanger.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import zelix.utils.hooks.visual.ChatUtils;

public class LocalFileData
extends SimpleTexture {
    private BufferedImage bufferedImage;
    private boolean textureUploaded;
    private final IImageBuffer imageBuffer;
    private final File fileLocation;

    public LocalFileData(ResourceLocation textureLocation, File fileToLoad) {
        this(textureLocation, fileToLoad, null);
    }

    public LocalFileData(ResourceLocation textureLocation, File fileToLoad, IImageBuffer imageBuffer) {
        super(textureLocation);
        this.fileLocation = fileToLoad;
        this.imageBuffer = imageBuffer;
    }

    public void loadTexture(IResourceManager resourceManager) throws IOException {
        block6: {
            if (this.bufferedImage == null && this.textureLocation != null) {
                super.loadTexture(resourceManager);
            }
            if (this.fileLocation != null && this.fileLocation.isFile()) {
                try {
                    this.bufferedImage = ImageIO.read(this.fileLocation);
                    if (this.imageBuffer == null) break block6;
                    this.bufferedImage = this.imageBuffer.parseUserSkin(this.bufferedImage);
                    if (this.imageBuffer != null) {
                        this.imageBuffer.skinAvailable();
                    }
                }
                catch (IOException ex) {
                    ChatUtils.error("SkinChanger: Unable to read file.");
                }
            } else {
                ChatUtils.error("SkinChanger: File did not exist.");
            }
        }
    }

    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }

    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            if (this.textureLocation != null) {
                this.func_147631_c();
            }
            TextureUtil.uploadTextureImage((int)super.getGlTextureId(), (BufferedImage)this.bufferedImage);
            this.textureUploaded = true;
        }
    }
}

