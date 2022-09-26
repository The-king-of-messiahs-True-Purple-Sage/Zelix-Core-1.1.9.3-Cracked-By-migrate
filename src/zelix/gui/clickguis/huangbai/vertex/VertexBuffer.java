/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.huangbai.vertex;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.huangbai.vertex.VertexFormat;

public class VertexBuffer {
    private int glBufferId;
    private final VertexFormat vertexFormat;
    private int count;

    public VertexBuffer(VertexFormat vertexFormatIn) {
        this.vertexFormat = vertexFormatIn;
    }

    public void bindBuffer() {
    }

    public void func_181722_a(ByteBuffer p_181722_1_) {
        this.bindBuffer();
        this.unbindBuffer();
        this.count = p_181722_1_.limit() / this.vertexFormat.getNextOffset();
    }

    public void drawArrays(int mode2) {
        GL11.glDrawArrays((int)mode2, (int)0, (int)this.count);
    }

    public void unbindBuffer() {
    }

    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            this.glBufferId = -1;
        }
    }
}

