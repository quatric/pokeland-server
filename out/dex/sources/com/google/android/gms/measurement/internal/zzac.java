package com.google.android.gms.measurement.internal;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.WorkerThread;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.common.util.Clock;
import com.metaps.common.C0854h;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzac extends zzge {
    private long zzey;
    private String zzez;
    private Boolean zzfa;
    private AccountManager zzfb;
    private Boolean zzfc;
    private long zzfd;

    zzac(zzfj zzfjVar) {
        super(zzfjVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzfc zzaa() {
        return super.zzaa();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzef zzab() {
        return super.zzab();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzeo zzac() {
        return super.zzac();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzs zzad() {
        return super.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ zzr zzae() {
        return super.zzae();
    }

    @Override // com.google.android.gms.measurement.internal.zzge
    protected final boolean zzbk() {
        Calendar calendar = Calendar.getInstance();
        this.zzey = TimeUnit.MINUTES.convert(calendar.get(15) + calendar.get(16), TimeUnit.MILLISECONDS);
        Locale locale = Locale.getDefault();
        String lowerCase = locale.getLanguage().toLowerCase(Locale.ENGLISH);
        String lowerCase2 = locale.getCountry().toLowerCase(Locale.ENGLISH);
        StringBuilder sb = new StringBuilder(String.valueOf(lowerCase).length() + 1 + String.valueOf(lowerCase2).length());
        sb.append(lowerCase);
        sb.append("-");
        sb.append(lowerCase2);
        this.zzez = sb.toString();
        return false;
    }

    public final long zzcq() {
        zzbi();
        return this.zzey;
    }

    public final String zzcr() {
        zzbi();
        return this.zzez;
    }

    @WorkerThread
    final long zzcs() {
        zzo();
        return this.zzfd;
    }

    @WorkerThread
    final void zzct() {
        zzo();
        this.zzfc = null;
        this.zzfd = 0L;
    }

    @WorkerThread
    final boolean zzcu() {
        zzo();
        long jCurrentTimeMillis = zzx().currentTimeMillis();
        if (jCurrentTimeMillis - this.zzfd > C0854h.f927i) {
            this.zzfc = null;
        }
        Boolean bool = this.zzfc;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.GET_ACCOUNTS") != 0) {
            zzab().zzgo().zzao("Permission error checking for dasher/unicorn accounts");
            this.zzfd = jCurrentTimeMillis;
            this.zzfc = false;
            return false;
        }
        if (this.zzfb == null) {
            this.zzfb = AccountManager.get(getContext());
        }
        try {
            Account[] result = this.zzfb.getAccountsByTypeAndFeatures("com.google", new String[]{"service_HOSTED"}, null, null).getResult();
            if (result != null && result.length > 0) {
                this.zzfc = true;
                this.zzfd = jCurrentTimeMillis;
                return true;
            }
            Account[] result2 = this.zzfb.getAccountsByTypeAndFeatures("com.google", new String[]{"service_uca"}, null, null).getResult();
            if (result2 != null && result2.length > 0) {
                this.zzfc = true;
                this.zzfd = jCurrentTimeMillis;
                return true;
            }
            this.zzfd = jCurrentTimeMillis;
            this.zzfc = false;
            return false;
        } catch (AuthenticatorException | OperationCanceledException | IOException e) {
            zzab().zzgl().zza("Exception checking account types", e);
        }
    }

    public final boolean zzj(Context context) {
        if (this.zzfa == null) {
            zzae();
            this.zzfa = false;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    packageManager.getPackageInfo("com.google.android.gms", 128);
                    this.zzfa = true;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return this.zzfa.booleanValue();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzl() {
        super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzm() {
        super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzn() {
        super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ void zzo() {
        super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzac zzw() {
        return super.zzw();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf, com.google.android.gms.measurement.internal.zzgh
    public final /* bridge */ /* synthetic */ Clock zzx() {
        return super.zzx();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzed zzy() {
        return super.zzy();
    }

    @Override // com.google.android.gms.measurement.internal.zzgf
    public final /* bridge */ /* synthetic */ zzjs zzz() {
        return super.zzz();
    }
}
