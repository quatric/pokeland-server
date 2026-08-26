package com.nintendo.npf.sdk.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.b, reason: from Kotlin metadata */
/* JADX INFO: compiled from: InvalidAccessedActivityStrategy.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u0006H\u0016J\u0012\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/InvalidAccessedActivityStrategy;", "Lcom/nintendo/npf/sdk/internal/app/ActivityStrategy;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "onResume", "onSavedInstanceState", "outState", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
public final class InvalidAccessedActivityStrategy implements ActivityStrategy {

    /* JADX INFO: renamed from: a */
    private final Activity f1154a;

    public InvalidAccessedActivityStrategy(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        this.f1154a = activity;
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1167a() {
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1168a(int i, int i2, @Nullable Intent intent) {
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1169a(@NotNull Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "intent");
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1170a(@Nullable Bundle bundle) {
        this.f1154a.startActivity(this.f1154a.getPackageManager().getLaunchIntentForPackage(this.f1154a.getPackageName()));
        this.f1154a.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1171b() {
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1172b(@Nullable Bundle bundle) {
    }
}
