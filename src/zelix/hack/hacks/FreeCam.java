/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.world.World
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Entity301;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;

public class FreeCam
extends Hack {
    public Entity301 entity301 = null;

    public FreeCam() {
        super("FreeCam", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Allows you to move the camera without moving your character.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        return side != Connection.Side.OUT || !(packet instanceof CPacketPlayer) && !(packet instanceof CPacketPlayer.Position) && !(packet instanceof CPacketPlayer.Rotation) && !(packet instanceof CPacketPlayer.PositionRotation);
    }

    @Override
    public void onEnable() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.entity301 = new Entity301((World)Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.entity301.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.entity301.field_71071_by = Wrapper.INSTANCE.inventory();
            this.entity301.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.entity301.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.entity301.field_70759_as = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntityInWorld((Entity)this.entity301);
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.entity301 != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.player().setPosition(this.entity301.posX, this.entity301.posY, this.entity301.posZ);
            Wrapper.INSTANCE.player().rotationPitch = this.entity301.rotationPitch;
            Wrapper.INSTANCE.player().rotationYaw = this.entity301.rotationYaw;
            Wrapper.INSTANCE.player().rotationYawHead = this.entity301.field_70759_as;
            Wrapper.INSTANCE.world().removeEntity((Entity)this.entity301);
            this.entity301 = null;
        }
        super.onDisable();
    }
}

