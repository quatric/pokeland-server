package com.google.firebase;

import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public interface FirebaseAppLifecycleListener {
    void onDeleted(String str, FirebaseOptions firebaseOptions);
}
