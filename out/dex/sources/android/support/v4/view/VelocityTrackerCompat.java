package android.support.v4.view;

import android.view.VelocityTracker;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Deprecated
public final class VelocityTrackerCompat {
    private VelocityTrackerCompat() {
    }

    @Deprecated
    public static float getXVelocity(VelocityTracker velocityTracker, int i) {
        return velocityTracker.getXVelocity(i);
    }

    @Deprecated
    public static float getYVelocity(VelocityTracker velocityTracker, int i) {
        return velocityTracker.getYVelocity(i);
    }
}
