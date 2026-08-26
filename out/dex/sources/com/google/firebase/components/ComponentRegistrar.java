package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.List;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public interface ComponentRegistrar {
    @KeepForSdk
    List<Component<?>> getComponents();
}
