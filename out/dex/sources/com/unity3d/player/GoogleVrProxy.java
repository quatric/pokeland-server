package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class GoogleVrProxy extends C1121c implements GoogleVrVideo {

    /* JADX INFO: renamed from: f */
    private boolean f1841f;

    /* JADX INFO: renamed from: g */
    private boolean f1842g;

    /* JADX INFO: renamed from: h */
    private Runnable f1843h;

    /* JADX INFO: renamed from: i */
    private Vector f1844i;

    /* JADX INFO: renamed from: j */
    private SurfaceView f1845j;

    /* JADX INFO: renamed from: k */
    private C1085a f1846k;

    /* JADX INFO: renamed from: l */
    private Thread f1847l;

    /* JADX INFO: renamed from: m */
    private Handler f1848m;

    /* JADX INFO: renamed from: com.unity3d.player.GoogleVrProxy$a */
    class C1085a {

        /* JADX INFO: renamed from: a */
        public boolean f1860a = false;

        /* JADX INFO: renamed from: b */
        public boolean f1861b = false;

        /* JADX INFO: renamed from: c */
        public boolean f1862c = false;

        /* JADX INFO: renamed from: d */
        public boolean f1863d = false;

        /* JADX INFO: renamed from: e */
        public boolean f1864e = true;

        /* JADX INFO: renamed from: f */
        public boolean f1865f = false;

        C1085a() {
        }

        /* JADX INFO: renamed from: a */
        public final boolean m1806a() {
            return this.f1860a && this.f1861b;
        }

        /* JADX INFO: renamed from: b */
        public final void m1807b() {
            this.f1860a = false;
            this.f1861b = false;
            this.f1863d = false;
            this.f1864e = true;
            this.f1865f = false;
        }
    }

    public GoogleVrProxy(InterfaceC1124f interfaceC1124f) {
        super("Google VR", interfaceC1124f);
        this.f1841f = false;
        this.f1842g = false;
        this.f1843h = null;
        this.f1844i = new Vector();
        this.f1845j = null;
        this.f1846k = new C1085a();
        this.f1847l = null;
        this.f1848m = new Handler(Looper.getMainLooper()) { // from class: com.unity3d.player.GoogleVrProxy.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 135711) {
                    super.handleMessage(message);
                }
                switch (message.arg1) {
                    case 2147483645:
                        Iterator it = GoogleVrProxy.this.f1844i.iterator();
                        while (it.hasNext()) {
                            ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onFrameAvailable();
                        }
                        break;
                    case 2147483646:
                        Surface surface = (Surface) message.obj;
                        Iterator it2 = GoogleVrProxy.this.f1844i.iterator();
                        while (it2.hasNext()) {
                            ((GoogleVrVideo.GoogleVrVideoCallbacks) it2.next()).onSurfaceAvailable(surface);
                        }
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        };
        initVrJni();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1793a(boolean z) {
        this.f1846k.f1863d = z;
    }

    /* JADX INFO: renamed from: a */
    private static boolean m1794a(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    /* JADX INFO: renamed from: a */
    private boolean m1795a(ClassLoader classLoader) {
        try {
            Class<?> clsLoadClass = classLoader.loadClass("com.unity3d.unitygvr.GoogleVR");
            C1133o c1133o = new C1133o(clsLoadClass, clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            c1133o.m1957a("initialize", new Class[]{Activity.class, Context.class, SurfaceView.class, Boolean.TYPE, Handler.class});
            c1133o.m1957a("deinitialize", new Class[0]);
            c1133o.m1957a("load", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Runnable.class});
            c1133o.m1957a("enable", new Class[]{Boolean.TYPE});
            c1133o.m1957a("unload", new Class[0]);
            c1133o.m1957a("pause", new Class[0]);
            c1133o.m1957a("resume", new Class[0]);
            c1133o.m1957a("getGvrLayout", new Class[0]);
            c1133o.m1957a("getVideoSurfaceId", new Class[0]);
            c1133o.m1957a("getVideoSurface", new Class[0]);
            this.f2032a = c1133o;
            return true;
        } catch (Exception e) {
            reportError("Exception initializing GoogleVR from Unity library. " + e.getLocalizedMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public boolean m1798d() {
        return this.f1846k.f1863d;
    }

    /* JADX INFO: renamed from: e */
    private void m1800e() {
        Activity activity = (Activity) this.f2034c;
        if (!this.f1842g || this.f1846k.f1865f || activity == null) {
            return;
        }
        this.f1846k.f1865f = true;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        activity.startActivity(intent);
    }

    private final native void initVrJni();

    private final native boolean isQuiting();

    private final native void setVrVideoTransform(float[][] fArr);

    /* JADX INFO: renamed from: a */
    public final void m1801a(Intent intent) {
        if (intent == null || !intent.getBooleanExtra("android.intent.extra.VR_LAUNCH", false)) {
            return;
        }
        this.f1842g = true;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m1802a() {
        return this.f1846k.f1860a;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m1803a(Activity activity, Context context, SurfaceView surfaceView, Runnable runnable) {
        String str;
        boolean zBooleanValue;
        if (activity == null || context == null || surfaceView == null || runnable == null) {
            str = "Invalid parameters passed to Google VR initiialization.";
        } else {
            this.f1846k.m1807b();
            this.f2034c = context;
            this.f1843h = runnable;
            if (!m1794a(19)) {
                str = "Google VR requires a device that supports an api version of 19 (KitKat) or better.";
            } else if (this.f1842g && !m1794a(24)) {
                str = "Daydream requires a device that supports an api version of 24 (Nougat) or better.";
            } else {
                if (!m1795a(UnityPlayer.class.getClassLoader())) {
                    return false;
                }
                try {
                    zBooleanValue = ((Boolean) this.f2032a.m1956a("initialize", activity, context, surfaceView, Boolean.valueOf(this.f1842g), this.f1848m)).booleanValue();
                } catch (Exception e) {
                    reportError("Exception while trying to intialize Unity Google VR Library. " + e.getLocalizedMessage());
                    zBooleanValue = false;
                }
                if (zBooleanValue) {
                    this.f1845j = surfaceView;
                    this.f1846k.f1860a = true;
                    this.f2035d = "";
                    return true;
                }
                str = "Unable to initialize GoogleVR library.";
            }
        }
        reportError(str);
        return false;
    }

    /* JADX INFO: renamed from: b */
    public final void m1804b() {
        resumeGvrLayout();
    }

    /* JADX INFO: renamed from: c */
    public final void m1805c() {
        SurfaceView surfaceView = this.f1845j;
        if (surfaceView != null) {
            surfaceView.getHolder().setSizeFromLayout();
        }
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void deregisterGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (this.f1844i.contains(googleVrVideoCallbacks)) {
            googleVrVideoCallbacks.onSurfaceUnavailable();
            this.f1844i.remove(googleVrVideoCallbacks);
        }
    }

    protected Object getVideoSurface() {
        if (m1798d() && !this.f1846k.f1864e) {
            try {
                return this.f2032a.m1956a("getVideoSurface", new Object[0]);
            } catch (Exception e) {
                reportError("Exception caught while Getting GoogleVR Video Surface. " + e.getLocalizedMessage());
            }
        }
        return null;
    }

    protected int getVideoSurfaceId() {
        if (m1798d() && !this.f1846k.f1864e) {
            try {
                return ((Integer) this.f2032a.m1956a("getVideoSurfaceId", new Object[0])).intValue();
            } catch (Exception e) {
                reportError("Exception caught while getting Video Surface ID from GoogleVR. " + e.getLocalizedMessage());
            }
        }
        return -1;
    }

    protected long loadGoogleVr(final boolean z, final boolean z2, final boolean z3, final boolean z4, final boolean z5) {
        if (!this.f1846k.f1860a) {
            return 0L;
        }
        final AtomicLong atomicLong = new AtomicLong(0L);
        this.f2035d = (z || z2) ? "Daydream" : "Cardboard";
        if (!runOnUiThreadWithSync(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    atomicLong.set(((Long) GoogleVrProxy.this.f2032a.m1956a("load", Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), Boolean.valueOf(z5), GoogleVrProxy.this.f1843h)).longValue());
                    GoogleVrProxy.this.f1846k.f1861b = true;
                } catch (Exception e) {
                    GoogleVrProxy.this.reportError("Exception caught while loading GoogleVR. " + e.getLocalizedMessage());
                    atomicLong.set(0L);
                }
            }
        }) || atomicLong.longValue() == 0) {
            reportError("Google VR had a fatal issue while loading. VR will not be available.");
        }
        return atomicLong.longValue();
    }

    protected void pauseGvrLayout() {
        if (this.f1846k.m1806a() && !this.f1846k.f1864e) {
            if (m1798d()) {
                Iterator it = this.f1844i.iterator();
                while (it.hasNext()) {
                    ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onSurfaceUnavailable();
                }
            }
            if (this.f2032a != null) {
                this.f2032a.m1956a("pause", new Object[0]);
            }
            this.f1846k.f1864e = true;
        }
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void registerGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (this.f1844i.contains(googleVrVideoCallbacks)) {
            return;
        }
        this.f1844i.add(googleVrVideoCallbacks);
        Surface surface = (Surface) getVideoSurface();
        if (surface != null) {
            googleVrVideoCallbacks.onSurfaceAvailable(surface);
        }
    }

    protected void resumeGvrLayout() {
        if (this.f1846k.m1806a() && this.f1846k.f1864e) {
            if (this.f2032a != null) {
                this.f2032a.m1956a("resume", new Object[0]);
            }
            this.f1846k.f1864e = false;
        }
    }

    protected void setGoogleVrModeEnabled(final boolean z) {
        if (!this.f1846k.m1806a() || this.f2033b == null || this.f2034c == null) {
            return;
        }
        if (!z && isQuiting()) {
            m1800e();
        }
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.3
            @Override // java.lang.Runnable
            public final void run() {
                if (z == GoogleVrProxy.this.m1798d()) {
                    return;
                }
                try {
                    if (z && !GoogleVrProxy.this.m1798d()) {
                        if (GoogleVrProxy.this.f2032a != null && GoogleVrProxy.this.f2033b != null && !GoogleVrProxy.this.f2033b.addViewToPlayer((View) GoogleVrProxy.this.f2032a.m1956a("getGvrLayout", new Object[0]), true)) {
                            GoogleVrProxy.this.reportError("Unable to add Google VR to view hierarchy.");
                            return;
                        }
                        if (GoogleVrProxy.this.f2032a != null) {
                            GoogleVrProxy.this.f2032a.m1956a("enable", true);
                        }
                        GoogleVrProxy.this.m1793a(true);
                        return;
                    }
                    if (z || !GoogleVrProxy.this.m1798d()) {
                        return;
                    }
                    GoogleVrProxy.this.m1793a(false);
                    if (GoogleVrProxy.this.f2032a != null) {
                        GoogleVrProxy.this.f2032a.m1956a("enable", false);
                    }
                    if (GoogleVrProxy.this.f2032a == null || GoogleVrProxy.this.f2033b == null) {
                        return;
                    }
                    GoogleVrProxy.this.f2033b.removeViewFromPlayer((View) GoogleVrProxy.this.f2032a.m1956a("getGvrLayout", new Object[0]));
                } catch (Exception e) {
                    GoogleVrProxy.this.reportError("Exception enabling Google VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override // com.unity3d.player.GoogleVrVideo
    public void setVideoLocationTransform(float[] fArr) {
        float[][] fArr2 = (float[][]) Array.newInstance((Class<?>) float.class, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                fArr2[i][i2] = fArr[(i * 4) + i2];
            }
        }
        setVrVideoTransform(fArr2);
    }

    protected void unloadGoogleVr() {
        if (this.f1846k.f1863d) {
            setGoogleVrModeEnabled(false);
        }
        if (this.f1846k.f1862c) {
            this.f1846k.f1862c = false;
        }
        this.f1845j = null;
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.GoogleVrProxy.4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (GoogleVrProxy.this.f2032a != null) {
                        GoogleVrProxy.this.f2032a.m1956a("unload", new Object[0]);
                        GoogleVrProxy.this.f2032a.m1956a("deinitialize", new Object[0]);
                        GoogleVrProxy.this.f2032a = null;
                    }
                    GoogleVrProxy.this.f1846k.f1861b = false;
                } catch (Exception e) {
                    GoogleVrProxy.this.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }
}
