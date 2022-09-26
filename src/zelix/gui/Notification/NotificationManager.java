/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.Notification;

import java.util.ArrayList;
import zelix.gui.Notification.Notification;

public class NotificationManager {
    public ArrayList<Notification> notifications = new ArrayList();

    public void add(Notification noti) {
        noti.y = this.notifications.size() * 25;
        this.notifications.add(noti);
    }

    public void draw() {
        int i = 0;
        Notification remove = null;
        for (Notification notification : this.notifications) {
            if (notification.x == 0.0f) {
                boolean bl = notification.in = !notification.in;
            }
            if (Math.abs((double)notification.x - notification.width) < 0.1 && !notification.in) {
                remove = notification;
            }
            notification.x = notification.in ? notification.animationUtils.animate(0.0f, notification.x, 0.1f) : (float)notification.animationUtils.animate(notification.width, (double)notification.x, (double)0.1f);
            notification.onRender();
            ++i;
        }
        if (remove != null) {
            this.notifications.remove(remove);
        }
    }
}

