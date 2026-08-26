package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzet {
    private long value;
    private final String zzjj;
    private boolean zzmi;
    private final /* synthetic */ zzeo zzmj;
    private final long zzmo;

    public zzet(zzeo zzeoVar, String str, long j) {
        this.zzmj = zzeoVar;
        Preconditions.checkNotEmpty(str);
        this.zzjj = str;
        this.zzmo = j;
    }

    @WorkerThread
    public final long get() {
        if (!this.zzmi) {
            this.zzmi = true;
            this.value = this.zzmj.zzhb().getLong(this.zzjj, this.zzmo);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(long j) {
        SharedPreferences.Editor editorEdit = this.zzmj.zzhb().edit();
        editorEdit.putLong(this.zzjj, j);
        editorEdit.apply();
        this.value = j;
    }
}
