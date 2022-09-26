/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package zelix.utils.ManagerUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public interface Utils {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final FontRenderer fr = Utils.mc.fontRendererObj;
}

