package com.p001a.p002a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: renamed from: com.a.a.e */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0173e extends BroadcastReceiver {

    /* JADX INFO: renamed from: a */
    @NonNull
    static final CountDownLatch f29a = new CountDownLatch(1);

    @Override // android.content.BroadcastReceiver
    public final void onReceive(@Nullable Context context, @Nullable Intent intent) {
        if (context != null && intent != null) {
            try {
                if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra("referrer");
                    if (stringExtra != null && !stringExtra.trim().isEmpty()) {
                        C0174f.m16a(3, "RRC", "onReceive", stringExtra);
                        C0178j c0178j = new C0178j(context);
                        if (c0178j.m237b("referrer_source") == null) {
                            c0178j.m236a("referrer_source", (Object) "gplay");
                            c0178j.m236a("referrer", (Object) stringExtra);
                        } else {
                            C0174f.m16a(2, "RRC", "onReceive", "Skip: Previous referrer exists");
                        }
                        f29a.countDown();
                        return;
                    }
                    C0174f.m16a(2, "RRC", "onReceive", "Invalid Referrer");
                    return;
                }
            } catch (Throwable th) {
                C0174f.m16a(1, "RRC", "onReceive", "Unknown error when receiving install referrer", th);
            }
        }
        C0174f.m16a(2, "RRC", "onReceive", "Invalid Intent/Action");
    }
}
