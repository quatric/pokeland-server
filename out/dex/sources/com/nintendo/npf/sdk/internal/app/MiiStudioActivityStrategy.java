package com.nintendo.npf.sdk.internal.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.c, reason: from Kotlin metadata */
/* JADX INFO: compiled from: MiiStudioActivityStrategy.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 )2\u00020\u0001:\u0001)B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0005H\u0007J\"\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u00132\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0013H\u0016J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\u001cH\u0016J\b\u0010#\u001a\u00020\u0013H\u0016J\u0012\u0010$\u001a\u00020\u00132\b\u0010%\u001a\u0004\u0018\u00010\u001fH\u0016J\u0014\u0010&\u001a\u0004\u0018\u00010\u00152\b\u0010'\u001a\u0004\u0018\u00010(H\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivityStrategy;", "Lcom/nintendo/npf/sdk/internal/app/ActivityStrategy;", "activity", "Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivity;", "naIdToken", "", "(Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivity;Ljava/lang/String;)V", "backFromBrowser", "", "hasCallbacked", "launched", "locator", "Lcom/nintendo/npf/sdk/internal/ServiceLocator;", "kotlin.jvm.PlatformType", "getLocator", "()Lcom/nintendo/npf/sdk/internal/ServiceLocator;", "locator$delegate", "Lkotlin/Lazy;", "callbackOpenMiiStudio", "", "error", "Lcom/nintendo/npf/sdk/NPFError;", "makeUrl", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "onResume", "onSavedInstanceState", "outState", "parseUri", "uri", "Landroid/net/Uri;", "Companion", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
public final class MiiStudioActivityStrategy implements ActivityStrategy {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ KProperty[] f1155a = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(MiiStudioActivityStrategy.class), "locator", "getLocator()Lcom/nintendo/npf/sdk/internal/ServiceLocator;"))};

    /* JADX INFO: renamed from: b */
    public static final a f1156b = new a(null);

    /* JADX INFO: renamed from: i */
    private static final String f1157i = MiiStudioActivityStrategy.class.getSimpleName();

    /* JADX INFO: renamed from: c */
    private final Lazy f1158c;

    /* JADX INFO: renamed from: d */
    private boolean f1159d;

    /* JADX INFO: renamed from: e */
    private boolean f1160e;

    /* JADX INFO: renamed from: f */
    private boolean f1161f;

    /* JADX INFO: renamed from: g */
    private final MiiStudioActivity f1162g;

    /* JADX INFO: renamed from: h */
    private final String f1163h;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.c$a */
    /* JADX INFO: compiled from: MiiStudioActivityStrategy.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, m1984d2 = {"Lcom/nintendo/npf/sdk/internal/app/MiiStudioActivityStrategy$Companion;", "", "()V", "REQUEST_CODE_VALUE", "", "TAG", "", "kotlin.jvm.PlatformType", "NPFSDK_release"}, m1985k = 1, m1986mv = {1, 1, 16})
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.c$b */
    /* JADX INFO: compiled from: MiiStudioActivityStrategy.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, m1984d2 = {"<anonymous>", "Lcom/nintendo/npf/sdk/internal/ServiceLocator;", "kotlin.jvm.PlatformType", "invoke"}, m1985k = 3, m1986mv = {1, 1, 16})
    static final class b extends Lambda implements Function0<InterfaceC0875a> {

        /* JADX INFO: renamed from: a */
        public static final b f1164a = new b();

        b() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final InterfaceC0875a invoke() {
            return InterfaceC0875a.a.m1072b();
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.app.c$c */
    /* JADX INFO: compiled from: MiiStudioActivityStrategy.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, m1984d2 = {"<anonymous>", "", "<name for destructuring parameter 0>", "Lkotlin/Pair;", "invoke"}, m1985k = 3, m1986mv = {1, 1, 16})
    static final class c extends Lambda implements Function1<Pair<? extends String, ? extends String>, String> {

        /* JADX INFO: renamed from: a */
        public static final c f1165a = new c();

        c() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final String invoke(@NotNull Pair<String, String> pair) {
            Intrinsics.checkParameterIsNotNull(pair, "<name for destructuring parameter 0>");
            return pair.component1() + '=' + URLEncoder.encode(pair.component2(), "UTF-8");
        }
    }

    public MiiStudioActivityStrategy(@NotNull MiiStudioActivity activity, @NotNull String naIdToken) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(naIdToken, "naIdToken");
        this.f1162g = activity;
        this.f1163h = naIdToken;
        this.f1158c = LazyKt.lazy(b.f1164a);
    }

    /* JADX INFO: renamed from: a */
    private final void m1173a(NPFError nPFError) {
        this.f1161f = true;
        InterfaceC0875a locator = m1174d();
        Intrinsics.checkExpressionValueIsNotNull(locator, "locator");
        locator.mo1051e().m1724c(nPFError);
    }

    /* JADX INFO: renamed from: d */
    private final InterfaceC0875a m1174d() {
        Lazy lazy = this.f1158c;
        KProperty kProperty = f1155a[0];
        return (InterfaceC0875a) lazy.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00b3  */
    @VisibleForTesting
    @Nullable
    /* JADX INFO: renamed from: a */
    public final NPFError m1175a(@Nullable Uri uri) {
        String str;
        String query;
        List listSplit$default;
        String str2 = (String) null;
        if (uri == null || (query = uri.getQuery()) == null) {
            str = str2;
        } else {
            if (!(!StringsKt.isBlank(query))) {
                query = null;
            }
            if (query == null || (listSplit$default = StringsKt.split$default((CharSequence) query, new String[]{"&"}, false, 0, 6, (Object) null)) == null) {
                str = str2;
            } else {
                List list = listSplit$default;
                ArrayList<Pair> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    List listSplit$default2 = StringsKt.split$default((CharSequence) it.next(), new String[]{"="}, false, 0, 6, (Object) null);
                    arrayList.add(new Pair(URLDecoder.decode((String) listSplit$default2.get(0), "UTF-8"), URLDecoder.decode((String) listSplit$default2.get(1), "UTF-8")));
                }
                str = str2;
                for (Pair pair : arrayList) {
                    String str3 = (String) pair.component1();
                    String str4 = (String) pair.component2();
                    if (Intrinsics.areEqual(str3, "error")) {
                        str2 = str4;
                    } else if (Intrinsics.areEqual(str3, "error_description")) {
                        str = str4;
                    }
                }
            }
        }
        if (str2 == null) {
            return null;
        }
        if ((StringsKt.isBlank(str2) ^ true ? str2 : null) != null) {
            return Intrinsics.areEqual(str2, "user_canceled") ? new C1025o(NPFError.ErrorType.USER_CANCEL, -1, str) : new C1025o(NPFError.ErrorType.NPF_ERROR, 4900, str);
        }
        return null;
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1167a() {
        C0955e.m1393b(f1157i, "onResume");
        if (!this.f1159d && !this.f1160e) {
            this.f1159d = true;
        } else {
            if (this.f1162g.isFinishing()) {
                return;
            }
            this.f1162g.finish();
        }
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1168a(int i, int i2, @Nullable Intent intent) {
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1169a(@NotNull Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "intent");
        C0955e.m1393b(f1157i, "onNewIntent");
        if (this.f1161f) {
            return;
        }
        NPFError nPFErrorM1175a = m1175a(intent.getData());
        if (nPFErrorM1175a != null) {
            if (nPFErrorM1175a.getErrorType() == NPFError.ErrorType.USER_CANCEL) {
                C0954d.m1389b("mii_studio_error", "MiiStudio#UserCanceledOnBrowser", nPFErrorM1175a);
            } else {
                C0954d.m1389b("mii_studio_error", "MiiStudio#OtherError", nPFErrorM1175a);
            }
        }
        m1173a(nPFErrorM1175a);
        this.f1162g.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: a */
    public void mo1170a(@Nullable Bundle bundle) {
        this.f1162g.requestWindowFeature(1);
        if (bundle != null) {
            this.f1160e = true;
            return;
        }
        String strM1176c = m1176c();
        C0955e.m1391a(f1157i, "url : " + strM1176c);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strM1176c));
        if (this.f1162g.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            this.f1162g.startActivity(intent);
            return;
        }
        NPFError nPFErrorM1659b = C1025o.m1659b();
        C0954d.m1389b("mii_studio_error", "MiiStudio#BrowserNotAvailable", nPFErrorM1659b);
        m1173a(nPFErrorM1659b);
        this.f1162g.finish();
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1171b() {
        C0955e.m1393b(f1157i, "onDestroy");
        if (this.f1161f) {
            return;
        }
        C1025o c1025o = new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for openMiiStudio.");
        C0954d.m1389b("mii_studio_error", "MiiStudio#BackFromBrowser", c1025o);
        m1173a(c1025o);
    }

    @Override // com.nintendo.npf.sdk.internal.app.ActivityStrategy
    /* JADX INFO: renamed from: b */
    public void mo1172b(@Nullable Bundle bundle) {
    }

    @VisibleForTesting
    @NotNull
    /* JADX INFO: renamed from: c */
    public final String m1176c() {
        InterfaceC0875a locator = m1174d();
        Intrinsics.checkExpressionValueIsNotNull(locator, "locator");
        C0947b capabilities = locator.mo1065s();
        Intrinsics.checkExpressionValueIsNotNull(capabilities, "capabilities");
        String str = capabilities.m1315F() ? "http" : "https";
        String strM1329d = capabilities.m1329d();
        try {
            return str + "://" + capabilities.m1330e() + "/mii_studio?" + CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Pair[]{new Pair("redirect_uri", "npf" + strM1329d + "://mii_studio"), new Pair("client_id", strM1329d), new Pair("lang", capabilities.m1347v()), new Pair("id_token_hint", this.f1163h)}), "&", null, null, 0, null, c.f1165a, 30, null);
        } catch (UnsupportedEncodingException unused) {
            throw new IllegalArgumentException("clientId=" + strM1329d);
        }
    }
}
