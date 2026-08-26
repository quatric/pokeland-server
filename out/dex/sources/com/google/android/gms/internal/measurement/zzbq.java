package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzbq {

    public static final class zza extends zzey<zza, C1275zza> implements zzgk {
        private static volatile zzgr<zza> zzuo;
        private static final zza zzwa = new zza();
        private int zzue;
        private String zzvy = "";
        private String zzvz = "";

        /* JADX INFO: renamed from: com.google.android.gms.internal.measurement.zzbq$zza$zza, reason: collision with other inner class name */
        public static final class C1275zza extends zzey.zza<zza, C1275zza> implements zzgk {
            private C1275zza() {
                super(zza.zzwa);
            }

            /* synthetic */ C1275zza(zzbp zzbpVar) {
                this();
            }
        }

        static {
            zzey.zza((Class<zza>) zza.class, zzwa);
        }

        private zza() {
        }

        public static zzgr<zza> zzkj() {
            return (zzgr) zzwa.zza(zzey.zzd.zzaij, (Object) null, (Object) null);
        }

        public final String getKey() {
            return this.zzvy;
        }

        public final String getValue() {
            return this.zzvz;
        }

        @Override // com.google.android.gms.internal.measurement.zzey
        protected final Object zza(int i, Object obj, Object obj2) {
            zzbp zzbpVar = null;
            switch (zzbp.zzud[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C1275zza(zzbpVar);
                case 3:
                    return zza(zzwa, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001", new Object[]{"zzue", "zzvy", "zzvz"});
                case 4:
                    return zzwa;
                case 5:
                    zzgr<zza> zzcVar = zzuo;
                    if (zzcVar == null) {
                        synchronized (zza.class) {
                            zzcVar = zzuo;
                            if (zzcVar == null) {
                                zzcVar = new zzey.zzc<>(zzwa);
                                zzuo = zzcVar;
                            }
                            break;
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
