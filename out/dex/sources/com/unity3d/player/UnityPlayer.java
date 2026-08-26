package com.unity3d.player;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UnityPlayer extends FrameLayout implements InterfaceC1124f {
    public static Activity currentActivity;

    /* JADX INFO: renamed from: a */
    C1115e f1877a;

    /* JADX INFO: renamed from: b */
    DialogC1129k f1878b;

    /* JADX INFO: renamed from: c */
    private int f1879c;

    /* JADX INFO: renamed from: d */
    private boolean f1880d;

    /* JADX INFO: renamed from: e */
    private boolean f1881e;

    /* JADX INFO: renamed from: f */
    private C1132n f1882f;

    /* JADX INFO: renamed from: g */
    private final ConcurrentLinkedQueue f1883g;

    /* JADX INFO: renamed from: h */
    private BroadcastReceiver f1884h;

    /* JADX INFO: renamed from: i */
    private boolean f1885i;

    /* JADX INFO: renamed from: j */
    private C1113c f1886j;

    /* JADX INFO: renamed from: k */
    private TelephonyManager f1887k;

    /* JADX INFO: renamed from: l */
    private ClipboardManager f1888l;

    /* JADX INFO: renamed from: m */
    private C1130l f1889m;

    /* JADX INFO: renamed from: n */
    private GoogleARCoreApi f1890n;

    /* JADX INFO: renamed from: o */
    private C1111a f1891o;

    /* JADX INFO: renamed from: p */
    private Camera2Wrapper f1892p;

    /* JADX INFO: renamed from: q */
    private Context f1893q;

    /* JADX INFO: renamed from: r */
    private SurfaceView f1894r;

    /* JADX INFO: renamed from: s */
    private boolean f1895s;

    /* JADX INFO: renamed from: t */
    private C1135q f1896t;

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$a */
    class C1111a implements SensorEventListener {
        C1111a() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$b */
    static final class EnumC1112b {

        /* JADX INFO: renamed from: a */
        public static final int f1950a = 1;

        /* JADX INFO: renamed from: b */
        public static final int f1951b = 2;

        /* JADX INFO: renamed from: c */
        public static final int f1952c = 3;

        /* JADX INFO: renamed from: d */
        private static final /* synthetic */ int[] f1953d = {f1950a, f1951b, f1952c};
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$c */
    private class C1113c extends PhoneStateListener {
        private C1113c() {
        }

        /* synthetic */ C1113c(UnityPlayer unityPlayer, byte b) {
            this();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onCallStateChanged(int i, String str) {
            UnityPlayer.this.nativeMuteMasterAudio(i == 1);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$d */
    enum EnumC1114d {
        PAUSE,
        RESUME,
        QUIT,
        SURFACE_LOST,
        SURFACE_ACQUIRED,
        FOCUS_LOST,
        FOCUS_GAINED,
        NEXT_FRAME
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$e */
    private class C1115e extends Thread {

        /* JADX INFO: renamed from: a */
        Handler f1964a;

        /* JADX INFO: renamed from: b */
        boolean f1965b;

        /* JADX INFO: renamed from: c */
        boolean f1966c;

        /* JADX INFO: renamed from: d */
        int f1967d;

        /* JADX INFO: renamed from: e */
        int f1968e;

        private C1115e() {
            this.f1965b = false;
            this.f1966c = false;
            this.f1967d = EnumC1112b.f1951b;
            this.f1968e = 5;
        }

        /* synthetic */ C1115e(UnityPlayer unityPlayer, byte b) {
            this();
        }

        /* JADX INFO: renamed from: a */
        private void m1871a(EnumC1114d enumC1114d) {
            Handler handler = this.f1964a;
            if (handler != null) {
                Message.obtain(handler, 2269, enumC1114d).sendToTarget();
            }
        }

        /* JADX INFO: renamed from: a */
        public final void m1872a() {
            m1871a(EnumC1114d.QUIT);
        }

        /* JADX INFO: renamed from: a */
        public final void m1873a(Runnable runnable) {
            if (this.f1964a == null) {
                return;
            }
            m1871a(EnumC1114d.PAUSE);
            Message.obtain(this.f1964a, runnable).sendToTarget();
        }

        /* JADX INFO: renamed from: b */
        public final void m1874b() {
            m1871a(EnumC1114d.RESUME);
        }

        /* JADX INFO: renamed from: b */
        public final void m1875b(Runnable runnable) {
            if (this.f1964a == null) {
                return;
            }
            m1871a(EnumC1114d.SURFACE_LOST);
            Message.obtain(this.f1964a, runnable).sendToTarget();
        }

        /* JADX INFO: renamed from: c */
        public final void m1876c() {
            m1871a(EnumC1114d.FOCUS_GAINED);
        }

        /* JADX INFO: renamed from: c */
        public final void m1877c(Runnable runnable) {
            Handler handler = this.f1964a;
            if (handler == null) {
                return;
            }
            Message.obtain(handler, runnable).sendToTarget();
            m1871a(EnumC1114d.SURFACE_ACQUIRED);
        }

        /* JADX INFO: renamed from: d */
        public final void m1878d() {
            m1871a(EnumC1114d.FOCUS_LOST);
        }

        /* JADX INFO: renamed from: d */
        public final void m1879d(Runnable runnable) {
            Handler handler = this.f1964a;
            if (handler != null) {
                Message.obtain(handler, runnable).sendToTarget();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.f1964a = new Handler(new Handler.Callback() { // from class: com.unity3d.player.UnityPlayer.e.1
                /* JADX INFO: renamed from: a */
                private void m1880a() {
                    if (C1115e.this.f1967d == EnumC1112b.f1952c && C1115e.this.f1966c) {
                        UnityPlayer.this.nativeFocusChanged(true);
                        C1115e.this.f1967d = EnumC1112b.f1950a;
                    }
                }

                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    if (message.what != 2269) {
                        return false;
                    }
                    EnumC1114d enumC1114d = (EnumC1114d) message.obj;
                    if (enumC1114d == EnumC1114d.NEXT_FRAME) {
                        return true;
                    }
                    if (enumC1114d == EnumC1114d.QUIT) {
                        Looper.myLooper().quit();
                    } else if (enumC1114d == EnumC1114d.RESUME) {
                        C1115e.this.f1965b = true;
                    } else if (enumC1114d == EnumC1114d.PAUSE) {
                        C1115e.this.f1965b = false;
                    } else if (enumC1114d == EnumC1114d.SURFACE_LOST) {
                        C1115e.this.f1966c = false;
                    } else {
                        if (enumC1114d == EnumC1114d.SURFACE_ACQUIRED) {
                            C1115e.this.f1966c = true;
                        } else if (enumC1114d == EnumC1114d.FOCUS_LOST) {
                            if (C1115e.this.f1967d == EnumC1112b.f1950a) {
                                UnityPlayer.this.nativeFocusChanged(false);
                            }
                            C1115e.this.f1967d = EnumC1112b.f1951b;
                        } else if (enumC1114d == EnumC1114d.FOCUS_GAINED) {
                            C1115e.this.f1967d = EnumC1112b.f1952c;
                        }
                        m1880a();
                    }
                    return true;
                }
            });
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.unity3d.player.UnityPlayer.e.2
                @Override // android.os.MessageQueue.IdleHandler
                public final boolean queueIdle() {
                    UnityPlayer.this.executeGLThreadJobs();
                    if (!C1115e.this.f1965b || !C1115e.this.f1966c) {
                        return true;
                    }
                    if (C1115e.this.f1968e >= 0) {
                        if (C1115e.this.f1968e == 0 && UnityPlayer.this.m1854k()) {
                            UnityPlayer.this.m1820a();
                        }
                        C1115e.this.f1968e--;
                    }
                    if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                        UnityPlayer.this.m1842e();
                    }
                    Message.obtain(C1115e.this.f1964a, 2269, EnumC1114d.NEXT_FRAME).sendToTarget();
                    return true;
                }
            });
            Looper.loop();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$f */
    private abstract class AbstractRunnableC1116f implements Runnable {
        private AbstractRunnableC1116f() {
        }

        /* synthetic */ AbstractRunnableC1116f(UnityPlayer unityPlayer, byte b) {
            this();
        }

        /* JADX INFO: renamed from: a */
        public abstract void mo1869a();

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.isFinishing()) {
                return;
            }
            mo1869a();
        }
    }

    static {
        new C1131m().m1943a();
        try {
            System.loadLibrary("main");
        } catch (UnsatisfiedLinkError e) {
            C1125g.Log(6, "Failed to load 'libmain.so', the application will terminate.");
            throw e;
        }
    }

    public UnityPlayer(Context context) {
        super(context);
        this.f1879c = -1;
        byte b = 0;
        this.f1880d = false;
        this.f1881e = true;
        this.f1882f = new C1132n();
        this.f1883g = new ConcurrentLinkedQueue();
        this.f1884h = null;
        this.f1877a = new C1115e(this, b);
        this.f1885i = false;
        this.f1886j = new C1113c(this, b);
        this.f1890n = null;
        this.f1891o = new C1111a();
        this.f1892p = null;
        this.f1878b = null;
        if (context instanceof Activity) {
            currentActivity = (Activity) context;
            this.f1879c = currentActivity.getRequestedOrientation();
        }
        m1822a(currentActivity);
        this.f1893q = context;
        if (currentActivity != null && m1854k()) {
            this.f1889m = new C1130l(this.f1893q, C1130l.a.m1942a()[getSplashMode()]);
            addView(this.f1889m);
        }
        m1823a(this.f1893q.getApplicationInfo());
        if (!C1132n.m1946c()) {
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.f1893q).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.unity3d.player.UnityPlayer.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    UnityPlayer.this.m1842e();
                }
            }).setMessage("Your hardware does not support this application, sorry!").create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.show();
            return;
        }
        initJni(context);
        this.f1882f.m1949c(true);
        this.f1894r = m1837c();
        this.f1894r.setContentDescription(m1819a(context));
        addView(this.f1894r);
        bringChildToFront(this.f1889m);
        this.f1895s = false;
        nativeInitWebRequest(UnityWebRequest.class);
        m1857m();
        this.f1887k = (TelephonyManager) this.f1893q.getSystemService("phone");
        this.f1888l = (ClipboardManager) this.f1893q.getSystemService("clipboard");
        this.f1892p = new Camera2Wrapper(this.f1893q);
        this.f1877a.start();
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (C1132n.m1946c()) {
            try {
                nativeUnitySendMessage(str, str2, str3.getBytes("UTF-8"));
                return;
            } catch (UnsupportedEncodingException unused) {
                return;
            }
        }
        C1125g.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
    }

    /* JADX INFO: renamed from: a */
    private static String m1819a(Context context) {
        return context.getResources().getString(context.getResources().getIdentifier("game_view_content_description", "string", context.getPackageName()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1820a() {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.17
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.removeView(unityPlayer.f1889m);
                UnityPlayer.m1843f(UnityPlayer.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1821a(int i, Surface surface) {
        if (this.f1880d) {
            return;
        }
        m1836b(0, surface);
    }

    /* JADX INFO: renamed from: a */
    private static void m1822a(Activity activity) {
        View decorView;
        if (activity == null || !activity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false) || activity.getWindow() == null || (decorView = activity.getWindow().getDecorView()) == null) {
            return;
        }
        decorView.setSystemUiVisibility(7);
    }

    /* JADX INFO: renamed from: a */
    private static void m1823a(ApplicationInfo applicationInfo) {
        if (NativeLoader.load(applicationInfo.nativeLibraryDir)) {
            C1132n.m1944a();
        } else {
            C1125g.Log(6, "NativeLoader.load failure, Unity libraries were not loaded.");
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1824a(View view, View view2) {
        boolean z;
        if (this.f1882f.m1951d()) {
            z = false;
        } else {
            pause();
            z = true;
        }
        if (view != null) {
            ViewParent parent = view.getParent();
            if (!(parent instanceof UnityPlayer) || ((UnityPlayer) parent) != this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
                addView(view);
                bringChildToFront(view);
                view.setVisibility(0);
            }
        }
        if (view2 != null && view2.getParent() == this) {
            view2.setVisibility(8);
            removeView(view2);
        }
        if (z) {
            resume();
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1825a(AbstractRunnableC1116f abstractRunnableC1116f) {
        if (isFinishing()) {
            return;
        }
        m1834b(abstractRunnableC1116f);
    }

    /* JADX INFO: renamed from: b */
    private void m1834b(Runnable runnable) {
        if (C1132n.m1946c()) {
            if (Thread.currentThread() == this.f1877a) {
                runnable.run();
            } else {
                this.f1883g.add(runnable);
            }
        }
    }

    /* JADX INFO: renamed from: b */
    private static boolean m1835b() {
        if (currentActivity == null) {
            return false;
        }
        TypedValue typedValue = new TypedValue();
        return currentActivity.getTheme().resolveAttribute(R.attr.windowIsTranslucent, typedValue, true) && typedValue.type == 18 && typedValue.data != 0;
    }

    /* JADX INFO: renamed from: b */
    private boolean m1836b(final int i, final Surface surface) {
        if (!C1132n.m1946c() || !this.f1882f.m1952e()) {
            return false;
        }
        final Semaphore semaphore = new Semaphore(0);
        Runnable runnable = new Runnable() { // from class: com.unity3d.player.UnityPlayer.20
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.nativeRecreateGfxState(i, surface);
                semaphore.release();
            }
        };
        if (i != 0) {
            runnable.run();
        } else if (surface == null) {
            this.f1877a.m1875b(runnable);
        } else {
            this.f1877a.m1877c(runnable);
        }
        if (surface != null || i != 0) {
            return true;
        }
        try {
            if (semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                return true;
            }
            C1125g.Log(5, "Timeout while trying detaching primary window.");
            return true;
        } catch (InterruptedException unused) {
            C1125g.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public SurfaceView m1837c() {
        SurfaceView surfaceView = new SurfaceView(this.f1893q);
        if (m1835b()) {
            surfaceView.getHolder().setFormat(-3);
            surfaceView.setZOrderOnTop(true);
        } else {
            surfaceView.getHolder().setFormat(-1);
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.unity3d.player.UnityPlayer.18
            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                UnityPlayer.this.m1821a(0, surfaceHolder.getSurface());
                UnityPlayer.this.m1839d();
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.m1821a(0, surfaceHolder.getSurface());
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.m1821a(0, (Surface) null);
            }
        });
        surfaceView.setFocusable(true);
        surfaceView.setFocusableInTouchMode(true);
        return surfaceView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public void m1839d() {
        if (C1132n.m1946c() && this.f1882f.m1952e()) {
            this.f1877a.m1879d(new Runnable() { // from class: com.unity3d.player.UnityPlayer.19
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeSendSurfaceChangedEvent();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e */
    public void m1842e() {
        Context context = this.f1893q;
        if (!(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        ((Activity) this.f1893q).finish();
    }

    /* JADX INFO: renamed from: f */
    static /* synthetic */ C1130l m1843f(UnityPlayer unityPlayer) {
        unityPlayer.f1889m = null;
        return null;
    }

    /* JADX INFO: renamed from: f */
    private void m1844f() {
        reportSoftInputStr(null, 1, true);
        if (this.f1882f.m1954g()) {
            if (C1132n.m1946c()) {
                final Semaphore semaphore = new Semaphore(0);
                this.f1877a.m1873a(isFinishing() ? new Runnable() { // from class: com.unity3d.player.UnityPlayer.22
                    @Override // java.lang.Runnable
                    public final void run() {
                        UnityPlayer.this.m1845g();
                        semaphore.release();
                    }
                } : new Runnable() { // from class: com.unity3d.player.UnityPlayer.23
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (!UnityPlayer.this.nativePause()) {
                            semaphore.release();
                            return;
                        }
                        UnityPlayer.m1858m(UnityPlayer.this);
                        UnityPlayer.this.m1845g();
                        semaphore.release(2);
                    }
                });
                try {
                    if (!semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                        C1125g.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException unused) {
                    C1125g.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore.drainPermits() > 0) {
                    destroy();
                }
            }
            this.f1882f.m1950d(false);
            this.f1882f.m1948b(true);
            if (this.f1885i) {
                this.f1887k.listen(this.f1886j, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: g */
    public void m1845g() {
        nativeDone();
        this.f1882f.m1949c(false);
    }

    /* JADX INFO: renamed from: h */
    private void m1847h() {
        if (this.f1882f.m1953f()) {
            this.f1882f.m1950d(true);
            m1834b(new Runnable() { // from class: com.unity3d.player.UnityPlayer.3
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeResume();
                }
            });
            this.f1877a.m1874b();
        }
    }

    /* JADX INFO: renamed from: i */
    private static void m1849i() {
        if (C1132n.m1946c()) {
            if (!NativeLoader.unload()) {
                throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
            }
            C1132n.m1945b();
        }
    }

    private final native void initJni(Context context);

    /* JADX INFO: renamed from: j */
    private ApplicationInfo m1851j() {
        return this.f1893q.getPackageManager().getApplicationInfo(this.f1893q.getPackageName(), 128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: k */
    public boolean m1854k() {
        try {
            return m1851j().metaData.getBoolean("unity.splash-enable");
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: renamed from: l */
    private boolean m1855l() {
        try {
            return m1851j().metaData.getBoolean("unity.tango-enable");
        } catch (Exception unused) {
            return false;
        }
    }

    protected static boolean loadLibraryStatic(String str) {
        StringBuilder sb;
        try {
            System.loadLibrary(str);
            return true;
        } catch (Exception e) {
            sb = new StringBuilder("Unknown error ");
            sb.append(e);
            C1125g.Log(6, sb.toString());
            return false;
        } catch (UnsatisfiedLinkError unused) {
            sb = new StringBuilder("Unable to find ");
            sb.append(str);
            C1125g.Log(6, sb.toString());
            return false;
        }
    }

    /* JADX INFO: renamed from: m */
    private void m1857m() {
        Context context = this.f1893q;
        if (context instanceof Activity) {
            ((Activity) context).getWindow().setFlags(1024, 1024);
        }
    }

    /* JADX INFO: renamed from: m */
    static /* synthetic */ boolean m1858m(UnityPlayer unityPlayer) {
        unityPlayer.f1895s = true;
        return true;
    }

    private final native void nativeDone();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeFocusChanged(boolean z);

    private final native void nativeInitWebRequest(Class cls);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeIsAutorotationOn();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeLowMemory();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeMuteMasterAudio(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativePause();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeRecreateGfxState(int i, Surface surface);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeRender();

    private final native void nativeRestartActivityIndicator();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeResume();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSendSurfaceChangedEvent();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputSelection(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputString(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputCanceled();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputClosed();

    private final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, byte[] bArr);

    /* JADX INFO: renamed from: t */
    static /* synthetic */ C1135q m1865t(UnityPlayer unityPlayer) {
        unityPlayer.f1896t = null;
        return null;
    }

    /* JADX INFO: renamed from: a */
    final void m1868a(Runnable runnable) {
        Context context = this.f1893q;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            C1125g.Log(5, "Not running Unity from an Activity; ignored...");
        }
    }

    protected void addPhoneCallListener() {
        this.f1885i = true;
        this.f1887k.listen(this.f1886j, 32);
    }

    @Override // com.unity3d.player.InterfaceC1124f
    public boolean addViewToPlayer(View view, boolean z) {
        m1824a(view, z ? this.f1894r : null);
        boolean z2 = true;
        boolean z3 = view.getParent() == this;
        boolean z4 = z && this.f1894r.getParent() == null;
        boolean z5 = this.f1894r.getParent() == this;
        if (!z3 || (!z4 && !z5)) {
            z2 = false;
        }
        if (!z2) {
            if (!z3) {
                C1125g.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z4 && !z5) {
                C1125g.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z2;
    }

    public void configurationChanged(Configuration configuration) {
        SurfaceView surfaceView = this.f1894r;
        if (surfaceView instanceof SurfaceView) {
            surfaceView.getHolder().setSizeFromLayout();
        }
        C1135q c1135q = this.f1896t;
        if (c1135q != null) {
            c1135q.m1981c();
        }
        GoogleVrProxy googleVrProxyM1790b = GoogleVrApi.m1790b();
        if (googleVrProxyM1790b != null) {
            googleVrProxyM1790b.m1805c();
        }
    }

    public void destroy() {
        if (GoogleVrApi.m1790b() != null) {
            GoogleVrApi.m1788a();
        }
        Camera2Wrapper camera2Wrapper = this.f1892p;
        if (camera2Wrapper != null) {
            camera2Wrapper.m1785a();
            this.f1892p = null;
        }
        this.f1895s = true;
        if (!this.f1882f.m1951d()) {
            pause();
        }
        this.f1877a.m1872a();
        try {
            this.f1877a.join(4000L);
        } catch (InterruptedException unused) {
            this.f1877a.interrupt();
        }
        BroadcastReceiver broadcastReceiver = this.f1884h;
        if (broadcastReceiver != null) {
            this.f1893q.unregisterReceiver(broadcastReceiver);
        }
        this.f1884h = null;
        if (C1132n.m1946c()) {
            removeAllViews();
        }
        kill();
        m1849i();
    }

    protected void disableLogger() {
        C1125g.f2040a = true;
    }

    public boolean displayChanged(int i, Surface surface) {
        if (i == 0) {
            this.f1880d = surface != null;
            m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.21
                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.f1880d) {
                        UnityPlayer unityPlayer = UnityPlayer.this;
                        unityPlayer.removeView(unityPlayer.f1894r);
                    } else {
                        UnityPlayer unityPlayer2 = UnityPlayer.this;
                        unityPlayer2.addView(unityPlayer2.f1894r);
                    }
                }
            });
        }
        return m1836b(i, surface);
    }

    protected void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.f1883g.poll();
            if (runnable == null) {
                return;
            } else {
                runnable.run();
            }
        }
    }

    protected String getClipboardText() {
        ClipData primaryClip = this.f1888l.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).coerceToText(this.f1893q).toString() : "";
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    protected int getSplashMode() {
        try {
            return m1851j().metaData.getInt("unity.splash-mode");
        } catch (Exception unused) {
            return 0;
        }
    }

    public View getView() {
        return this;
    }

    protected void hideSoftInput() {
        final Runnable runnable = new Runnable() { // from class: com.unity3d.player.UnityPlayer.5
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.f1878b != null) {
                    UnityPlayer.this.f1878b.dismiss();
                    UnityPlayer.this.f1878b = null;
                }
            }
        };
        if (C1128j.f2042b) {
            m1825a(new AbstractRunnableC1116f() { // from class: com.unity3d.player.UnityPlayer.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(UnityPlayer.this, (byte) 0);
                }

                @Override // com.unity3d.player.UnityPlayer.AbstractRunnableC1116f
                /* JADX INFO: renamed from: a */
                public final void mo1869a() {
                    UnityPlayer.this.m1868a(runnable);
                }
            });
        } else {
            m1868a(runnable);
        }
    }

    public void init(int i, boolean z) {
    }

    protected boolean initializeGoogleAr() {
        if (this.f1890n != null || currentActivity == null || !m1855l()) {
            return false;
        }
        this.f1890n = new GoogleARCoreApi();
        this.f1890n.initializeARCore(currentActivity);
        if (this.f1882f.m1951d()) {
            return false;
        }
        this.f1890n.resumeARCore();
        return false;
    }

    protected boolean initializeGoogleVr() {
        final GoogleVrProxy googleVrProxyM1790b = GoogleVrApi.m1790b();
        if (googleVrProxyM1790b == null) {
            GoogleVrApi.m1789a(this);
            googleVrProxyM1790b = GoogleVrApi.m1790b();
            if (googleVrProxyM1790b == null) {
                C1125g.Log(6, "Unable to create Google VR subsystem.");
                return false;
            }
        }
        final Semaphore semaphore = new Semaphore(0);
        final Runnable runnable = new Runnable() { // from class: com.unity3d.player.UnityPlayer.13
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.injectEvent(new KeyEvent(0, 4));
                UnityPlayer.this.injectEvent(new KeyEvent(1, 4));
            }
        };
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.14
            @Override // java.lang.Runnable
            public final void run() {
                if (!googleVrProxyM1790b.m1803a(UnityPlayer.currentActivity, UnityPlayer.this.f1893q, UnityPlayer.this.m1837c(), runnable)) {
                    C1125g.Log(6, "Unable to initialize Google VR subsystem.");
                }
                if (UnityPlayer.currentActivity != null) {
                    googleVrProxyM1790b.m1801a(UnityPlayer.currentActivity.getIntent());
                }
                semaphore.release();
            }
        });
        try {
            if (semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                return googleVrProxyM1790b.m1802a();
            }
            C1125g.Log(5, "Timeout while trying to initialize Google VR.");
            return false;
        } catch (InterruptedException e) {
            C1125g.Log(5, "UI thread was interrupted while initializing Google VR. " + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean injectEvent(InputEvent inputEvent) {
        if (C1132n.m1946c()) {
            return nativeInjectEvent(inputEvent);
        }
        return false;
    }

    protected boolean isFinishing() {
        if (!this.f1895s) {
            Context context = this.f1893q;
            boolean z = (context instanceof Activity) && ((Activity) context).isFinishing();
            this.f1895s = z;
            if (!z) {
                return false;
            }
        }
        return true;
    }

    protected void kill() {
        Process.killProcess(Process.myPid());
    }

    protected boolean loadLibrary(String str) {
        return loadLibraryStatic(str);
    }

    public void lowMemory() {
        if (C1132n.m1946c()) {
            m1834b(new Runnable() { // from class: com.unity3d.player.UnityPlayer.2
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeLowMemory();
                }
            });
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public void pause() {
        GoogleARCoreApi googleARCoreApi = this.f1890n;
        if (googleARCoreApi != null) {
            googleARCoreApi.pauseARCore();
        }
        C1135q c1135q = this.f1896t;
        if (c1135q != null) {
            c1135q.m1978a();
        }
        GoogleVrProxy googleVrProxyM1790b = GoogleVrApi.m1790b();
        if (googleVrProxyM1790b != null) {
            googleVrProxyM1790b.pauseGvrLayout();
        }
        m1844f();
    }

    public void quit() {
        destroy();
    }

    @Override // com.unity3d.player.InterfaceC1124f
    public void removeViewFromPlayer(View view) {
        m1824a(this.f1894r, view);
        boolean z = view.getParent() == null;
        boolean z2 = this.f1894r.getParent() == this;
        if (z && z2) {
            return;
        }
        if (!z) {
            C1125g.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
        }
        if (z2) {
            return;
        }
        C1125g.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
    }

    @Override // com.unity3d.player.InterfaceC1124f
    public void reportError(String str, String str2) {
        C1125g.Log(6, str + ": " + str2);
    }

    protected void reportSoftInputSelection(final int i, final int i2) {
        m1825a(new AbstractRunnableC1116f() { // from class: com.unity3d.player.UnityPlayer.12
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.AbstractRunnableC1116f
            /* JADX INFO: renamed from: a */
            public final void mo1869a() {
                UnityPlayer.this.nativeSetInputSelection(i, i2);
            }
        });
    }

    protected void reportSoftInputStr(final String str, final int i, final boolean z) {
        if (i == 1) {
            hideSoftInput();
        }
        m1825a(new AbstractRunnableC1116f() { // from class: com.unity3d.player.UnityPlayer.11
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.AbstractRunnableC1116f
            /* JADX INFO: renamed from: a */
            public final void mo1869a() {
                if (z) {
                    UnityPlayer.this.nativeSoftInputCanceled();
                } else {
                    String str2 = str;
                    if (str2 != null) {
                        UnityPlayer.this.nativeSetInputString(str2);
                    }
                }
                if (i == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    protected void requestUserAuthorization(String str) {
        if (!C1128j.f2043c || str == null || str.isEmpty() || currentActivity == null) {
            return;
        }
        C1128j.f2044d.mo1927a(currentActivity, str);
    }

    public void resume() {
        GoogleARCoreApi googleARCoreApi = this.f1890n;
        if (googleARCoreApi != null) {
            googleARCoreApi.resumeARCore();
        }
        this.f1882f.m1948b(false);
        C1135q c1135q = this.f1896t;
        if (c1135q != null) {
            c1135q.m1980b();
        }
        m1847h();
        nativeRestartActivityIndicator();
        GoogleVrProxy googleVrProxyM1790b = GoogleVrApi.m1790b();
        if (googleVrProxyM1790b != null) {
            googleVrProxyM1790b.m1804b();
        }
    }

    protected void setCharacterLimit(final int i) {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.8
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.f1878b != null) {
                    UnityPlayer.this.f1878b.m1938a(i);
                }
            }
        });
    }

    protected void setClipboardText(String str) {
        this.f1888l.setPrimaryClip(ClipData.newPlainText("Text", str));
    }

    protected void setHideInputField(final boolean z) {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.9
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.f1878b != null) {
                    UnityPlayer.this.f1878b.m1941a(z);
                }
            }
        });
    }

    protected void setSelection(final int i, final int i2) {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.10
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.f1878b != null) {
                    UnityPlayer.this.f1878b.m1939a(i, i2);
                }
            }
        });
    }

    protected void setSoftInputStr(final String str) {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.7
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.f1878b == null || str == null) {
                    return;
                }
                UnityPlayer.this.f1878b.m1940a(str);
            }
        });
    }

    protected void showSoftInput(final String str, final int i, final boolean z, final boolean z2, final boolean z3, final boolean z4, final String str2, final int i2, final boolean z5) {
        m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.4
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.f1878b = new DialogC1129k(unityPlayer.f1893q, this, str, i, z, z2, z3, str2, i2, z5);
                UnityPlayer.this.f1878b.show();
            }
        });
    }

    protected boolean showVideoPlayer(String str, int i, int i2, int i3, boolean z, int i4, int i5) {
        if (this.f1896t == null) {
            this.f1896t = new C1135q(this);
        }
        boolean zM1979a = this.f1896t.m1979a(this.f1893q, str, i, i2, i3, z, i4, i5, new C1135q.a() { // from class: com.unity3d.player.UnityPlayer.15
            @Override // com.unity3d.player.C1135q.a
            /* JADX INFO: renamed from: a */
            public final void mo1870a() {
                UnityPlayer.m1865t(UnityPlayer.this);
            }
        });
        if (zM1979a) {
            m1868a(new Runnable() { // from class: com.unity3d.player.UnityPlayer.16
                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.nativeIsAutorotationOn() && (UnityPlayer.this.f1893q instanceof Activity)) {
                        ((Activity) UnityPlayer.this.f1893q).setRequestedOrientation(UnityPlayer.this.f1879c);
                    }
                }
            });
        }
        return zM1979a;
    }

    protected boolean skipPermissionsDialog() {
        if (!C1128j.f2043c || currentActivity == null) {
            return false;
        }
        return C1128j.f2044d.mo1928a(currentActivity);
    }

    public void start() {
    }

    public void stop() {
    }

    protected void toggleGyroscopeSensor(boolean z) {
        SensorManager sensorManager = (SensorManager) this.f1893q.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z) {
            sensorManager.registerListener(this.f1891o, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.f1891o);
        }
    }

    public void windowFocusChanged(boolean z) {
        this.f1882f.m1947a(z);
        if (this.f1882f.m1952e()) {
            if (z && this.f1878b != null) {
                nativeSoftInputLostFocus();
                reportSoftInputStr(null, 1, false);
            }
            if (z) {
                this.f1877a.m1876c();
            } else {
                this.f1877a.m1878d();
            }
            m1847h();
        }
    }
}
