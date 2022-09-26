/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockRendererDispatcher
 *  net.minecraft.init.Blocks
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.init.Blocks;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WForgeBlockModelRenderer;
import zelix.otherhacks.net.wurstclient.forge.settings.BlockListSetting;

public final class XRayHack
extends Hack {
    public static final BlockListSetting blocks = new BlockListSetting("Blocks", Blocks.coal_ore, Blocks.iron_ore, Blocks.gold_ore, Blocks.diamond_ore);

    public XRayHack() {
        super("X-Ray", "Allows you to see ores through walls.");
        this.setCategory(Category.RENDER);
        this.addSetting(blocks);
        try {
            BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
            if (renderer.getClass().getName().equals("codechicken.lib.render.block.CCBlockRendererDispatcher")) {
                Field parentDispatcher = renderer.getClass().getDeclaredField("parentDispatcher");
                parentDispatcher.setAccessible(true);
                renderer = (BlockRendererDispatcher)parentDispatcher.get(renderer);
            }
            Field blockModelRenderer = renderer.getClass().getDeclaredField(ForgeWurst.getForgeWurst().isObfuscated() ? "field_175027_c" : "blockModelRenderer");
            blockModelRenderer.setAccessible(true);
            blockModelRenderer.set(renderer, (Object)new WForgeBlockModelRenderer(mc.func_184125_al()));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRenderName() {
        return "X-Wurst";
    }

    @Override
    protected void onEnable() {
        XRayHack.mc.renderGlobal.loadRenderers();
    }

    @Override
    protected void onDisable() {
        XRayHack.mc.renderGlobal.loadRenderers();
    }
}

