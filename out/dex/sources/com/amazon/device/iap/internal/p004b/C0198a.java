package com.amazon.device.iap.internal.p004b;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.a */
/* JADX INFO: compiled from: ReceiptParsingException.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0198a extends RuntimeException {

    /* JADX INFO: renamed from: a */
    private final String f158a;

    /* JADX INFO: renamed from: b */
    private final String f159b;

    public C0198a(String str, String str2, Throwable th) {
        super(th);
        this.f158a = str;
        this.f159b = str2;
    }

    /* JADX INFO: renamed from: a */
    public String m325a() {
        return this.f158a;
    }
}
