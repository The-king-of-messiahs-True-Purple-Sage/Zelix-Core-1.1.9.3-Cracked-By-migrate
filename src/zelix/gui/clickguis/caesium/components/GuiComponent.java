/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

public interface GuiComponent {
    public void render(int var1, int var2, int var3, int var4, int var5);

    public void mouseClicked(int var1, int var2, int var3);

    public void keyTyped(int var1, char var2);

    public int getWidth();

    public int getHeight();
}

