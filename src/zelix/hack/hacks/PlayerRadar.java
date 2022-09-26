/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 */
package zelix.hack.hacks;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class PlayerRadar
extends Hack {
    public PlayerRadar() {
        super("PlayerRadar", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Show all players around you.";
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        int y = 2;
        ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
        for (Entity o : Utils.getEntityList()) {
            if (!(o instanceof EntityPlayer)) continue;
            EntityPlayer e = (EntityPlayer)o;
            float range = Wrapper.INSTANCE.player().getDistanceToEntity((Entity)e);
            float health = e.getHealth();
            String heal = " \u00a72[" + RenderUtils.DF(health, 0) + "] ";
            heal = (double)health >= 12.0 ? " \u00a72[" + RenderUtils.DF(health, 0) + "] " : ((double)health >= 4.0 ? " \u00a76[" + RenderUtils.DF(health, 0) + "] " : " \u00a74[" + RenderUtils.DF(health, 0) + "] ");
            String name = e.getGameProfile().getName();
            String str = name + heal + "\u00a77[" + RenderUtils.DF(range, 0) + "]";
            int color = e.isInvisible() ? ColorUtils.color(155, 155, 155, 255) : ColorUtils.color(255, 255, 255, 255);
            Wrapper.INSTANCE.fontRenderer().drawString(str, sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(str), y, color);
            y += 12;
        }
        super.onRenderGameOverlay(event);
    }
}

