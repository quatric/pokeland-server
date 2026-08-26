package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.common.C0271R;
import com.google.android.gms.common.annotation.KeepForSdk;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class StringResourceValueReader {
    private final Resources zzeu;
    private final String zzev;

    public StringResourceValueReader(Context context) {
        Preconditions.checkNotNull(context);
        this.zzeu = context.getResources();
        this.zzev = this.zzeu.getResourcePackageName(C0271R.string.common_google_play_services_unknown_issue);
    }

    @KeepForSdk
    @Nullable
    public String getString(String str) {
        int identifier = this.zzeu.getIdentifier(str, "string", this.zzev);
        if (identifier == 0) {
            return null;
        }
        return this.zzeu.getString(identifier);
    }
}
