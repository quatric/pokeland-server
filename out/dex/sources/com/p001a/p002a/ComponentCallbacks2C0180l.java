package com.p001a.p002a;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnyThread;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import java.lang.ref.WeakReference;
import org.jetbrains.annotations.Contract;

/* JADX INFO: renamed from: com.a.a.l */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class ComponentCallbacks2C0180l implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    /* JADX INFO: renamed from: a */
    @NonNull
    private final Handler f101a;

    /* JADX INFO: renamed from: b */
    @NonNull
    private final InterfaceC0179k f102b;

    /* JADX INFO: renamed from: d */
    private boolean f104d;

    /* JADX INFO: renamed from: c */
    @Nullable
    private WeakReference<Activity> f103c = null;

    /* JADX INFO: renamed from: e */
    @NonNull
    private final Runnable f105e = new Runnable() { // from class: com.a.a.l.1
        @Override // java.lang.Runnable
        @WorkerThread
        public final void run() {
            C0174f.m16a(4, "SMO", "goInactive", new Object[0]);
            ComponentCallbacks2C0180l.this.f104d = false;
            ComponentCallbacks2C0180l.this.f102b.mo124c(false);
        }
    };

    @AnyThread
    ComponentCallbacks2C0180l(@NonNull Context context, @NonNull Handler handler, @NonNull InterfaceC0179k interfaceC0179k) {
        this.f104d = false;
        this.f102b = interfaceC0179k;
        this.f101a = handler;
        C0174f.m16a(5, "SMO", "SessionMonito", new Object[0]);
        if (context instanceof Application) {
            ((Application) context).registerActivityLifecycleCallbacks(this);
            context.registerComponentCallbacks(this);
        } else {
            C0174f.m16a(2, "SMO", "SessionMonito", "Invalid Application Context");
        }
        if (C0178j.m213a(context)) {
            this.f104d = true;
            interfaceC0179k.mo124c(true);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m248b() {
        this.f101a.removeCallbacks(this.f105e);
        if (this.f104d) {
            return;
        }
        C0174f.m16a(4, "SMO", "goActive", "goActive");
        this.f104d = true;
        this.f102b.mo124c(true);
    }

    @Contract(pure = true)
    /* JADX INFO: renamed from: a */
    final boolean m249a() {
        return this.f104d;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        C0174f.m16a(5, "SMO", "onActivityCre", new Object[0]);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityDestroyed(@NonNull Activity activity) {
        C0174f.m16a(5, "SMO", "onActivityDes", new Object[0]);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityPaused(@NonNull Activity activity) {
        C0174f.m16a(5, "SMO", "onActivityPau", new Object[0]);
        if (this.f103c == null) {
            this.f103c = new WeakReference<>(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityResumed(@NonNull Activity activity) {
        C0174f.m16a(5, "SMO", "onActivityRes", new Object[0]);
        if (this.f103c == null) {
            this.f103c = new WeakReference<>(activity);
        }
        m248b();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        C0174f.m16a(5, "SMO", "onActivitySav", new Object[0]);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityStarted(@NonNull Activity activity) {
        C0174f.m16a(5, "SMO", "onActivitySta", Boolean.toString(this.f104d));
        this.f103c = new WeakReference<>(activity);
        m248b();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    @MainThread
    public final void onActivityStopped(@NonNull Activity activity) {
        WeakReference<Activity> weakReference;
        Activity activity2;
        C0174f.m16a(5, "SMO", "onActivitySto", Boolean.toString(this.f104d));
        if (this.f104d && (weakReference = this.f103c) != null && (activity2 = weakReference.get()) != null && activity2.equals(activity)) {
            C0174f.m16a(5, "SMO", "onActivitySto", "?GoInactive?");
            this.f101a.removeCallbacks(this.f105e);
            this.f101a.postDelayed(this.f105e, 3000L);
        }
        this.f103c = null;
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(@NonNull Configuration configuration) {
        C0174f.m16a(5, "SMO", "onConfigurati", new Object[0]);
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
        C0174f.m16a(5, "SMO", "onLowMemory", new Object[0]);
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        C0174f.m16a(5, "SMO", "onTrimMemory", Boolean.toString(this.f104d));
        if (this.f104d && i == 20) {
            C0174f.m16a(5, "SMO", "onTrimMemory", "GoInactive");
            this.f101a.removeCallbacks(this.f105e);
            this.f104d = false;
            this.f102b.mo124c(false);
        }
    }
}
