/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.EnemyManager;
import zelix.managers.FriendManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class InteractClick
extends Hack {
    public InteractClick() {
        super("InteractClick", HackCategory.COMBAT);
    }

    @Override
    public String getDescription() {
        return "Left - Add to Enemys, Rigth - Add to Friends, Wheel - Remove from All.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Entity entity;
        RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
        if (object == null) {
            return;
        }
        if (object.field_72313_a == RayTraceResult.Type.ENTITY && (entity = object.field_72308_g) instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !Wrapper.INSTANCE.player().isDead && Wrapper.INSTANCE.player().canEntityBeSeen(entity)) {
            EntityPlayer player = (EntityPlayer)entity;
            String ID = Utils.getPlayerName(player);
            if (Mouse.isButtonDown((int)1) && Wrapper.INSTANCE.mc().currentScreen == null) {
                FriendManager.addFriend(ID);
            } else if (Mouse.isButtonDown((int)0) && Wrapper.INSTANCE.mc().currentScreen == null) {
                EnemyManager.addEnemy(ID);
            } else if (Mouse.isButtonDown((int)2) && Wrapper.INSTANCE.mc().currentScreen == null) {
                EnemyManager.removeEnemy(ID);
                FriendManager.removeFriend(ID);
            }
        }
        super.onClientTick(event);
    }
}

