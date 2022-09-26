/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.otherhacks.net.wurstclient.forge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WForgeRegistryEntry;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public abstract class Hack
extends WForgeRegistryEntry<Hack> {
    protected static final ForgeWurst wurst = ForgeWurst.getForgeWurst();
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private final String description;
    private Category category;
    private final LinkedHashMap<String, Setting> settings = new LinkedHashMap();
    private boolean enabled;
    private final boolean stateSaved = !((Object)((Object)this)).getClass().isAnnotationPresent(DontSaveState.class);

    public Hack(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public final String getName() {
        return this.name;
    }

    public String getRenderName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final Category getCategory() {
        return this.category;
    }

    protected final void setCategory(Category category) {
        this.category = category;
    }

    public final Map<String, Setting> getSettings() {
        return Collections.unmodifiableMap(this.settings);
    }

    protected final void addSetting(Setting setting) {
        String key = setting.getName().toLowerCase();
        if (this.settings.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate setting: " + this.name + " " + key);
        }
        this.settings.put(key, setting);
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        if (this.stateSaved) {
            wurst.getHax().saveEnabledHacks();
        }
    }

    public final boolean isStateSaved() {
        return this.stateSaved;
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DontSaveState {
    }
}

