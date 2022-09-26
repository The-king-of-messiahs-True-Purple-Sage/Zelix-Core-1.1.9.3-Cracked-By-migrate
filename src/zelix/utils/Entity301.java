/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.MoverType
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.World
 */
package zelix.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.MoverType;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class Entity301
extends EntityOtherPlayerMP {
    private static MovementInput movementInput = null;

    public Entity301(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    public void setMovementInput(MovementInput movementInput) {
        Entity301.movementInput = movementInput;
        if (movementInput.jump && this.onGround) {
            super.func_70664_aZ();
        }
        super.func_191958_b(movementInput.moveStrafe, this.field_70701_bs, movementInput.field_192832_b, this.field_70764_aw);
    }

    public void func_70091_d(MoverType type, double x, double y, double z) {
        this.onGround = true;
        super.func_70091_d(type, x, y, z);
        this.onGround = true;
    }

    public boolean isSneaking() {
        return false;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.noClip = true;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.noClip = false;
    }
}

