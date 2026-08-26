package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: renamed from: com.unity3d.player.q */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C1135q {

    /* JADX INFO: renamed from: a */
    private UnityPlayer f2104a;

    /* JADX INFO: renamed from: c */
    private a f2106c;

    /* JADX INFO: renamed from: b */
    private Context f2105b = null;

    /* JADX INFO: renamed from: d */
    private final Semaphore f2107d = new Semaphore(0);

    /* JADX INFO: renamed from: e */
    private final Lock f2108e = new ReentrantLock();

    /* JADX INFO: renamed from: f */
    private SurfaceHolderCallbackC1134p f2109f = null;

    /* JADX INFO: renamed from: g */
    private int f2110g = 2;

    /* JADX INFO: renamed from: h */
    private boolean f2111h = false;

    /* JADX INFO: renamed from: i */
    private boolean f2112i = false;

    /* JADX INFO: renamed from: com.unity3d.player.q$1, reason: invalid class name */
    final class AnonymousClass1 implements Runnable {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ String f2113a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ int f2114b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ int f2115c;

        /* JADX INFO: renamed from: d */
        final /* synthetic */ int f2116d;

        /* JADX INFO: renamed from: e */
        final /* synthetic */ boolean f2117e;

        /* JADX INFO: renamed from: f */
        final /* synthetic */ long f2118f;

        /* JADX INFO: renamed from: g */
        final /* synthetic */ long f2119g;

        AnonymousClass1(String str, int i, int i2, int i3, boolean z, long j, long j2) {
            this.f2113a = str;
            this.f2114b = i;
            this.f2115c = i2;
            this.f2116d = i3;
            this.f2117e = z;
            this.f2118f = j;
            this.f2119g = j2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (C1135q.this.f2109f != null) {
                C1125g.Log(5, "Video already playing");
                C1135q.this.f2110g = 2;
                C1135q.this.f2107d.release();
            } else {
                C1135q c1135q = C1135q.this;
                c1135q.f2109f = new SurfaceHolderCallbackC1134p(c1135q.f2105b, this.f2113a, this.f2114b, this.f2115c, this.f2116d, this.f2117e, this.f2118f, this.f2119g, new SurfaceHolderCallbackC1134p.a() { // from class: com.unity3d.player.q.1.1
                    @Override // com.unity3d.player.SurfaceHolderCallbackC1134p.a
                    /* JADX INFO: renamed from: a */
                    public final void mo1965a(int i) {
                        C1135q.this.f2108e.lock();
                        C1135q.this.f2110g = i;
                        if (i == 3 && C1135q.this.f2112i) {
                            C1135q.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.q.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    C1135q.this.m1973d();
                                    C1135q.this.f2104a.resume();
                                }
                            });
                        }
                        if (i != 0) {
                            C1135q.this.f2107d.release();
                        }
                        C1135q.this.f2108e.unlock();
                    }
                });
                if (C1135q.this.f2109f != null) {
                    C1135q.this.f2104a.addView(C1135q.this.f2109f);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.q$a */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1870a();
    }

    C1135q(UnityPlayer unityPlayer) {
        this.f2104a = null;
        this.f2104a = unityPlayer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public void m1973d() {
        SurfaceHolderCallbackC1134p surfaceHolderCallbackC1134p = this.f2109f;
        if (surfaceHolderCallbackC1134p != null) {
            this.f2104a.removeViewFromPlayer(surfaceHolderCallbackC1134p);
            this.f2112i = false;
            this.f2109f.destroyPlayer();
            this.f2109f = null;
            a aVar = this.f2106c;
            if (aVar != null) {
                aVar.mo1870a();
            }
        }
    }

    /* JADX INFO: renamed from: h */
    static /* synthetic */ boolean m1977h(C1135q c1135q) {
        c1135q.f2112i = true;
        return true;
    }

    /* JADX INFO: renamed from: a */
    public final void m1978a() {
        this.f2108e.lock();
        SurfaceHolderCallbackC1134p surfaceHolderCallbackC1134p = this.f2109f;
        if (surfaceHolderCallbackC1134p != null) {
            if (this.f2110g == 0) {
                surfaceHolderCallbackC1134p.CancelOnPrepare();
            } else if (this.f2112i) {
                this.f2111h = surfaceHolderCallbackC1134p.m1964a();
                if (!this.f2111h) {
                    this.f2109f.pause();
                }
            }
        }
        this.f2108e.unlock();
    }

    /* JADX INFO: renamed from: a */
    public final boolean m1979a(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, a aVar) {
        this.f2108e.lock();
        this.f2106c = aVar;
        this.f2105b = context;
        this.f2107d.drainPermits();
        this.f2110g = 2;
        runOnUiThread(new AnonymousClass1(str, i, i2, i3, z, j, j2));
        boolean z2 = false;
        try {
            this.f2108e.unlock();
            this.f2107d.acquire();
            this.f2108e.lock();
            if (this.f2110g != 2) {
                z2 = true;
            }
        } catch (InterruptedException unused) {
        }
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.q.2
            @Override // java.lang.Runnable
            public final void run() {
                C1135q.this.f2104a.pause();
            }
        });
        runOnUiThread((!z2 || this.f2110g == 3) ? new Runnable() { // from class: com.unity3d.player.q.4
            @Override // java.lang.Runnable
            public final void run() {
                C1135q.this.m1973d();
                C1135q.this.f2104a.resume();
            }
        } : new Runnable() { // from class: com.unity3d.player.q.3
            @Override // java.lang.Runnable
            public final void run() {
                if (C1135q.this.f2109f != null) {
                    C1135q.this.f2104a.addViewToPlayer(C1135q.this.f2109f, true);
                    C1135q.m1977h(C1135q.this);
                    C1135q.this.f2109f.requestFocus();
                }
            }
        });
        this.f2108e.unlock();
        return z2;
    }

    /* JADX INFO: renamed from: b */
    public final void m1980b() {
        this.f2108e.lock();
        SurfaceHolderCallbackC1134p surfaceHolderCallbackC1134p = this.f2109f;
        if (surfaceHolderCallbackC1134p != null && this.f2112i && !this.f2111h) {
            surfaceHolderCallbackC1134p.start();
        }
        this.f2108e.unlock();
    }

    /* JADX INFO: renamed from: c */
    public final void m1981c() {
        this.f2108e.lock();
        SurfaceHolderCallbackC1134p surfaceHolderCallbackC1134p = this.f2109f;
        if (surfaceHolderCallbackC1134p != null) {
            surfaceHolderCallbackC1134p.updateVideoLayout();
        }
        this.f2108e.unlock();
    }

    protected final void runOnUiThread(Runnable runnable) {
        Context context = this.f2105b;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            C1125g.Log(5, "Not running from an Activity; Ignoring execution request...");
        }
    }
}
