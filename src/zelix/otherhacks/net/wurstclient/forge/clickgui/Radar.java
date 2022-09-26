/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.hacks.RadarHack;

public final class Radar
extends Component {
    private final RadarHack hack;

    public Radar(RadarHack hack) {
        this.hack = hack;
        this.setWidth(this.getDefaultWidth());
        this.setHeight(this.getDefaultHeight());
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        boolean hovering;
        ClickGui gui = ForgeWurst.getForgeWurst().getGui();
        float[] bgColor = gui.getBgColor();
        float[] acColor = gui.getAcColor();
        float opacity = gui.getOpacity();
        int x1 = this.getX();
        int x2 = x1 + this.getWidth();
        int y1 = this.getY();
        int y2 = y1 + this.getHeight();
        int scroll = this.getParent().isScrollingEnabled() ? this.getParent().getScrollOffset() : 0;
        boolean bl = hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2 && mouseY >= -scroll && mouseY < this.getParent().getHeight() - 13 - scroll;
        if (hovering) {
            gui.setTooltip(null);
        }
        GL11.glColor4f((float)bgColor[0], (float)bgColor[1], (float)bgColor[2], (float)opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        double middleX = (double)(x1 + x2) / 2.0;
        double middleY = (double)(y1 + y2) / 2.0;
        GL11.glPushMatrix();
        GL11.glTranslated((double)middleX, (double)middleY, (double)0.0);
        EntityPlayerSP player = WMinecraft.getPlayer();
        if (!this.hack.isRotateEnabled()) {
            GL11.glRotated((double)(180.0f + player.rotationYaw), (double)0.0, (double)0.0, (double)1.0);
        }
        double xa1 = 0.0;
        double xa2 = 2.0;
        double xa3 = -2.0;
        double ya1 = -2.0;
        double ya2 = 2.0;
        double ya3 = 1.0;
        GL11.glColor4f((float)acColor[0], (float)acColor[1], (float)acColor[2], (float)opacity);
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)xa1, (double)ya1);
        GL11.glVertex2d((double)xa2, (double)ya2);
        GL11.glVertex2d((double)xa1, (double)ya3);
        GL11.glVertex2d((double)xa3, (double)ya2);
        GL11.glEnd();
        GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xa1, (double)ya1);
        GL11.glVertex2d((double)xa2, (double)ya2);
        GL11.glVertex2d((double)xa1, (double)ya3);
        GL11.glVertex2d((double)xa3, (double)ya2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)2832);
        GL11.glPointSize((float)2.0f);
        GL11.glBegin((int)0);
        for (Entity e : this.hack.getEntities()) {
            double diffX = e.prevPosX + (e.posX - e.prevPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
            double diffZ = e.prevPosZ + (e.posZ - e.prevPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);
            double distance = Math.sqrt(diffX * diffX + diffZ * diffZ) * ((double)this.getWidth() * 0.5 / this.hack.getRadius());
            double neededRotation = Math.toDegrees(Math.atan2(diffZ, diffX));
            double angle = this.hack.isRotateEnabled() ? Math.toRadians((double)player.rotationYaw - neededRotation - 90.0) : Math.toRadians(180.0 - neededRotation - 90.0);
            double renderX = Math.sin(angle) * distance;
            double renderY = Math.cos(angle) * distance;
            if (Math.abs(renderX) > (double)this.getWidth() / 2.0 || Math.abs(renderY) > (double)this.getHeight() / 2.0) continue;
            int color = e instanceof EntityPlayer ? 0xFF0000 : (e instanceof IMob ? 0xFF8000 : (e instanceof EntityAnimal || e instanceof EntityAmbientCreature || e instanceof EntityWaterMob ? 65280 : 0x808080));
            GL11.glColor4f((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)1.0f);
            GL11.glVertex2d((double)(middleX + renderX), (double)(middleY + renderY));
        }
        GL11.glEnd();
    }

    @Override
    public int getDefaultWidth() {
        return 96;
    }

    @Override
    public int getDefaultHeight() {
        return 96;
    }
}

