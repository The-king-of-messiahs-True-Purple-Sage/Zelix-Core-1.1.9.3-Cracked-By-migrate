/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.client.event.GuiContainerEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.ProjectileImpactEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.EntityItemPickupEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package zelix.hack;

import java.util.ArrayList;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.eventapi.event.EventPlayerPost;
import zelix.eventapi.event.EventPlayerPre;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.Value;

public class Hack {
    private String name;
    private String Chinese_name;
    private HackCategory category;
    private boolean toggled;
    private boolean show;
    private int key;
    private ArrayList<Value> values = new ArrayList();

    public Hack(String name, HackCategory category) {
        this.name = name;
        this.category = category;
        this.toggled = false;
        this.show = true;
        this.key = 0;
    }

    public void addValue(Value ... values) {
        for (Value value : values) {
            this.getValues().add(value);
        }
    }

    public ArrayList<Value> getValues() {
        return this.values;
    }

    public boolean isToggledMode(String modeName) {
        for (Value value : this.values) {
            if (!(value instanceof ModeValue)) continue;
            ModeValue modeValue = (ModeValue)value;
            for (Mode mode2 : modeValue.getModes()) {
                if (!mode2.getName().equalsIgnoreCase(modeName) || !mode2.isToggled()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isToggledValue(String valueName) {
        for (Value value : this.values) {
            BooleanValue booleanValue;
            if (!(value instanceof BooleanValue) || !(booleanValue = (BooleanValue)value).getName().equalsIgnoreCase(valueName) || !((Boolean)booleanValue.getValue()).booleanValue()) continue;
            return true;
        }
        return false;
    }

    public void setValues(ArrayList<Value> values) {
        for (Value value : values) {
            for (Value value1 : this.values) {
                if (!value.getName().equalsIgnoreCase(value1.getName())) continue;
                value1.setValue(value.getValue());
            }
        }
    }

    public void toggle() {
        boolean bl = this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        RenderUtils.splashTickPos = 0;
        if (!RenderUtils.isSplash && !(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen)) {
            RenderUtils.isSplash = true;
        }
    }

    public void setChinese(String Ch) {
        this.Chinese_name = Ch;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onGuiContainer(GuiContainerEvent event) {
    }

    public void onGuiOpen(GuiOpenEvent event) {
    }

    public void onMouse(MouseEvent event) {
    }

    public boolean onPacket(Object packet, Connection.Side side) {
        return true;
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    }

    public void onClientTick(TickEvent.ClientTickEvent event) {
    }

    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
    }

    public void onAttackEntity(AttackEntityEvent event) {
    }

    public void onItemPickup(EntityItemPickupEvent event) {
    }

    public void onProjectileImpact(ProjectileImpactEvent event) {
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    }

    public void onRenderWorldLast(RenderWorldLastEvent event) {
    }

    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
    }

    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
    }

    public void onInputUpdate(InputUpdateEvent event) {
    }

    public void onPlayerEventPre(EventPlayerPre event) {
    }

    public void onPlayerEventPost(EventPlayerPost event) {
    }

    public void onRender3D(RenderBlockOverlayEvent event) {
    }

    public void d(Object o) {
        String str = "[DEBUG]: " + o;
        ChatUtils.warning(str);
    }

    public void d() {
        String str = "[DEBUG]: !";
        ChatUtils.warning(str);
    }

    public String getDescription() {
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getRenderName() {
        if (ClickGui.language.getMode("Chinese").isToggled()) {
            return this.Chinese_name == null ? this.name : this.Chinese_name;
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HackCategory getCategory() {
        return this.category;
    }

    public void setCategory(HackCategory category) {
        this.category = category;
    }

    public int getKey() {
        if (this.key == -1) {
            return 0;
        }
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}

