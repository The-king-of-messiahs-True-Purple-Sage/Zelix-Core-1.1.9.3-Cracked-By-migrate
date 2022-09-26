/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 */
package zelix.managers;

import java.util.Iterator;
import java.util.LinkedList;
import net.minecraft.item.Item;
import zelix.managers.FileManager;
import zelix.utils.hooks.visual.ChatUtils;

public class PickupFilterManager {
    public static LinkedList<Integer> items = new LinkedList();

    public static void addItem(int id) {
        try {
            if (Item.getItemById((int)id) == null) {
                ChatUtils.error("Item is null.");
                return;
            }
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                int itemId = (Integer)iterator.next();
                if (itemId != id) continue;
                ChatUtils.error("Item already added.");
                return;
            }
            items.add(id);
            FileManager.savePickupFilter();
            ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77NAME: \u00a73%s \u00a77- ADDED.", id, Item.getItemById((int)id).getUnlocalizedName()));
        }
        catch (Exception ex) {
            ChatUtils.error(ex.getMessage());
        }
    }

    public static void removeItem(int id) {
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) {
            int itemId = (Integer)iterator.next();
            if (itemId != id) continue;
            items.remove((Object)id);
            ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77- REMOVED.", id));
            FileManager.savePickupFilter();
            return;
        }
        ChatUtils.error("Item not found.");
    }

    public static void clear() {
        if (items.isEmpty()) {
            return;
        }
        items.clear();
        FileManager.savePickupFilter();
        ChatUtils.message("\u00a7dPickupFilter \u00a77list clear.");
    }
}

