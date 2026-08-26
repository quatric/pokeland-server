package com.google.android.gms.common;

import android.content.Intent;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int zzag;

    public GooglePlayServicesRepairableException(int i, String str, Intent intent) {
        super(str, intent);
        this.zzag = i;
    }

    public int getConnectionStatusCode() {
        return this.zzag;
    }
}
