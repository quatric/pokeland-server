package com.metaps.analytics;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.metaps.common.C0847a;
import com.p001a.p002a.C0173e;
import com.p001a.p002a.C0174f;
import com.p001a.p002a.InterfaceC0169a;

/* JADX INFO: renamed from: com.metaps.analytics.r */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0829r {

    /* JADX INFO: renamed from: a */
    private static C0829r f735a;

    /* JADX INFO: renamed from: b */
    private boolean f736b = false;

    /* JADX INFO: renamed from: com.metaps.analytics.r$1, reason: invalid class name */
    class AnonymousClass1 extends Thread {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ Context f737a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ String f738b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ CampaignListener f739c;

        AnonymousClass1(Context context, String str, CampaignListener campaignListener) {
            this.f737a = context;
            this.f738b = str;
            this.f739c = campaignListener;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.metaps.analytics.r.1.1
                @Override // java.lang.Runnable
                public void run() {
                    C0174f.m18a(new C0174f.a(AnonymousClass1.this.f737a).m48a(AnonymousClass1.this.f738b).m45a(new InterfaceC0169a() { // from class: com.metaps.analytics.r.1.1.1
                        @Override // com.p001a.p002a.InterfaceC0169a
                        /* JADX INFO: renamed from: a */
                        public void mo9a(String str) {
                            C0847a.m903a(C0819h.class.toString(), "#onAttributionReceived. jsonString:" + str);
                            Campaign campaignM609a = Campaign.m609a(str);
                            if (campaignM609a != null) {
                                C0847a.m903a(C0819h.class.toString(), "#onAttributionReceived. campaign found:" + campaignM609a.m610a());
                                if (AnonymousClass1.this.f739c != null) {
                                    AnonymousClass1.this.f739c.onCampaignMatched(campaignM609a);
                                }
                            }
                        }
                    }).m44a(C0847a.m907a() ? 5 : 0));
                }
            });
        }
    }

    private C0829r() {
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0829r m808a() {
        if (f735a == null) {
            f735a = new C0829r();
        }
        return f735a;
    }

    /* JADX INFO: renamed from: a */
    protected void m809a(Context context, Intent intent) {
        new C0173e().onReceive(context, intent);
    }

    /* JADX INFO: renamed from: a */
    public void m810a(Context context, String str, CampaignListener campaignListener) {
        if (this.f736b) {
            return;
        }
        this.f736b = true;
        new AnonymousClass1(context, str, campaignListener).start();
    }

    /* JADX INFO: renamed from: b */
    protected Campaign m811b() {
        return Campaign.m609a(C0174f.m32f());
    }
}
