package com.nintendo.npf.sdk.internal.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.d */
/* JADX INFO: compiled from: NintendoAccountActivityExperimentalStrategy.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0901d implements ActivityStrategy {

    /* JADX INFO: renamed from: a */
    private static String f1166a = "d";

    /* JADX INFO: renamed from: b */
    private static String f1167b = "authSession";

    /* JADX INFO: renamed from: d */
    private NintendoAccountActivity f1169d;

    /* JADX INFO: renamed from: g */
    private NPFError f1172g;

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1168c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: e */
    private boolean f1170e = false;

    /* JADX INFO: renamed from: f */
    private boolean f1171f = false;

    public C0901d(NintendoAccountActivity nintendoAccountActivity) {
        this.f1169d = nintendoAccountActivity;
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1167a() {
        C0955e.m1393b(f1166a, "onResume");
        if (!this.f1170e && !this.f1171f) {
            this.f1170e = true;
        } else {
            if (this.f1169d.isFinishing()) {
                return;
            }
            this.f1169d.finish();
        }
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1168a(int i, int i2, Intent intent) {
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1169a(Intent intent) {
        C0955e.m1393b(f1166a, "onNewIntent");
        this.f1171f = true;
        this.f1168c.mo1051e().m1713a(this.f1169d.m1159a(intent.getData()));
        this.f1169d.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1170a(Bundle bundle) {
        int i;
        C0949d c0949d;
        this.f1169d.requestWindowFeature(1);
        if (bundle != null && (c0949d = (C0949d) bundle.getParcelable(f1167b)) != null) {
            this.f1168c.mo1051e().m1716a(c0949d);
        }
        if (this.f1168c.mo1051e().m1709a() == null) {
            C0955e.m1396d(f1166a, "Illegal access has detected.");
            this.f1169d.finish();
            return;
        }
        if (bundle != null) {
            this.f1171f = true;
            return;
        }
        try {
            i = this.f1169d.getIntent().getExtras().getInt("requestCode");
        } catch (Exception unused) {
            C0955e.m1391a(f1166a, "onCreate intent is null");
            i = 0;
        }
        C0955e.m1391a(f1166a, "onCreate requestCode : " + i);
        String string = this.f1169d.getIntent().getExtras().getString("queryParameter");
        String str = (this.f1168c.mo1065s().m1315F() ? "http" : "https") + "://" + this.f1168c.mo1065s().m1330e() + "/connect/1.0.0/authorize?" + string;
        C0955e.m1391a(f1166a, "url : " + str);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (this.f1169d.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            this.f1169d.startActivity(intent);
        } else {
            this.f1172g = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Browser is not available");
            this.f1169d.finish();
        }
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1171b() {
        C0955e.m1391a(f1166a, "onDestroy: backFromBrowser: " + this.f1171f);
        if (this.f1168c.mo1051e().m1722b()) {
            if (this.f1172g == null) {
                this.f1172g = new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for authorization");
            }
            this.f1168c.mo1051e().m1712a(this.f1172g);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1172b(Bundle bundle) {
        bundle.putParcelable(f1167b, this.f1168c.mo1051e().m1709a());
    }
}
