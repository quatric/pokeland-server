package com.nintendo.npf.sdk.internal.app;

import android.content.Intent;
import android.os.Bundle;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.a, reason: from Kotlin metadata */
/* JADX INFO: compiled from: ActivityStrategy.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0012\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\b\u0010\f\u001a\u00020\u0003H&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\bH&J\b\u0010\u000f\u001a\u00020\u0003H&J\u0012\u0010\u0010\u001a\u00020\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\u000bH&¨\u0006\u0012"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/ActivityStrategy;", "", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "onResume", "onSavedInstanceState", "outState", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
public interface ActivityStrategy {
    /* JADX INFO: renamed from: a */
    void mo1167a();

    /* JADX INFO: renamed from: a */
    void mo1168a(int i, int i2, @Nullable Intent intent);

    /* JADX INFO: renamed from: a */
    void mo1169a(@NotNull Intent intent);

    /* JADX INFO: renamed from: a */
    void mo1170a(@Nullable Bundle bundle);

    /* JADX INFO: renamed from: b */
    void mo1171b();

    /* JADX INFO: renamed from: b */
    void mo1172b(@Nullable Bundle bundle);
}
