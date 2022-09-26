/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.Logger
 */
package zelix.hack.hacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.ReflectionHelper;
import zelix.utils.hooks.visual.ChatUtils;

public class AntiItemLag
extends Hack {
    public static final Logger logger = FMLCommonHandler.instance().getFMLLogger();
    public int INT_ITEM_LAG_MAX = 128;
    public int INT_ITEM_LAG_DOWN_TO = 64;
    public Configuration configuration;
    public boolean antiItemLag = true;

    public AntiItemLag() {
        super("AntiItemLag", HackCategory.ANOTHER);
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        this.updateAntiItemLag();
        super.onPlayerTick(event);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.updateAntiItemLag();
        super.onClientTick(event);
    }

    private void updateAntiItemLag() {
        int i;
        ArrayList<Entity> targetList;
        ArrayList list1;
        if (!this.antiItemLag) {
            return;
        }
        HashMap map = new HashMap();
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        if (worldClient == null) {
            return;
        }
        List list = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        if (list == null) {
            return;
        }
        for (int j = 0; j < list.size(); ++j) {
            Entity entity1;
            Item item;
            Entity entity = (Entity)list.get(j);
            Item item2 = item = entity instanceof EntityItem ? ((EntityItem)entity).getEntityItem().getItem() : null;
            if (item == null) continue;
            list1 = (ArrayList)map.get(item);
            if (list1 == null) {
                list1 = new ArrayList();
                map.put(item, list1);
            }
            targetList = null;
            for (i = 0; i < list1.size() && !(entity.getDistanceSqToEntity(entity1 = (Entity)(targetList = (List)list1.get(i)).get(0)) <= 1.0); ++i) {
                targetList = null;
            }
            if (targetList == null) {
                targetList = new ArrayList<Entity>();
                list1.add(targetList);
            }
            targetList.add(entity);
        }
        int removeCount = 0;
        for (Map.Entry entry : map.entrySet()) {
            list1 = (List)entry.getValue();
            targetList = null;
            for (i = 0; i < list1.size(); ++i) {
                targetList = (ArrayList<Entity>)list1.get(i);
                this.INT_ITEM_LAG_MAX = this.configuration.get("general", "INT_ITEM_LAG_MAX", this.INT_ITEM_LAG_MAX).getInt();
                this.INT_ITEM_LAG_DOWN_TO = this.configuration.get("general", "INT_ITEM_LAG_DOWN_TO", this.INT_ITEM_LAG_DOWN_TO).getInt();
                if (targetList.size() <= this.INT_ITEM_LAG_MAX) continue;
                for (int j = this.INT_ITEM_LAG_DOWN_TO - 1; j < targetList.size(); ++j) {
                    ++removeCount;
                    Entity entityIn = (Entity)targetList.get(j);
                    if (entityIn instanceof EntityPlayer) {
                        worldClient.playerEntities.remove(entityIn);
                        worldClient.updateAllPlayersSleepingFlag();
                    }
                    int x = entityIn.chunkCoordX;
                    int z = entityIn.chunkCoordZ;
                    Method method = ReflectionHelper.findMethod(World.class, worldClient, new String[]{"isChunkLoaded", "func_175680_a"}, Integer.TYPE, Integer.TYPE, Boolean.TYPE);
                    method.setAccessible(true);
                    boolean isLoad = false;
                    try {
                        isLoad = (Boolean)method.invoke((Object)worldClient, new Integer(x), new Integer(z), new Boolean(true));
                    }
                    catch (IllegalAccessException e) {
                        logger.log(Level.INFO, e.getMessage());
                    }
                    catch (IllegalArgumentException e) {
                        logger.log(Level.INFO, e.getMessage());
                    }
                    catch (InvocationTargetException e) {
                        logger.log(Level.INFO, e.getMessage());
                    }
                    catch (Exception e) {
                        logger.log(Level.INFO, e.getMessage());
                    }
                    if (entityIn.addedToChunk && isLoad) {
                        worldClient.getChunkFromChunkCoords(x, z).removeEntity(entityIn);
                    }
                    worldClient.loadedEntityList.remove(entityIn);
                }
            }
        }
        if (removeCount > 0) {
            ChatUtils.message("antiItemLag: " + removeCount);
        }
    }
}

