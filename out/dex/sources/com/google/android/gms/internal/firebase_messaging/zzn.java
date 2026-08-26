package com.google.android.gms.internal.firebase_messaging;

import java.io.PrintStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzn {
    private static final zzm zzk;
    private static final int zzl;

    static final class zza extends zzm {
        zza() {
        }

        @Override // com.google.android.gms.internal.firebase_messaging.zzm
        public final void zza(Throwable th, Throwable th2) {
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001e A[Catch: Throwable -> 0x002a, TryCatch #0 {Throwable -> 0x002a, blocks: (B:5:0x0007, B:7:0x000f, B:8:0x0015, B:10:0x001e, B:11:0x0024), top: B:25:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:11:0x0024 A[Catch: Throwable -> 0x002a, TRY_LEAVE, TryCatch #0 {Throwable -> 0x002a, blocks: (B:5:0x0007, B:7:0x000f, B:8:0x0015, B:10:0x001e, B:11:0x0024), top: B:25:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:8:0x0015 A[Catch: Throwable -> 0x002a, TryCatch #0 {Throwable -> 0x002a, blocks: (B:5:0x0007, B:7:0x000f, B:8:0x0015, B:10:0x001e, B:11:0x0024), top: B:25:0x0007 }] */
    static {
        Integer numZzb;
        zzm zzaVar;
        try {
            numZzb = zzb();
            if (numZzb != null) {
                try {
                    if (numZzb.intValue() >= 19) {
                        zzaVar = new zzr();
                    } else if (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic")) {
                        zzaVar = new zzq();
                    } else {
                        zzaVar = new zza();
                    }
                } catch (Throwable th) {
                    th = th;
                    PrintStream printStream = System.err;
                    String name = zza.class.getName();
                    StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 133);
                    sb.append("An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy ");
                    sb.append(name);
                    sb.append("will be used. The error is: ");
                    printStream.println(sb.toString());
                    th.printStackTrace(System.err);
                    zzaVar = new zza();
                }
            } else if (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic")) {
                zzaVar = new zzq();
            } else {
                zzaVar = new zza();
            }
        } catch (Throwable th2) {
            th = th2;
            numZzb = null;
        }
        zzk = zzaVar;
        zzl = numZzb != null ? numZzb.intValue() : 1;
    }

    public static void zza(Throwable th, Throwable th2) {
        zzk.zza(th, th2);
    }

    private static Integer zzb() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
