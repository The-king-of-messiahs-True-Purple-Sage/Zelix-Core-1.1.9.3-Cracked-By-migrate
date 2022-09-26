/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.xray.etc;

import zelix.hack.hacks.xray.etc.Runner;

public class RefreshingJob {
    public Runner refresher;
    public Thread runner;

    public RefreshingJob(Runner refresher) {
        this.refresher = refresher;
        this.runner = new Thread(refresher);
        this.runner.start();
    }

    public void cancel() {
        this.refresher.isRunning = false;
    }
}

