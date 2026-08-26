package com.google.android.gms.auth.api.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface GoogleSignInOptionsExtension {

    @KeepForSdk
    public static final int FITNESS = 3;

    @KeepForSdk
    public static final int GAMES = 1;

    @KeepForSdk
    int getExtensionType();

    @KeepForSdk
    @Nullable
    List<Scope> getImpliedScopes();

    Bundle toBundle();
}
