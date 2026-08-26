package com.metaps.common;

/* JADX INFO: renamed from: com.metaps.common.d */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0850d implements Comparable<AbstractC0850d> {

    /* JADX INFO: renamed from: a */
    private long f878a;

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(AbstractC0850d abstractC0850d) {
        return new Long(this.f878a).compareTo(Long.valueOf(abstractC0850d.f878a));
    }

    /* JADX INFO: renamed from: a */
    protected long m926a() {
        return this.f878a;
    }

    /* JADX INFO: renamed from: a */
    protected void m927a(long j) {
        this.f878a = j;
    }

    /* JADX INFO: renamed from: b */
    public abstract void m928b();
}
