package com.unity3d.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.ViewCompat;
import android.view.View;

/* JADX INFO: renamed from: com.unity3d.player.l */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C1130l extends View {

    /* JADX INFO: renamed from: a */
    final int f2054a;

    /* JADX INFO: renamed from: b */
    final int f2055b;

    /* JADX INFO: renamed from: c */
    Bitmap f2056c;

    /* JADX INFO: renamed from: d */
    Bitmap f2057d;

    /* JADX INFO: renamed from: com.unity3d.player.l$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f2058a = new int[a.m1942a().length];

        static {
            try {
                f2058a[a.f2059a - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2058a[a.f2060b - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2058a[a.f2061c - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.unity3d.player.l$a */
    static final class a {

        /* JADX INFO: renamed from: a */
        public static final int f2059a = 1;

        /* JADX INFO: renamed from: b */
        public static final int f2060b = 2;

        /* JADX INFO: renamed from: c */
        public static final int f2061c = 3;

        /* JADX INFO: renamed from: d */
        private static final /* synthetic */ int[] f2062d = {f2059a, f2060b, f2061c};

        /* JADX INFO: renamed from: a */
        public static int[] m1942a() {
            return (int[]) f2062d.clone();
        }
    }

    public C1130l(Context context, int i) {
        super(context);
        this.f2054a = i;
        this.f2055b = getResources().getIdentifier("unity_static_splash", "drawable", getContext().getPackageName());
        if (this.f2055b != 0) {
            forceLayout();
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.f2056c;
        if (bitmap != null) {
            bitmap.recycle();
            this.f2056c = null;
        }
        Bitmap bitmap2 = this.f2057d;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.f2057d = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x006d  */
    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.f2055b == 0) {
            return;
        }
        if (this.f2056c == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            this.f2056c = BitmapFactory.decodeResource(getResources(), this.f2055b, options);
        }
        int width = this.f2056c.getWidth();
        int height = this.f2056c.getHeight();
        int width2 = getWidth();
        int height2 = getHeight();
        if (width2 == 0 || height2 == 0) {
            return;
        }
        float f = width / height;
        float f2 = width2;
        float f3 = height2;
        boolean z2 = f2 / f3 <= f;
        int i5 = AnonymousClass1.f2058a[this.f2054a - 1];
        if (i5 == 1) {
            if (width2 < width) {
                height = (int) (f2 / f);
                width = width2;
            }
            if (height2 < height) {
                width = (int) (f3 * f);
                height = height2;
            }
        } else if (i5 == 2 || i5 == 3) {
            if ((this.f2054a == a.f2061c) ^ z2) {
                height = (int) (f2 / f);
                width = width2;
            } else {
                width = (int) (f3 * f);
                height = height2;
            }
        }
        Bitmap bitmap = this.f2057d;
        if (bitmap != null) {
            if (bitmap.getWidth() == width && this.f2057d.getHeight() == height) {
                return;
            }
            Bitmap bitmap2 = this.f2057d;
            if (bitmap2 != this.f2056c) {
                bitmap2.recycle();
                this.f2057d = null;
            }
        }
        this.f2057d = Bitmap.createScaledBitmap(this.f2056c, width, height, true);
        this.f2057d.setDensity(getResources().getDisplayMetrics().densityDpi);
        ColorDrawable colorDrawable = new ColorDrawable(ViewCompat.MEASURED_STATE_MASK);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), this.f2057d);
        bitmapDrawable.setGravity(17);
        setBackground(new LayerDrawable(new Drawable[]{colorDrawable, bitmapDrawable}));
    }
}
