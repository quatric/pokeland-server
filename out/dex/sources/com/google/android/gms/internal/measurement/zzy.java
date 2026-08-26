package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.util.ArrayList;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzy extends zzz.zzb {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzx;
    private final /* synthetic */ String zzy;
    private final /* synthetic */ Bundle zzz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzy(zzz zzzVar, String str, String str2, Context context, Bundle bundle) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzx = str;
        this.zzy = str2;
        this.val$context = context;
        this.zzz = bundle;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0076 A[PHI: r2
      0x0076: PHI (r2v12 int) = (r2v10 int), (r2v17 int) binds: [B:27:0x007e, B:20:0x0072] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    public final void zzf() {
        String str;
        String str2;
        String str3;
        int iMax;
        boolean z;
        try {
            this.zzaa.zzaf = new ArrayList();
            zzz zzzVar = this.zzaa;
            if (zzz.zza(this.zzx, this.zzy)) {
                String str4 = this.zzy;
                str2 = this.zzx;
                str3 = str4;
                str = this.zzaa.zzu;
            } else {
                str = null;
                str2 = null;
                str3 = null;
            }
            zzz.zze(this.val$context);
            boolean z2 = zzz.zzai.booleanValue() || str2 != null;
            this.zzaa.zzar = this.zzaa.zza(this.val$context, z2);
            if (this.zzaa.zzar == null) {
                Log.w(this.zzaa.zzu, "Failed to connect to measurement client.");
                return;
            }
            int iZzd = zzz.zzd(this.val$context);
            int iZzc = zzz.zzc(this.val$context);
            if (z2) {
                iMax = Math.max(iZzd, iZzc);
                if (iZzc < iZzd) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                iMax = iZzd > 0 ? iZzd : iZzc;
                if (iZzd > 0) {
                    z = true;
                } else {
                    z = false;
                }
            }
            this.zzaa.zzar.initialize(ObjectWrapper.wrap(this.val$context), new zzx(16250L, iMax, z, str, str2, str3, this.zzz), this.timestamp);
        } catch (RemoteException e) {
            this.zzaa.zza((Exception) e, true, false);
        }
    }
}
