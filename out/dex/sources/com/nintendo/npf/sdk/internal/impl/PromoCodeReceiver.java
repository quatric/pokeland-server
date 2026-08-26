package com.nintendo.npf.sdk.internal.impl;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PromoCodeReceiver extends BroadcastReceiver {

    /* JADX INFO: renamed from: a */
    private static final String f1343a = "PromoCodeReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        C0955e.m1391a(f1343a, "PromoCodeReceiver#onReceive");
        InterfaceC0875a.a.m1071a((Application) context.getApplicationContext());
        InterfaceC0875a.a.m1072b().mo1069w().m1734a(false, null);
    }
}
