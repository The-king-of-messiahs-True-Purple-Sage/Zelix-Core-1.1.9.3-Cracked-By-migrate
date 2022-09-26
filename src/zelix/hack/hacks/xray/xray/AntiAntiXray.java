/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.xray.xray;

import java.util.ArrayList;
import java.util.List;
import zelix.hack.hacks.xray.etc.RefreshingJob;
import zelix.hack.hacks.xray.etc.Runner;

public class AntiAntiXray {
    public static List<RefreshingJob> jobs = new ArrayList<RefreshingJob>();

    public static void revealNewBlocks(int radX, int radY, int radZ, long delayInMS) {
        RefreshingJob rfj = new RefreshingJob(new Runner(radX, radY, radZ, delayInMS));
        jobs.add(rfj);
    }
}

