/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  net.minecraft.block.Block
 */
package zelix.otherhacks.net.wurstclient.forge.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.BlockListEditButton;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;

public final class BlockListSetting
extends Setting {
    private final ArrayList<String> blockNames = new ArrayList();
    private final String[] defaultNames;

    public BlockListSetting(String name, String description, Block ... blocks) {
        super(name, description);
        ((Stream)Arrays.stream(blocks).parallel()).map(b -> BlockUtils.getName(b)).distinct().sorted().forEachOrdered(s -> this.blockNames.add((String)s));
        this.defaultNames = this.blockNames.toArray(new String[0]);
    }

    public BlockListSetting(String name, Block ... blocks) {
        this(name, (String)null, blocks);
    }

    public List<String> getBlockNames() {
        return Collections.unmodifiableList(this.blockNames);
    }

    public void add(Block block) {
        String name = BlockUtils.getName(block);
        if (Collections.binarySearch(this.blockNames, name) >= 0) {
            return;
        }
        this.blockNames.add(name);
        Collections.sort(this.blockNames);
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    public void remove(int index) {
        if (index < 0 || index >= this.blockNames.size()) {
            return;
        }
        this.blockNames.remove(index);
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    public void resetToDefaults() {
        this.blockNames.clear();
        this.blockNames.addAll(Arrays.asList(this.defaultNames));
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    @Override
    public Component getComponent() {
        return new BlockListEditButton(this);
    }

    @Override
    public void fromJson(JsonElement json) {
        if (!json.isJsonArray()) {
            return;
        }
        this.blockNames.clear();
        StreamSupport.stream(json.getAsJsonArray().spliterator(), true).filter(e -> e.isJsonPrimitive()).filter(e -> e.getAsJsonPrimitive().isString()).map(e -> Block.getBlockFromName((String)e.getAsString())).filter(Objects::nonNull).map(b -> BlockUtils.getName(b)).distinct().sorted().forEachOrdered(s -> this.blockNames.add((String)s));
    }

    @Override
    public JsonElement toJson() {
        JsonArray json = new JsonArray();
        this.blockNames.forEach(s -> json.add((JsonElement)new JsonPrimitive(s)));
        return json;
    }
}

