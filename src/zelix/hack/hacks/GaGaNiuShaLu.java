/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.EntityLivingBase
 */
package zelix.hack.hacks;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.value.BooleanValue;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class GaGaNiuShaLu
extends Hack {
    public static EntityLivingBase target;
    public List<EntityLivingBase> targets;
    public NumberValue MaxTurnSpeed;
    public NumberValue MinTurnSpeed;
    public NumberValue MaxPredictSize;
    public NumberValue MinPredictSize;
    public NumberValue MaxCPS;
    public NumberValue MinCPS;
    public NumberValue Fov;
    public NumberValue Distance;
    public NumberValue throughWallDistance;
    public NumberValue hurtTime;
    public NumberValue cooldown;
    public BooleanValue throughWall;
    public BooleanValue autoDelay;
    public BooleanValue silentRotation;
    public BooleanValue AutoBlock;
    public BooleanValue Predict;
    public BooleanValue NoSwing;
    public ModeValue PriorityMode;
    public ModeValue TargetMode;
    public Minecraft mc = Minecraft.getMinecraft();

    public GaGaNiuShaLu() {
        super("GaGaNiuShaLu", HackCategory.COMBAT);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

