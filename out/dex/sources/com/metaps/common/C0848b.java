package com.metaps.common;

/* JADX INFO: renamed from: com.metaps.common.b */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0848b extends Exception {

    /* JADX INFO: renamed from: a */
    private int f860a;

    public C0848b(String str) {
        super(str);
        this.f860a = -1;
    }

    public C0848b(String str, int i) {
        super(str);
        this.f860a = -1;
        this.f860a = i;
    }

    /* JADX INFO: renamed from: a */
    public int m916a() {
        return this.f860a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.f860a <= 0) {
            return super.getMessage();
        }
        return "[statusCode=" + this.f860a + "] " + super.getMessage();
    }
}
