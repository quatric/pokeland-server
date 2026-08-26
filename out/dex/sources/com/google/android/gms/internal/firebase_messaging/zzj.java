package com.google.android.gms.internal.firebase_messaging;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzj {
    private static final OutputStream zzg = new zzi();

    public static InputStream zza(InputStream inputStream, long j) {
        return new zzl(inputStream, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
    }
}
