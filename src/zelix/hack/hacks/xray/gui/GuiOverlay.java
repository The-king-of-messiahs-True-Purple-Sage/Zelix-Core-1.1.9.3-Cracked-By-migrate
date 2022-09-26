/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.toasts.IToast
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package zelix.hack.hacks.xray.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.xray.AntiAntiXray;
import zelix.hack.hacks.xray.xray.Controller;

public class GuiOverlay {
    public static String XrayStr = "";
    private static final ResourceLocation circle = new ResourceLocation("xray:textures/gui/circle.png");

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void RenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        int width = event.getResolution().getScaledWidth();
        for (int i = 0; i < AntiAntiXray.jobs.size(); ++i) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(width - 160), (float)(i * 32), (float)0.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(IToast.field_193654_a);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(0, 0, 0, 96, 160, 32);
            XRay.mc.fontRendererObj.drawStringWithShadow(I18n.format((String)"xray.toast.title", (Object[])new Object[0]), 6.0f, 6.0f, 0);
            XRay.mc.fontRendererObj.drawStringWithShadow(AntiAntiXray.jobs.get((int)i).refresher.getProcessText(), 6.0f, 18.0f, 0);
            GlStateManager.popMatrix();
        }
        if (!Controller.drawOres() || !Configuration.showOverlay) {
            XrayStr = "";
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color((float)0.0f, (float)255.0f, (float)0.0f, (float)30.0f);
        XRay.mc.renderEngine.bindTexture(circle);
        Gui.drawModalRectWithCustomSizedTexture((int)5, (int)5, (float)0.0f, (float)0.0f, (int)5, (int)5, (float)5.0f, (float)5.0f);
        GlStateManager.disableBlend();
        XrayStr = "Rendering";
        if (Configuration.freeze) {
            GlStateManager.enableBlend();
            GlStateManager.color((float)0.0f, (float)255.0f, (float)0.0f, (float)30.0f);
            XRay.mc.renderEngine.bindTexture(circle);
            Gui.drawModalRectWithCustomSizedTexture((int)5, (int)17, (float)0.0f, (float)0.0f, (int)5, (int)5, (float)5.0f, (float)5.0f);
            GlStateManager.disableBlend();
            XrayStr = "Rendering Freeze";
        }
        GlStateManager.popMatrix();
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        double zLevel = 0.0;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.func_181662_b((double)x, (double)(y + height), zLevel).func_187315_a((double)((float)textureX * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).func_181675_d();
        bufferbuilder.func_181662_b((double)(x + width), (double)(y + height), zLevel).func_187315_a((double)((float)(textureX + width) * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).func_181675_d();
        bufferbuilder.func_181662_b((double)(x + width), (double)y, zLevel).func_187315_a((double)((float)(textureX + width) * 0.00390625f), (double)((float)textureY * 0.00390625f)).func_181675_d();
        bufferbuilder.func_181662_b((double)x, (double)y, zLevel).func_187315_a((double)((float)textureX * 0.00390625f), (double)((float)textureY * 0.00390625f)).func_181675_d();
        tessellator.draw();
    }
}

