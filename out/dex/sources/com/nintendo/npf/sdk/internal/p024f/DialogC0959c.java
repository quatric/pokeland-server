package com.nintendo.npf.sdk.internal.p024f;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.common.net.HttpHeaders;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0920c;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.util.HashMap;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.c */
/* JADX INFO: compiled from: SDKWebViewDialog.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class DialogC0959c extends Dialog {

    /* JADX INFO: renamed from: a */
    private static final String f1299a = "c";

    /* JADX INFO: renamed from: b */
    private int f1300b;

    /* JADX INFO: renamed from: c */
    private LinearLayout f1301c;

    /* JADX INFO: renamed from: d */
    private C0957a f1302d;

    /* JADX INFO: renamed from: e */
    private C0958b f1303e;

    /* JADX INFO: renamed from: f */
    private boolean f1304f;

    /* JADX INFO: renamed from: g */
    private boolean f1305g;

    /* JADX INFO: renamed from: h */
    private final InterfaceC0875a f1306h;

    public DialogC0959c(Activity activity, int i, String str, boolean z, String str2) {
        super(activity, R.style.Theme.Panel);
        this.f1304f = false;
        this.f1305g = false;
        this.f1306h = InterfaceC0875a.a.m1072b();
        this.f1300b = activity.getWindow().getDecorView().getSystemUiVisibility();
        setCancelable(false);
        float f = i / 320.0f;
        float f2 = 0.42666668f * f;
        float f3 = f * 44.0f;
        requestWindowFeature(1);
        if ((activity.getWindow().getAttributes().flags & 1024) != 0) {
            getWindow().addFlags(1024);
        }
        this.f1301c = new LinearLayout(activity);
        this.f1301c.setOrientation(1);
        this.f1301c.setVisibility(4);
        int iM1407a = m1407a(activity);
        if (iM1407a > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            layoutParams.height = iM1407a;
            View view = new View(activity);
            view.setBackgroundColor(Color.rgb(230, 0, 18));
            view.setLayoutParams(layoutParams);
            this.f1301c.addView(view);
        }
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        relativeLayout.setBackgroundColor(Color.rgb(230, 0, 18));
        relativeLayout.setPadding(0, 0, 0, 0);
        ImageButton imageButton = new ImageButton(activity);
        imageButton.setBackgroundColor(Color.argb(0, 0, 0, 0));
        Bitmap bitmapM1415d = m1415d();
        float width = bitmapM1415d.getWidth() * f2;
        float height = (f3 - (bitmapM1415d.getHeight() * f2)) / 2.0f;
        imageButton.setImageBitmap(m1415d());
        int i2 = (int) f3;
        imageButton.setLayoutParams(new ViewGroup.LayoutParams(i2, i2));
        int i3 = (int) ((f3 - width) / 2.0f);
        int i4 = (int) height;
        imageButton.setPadding(i3, i4, i3, i4);
        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.nintendo.npf.sdk.internal.f.c.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                DialogC0959c.this.m1419a(true, true);
            }
        });
        relativeLayout.addView(imageButton);
        ImageView imageView = new ImageView(activity);
        Bitmap bitmapM1416e = m1416e();
        imageView.setImageBitmap(bitmapM1416e);
        int height2 = (int) ((f3 - (bitmapM1416e.getHeight() * f2)) / 2.0f);
        imageView.setPadding(0, height2, 0, height2);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams2.addRule(13);
        relativeLayout.addView(imageView, layoutParams2);
        this.f1301c.addView(relativeLayout, new ViewGroup.LayoutParams(-1, i2));
        FrameLayout frameLayout = new FrameLayout(activity);
        this.f1302d = new C0957a(activity);
        this.f1303e = new C0958b(activity, this, z);
        this.f1302d.setWebViewClient(this.f1303e);
        this.f1302d.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        frameLayout.addView(this.f1302d);
        this.f1301c.addView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
        setContentView(this.f1301c, new ViewGroup.LayoutParams(-1, -1));
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setLayout(i, -1);
        HashMap map = new HashMap();
        map.put(HttpHeaders.ACCEPT_LANGUAGE, C0920c.m1228a(str2));
        this.f1302d.loadUrl(str, map);
    }

    @TargetApi(MotionEventCompat.AXIS_RELATIVE_Y)
    /* JADX INFO: renamed from: a */
    private int m1407a(Activity activity) {
        DisplayCutout displayCutout;
        if (Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = activity.getWindow().getAttributes().layoutInDisplayCutoutMode;
        getWindow().setAttributes(attributes);
        if (attributes.layoutInDisplayCutoutMode == 1 && (displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout()) != null) {
            return displayCutout.getSafeInsetTop();
        }
        return 0;
    }

    /* JADX INFO: renamed from: d */
    private Bitmap m1415d() {
        byte[] bArrDecode = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAB0AAAAdCAYAAABWk2cPAAAA2ElEQVR42sWXQQqDMBBFIz2AuOgmqyx7QnFdpGQptndybS/0m4QINcQS/KHzYHZxHppkZlQeABcXxsXNxVVVxOeLeY33fAtX7Hm6aEhZE/LseQcxAI08L1I6I49W4bWPsSeFFscYv6DFbywpTGm3hX2RmBf26QNDgZgRDipCiAkhISaEhPiEkBYvhJAQ80JenApZ8R1ljKomBXu41BaOKONBqvg9FTu9YvdUrCKJ1V6xLiPWT0UmB5EZSWwa1Mgzk4VlQh797wl/TX8tdPzUXeVm0cW8ehN+AJ2wl1hYrZCxAAAAAElFTkSuQmCC", 0);
        return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
    }

    /* JADX INFO: renamed from: e */
    private Bitmap m1416e() {
        byte[] bArrDecode = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAXEAAAA7CAMAAAC67UooAAAC+lBMVEUAAAD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////86i/ucAAAA/nRSTlMAEkh8qsno9f/x2b6VaS0CInvF/e2hTwVV6YYZAVjW95ITLsTwd/vBHA+5+sOOYEIoIBs1eKfn7EXS+AuD4fxm3e6BFANSzxHTXnKzOql0bjIhsgZM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", 0);
        return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
    }

    /* JADX INFO: renamed from: a */
    public void m1417a() {
        this.f1303e.m1405a();
        this.f1306h.mo1058l().m1442a((NPFError) null);
    }

    /* JADX INFO: renamed from: a */
    public void m1418a(boolean z) {
        this.f1301c.setVisibility(0);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(this.f1301c.getWidth(), 0.0f, 0.0f, 0.0f));
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.nintendo.npf.sdk.internal.f.c.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                DialogC0959c.this.f1305g = true;
                DialogC0959c.this.f1304f = false;
                DialogC0959c.this.f1303e.m1406a(false);
                DialogC0959c.this.f1306h.mo1058l().m1444b();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        if (z) {
            animationSet.setDuration(400L);
        } else {
            animationSet.setDuration(0L);
        }
        this.f1301c.startAnimation(animationSet);
    }

    /* JADX INFO: renamed from: a */
    public void m1419a(final boolean z, final boolean z2) {
        C0955e.m1391a(f1299a, "hideWebView : " + z + " : " + z2);
        if (this.f1304f) {
            return;
        }
        this.f1304f = true;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0.0f, this.f1301c.getWidth(), 0.0f, 0.0f));
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.nintendo.npf.sdk.internal.f.c.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                C0955e.m1391a(DialogC0959c.f1299a, "onAnimationEnd!");
                if (z) {
                    DialogC0959c.this.m1417a();
                    return;
                }
                if (z2) {
                    DialogC0959c.this.f1306h.mo1058l().m1445c();
                } else {
                    DialogC0959c.this.f1306h.mo1058l().m1446d();
                }
                DialogC0959c.this.hide();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        if (z2 && this.f1305g) {
            animationSet.setDuration(400L);
        } else {
            animationSet.setDuration(0L);
        }
        this.f1301c.startAnimation(animationSet);
    }

    /* JADX INFO: renamed from: b */
    public void m1420b() {
        new Handler().post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.c.4
            @Override // java.lang.Runnable
            public void run() {
                ((ViewGroup) DialogC0959c.this.f1302d.getParent()).removeView(DialogC0959c.this.f1302d);
                DialogC0959c.this.f1302d.removeAllViews();
                DialogC0959c.this.f1302d.destroy();
                DialogC0959c.this.f1302d = null;
                DialogC0959c.this.dismiss();
            }
        });
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        m1419a(true, true);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            getWindow().getDecorView().setSystemUiVisibility(this.f1300b);
        }
    }
}
