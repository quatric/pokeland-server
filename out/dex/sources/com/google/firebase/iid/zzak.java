package com.google.firebase.iid;

import android.os.Bundle;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzak extends zzaj<Void> {
    zzak(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    @Override // com.google.firebase.iid.zzaj
    final boolean zzab() {
        return true;
    }

    @Override // com.google.firebase.iid.zzaj
    final void zzb(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            finish(null);
        } else {
            zza(new zzam(4, "Invalid response to one way request"));
        }
    }
}
