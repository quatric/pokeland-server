package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzej {
    private static final Class<?> zzaeq = zztl();

    private static final zzel zzdu(String str) throws Exception {
        return (zzel) zzaeq.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }

    private static Class<?> zztl() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzel zztm() {
        if (zzaeq != null) {
            try {
                return zzdu("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return zzel.zzaev;
    }

    static zzel zztn() {
        zzel zzelVarZzdu;
        if (zzaeq != null) {
            try {
                zzelVarZzdu = zzdu("loadGeneratedRegistry");
            } catch (Exception unused) {
                zzelVarZzdu = null;
            }
        } else {
            zzelVarZzdu = null;
        }
        if (zzelVarZzdu == null) {
            zzelVarZzdu = zzel.zztn();
        }
        return zzelVarZzdu == null ? zztm() : zzelVarZzdu;
    }
}
