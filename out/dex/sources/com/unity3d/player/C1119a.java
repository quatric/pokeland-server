package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: com.unity3d.player.a */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C1119a {

    /* JADX INFO: renamed from: b */
    private static CameraManager f1985b;

    /* JADX INFO: renamed from: c */
    private static String[] f1986c;

    /* JADX INFO: renamed from: e */
    private static Semaphore f1987e = new Semaphore(1);

    /* JADX INFO: renamed from: a */
    private InterfaceC1122d f1992a;

    /* JADX INFO: renamed from: d */
    private CameraDevice f1993d;

    /* JADX INFO: renamed from: f */
    private HandlerThread f1994f;

    /* JADX INFO: renamed from: g */
    private Handler f1995g;

    /* JADX INFO: renamed from: h */
    private Rect f1996h;

    /* JADX INFO: renamed from: i */
    private Rect f1997i;

    /* JADX INFO: renamed from: j */
    private int f1998j;

    /* JADX INFO: renamed from: k */
    private int f1999k;

    /* JADX INFO: renamed from: n */
    private int f2002n;

    /* JADX INFO: renamed from: o */
    private int f2003o;

    /* JADX INFO: renamed from: q */
    private Range f2005q;

    /* JADX INFO: renamed from: s */
    private Image f2007s;

    /* JADX INFO: renamed from: t */
    private CaptureRequest.Builder f2008t;

    /* JADX INFO: renamed from: w */
    private int f2011w;

    /* JADX INFO: renamed from: x */
    private SurfaceTexture f2012x;

    /* JADX INFO: renamed from: l */
    private float f2000l = -1.0f;

    /* JADX INFO: renamed from: m */
    private float f2001m = -1.0f;

    /* JADX INFO: renamed from: p */
    private boolean f2004p = false;

    /* JADX INFO: renamed from: r */
    private ImageReader f2006r = null;

    /* JADX INFO: renamed from: u */
    private CameraCaptureSession f2009u = null;

    /* JADX INFO: renamed from: v */
    private Object f2010v = new Object();

    /* JADX INFO: renamed from: y */
    private Surface f2013y = null;

    /* JADX INFO: renamed from: z */
    private int f2014z = a.f2022c;

    /* JADX INFO: renamed from: A */
    private CameraCaptureSession.CaptureCallback f1988A = new CameraCaptureSession.CaptureCallback() { // from class: com.unity3d.player.a.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            C1119a.this.m1892a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            C1125g.Log(5, "Camera2: Capture session failed " + captureRequest.getTag() + " reason " + captureFailure.getReason());
            C1119a.this.m1892a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
        }
    };

    /* JADX INFO: renamed from: B */
    private final CameraDevice.StateCallback f1989B = new CameraDevice.StateCallback() { // from class: com.unity3d.player.a.3
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(CameraDevice cameraDevice) {
            C1119a.f1987e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(CameraDevice cameraDevice) {
            C1125g.Log(5, "Camera2: CameraDevice disconnected.");
            C1119a.this.m1890a(cameraDevice);
            C1119a.f1987e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(CameraDevice cameraDevice, int i) {
            C1125g.Log(6, "Camera2: Error opeining CameraDevice " + i);
            C1119a.this.m1890a(cameraDevice);
            C1119a.f1987e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(CameraDevice cameraDevice) {
            C1119a.this.f1993d = cameraDevice;
            C1119a.f1987e.release();
        }
    };

    /* JADX INFO: renamed from: C */
    private final ImageReader.OnImageAvailableListener f1990C = new ImageReader.OnImageAvailableListener() { // from class: com.unity3d.player.a.4
        @Override // android.media.ImageReader.OnImageAvailableListener
        public final void onImageAvailable(ImageReader imageReader) {
            if (C1119a.f1987e.tryAcquire()) {
                Image imageAcquireNextImage = imageReader.acquireNextImage();
                if (imageAcquireNextImage != null) {
                    Image.Plane[] planes = imageAcquireNextImage.getPlanes();
                    if (imageAcquireNextImage.getFormat() == 35 && planes != null && planes.length == 3) {
                        C1119a.this.f1992a.mo1787a(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                    } else {
                        C1125g.Log(6, "Camera2: Wrong image format.");
                    }
                    if (C1119a.this.f2007s != null) {
                        C1119a.this.f2007s.close();
                    }
                    C1119a.this.f2007s = imageAcquireNextImage;
                }
                C1119a.f1987e.release();
            }
        }
    };

    /* JADX INFO: renamed from: D */
    private final SurfaceTexture.OnFrameAvailableListener f1991D = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.unity3d.player.a.5
        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
            C1119a.this.f1992a.mo1786a(surfaceTexture);
        }
    };

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.unity3d.player.a$a */
    private static final class a {

        /* JADX INFO: renamed from: a */
        public static final int f2020a = 1;

        /* JADX INFO: renamed from: b */
        public static final int f2021b = 2;

        /* JADX INFO: renamed from: c */
        public static final int f2022c = 3;

        /* JADX INFO: renamed from: d */
        private static final /* synthetic */ int[] f2023d = {f2020a, f2021b, f2022c};
    }

    protected C1119a(InterfaceC1122d interfaceC1122d) {
        this.f1992a = null;
        this.f1992a = interfaceC1122d;
        m1907g();
    }

    /* JADX INFO: renamed from: a */
    public static int m1881a(Context context) {
        return m1901c(context).length;
    }

    /* JADX INFO: renamed from: a */
    public static int m1882a(Context context, int i) {
        try {
            return ((Integer) m1894b(context).getCameraCharacteristics(m1901c(context)[i]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
            return 0;
        }
    }

    /* JADX INFO: renamed from: a */
    private static int m1883a(Range[] rangeArr, int i) {
        int i2 = -1;
        double d = Double.MAX_VALUE;
        for (int i3 = 0; i3 < rangeArr.length; i3++) {
            int iIntValue = ((Integer) rangeArr[i3].getLower()).intValue();
            int iIntValue2 = ((Integer) rangeArr[i3].getUpper()).intValue();
            float f = i;
            if (f + 0.1f > iIntValue && f - 0.1f < iIntValue2) {
                return i;
            }
            double dMin = Math.min(Math.abs(i - iIntValue), Math.abs(i - iIntValue2));
            if (dMin < d) {
                i2 = i3;
                d = dMin;
            }
        }
        return ((Integer) (i > ((Integer) rangeArr[i2].getUpper()).intValue() ? rangeArr[i2].getUpper() : rangeArr[i2].getLower())).intValue();
    }

    /* JADX INFO: renamed from: a */
    private static Rect m1884a(Size[] sizeArr, double d, double d2) {
        double d3 = Double.MAX_VALUE;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sizeArr.length; i3++) {
            int width = sizeArr[i3].getWidth();
            int height = sizeArr[i3].getHeight();
            double d4 = width;
            Double.isNaN(d4);
            double dAbs = Math.abs(Math.log(d / d4));
            double d5 = height;
            Double.isNaN(d5);
            double dAbs2 = dAbs + Math.abs(Math.log(d2 / d5));
            if (dAbs2 < d3) {
                i = width;
                i2 = height;
                d3 = dAbs2;
            }
        }
        return new Rect(0, 0, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1890a(CameraDevice cameraDevice) {
        synchronized (this.f2010v) {
            this.f2009u = null;
        }
        cameraDevice.close();
        this.f1993d = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1892a(Object obj) {
        if (obj != "Focus") {
            if (obj == "Cancel focus") {
                synchronized (this.f2010v) {
                    if (this.f2009u != null) {
                        m1913j();
                    }
                }
                return;
            }
            return;
        }
        this.f2004p = false;
        synchronized (this.f2010v) {
            if (this.f2009u != null) {
                try {
                    this.f2008t.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                    this.f2008t.setTag("Regular");
                    this.f2009u.setRepeatingRequest(this.f2008t.build(), this.f1988A, this.f1995g);
                } catch (CameraAccessException e) {
                    C1125g.Log(6, "Camera2: CameraAccessException " + e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static Size[] m1893a(CameraCharacteristics cameraCharacteristics) {
        String str;
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            str = "Camera2: configuration map is not available.";
        } else {
            Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
            if (outputSizes != null && outputSizes.length != 0) {
                return outputSizes;
            }
            str = "Camera2: output sizes for YUV_420_888 format are not avialable.";
        }
        C1125g.Log(6, str);
        return null;
    }

    /* JADX INFO: renamed from: b */
    private static CameraManager m1894b(Context context) {
        if (f1985b == null) {
            f1985b = (CameraManager) context.getSystemService("camera");
        }
        return f1985b;
    }

    /* JADX INFO: renamed from: b */
    private void m1896b(CameraCharacteristics cameraCharacteristics) {
        this.f1999k = ((Integer) cameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
        if (this.f1999k > 0) {
            this.f1997i = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            float fWidth = this.f1997i.width() / this.f1997i.height();
            float fWidth2 = this.f1996h.width() / this.f1996h.height();
            if (fWidth2 > fWidth) {
                this.f2002n = 0;
                this.f2003o = (int) ((this.f1997i.height() - (this.f1997i.width() / fWidth2)) / 2.0f);
            } else {
                this.f2003o = 0;
                this.f2002n = (int) ((this.f1997i.width() - (this.f1997i.height() * fWidth2)) / 2.0f);
            }
            this.f1998j = Math.min(this.f1997i.width(), this.f1997i.height()) / 20;
        }
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1898b(Context context, int i) {
        try {
            return ((Integer) m1894b(context).getCameraCharacteristics(m1901c(context)[i]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    /* JADX INFO: renamed from: c */
    public static boolean m1900c(Context context, int i) {
        try {
            return ((Integer) m1894b(context).getCameraCharacteristics(m1901c(context)[i]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() > 0;
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    /* JADX INFO: renamed from: c */
    private static String[] m1901c(Context context) {
        if (f1986c == null) {
            try {
                f1986c = m1894b(context).getCameraIdList();
            } catch (CameraAccessException e) {
                C1125g.Log(6, "Camera2: CameraAccessException " + e);
                f1986c = new String[0];
            }
        }
        return f1986c;
    }

    /* JADX INFO: renamed from: d */
    public static int[] m1903d(Context context, int i) {
        try {
            Size[] sizeArrM1893a = m1893a(m1894b(context).getCameraCharacteristics(m1901c(context)[i]));
            if (sizeArrM1893a == null) {
                return null;
            }
            int[] iArr = new int[sizeArrM1893a.length * 2];
            for (int i2 = 0; i2 < sizeArrM1893a.length; i2++) {
                int i3 = i2 * 2;
                iArr[i3] = sizeArrM1893a[i2].getWidth();
                iArr[i3 + 1] = sizeArrM1893a[i2].getHeight();
            }
            return iArr;
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
            return null;
        }
    }

    /* JADX INFO: renamed from: g */
    private void m1907g() {
        this.f1994f = new HandlerThread("CameraBackground");
        this.f1994f.start();
        this.f1995g = new Handler(this.f1994f.getLooper());
    }

    /* JADX INFO: renamed from: h */
    private void m1910h() {
        this.f1994f.quit();
        try {
            this.f1994f.join(4000L);
            this.f1994f = null;
            this.f1995g = null;
        } catch (InterruptedException e) {
            this.f1994f.interrupt();
            C1125g.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e);
        }
    }

    /* JADX INFO: renamed from: i */
    private void m1912i() {
        try {
            if (!f1987e.tryAcquire(4L, TimeUnit.SECONDS)) {
                C1125g.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.f1993d.close();
            try {
                if (!f1987e.tryAcquire(4L, TimeUnit.SECONDS)) {
                    C1125g.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e) {
                C1125g.Log(6, "Camera2: Interrupted while waiting to close camera " + e);
            }
            this.f1993d = null;
            f1987e.release();
        } catch (InterruptedException e2) {
            C1125g.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: j */
    public void m1913j() {
        try {
            if (this.f1999k != 0 && this.f2000l >= 0.0f && this.f2000l <= 1.0f && this.f2001m >= 0.0f && this.f2001m <= 1.0f) {
                this.f2004p = true;
                int iWidth = (int) (((this.f1997i.width() - (this.f2002n * 2)) * this.f2000l) + this.f2002n);
                double dHeight = this.f1997i.height() - (this.f2003o * 2);
                double d = this.f2001m;
                Double.isNaN(d);
                Double.isNaN(dHeight);
                double d2 = dHeight * (1.0d - d);
                double d3 = this.f2003o;
                Double.isNaN(d3);
                this.f2008t.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(Math.max(this.f1998j + 1, Math.min(iWidth, (this.f1997i.width() - this.f1998j) - 1)) - this.f1998j, Math.max(this.f1998j + 1, Math.min((int) (d2 + d3), (this.f1997i.height() - this.f1998j) - 1)) - this.f1998j, this.f1998j * 2, this.f1998j * 2, 999)});
                this.f2008t.set(CaptureRequest.CONTROL_AF_MODE, 1);
                this.f2008t.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                this.f2008t.setTag("Focus");
                this.f2009u.capture(this.f2008t.build(), this.f1988A, this.f1995g);
                return;
            }
            this.f2008t.set(CaptureRequest.CONTROL_AF_MODE, 4);
            this.f2008t.setTag("Regular");
            if (this.f2009u != null) {
                this.f2009u.setRepeatingRequest(this.f2008t.build(), this.f1988A, this.f1995g);
            }
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* JADX INFO: renamed from: k */
    private void m1914k() {
        try {
            if (this.f2009u != null) {
                this.f2009u.stopRepeating();
                this.f2008t.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.f2008t.set(CaptureRequest.CONTROL_AF_MODE, 0);
                this.f2008t.setTag("Cancel focus");
                this.f2009u.capture(this.f2008t.build(), this.f1988A, this.f1995g);
            }
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* JADX INFO: renamed from: a */
    public final Rect m1915a() {
        return this.f1996h;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m1916a(float f, float f2) {
        if (this.f1999k <= 0) {
            return false;
        }
        if (this.f2004p) {
            C1125g.Log(5, "Camera2: Setting manual focus point already started.");
            return false;
        }
        this.f2000l = f;
        this.f2001m = f2;
        synchronized (this.f2010v) {
            if (this.f2009u != null && this.f2014z != a.f2021b) {
                m1914k();
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m1917a(Context context, int i, int i2, int i3, int i4, int i5) {
        try {
            CameraCharacteristics cameraCharacteristics = f1985b.getCameraCharacteristics(m1901c(context)[i]);
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                C1125g.Log(5, "Camera2: only LEGACY hardware level is supported.");
                return false;
            }
            Size[] sizeArrM1893a = m1893a(cameraCharacteristics);
            if (sizeArrM1893a != null && sizeArrM1893a.length != 0) {
                this.f1996h = m1884a(sizeArrM1893a, i2, i3);
                Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                if (rangeArr == null || rangeArr.length == 0) {
                    C1125g.Log(6, "Camera2: target FPS ranges are not avialable.");
                } else {
                    int iM1883a = m1883a(rangeArr, i4);
                    this.f2005q = new Range(Integer.valueOf(iM1883a), Integer.valueOf(iM1883a));
                    try {
                        if (!f1987e.tryAcquire(4L, TimeUnit.SECONDS)) {
                            C1125g.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                            return false;
                        }
                        try {
                            f1985b.openCamera(m1901c(context)[i], this.f1989B, this.f1995g);
                            try {
                                if (!f1987e.tryAcquire(4L, TimeUnit.SECONDS)) {
                                    C1125g.Log(5, "Camera2: Timeout waiting to open camera.");
                                    return false;
                                }
                                f1987e.release();
                                this.f2011w = i5;
                                m1896b(cameraCharacteristics);
                                return this.f1993d != null;
                            } catch (InterruptedException e) {
                                C1125g.Log(6, "Camera2: Interrupted while waiting to open camera " + e);
                            }
                        } catch (CameraAccessException e2) {
                            C1125g.Log(6, "Camera2: CameraAccessException " + e2);
                            f1987e.release();
                            return false;
                        }
                    } catch (InterruptedException e3) {
                        C1125g.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e3);
                        return false;
                    }
                }
            }
            return false;
        } catch (CameraAccessException e4) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e4);
            return false;
        }
    }

    /* JADX INFO: renamed from: b */
    public final void m1918b() {
        if (this.f1993d != null) {
            m1921e();
            m1912i();
            this.f1988A = null;
            this.f2013y = null;
            this.f2012x = null;
            Image image = this.f2007s;
            if (image != null) {
                image.close();
                this.f2007s = null;
            }
            ImageReader imageReader = this.f2006r;
            if (imageReader != null) {
                imageReader.close();
                this.f2006r = null;
            }
        }
        m1910h();
    }

    /* JADX INFO: renamed from: c */
    public final void m1919c() {
        if (this.f2006r == null) {
            this.f2006r = ImageReader.newInstance(this.f1996h.width(), this.f1996h.height(), 35, 2);
            this.f2006r.setOnImageAvailableListener(this.f1990C, this.f1995g);
            this.f2007s = null;
            int i = this.f2011w;
            if (i != 0) {
                this.f2012x = new SurfaceTexture(i);
                this.f2012x.setDefaultBufferSize(this.f1996h.width(), this.f1996h.height());
                this.f2012x.setOnFrameAvailableListener(this.f1991D, this.f1995g);
                this.f2013y = new Surface(this.f2012x);
            }
        }
        try {
            if (this.f2009u == null) {
                this.f1993d.createCaptureSession(Arrays.asList(this.f2013y != null ? new Surface[]{this.f2013y, this.f2006r.getSurface()} : new Surface[]{this.f2006r.getSurface()}), new CameraCaptureSession.StateCallback() { // from class: com.unity3d.player.a.2
                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        C1125g.Log(6, "Camera2: CaptureSession configuration failed.");
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        if (C1119a.this.f1993d == null) {
                            return;
                        }
                        synchronized (C1119a.this.f2010v) {
                            C1119a.this.f2009u = cameraCaptureSession;
                            try {
                                C1119a.this.f2008t = C1119a.this.f1993d.createCaptureRequest(1);
                                if (C1119a.this.f2013y != null) {
                                    C1119a.this.f2008t.addTarget(C1119a.this.f2013y);
                                }
                                C1119a.this.f2008t.addTarget(C1119a.this.f2006r.getSurface());
                                C1119a.this.f2008t.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, C1119a.this.f2005q);
                                C1119a.this.m1913j();
                            } catch (CameraAccessException e) {
                                C1125g.Log(6, "Camera2: CameraAccessException " + e);
                            }
                        }
                    }
                }, this.f1995g);
            } else if (this.f2014z == a.f2021b) {
                this.f2009u.setRepeatingRequest(this.f2008t.build(), this.f1988A, this.f1995g);
            }
            this.f2014z = a.f2020a;
        } catch (CameraAccessException e) {
            C1125g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* JADX INFO: renamed from: d */
    public final void m1920d() {
        synchronized (this.f2010v) {
            if (this.f2009u != null) {
                try {
                    this.f2009u.stopRepeating();
                    this.f2014z = a.f2021b;
                } catch (CameraAccessException e) {
                    C1125g.Log(6, "Camera2: CameraAccessException " + e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: e */
    public final void m1921e() {
        synchronized (this.f2010v) {
            if (this.f2009u != null) {
                try {
                    this.f2009u.abortCaptures();
                } catch (CameraAccessException e) {
                    C1125g.Log(6, "Camera2: CameraAccessException " + e);
                }
                this.f2009u.close();
                this.f2009u = null;
                this.f2014z = a.f2022c;
            }
        }
    }
}
