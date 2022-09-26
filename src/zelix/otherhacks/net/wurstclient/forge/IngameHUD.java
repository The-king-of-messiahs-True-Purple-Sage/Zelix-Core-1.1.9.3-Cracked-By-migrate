/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.HackList;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGuiScreen;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public final class IngameHUD {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final HackList hackList;
    private final ClickGui clickGui;

    public IngameHUD(HackList hackList, ClickGui clickGui) {
        this.hackList = hackList;
        this.clickGui = clickGui;
    }

    @SubscribeEvent
    public void onRenderGUI(RenderGameOverlayEvent.Post event) {
        int textColor;
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || this.mc.gameSettings.showDebugInfo) {
            return;
        }
        boolean blend = GL11.glGetBoolean((int)3042);
        this.clickGui.updateColors();
        if (this.hackList.rainbowUiHack.isEnabled()) {
            float[] acColor = this.clickGui.getAcColor();
            textColor = (int)(acColor[0] * 256.0f) << 16 | (int)(acColor[1] * 256.0f) << 8 | (int)(acColor[2] * 256.0f);
        } else {
            textColor = 0xFFFFFF;
        }
        GL11.glPushMatrix();
        GL11.glScaled((double)1.33333333, (double)1.33333333, (double)1.0);
        WMinecraft.getFontRenderer().drawStringWithShadow("ForgeWurst v0.11 by H2Eng", 3.0f, 3.0f, textColor);
        GL11.glPopMatrix();
        int y = 19;
        ArrayList<Hack> hacks = new ArrayList<Hack>();
        hacks.addAll(this.hackList.getValues());
        hacks.sort(Comparator.comparing(Hack::getName));
        for (Hack hack : hacks) {
            if (!hack.isEnabled()) continue;
            WMinecraft.getFontRenderer().drawStringWithShadow(hack.getRenderName(), 2.0f, (float)y, textColor);
            y += 9;
        }
        if (!(this.mc.currentScreen instanceof ClickGuiScreen)) {
            this.clickGui.renderPinnedWindows(event.getPartialTicks());
        }
        if (blend) {
            GL11.glEnable((int)3042);
        } else {
            GL11.glDisable((int)3042);
        }
    }
}

