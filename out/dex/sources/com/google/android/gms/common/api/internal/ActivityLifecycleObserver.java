package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public abstract class ActivityLifecycleObserver {
    @KeepForSdk
    /* JADX INFO: renamed from: of */
    public static final ActivityLifecycleObserver m419of(Activity activity) {
        return new zaa(activity);
    }

    @KeepForSdk
    public abstract ActivityLifecycleObserver onStopCallOnce(Runnable runnable);
}
