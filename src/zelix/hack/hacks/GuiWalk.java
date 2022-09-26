/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package zelix.hack.hacks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class GuiWalk
extends Hack {
    public GuiWalk() {
        super("GuiWalk", HackCategory.MOVEMENT);
    }

    @Override
    public String getDescription() {
        return "Allows you to walk while the gui is open.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!(Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer) && !(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen)) {
            return;
        }
        double speed = 0.05;
        if (!Wrapper.INSTANCE.player().onGround) {
            speed /= 4.0;
        }
        this.handleJump();
        this.handleForward(speed);
        if (!Wrapper.INSTANCE.player().onGround) {
            speed /= 2.0;
        }
        this.handleBack(speed);
        this.handleLeft(speed);
        this.handleRight(speed);
        super.onClientTick(event);
    }

    void moveForward(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionX -= (double)MathHelper.func_76126_a((float)direction) * speed;
        Wrapper.INSTANCE.player().motionZ += (double)MathHelper.func_76134_b((float)direction) * speed;
    }

    void moveBack(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionX += (double)MathHelper.func_76126_a((float)direction) * speed;
        Wrapper.INSTANCE.player().motionZ -= (double)MathHelper.func_76134_b((float)direction) * speed;
    }

    void moveLeft(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionZ += (double)MathHelper.func_76126_a((float)direction) * speed;
        Wrapper.INSTANCE.player().motionX += (double)MathHelper.func_76134_b((float)direction) * speed;
    }

    void moveRight(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionZ -= (double)MathHelper.func_76126_a((float)direction) * speed;
        Wrapper.INSTANCE.player().motionX -= (double)MathHelper.func_76134_b((float)direction) * speed;
    }

    void handleForward(double speed) {
        if (!Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode())) {
            return;
        }
        this.moveForward(speed);
    }

    void handleBack(double speed) {
        if (!Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindBack.getKeyCode())) {
            return;
        }
        this.moveBack(speed);
    }

    void handleLeft(double speed) {
        if (!Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindLeft.getKeyCode())) {
            return;
        }
        this.moveLeft(speed);
    }

    void handleRight(double speed) {
        if (!Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindRight.getKeyCode())) {
            return;
        }
        this.moveRight(speed);
    }

    void handleJump() {
        if (Wrapper.INSTANCE.player().onGround && Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindJump.getKeyCode())) {
            Wrapper.INSTANCE.player().jump();
        }
    }
}

