package com.nintendo.npf.sdk.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NintendoAccountActivity extends Activity {

    /* JADX INFO: renamed from: a */
    private static final String f1133a = "NintendoAccountActivity";

    /* JADX INFO: renamed from: b */
    private ActivityStrategy f1134b;

    /* JADX WARN: Code duplicated, block: B:21:0x0055  */
    /* JADX INFO: renamed from: a */
    public C0949d.b m1159a(Uri uri) {
        String str;
        String str2 = null;
        if (uri != null) {
            try {
                String fragment = uri.getFragment();
                if (fragment == null || fragment.isEmpty()) {
                    str = null;
                } else {
                    str = null;
                    String str3 = null;
                    for (String str4 : fragment.split("&")) {
                        String[] strArrSplit = str4.split("=");
                        String strDecode = URLDecoder.decode(strArrSplit[0], "UTF-8");
                        String strDecode2 = URLDecoder.decode(strArrSplit[1], "UTF-8");
                        if (strDecode.equals("state")) {
                            str = strDecode2;
                        } else if (strDecode.equals("session_token_code")) {
                            str3 = strDecode2;
                        }
                    }
                    str2 = str3;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            str = null;
        }
        return new C0949d.b(str2, str);
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        this.f1134b.mo1168a(i, i2, intent);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        int i = extras != null ? extras.getInt("requestCode") : 0;
        if (this.f1134b == null) {
            InterfaceC0875a.a.m1071a(getApplication());
            if (i != 343) {
                this.f1134b = new C0902e(this);
            } else {
                this.f1134b = new C0901d(this);
            }
        }
        this.f1134b.mo1170a(bundle);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.f1134b.mo1171b();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.f1134b.mo1169a(intent);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.f1134b.mo1167a();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.f1134b.mo1172b(bundle);
    }
}
