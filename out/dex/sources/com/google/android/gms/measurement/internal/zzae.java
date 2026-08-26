package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzae {
    final String name;
    final String zzce;
    final long zzfg;
    final long zzfh;
    final long zzfi;
    final long zzfj;
    final long zzfk;
    final Long zzfl;
    final Long zzfm;
    final Long zzfn;
    final Boolean zzfo;

    zzae(String str, String str2, long j, long j2, long j3, long j4, long j5, Long l, Long l2, Long l3, Boolean bool) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkArgument(j >= 0);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        this.zzce = str;
        this.name = str2;
        this.zzfg = j;
        this.zzfh = j2;
        this.zzfi = j3;
        this.zzfj = j4;
        this.zzfk = j5;
        this.zzfl = l;
        this.zzfm = l2;
        this.zzfn = l3;
        this.zzfo = bool;
    }

    zzae(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Long l3, Boolean bool) {
        this(str, str2, j, j2, 0L, j3, 0L, null, null, null, null);
    }

    final zzae zza(long j, long j2) {
        return new zzae(this.zzce, this.name, this.zzfg, this.zzfh, this.zzfi, this.zzfj, j, Long.valueOf(j2), this.zzfm, this.zzfn, this.zzfo);
    }

    final zzae zza(Long l, Long l2, Boolean bool) {
        return new zzae(this.zzce, this.name, this.zzfg, this.zzfh, this.zzfi, this.zzfj, this.zzfk, this.zzfl, l, l2, (bool == null || bool.booleanValue()) ? bool : null);
    }

    final zzae zzw(long j) {
        return new zzae(this.zzce, this.name, this.zzfg, this.zzfh, this.zzfi, j, this.zzfk, this.zzfl, this.zzfm, this.zzfn, this.zzfo);
    }
}
