/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraftforge.client.model.obj.OBJModel$Texture
 */
package zelix.gui.clickguis.gishcode.theme;

import java.util.HashMap;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.model.obj.OBJModel;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;

public class Theme {
    public FontRenderer fontRenderer;
    public OBJModel.Texture icons;
    private HashMap<ComponentType, ComponentRenderer> rendererHashMap = new HashMap();
    private String themeName;
    private int frameHeight = 15;

    public Theme(String themeName) {
        this.themeName = themeName;
    }

    public void addRenderer(ComponentType componentType, ComponentRenderer componentRenderer) {
        this.rendererHashMap.put(componentType, componentRenderer);
    }

    public HashMap<ComponentType, ComponentRenderer> getRenderer() {
        return this.rendererHashMap;
    }

    public String getThemeName() {
        return this.themeName;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public int getFrameHeight() {
        return this.frameHeight;
    }
}

