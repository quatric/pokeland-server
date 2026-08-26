package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzep {
    private static final zzen<?> zzafb = new zzem();
    private static final zzen<?> zzafc = zztt();

    private static zzen<?> zztt() {
        try {
            return (zzen) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zzen<?> zztu() {
        return zzafb;
    }

    static zzen<?> zztv() {
        zzen<?> zzenVar = zzafc;
        if (zzenVar != null) {
            return zzenVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
