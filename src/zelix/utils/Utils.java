/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityEgg
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package zelix.utils;

import com.mojang.authlib.GameProfile;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zelix.Core;
import zelix.managers.EnemyManager;
import zelix.utils.BlockUtils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Mapping;
import zelix.utils.system.WebUtils;

public class Utils {
    public static boolean lookChanged;
    public static float[] rotationsToBlock;
    private static final Random RANDOM;
    public static String LastContent;

    public static String encodeHexString(byte[] data) {
        return new String(Utils.encodeHex(data));
    }

    public static char[] encodeHex(byte[] data) {
        return Utils.encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return Utils.encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; ++i) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0xF & data[i]];
        }
        return out;
    }

    public static boolean isPlayerHoldingWeapon() {
        if (Wrapper.INSTANCE.player().func_184586_b(EnumHand.MAIN_HAND) == null) {
            return false;
        }
        Item item = Wrapper.INSTANCE.player().func_184586_b(EnumHand.MAIN_HAND).getItem();
        return item instanceof ItemSword || item instanceof ItemAxe;
    }

    public static String readFile(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            if (line != null) {
                return line;
            }
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCPUSerialNumber() {
        String serial;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            serial = sc.next();
            serial = sc.next();
        }
        catch (IOException e) {
            throw new RuntimeException("\u83b7\u53d6CPU\u5e8f\u5217\u53f7\u5931\u8d25");
        }
        return serial;
    }

    public static String getHWID(boolean ismd5) {
        String serial;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            serial = sc.next();
            serial = sc.next();
        }
        catch (IOException e) {
            throw new RuntimeException("\u83b7\u53d6CPU\u5e8f\u5217\u53f7\u5931\u8d25");
        }
        return serial;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getClassByteCode(String className) {
        String jarname = "/" + className.replace('.', '/') + ".class";
        InputStream is = Core.class.getResourceAsStream(jarname);
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] imgdata = null;
        try {
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            imgdata = bytestream.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bytestream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgdata;
    }

    public static String getSubString(String text, String left, String right) {
        int zLen;
        String result = "";
        zLen = left == null || left.isEmpty() ? 0 : ((zLen = text.indexOf(left)) > -1 ? (zLen += left.length()) : 0);
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; ++i) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte)Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (int n : bytes) {
            a = n < 0 ? 256 + n : n;
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    public static boolean nullCheck() {
        return Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null;
    }

    public static void copy(String content) {
        if (LastContent != content) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
        }
        LastContent = content;
    }

    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null && clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                ret = (String)clipTf.getTransferData(DataFlavor.stringFlavor);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static Vec3d getRandomCenter(AxisAlignedBB bb) {
        return new Vec3d(bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * 0.8 * Math.random(), bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * Math.random() + 0.1 * Math.random(), bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * 0.8 * Math.random());
    }

    public static boolean isMoving(Entity e) {
        return e.motionX != 0.0 && e.motionZ != 0.0 && (e.motionY != 0.0 || e.motionY > 0.0);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockUtils.getBlock(pos).canCollideCheck(BlockUtils.getState(pos), false);
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        rotationsToBlock = Utils.getNeededRotations(vec);
    }

    public static List<Entity> getEntityList() {
        return Wrapper.INSTANCE.world().getLoadedEntityList();
    }

    public static boolean isNullOrEmptyStack(ItemStack stack) {
        return stack == null || stack.func_190926_b();
    }

    public static void windowClick(int windowId, int slotId, int mouseButton, ClickType type) {
        Wrapper.INSTANCE.controller().func_187098_a(windowId, slotId, mouseButton, type, (EntityPlayer)Wrapper.INSTANCE.player());
    }

    public static void swingMainHand() {
        Wrapper.INSTANCE.player().func_184609_a(EnumHand.MAIN_HAND);
    }

    public static void attack(Entity entity) {
        Wrapper.INSTANCE.controller().attackEntity((EntityPlayer)Wrapper.INSTANCE.player(), entity);
    }

    public static void addEffect(int id, int duration, int amplifier) {
        Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.func_188412_a((int)id), duration, amplifier));
    }

    public static void removeEffect(int id) {
        Wrapper.INSTANCE.player().func_184589_d(Potion.func_188412_a((int)id));
    }

    public static void clearEffects() {
        for (PotionEffect effect : Wrapper.INSTANCE.player().getActivePotionEffects()) {
            Wrapper.INSTANCE.player().func_184589_d(effect.func_188419_a());
        }
    }

    public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset, double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;
        if (Wrapper.INSTANCE.player().isSneaking()) {
            wasSneaking = true;
        }
        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];
        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];
        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
            if (count > 120) break;
            double offset = extendOffset && (count & 1) == 0 ? setOffset + 0.15 : setOffset;
            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;
            if (diffX < 0.0) {
                startX = Math.abs(diffX) > offset ? (startX += offset) : (startX += Math.abs(diffX));
            }
            if (diffX > 0.0) {
                startX = Math.abs(diffX) > offset ? (startX -= offset) : (startX -= Math.abs(diffX));
            }
            if (diffY < 0.0) {
                startY = Math.abs(diffY) > offset ? (startY += offset) : (startY += Math.abs(diffY));
            }
            if (diffY > 0.0) {
                startY = Math.abs(diffY) > offset ? (startY -= offset) : (startY -= Math.abs(diffY));
            }
            if (diffZ < 0.0) {
                startZ = Math.abs(diffZ) > offset ? (startZ += offset) : (startZ += Math.abs(diffZ));
            }
            if (diffZ > 0.0) {
                startZ = Math.abs(diffZ) > offset ? (startZ -= offset) : (startZ -= Math.abs(diffZ));
            }
            if (wasSneaking) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SNEAKING));
            }
            Wrapper.INSTANCE.mc().getNetHandler().getNetworkManager().sendPacket((Packet)new CPacketPlayer.Position(startX, startY, startZ, onGround));
            ++count;
        }
        if (wasSneaking) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SNEAKING));
        }
        return new double[]{startX, startY, startZ};
    }

    public static void selfDamage(double posY) {
        if (!Wrapper.INSTANCE.player().onGround) {
            return;
        }
        int i = 0;
        while ((double)i <= 64.0) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + posY, Wrapper.INSTANCE.player().posZ, false));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, (double)i == 64.0));
            ++i;
        }
        Wrapper.INSTANCE.player().motionX *= 0.2;
        Wrapper.INSTANCE.player().motionZ *= 0.2;
        Utils.swingMainHand();
    }

    public static String getPlayerName(EntityPlayer player) {
        return player.getGameProfile() != null ? player.getGameProfile().getName() : player.getName();
    }

    public static boolean isPlayer(Entity entity) {
        String playerName;
        EntityPlayer player;
        String entityName;
        return entity instanceof EntityPlayer && (entityName = Utils.getPlayerName(player = (EntityPlayer)entity)).equals(playerName = Utils.getPlayerName((EntityPlayer)Wrapper.INSTANCE.player()));
    }

    public static boolean isMurder(EntityLivingBase entity) {
        Utils.mysteryFind(entity, 0);
        if (!EnemyManager.murders.isEmpty() && entity instanceof EntityPlayer) {
            EntityPlayer murder = (EntityPlayer)entity;
            for (String name : EnemyManager.murders) {
                if (!murder.getGameProfile().getName().equals(name)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isDetect(EntityLivingBase entity) {
        Utils.mysteryFind(entity, 1);
        if (!EnemyManager.detects.isEmpty() && entity instanceof EntityPlayer) {
            EntityPlayer murder = (EntityPlayer)entity;
            for (String name : EnemyManager.detects) {
                if (!murder.getGameProfile().getName().equals(name)) continue;
                return true;
            }
        }
        return false;
    }

    public static void mysteryFind(EntityLivingBase entity, int target) {
        int index;
        if (target == 0) {
            if (!EnemyManager.murders.isEmpty()) {
                for (index = 0; index < EnemyManager.murders.size(); ++index) {
                    EntityLivingBase murder = Utils.getWorldEntityByName(EnemyManager.murders.get(index));
                    if (murder != null) continue;
                    EnemyManager.murders.remove(index);
                }
            }
        } else if (target == 1 && !EnemyManager.detects.isEmpty()) {
            for (index = 0; index < EnemyManager.detects.size(); ++index) {
                EntityLivingBase detect = Utils.getWorldEntityByName(EnemyManager.detects.get(index));
                if (detect != null) continue;
                EnemyManager.detects.remove(index);
            }
        }
        if (entity instanceof EntityPlayerSP) {
            return;
        }
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)entity;
        if (player.getGameProfile() == null) {
            return;
        }
        GameProfile profile = player.getGameProfile();
        if (profile.getName() == null) {
            return;
        }
        if (EnemyManager.murders.contains(profile.getName()) || EnemyManager.detects.contains(profile.getName())) {
            return;
        }
        if (player.inventory == null) {
            return;
        }
        for (int slot = 0; slot < 36; ++slot) {
            String name;
            Item item;
            ItemStack stack = player.inventory.getStackInSlot(slot);
            if (stack == null || (item = stack.getItem()) == null) continue;
            if (target == 0) {
                if (item != Items.iron_sword && item != Items.diamond_sword && item != Items.golden_sword && item != Items.stone_sword && item != Items.wooden_sword && item != Items.iron_shovel && item != Items.diamond_shovel && item != Items.golden_shovel && item != Items.stone_shovel && item != Items.wooden_shovel && item != Items.iron_axe && item != Items.diamond_axe && item != Items.golden_axe && item != Items.stone_axe && item != Items.wooden_axe && item != Items.iron_pickaxe && item != Items.diamond_pickaxe && item != Items.golden_pickaxe && item != Items.stone_pickaxe && item != Items.wooden_pickaxe && item != Items.iron_hoe && item != Items.diamond_hoe && item != Items.golden_hoe && item != Items.stone_hoe && item != Items.wooden_hoe && item != Items.stick && item != Items.blaze_rod && item != Items.fishing_rod && item != Items.carrot && item != Items.golden_carrot && item != Items.bone && item != Items.cookie && item != Items.feather && item != Items.pumpkin_pie && item != Items.cooked_fish && item != Items.fish && item != Items.shears && item != Items.carrot_on_a_stick) continue;
                name = player.getGameProfile().getName();
                EnemyManager.murders.add(name);
                continue;
            }
            if (target != 1 || item != Items.bow) continue;
            name = player.getGameProfile().getName();
            EnemyManager.detects.add(name);
        }
    }

    public static boolean checkEnemyNameColor(EntityLivingBase entity) {
        String name = entity.func_145748_c_().func_150254_d();
        return !Utils.getEntityNameColor((EntityLivingBase)Wrapper.INSTANCE.player()).equals(Utils.getEntityNameColor(entity));
    }

    public static String getEntityNameColor(EntityLivingBase entity) {
        String name = entity.func_145748_c_().func_150254_d();
        if (name.contains("\u00a7")) {
            if (name.contains("\u00a71")) {
                return "\u00a71";
            }
            if (name.contains("\u00a72")) {
                return "\u00a72";
            }
            if (name.contains("\u00a73")) {
                return "\u00a73";
            }
            if (name.contains("\u00a74")) {
                return "\u00a74";
            }
            if (name.contains("\u00a75")) {
                return "\u00a75";
            }
            if (name.contains("\u00a76")) {
                return "\u00a76";
            }
            if (name.contains("\u00a77")) {
                return "\u00a77";
            }
            if (name.contains("\u00a78")) {
                return "\u00a78";
            }
            if (name.contains("\u00a79")) {
                return "\u00a79";
            }
            if (name.contains("\u00a70")) {
                return "\u00a70";
            }
            if (name.contains("\u00a7e")) {
                return "\u00a7e";
            }
            if (name.contains("\u00a7d")) {
                return "\u00a7d";
            }
            if (name.contains("\u00a7a")) {
                return "\u00a7a";
            }
            if (name.contains("\u00a7b")) {
                return "\u00a7b";
            }
            if (name.contains("\u00a7c")) {
                return "\u00a7c";
            }
            if (name.contains("\u00a7f")) {
                return "\u00a7f";
            }
        }
        return "null";
    }

    public static int getPlayerArmorColor(EntityPlayer player, ItemStack stack) {
        if (player == null || stack == null || stack.getItem() == null || !(stack.getItem() instanceof ItemArmor)) {
            return -1;
        }
        ItemArmor itemArmor = (ItemArmor)stack.getItem();
        if (itemArmor == null || itemArmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER) {
            return -1;
        }
        return itemArmor.getColor(stack);
    }

    public static boolean checkEnemyColor(EntityPlayer enemy) {
        int colorEnemy0 = Utils.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(0));
        int colorEnemy1 = Utils.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(1));
        int colorEnemy2 = Utils.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(2));
        int colorEnemy3 = Utils.getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(3));
        int colorPlayer0 = Utils.getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(0));
        int colorPlayer1 = Utils.getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(1));
        int colorPlayer2 = Utils.getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(2));
        int colorPlayer3 = Utils.getPlayerArmorColor((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(3));
        return !(colorEnemy0 == colorPlayer0 && colorPlayer0 != -1 && colorEnemy0 != 1 || colorEnemy1 == colorPlayer1 && colorPlayer1 != -1 && colorEnemy1 != 1 || colorEnemy2 == colorPlayer2 && colorPlayer2 != -1 && colorEnemy2 != 1) && (colorEnemy3 != colorPlayer3 || colorPlayer3 == -1 || colorEnemy3 == 1);
    }

    public static boolean screenCheck() {
        return !(Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer) && !(Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat) && !(Wrapper.INSTANCE.mc().currentScreen instanceof GuiScreen);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static EntityLivingBase getWorldEntityByName(String name) {
        EntityLivingBase entity = null;
        for (Entity object : Utils.getEntityList()) {
            EntityLivingBase entityForCheck;
            if (!(object instanceof EntityLivingBase) || !(entityForCheck = (EntityLivingBase)object).getName().contains(name)) continue;
            entity = entityForCheck;
        }
        return entity;
    }

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg((World)Wrapper.INSTANCE.world());
        var4.posX = (double)var0 + 0.5;
        var4.posY = (double)var1 + 0.5;
        var4.posZ = (double)var2 + 0.5;
        var4.posX += (double)var3.func_176730_m().func_177958_n() * 0.25;
        var4.posY += (double)var3.func_176730_m().func_177956_o() * 0.25;
        var4.posZ += (double)var3.func_176730_m().func_177952_p() * 0.25;
        return Utils.getDirectionToEntity((Entity)var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{Utils.getYaw(var0) + Wrapper.INSTANCE.player().rotationYaw, Utils.getPitch(var0) + Wrapper.INSTANCE.player().rotationPitch};
    }

    public static float getPitch(Entity entity) {
        double x = entity.posX - Wrapper.INSTANCE.player().posX;
        double y = entity.posY - Wrapper.INSTANCE.player().posY;
        double z = entity.posZ - Wrapper.INSTANCE.player().posZ;
        double pitch = Math.asin(y /= (double)Wrapper.INSTANCE.player().getDistanceToEntity(entity)) * 57.29577951308232;
        pitch = -pitch;
        return (float)pitch;
    }

    public static float getYaw(Entity entity) {
        double x = entity.posX - Wrapper.INSTANCE.player().posX;
        double y = entity.posY - Wrapper.INSTANCE.player().posY;
        double z = entity.posZ - Wrapper.INSTANCE.player().posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        return (float)yaw;
    }

    public static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = Utils.getEyesPos();
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Wrapper.INSTANCE.player().rotationYaw + MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.player().rotationYaw)), Wrapper.INSTANCE.player().rotationPitch + MathHelper.func_76142_g((float)(pitch - Wrapper.INSTANCE.player().rotationPitch))};
    }

    public static float getDirection() {
        float var1 = Wrapper.INSTANCE.player().rotationYaw;
        if (Wrapper.INSTANCE.player().field_191988_bg < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Wrapper.INSTANCE.player().field_191988_bg < 0.0f) {
            forward = -0.5f;
        } else if (Wrapper.INSTANCE.player().field_191988_bg > 0.0f) {
            forward = 0.5f;
        }
        if (Wrapper.INSTANCE.player().moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Wrapper.INSTANCE.player().moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public static void faceVectorPacket(Vec3d vec) {
        float[] rotations = Utils.getNeededRotations(vec);
        EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;
        float preYaw = pl.rotationYaw;
        float prePitch = pl.rotationPitch;
        pl.rotationYaw = rotations[0];
        pl.rotationPitch = rotations[1];
        try {
            Method onUpdateWalkingPlayer = pl.getClass().getDeclaredMethod(Mapping.onUpdateWalkingPlayer, new Class[0]);
            onUpdateWalkingPlayer.setAccessible(true);
            onUpdateWalkingPlayer.invoke((Object)pl, new Object[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        pl.rotationYaw = preYaw;
        pl.rotationPitch = prePitch;
    }

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        if (entity.width == width && entity.height == height) {
            return;
        }
        entity.width = width;
        entity.height = height;
        double d0 = (double)width / 2.0;
        entity.func_174826_a(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0, entity.posY + (double)entity.height, entity.posZ + d0));
    }

    public static boolean placeBlockScaffold(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec;
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.getOpposite();
            Vec3d vec3d = new Vec3d((Vec3i)pos);
            Vec3d vec3d2 = new Vec3d((Vec3i)neighbor);
            if (!(eyesPos.func_72436_e(vec3d.func_72441_c(0.5, 0.5, 0.5)) < eyesPos.func_72436_e(vec3d2.func_72441_c(0.5, 0.5, 0.5))) || !Utils.canBeClicked(neighbor) || !(eyesPos.func_72436_e(hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5))) <= 18.0625)) continue;
            Utils.faceVectorPacketInstant(hitVec);
            Utils.swingMainHand();
            Wrapper.INSTANCE.controller().func_187099_a(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.world(), neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            try {
                Field f = Minecraft.class.getDeclaredField(Mapping.rightClickDelayTimer);
                f.setAccessible(true);
                f.set(Wrapper.INSTANCE.mc(), 4);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static boolean isInsideBlock(EntityLivingBase entity) {
        for (int x = MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72340_a); x < MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72336_d) + 1; ++x) {
            for (int y = MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72338_b); y < MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72337_e) + 1; ++y) {
                for (int z = MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72339_c); z < MathHelper.func_76128_c((double)entity.func_174813_aQ().field_72334_f) + 1; ++z) {
                    AxisAlignedBB boundingBox;
                    Block block = BlockUtils.getBlock(new BlockPos(x, y, z));
                    if (block == null || block instanceof BlockAir || (boundingBox = block.func_180646_a(BlockUtils.getState(new BlockPos(x, y, z)), (IBlockAccess)Wrapper.INSTANCE.world(), new BlockPos(x, y, z))) == null || !entity.func_174813_aQ().func_72326_a(boundingBox)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBlockEdge(EntityLivingBase entity) {
        return Wrapper.INSTANCE.world().func_184144_a((Entity)entity, entity.func_174813_aQ().func_72317_d(0.0, -0.5, 0.0).func_72321_a(0.001, 0.0, 0.001)).isEmpty() && entity.onGround;
    }

    public static void faceEntity(EntityLivingBase entity) {
        if (entity == null) {
            return;
        }
        double d0 = entity.posX - Wrapper.INSTANCE.player().posX;
        double d4 = entity.posY - Wrapper.INSTANCE.player().posY;
        double d1 = entity.posZ - Wrapper.INSTANCE.player().posZ;
        double d2 = Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        double d3 = MathHelper.func_76133_a((double)(d0 * d0 + d1 * d1));
        float f = (float)(Math.atan2(d1, d0) * 180.0 / Math.PI) - 90.0f;
        float f1 = (float)(-(Math.atan2(d2, d3) * 180.0 / Math.PI));
        Wrapper.INSTANCE.player().rotationYaw = f;
        Wrapper.INSTANCE.player().rotationPitch = f1;
    }

    public static void assistFaceEntity(Entity entity, float yaw, float pitch) {
        double yDifference;
        if (entity == null) {
            return;
        }
        double diffX = entity.posX - Wrapper.INSTANCE.player().posX;
        double diffZ = entity.posZ - Wrapper.INSTANCE.player().posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            yDifference = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() - (Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight());
        } else {
            yDifference = (entity.func_174813_aQ().field_72338_b + entity.func_174813_aQ().field_72337_e) / 2.0 - (Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight());
        }
        double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        float rotationYaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float rotationPitch = (float)(-(Math.atan2(yDifference, dist) * 180.0 / Math.PI));
        if (yaw > 0.0f) {
            Wrapper.INSTANCE.player().rotationYaw = Utils.updateRotation(Wrapper.INSTANCE.player().rotationYaw, rotationYaw, yaw / 4.0f);
        }
        if (pitch > 0.0f) {
            Wrapper.INSTANCE.player().rotationPitch = Utils.updateRotation(Wrapper.INSTANCE.player().rotationPitch, rotationPitch, pitch / 4.0f);
        }
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.func_76142_g((float)(p_70663_2_ - p_70663_1_));
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }

    public static int getDistanceFromMouse(EntityLivingBase entity) {
        float[] neededRotations = Utils.getRotationsNeeded((Entity)entity);
        if (neededRotations != null) {
            float neededYaw = Wrapper.INSTANCE.player().rotationYaw - neededRotations[0];
            float neededPitch = Wrapper.INSTANCE.player().rotationPitch - neededRotations[1];
            float distanceFromMouse = MathHelper.func_76129_c((float)(neededYaw * neededYaw + neededPitch * neededPitch * 2.0f));
            return (int)distanceFromMouse;
        }
        return -1;
    }

    public static float[] getSmoothNeededRotations(Vec3d vec, float yaw, float pitch) {
        Vec3d eyesPos = Utils.getEyesPos();
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float rotationYaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float rotationPitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Utils.updateRotation(Wrapper.INSTANCE.player().rotationYaw, rotationYaw, yaw / 4.0f), Utils.updateRotation(Wrapper.INSTANCE.player().rotationPitch, rotationPitch, pitch / 4.0f)};
    }

    public static float[] getRotationsNeeded(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - Wrapper.INSTANCE.mc().thePlayer.posX;
        double diffZ = entity.posZ - Wrapper.INSTANCE.mc().thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() - (Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight());
        } else {
            diffY = (entity.func_174813_aQ().field_72338_b + entity.func_174813_aQ().field_72337_e) / 2.0 - (Wrapper.INSTANCE.mc().thePlayer.posY + (double)Wrapper.INSTANCE.mc().thePlayer.getEyeHeight());
        }
        double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{Wrapper.INSTANCE.mc().thePlayer.rotationYaw + MathHelper.func_76142_g((float)(yaw - Wrapper.INSTANCE.mc().thePlayer.rotationYaw)), Wrapper.INSTANCE.mc().thePlayer.rotationPitch + MathHelper.func_76142_g((float)(pitch - Wrapper.INSTANCE.mc().thePlayer.rotationPitch))};
    }

    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
        byte[] bytes = main.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5 = md.digest(bytes);
        int i = 0;
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
            if (i != md5.length - 1) {
                sb.append("-");
            }
            ++i;
        }
        return sb.toString();
    }

    public static boolean checkuser() {
        try {
            if (WebUtils.get("https://gitee.com/beimian-hurricane/codes/9j3p4subfkaiyt8egdw2586/raw?blob_name=HWID.txt").contains(Utils.getHWID())) {
                ChatUtils.message("OK!");
                return true;
            }
            ChatUtils.error("verification failed! your uuid is" + Utils.getHWID());
            return false;
        }
        catch (NoSuchAlgorithmException e) {
            ChatUtils.error("Network Error!");
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            ChatUtils.error("Error");
            e.printStackTrace();
        }
        catch (IOException e) {
            ChatUtils.error("Error");
            e.printStackTrace();
        }
        return true;
    }

    static {
        rotationsToBlock = null;
        RANDOM = new Random();
    }

    public static class Player {
        public static Minecraft mc = Minecraft.getMinecraft();

        public static void hotkeyToSlot(int slot) {
            if (!Player.isPlayerInGame()) {
                return;
            }
            Player.mc.thePlayer.inventory.currentItem = slot;
        }

        public static void sendMessageToSelf(String txt) {
            ChatUtils.message(txt);
        }

        public static boolean isPlayerInGame() {
            return Player.mc.thePlayer != null && Player.mc.theWorld != null;
        }

        public static boolean isMoving() {
            return Player.mc.thePlayer.field_191988_bg != 0.0f || Player.mc.thePlayer.moveStrafing != 0.0f;
        }

        public static void aim(Entity en, float ps, boolean pc) {
            float[] t;
            if (en != null && (t = Player.getTargetRotations(en)) != null) {
                float y = t[0];
                float p = t[1] + 4.0f + ps;
                if (pc) {
                    mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Rotation(y, p, Player.mc.thePlayer.onGround));
                } else {
                    Player.mc.thePlayer.rotationYaw = y;
                    Player.mc.thePlayer.rotationPitch = p;
                }
            }
        }

        public static double fovFromEntity(Entity en) {
            return ((double)(Player.mc.thePlayer.rotationYaw - Player.fovToEntity(en)) % 360.0 + 540.0) % 360.0 - 180.0;
        }

        public static float fovToEntity(Entity ent) {
            double x = ent.posX - Player.mc.thePlayer.posX;
            double z = ent.posZ - Player.mc.thePlayer.posZ;
            double yaw = Math.atan2(x, z) * 57.2957795;
            return (float)(yaw * -1.0);
        }

        public static boolean fov(Entity entity, float fov) {
            fov = (float)((double)fov * 0.5);
            double v = ((double)(Player.mc.thePlayer.rotationYaw - Player.fovToEntity(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
            return v > 0.0 && v < (double)fov || (double)(-fov) < v && v < 0.0;
        }

        public static float[] getTargetRotations(Entity q) {
            double diffY;
            if (q == null) {
                return null;
            }
            double diffX = q.posX - Player.mc.thePlayer.posX;
            if (q instanceof EntityLivingBase) {
                EntityLivingBase en = (EntityLivingBase)q;
                diffY = en.posY + (double)en.getEyeHeight() * 0.9 - (Player.mc.thePlayer.posY + (double)Player.mc.thePlayer.getEyeHeight());
            } else {
                diffY = (q.func_174813_aQ().field_72338_b + q.func_174813_aQ().field_72337_e) / 2.0 - (Player.mc.thePlayer.posY + (double)Player.mc.thePlayer.getEyeHeight());
            }
            double diffZ = q.posZ - Player.mc.thePlayer.posZ;
            double dist = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
            return new float[]{Player.mc.thePlayer.rotationYaw + Player.wrapAngleTo180_float(yaw - Player.mc.thePlayer.rotationYaw), Player.mc.thePlayer.rotationPitch + Player.wrapAngleTo180_float(pitch - Player.mc.thePlayer.rotationPitch)};
        }

        public static float wrapAngleTo180_float(float value) {
            if ((value %= 360.0f) >= 180.0f) {
                value -= 360.0f;
            }
            if (value < -180.0f) {
                value += 360.0f;
            }
            return value;
        }

        public static void fixMovementSpeed(double s, boolean m) {
            if (!m || Player.isMoving()) {
                Player.mc.thePlayer.motionX = -Math.sin(Player.correctRotations()) * s;
                Player.mc.thePlayer.motionZ = Math.cos(Player.correctRotations()) * s;
            }
        }

        public static void bop(double s) {
            double forward = Player.mc.thePlayer.movementInput.field_192832_b;
            double strafe = Player.mc.thePlayer.movementInput.moveStrafe;
            float yaw = Player.mc.thePlayer.rotationYaw;
            if (forward == 0.0 && strafe == 0.0) {
                Player.mc.thePlayer.motionX = 0.0;
                Player.mc.thePlayer.motionZ = 0.0;
            } else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += (float)(forward > 0.0 ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += (float)(forward > 0.0 ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                double rad = Math.toRadians(yaw + 90.0f);
                double sin = Math.sin(rad);
                double cos = Math.cos(rad);
                Player.mc.thePlayer.motionX = forward * s * cos + strafe * s * sin;
                Player.mc.thePlayer.motionZ = forward * s * sin - strafe * s * cos;
            }
        }

        public static float correctRotations() {
            float yw = Player.mc.thePlayer.rotationYaw;
            if (Player.mc.thePlayer.field_191988_bg < 0.0f) {
                yw += 180.0f;
            }
            float f = Player.mc.thePlayer.field_191988_bg < 0.0f ? -0.5f : (Player.mc.thePlayer.field_191988_bg > 0.0f ? 0.5f : 1.0f);
            if (Player.mc.thePlayer.moveStrafing > 0.0f) {
                yw -= 90.0f * f;
            }
            if (Player.mc.thePlayer.moveStrafing < 0.0f) {
                yw += 90.0f * f;
            }
            return yw *= (float)Math.PI / 180;
        }

        public static double pythagorasMovement() {
            return Math.sqrt(Player.mc.thePlayer.motionX * Player.mc.thePlayer.motionX + Player.mc.thePlayer.motionZ * Player.mc.thePlayer.motionZ);
        }
    }
}

