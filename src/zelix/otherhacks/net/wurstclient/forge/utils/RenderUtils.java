/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WVec3d;

public final class RenderUtils {
    public static void drawSolidBox(AxisAlignedBB bb) {
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72336_d, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)bb.field_72337_e, (double)bb.field_72339_c);
    }

    public static void drawNode(AxisAlignedBB bb) {
        double midX = (bb.field_72340_a + bb.field_72336_d) / 2.0;
        double midY = (bb.field_72338_b + bb.field_72337_e) / 2.0;
        double midZ = (bb.field_72339_c + bb.field_72334_f) / 2.0;
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)bb.field_72337_e, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)bb.field_72336_d, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)bb.field_72340_a, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72339_c);
        GL11.glVertex3d((double)midX, (double)bb.field_72338_b, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.field_72334_f);
    }

    public static void drawArrow(Vec3d from, Vec3d to) {
        double startX = WVec3d.getX(from);
        double startY = WVec3d.getY(from);
        double startZ = WVec3d.getZ(from);
        double endX = WVec3d.getX(to);
        double endY = WVec3d.getY(to);
        double endZ = WVec3d.getZ(to);
        GL11.glPushMatrix();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)startX, (double)startY, (double)startZ);
        GL11.glVertex3d((double)endX, (double)endY, (double)endZ);
        GL11.glEnd();
        GL11.glTranslated((double)endX, (double)endY, (double)endZ);
        GL11.glScaled((double)0.1, (double)0.1, (double)0.1);
        double angleX = Math.atan2(endY - startY, startZ - endZ);
        GL11.glRotated((double)(Math.toDegrees(angleX) + 90.0), (double)1.0, (double)0.0, (double)0.0);
        double angleZ = Math.atan2(endX - startX, Math.sqrt(Math.pow(endY - startY, 2.0) + Math.pow(endZ - startZ, 2.0)));
        GL11.glRotated((double)Math.toDegrees(angleZ), (double)0.0, (double)0.0, (double)1.0);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)1.0);
        GL11.glVertex3d((double)-1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)-1.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)-1.0);
        GL11.glVertex3d((double)1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)1.0);
        GL11.glVertex3d((double)1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)1.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)-1.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)2.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)-1.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)2.0, (double)1.0);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}

