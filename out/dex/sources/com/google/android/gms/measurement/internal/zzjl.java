package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzjl implements zzel {
    private final /* synthetic */ zzjg zztl;

    zzjl(zzjg zzjgVar) {
        this.zztl = zzjgVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzel
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zztl.zzb(str, i, th, bArr, map);
    }
}
