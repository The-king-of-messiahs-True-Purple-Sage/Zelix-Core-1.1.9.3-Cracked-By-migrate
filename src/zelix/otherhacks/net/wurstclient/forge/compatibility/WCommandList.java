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
import zelix.otherhacks.net.wurstclient.forge.Command;

public abstract class WCommandList {
    private static IForgeRegistry<Command> registry;

    public WCommandList() {
        if (registry != null) {
            throw new IllegalStateException("Multiple instances of CommandList!");
        }
        RegistryBuilder registryBuilder = new RegistryBuilder();
        registryBuilder.setName(new ResourceLocation("forgewurst", "cmds"));
        registryBuilder.setType(Command.class);
        registryBuilder.disableSaving();
        registry = registryBuilder.create();
    }

    protected final <T extends Command> T register(T cmd) {
        cmd.setRegistryName("forgewurst", cmd.getName().toLowerCase());
        registry.register(cmd);
        return cmd;
    }

    public final IForgeRegistry<Command> getRegistry() {
        return registry;
    }

    public final Collection<Command> getValues() {
        try {
            return registry.getValuesCollection();
        }
        catch (NoSuchMethodError e) {
            return registry.getValues();
        }
    }

    public final Command get(String name) {
        ResourceLocation location = new ResourceLocation("forgewurst", name.toLowerCase());
        return (Command)registry.getValue(location);
    }
}

