package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzev {
    private String value;
    private final String zzjj;
    private boolean zzmi;
    private final /* synthetic */ zzeo zzmj;
    private final String zzmp;

    public zzev(zzeo zzeoVar, String str, String str2) {
        this.zzmj = zzeoVar;
        Preconditions.checkNotEmpty(str);
        this.zzjj = str;
        this.zzmp = null;
    }

    @WorkerThread
    public final void zzau(String str) {
        if (zzjs.zzs(str, this.value)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.zzmj.zzhb().edit();
        editorEdit.putString(this.zzjj, str);
        editorEdit.apply();
        this.value = str;
    }

    @WorkerThread
    public final String zzho() {
        if (!this.zzmi) {
            this.zzmi = true;
            this.value = this.zzmj.zzhb().getString(this.zzjj, null);
        }
        return this.value;
    }
}
