package com.nintendo.npf.sdk.internal.p022d;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d */
/* JADX INFO: compiled from: NintendoAccountAuthSession.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0949d implements Parcelable {
    public static final Parcelable.Creator<C0949d> CREATOR;

    /* JADX INFO: renamed from: j */
    private static final char[] f1264j = new char[62];

    /* JADX INFO: renamed from: a */
    public final c f1265a;

    /* JADX INFO: renamed from: b */
    public final String f1266b;

    /* JADX INFO: renamed from: c */
    public final String f1267c;

    /* JADX INFO: renamed from: d */
    private a f1268d;

    /* JADX INFO: renamed from: e */
    private d f1269e;

    /* JADX INFO: renamed from: f */
    private b f1270f;

    /* JADX INFO: renamed from: g */
    private NPFError f1271g;

    /* JADX INFO: renamed from: h */
    private WeakReference<Activity> f1272h;

    /* JADX INFO: renamed from: i */
    private NintendoAccount.AuthorizationCallback f1273i;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d$2, reason: invalid class name */
    /* JADX INFO: compiled from: NintendoAccountAuthSession.java */
    static /* synthetic */ class AnonymousClass2 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f1274a = new int[a.values().length];

        static {
            try {
                f1274a[a.NOT_STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1274a[a.PENDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1274a[a.CLOSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1274a[a.TRYING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1274a[a.RETRYING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d$a */
    /* JADX INFO: compiled from: NintendoAccountAuthSession.java */
    private enum a {
        NOT_STARTED,
        TRYING,
        PENDING,
        RETRYING,
        CLOSED
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d$b */
    /* JADX INFO: compiled from: NintendoAccountAuthSession.java */
    public static class b {

        /* JADX INFO: renamed from: a */
        public final String f1276a;

        /* JADX INFO: renamed from: b */
        public final String f1277b;

        public b(String str, String str2) {
            this.f1276a = str;
            this.f1277b = str2;
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d$c */
    /* JADX INFO: compiled from: NintendoAccountAuthSession.java */
    public enum c {
        AUTHORIZE_BY,
        AUTHORIZE_BY_2,
        SWITCH_BY,
        SWITCH_BY_2
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.d.d$d */
    /* JADX INFO: compiled from: NintendoAccountAuthSession.java */
    public enum d {
        NORMAL,
        CALLER_ACTIVITY_IS_DEAD,
        PROCESS_RESTARTED
    }

    static {
        int i = 0;
        char c2 = 'a';
        while (c2 <= 'z') {
            f1264j[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = '0';
        while (c3 <= '9') {
            f1264j[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        char c4 = 'A';
        while (c4 <= 'Z') {
            f1264j[i] = c4;
            c4 = (char) (c4 + 1);
            i++;
        }
        CREATOR = new Parcelable.Creator<C0949d>() { // from class: com.nintendo.npf.sdk.internal.d.d.1
            @Override // android.os.Parcelable.Creator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0949d createFromParcel(Parcel parcel) {
                return new C0949d(parcel);
            }

            @Override // android.os.Parcelable.Creator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public C0949d[] newArray(int i2) {
                return new C0949d[i2];
            }
        };
    }

    private C0949d(Parcel parcel) {
        this.f1268d = a.TRYING;
        this.f1265a = c.valueOf(parcel.readString());
        this.f1266b = parcel.readString();
        this.f1267c = parcel.readString();
    }

    public C0949d(c cVar) {
        this.f1268d = a.NOT_STARTED;
        this.f1265a = cVar;
        this.f1266b = m1365a(50);
        this.f1267c = m1365a(50);
    }

    /* JADX INFO: renamed from: a */
    public static String m1365a(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            char[] cArr = f1264j;
            double length = cArr.length;
            double dRandom = Math.random();
            Double.isNaN(length);
            sb.append(cArr[(int) (length * dRandom)]);
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: i */
    private void m1366i() {
        WeakReference<Activity> weakReference;
        if (!m1376d()) {
            throw new IllegalStateException("checkSystemState() can be called only when isTrying() == true.");
        }
        d dVar = this.f1269e;
        if (dVar == null || dVar == d.NORMAL) {
            if (this.f1273i == null || (weakReference = this.f1272h) == null) {
                this.f1269e = d.PROCESS_RESTARTED;
                return;
            }
            Activity activity = weakReference.get();
            if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                this.f1269e = d.NORMAL;
                return;
            }
            this.f1272h = null;
            this.f1273i = null;
            this.f1269e = d.CALLER_ACTIVITY_IS_DEAD;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1367a() {
        this.f1268d = a.PENDING;
    }

    /* JADX INFO: renamed from: a */
    public void m1368a(Activity activity, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        if (m1375c()) {
            this.f1268d = a.TRYING;
            this.f1272h = new WeakReference<>(activity);
            this.f1273i = authorizationCallback;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1369a(@NonNull NPFError nPFError) {
        if (!m1377e()) {
            throw new IllegalStateException("registerError() is must be called when isWaitingSessionTokenCode() is true");
        }
        m1366i();
        this.f1271g = nPFError;
    }

    /* JADX INFO: renamed from: a */
    public void m1370a(@NonNull b bVar) {
        if (!m1377e()) {
            throw new IllegalStateException("registerSessionTokenCode() is must be called when isWaitingSessionTokenCode() is true");
        }
        m1366i();
        this.f1270f = bVar;
    }

    /* JADX INFO: renamed from: a */
    public void m1371a(c cVar, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        if (m1373a(cVar)) {
            this.f1268d = a.RETRYING;
            this.f1273i = authorizationCallback;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1372a(NintendoAccount nintendoAccount, NPFError nPFError) {
        this.f1268d = a.CLOSED;
        NintendoAccount.AuthorizationCallback authorizationCallback = this.f1273i;
        if (authorizationCallback != null) {
            authorizationCallback.onComplete(nintendoAccount, nPFError);
        }
        this.f1273i = null;
        this.f1272h = null;
    }

    /* JADX INFO: renamed from: a */
    public boolean m1373a(c cVar) {
        return cVar == this.f1265a && this.f1268d == a.PENDING;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1374b() {
        int i = AnonymousClass2.f1274a[this.f1268d.ordinal()];
        return (i == 4 || i == 5) ? false : true;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1375c() {
        return this.f1268d == a.NOT_STARTED && this.f1273i == null;
    }

    /* JADX INFO: renamed from: d */
    public boolean m1376d() {
        return this.f1268d == a.TRYING;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: renamed from: e */
    public boolean m1377e() {
        return this.f1268d == a.TRYING && this.f1270f == null && this.f1271g == null;
    }

    /* JADX INFO: renamed from: f */
    public d m1378f() {
        d dVar = this.f1269e;
        if (dVar != null) {
            return dVar;
        }
        throw new IllegalStateException("getSystemState() can be called only after registerSessionTokenCode() or registerError() was called.");
    }

    /* JADX INFO: renamed from: g */
    public b m1379g() {
        return this.f1270f;
    }

    @Nullable
    /* JADX INFO: renamed from: h */
    public NPFError m1380h() {
        return this.f1271g;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1265a.name());
        parcel.writeString(this.f1266b);
        parcel.writeString(this.f1267c);
    }
}
