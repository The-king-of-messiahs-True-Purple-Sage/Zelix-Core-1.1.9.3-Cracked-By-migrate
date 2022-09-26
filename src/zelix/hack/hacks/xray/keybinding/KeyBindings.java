/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 */
package zelix.hack.hacks.xray.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {
    static final int keyIndex_toggleXray = 0;
    static final int keyIndex_showXrayMenu = 1;
    static final int keyIndex_scan = 2;
    static final int keyIndex_removeblock = 3;
    static final int keyIndex_freeze = 4;
    private static final int[] keyBind_keyValues = new int[]{157, 44, 34, 47, 49};
    private static final String[] keyBind_descriptions = new String[]{"Start Render", "Open Controller", "Scan Ore", "Remove Blocks", "Freeze"};
    static KeyBinding[] keyBind_keys = null;

    public static void setup() {
        keyBind_keys = new KeyBinding[keyBind_descriptions.length];
        for (int i = 0; i < keyBind_descriptions.length; ++i) {
            KeyBindings.keyBind_keys[i] = new KeyBinding(keyBind_descriptions[i], keyBind_keyValues[i], "X-Ray");
            ClientRegistry.registerKeyBinding((KeyBinding)keyBind_keys[i]);
        }
    }
}

