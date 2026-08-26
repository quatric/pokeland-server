package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Camera2Wrapper implements InterfaceC1122d {

    /* JADX INFO: renamed from: a */
    private Context f1837a;

    /* JADX INFO: renamed from: b */
    private C1119a f1838b = null;

    /* JADX INFO: renamed from: c */
    private final int f1839c = 100;

    public Camera2Wrapper(Context context) {
        this.f1837a = context;
        initCamera2Jni();
    }

    /* JADX INFO: renamed from: a */
    private static int m1784a(float f) {
        return (int) Math.min(Math.max((f * 2000.0f) - 1000.0f, -900.0f), 900.0f);
    }

    private final native void initCamera2Jni();

    private final native void nativeFrameReady(Object obj, Object obj2, Object obj3, int i, int i2, int i3);

    private final native void nativeSurfaceTextureReady(Object obj);

    /* JADX INFO: renamed from: a */
    public final void m1785a() {
        closeCamera2();
    }

    @Override // com.unity3d.player.InterfaceC1122d
    /* JADX INFO: renamed from: a */
    public final void mo1786a(Object obj) {
        nativeSurfaceTextureReady(obj);
    }

    @Override // com.unity3d.player.InterfaceC1122d
    /* JADX INFO: renamed from: a */
    public final void mo1787a(Object obj, Object obj2, Object obj3, int i, int i2, int i3) {
        nativeFrameReady(obj, obj2, obj3, i, i2, i3);
    }

    protected void closeCamera2() {
        C1119a c1119a = this.f1838b;
        if (c1119a != null) {
            c1119a.m1918b();
        }
        this.f1838b = null;
    }

    protected int getCamera2Count() {
        if (C1128j.f2042b) {
            return C1119a.m1881a(this.f1837a);
        }
        return 0;
    }

    protected int[] getCamera2Resolutions(int i) {
        if (C1128j.f2042b) {
            return C1119a.m1903d(this.f1837a, i);
        }
        return null;
    }

    protected int getCamera2SensorOrientation(int i) {
        if (C1128j.f2042b) {
            return C1119a.m1882a(this.f1837a, i);
        }
        return 0;
    }

    protected Object getCameraFocusArea(float f, float f2) {
        int iM1784a = m1784a(f);
        int iM1784a2 = m1784a(1.0f - f2);
        return new Camera.Area(new Rect(iM1784a - 100, iM1784a2 - 100, iM1784a + 100, iM1784a2 + 100), 1000);
    }

    protected Rect getFrameSizeCamera2() {
        C1119a c1119a = this.f1838b;
        return c1119a != null ? c1119a.m1915a() : new Rect();
    }

    protected boolean initializeCamera2(int i, int i2, int i3, int i4, int i5) {
        if (!C1128j.f2042b || this.f1838b != null || UnityPlayer.currentActivity == null) {
            return false;
        }
        this.f1838b = new C1119a(this);
        return this.f1838b.m1917a(this.f1837a, i, i2, i3, i4, i5);
    }

    protected boolean isCamera2AutoFocusPointSupported(int i) {
        if (C1128j.f2042b) {
            return C1119a.m1900c(this.f1837a, i);
        }
        return false;
    }

    protected boolean isCamera2FrontFacing(int i) {
        if (C1128j.f2042b) {
            return C1119a.m1898b(this.f1837a, i);
        }
        return false;
    }

    protected void pauseCamera2() {
        C1119a c1119a = this.f1838b;
        if (c1119a != null) {
            c1119a.m1920d();
        }
    }

    protected boolean setAutoFocusPoint(float f, float f2) {
        C1119a c1119a;
        if (!C1128j.f2042b || (c1119a = this.f1838b) == null) {
            return false;
        }
        return c1119a.m1916a(f, f2);
    }

    protected void startCamera2() {
        C1119a c1119a = this.f1838b;
        if (c1119a != null) {
            c1119a.m1919c();
        }
    }

    protected void stopCamera2() {
        C1119a c1119a = this.f1838b;
        if (c1119a != null) {
            c1119a.m1921e();
        }
    }
}
