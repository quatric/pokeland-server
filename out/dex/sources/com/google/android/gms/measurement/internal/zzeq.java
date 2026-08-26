package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzeq {
    private boolean value;
    private final String zzjj;
    private final boolean zzmh;
    private boolean zzmi;
    private final /* synthetic */ zzeo zzmj;

    public zzeq(zzeo zzeoVar, String str, boolean z) {
        this.zzmj = zzeoVar;
        Preconditions.checkNotEmpty(str);
        this.zzjj = str;
        this.zzmh = z;
    }

    @WorkerThread
    public final boolean get() {
        if (!this.zzmi) {
            this.zzmi = true;
            this.value = this.zzmj.zzhb().getBoolean(this.zzjj, this.zzmh);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(boolean z) {
        SharedPreferences.Editor editorEdit = this.zzmj.zzhb().edit();
        editorEdit.putBoolean(this.zzjj, z);
        editorEdit.apply();
        this.value = z;
    }
}
