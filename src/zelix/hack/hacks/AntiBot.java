/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.SPacketSpawnPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.EntityBot;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class AntiBot
extends Hack {
    public static ArrayList<EntityBot> bots = new ArrayList();
    public NumberValue level = new NumberValue("AILevel", 0.0, 0.0, 6.0);
    public NumberValue tick = new NumberValue("TicksExisted", 0.0, 0.0, 999.0);
    public BooleanValue ifInAir;
    public BooleanValue ifGround;
    public BooleanValue ifZeroHealth;
    public BooleanValue ifInvisible = new BooleanValue("Invisible", false);
    public BooleanValue ifEntityId;
    public BooleanValue ifTabName;
    public BooleanValue ifPing;
    public BooleanValue remove;
    public BooleanValue gwen;
    public BooleanValue mineland;
    public static ModeValue mode;
    private static List invalid;

    public AntiBot() {
        super("AntiBot", HackCategory.COMBAT);
        this.ifInAir = new BooleanValue("InAir", false);
        this.ifGround = new BooleanValue("OnGround", false);
        this.ifZeroHealth = new BooleanValue("ZeroHealth", false);
        this.ifEntityId = new BooleanValue("EntityId", false);
        this.ifTabName = new BooleanValue("OutTabName", false);
        this.ifPing = new BooleanValue("PingCheck", false);
        this.remove = new BooleanValue("RemoveBots", false);
        this.gwen = new BooleanValue("Gwen", false);
        this.mineland = new BooleanValue("Mineland", false);
        mode = new ModeValue("Mode", new Mode("Basic", false), new Mode("Mineplex", true), new Mode("Hypixel", false));
        this.addValue(this.level, this.tick, this.remove, this.gwen, this.ifInvisible, this.ifInAir, this.ifGround, this.ifZeroHealth, this.ifEntityId, this.ifTabName, this.ifPing, mode);
    }

    @Override
    public String getDescription() {
        return "Ignore/Remove anti cheat bots.";
    }

    @Override
    public void onEnable() {
        bots.clear();
        invalid.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        bots.clear();
        invalid.clear();
        super.onDisable();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (((Boolean)this.gwen.getValue()).booleanValue()) {
            for (Entity entity : Utils.getEntityList()) {
                double posZ;
                double difZ;
                double posY;
                double difY;
                SPacketSpawnPlayer spawn;
                double posX;
                double difX;
                double dist;
                if (!(packet instanceof SPacketSpawnPlayer) || !((dist = Math.sqrt((difX = Wrapper.INSTANCE.player().posX - (posX = (spawn = (SPacketSpawnPlayer)packet).func_186898_d() / 32.0)) * difX + (difY = Wrapper.INSTANCE.player().posY - (posY = spawn.func_186897_e() / 32.0)) * difY + (difZ = Wrapper.INSTANCE.player().posZ - (posZ = spawn.func_186899_f() / 32.0)) * difZ)) <= 17.0) || posX == Wrapper.INSTANCE.player().posX || posY == Wrapper.INSTANCE.player().posY || posZ == Wrapper.INSTANCE.player().posZ) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mode.getMode("Basic").isToggled()) {
            if ((double)((Double)this.tick.getValue()).intValue() > 0.0) {
                bots.clear();
            }
            for (Entity object : Utils.getEntityList()) {
                EntityLivingBase entity;
                if (!(object instanceof EntityLivingBase) || (entity = (EntityLivingBase)object) instanceof EntityPlayerSP || !(entity instanceof EntityPlayer) || entity instanceof EntityArmorStand || entity == Wrapper.INSTANCE.player()) continue;
                EntityPlayer bot = (EntityPlayer)entity;
                if (!this.isBotBase(bot)) {
                    boolean isAi;
                    int ailevel = ((Double)this.level.getValue()).intValue();
                    boolean bl = isAi = (double)ailevel > 0.0;
                    if (isAi && this.botPercentage(bot) > ailevel) {
                        this.addBot(bot);
                        continue;
                    }
                    if (isAi || !this.botCondition(bot)) continue;
                    this.addBot(bot);
                    continue;
                }
                this.addBot(bot);
                if (!((Boolean)this.remove.getValue()).booleanValue()) continue;
                Wrapper.INSTANCE.world().removeEntity((Entity)bot);
            }
        } else if (mode.getMode("Mineplex").isToggled() && Wrapper.INSTANCE.player().ticksExisted > 40) {
            for (Object o1 : Wrapper.INSTANCE.world().loadedEntityList) {
                Entity ent1 = (Entity)o1;
                if (!(ent1 instanceof EntityPlayer) || ent1 instanceof EntityPlayerSP) continue;
                int ticks1 = ent1.ticksExisted;
                double formated = Math.abs(Wrapper.INSTANCE.player().posY - ent1.posY);
                String name = ent1.getName();
                String diffX1 = ent1.getCustomNameTag();
                if (diffX1 != "" || invalid.contains((EntityPlayer)ent1)) continue;
                invalid.add(ent1);
                Wrapper.INSTANCE.world().removeEntity(ent1);
            }
        } else if (mode.getMode("Hypixel").isToggled()) {
            for (Object o : Wrapper.INSTANCE.world().getLoadedEntityList()) {
                EntityPlayer ent1;
                if (!(o instanceof EntityPlayer) || (ent1 = (EntityPlayer)o) == Wrapper.INSTANCE.player() || invalid.contains(ent1)) continue;
                String formated = ent1.func_145748_c_().func_150254_d();
                String custom = ent1.getCustomNameTag();
                String name1 = ent1.getName();
                if (ent1.isInvisible() && !formated.startsWith("\ufffd\ufffdc") && formated.endsWith("\ufffd\ufffdr") && custom.equals(name1)) {
                    List<EntityPlayer> list;
                    double diffX = Math.abs(ent1.posX - Wrapper.INSTANCE.player().posX);
                    double diffY = Math.abs(ent1.posY - Wrapper.INSTANCE.player().posY);
                    double diffZ = Math.abs(ent1.posZ - Wrapper.INSTANCE.player().posZ);
                    double diffH = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    if (diffY < 13.0 && diffY > 10.0 && diffH < 3.0 && !(list = AntiBot.getTabPlayerList()).contains(ent1)) {
                        invalid.add(ent1);
                        Wrapper.INSTANCE.world().removeEntity((Entity)ent1);
                    }
                }
                if (!formated.startsWith("\ufffd\ufffd") && formated.endsWith("\ufffd\ufffdr")) {
                    invalid.add(ent1);
                }
                if (ent1.isInvisible() && !custom.equalsIgnoreCase("") && custom.toLowerCase().contains("\ufffd\ufffdc\ufffd\ufffdc") && name1.contains("\ufffd\ufffdc")) {
                    invalid.add(ent1);
                }
                if (!custom.equalsIgnoreCase("") && custom.toLowerCase().contains("\ufffd\ufffdc") && custom.toLowerCase().contains("\ufffd\ufffdr")) {
                    invalid.add(ent1);
                }
                if (formated.contains("\ufffd\ufffd8[NPC]")) {
                    invalid.add(ent1);
                }
                if (formated.contains("\ufffd\ufffdc") || custom.equalsIgnoreCase("")) break;
                invalid.add(ent1);
                break;
            }
        }
        super.onClientTick(event);
    }

    void addBot(EntityPlayer player) {
        if (!AntiBot.isBot((Entity)player)) {
            bots.add(new EntityBot(player));
        }
    }

    public static boolean isBot(Entity player) {
        block6: {
            block7: {
                block5: {
                    if (!mode.getMode("Basic").isToggled()) break block5;
                    for (EntityBot bot : bots) {
                        if (bot.getName().equals(player.getName())) {
                            if (player.isInvisible() != bot.isInvisible()) {
                                return player.isInvisible();
                            }
                            return true;
                        }
                        EntityPlayer X = (EntityPlayer)player;
                        if (bot.getId() != player.getEntityId() && !bot.getUuid().equals(X.getGameProfile().getId())) continue;
                        return true;
                    }
                    break block6;
                }
                if (!mode.getMode("Mineplex").isToggled()) break block7;
                for (Entity ent : Wrapper.INSTANCE.world().loadedEntityList) {
                    if (!invalid.contains(ent)) continue;
                    return true;
                }
                break block6;
            }
            if (!mode.getMode("Hypixel").isToggled()) break block6;
            for (Entity ent : Wrapper.INSTANCE.world().loadedEntityList) {
                if (!invalid.contains(ent)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isHypixelNPC(Entity entity) {
        String formattedName = entity.func_145748_c_().func_150254_d();
        String customName = entity.getCustomNameTag();
        if (!formattedName.startsWith("\u00a7") && formattedName.endsWith("\u00a7r")) {
            return true;
        }
        return formattedName.contains("[NPC]");
    }

    boolean botCondition(EntityPlayer bot) {
        boolean percentage = false;
        if ((double)((Double)this.tick.getValue()).intValue() > 0.0 && bot.ticksExisted < ((Double)this.tick.getValue()).intValue()) {
            return true;
        }
        if (((Boolean)this.ifInAir.getValue()).booleanValue() && bot.isInvisible() && bot.motionY == 0.0 && bot.posY > Wrapper.INSTANCE.player().posY + 1.0 && BlockUtils.isBlockMaterial(new BlockPos((Entity)bot).func_177977_b(), Blocks.air)) {
            return true;
        }
        if (((Boolean)this.ifGround.getValue()).booleanValue() && bot.motionY == 0.0 && !bot.isCollidedVertically && bot.onGround && bot.posY % 1.0 != 0.0 && bot.posY % 0.5 != 0.0) {
            return true;
        }
        if (((Boolean)this.ifZeroHealth.getValue()).booleanValue() && bot.getHealth() <= 0.0f) {
            return true;
        }
        if (((Boolean)this.ifInvisible.getValue()).booleanValue() && bot.isInvisible()) {
            return true;
        }
        if (((Boolean)this.ifEntityId.getValue()).booleanValue() && bot.getEntityId() >= 1000000000) {
            return true;
        }
        if (((Boolean)this.ifTabName.getValue()).booleanValue()) {
            boolean isTabName = false;
            for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfoMap()) {
                if (npi.getGameProfile() == null || !npi.getGameProfile().getName().contains(bot.getName())) continue;
                isTabName = true;
            }
            if (!isTabName) {
                return true;
            }
        }
        return false;
    }

    int botPercentage(EntityPlayer bot) {
        int percentage = 0;
        if ((double)((Double)this.tick.getValue()).intValue() > 0.0 && bot.ticksExisted < ((Double)this.tick.getValue()).intValue()) {
            ++percentage;
        }
        if (((Boolean)this.ifInAir.getValue()).booleanValue() && bot.isInvisible() && bot.posY > Wrapper.INSTANCE.player().posY + 1.0 && BlockUtils.isBlockMaterial(new BlockPos((Entity)bot).func_177977_b(), Blocks.air)) {
            ++percentage;
        }
        if (((Boolean)this.ifGround.getValue()).booleanValue() && bot.motionY == 0.0 && !bot.isCollidedVertically && bot.onGround && bot.posY % 1.0 != 0.0 && bot.posY % 0.5 != 0.0) {
            ++percentage;
        }
        if (((Boolean)this.ifZeroHealth.getValue()).booleanValue() && bot.getHealth() <= 0.0f) {
            ++percentage;
        }
        if (((Boolean)this.ifInvisible.getValue()).booleanValue() && bot.isInvisible()) {
            ++percentage;
        }
        if (((Boolean)this.ifEntityId.getValue()).booleanValue() && bot.getEntityId() >= 1000000000) {
            ++percentage;
        }
        if (((Boolean)this.ifTabName.getValue()).booleanValue()) {
            boolean isTabName = false;
            for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfoMap()) {
                if (npi.getGameProfile() == null || !npi.getGameProfile().getName().contains(bot.getName())) continue;
                isTabName = true;
            }
            if (!isTabName) {
                ++percentage;
            }
        }
        return percentage;
    }

    boolean isBotBase(EntityPlayer bot) {
        if (AntiBot.isBot((Entity)bot)) {
            return true;
        }
        if (bot.getGameProfile() == null) {
            return true;
        }
        GameProfile botProfile = bot.getGameProfile();
        if (bot.getUniqueID() == null) {
            return true;
        }
        UUID botUUID = bot.getUniqueID();
        if (botProfile.getName() == null) {
            return true;
        }
        String botName = botProfile.getName();
        return botName.contains("Body #") || botName.contains("NPC") || botName.equalsIgnoreCase(Utils.getEntityNameColor((EntityLivingBase)bot));
    }

    public List getInvalid() {
        return invalid;
    }

    public static List<EntityPlayer> getTabPlayerList() {
        NetHandlerPlayClient nhpc = Minecraft.getMinecraft().thePlayer.sendQueue;
        ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        return list;
    }

    static {
        invalid = new ArrayList();
    }
}

