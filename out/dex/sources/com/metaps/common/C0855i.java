package com.metaps.common;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;

/* JADX INFO: renamed from: com.metaps.common.i */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0855i implements LocationListener {

    /* JADX INFO: renamed from: a */
    private static C0855i f947a;

    /* JADX INFO: renamed from: b */
    private LocationManager f948b;

    /* JADX INFO: renamed from: c */
    private Location f949c;

    /* JADX INFO: renamed from: d */
    private boolean f950d = false;

    /* JADX INFO: renamed from: e */
    private boolean f951e = true;

    /* JADX INFO: renamed from: f */
    private int f952f = 2;

    private C0855i() {
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0855i m978a() {
        if (f947a == null) {
            f947a = new C0855i();
        }
        return f947a;
    }

    /* JADX INFO: renamed from: a */
    private String m979a(String str) {
        if ("gps".equals(str)) {
            return "android.permission.ACCESS_FINE_LOCATION";
        }
        if ("network".equals(str)) {
            return "android.permission.ACCESS_COARSE_LOCATION";
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    private void m980a(int i) {
        this.f952f = i;
    }

    /* JADX INFO: renamed from: a */
    private void m981a(final long j) {
        new Thread(new Runnable() { // from class: com.metaps.common.i.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(j);
                } catch (Exception e) {
                    C0847a.m905a(C0855i.class.toString(), "Exception occurred in timeout thread.", e);
                }
                C0855i.this.m985e();
                C0847a.m903a(C0855i.class.toString(), "Location updates timeout.");
            }
        }).start();
    }

    /* JADX INFO: renamed from: a */
    private boolean m983a(String str, Context context) {
        return str != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    /* JADX INFO: renamed from: b */
    private synchronized LocationManager m984b(Context context) {
        if (this.f948b == null) {
            this.f948b = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
        }
        return this.f948b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e */
    public synchronized void m985e() {
        try {
            if (this.f950d) {
                if (this.f948b != null) {
                    this.f948b.removeUpdates(this);
                }
                this.f950d = false;
                C0847a.m903a(C0855i.class.toString(), "Stop location updates.");
            }
        } catch (Exception e) {
            C0847a.m905a(C0855i.class.toString(), "Exception occurred during stopping location updates.", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m986a(Context context) {
        boolean z;
        try {
            if (!this.f951e) {
                C0847a.m903a(C0855i.class.toString(), "Location not enabled.");
                return;
            }
            Iterator<String> it = m984b(context).getProviders(true).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (m983a(m979a(it.next()), context)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                C0847a.m903a(C0855i.class.toString(), "Location permission not granted.");
                return;
            }
            synchronized (this) {
                if (this.f950d) {
                    C0847a.m903a(C0855i.class.toString(), "Updating location currently.");
                    return;
                }
                this.f950d = true;
                Criteria criteria = new Criteria();
                criteria.setBearingRequired(false);
                criteria.setSpeedRequired(false);
                criteria.setAltitudeRequired(false);
                criteria.setAccuracy(this.f952f);
                m984b(context).requestLocationUpdates(0L, 0.0f, criteria, this, (Looper) null);
                C0847a.m903a(C0855i.class.toString(), "Request location updates.");
                m981a(((long) C0858l.m1018a(0).m1030e(C0858l.f985f)) * 1000);
            }
        } catch (Exception e) {
            C0847a.m905a(C0855i.class.toString(), "Cannot request location updates.", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m987a(boolean z) {
        this.f951e = z;
    }

    /* JADX INFO: renamed from: b */
    public Location m988b() {
        return this.f949c;
    }

    /* JADX INFO: renamed from: c */
    public void m989c() {
        m980a(1);
    }

    /* JADX INFO: renamed from: d */
    public void m990d() {
        m980a(2);
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        C0847a.m903a(C0855i.class.toString(), "Location updated.");
        this.f949c = location;
        m985e();
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
        C0847a.m903a(C0855i.class.toString(), "Provider disabled.");
        m985e();
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
        C0847a.m903a(C0855i.class.toString(), "Provider enabled.");
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        C0847a.m903a(C0855i.class.toString(), "Status changed: " + i);
        if (i != 2) {
            m985e();
        }
    }
}
