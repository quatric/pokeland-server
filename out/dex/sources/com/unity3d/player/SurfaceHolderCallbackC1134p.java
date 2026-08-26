package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: renamed from: com.unity3d.player.p */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SurfaceHolderCallbackC1134p extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {

    /* JADX INFO: renamed from: a */
    private static boolean f2075a = false;

    /* JADX INFO: renamed from: b */
    private final Context f2076b;

    /* JADX INFO: renamed from: c */
    private final SurfaceView f2077c;

    /* JADX INFO: renamed from: d */
    private final SurfaceHolder f2078d;

    /* JADX INFO: renamed from: e */
    private final String f2079e;

    /* JADX INFO: renamed from: f */
    private final int f2080f;

    /* JADX INFO: renamed from: g */
    private final int f2081g;

    /* JADX INFO: renamed from: h */
    private final boolean f2082h;

    /* JADX INFO: renamed from: i */
    private final long f2083i;

    /* JADX INFO: renamed from: j */
    private final long f2084j;

    /* JADX INFO: renamed from: k */
    private final FrameLayout f2085k;

    /* JADX INFO: renamed from: l */
    private final Display f2086l;

    /* JADX INFO: renamed from: m */
    private int f2087m;

    /* JADX INFO: renamed from: n */
    private int f2088n;

    /* JADX INFO: renamed from: o */
    private int f2089o;

    /* JADX INFO: renamed from: p */
    private int f2090p;

    /* JADX INFO: renamed from: q */
    private MediaPlayer f2091q;

    /* JADX INFO: renamed from: r */
    private MediaController f2092r;

    /* JADX INFO: renamed from: s */
    private boolean f2093s;

    /* JADX INFO: renamed from: t */
    private boolean f2094t;

    /* JADX INFO: renamed from: u */
    private int f2095u;

    /* JADX INFO: renamed from: v */
    private boolean f2096v;

    /* JADX INFO: renamed from: w */
    private boolean f2097w;

    /* JADX INFO: renamed from: x */
    private a f2098x;

    /* JADX INFO: renamed from: y */
    private b f2099y;

    /* JADX INFO: renamed from: z */
    private volatile int f2100z;

    /* JADX INFO: renamed from: com.unity3d.player.p$a */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1965a(int i);
    }

    /* JADX INFO: renamed from: com.unity3d.player.p$b */
    public class b implements Runnable {

        /* JADX INFO: renamed from: b */
        private SurfaceHolderCallbackC1134p f2102b;

        /* JADX INFO: renamed from: c */
        private boolean f2103c = false;

        public b(SurfaceHolderCallbackC1134p surfaceHolderCallbackC1134p) {
            this.f2102b = surfaceHolderCallbackC1134p;
        }

        /* JADX INFO: renamed from: a */
        public final void m1966a() {
            this.f2103c = true;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (this.f2103c) {
                return;
            }
            if (SurfaceHolderCallbackC1134p.f2075a) {
                SurfaceHolderCallbackC1134p.m1960b("Stopping the video player due to timeout.");
            }
            this.f2102b.CancelOnPrepare();
        }
    }

    protected SurfaceHolderCallbackC1134p(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, a aVar) {
        super(context);
        this.f2093s = false;
        this.f2094t = false;
        this.f2095u = 0;
        this.f2096v = false;
        this.f2097w = false;
        this.f2100z = 0;
        this.f2098x = aVar;
        this.f2076b = context;
        this.f2085k = this;
        this.f2077c = new SurfaceView(context);
        this.f2078d = this.f2077c.getHolder();
        this.f2078d.addCallback(this);
        this.f2085k.setBackgroundColor(i);
        this.f2085k.addView(this.f2077c);
        this.f2086l = ((WindowManager) this.f2076b.getSystemService("window")).getDefaultDisplay();
        this.f2079e = str;
        this.f2080f = i2;
        this.f2081g = i3;
        this.f2082h = z;
        this.f2083i = j;
        this.f2084j = j2;
        if (f2075a) {
            m1960b("fileName: " + this.f2079e);
        }
        if (f2075a) {
            m1960b("backgroundColor: " + i);
        }
        if (f2075a) {
            m1960b("controlMode: " + this.f2080f);
        }
        if (f2075a) {
            m1960b("scalingMode: " + this.f2081g);
        }
        if (f2075a) {
            m1960b("isURL: " + this.f2082h);
        }
        if (f2075a) {
            m1960b("videoOffset: " + this.f2083i);
        }
        if (f2075a) {
            m1960b("videoLength: " + this.f2084j);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    /* JADX INFO: renamed from: a */
    private void m1958a(int i) {
        this.f2100z = i;
        a aVar = this.f2098x;
        if (aVar != null) {
            aVar.mo1965a(this.f2100z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m1960b(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    /* JADX INFO: renamed from: c */
    private void m1962c() {
        FileInputStream fileInputStream;
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(this.f2078d);
            if (this.f2096v) {
                return;
            }
            if (f2075a) {
                m1960b("Resuming playback");
            }
            this.f2091q.start();
            return;
        }
        m1958a(0);
        doCleanUp();
        try {
            this.f2091q = new MediaPlayer();
            if (this.f2082h) {
                this.f2091q.setDataSource(this.f2076b, Uri.parse(this.f2079e));
            } else {
                if (this.f2084j != 0) {
                    fileInputStream = new FileInputStream(this.f2079e);
                    this.f2091q.setDataSource(fileInputStream.getFD(), this.f2083i, this.f2084j);
                } else {
                    try {
                        AssetFileDescriptor assetFileDescriptorOpenFd = getResources().getAssets().openFd(this.f2079e);
                        this.f2091q.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                        assetFileDescriptorOpenFd.close();
                    } catch (IOException unused) {
                        fileInputStream = new FileInputStream(this.f2079e);
                        this.f2091q.setDataSource(fileInputStream.getFD());
                        fileInputStream.close();
                    }
                }
                fileInputStream.close();
            }
            this.f2091q.setDisplay(this.f2078d);
            this.f2091q.setScreenOnWhilePlaying(true);
            this.f2091q.setOnBufferingUpdateListener(this);
            this.f2091q.setOnCompletionListener(this);
            this.f2091q.setOnPreparedListener(this);
            this.f2091q.setOnVideoSizeChangedListener(this);
            this.f2091q.setAudioStreamType(3);
            this.f2091q.prepareAsync();
            this.f2099y = new b(this);
            new Thread(this.f2099y).start();
        } catch (Exception e) {
            if (f2075a) {
                m1960b("error: " + e.getMessage() + e);
            }
            m1958a(2);
        }
    }

    /* JADX INFO: renamed from: d */
    private void m1963d() {
        if (isPlaying()) {
            return;
        }
        m1958a(1);
        if (f2075a) {
            m1960b("startVideoPlayback");
        }
        updateVideoLayout();
        if (this.f2096v) {
            return;
        }
        start();
    }

    public final void CancelOnPrepare() {
        m1958a(2);
    }

    /* JADX INFO: renamed from: a */
    final boolean m1964a() {
        return this.f2096v;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekBackward() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekForward() {
        return true;
    }

    protected final void destroyPlayer() {
        if (f2075a) {
            m1960b("destroyPlayer");
        }
        if (!this.f2096v) {
            pause();
        }
        doCleanUp();
    }

    protected final void doCleanUp() {
        b bVar = this.f2099y;
        if (bVar != null) {
            bVar.m1966a();
            this.f2099y = null;
        }
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.f2091q = null;
        }
        this.f2089o = 0;
        this.f2090p = 0;
        this.f2094t = false;
        this.f2093s = false;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getBufferPercentage() {
        if (this.f2082h) {
            return this.f2095u;
        }
        return 100;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getCurrentPosition() {
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getDuration() {
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getDuration();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean isPlaying() {
        boolean z = this.f2094t && this.f2093s;
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return !z;
        }
        return mediaPlayer.isPlaying() || !z;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (f2075a) {
            m1960b("onBufferingUpdate percent:" + i);
        }
        this.f2095u = i;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (f2075a) {
            m1960b("onCompletion called");
        }
        destroyPlayer();
        m1958a(3);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 && (this.f2080f != 2 || i == 0 || keyEvent.isSystem())) {
            MediaController mediaController = this.f2092r;
            return mediaController != null ? mediaController.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
        }
        destroyPlayer();
        m1958a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (f2075a) {
            m1960b("onPrepared called");
        }
        b bVar = this.f2099y;
        if (bVar != null) {
            bVar.m1966a();
            this.f2099y = null;
        }
        int i = this.f2080f;
        if (i == 0 || i == 1) {
            this.f2092r = new MediaController(this.f2076b);
            this.f2092r.setMediaPlayer(this);
            this.f2092r.setAnchorView(this);
            this.f2092r.setEnabled(true);
            Context context = this.f2076b;
            if (context instanceof Activity) {
                this.f2092r.setSystemUiVisibility(((Activity) context).getWindow().getDecorView().getSystemUiVisibility());
            }
            this.f2092r.show();
        }
        this.f2094t = true;
        if (this.f2094t && this.f2093s) {
            m1963d();
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f2080f != 2 || action != 0) {
            MediaController mediaController = this.f2092r;
            return mediaController != null ? mediaController.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        m1958a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (f2075a) {
            m1960b("onVideoSizeChanged called " + i + "x" + i2);
        }
        if (i != 0 && i2 != 0) {
            this.f2093s = true;
            this.f2089o = i;
            this.f2090p = i2;
            if (this.f2094t && this.f2093s) {
                m1963d();
                return;
            }
            return;
        }
        if (f2075a) {
            m1960b("invalid video width(" + i + ") or height(" + i2 + ")");
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void pause() {
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.f2097w) {
            mediaPlayer.pause();
        }
        this.f2096v = true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void seekTo(int i) {
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seekTo(i);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void start() {
        if (f2075a) {
            m1960b("Start");
        }
        MediaPlayer mediaPlayer = this.f2091q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.f2097w) {
            mediaPlayer.start();
        }
        this.f2096v = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (f2075a) {
            m1960b("surfaceChanged called " + i + " " + i2 + "x" + i3);
        }
        if (this.f2087m == i2 && this.f2088n == i3) {
            return;
        }
        this.f2087m = i2;
        this.f2088n = i3;
        if (this.f2097w) {
            updateVideoLayout();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (f2075a) {
            m1960b("surfaceCreated called");
        }
        this.f2097w = true;
        m1962c();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (f2075a) {
            m1960b("surfaceDestroyed called");
        }
        this.f2097w = false;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004f  */
    /* JADX WARN: Code duplicated, block: B:20:0x0053  */
    protected final void updateVideoLayout() {
        if (f2075a) {
            m1960b("updateVideoLayout");
        }
        if (this.f2091q == null) {
            return;
        }
        if (this.f2087m == 0 || this.f2088n == 0) {
            WindowManager windowManager = (WindowManager) this.f2076b.getSystemService("window");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            this.f2087m = displayMetrics.widthPixels;
            this.f2088n = displayMetrics.heightPixels;
        }
        int i = this.f2087m;
        int i2 = this.f2088n;
        if (this.f2093s) {
            int i3 = this.f2089o;
            int i4 = this.f2090p;
            float f = i3 / i4;
            float f2 = i / i2;
            int i5 = this.f2081g;
            if (i5 == 1) {
                if (f2 <= f) {
                    i2 = (int) (i / f);
                } else {
                    i = (int) (i2 * f);
                }
            } else if (i5 == 2) {
                if (f2 >= f) {
                    i2 = (int) (i / f);
                } else {
                    i = (int) (i2 * f);
                }
            } else if (i5 == 0) {
                i = i3;
                i2 = i4;
            }
        } else if (f2075a) {
            m1960b("updateVideoLayout: Video size is not known yet");
        }
        if (this.f2087m == i && this.f2088n == i2) {
            return;
        }
        if (f2075a) {
            m1960b("frameWidth = " + i + "; frameHeight = " + i2);
        }
        this.f2085k.updateViewLayout(this.f2077c, new FrameLayout.LayoutParams(i, i2, 17));
    }
}
