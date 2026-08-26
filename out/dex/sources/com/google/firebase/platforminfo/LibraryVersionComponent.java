package com.google.firebase.platforminfo;

import com.google.firebase.components.Component;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class LibraryVersionComponent {
    private LibraryVersionComponent() {
    }

    public static Component<?> create(String str, String str2) {
        return Component.intoSet(LibraryVersion.create(str, str2), LibraryVersion.class);
    }
}
