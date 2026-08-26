package com.google.firebase.platforminfo;

import com.google.auto.value.AutoValue;
import javax.annotation.Nonnull;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@AutoValue
abstract class LibraryVersion {
    LibraryVersion() {
    }

    static LibraryVersion create(String str, String str2) {
        return new AutoValue_LibraryVersion(str, str2);
    }

    @Nonnull
    public abstract String getLibraryName();

    @Nonnull
    public abstract String getVersion();
}
