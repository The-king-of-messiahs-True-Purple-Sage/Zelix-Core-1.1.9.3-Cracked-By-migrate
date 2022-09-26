/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraftforge.event.entity.player.EntityItemPickupEvent
 */
package zelix.hack.hacks;

import java.util.Iterator;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.PickupFilterManager;
import zelix.value.BooleanValue;

public class PickupFilter
extends Hack {
    public BooleanValue all = new BooleanValue("IgnoreAll", false);

    public PickupFilter() {
        super("PickupFilter", HackCategory.ANOTHER);
        this.addValue(this.all);
    }

    @Override
    public String getDescription() {
        return "Filters item picking.";
    }

    @Override
    public void onItemPickup(EntityItemPickupEvent event) {
        if (((Boolean)this.all.getValue()).booleanValue()) {
            event.setCanceled(true);
            return;
        }
        Iterator iterator = PickupFilterManager.items.iterator();
        while (iterator.hasNext()) {
            int itemId = (Integer)iterator.next();
            Item item = Item.getItemById((int)itemId);
            if (item == null || event.getItem().getEntityItem().getItem() != item) continue;
            event.setCanceled(true);
        }
        super.onItemPickup(event);
    }
}

