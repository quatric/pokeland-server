package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.analytics.Analytics;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.a */
/* JADX INFO: compiled from: ActivityLifecycleCallbacksImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0998a implements Application.ActivityLifecycleCallbacks {

    /* JADX INFO: renamed from: a */
    private static final String f1414a = "a";

    /* JADX INFO: renamed from: b */
    private NPFSDK.EventHandler f1415b;

    /* JADX INFO: renamed from: c */
    private Timer f1416c;

    /* JADX INFO: renamed from: m */
    private a f1426m;

    /* JADX INFO: renamed from: d */
    private Object f1417d = new Object();

    /* JADX INFO: renamed from: e */
    private long f1418e = 0;

    /* JADX INFO: renamed from: f */
    private long f1419f = 0;

    /* JADX INFO: renamed from: g */
    private long f1420g = 0;

    /* JADX INFO: renamed from: h */
    private boolean f1421h = false;

    /* JADX INFO: renamed from: i */
    private boolean f1422i = false;

    /* JADX INFO: renamed from: j */
    private boolean f1423j = false;

    /* JADX INFO: renamed from: k */
    private boolean f1424k = false;

    /* JADX INFO: renamed from: l */
    private PromoCodeReceiver f1425l = null;

    /* JADX INFO: renamed from: n */
    private PromoCodeResumeLock f1427n = new PromoCodeResumeLock(this);

    /* JADX INFO: renamed from: o */
    private boolean f1428o = false;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.a$a */
    /* JADX INFO: compiled from: ActivityLifecycleCallbacksImpl.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1148a(int i, int i2, Intent intent);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.a$b */
    /* JADX INFO: compiled from: ActivityLifecycleCallbacksImpl.java */
    private enum b {
        START,
        UPDATE,
        PAUSE,
        RESUME
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.a$c */
    /* JADX INFO: compiled from: ActivityLifecycleCallbacksImpl.java */
    private static class c {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1435a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1502a(b bVar, long j) {
        C0955e.m1391a(f1414a, "Analytics session : " + bVar + " : " + j);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("action", bVar.toString().toLowerCase());
            jSONObject.put("duration", j);
            Analytics.reportEvent("NPFCOMMON", "SESSION", null, jSONObject);
        } catch (JSONException e) {
            C0955e.m1394b(f1414a, "sendSessionEvent error", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: g */
    public void m1512g() {
        m1502a(b.UPDATE, (Calendar.getInstance().getTimeInMillis() - this.f1418e) - this.f1420g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: h */
    public void m1513h() {
        synchronized (this.f1417d) {
            if (this.f1416c != null) {
                this.f1416c.cancel();
                this.f1416c.purge();
                this.f1416c = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: i */
    public void m1514i() {
        synchronized (this.f1417d) {
            if (this.f1416c == null) {
                TimerTask timerTask = new TimerTask() { // from class: com.nintendo.npf.sdk.internal.impl.a.3
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        C0998a.this.m1512g();
                    }
                };
                this.f1416c = new Timer(true);
                C0947b c0947bMo1065s = c.f1435a.mo1065s();
                this.f1416c.schedule(timerTask, c0947bMo1065s.m1314E(), c0947bMo1065s.m1314E());
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1515a(@NonNull NPFSDK.EventHandler eventHandler) {
        this.f1415b = eventHandler;
    }

    /* JADX INFO: renamed from: a */
    public void m1516a(a aVar) {
        this.f1426m = aVar;
    }

    /* JADX INFO: renamed from: a */
    public void m1517a(@NonNull final BaaSUser.AuthorizationCallback authorizationCallback) {
        if (this.f1423j) {
            this.f1415b.onNintendoAccountAuthError(new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "App is launched from authorization browser page after closing auth process"));
            this.f1423j = false;
            this.f1424k = true;
        }
        new AsyncTask<Void, Void, Void>() { // from class: com.nintendo.npf.sdk.internal.impl.a.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public Void doInBackground(Void... voidArr) {
                if (c.f1435a.mo1065s().m1341p() != null && !"".equals(c.f1435a.mo1065s().m1341p())) {
                    return null;
                }
                Application applicationMo1047a = c.f1435a.mo1047a();
                if (Build.MANUFACTURER.equals("Amazon")) {
                    ContentResolver contentResolver = applicationMo1047a.getContentResolver();
                    int i = Settings.Secure.getInt(contentResolver, "limit_ad_tracking", 2);
                    if (i == 0) {
                        c.f1435a.mo1065s().m1321a(Settings.Secure.getString(contentResolver, "advertising_id"));
                        return null;
                    }
                    if (i == 2) {
                        C0955e.m1396d(C0998a.f1414a, "Failed Fire OS does not available ");
                        return null;
                    }
                    C0955e.m1396d(C0998a.f1414a, "Failed getting advertisingId");
                    return null;
                }
                try {
                    AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationMo1047a);
                    if (advertisingIdInfo != null) {
                        c.f1435a.mo1065s().m1321a(advertisingIdInfo.getId());
                    } else {
                        C0955e.m1396d(C0998a.f1414a, "Failed getting advertisingId: probably, google play service disable.");
                    }
                    return null;
                } catch (GooglePlayServicesNotAvailableException e) {
                    C0955e.m1394b(C0998a.f1414a, "Failed Google Play Service is not available ", e);
                    return null;
                } catch (GooglePlayServicesRepairableException e2) {
                    C0955e.m1394b(C0998a.f1414a, "Failed Google Play Service library load ", e2);
                    return null;
                } catch (IOException e3) {
                    C0955e.m1394b(C0998a.f1414a, "Failed getting advertisingId ", e3);
                    return null;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onPostExecute(Void r2) {
                C1017g.m1615a((String) null, (String) null, new C1017g.a() { // from class: com.nintendo.npf.sdk.internal.impl.a.2.1
                    @Override // com.nintendo.npf.sdk.internal.impl.C1017g.a
                    /* JADX INFO: renamed from: a */
                    public void mo1237a(BaaSUser baaSUser, String str, NPFError nPFError) {
                        if (baaSUser != null && C1027q.m1705b(nPFError)) {
                            if (!C0998a.this.f1422i) {
                                C0998a.this.m1523d();
                                C0998a.this.m1513h();
                                if (str == null) {
                                    c.f1435a.mo1065s().m1311B();
                                } else {
                                    c.f1435a.mo1065s().m1327c(str);
                                }
                                C0998a.this.m1502a(b.START, 0L);
                                C0998a.this.f1422i = true;
                                if (AbstractC0880e.m1127b()) {
                                    c.f1435a.mo1069w().m1734a(true, nPFError);
                                }
                                if (C0998a.this.f1424k) {
                                    C0954d.m1389b("naauth_error", "NAAuth#BeKilledBySysOrUserOnBackgroudAndResumeToApp#Error", new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "App is launched from authorization browser page after closing auth process"));
                                    C0998a.this.f1424k = false;
                                }
                            }
                            C0998a.this.m1514i();
                        }
                        authorizationCallback.onComplete(baaSUser, nPFError);
                    }
                });
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: renamed from: a */
    public void m1518a(boolean z) {
        this.f1427n.m1739b();
        this.f1428o = z;
    }

    /* JADX INFO: renamed from: a */
    public boolean m1519a() {
        return this.f1422i;
    }

    /* JADX INFO: renamed from: b */
    public PromoCodeResumeLock m1520b() {
        return this.f1427n;
    }

    /* JADX INFO: renamed from: b */
    public void m1521b(boolean z) {
        this.f1423j = z;
    }

    /* JADX INFO: renamed from: c */
    public NPFSDK.EventHandler m1522c() {
        return this.f1415b;
    }

    /* JADX INFO: renamed from: d */
    public void m1523d() {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        this.f1418e = timeInMillis;
        this.f1419f = timeInMillis;
        this.f1420g = 0L;
        this.f1421h = false;
    }

    /* JADX INFO: renamed from: e */
    public a m1524e() {
        return this.f1426m;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        C0955e.m1393b(f1414a, "Calling onActivityCreated()");
        C0955e.m1391a(f1414a, "onCreated : " + activity.getPackageName() + "." + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        C0955e.m1393b(f1414a, "Calling onActivityDestroyed()");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        C0955e.m1393b(f1414a, "Calling onActivityPaused()");
        C0955e.m1391a(f1414a, "onPaused : " + activity.getPackageName() + "." + activity.getLocalClassName());
        if (activity.getLocalClassName().startsWith("com.nintendo.npf.sdk.internal.app")) {
            return;
        }
        this.f1421h = true;
        m1513h();
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        this.f1419f = timeInMillis;
        m1502a(b.PAUSE, (timeInMillis - this.f1418e) - this.f1420g);
        if (!AbstractC0880e.m1127b() || this.f1425l == null || this.f1428o) {
            return;
        }
        c.f1435a.mo1047a().unregisterReceiver(this.f1425l);
        this.f1425l = null;
        C0955e.m1391a(f1414a, "onPause : unregister PURCHASES_UPDATED broadcast receiver.");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        C0955e.m1393b(f1414a, "Calling onActivityResumed()");
        C0955e.m1391a(f1414a, "onResumed : " + activity.getPackageName() + "." + activity.getLocalClassName());
        if (activity.getLocalClassName().startsWith("com.nintendo.npf.sdk.internal.app")) {
            return;
        }
        if (this.f1422i) {
            C0955e.m1391a(f1414a, "onResumed initialized");
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            C0955e.m1391a(f1414a, "session pausedTimestamp : " + this.f1419f);
            long j = this.f1419f;
            if (j == 0 || timeInMillis - j > 600000) {
                m1523d();
                c.f1435a.mo1065s().m1311B();
                m1502a(b.START, 0L);
            } else if (this.f1421h) {
                this.f1420g += timeInMillis - j;
                m1502a(b.RESUME, (timeInMillis - this.f1418e) - this.f1420g);
                this.f1421h = false;
            } else {
                m1512g();
            }
            m1514i();
            if (AbstractC0880e.m1127b() && this.f1425l == null && !this.f1428o) {
                c.f1435a.mo1069w().m1734a(false, null);
            }
        } else {
            m1517a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.a.1
                @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
                public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                }
            });
        }
        if (AbstractC0880e.m1127b() && this.f1425l == null && !this.f1428o) {
            IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
            this.f1425l = new PromoCodeReceiver();
            c.f1435a.mo1047a().registerReceiver(this.f1425l, intentFilter);
            C0955e.m1391a(f1414a, "onResume : register PURCHASES_UPDATED broadcast receiver.");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        C0955e.m1393b(f1414a, "Calling onActivitySaveInstanceState()");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        C0955e.m1393b(f1414a, "Calling onActivityStarted()");
        C0955e.m1391a(f1414a, "onStarted : " + activity.getPackageName() + "." + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        C0955e.m1393b(f1414a, "Calling onActivityStopped()");
        C0955e.m1391a(f1414a, "onStopped : " + activity.getPackageName() + "." + activity.getLocalClassName());
    }
}
