/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.RenderUtils;

public class ItemESP
extends Hack {
    public ItemESP() {
        super("ItemESP", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Highlights nearby items.";
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Entity object : Utils.getEntityList()) {
            if (!(object instanceof EntityItem) && !(object instanceof EntityArrow)) continue;
            Entity item = object;
            RenderUtils.drawESP(item, 1.0f, 1.0f, 1.0f, 1.0f, event.getPartialTicks());
        }
        super.onRenderWorldLast(event);
    }
}

