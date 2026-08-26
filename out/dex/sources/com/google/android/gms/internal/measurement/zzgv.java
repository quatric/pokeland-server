package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgv implements zzgg {
    private final int flags;
    private final String info;
    private final Object[] zzakk;
    private final zzgi zzakn;

    zzgv(zzgi zzgiVar, String str, Object[] objArr) {
        this.zzakn = zzgiVar;
        this.info = str;
        this.zzakk = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.flags = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 < 55296) {
                this.flags = i | (cCharAt2 << i2);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzgg
    public final int zzvr() {
        return (this.flags & 1) == 1 ? zzey.zzd.zzail : zzey.zzd.zzaim;
    }

    @Override // com.google.android.gms.internal.measurement.zzgg
    public final boolean zzvs() {
        return (this.flags & 2) == 2;
    }

    @Override // com.google.android.gms.internal.measurement.zzgg
    public final zzgi zzvt() {
        return this.zzakn;
    }

    final String zzvz() {
        return this.info;
    }

    final Object[] zzwa() {
        return this.zzakk;
    }
}
