package com.unity3d.player;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleVrApi {

    /* JADX INFO: renamed from: a */
    private static AtomicReference f1840a = new AtomicReference();

    private GoogleVrApi() {
    }

    /* JADX INFO: renamed from: a */
    static void m1788a() {
        f1840a.set(null);
    }

    /* JADX INFO: renamed from: a */
    static void m1789a(InterfaceC1124f interfaceC1124f) {
        f1840a.compareAndSet(null, new GoogleVrProxy(interfaceC1124f));
    }

    /* JADX INFO: renamed from: b */
    static GoogleVrProxy m1790b() {
        return (GoogleVrProxy) f1840a.get();
    }

    public static GoogleVrVideo getGoogleVrVideo() {
        return (GoogleVrVideo) f1840a.get();
    }
}
