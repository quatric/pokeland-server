package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C1273zaa> zack;

    /* JADX INFO: renamed from: com.google.android.gms.common.api.internal.zaa$zaa, reason: collision with other inner class name */
    @VisibleForTesting(otherwise = 2)
    static class C1273zaa extends LifecycleCallback {
        private List<Runnable> zacl;

        private C1273zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacl = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static C1273zaa zaa(Activity activity) {
            C1273zaa c1273zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                c1273zaa = (C1273zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C1273zaa.class);
                if (c1273zaa == null) {
                    c1273zaa = new C1273zaa(fragment);
                }
            }
            return c1273zaa;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacl.add(runnable);
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        @MainThread
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacl;
                this.zacl = new ArrayList();
            }
            Iterator<Runnable> it = list.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }

    public zaa(Activity activity) {
        this(C1273zaa.zaa(activity));
    }

    @VisibleForTesting(otherwise = 2)
    private zaa(C1273zaa c1273zaa) {
        this.zack = new WeakReference<>(c1273zaa);
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C1273zaa c1273zaa = this.zack.get();
        if (c1273zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c1273zaa.zaa(runnable);
        return this;
    }
}
