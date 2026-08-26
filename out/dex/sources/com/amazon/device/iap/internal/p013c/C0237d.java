package com.amazon.device.iap.internal.p013c;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.c.d */
/* JADX INFO: compiled from: PendingReceipt.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C0237d {

    /* JADX INFO: renamed from: a */
    private final String f224a;

    /* JADX INFO: renamed from: b */
    private final String f225b;

    /* JADX INFO: renamed from: c */
    private final long f226c;

    /* JADX INFO: renamed from: d */
    private final String f227d;

    public C0237d(String str, String str2, String str3, long j) {
        this.f224a = str;
        this.f225b = str2;
        this.f227d = str3;
        this.f226c = j;
    }

    /* JADX INFO: renamed from: a */
    public static C0237d m376a(String str) throws C0238e {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new C0237d(jSONObject.getString("KEY_USER_ID"), jSONObject.getString("KEY_RECEIPT_STRING"), jSONObject.getString("KEY_REQUEST_ID"), jSONObject.getLong("KEY_TIMESTAMP"));
        } catch (Throwable th) {
            throw new C0238e("Input invalid for PendingReceipt Object:" + str, th);
        }
    }

    /* JADX INFO: renamed from: a */
    public String m377a() {
        return this.f227d;
    }

    /* JADX INFO: renamed from: b */
    public String m378b() {
        return this.f225b;
    }

    /* JADX INFO: renamed from: c */
    public long m379c() {
        return this.f226c;
    }

    /* JADX INFO: renamed from: d */
    public String m380d() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("KEY_USER_ID", this.f224a);
        jSONObject.put("KEY_RECEIPT_STRING", this.f225b);
        jSONObject.put("KEY_REQUEST_ID", this.f227d);
        jSONObject.put("KEY_TIMESTAMP", this.f226c);
        return jSONObject.toString();
    }
}
