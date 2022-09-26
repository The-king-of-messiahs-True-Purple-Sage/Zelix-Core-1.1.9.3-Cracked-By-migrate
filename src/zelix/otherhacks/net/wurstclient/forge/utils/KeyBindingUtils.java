/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import java.lang.reflect.Field;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;

public final class KeyBindingUtils {
    private static ForgeWurst wurst = ForgeWurst.getForgeWurst();

    public static void setPressed(KeyBinding binding, boolean pressed) {
        try {
            Field field = binding.getClass().getDeclaredField(wurst.isObfuscated() ? "field_74513_e" : "pressed");
            field.setAccessible(true);
            field.setBoolean(binding, pressed);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetPressed(KeyBinding binding) {
        KeyBindingUtils.setPressed(binding, GameSettings.isKeyDown((KeyBinding)binding));
    }
}

