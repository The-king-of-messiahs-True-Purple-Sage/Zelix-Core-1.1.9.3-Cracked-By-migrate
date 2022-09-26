/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainerCreative
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.GameType
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;

public class FakeCreative
extends Hack {
    public GameType gameType;
    public BooleanValue showItemsId = new BooleanValue("ShowItemsID", true);

    public FakeCreative() {
        super("FakeCreative", HackCategory.ANOTHER);
        this.addValue(this.showItemsId);
    }

    @Override
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiContainerCreative) {
            event.setGui((GuiScreen)new zelix.gui.clickguis.GuiContainerCreative((EntityPlayer)Wrapper.INSTANCE.player()));
        }
        super.onGuiOpen(event);
    }

    @Override
    public void onDisable() {
        if (this.gameType == null) {
            return;
        }
        Wrapper.INSTANCE.controller().func_78746_a(this.gameType);
        this.gameType = null;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Wrapper.INSTANCE.controller().func_178889_l() == GameType.CREATIVE) {
            return;
        }
        this.gameType = Wrapper.INSTANCE.controller().func_178889_l();
        Wrapper.INSTANCE.controller().func_78746_a(GameType.CREATIVE);
        super.onClientTick(event);
    }
}

