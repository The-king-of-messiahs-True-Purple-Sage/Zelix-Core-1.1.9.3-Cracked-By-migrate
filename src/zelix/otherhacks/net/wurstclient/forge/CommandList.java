/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge;

import zelix.otherhacks.net.wurstclient.forge.commands.BindsCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.ClearCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.GmCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.HelpCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.SayCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.SetCheckboxCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.SetEnumCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.SetSliderCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.TCmd;
import zelix.otherhacks.net.wurstclient.forge.commands.VClipCmd;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WCommandList;

public final class CommandList
extends WCommandList {
    public final BindsCmd bindsCmd = this.register(new BindsCmd());
    public final ClearCmd clearCmd = this.register(new ClearCmd());
    public final GmCmd gmCmd = this.register(new GmCmd());
    public final HelpCmd helpCmd = this.register(new HelpCmd());
    public final SayCmd sayCmd = this.register(new SayCmd());
    public final SetCheckboxCmd setCheckboxCmd = this.register(new SetCheckboxCmd());
    public final SetEnumCmd setEnumCmd = this.register(new SetEnumCmd());
    public final SetSliderCmd setSliderCmd = this.register(new SetSliderCmd());
    public final TCmd tCmd = this.register(new TCmd());
    public final VClipCmd vClipCmd = this.register(new VClipCmd());
}

