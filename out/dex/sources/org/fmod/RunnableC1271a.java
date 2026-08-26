package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: renamed from: org.fmod.a */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class RunnableC1271a implements Runnable {

    /* JADX INFO: renamed from: a */
    private final FMODAudioDevice f2149a;

    /* JADX INFO: renamed from: b */
    private final ByteBuffer f2150b;

    /* JADX INFO: renamed from: c */
    private final int f2151c;

    /* JADX INFO: renamed from: d */
    private final int f2152d;

    /* JADX INFO: renamed from: e */
    private final int f2153e = 2;

    /* JADX INFO: renamed from: f */
    private volatile Thread f2154f;

    /* JADX INFO: renamed from: g */
    private volatile boolean f2155g;

    /* JADX INFO: renamed from: h */
    private AudioRecord f2156h;

    /* JADX INFO: renamed from: i */
    private boolean f2157i;

    RunnableC1271a(FMODAudioDevice fMODAudioDevice, int i, int i2) {
        this.f2149a = fMODAudioDevice;
        this.f2151c = i;
        this.f2152d = i2;
        this.f2150b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize(i, i2, 2));
    }

    /* JADX INFO: renamed from: d */
    private void m2004d() {
        AudioRecord audioRecord = this.f2156h;
        if (audioRecord != null) {
            if (audioRecord.getState() == 1) {
                this.f2156h.stop();
            }
            this.f2156h.release();
            this.f2156h = null;
        }
        this.f2150b.position(0);
        this.f2157i = false;
    }

    /* JADX INFO: renamed from: a */
    public final int m2005a() {
        return this.f2150b.capacity();
    }

    /* JADX INFO: renamed from: b */
    public final void m2006b() {
        if (this.f2154f != null) {
            m2007c();
        }
        this.f2155g = true;
        this.f2154f = new Thread(this);
        this.f2154f.start();
    }

    /* JADX INFO: renamed from: c */
    public final void m2007c() {
        while (this.f2154f != null) {
            this.f2155g = false;
            try {
                this.f2154f.join();
                this.f2154f = null;
            } catch (InterruptedException unused) {
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 3;
        while (this.f2155g) {
            if (!this.f2157i && i > 0) {
                m2004d();
                this.f2156h = new AudioRecord(1, this.f2151c, this.f2152d, this.f2153e, this.f2150b.capacity());
                this.f2157i = this.f2156h.getState() == 1;
                if (this.f2157i) {
                    this.f2150b.position(0);
                    this.f2156h.startRecording();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioRecord failed to initialize (status " + this.f2156h.getState() + ")");
                    i += -1;
                    m2004d();
                }
            }
            if (this.f2157i && this.f2156h.getRecordingState() == 3) {
                AudioRecord audioRecord = this.f2156h;
                ByteBuffer byteBuffer = this.f2150b;
                this.f2149a.fmodProcessMicData(this.f2150b, audioRecord.read(byteBuffer, byteBuffer.capacity()));
                this.f2150b.position(0);
            }
        }
        m2004d();
    }
}
