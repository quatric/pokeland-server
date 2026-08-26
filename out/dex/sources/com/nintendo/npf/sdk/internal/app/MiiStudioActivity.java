package com.nintendo.npf.sdk.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: MiiStudioActivity.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0006H\u0014J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u0006H\u0016J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000eH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivity;", "Landroid/app/Activity;", "()V", "currentStrategy", "Lcom/nintendo/npf/sdk/internal/app/ActivityStrategy;", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "onResume", "onSaveInstanceState", "outState", "Companion", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
public final class MiiStudioActivity extends Activity {

    /* JADX INFO: renamed from: a */
    public static final C0891a f1131a = new C0891a(null);

    /* JADX INFO: renamed from: b */
    private ActivityStrategy f1132b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.MiiStudioActivity$a */
    /* JADX INFO: compiled from: MiiStudioActivity.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivity$Companion;", "", "()V", "NA_ID_TOKEN_KEY", "", "REQUEST_CODE_KEY", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
    public static final class C0891a {
        private C0891a() {
        }

        public /* synthetic */ C0891a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1168a(requestCode, resultCode, data);
    }

    @Override // android.app.Activity
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InvalidAccessedActivityStrategy invalidAccessedActivityStrategy;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        Bundle extras = intent.getExtras();
        if (extras == null || extras.getInt("requestCode") != 452) {
            invalidAccessedActivityStrategy = new InvalidAccessedActivityStrategy(this);
        } else {
            String string = extras.getString("naIdToken");
            if (string == null) {
                Intrinsics.throwNpe();
            }
            invalidAccessedActivityStrategy = new MiiStudioActivityStrategy(this, string);
        }
        this.f1132b = invalidAccessedActivityStrategy;
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1170a(savedInstanceState);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1171b();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onNewIntent(@NotNull Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "intent");
        super.onNewIntent(intent);
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1169a(intent);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1167a();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        Intrinsics.checkParameterIsNotNull(outState, "outState");
        super.onSaveInstanceState(outState);
        ActivityStrategy activityStrategy = this.f1132b;
        if (activityStrategy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentStrategy");
        }
        activityStrategy.mo1172b(outState);
    }
}
