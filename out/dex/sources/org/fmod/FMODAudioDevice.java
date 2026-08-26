package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FMODAudioDevice implements Runnable {

    /* JADX INFO: renamed from: h */
    private static int f2138h = 0;

    /* JADX INFO: renamed from: i */
    private static int f2139i = 1;

    /* JADX INFO: renamed from: j */
    private static int f2140j = 2;

    /* JADX INFO: renamed from: k */
    private static int f2141k = 3;

    /* JADX INFO: renamed from: a */
    private volatile Thread f2142a = null;

    /* JADX INFO: renamed from: b */
    private volatile boolean f2143b = false;

    /* JADX INFO: renamed from: c */
    private AudioTrack f2144c = null;

    /* JADX INFO: renamed from: d */
    private boolean f2145d = false;

    /* JADX INFO: renamed from: e */
    private ByteBuffer f2146e = null;

    /* JADX INFO: renamed from: f */
    private byte[] f2147f = null;

    /* JADX INFO: renamed from: g */
    private volatile RunnableC1271a f2148g;

    private native int fmodGetInfo(int i);

    private native int fmodProcess(ByteBuffer byteBuffer);

    private void releaseAudioTrack() {
        AudioTrack audioTrack = this.f2144c;
        if (audioTrack != null) {
            if (audioTrack.getState() == 1) {
                this.f2144c.stop();
            }
            this.f2144c.release();
            this.f2144c = null;
        }
        this.f2146e = null;
        this.f2147f = null;
        this.f2145d = false;
    }

    public synchronized void close() {
        stop();
    }

    native int fmodProcessMicData(ByteBuffer byteBuffer, int i);

    public boolean isRunning() {
        return this.f2142a != null && this.f2142a.isAlive();
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = 3;
        while (this.f2143b) {
            if (!this.f2145d && i > 0) {
                releaseAudioTrack();
                int iFmodGetInfo = fmodGetInfo(f2138h);
                int iRound = Math.round(AudioTrack.getMinBufferSize(iFmodGetInfo, 3, 2) * 1.1f) & (-4);
                int iFmodGetInfo2 = fmodGetInfo(f2139i);
                int iFmodGetInfo3 = fmodGetInfo(f2140j) * iFmodGetInfo2 * 4;
                this.f2144c = new AudioTrack(3, iFmodGetInfo, 3, 2, iFmodGetInfo3 > iRound ? iFmodGetInfo3 : iRound, 1);
                this.f2145d = this.f2144c.getState() == 1;
                if (this.f2145d) {
                    this.f2146e = ByteBuffer.allocateDirect(iFmodGetInfo2 * 2 * 2);
                    this.f2147f = new byte[this.f2146e.capacity()];
                    this.f2144c.play();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioTrack failed to initialize (status " + this.f2144c.getState() + ")");
                    releaseAudioTrack();
                    i += -1;
                }
            }
            if (this.f2145d) {
                if (fmodGetInfo(f2141k) == 1) {
                    fmodProcess(this.f2146e);
                    ByteBuffer byteBuffer = this.f2146e;
                    byteBuffer.get(this.f2147f, 0, byteBuffer.capacity());
                    this.f2144c.write(this.f2147f, 0, this.f2146e.capacity());
                    this.f2146e.position(0);
                } else {
                    releaseAudioTrack();
                }
            }
        }
        releaseAudioTrack();
    }

    public synchronized void start() {
        if (this.f2142a != null) {
            stop();
        }
        this.f2142a = new Thread(this, "FMODAudioDevice");
        this.f2142a.setPriority(10);
        this.f2143b = true;
        this.f2142a.start();
        if (this.f2148g != null) {
            this.f2148g.m2006b();
        }
    }

    public synchronized int startAudioRecord(int i, int i2, int i3) {
        if (this.f2148g == null) {
            this.f2148g = new RunnableC1271a(this, i, i2);
            this.f2148g.m2006b();
        }
        return this.f2148g.m2005a();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public synchronized void stop() {
        while (this.f2142a != null) {
            this.f2143b = false;
            try {
                this.f2142a.join();
                this.f2142a = null;
            } catch (InterruptedException unused) {
            }
        }
        if (this.f2148g != null) {
            this.f2148g.m2007c();
        }
    }

    public synchronized void stopAudioRecord() {
        if (this.f2148g != null) {
            this.f2148g.m2007c();
            this.f2148g = null;
        }
    }
}
