/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.visual.font;

import java.awt.Font;
import java.io.File;
import zelix.utils.visual.font.CFontRenderer;

public abstract class FontLoaders {
    public static CFontRenderer kiona12 = new CFontRenderer(FontLoaders.getKiona(12), true, true);
    public static CFontRenderer kiona14 = new CFontRenderer(FontLoaders.getKiona(14), true, true);
    public static CFontRenderer kiona16 = new CFontRenderer(FontLoaders.getKiona(16), true, true);
    public static CFontRenderer kiona18 = new CFontRenderer(FontLoaders.getKiona(18), true, true);
    public static CFontRenderer kiona20 = new CFontRenderer(FontLoaders.getKiona(20), true, true);
    public static CFontRenderer kiona22 = new CFontRenderer(FontLoaders.getKiona(22), true, true);
    public static CFontRenderer kiona24 = new CFontRenderer(FontLoaders.getKiona(24), true, true);
    public static CFontRenderer kiona26 = new CFontRenderer(FontLoaders.getKiona(26), true, true);
    public static CFontRenderer kiona28 = new CFontRenderer(FontLoaders.getKiona(28), true, true);
    public static CFontRenderer kiona30 = new CFontRenderer(FontLoaders.getKiona(30), true, true);
    public static CFontRenderer kiona32 = new CFontRenderer(FontLoaders.getKiona(32), true, true);
    public static CFontRenderer kiona34 = new CFontRenderer(FontLoaders.getKiona(34), true, true);
    public static CFontRenderer kiona36 = new CFontRenderer(FontLoaders.getKiona(36), true, true);
    public static CFontRenderer default16 = new CFontRenderer(new Font("default", 0, 16), true, true);
    public static CFontRenderer default18 = new CFontRenderer(new Font("default", 0, 18), true, true);
    public static CFontRenderer default14 = new CFontRenderer(new Font("default", 0, 14), true, true);
    public static CFontRenderer default30 = new CFontRenderer(new Font("default", 0, 30), true, true);
    public static CFontRenderer SFB6 = new CFontRenderer(FontLoaders.getSFB(6), true, true);
    public static CFontRenderer default20 = new CFontRenderer(new Font("default", 0, 20), true, true);
    public static CFontRenderer SFB8 = new CFontRenderer(FontLoaders.getSFB(8), true, true);
    public static CFontRenderer SFB9 = new CFontRenderer(FontLoaders.getSFB(9), true, true);
    public static CFontRenderer SFB11 = new CFontRenderer(FontLoaders.getSFB(11), true, true);
    public static CFontRenderer icon18 = new CFontRenderer(FontLoaders.getIcon(18), true, true);
    public static CFontRenderer icon24 = new CFontRenderer(FontLoaders.getIcon(24), true, true);
    public static CFontRenderer CN18 = new CFontRenderer(FontLoaders.getCN(18), true, true);

    private static Font getKiona(int size) {
        Font font = new Font("default", 0, size);
        try {
            File a = new File("C:\\Thanatos\\font.ttf");
            font = Font.createFont(0, a);
            font = font.deriveFont(0, size);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return font;
    }

    public static Font getCN(int size) {
        Font font = new Font("default", 0, size);
        try {
            File a = new File("C:\\Zelix\\Chinese.ttf");
            font = Font.createFont(0, a);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getSFB(int size) {
        Font font = new Font("default", 0, size);
        try {
            File a = new File("C:\\Thanatos\\SF-UI-Display-Bold.otf");
            font = Font.createFont(0, a);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getIcon(int size) {
        Font font = new Font("default", 0, size);
        try {
            File a = new File("C:\\Thanatos\\icon.ttf");
            font = Font.createFont(0, a);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            font = new Font("default", 0, size);
        }
        return font;
    }
}

