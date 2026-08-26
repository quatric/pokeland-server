package com.metaps.analytics;

import android.content.Context;
import com.metaps.common.C0853g;
import com.metaps.common.Metaps;
import java.util.UUID;

/* JADX INFO: renamed from: com.metaps.analytics.y */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0836y {

    /* JADX INFO: renamed from: a */
    private static final long f840a = 0;

    /* JADX INFO: renamed from: b */
    private static final long f841b = 7776000;

    /* JADX INFO: renamed from: c */
    private final Context f842c;

    protected C0836y(Context context) {
        this.f842c = context;
    }

    /* JADX INFO: renamed from: b */
    private static String m891b() {
        return UUID.randomUUID().toString();
    }

    /* JADX INFO: renamed from: a */
    public String m892a() {
        return C0853g.m946d(this.f842c);
    }

    /* JADX INFO: renamed from: a */
    public String m893a(long j) {
        String strM892a = m895b(j) < ((long) Metaps.getAliveSessionTime()) ? m892a() : null;
        return strM892a == null ? m891b() : strM892a;
    }

    /* JADX INFO: renamed from: a */
    public boolean m894a(String str) {
        return C0853g.m944b(this.f842c, str);
    }

    /* JADX INFO: renamed from: b */
    public long m895b(long j) {
        return Math.min(f841b, Math.max(0L, j - C0853g.m945c(this.f842c)));
    }

    /* JADX INFO: renamed from: c */
    public boolean m896c(long j) {
        return C0853g.m940a(this.f842c, j);
    }
}
