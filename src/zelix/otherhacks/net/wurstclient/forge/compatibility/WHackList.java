/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryBuilder
 */
package zelix.otherhacks.net.wurstclient.forge.compatibility;

import java.util.Collection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import zelix.otherhacks.net.wurstclient.forge.Hack;

public abstract class WHackList {
    private static IForgeRegistry<Hack> registry;

    public WHackList() {
        if (registry != null) {
            throw new IllegalStateException("Multiple instances of HackList!");
        }
        RegistryBuilder registryBuilder = new RegistryBuilder();
        registryBuilder.setName(new ResourceLocation("forgewurst", "hax"));
        registryBuilder.setType(Hack.class);
        registryBuilder.disableSaving();
        registry = registryBuilder.create();
    }

    protected final <T extends Hack> T register(T hack) {
        hack.setRegistryName("forgewurst", hack.getName().toLowerCase());
        registry.register(hack);
        return hack;
    }

    public final IForgeRegistry<Hack> getRegistry() {
        return registry;
    }

    public final Collection<Hack> getValues() {
        try {
            return registry.getValuesCollection();
        }
        catch (NoSuchMethodError e) {
            return registry.getValues();
        }
    }

    public final Hack get(String name) {
        ResourceLocation location = new ResourceLocation("forgewurst", name.toLowerCase());
        return (Hack)registry.getValue(location);
    }
}

