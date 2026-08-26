package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.h */
/* JADX INFO: compiled from: IBillingMarketService.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface InterfaceC0883h {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.h$a */
    /* JADX INFO: compiled from: IBillingMarketService.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1138a(Bundle bundle, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.h$b */
    /* JADX INFO: compiled from: IBillingMarketService.java */
    public interface b {
        /* JADX INFO: renamed from: a */
        void mo1139a(HashMap<String, JSONObject> map, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.h$c */
    /* JADX INFO: compiled from: IBillingMarketService.java */
    public interface c {
        /* JADX INFO: renamed from: a */
        void mo1140a(int i, Intent intent);
    }

    /* JADX INFO: renamed from: a */
    int mo1084a(String str);

    /* JADX INFO: renamed from: a */
    void mo1085a(Activity activity, String str, BigDecimal bigDecimal, String str2, int i, String str3, String str4, c cVar);

    /* JADX INFO: renamed from: a */
    void mo1086a(a aVar);

    /* JADX INFO: renamed from: a */
    void mo1087a(@NonNull List<String> list, b bVar);
}
