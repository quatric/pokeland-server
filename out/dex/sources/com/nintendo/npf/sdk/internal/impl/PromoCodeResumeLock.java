package com.nintendo.npf.sdk.internal.impl;

import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.u, reason: from Kotlin metadata */
/* JADX INFO: compiled from: PromoCodeResumeLock.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/impl/PromoCodeResumeLock;", "", "activityLifecycleCallbacks", "Lcom/nintendo/npf/sdk/internal/impl/ActivityLifecycleCallbacksImpl;", "(Lcom/nintendo/npf/sdk/internal/impl/ActivityLifecycleCallbacksImpl;)V", "handler", "Landroid/os/Handler;", "clearTimer", "", "lock", "unlock", "delayMilli", "", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
public final class PromoCodeResumeLock {

    /* JADX INFO: renamed from: a */
    private final Handler f1683a;

    /* JADX INFO: renamed from: b */
    private final C0998a f1684b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.u$a */
    /* JADX INFO: compiled from: PromoCodeResumeLock.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m1984d2 = {"<anonymous>", "", "run"}, m1985k = 3, m1986mv = {1, 1, 16})
    static final class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            PromoCodeResumeLock.this.f1684b.m1518a(false);
        }
    }

    public PromoCodeResumeLock(@NotNull C0998a activityLifecycleCallbacks) {
        Intrinsics.checkParameterIsNotNull(activityLifecycleCallbacks, "activityLifecycleCallbacks");
        this.f1684b = activityLifecycleCallbacks;
        this.f1683a = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: renamed from: a */
    public final void m1737a() {
        this.f1684b.m1518a(true);
    }

    /* JADX INFO: renamed from: a */
    public final void m1738a(long j) {
        m1739b();
        this.f1683a.postDelayed(new a(), j);
    }

    /* JADX INFO: renamed from: b */
    public final void m1739b() {
        this.f1683a.removeCallbacksAndMessages(null);
    }
}
