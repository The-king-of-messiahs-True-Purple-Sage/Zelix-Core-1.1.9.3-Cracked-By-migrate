/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 */
package zelix;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import zelix.LoadClient;

@Mod(modid="zeer112c2d2", name="z", version="220707", acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]")
public class Test {
    public static final String MODID = "zeer112c2d2";
    public static final String NAME = "z";
    public static final String VERSION = "220707";

    public Test() {
        this.init(null);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent E) {
        LoadClient.RLoad("zelix.Core", "zelix.Core");
    }
}

