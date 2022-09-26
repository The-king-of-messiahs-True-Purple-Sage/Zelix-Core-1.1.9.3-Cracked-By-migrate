/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockChest$Type
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityMinecartChest
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package zelix.hack.hacks;

import java.util.ArrayDeque;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class ChestESP
extends Hack {
    private int maxChests = 1000;
    public boolean shouldInform = true;
    private TileEntityChest openChest;
    private ArrayDeque<TileEntityChest> emptyChests = new ArrayDeque();
    private ArrayDeque<TileEntityChest> nonEmptyChests = new ArrayDeque();
    private String[] chestClasses = new String[]{"TileEntityIronChest", "TileEntityGoldChest", "TileEntityDiamondChest", "TileEntityCopperChest", "TileEntitySilverChest", "TileEntityCrystalChest", "TileEntityObsidianChest", "TileEntityDirtChest"};

    public ChestESP() {
        super("ChestESP", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Allows you to see all of the chests around you.";
    }

    @Override
    public void onEnable() {
        this.shouldInform = true;
        this.emptyChests.clear();
        this.nonEmptyChests.clear();
        super.onEnable();
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        int i;
        int chests = 0;
        for (i = 0; i < Wrapper.INSTANCE.world().loadedTileEntityList.size(); ++i) {
            TileEntity tileEntity = (TileEntity)Wrapper.INSTANCE.world().loadedTileEntityList.get(i);
            if (chests >= this.maxChests) break;
            if (tileEntity instanceof TileEntityChest) {
                boolean trapped;
                ++chests;
                TileEntityChest chest = (TileEntityChest)tileEntity;
                boolean bl = trapped = chest.func_145980_j() == BlockChest.Type.TRAP;
                if (this.emptyChests.contains(tileEntity)) {
                    RenderUtils.drawBlockESP(chest.func_174877_v(), 0.25f, 0.25f, 0.25f);
                } else if (this.nonEmptyChests.contains(tileEntity)) {
                    if (trapped) {
                        RenderUtils.drawBlockESP(chest.func_174877_v(), 0.0f, 1.0f, 0.0f);
                    } else {
                        RenderUtils.drawBlockESP(chest.func_174877_v(), 1.0f, 0.0f, 0.0f);
                    }
                } else if (trapped) {
                    RenderUtils.drawBlockESP(chest.func_174877_v(), 0.0f, 1.0f, 0.0f);
                } else {
                    RenderUtils.drawBlockESP(chest.func_174877_v(), 1.0f, 0.0f, 0.0f);
                }
                if (trapped) {
                    RenderUtils.drawBlockESP(chest.func_174877_v(), 0.0f, 1.0f, 0.0f);
                    continue;
                }
                RenderUtils.drawBlockESP(chest.func_174877_v(), 1.0f, 0.0f, 0.0f);
                continue;
            }
            if (tileEntity instanceof TileEntityEnderChest) {
                ++chests;
                RenderUtils.drawBlockESP(((TileEntityEnderChest)tileEntity).func_174877_v(), 1.0f, 0.0f, 1.0f);
                continue;
            }
            try {
                for (String chestClass : this.chestClasses) {
                    Class<?> clazz = Class.forName("cpw.mods.ironchest.common.tileentity.chest." + chestClass);
                    if (clazz == null || !clazz.isInstance(tileEntity)) continue;
                    RenderUtils.drawBlockESP(tileEntity.func_174877_v(), 0.7f, 0.7f, 0.7f);
                }
                continue;
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        for (i = 0; i < Utils.getEntityList().size(); ++i) {
            Entity entity = Utils.getEntityList().get(i);
            if (chests >= this.maxChests) break;
            if (!(entity instanceof EntityMinecartChest)) continue;
            ++chests;
            RenderUtils.drawBlockESP(((EntityMinecartChest)entity).func_180425_c(), 1.0f, 1.0f, 1.0f);
        }
        if (chests >= this.maxChests && this.shouldInform) {
            ChatUtils.warning("To prevent lag, it will only show the first " + this.maxChests + " chests.");
            this.shouldInform = false;
        } else if (chests < this.maxChests) {
            this.shouldInform = true;
        }
        super.onRenderWorldLast(event);
    }
}

