package com.google.firebase.iid;

import android.util.Base64;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.VisibleForTesting;
import java.security.KeyPair;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzy {
    private final KeyPair zzbw;
    private final long zzbx;

    @VisibleForTesting
    zzy(KeyPair keyPair, long j) {
        this.zzbw = keyPair;
        this.zzbx = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzv() {
        return Base64.encodeToString(this.zzbw.getPublic().getEncoded(), 11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzw() {
        return Base64.encodeToString(this.zzbw.getPrivate().getEncoded(), 11);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzy)) {
            return false;
        }
        zzy zzyVar = (zzy) obj;
        return this.zzbx == zzyVar.zzbx && this.zzbw.getPublic().equals(zzyVar.zzbw.getPublic()) && this.zzbw.getPrivate().equals(zzyVar.zzbw.getPrivate());
    }

    final long getCreationTime() {
        return this.zzbx;
    }

    final KeyPair getKeyPair() {
        return this.zzbw;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbw.getPublic(), this.zzbw.getPrivate(), Long.valueOf(this.zzbx));
    }
}
