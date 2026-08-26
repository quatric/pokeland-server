package com.metaps.analytics.assist;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.api.client.http.HttpStatusCodes;
import com.metaps.common.C0847a;
import com.metaps.common.C0848b;
import com.metaps.common.C0849c;
import com.metaps.common.C0854h;
import java.util.concurrent.CountDownLatch;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AppSpot extends FrameLayout {

    /* JADX INFO: renamed from: a */
    protected int f382a;

    /* JADX INFO: renamed from: b */
    protected int f383b;

    /* JADX INFO: renamed from: c */
    protected int f384c;

    /* JADX INFO: renamed from: d */
    protected int f385d;

    /* JADX INFO: renamed from: e */
    protected int f386e;

    /* JADX INFO: renamed from: f */
    protected int f387f;

    /* JADX INFO: renamed from: g */
    protected int f388g;

    /* JADX INFO: renamed from: h */
    protected boolean f389h;

    /* JADX INFO: renamed from: i */
    protected View.OnClickListener f390i;

    /* JADX INFO: renamed from: j */
    private String f391j;

    /* JADX INFO: renamed from: k */
    private AppSpotType f392k;

    /* JADX INFO: renamed from: l */
    private AppSpotConfig f393l;

    /* JADX INFO: renamed from: m */
    private AppSpotListener f394m;

    /* JADX INFO: renamed from: n */
    private AbstractC0801d f395n;

    /* JADX INFO: renamed from: o */
    private final Handler f396o;

    /* JADX INFO: renamed from: p */
    private boolean f397p;

    /* JADX INFO: renamed from: q */
    private AppSpotListener.NotLoadReason f398q;

    /* JADX INFO: renamed from: r */
    private Activity f399r;

    /* JADX INFO: renamed from: com.metaps.analytics.assist.AppSpot$3 */
    static /* synthetic */ class C07903 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f409a = new int[EnumC0804g.values().length];

        static {
            try {
                f409a[EnumC0804g.HOUSE_AD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f409a[EnumC0804g.PROMOTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: renamed from: com.metaps.analytics.assist.AppSpot$a */
    private class RunnableC0797a implements Runnable {

        /* JADX INFO: renamed from: b */
        private Context f421b;

        /* JADX INFO: renamed from: c */
        private String f422c;

        /* JADX INFO: renamed from: d */
        private AppSpotType f423d;

        /* JADX INFO: renamed from: e */
        private AppSpotConfig f424e;

        /* JADX INFO: renamed from: f */
        private Object f425f = new Object();

        public RunnableC0797a(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig) {
            this.f421b = context;
            this.f422c = str;
            this.f423d = appSpotType;
            this.f424e = appSpotConfig;
        }

        /* JADX INFO: renamed from: a */
        private JSONObject m663a(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig) throws JSONException {
            String strM687a = C0803f.m683b().m687a(str);
            String strM685a = C0803f.m683b().m685a(context, appSpotType, appSpotConfig);
            if (strM687a == null || strM685a == null) {
                C0847a.m911c("Failed to get remote setting - spotCode:" + str);
                AppSpot.this.f398q = AppSpotListener.NotLoadReason.NO_REMOTE_SETTING;
                return null;
            }
            try {
                C0849c.a aVarM919a = new C0849c().m919a(strM687a, strM685a, C0849c.f862b);
                if (aVarM919a.f871f == 200 && aVarM919a.f874i.equals(String.valueOf(HttpStatusCodes.STATUS_CODE_OK)) && aVarM919a.f876k != null) {
                    return aVarM919a.f876k;
                }
                C0847a.m911c("Failed to get content to app spot - spotCode:" + str + " code:" + aVarM919a.f874i + " noRetry?:" + aVarM919a.f877l);
                String string = getClass().toString();
                StringBuilder sb = new StringBuilder();
                sb.append("Error occurred while getting content to app spot :");
                sb.append(aVarM919a.f873h);
                C0847a.m909b(string, sb.toString());
                if (aVarM919a.f877l) {
                    AppSpot.this.f397p = true;
                }
                if (aVarM919a.f876k == null) {
                    AppSpot.this.f398q = AppSpotListener.NotLoadReason.NO_CONTENT;
                }
                return null;
            } catch (C0848b e) {
                C0847a.m911c("Failed to get content to app spot - spotCode:" + str + " message:" + e.getMessage());
                C0847a.m905a(getClass().toString(), "Error occurred while getting content to app spot.", e);
                return null;
            }
        }

        /* JADX INFO: renamed from: a */
        private void m664a() {
            StringBuilder sb;
            String message;
            for (int i = 0; i < 5; i++) {
                try {
                    JSONObject jSONObjectM663a = m663a(this.f421b, this.f422c, this.f423d, this.f424e);
                    if (jSONObjectM663a != null && AppSpot.this.m647a(jSONObjectM663a, false)) {
                        return;
                    }
                    if (AppSpot.this.f397p) {
                        C0847a.m908b("requestList forced no retry.");
                        break;
                    }
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    sb = new StringBuilder();
                    sb.append("Thread.sleep interrupted. message:");
                    message = e.getMessage();
                    sb.append(message);
                    C0847a.m911c(sb.toString());
                } catch (JSONException e2) {
                    sb = new StringBuilder();
                    sb.append("Failed to parse JSON string. message:");
                    message = e2.getMessage();
                    sb.append(message);
                    C0847a.m911c(sb.toString());
                } catch (Exception e3) {
                    sb = new StringBuilder();
                    sb.append("Failed to load app spot view. message:");
                    message = e3.getMessage();
                    sb.append(message);
                    C0847a.m911c(sb.toString());
                }
            }
            AppSpot appSpot = AppSpot.this;
            appSpot.m642a(appSpot.f398q);
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.f425f) {
                m664a();
            }
        }
    }

    protected AppSpot(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig, AppSpotListener appSpotListener) {
        super(context);
        this.f397p = false;
        this.f398q = AppSpotListener.NotLoadReason.LOAD_FAILED;
        this.f387f = ViewCompat.MEASURED_STATE_MASK;
        this.f388g = 0;
        this.f389h = false;
        this.f390i = null;
        this.f382a = -2;
        this.f383b = -2;
        this.f384c = 17;
        this.f385d = -2;
        this.f386e = -2;
        this.f396o = new Handler(Looper.getMainLooper());
        this.f391j = str;
        this.f392k = appSpotType;
        this.f393l = appSpotConfig;
        this.f394m = appSpotListener;
        m658f();
        if (C0854h.m977d()) {
            C0847a.m911c("Unavailable to use Analytics SDK. In's in the zombie mode.");
        } else {
            m639a(context, str, appSpotType, appSpotConfig);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public AbstractC0801d m637a(EnumC0804g enumC0804g, String str, JSONObject jSONObject) throws JSONException {
        if (str == null || enumC0804g == null) {
            return null;
        }
        int i = C07903.f409a[enumC0804g.ordinal()];
        if (i == 1) {
            return new C0810m(getContext(), this, str, jSONObject);
        }
        if (i != 2) {
            return null;
        }
        return new C0812o(getContext(), this, str, jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m638a() {
        C0847a.m903a(getClass().toString(), "onLoad() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.5
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onLoad(AppSpot.this);
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private void m639a(Context context, String str, AppSpotType appSpotType, AppSpotConfig appSpotConfig) {
        new Thread(new RunnableC0797a(context, str, appSpotType, appSpotConfig)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m642a(final AppSpotListener.NotLoadReason notLoadReason) {
        C0847a.m903a(getClass().toString(), "onNotLoad() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.6
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onNotLoad(AppSpot.this, notLoadReason);
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private void m643a(final AppSpotListener.NotShowReason notShowReason) {
        C0847a.m903a(getClass().toString(), "onNotShow() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.8
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onNotShow(AppSpot.this, notShowReason);
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private void m644a(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            this.f396o.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public boolean m647a(final JSONObject jSONObject, final boolean z) throws JSONException {
        String string;
        String str;
        if (z && !this.f389h) {
            string = getClass().toString();
            str = "This type of app spot cannot be replaced.";
        } else {
            if (jSONObject.has("assist_type") && jSONObject.has("html")) {
                final EnumC0804g enumC0804gM691a = EnumC0804g.m691a(jSONObject.getString("assist_type"));
                final String string2 = jSONObject.getString("html");
                m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.11
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (AppSpot.this) {
                            try {
                                try {
                                    AbstractC0801d abstractC0801dM637a = AppSpot.this.m637a(enumC0804gM691a, string2, jSONObject);
                                    if (abstractC0801dM637a != null && abstractC0801dM637a.isLoaded()) {
                                        AppSpot.this.addView(abstractC0801dM637a, 0);
                                        if (z && AppSpot.this.f395n != null) {
                                            AppSpot.this.removeView(AppSpot.this.f395n);
                                        }
                                        AppSpot.this.f395n = abstractC0801dM637a;
                                        if (AppSpot.this.f395n != null) {
                                            AppSpot.this.f395n.setVisibility(0);
                                        }
                                        AppSpot.this.m638a();
                                        return;
                                    }
                                    AppSpot.this.m642a(AppSpotListener.NotLoadReason.NO_CONTENT);
                                } catch (Exception unused) {
                                    C0847a.m909b(AppSpot.class.toString(), "Failed to set inner view.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                return true;
            }
            string = getClass().toString();
            str = "Necessary response keys not contained.";
        }
        C0847a.m909b(string, str);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m650b() {
        C0847a.m903a(getClass().toString(), "onShow() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.7
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onShow(AppSpot.this);
                }
            });
        }
    }

    /* JADX INFO: renamed from: b */
    private void m651b(final AppSpotListener.DismissReason dismissReason) {
        C0847a.m903a(getClass().toString(), "onDismiss() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.10
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onDismiss(AppSpot.this, dismissReason);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public void m652c() {
        Activity activity = this.f399r;
        if (activity != null) {
            try {
                ((WindowManager) activity.getSystemService("window")).addView(this, m656e());
                C0847a.m903a(getClass().toString(), "Overlay is added to the WindowManager.");
            } catch (RuntimeException e) {
                C0847a.m905a(getClass().toString(), "Failed to show the overlay.", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public void m654d() {
        Activity activity = this.f399r;
        if (activity != null) {
            try {
                ((WindowManager) activity.getSystemService("window")).removeView(this);
                C0847a.m903a(getClass().toString(), "Overlay is removed from the WindowManager.");
            } catch (RuntimeException e) {
                C0847a.m905a(getClass().toString(), "Failed to remove the overlay.", e);
            }
        }
    }

    /* JADX INFO: renamed from: e */
    private WindowManager.LayoutParams m656e() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = this.f382a;
        layoutParams.width = this.f383b;
        layoutParams.format = 1;
        layoutParams.gravity = this.f384c;
        layoutParams.verticalMargin = 0.0f;
        layoutParams.horizontalMargin = 0.0f;
        if (this.f389h) {
            layoutParams.flags = 8;
            layoutParams.type = PointerIconCompat.TYPE_HELP;
        }
        layoutParams.flags |= 32;
        layoutParams.flags &= -257;
        layoutParams.flags |= 262144;
        return layoutParams;
    }

    /* JADX INFO: renamed from: f */
    private void m658f() {
        this.f396o.post(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.2
            @Override // java.lang.Runnable
            public void run() {
                AppSpot.this.setFocusable(true);
                AppSpot.this.setFocusableInTouchMode(true);
                AppSpot.this.setVisibility(4);
                ColorDrawable colorDrawable = new ColorDrawable(AppSpot.this.f387f);
                colorDrawable.setAlpha(AppSpot.this.f388g);
                AppSpot.this.setBackgroundDrawable(colorDrawable);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    protected void m662a(AppSpotListener.DismissReason dismissReason) {
        synchronized (this) {
            C0847a.m903a(getClass().toString(), "AppSpotView.dismiss() is called.");
            if (this.f399r != null) {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                this.f399r.runOnUiThread(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.4
                    @Override // java.lang.Runnable
                    public void run() {
                        AppSpot.this.m654d();
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    C0847a.m905a(getClass().toString(), "Failed to wait for dismiss process.", e);
                }
                m651b(dismissReason);
                this.f399r = null;
                this.f390i = null;
            } else {
                this.f399r = null;
                this.f390i = null;
            }
            throw th;
        }
    }

    public void dismiss() {
        m662a(AppSpotListener.DismissReason.METHOD_CALL);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.f389h || keyEvent.getRepeatCount() != 0 || keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        C0847a.m903a(getClass().toString(), "Back button clicked.");
        m662a(AppSpotListener.DismissReason.BACK_BUTTON);
        return true;
    }

    public AppSpotConfig getAppSpotConfig() {
        return this.f393l;
    }

    public AppSpotType getAppSpotType() {
        return this.f392k;
    }

    public String getSpotCode() {
        return this.f391j;
    }

    public boolean isLoaded() {
        return this.f395n != null;
    }

    public void onClick(final String str) {
        C0847a.m903a(getClass().toString(), "onClick() is called.");
        if (this.f394m != null) {
            m644a(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.9
                @Override // java.lang.Runnable
                public void run() {
                    AppSpot.this.f394m.onClick(AppSpot.this, str);
                }
            });
        }
    }

    public void show(Activity activity) {
        synchronized (this) {
            C0847a.m903a(getClass().toString(), "AppSpotView.show() is called.");
            if (!isLoaded()) {
                m643a(AppSpotListener.NotShowReason.NOT_LOADED);
                return;
            }
            if (activity.isFinishing()) {
                m643a(AppSpotListener.NotShowReason.INVALID_GUI);
            } else if (this.f399r != null) {
                C0847a.m908b("AppSpotView already shown.");
                m643a(AppSpotListener.NotShowReason.ALREADY_SHOWN);
            } else {
                this.f399r = activity;
                this.f399r.runOnUiThread(new Runnable() { // from class: com.metaps.analytics.assist.AppSpot.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AppSpot.this.m652c();
                        AppSpot.this.setVisibility(0);
                        if (AppSpot.this.f395n != null) {
                            AppSpot.this.f395n.mo675b();
                        }
                        AppSpot.this.m650b();
                    }
                });
            }
        }
    }
}
