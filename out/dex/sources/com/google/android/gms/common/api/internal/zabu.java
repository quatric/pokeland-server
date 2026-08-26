package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class zabu extends zal {
    private TaskCompletionSource<Void> zajo;

    private zabu(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zajo = new TaskCompletionSource<>();
        this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
    }

    public static zabu zac(Activity activity) {
        LifecycleFragment fragment = getFragment(activity);
        zabu zabuVar = (zabu) fragment.getCallbackOrNull("GmsAvailabilityHelper", zabu.class);
        if (zabuVar == null) {
            return new zabu(fragment);
        }
        if (zabuVar.zajo.getTask().isComplete()) {
            zabuVar.zajo = new TaskCompletionSource<>();
        }
        return zabuVar;
    }

    public final Task<Void> getTask() {
        return this.zajo.getTask();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onDestroy() {
        super.onDestroy();
        this.zajo.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    @Override // com.google.android.gms.common.api.internal.zal
    protected final void zaa(ConnectionResult connectionResult, int i) {
        this.zajo.setException(ApiExceptionUtil.fromStatus(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    @Override // com.google.android.gms.common.api.internal.zal
    protected final void zao() {
        int iIsGooglePlayServicesAvailable = this.zacc.isGooglePlayServicesAvailable(this.mLifecycleFragment.getLifecycleActivity());
        if (iIsGooglePlayServicesAvailable == 0) {
            this.zajo.setResult(null);
        } else {
            if (this.zajo.getTask().isComplete()) {
                return;
            }
            zab(new ConnectionResult(iIsGooglePlayServicesAvailable, null), 0);
        }
    }
}
