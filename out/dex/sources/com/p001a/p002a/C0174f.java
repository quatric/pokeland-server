package com.p001a.p002a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.AnyThread;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Date;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.a.a.f */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0174f {

    /* JADX INFO: renamed from: a */
    public static final int f30a = 0;

    /* JADX INFO: renamed from: b */
    public static final int f31b = 1;

    /* JADX INFO: renamed from: c */
    public static final int f32c = 2;

    /* JADX INFO: renamed from: d */
    public static final int f33d = 3;

    /* JADX INFO: renamed from: e */
    public static final int f34e = 4;

    /* JADX INFO: renamed from: f */
    public static final int f35f = 5;

    /* JADX INFO: renamed from: g */
    public static final int f36g = 1;

    /* JADX INFO: renamed from: h */
    public static final int f37h = 2;

    /* JADX INFO: renamed from: i */
    public static final int f38i = 3;

    /* JADX INFO: renamed from: j */
    public static final int f39j = 4;

    /* JADX INFO: renamed from: k */
    public static final int f40k = 5;

    /* JADX INFO: renamed from: l */
    public static final int f41l = 6;

    /* JADX INFO: renamed from: m */
    public static final int f42m = 7;

    /* JADX INFO: renamed from: n */
    public static final int f43n = 8;

    /* JADX INFO: renamed from: o */
    public static final int f44o = 9;

    /* JADX INFO: renamed from: p */
    public static final int f45p = 10;

    /* JADX INFO: renamed from: q */
    public static final int f46q = 11;

    /* JADX INFO: renamed from: r */
    public static final int f47r = 12;

    /* JADX INFO: renamed from: s */
    public static final int f48s = 13;

    /* JADX INFO: renamed from: t */
    public static final int f49t = 14;

    /* JADX INFO: renamed from: u */
    @NonNull
    private static final C0174f f50u = new C0174f();

    /* JADX INFO: renamed from: v */
    @Nullable
    private String f52v = null;

    /* JADX INFO: renamed from: w */
    @Nullable
    private String f53w = null;

    /* JADX INFO: renamed from: x */
    private transient int f54x = 5;

    /* JADX INFO: renamed from: y */
    @Nullable
    private transient InterfaceC0172d f55y = null;

    /* JADX INFO: renamed from: z */
    private transient int f56z = 0;

    /* JADX INFO: renamed from: A */
    @Nullable
    private RunnableC0175g f51A = null;

    /* JADX INFO: renamed from: com.a.a.f$a */
    @AnyThread
    public static class a {

        /* JADX INFO: renamed from: a */
        @NonNull
        private final Context f57a;

        /* JADX INFO: renamed from: b */
        @Nullable
        private String f58b = null;

        /* JADX INFO: renamed from: c */
        @Nullable
        private String f59c = null;

        /* JADX INFO: renamed from: d */
        @Nullable
        private Integer f60d = null;

        /* JADX INFO: renamed from: e */
        @Nullable
        private InterfaceC0169a f61e = null;

        /* JADX INFO: renamed from: f */
        @Nullable
        private InterfaceC0170b f62f = null;

        /* JADX INFO: renamed from: g */
        @Nullable
        private Boolean f63g = null;

        /* JADX INFO: renamed from: h */
        @Nullable
        private c f64h = null;

        /* JADX INFO: renamed from: i */
        @Nullable
        private JSONObject f65i = null;

        public a(@NonNull Context context) {
            this.f57a = context;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m44a(int i) {
            this.f60d = Integer.valueOf(i);
            return this;
        }

        @CheckResult
        @NonNull
        @Deprecated
        /* JADX INFO: renamed from: a */
        public final a m45a(@NonNull InterfaceC0169a interfaceC0169a) {
            this.f61e = interfaceC0169a;
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m46a(@NonNull InterfaceC0170b interfaceC0170b) {
            this.f62f = interfaceC0170b;
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m47a(@NonNull c cVar) {
            this.f64h = cVar;
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m48a(@Size(min = 1) @NonNull String str) {
            this.f58b = str;
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m49a(@Size(min = 1) @NonNull String str, @Size(min = 1) @NonNull String str2) {
            if (this.f65i == null) {
                this.f65i = new JSONObject();
            }
            C0178j.m209a(str, str2, this.f65i);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m50a(@NonNull JSONObject jSONObject) {
            if (jSONObject != null && jSONObject.length() > 0) {
                if (this.f65i == null) {
                    this.f65i = new JSONObject();
                }
                C0178j.m222b(this.f65i, jSONObject);
            }
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final a m51a(boolean z) {
            this.f63g = Boolean.valueOf(z);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final a m52b(@Size(min = 1) @NonNull String str) {
            this.f59c = str;
            return this;
        }
    }

    /* JADX INFO: renamed from: com.a.a.f$b */
    @AnyThread
    public static class b implements Parcelable {

        /* JADX INFO: renamed from: a */
        @NonNull
        public static final Parcelable.Creator<b> f66a = new Parcelable.Creator<b>() { // from class: com.a.a.f.b.1
            @Override // android.os.Parcelable.Creator
            @NonNull
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public final b createFromParcel(@NonNull Parcel parcel) {
                return new b(parcel);
            }

            @Override // android.os.Parcelable.Creator
            @Contract(pure = true)
            @NonNull
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public final b[] newArray(int i) {
                return new b[i];
            }
        };

        /* JADX INFO: renamed from: b */
        @NonNull
        final JSONObject f67b;

        /* JADX INFO: renamed from: c */
        @NonNull
        final String f68c;

        /* JADX INFO: renamed from: d */
        long f69d;

        /* JADX INFO: renamed from: e */
        boolean f70e;

        /* JADX INFO: renamed from: f */
        @Nullable
        String f71f;

        /* JADX INFO: renamed from: g */
        @Nullable
        String f72g;

        public b(int i) {
            String str;
            this.f67b = new JSONObject();
            this.f69d = -1L;
            this.f70e = false;
            this.f71f = null;
            this.f72g = null;
            switch (i) {
                case 1:
                    str = "Achievement";
                    break;
                case 2:
                    str = "Add to Cart";
                    break;
                case 3:
                    str = "Add to Wish List";
                    break;
                case 4:
                    str = "Checkout Start";
                    break;
                case 5:
                    str = "Level Complete";
                    break;
                case 6:
                    str = "Purchase";
                    break;
                case 7:
                    str = "Rating";
                    break;
                case 8:
                    str = "Registration Complete";
                    break;
                case 9:
                    str = "Search";
                    break;
                case 10:
                    str = "Tutorial Complete";
                    break;
                case 11:
                    str = "View";
                    break;
                case 12:
                    str = "Ad View";
                    break;
                case 13:
                    str = "Push Received";
                    break;
                case 14:
                    str = "Push Opened";
                    break;
                default:
                    str = "";
                    break;
            }
            this.f68c = str;
            this.f69d = C0178j.m202a();
        }

        protected b(@NonNull Parcel parcel) {
            this.f67b = new JSONObject();
            this.f69d = -1L;
            this.f70e = false;
            JSONObject jSONObject = null;
            this.f71f = null;
            this.f72g = null;
            try {
                jSONObject = new JSONObject(parcel.readString());
            } catch (JSONException e) {
                C0174f.m16a(2, "EVT", "Event", e);
            }
            if (jSONObject != null) {
                C0178j.m222b(this.f67b, jSONObject);
            }
            this.f68c = parcel.readString();
            this.f69d = parcel.readLong();
            this.f70e = parcel.readByte() != 0;
            this.f71f = parcel.readString();
            this.f72g = parcel.readString();
        }

        public b(@Size(min = 1) @NonNull String str) {
            this.f67b = new JSONObject();
            this.f69d = -1L;
            this.f70e = false;
            this.f71f = null;
            this.f72g = null;
            this.f68c = str == null ? "" : str;
            this.f69d = C0178j.m202a();
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: A */
        public final b m53A(@NonNull String str) {
            C0178j.m209a("results", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: B */
        public final b m54B(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.SCORE, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: C */
        public final b m55C(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.SEARCH_TERM, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: D */
        public final b m56D(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.START_DATE, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: E */
        public final b m57E(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.SUCCESS, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: F */
        public final b m58F(@NonNull String str) {
            C0178j.m209a("user_id", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: G */
        public final b m59G(@NonNull String str) {
            C0178j.m209a("user_name", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: H */
        public final b m60H(@NonNull String str) {
            C0178j.m209a("validated", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: I */
        public final b m61I(@NonNull String str) {
            C0178j.m209a("action", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        final b m62a() {
            this.f70e = true;
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m63a(double d) {
            C0178j.m209a("duration", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m64a(@NonNull Bundle bundle) {
            C0178j.m209a("payload", C0178j.m231e(bundle), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m65a(@NonNull String str) {
            C0178j.m209a("device_type", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m66a(@Size(min = 1) @NonNull String str, double d) {
            C0178j.m209a(str, Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m67a(@Size(min = 1) @NonNull String str, long j) {
            C0178j.m209a(str, Long.valueOf(j), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m68a(@Size(min = 1) @NonNull String str, @Size(min = 1) @NonNull String str2) {
            if (str == null || str2 == null || str.trim().isEmpty() || str2.trim().isEmpty()) {
                C0174f.m16a(2, "EVT", "setGooglePlay", "Invalid Input");
            } else {
                this.f71f = str;
                this.f72g = str2;
            }
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m69a(@Size(min = 1) @NonNull String str, @NonNull Date date) {
            C0178j.m209a(str, date, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m70a(@Size(min = 1) @NonNull String str, boolean z) {
            C0178j.m209a(str, Boolean.valueOf(z), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m71a(@NonNull Date date) {
            C0178j.m209a("date", date, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m72a(@NonNull JSONObject jSONObject) {
            C0178j.m209a("payload", jSONObject, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final b m73a(boolean z) {
            C0178j.m209a("background", Boolean.valueOf(z), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m74b(double d) {
            C0178j.m209a("max_rating_value", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m75b(@NonNull String str) {
            C0178j.m209a("placement", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m76b(@Size(min = 1) @NonNull String str, @NonNull String str2) {
            C0178j.m209a(str, str2, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m77b(@NonNull Date date) {
            C0178j.m209a(FirebaseAnalytics.Param.END_DATE, date, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m78b(@Size(min = 1) @NonNull JSONObject jSONObject) {
            if (jSONObject == null || jSONObject.length() < 1) {
                C0174f.m16a(2, "EVT", "addCustom", "Invalid keyValue object");
                return this;
            }
            C0178j.m222b(this.f67b, jSONObject);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: b */
        public final b m79b(boolean z) {
            C0178j.m209a("completed", Boolean.valueOf(z), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: c */
        public final b m80c(double d) {
            C0178j.m209a(FirebaseAnalytics.Param.PRICE, Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: c */
        public final b m81c(@NonNull String str) {
            C0178j.m209a("ad_type", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: c */
        public final b m82c(@NonNull Date date) {
            C0178j.m209a(FirebaseAnalytics.Param.START_DATE, date, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: d */
        public final b m83d(double d) {
            C0178j.m209a(FirebaseAnalytics.Param.QUANTITY, Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: d */
        public final b m84d(@NonNull String str) {
            C0178j.m209a("ad_campaign_id", str, this.f67b);
            return this;
        }

        @Override // android.os.Parcelable
        @Contract(pure = true)
        public final int describeContents() {
            return 0;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: e */
        public final b m85e(double d) {
            C0178j.m209a("rating_value", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: e */
        public final b m86e(@NonNull String str) {
            C0178j.m209a("ad_campaign_name", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: f */
        public final b m87f(double d) {
            C0178j.m209a("spatial_x", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: f */
        public final b m88f(@NonNull String str) {
            C0178j.m209a("ad_size", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: g */
        public final b m89g(double d) {
            C0178j.m209a("spatial_y", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: g */
        public final b m90g(@NonNull String str) {
            C0178j.m209a("ad_group_name", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: h */
        public final b m91h(double d) {
            C0178j.m209a("spatial_z", Double.valueOf(d), this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: h */
        public final b m92h(@NonNull String str) {
            C0178j.m209a("ad_group_id", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: i */
        public final b m93i(@NonNull String str) {
            C0178j.m209a("ad_network_name", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: j */
        public final b m94j(@NonNull String str) {
            C0178j.m209a("ad_mediation_name", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: k */
        public final b m95k(@NonNull String str) {
            C0178j.m209a("checkout_as_guest", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: l */
        public final b m96l(@NonNull String str) {
            C0178j.m209a("content_id", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: m */
        public final b m97m(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.CONTENT_TYPE, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: n */
        public final b m98n(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.CURRENCY, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: o */
        public final b m99o(@NonNull String str) {
            C0178j.m209a("date", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: p */
        public final b m100p(@NonNull String str) {
            C0178j.m209a("description", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: q */
        public final b m101q(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.DESTINATION, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: r */
        public final b m102r(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.END_DATE, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: s */
        public final b m103s(@NonNull String str) {
            C0178j.m209a("item_added_from", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: t */
        public final b m104t(@NonNull String str) {
            C0178j.m209a(FirebaseAnalytics.Param.LEVEL, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: u */
        public final b m105u(@NonNull String str) {
            C0178j.m209a(AppMeasurementSdk.ConditionalUserProperty.NAME, str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: v */
        public final b m106v(@NonNull String str) {
            C0178j.m209a("order_id", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: w */
        public final b m107w(@NonNull String str) {
            C0178j.m209a("origin", str, this.f67b);
            return this;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeString(C0178j.m207a(this.f67b));
            parcel.writeString(this.f68c);
            parcel.writeLong(this.f69d);
            parcel.writeByte(this.f70e ? (byte) 1 : (byte) 0);
            parcel.writeString(this.f71f);
            parcel.writeString(this.f72g);
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: x */
        public final b m108x(@NonNull String str) {
            C0178j.m209a("receipt_id", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: y */
        public final b m109y(@NonNull String str) {
            C0178j.m209a("referral_from", str, this.f67b);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: z */
        public final b m110z(@NonNull String str) {
            C0178j.m209a("registration_method", str, this.f67b);
            return this;
        }
    }

    /* JADX INFO: renamed from: com.a.a.f$c */
    @AnyThread
    public static class c {

        /* JADX INFO: renamed from: a */
        @NonNull
        final JSONObject f73a = new JSONObject();

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final c m113a(@Size(min = 1) @NonNull String str, @Size(min = 1) @NonNull String str2) {
            C0178j.m209a(str, str2, this.f73a);
            return this;
        }

        @CheckResult
        @NonNull
        /* JADX INFO: renamed from: a */
        public final c m114a(@Size(min = 1) @NonNull Map<String, String> map) {
            JSONObject jSONObjectM231e = C0178j.m231e(map);
            if (jSONObjectM231e == null || jSONObjectM231e.length() < 1) {
                C0174f.m16a(2, "IDL", "add", "Invalid Input");
            } else {
                C0178j.m222b(this.f73a, jSONObjectM231e);
            }
            return this;
        }
    }

    private C0174f() {
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m15a() {
        synchronized (f50u) {
            try {
                m16a(2, "TRA", "unConfigure", "UnConfigure Tracker");
                f50u.f54x = 3;
                f50u.f52v = null;
                f50u.f53w = null;
                f50u.f55y = null;
                f50u.f56z = 0;
                if (f50u.f51A != null) {
                    f50u.f51A.m116a();
                }
                f50u.f51A = null;
            } catch (Throwable th) {
                m16a(1, "TRA", "unConfigure", th);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m16a(int i, @Size(MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS) @NonNull String str, @Size(max = 13, min = MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS) @NonNull String str2, @Nullable Object... objArr) {
        String stackTraceString;
        synchronized (f50u) {
            if (i != 0) {
                if ((f50u.f54x != 0 && f50u.f54x >= i) || (f50u.f56z != 0 && f50u.f56z >= i)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("KO/");
                    sb.append("TR/");
                    sb.append((CharSequence) str, 0, Math.min(str.length(), 3));
                    sb.append("/");
                    sb.append((CharSequence) str2, 0, Math.min(str2.length(), 13));
                    String string = sb.toString();
                    StringBuilder sb2 = new StringBuilder();
                    if (objArr != null) {
                        for (int i2 = 0; i2 < objArr.length; i2++) {
                            if (objArr[i2] != null) {
                                try {
                                    if (objArr[i2] instanceof String) {
                                        JSONObject jSONObjectM231e = C0178j.m231e(objArr[i2]);
                                        JSONArray jSONArrayM232f = C0178j.m232f(objArr[i2]);
                                        stackTraceString = jSONObjectM231e != null ? jSONObjectM231e.toString(2) : null;
                                        if (stackTraceString == null && jSONArrayM232f != null) {
                                            stackTraceString = jSONArrayM232f.toString(2);
                                        }
                                        if (stackTraceString == null) {
                                            stackTraceString = (String) objArr[i2];
                                        }
                                    } else if (objArr[i2] instanceof JSONObject) {
                                        stackTraceString = ((JSONObject) objArr[i2]).toString(2);
                                    } else if (objArr[i2] instanceof JSONArray) {
                                        stackTraceString = ((JSONArray) objArr[i2]).toString(2);
                                    } else {
                                        stackTraceString = objArr[i2] instanceof Throwable ? Log.getStackTraceString((Throwable) objArr[i2]) : objArr[i2].toString();
                                    }
                                } catch (Throwable th) {
                                    stackTraceString = "Failed to build message.\n" + Log.getStackTraceString(th);
                                }
                                if (stackTraceString != null) {
                                    sb2.append(stackTraceString);
                                    if (i2 < objArr.length - 1) {
                                        sb2.append("\n");
                                    }
                                }
                            }
                        }
                    }
                    if (sb2.length() == 0) {
                        sb2.append(" ");
                    }
                    for (String str3 : sb2.toString().split("\n")) {
                        if (f50u.f56z > 0 && i <= f50u.f56z && f50u.f55y != null) {
                            try {
                                f50u.f55y.m14a(i, string, str3);
                            } catch (Throwable unused) {
                            }
                        }
                        if (f50u.f54x > 0 && i <= f50u.f54x && i != 0) {
                            if (i == 1) {
                                Log.e(string, str3);
                            } else if (i == 2) {
                                Log.w(string, str3);
                            } else if (i == 3) {
                                Log.i(string, str3);
                            } else if (i == 4) {
                                Log.d(string, str3);
                            } else if (i == 5) {
                                Log.v(string, str3);
                            }
                        }
                    }
                }
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    static void m17a(@NonNull InterfaceC0172d interfaceC0172d, int i) {
        synchronized (f50u) {
            f50u.f55y = interfaceC0172d;
            if (i > 0 && i <= 5) {
                f50u.f56z = i;
            }
        }
    }

    @SuppressLint({"ObsoleteSdkInt"})
    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m18a(@NonNull a aVar) {
        synchronized (f50u) {
            if (Build.VERSION.SDK_INT < 14) {
                m16a(1, "TRA", "configure", "Below API 14 is unsupported. Cannot Configure.");
                return;
            }
            try {
                if (f50u.f51A != null) {
                    m16a(2, "TRA", "configure", "Already Configured");
                    return;
                }
                if (aVar == null) {
                    m16a(1, "TRA", "configure", "Null Configuration");
                    return;
                }
                f50u.f54x = C0178j.m201a((Object) aVar.f60d, 3);
                if (aVar.f57a != null && aVar.f57a.getApplicationContext() != null) {
                    Context applicationContext = aVar.f57a.getApplicationContext();
                    SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("koov", 0);
                    sharedPreferences.edit().apply();
                    int iM201a = C0178j.m201a((Object) Integer.valueOf(sharedPreferences.getInt("log_level", -1)), f50u.f54x);
                    if (iM201a != f50u.f54x) {
                        f50u.f54x = iM201a;
                        m16a(4, "TRA", "configure", "Override LogLevel " + iM201a);
                    }
                    JSONObject jSONObject = new JSONObject();
                    C0178j.m210a("url_init", sharedPreferences.getString("url_init", null), jSONObject, 0);
                    C0178j.m210a("url_push_token_add", sharedPreferences.getString("url_push_token_add", null), jSONObject, 0);
                    C0178j.m210a("url_push_token_remove", sharedPreferences.getString("url_push_token_remove", null), jSONObject, 0);
                    C0178j.m210a("url_get_attribution", sharedPreferences.getString("url_get_attribution", null), jSONObject, 0);
                    C0178j.m210a("url_initial", sharedPreferences.getString("url_initial", null), jSONObject, 0);
                    C0178j.m210a("url_update", sharedPreferences.getString("url_update", null), jSONObject, 0);
                    C0178j.m210a("url_identity_link", sharedPreferences.getString("url_identity_link", null), jSONObject, 0);
                    C0178j.m210a("url_event", sharedPreferences.getString("url_event", null), jSONObject, 0);
                    if (jSONObject.length() != 0) {
                        m16a(4, "TRA", "configure", "Override URLs", jSONObject);
                    }
                    boolean z = aVar.f58b == null || aVar.f58b.trim().isEmpty();
                    boolean z2 = aVar.f59c == null || aVar.f59c.trim().isEmpty();
                    if ((z && z2) || (!z && !z2)) {
                        m16a(1, "TRA", "configure", "Either (but not both) App Guid or Partner Name required");
                        return;
                    }
                    f50u.f51A = new RunnableC0175g(applicationContext, m31e(), f50u.f53w, aVar.f58b, aVar.f59c, aVar.f61e, aVar.f62f, jSONObject, aVar.f65i);
                    if (aVar.f63g != null) {
                        f50u.f51A.m122b(aVar.f63g.booleanValue());
                    }
                    if (aVar.f64h != null) {
                        f50u.f51A.m118a(aVar.f64h);
                    }
                    m16a(3, "TRA", "configure", "Complete: " + m31e());
                    if (f50u.f54x > 3) {
                        m16a(2, "TRA", "configure", "Log Level set higher than recommended for publishing");
                    }
                    return;
                }
                m16a(1, "TRA", "configure", "Null Context");
            } catch (Throwable th) {
                m16a(1, "TRA", "configure", "Unknown", th);
                f50u.f51A = null;
            }
        }
    }

    @SuppressLint({"CheckResult"})
    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m19a(@NonNull b bVar) {
        synchronized (f50u) {
            m16a(3, "TRA", "sendEvent", "sendEvent(Event)");
            if (f50u.f51A == null || bVar == null || bVar.f68c.trim().isEmpty()) {
                m16a(2, "TRA", "sendEvent", "Invalid Configuration or Parameter");
            } else {
                if (bVar.f70e) {
                    long jM202a = C0178j.m202a();
                    double d = jM202a - bVar.f69d;
                    Double.isNaN(d);
                    double dRound = Math.round(d / 100.0d);
                    Double.isNaN(dRound);
                    bVar.m82c(new Date(bVar.f69d)).m77b(new Date(jM202a)).m63a(dRound / 10.0d);
                }
                f50u.f51A.m117a(6, bVar.f68c, C0178j.m207a(bVar.f67b), bVar.f71f, bVar.f72g, null);
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m20a(@NonNull c cVar) {
        synchronized (f50u) {
            m16a(3, "TRA", "setIdentityLi", "setIdentityLink");
            if (f50u.f51A == null || cVar == null || cVar.f73a.length() <= 0) {
                m16a(2, "TRA", "setIdentityLi", "Invalid Configuration or Parameter");
            } else {
                f50u.f51A.m118a(cVar);
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m21a(@NonNull String str) {
        synchronized (f50u) {
            m16a(3, "TRA", "addPushToken", "addPushToken");
            if (f50u.f51A == null || str == null || str.isEmpty()) {
                m16a(2, "TRA", "addPushToken", "Invalid Configuration or Parameter");
            } else {
                f50u.f51A.m119a(str, true);
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m22a(@NonNull String str, @NonNull String str2) {
        synchronized (f50u) {
            if (str != null) {
                if (f50u.f52v == null && f50u.f53w == null && f50u.f51A == null) {
                    f50u.f52v = str;
                    f50u.f53w = str2;
                }
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: a */
    public static void m23a(boolean z) {
        synchronized (f50u) {
            m16a(3, "TRA", "setSleep", "setSleep");
            if (f50u.f51A != null) {
                f50u.f51A.m120a(z);
            } else {
                m16a(2, "TRA", "setSleep", "Invalid Configuration or Parameter");
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: b */
    public static void m24b(@NonNull String str) {
        synchronized (f50u) {
            m16a(3, "TRA", "removePushTok", "removePushToken");
            if (f50u.f51A == null || str == null || str.isEmpty()) {
                m16a(2, "TRA", "removePushTok", "Invalid Configuration or Parameter");
            } else {
                f50u.f51A.m119a(str, false);
            }
        }
    }

    /* JADX INFO: renamed from: b */
    public static void m25b(@Size(min = 1) @NonNull String str, @NonNull String str2) {
        synchronized (f50u) {
            m16a(3, "TRA", "sendEvent", "sendEvent(String,String)");
            if (f50u.f51A == null || str == null || str2 == null || str.trim().isEmpty()) {
                m16a(2, "TRA", "sendEvent", "Invalid Configuration or Parameter");
            } else {
                f50u.f51A.m117a(6, str, str2, null, null, null);
            }
        }
    }

    @AnyThread
    /* JADX INFO: renamed from: b */
    public static void m26b(boolean z) {
        synchronized (f50u) {
            m16a(3, "TRA", "setAppLimitAd", "setAppLimitAdTracking");
            if (f50u.f51A != null) {
                f50u.f51A.m122b(z);
            } else {
                m16a(2, "TRA", "setAppLimitAd", "Invalid Configuration or Parameter");
            }
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: b */
    public static boolean m27b() {
        boolean z;
        synchronized (f50u) {
            z = f50u.f51A != null;
        }
        return z;
    }

    @AnyThread
    /* JADX INFO: renamed from: c */
    public static void m28c(@Size(min = 1) @NonNull String str) {
        synchronized (f50u) {
            m16a(3, "TRA", "sendEventDeep", "sendEventDeepLink");
            if (f50u.f51A == null || str == null || str.trim().isEmpty()) {
                m16a(2, "TRA", "sendEventDeep", "Invalid Configuration or Parameter");
            } else {
                f50u.f51A.m117a(8, null, null, null, null, str);
            }
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: c */
    public static boolean m29c() {
        synchronized (f50u) {
            if (f50u.f51A != null) {
                return f50u.f51A.mo126d();
            }
            m16a(2, "TRA", "isSessionActi", "Invalid Configuration or Parameter");
            return false;
        }
    }

    @Contract(pure = true)
    @AnyThread
    /* JADX INFO: renamed from: d */
    public static boolean m30d() {
        synchronized (f50u) {
            if (f50u.f51A != null) {
                return f50u.f51A.m123b();
            }
            m16a(2, "TRA", "isSleep", "Invalid Configuration or Parameter");
            return false;
        }
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: e */
    public static String m31e() {
        String str;
        synchronized (f50u) {
            if (f50u.f52v != null) {
                str = "AndroidTracker 3.3.1 (" + f50u.f52v + ")";
            } else {
                str = "AndroidTracker 3.3.1";
            }
        }
        return str;
    }

    @Contract(pure = true)
    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: f */
    public static String m32f() {
        synchronized (f50u) {
            if (f50u.f51A != null) {
                return f50u.f51A.m115a("attribution");
            }
            m16a(2, "TRA", "getAttributio", "Invalid Configuration or Parameter");
            return "";
        }
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: g */
    public static String m33g() {
        synchronized (f50u) {
            if (f50u.f51A != null) {
                return f50u.f51A.m115a("kochava_device_id");
            }
            m16a(2, "TRA", "getDeviceId", "Invalid Configuration or Parameter");
            return "";
        }
    }

    @NonNull
    @AnyThread
    /* JADX INFO: renamed from: h */
    public static C0171c m34h() {
        synchronized (f50u) {
            if (f50u.f51A != null) {
                return C0177i.a.m187a(f50u.f51A.m121b("install_referrer"), false, f50u.f51A.m115a("referrer"));
            }
            m16a(2, "TRA", "getInstallRef", "Invalid Configuration or Parameter");
            return C0177i.a.m187a(new JSONObject(), false, null);
        }
    }
}
