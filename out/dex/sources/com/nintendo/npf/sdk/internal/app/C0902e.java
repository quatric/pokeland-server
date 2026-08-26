package com.nintendo.npf.sdk.internal.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0953c;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.NintendoAccount;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.e */
/* JADX INFO: compiled from: NintendoAccountActivityLegacyStrategy.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0902e implements ActivityStrategy {

    /* JADX INFO: renamed from: a */
    private static String f1173a = "e";

    /* JADX INFO: renamed from: c */
    private NintendoAccountActivity f1175c;

    /* JADX INFO: renamed from: f */
    private NPFError f1178f;

    /* JADX INFO: renamed from: b */
    private final InterfaceC0875a f1174b = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: d */
    private boolean f1176d = false;

    /* JADX INFO: renamed from: e */
    private boolean f1177e = false;

    public C0902e(NintendoAccountActivity nintendoAccountActivity) {
        this.f1175c = nintendoAccountActivity;
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1167a() {
        if (!this.f1176d) {
            this.f1176d = true;
            return;
        }
        this.f1177e = true;
        this.f1174b.mo1051e().m1711a(this.f1175c.getIntent().getData());
        this.f1175c.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1168a(int i, int i2, Intent intent) {
        C0955e.m1393b(f1173a, "onActivityResult requestCode : " + i);
        C0955e.m1391a(f1173a, "onActivityResult resultCode : " + i2);
        if (this.f1174b.mo1049c().m1524e() != null) {
            this.f1174b.mo1049c().m1524e().mo1148a(i, i2, intent);
        }
        this.f1175c.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1169a(Intent intent) {
        this.f1175c.setIntent(intent);
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1170a(Bundle bundle) {
        int i;
        this.f1175c.requestWindowFeature(1);
        try {
            i = this.f1175c.getIntent().getExtras().getInt("requestCode");
        } catch (Exception unused) {
            C0955e.m1391a(f1173a, "onCreate intent is null");
            i = 0;
        }
        C0955e.m1391a(f1173a, "onCreate requestCode : " + i);
        if (i != 342) {
            this.f1175c.startActivity(this.f1175c.getPackageManager().getLaunchIntentForPackage(this.f1175c.getPackageName()));
            C0953c.f1282a = true;
            this.f1175c.finish();
            return;
        }
        if (this.f1174b.mo1065s().m1330e() == null) {
            C0953c.f1282a = true;
            this.f1175c.finish();
            return;
        }
        String string = this.f1175c.getIntent().getExtras().getString("queryParameter");
        String str = (this.f1174b.mo1065s().m1315F() ? "http" : "https") + "://" + this.f1174b.mo1065s().m1330e() + "/connect/1.0.0/authorize?" + string;
        C0955e.m1391a(f1173a, "url : " + str);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (this.f1175c.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            this.f1175c.startActivity(intent);
        } else {
            this.f1178f = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Browser is not available");
            this.f1175c.finish();
        }
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1171b() {
        C0955e.m1391a(f1173a, "onDestroy");
        if (this.f1177e || this.f1174b.mo1051e().m1723c() == null) {
            return;
        }
        if (this.f1178f == null) {
            this.f1178f = new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for authorization");
        }
        C0954d.m1389b("naauth_error", "NAAuth#NintendoAccountActivity#Error", this.f1178f);
        this.f1174b.mo1051e().m1717a((NintendoAccount) null, this.f1178f);
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1172b(Bundle bundle) {
    }
}
