/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import zelix.Core;
import zelix.hack.Hack;
import zelix.hack.HackCategory;

public class SelfDestruct
extends Hack {
    public static boolean isDes;

    public SelfDestruct() {
        super("SelfDestruct", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(null);
        for (Hack m : Core.hackManager.getHacks()) {
            m.setToggled(false);
        }
        MinecraftForge.EVENT_BUS.unregister((Object)Core.eventsHandler);
        FMLCommonHandler.instance().bus().unregister((Object)Core.eventsHandler);
        isDes = true;
        super.onEnable();
    }
}

