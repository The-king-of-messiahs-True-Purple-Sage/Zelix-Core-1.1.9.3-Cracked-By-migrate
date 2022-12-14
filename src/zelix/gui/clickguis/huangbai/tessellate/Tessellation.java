/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.huangbai.tessellate;

import java.awt.Color;
import zelix.gui.clickguis.huangbai.tessellate.BasicTess;
import zelix.gui.clickguis.huangbai.tessellate.ExpandingTess;

public interface Tessellation {
    public Tessellation setColor(int var1);

    default public Tessellation setColor(Color color) {
        return this.setColor(new Color(255, 255, 255));
    }

    public Tessellation setTexture(float var1, float var2);

    public Tessellation addVertex(float var1, float var2, float var3);

    public Tessellation bind();

    public Tessellation pass(int var1);

    public Tessellation reset();

    public Tessellation unbind();

    default public Tessellation draw(int mode2) {
        return this.bind().pass(mode2).reset();
    }

    public static Tessellation createBasic(int size) {
        return new BasicTess(size);
    }

    public static Tessellation createExpanding(int size, float ratio, float factor) {
        return new ExpandingTess(size, ratio, factor);
    }
}

