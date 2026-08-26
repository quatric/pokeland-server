package com.p001a.p002a;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.m */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@AnyThread
final class C0181m {

    /* JADX INFO: renamed from: a */
    @NonNull
    final Context f107a;

    /* JADX INFO: renamed from: c */
    @NonNull
    final JSONObject f109c;

    /* JADX INFO: renamed from: d */
    @NonNull
    final C0178j f110d;

    /* JADX INFO: renamed from: e */
    @Nullable
    final InterfaceC0169a f111e;

    /* JADX INFO: renamed from: f */
    @Nullable
    final InterfaceC0170b f112f;

    /* JADX INFO: renamed from: g */
    @NonNull
    final Runnable f113g;

    /* JADX INFO: renamed from: h */
    @NonNull
    final InterfaceC0176h f114h;

    /* JADX INFO: renamed from: i */
    @NonNull
    final Handler f115i;

    /* JADX INFO: renamed from: j */
    @NonNull
    final Handler f116j;

    /* JADX INFO: renamed from: b */
    @NonNull
    final List<String> f108b = Collections.synchronizedList(new ArrayList());

    /* JADX INFO: renamed from: k */
    @NonNull
    final HandlerThread f117k = new HandlerThread("EventThread");

    /* JADX INFO: renamed from: l */
    @NonNull
    final HandlerThread f118l = new HandlerThread("ControllerThread");

    /* JADX INFO: renamed from: m */
    final String f119m = UUID.randomUUID().toString().substring(0, 5);

    /* JADX INFO: renamed from: n */
    transient boolean f120n = false;

    /* JADX INFO: renamed from: o */
    transient long f121o = C0178j.m202a();

    /* JADX INFO: renamed from: p */
    final long f122p = C0178j.m202a();

    /* JADX INFO: renamed from: q */
    @Nullable
    private Object f123q = null;

    C0181m(@NonNull Context context, @NonNull Runnable runnable, @NonNull InterfaceC0176h interfaceC0176h, @NonNull JSONObject jSONObject, @Nullable InterfaceC0169a interfaceC0169a, @Nullable InterfaceC0170b interfaceC0170b) {
        this.f107a = context;
        this.f113g = runnable;
        this.f114h = interfaceC0176h;
        this.f109c = jSONObject;
        this.f111e = interfaceC0169a;
        this.f112f = interfaceC0170b;
        this.f110d = new C0178j(context);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() { // from class: com.a.a.m.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
                C0174f.m16a(1, "STT", "uncaughtExcep", "Critical Error: Shutting Down", th);
                C0174f.m15a();
            }
        };
        this.f117k.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        this.f118l.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        this.f117k.start();
        this.f118l.start();
        this.f115i = new Handler(this.f117k.getLooper());
        this.f116j = new Handler(this.f118l.getLooper());
    }

    /* JADX INFO: renamed from: a */
    final void m250a(@NonNull Runnable runnable) {
        this.f115i.post(runnable);
    }

    /* JADX INFO: renamed from: a */
    final void m251a(@NonNull Runnable runnable, long j) {
        this.f116j.postDelayed(runnable, j);
    }

    /* JADX INFO: renamed from: a */
    final void m252a(@NonNull Runnable runnable, boolean z) {
        if (z) {
            this.f116j.postAtFrontOfQueue(runnable);
        } else {
            this.f116j.post(runnable);
        }
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: a */
    final boolean m253a() {
        return this.f123q != null;
    }

    /* JADX INFO: renamed from: b */
    final void m254b() {
        Object obj = this.f123q;
        if (obj != null) {
            this.f116j.removeCallbacksAndMessages(obj);
            this.f123q = null;
        }
    }

    /* JADX INFO: renamed from: b */
    final void m255b(@NonNull Runnable runnable, long j) {
        this.f123q = new Object();
        this.f116j.postAtTime(runnable, this.f123q, SystemClock.uptimeMillis() + j);
    }
}
