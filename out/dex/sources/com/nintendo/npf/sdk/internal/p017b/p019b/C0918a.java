package com.nintendo.npf.sdk.internal.p017b.p019b;

import android.text.TextUtils;
import com.google.api.client.http.HttpMethods;
import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.a */
/* JADX INFO: compiled from: BaseHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0918a {

    /* JADX INFO: renamed from: a */
    protected String f1182a;

    /* JADX INFO: renamed from: b */
    protected String f1183b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.a$a */
    /* JADX INFO: compiled from: BaseHttpClient.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1145a(JSONArray jSONArray, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.a$b */
    /* JADX INFO: compiled from: BaseHttpClient.java */
    public interface b {
        /* JADX INFO: renamed from: a */
        void mo1143a(JSONObject jSONObject, NPFError nPFError);
    }

    /* JADX INFO: renamed from: a */
    private void m1212a(String str, String str2, Map<String, String> map, Map<String, String> map2, byte[] bArr, String str3, Boolean bool, final b bVar) {
        C0920c.m1229a(str, this.f1182a, this.f1183b, str2, map, map2, str3, bArr, new C0920c.a() { // from class: com.nintendo.npf.sdk.internal.b.b.a.1
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0920c.a
            /* JADX INFO: renamed from: a */
            public void mo1225a(int i, Map<String, List<String>> map3, String str4) {
                NPFError nPFErrorM1657a;
                JSONObject jSONObject = null;
                if (i < 200 || i >= 300) {
                    nPFErrorM1657a = C1025o.m1657a(i, str4);
                } else if (TextUtils.isEmpty(str4)) {
                    nPFErrorM1657a = null;
                } else {
                    try {
                        jSONObject = new JSONObject(str4);
                        nPFErrorM1657a = null;
                    } catch (JSONException e) {
                        nPFErrorM1657a = C1025o.m1658a(e);
                    }
                }
                bVar.mo1143a(jSONObject, nPFErrorM1657a);
            }
        }, bool.booleanValue());
    }

    /* JADX INFO: renamed from: a */
    private void m1213a(String str, String str2, Map<String, String> map, Map<String, String> map2, byte[] bArr, String str3, boolean z, final a aVar) {
        C0920c.m1229a(str, this.f1182a, this.f1183b, str2, map, map2, str3, bArr, new C0920c.a() { // from class: com.nintendo.npf.sdk.internal.b.b.a.2
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0920c.a
            /* JADX INFO: renamed from: a */
            public void mo1225a(int i, Map<String, List<String>> map3, String str4) {
                NPFError nPFErrorM1657a;
                JSONArray jSONArray = null;
                if (i < 200 || i >= 300) {
                    nPFErrorM1657a = C1025o.m1657a(i, str4);
                } else if (TextUtils.isEmpty(str4)) {
                    nPFErrorM1657a = null;
                } else {
                    try {
                        jSONArray = new JSONArray(str4);
                        nPFErrorM1657a = null;
                    } catch (JSONException e) {
                        nPFErrorM1657a = C1025o.m1658a(e);
                    }
                }
                aVar.mo1145a(jSONArray, nPFErrorM1657a);
            }
        }, z);
    }

    /* JADX INFO: renamed from: a */
    protected void m1214a(String str, Map<String, String> map, Map<String, String> map2, boolean z, a aVar) {
        m1213a("GET", str, map, map2, (byte[]) null, (String) null, z, aVar);
    }

    /* JADX INFO: renamed from: a */
    protected void m1215a(String str, Map<String, String> map, Map<String, String> map2, boolean z, b bVar) {
        m1212a("GET", str, map, map2, (byte[]) null, (String) null, Boolean.valueOf(z), bVar);
    }

    /* JADX INFO: renamed from: a */
    protected void m1216a(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr, String str2, boolean z, a aVar) {
        m1213a("POST", str, map, map2, bArr, str2, z, aVar);
    }

    /* JADX INFO: renamed from: a */
    protected void m1217a(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr, String str2, boolean z, b bVar) {
        m1212a("POST", str, map, map2, bArr, str2, Boolean.valueOf(z), bVar);
    }

    /* JADX INFO: renamed from: a */
    protected void m1218a(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr, boolean z, a aVar) {
        m1213a(HttpMethods.PUT, str, map, map2, bArr, C0849c.f862b, z, aVar);
    }

    /* JADX INFO: renamed from: a */
    protected void m1219a(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr, boolean z, b bVar) {
        m1212a(HttpMethods.PUT, str, map, map2, bArr, C0849c.f862b, Boolean.valueOf(z), bVar);
    }

    /* JADX INFO: renamed from: a */
    public void m1220a(boolean z, String str) {
        this.f1182a = z ? "http" : "https";
        this.f1183b = str;
    }

    /* JADX INFO: renamed from: a */
    protected byte[] m1221a(JSONArray jSONArray) {
        return jSONArray.toString().getBytes();
    }

    /* JADX INFO: renamed from: a */
    protected byte[] m1222a(JSONObject jSONObject) {
        return jSONObject.toString().getBytes();
    }

    /* JADX INFO: renamed from: b */
    protected void m1223b(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr, boolean z, b bVar) {
        m1212a(HttpMethods.PATCH, str, map, map2, bArr, "application/json-patch+json", Boolean.valueOf(z), bVar);
    }

    /* JADX INFO: renamed from: b */
    protected byte[] m1224b(JSONArray jSONArray) {
        return C0919b.m1226a(jSONArray.toString().getBytes());
    }
}
