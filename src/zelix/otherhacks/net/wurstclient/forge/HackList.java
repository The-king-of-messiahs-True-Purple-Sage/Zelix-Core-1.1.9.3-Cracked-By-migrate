/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 */
package zelix.otherhacks.net.wurstclient.forge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WHackList;
import zelix.otherhacks.net.wurstclient.forge.hacks.AutoFarmHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.AutoSprintHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.AutoSwimHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.AutoToolHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.AutoWalkHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.BunnyHopHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.ChestEspHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.ClickGuiHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.FastBreakHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.FastLadderHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.FastPlaceHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.FlightHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.FullbrightHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.GlideHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.ItemEspHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.KillauraHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.MobEspHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.NoFallHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.NoWebHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.NukerHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.PlayerEspHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.RadarHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.RainbowUiHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.SpiderHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.TimerHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.TunnellerHack;
import zelix.otherhacks.net.wurstclient.forge.hacks.XRayHack;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;
import zelix.otherhacks.net.wurstclient.forge.utils.JsonUtils;

public final class HackList
extends WHackList {
    public final AutoFarmHack autoFarmHack = this.register(new AutoFarmHack());
    public final AutoSprintHack autoSprintHack = this.register(new AutoSprintHack());
    public final AutoSwimHack autoSwimHack = this.register(new AutoSwimHack());
    public final AutoToolHack autoToolHack = this.register(new AutoToolHack());
    public final AutoWalkHack autoWalkHack = this.register(new AutoWalkHack());
    public final BunnyHopHack bunnyHopHack = this.register(new BunnyHopHack());
    public final ChestEspHack chestEspHack = this.register(new ChestEspHack());
    public final ClickGuiHack clickGuiHack = this.register(new ClickGuiHack());
    public final FastBreakHack fastBreakHack = this.register(new FastBreakHack());
    public final FastLadderHack fastLadderHack = this.register(new FastLadderHack());
    public final FastPlaceHack fastPlaceHack = this.register(new FastPlaceHack());
    public final FlightHack flightHack = this.register(new FlightHack());
    public final FullbrightHack fullbrightHack = this.register(new FullbrightHack());
    public final GlideHack glideHack = this.register(new GlideHack());
    public final ItemEspHack itemEspHack = this.register(new ItemEspHack());
    public final KillauraHack killauraHack = this.register(new KillauraHack());
    public final MobEspHack mobEspHack = this.register(new MobEspHack());
    public final NoFallHack noFallHack = this.register(new NoFallHack());
    public final NoWebHack noWebHack = this.register(new NoWebHack());
    public final NukerHack nukerHack = this.register(new NukerHack());
    public final PlayerEspHack playerEspHack = this.register(new PlayerEspHack());
    public final RadarHack radarHack = this.register(new RadarHack());
    public final RainbowUiHack rainbowUiHack = this.register(new RainbowUiHack());
    public final SpiderHack spiderHack = this.register(new SpiderHack());
    public final TimerHack timerHack = this.register(new TimerHack());
    public final TunnellerHack tunnellerHack = this.register(new TunnellerHack());
    public final XRayHack xRayHack = this.register(new XRayHack());
    private final Path enabledHacksFile;
    private final Path settingsFile;
    private boolean disableSaving;

    public HackList(Path enabledHacksFile, Path settingsFile) {
        this.enabledHacksFile = enabledHacksFile;
        this.settingsFile = settingsFile;
    }

    public void loadEnabledHacks() {
        JsonArray json;
        try (BufferedReader reader = Files.newBufferedReader(this.enabledHacksFile);){
            json = JsonUtils.jsonParser.parse((Reader)reader).getAsJsonArray();
        }
        catch (NoSuchFileException e) {
            this.saveEnabledHacks();
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.saveEnabledHacks();
            return;
        }
        this.disableSaving = true;
        for (JsonElement e : json) {
            Hack hack;
            if (!e.isJsonPrimitive() || !e.getAsJsonPrimitive().isString() || (hack = this.get(e.getAsString())) == null || !hack.isStateSaved()) continue;
            hack.setEnabled(true);
        }
        this.disableSaving = false;
        this.saveEnabledHacks();
    }

    public void saveEnabledHacks() {
        if (this.disableSaving) {
            return;
        }
        JsonArray enabledHacks = new JsonArray();
        for (Hack hack : this.getRegistry()) {
            if (!hack.isEnabled() || !hack.isStateSaved()) continue;
            enabledHacks.add((JsonElement)new JsonPrimitive(hack.getName()));
        }
        try (BufferedWriter writer = Files.newBufferedWriter(this.enabledHacksFile, new OpenOption[0]);){
            JsonUtils.prettyGson.toJson((JsonElement)enabledHacks, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        JsonObject json;
        try (BufferedReader reader = Files.newBufferedReader(this.settingsFile);){
            json = JsonUtils.jsonParser.parse((Reader)reader).getAsJsonObject();
        }
        catch (NoSuchFileException e) {
            this.saveSettings();
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.saveSettings();
            return;
        }
        this.disableSaving = true;
        for (Map.Entry e : json.entrySet()) {
            Hack hack;
            if (!((JsonElement)e.getValue()).isJsonObject() || (hack = this.get((String)e.getKey())) == null) continue;
            Map<String, Setting> settings = hack.getSettings();
            for (Map.Entry e2 : ((JsonElement)e.getValue()).getAsJsonObject().entrySet()) {
                String key = ((String)e2.getKey()).toLowerCase();
                if (!settings.containsKey(key)) continue;
                settings.get(key).fromJson((JsonElement)e2.getValue());
            }
        }
        this.disableSaving = false;
        this.saveSettings();
    }

    public void saveSettings() {
        if (this.disableSaving) {
            return;
        }
        JsonObject json = new JsonObject();
        for (Hack hack : this.getRegistry()) {
            if (hack.getSettings().isEmpty()) continue;
            JsonObject settings = new JsonObject();
            for (Setting setting : hack.getSettings().values()) {
                settings.add(setting.getName(), setting.toJson());
            }
            json.add(hack.getName(), (JsonElement)settings);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(this.settingsFile, new OpenOption[0]);){
            JsonUtils.prettyGson.toJson((JsonElement)json, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

