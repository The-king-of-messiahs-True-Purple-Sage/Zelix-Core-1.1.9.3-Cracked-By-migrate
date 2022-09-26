/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraft.network.play.client.CPacketClientStatus
 *  net.minecraft.network.play.client.CPacketCloseWindow
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerAbilities
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketVehicleMove
 */
package zelix.hack.hacks;

import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class PacketFilter
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Output", true), new Mode("Input", false), new Mode("AllSides", false));
    public BooleanValue cCPacketPlayer = new BooleanValue("Player", false);
    public BooleanValue cCPacketCloseWindow;
    public BooleanValue cCPacketRotation;
    public BooleanValue cCPacketPosition;
    public BooleanValue cCPacketPositionRotation;
    public BooleanValue cCPacketClientStatus;
    public BooleanValue cCPacketInput;
    public BooleanValue cCPacketPlayerAbilities;
    public BooleanValue cCPacketPlayerDigging;
    public BooleanValue cCPacketUseEntity;
    public BooleanValue cCPacketVehicleMove;
    public BooleanValue cCPacketEntityAction = new BooleanValue("EntityAction", false);
    public BooleanValue cCPacketClickWindow;

    public PacketFilter() {
        super("PacketFilter", HackCategory.ANOTHER);
        this.cCPacketCloseWindow = new BooleanValue("CloseWindow", false);
        this.cCPacketRotation = new BooleanValue("Rotation", false);
        this.cCPacketPosition = new BooleanValue("Position", false);
        this.cCPacketPositionRotation = new BooleanValue("PositionRotation", false);
        this.cCPacketClientStatus = new BooleanValue("ClientStatus", false);
        this.cCPacketInput = new BooleanValue("Input", false);
        this.cCPacketPlayerAbilities = new BooleanValue("PlayerAbilities", false);
        this.cCPacketPlayerDigging = new BooleanValue("PlayerDigging", false);
        this.cCPacketUseEntity = new BooleanValue("UseEntity", false);
        this.cCPacketVehicleMove = new BooleanValue("VehicleMove", false);
        this.cCPacketEntityAction = new BooleanValue("EntityAction", false);
        this.cCPacketClickWindow = new BooleanValue("ClickWindow", false);
        this.addValue(this.mode, this.cCPacketPlayer, this.cCPacketEntityAction, this.cCPacketCloseWindow, this.cCPacketRotation, this.cCPacketPosition, this.cCPacketPositionRotation, this.cCPacketClientStatus, this.cCPacketInput, this.cCPacketPlayerAbilities, this.cCPacketPlayerDigging, this.cCPacketUseEntity, this.cCPacketVehicleMove, this.cCPacketEntityAction, this.cCPacketClickWindow);
    }

    @Override
    public String getDescription() {
        return "Packet filter.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (this.mode.getMode("Output").isToggled() && side == Connection.Side.OUT || this.mode.getMode("Input").isToggled() && side == Connection.Side.IN || this.mode.getMode("AllSides").isToggled()) {
            return this.checkPacket(packet);
        }
        return true;
    }

    public boolean checkPacket(Object packet) {
        return !((Boolean)this.cCPacketPlayer.getValue() != false && packet instanceof CPacketPlayer || (Boolean)this.cCPacketEntityAction.getValue() != false && packet instanceof CPacketEntityAction || (Boolean)this.cCPacketCloseWindow.getValue() != false && packet instanceof CPacketCloseWindow || (Boolean)this.cCPacketRotation.getValue() != false && packet instanceof CPacketPlayer.Rotation || (Boolean)this.cCPacketPosition.getValue() != false && packet instanceof CPacketPlayer.Position || (Boolean)this.cCPacketPositionRotation.getValue() != false && packet instanceof CPacketPlayer.PositionRotation || (Boolean)this.cCPacketClientStatus.getValue() != false && packet instanceof CPacketClientStatus || (Boolean)this.cCPacketInput.getValue() != false && packet instanceof CPacketInput || (Boolean)this.cCPacketPlayerAbilities.getValue() != false && packet instanceof CPacketPlayerAbilities || (Boolean)this.cCPacketPlayerDigging.getValue() != false && packet instanceof CPacketPlayerDigging || (Boolean)this.cCPacketUseEntity.getValue() != false && packet instanceof CPacketUseEntity || (Boolean)this.cCPacketVehicleMove.getValue() != false && packet instanceof CPacketVehicleMove || (Boolean)this.cCPacketEntityAction.getValue() != false && packet instanceof CPacketEntityAction) && ((Boolean)this.cCPacketClickWindow.getValue() == false || !(packet instanceof CPacketClickWindow));
    }
}

