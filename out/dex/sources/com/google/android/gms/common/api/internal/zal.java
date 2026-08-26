package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zal extends LifecycleCallback implements DialogInterface.OnCancelListener {
    protected volatile boolean mStarted;
    protected final GoogleApiAvailability zacc;
    protected final AtomicReference<zam> zade;
    private final Handler zadf;

    protected zal(LifecycleFragment lifecycleFragment) {
        this(lifecycleFragment, GoogleApiAvailability.getInstance());
    }

    @VisibleForTesting
    private zal(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zade = new AtomicReference<>(null);
        this.zadf = new com.google.android.gms.internal.base.zal(Looper.getMainLooper());
        this.zacc = googleApiAvailability;
    }

    private static int zaa(@Nullable zam zamVar) {
        if (zamVar == null) {
            return -1;
        }
        return zamVar.zar();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onActivityResult(int i, int i2, Intent intent) {
        zam zamVar = this.zade.get();
        boolean z = true;
        if (i != 1) {
            if (i != 2) {
                z = false;
            } else {
                int iIsGooglePlayServicesAvailable = this.zacc.isGooglePlayServicesAvailable(getActivity());
                z = iIsGooglePlayServicesAvailable == 0;
                if (zamVar == null) {
                    return;
                }
                if (zamVar.getConnectionResult().getErrorCode() == 18 && iIsGooglePlayServicesAvailable == 18) {
                    return;
                }
            }
        } else if (i2 != -1) {
            if (i2 == 0) {
                zam zamVar2 = new zam(new ConnectionResult(intent != null ? intent.getIntExtra("<<ResolutionFailureErrorDetail>>", 13) : 13, null), zaa(zamVar));
                this.zade.set(zamVar2);
                zamVar = zamVar2;
            }
            z = false;
        }
        if (z) {
            zaq();
        } else if (zamVar != null) {
            zaa(zamVar.getConnectionResult(), zamVar.zar());
        }
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        zaa(new ConnectionResult(13, null), zaa(this.zade.get()));
        zaq();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zade.set(bundle.getBoolean("resolving_error", false) ? new zam(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zam zamVar = this.zade.get();
        if (zamVar != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zamVar.zar());
            bundle.putInt("failed_status", zamVar.getConnectionResult().getErrorCode());
            bundle.putParcelable("failed_resolution", zamVar.getConnectionResult().getResolution());
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zaa(ConnectionResult connectionResult, int i);

    public final void zab(ConnectionResult connectionResult, int i) {
        zam zamVar = new zam(connectionResult, i);
        if (this.zade.compareAndSet(null, zamVar)) {
            this.zadf.post(new zan(this, zamVar));
        }
    }

    protected abstract void zao();

    protected final void zaq() {
        this.zade.set(null);
        zao();
    }
}
