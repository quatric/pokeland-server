package com.google.android.gms.internal.measurement;

import android.support.v4.internal.view.SupportMenu;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class zzel {
    private static volatile boolean zzaer = false;
    private static volatile zzel zzaet;
    private static volatile zzel zzaeu;
    private final Map<zza, zzey.zze<?, ?>> zzaew;
    private static final Class<?> zzaes = zzto();
    static final zzel zzaev = new zzel(true);

    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.object == zzaVar.object && this.number == zzaVar.number;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * SupportMenu.USER_MASK) + this.number;
        }
    }

    zzel() {
        this.zzaew = new HashMap();
    }

    private zzel(boolean z) {
        this.zzaew = Collections.emptyMap();
    }

    static zzel zztn() {
        return zzex.zzc(zzel.class);
    }

    private static Class<?> zzto() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzel zztp() {
        zzel zzelVarZztm = zzaet;
        if (zzelVarZztm == null) {
            synchronized (zzel.class) {
                zzelVarZztm = zzaet;
                if (zzelVarZztm == null) {
                    zzelVarZztm = zzej.zztm();
                    zzaet = zzelVarZztm;
                }
            }
        }
        return zzelVarZztm;
    }

    public static zzel zztq() {
        zzel zzelVarZztn = zzaeu;
        if (zzelVarZztn == null) {
            synchronized (zzel.class) {
                zzelVarZztn = zzaeu;
                if (zzelVarZztn == null) {
                    zzelVarZztn = zzej.zztn();
                    zzaeu = zzelVarZztn;
                }
            }
        }
        return zzelVarZztn;
    }

    public final <ContainingType extends zzgi> zzey.zze<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzey.zze) this.zzaew.get(new zza(containingtype, i));
    }
}
