package com.nintendo.npf.sdk.internal.p024f;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.mynintendo.PointProgramService;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.d */
/* JADX INFO: compiled from: SDKWebViewManager.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0960d extends PointProgramService {

    /* JADX INFO: renamed from: a */
    private static final String f1313a = "d";

    /* JADX INFO: renamed from: b */
    private DialogC0959c f1314b;

    /* JADX INFO: renamed from: c */
    private int f1315c;

    /* JADX INFO: renamed from: d */
    private String f1316d;

    /* JADX INFO: renamed from: e */
    private PointProgramService.EventCallback f1317e;

    /* JADX INFO: renamed from: f */
    private String f1318f;

    /* JADX INFO: renamed from: h */
    private Activity f1320h;

    /* JADX INFO: renamed from: i */
    private String f1321i;

    /* JADX INFO: renamed from: j */
    private Dialog f1322j;

    /* JADX INFO: renamed from: g */
    private boolean f1319g = false;

    /* JADX INFO: renamed from: k */
    private final InterfaceC0875a f1323k = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.d$1, reason: invalid class name */
    /* JADX INFO: compiled from: SDKWebViewManager.java */
    class AnonymousClass1 implements Runnable {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ int f1324a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ String f1325b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ Activity f1326c;

        /* JADX INFO: renamed from: d */
        final /* synthetic */ PointProgramService.EventCallback f1327d;

        AnonymousClass1(int i, String str, Activity activity, PointProgramService.EventCallback eventCallback) {
            this.f1324a = i;
            this.f1325b = str;
            this.f1326c = activity;
            this.f1327d = eventCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            C0960d.this.f1315c = this.f1324a;
            C0960d.this.f1316d = this.f1325b;
            C0960d c0960d = C0960d.this;
            c0960d.f1318f = c0960d.f1323k.mo1048b().m1673b().getAccessToken();
            C0960d c0960d2 = C0960d.this;
            c0960d2.f1314b = new DialogC0959c(this.f1326c, c0960d2.f1315c, this.f1325b, true, C0960d.this.f1321i);
            C0960d.this.f1314b.show();
            if (this.f1327d != null) {
                C0960d.this.m1432e();
            }
            new Timer().schedule(new TimerTask() { // from class: com.nintendo.npf.sdk.internal.f.d.1.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    AnonymousClass1.this.f1326c.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.1.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (C0960d.this.f1314b == null || !C0960d.this.f1314b.isShowing()) {
                                return;
                            }
                            C0960d.this.f1314b.m1418a(true);
                        }
                    });
                }
            }, 100L);
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.d$4, reason: invalid class name */
    /* JADX INFO: compiled from: SDKWebViewManager.java */
    class AnonymousClass4 implements Runnable {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ boolean f1333a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ boolean f1334b;

        AnonymousClass4(boolean z, boolean z2) {
            this.f1333a = z;
            this.f1334b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.f1333a) {
                C0960d.this.f1314b.show();
                C0960d.this.f1314b.m1418a(this.f1334b);
                return;
            }
            String queryParameter = Uri.parse(C0960d.this.f1316d).getQueryParameter("access_token");
            if (queryParameter != null) {
                C0960d c0960d = C0960d.this;
                c0960d.f1316d = c0960d.f1316d.replace(queryParameter, C0960d.this.f1318f);
            } else {
                String query = Uri.parse(C0960d.this.f1316d).getQuery();
                String str = query + "&access_token=" + C0960d.this.f1318f;
                C0960d c0960d2 = C0960d.this;
                c0960d2.f1316d = c0960d2.f1316d.replace(query, str);
            }
            C0960d c0960d3 = C0960d.this;
            c0960d3.f1314b = new DialogC0959c(c0960d3.f1320h, C0960d.this.f1315c, C0960d.this.f1316d, this.f1334b, C0960d.this.f1321i);
            C0960d.this.f1314b.show();
            C0960d.this.m1432e();
            new Timer().schedule(new TimerTask() { // from class: com.nintendo.npf.sdk.internal.f.d.4.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    C0960d.this.f1320h.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.4.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            C0960d.this.f1314b.m1418a(AnonymousClass4.this.f1334b);
                        }
                    });
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1427a(boolean z, boolean z2) {
        this.f1320h.runOnUiThread(new AnonymousClass4(z2, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e */
    public void m1432e() {
        if (this.f1320h.isFinishing()) {
            return;
        }
        this.f1320h.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.6
            @Override // java.lang.Runnable
            public void run() {
                if (C0960d.this.f1322j == null) {
                    RelativeLayout relativeLayout = new RelativeLayout(C0960d.this.f1320h);
                    ProgressBar progressBar = new ProgressBar(C0960d.this.f1320h, null, R.attr.progressBarStyleLarge);
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(128, 128);
                    layoutParams.addRule(13);
                    relativeLayout.addView(progressBar, layoutParams);
                    C0960d c0960d = C0960d.this;
                    c0960d.f1322j = new Dialog(c0960d.f1320h);
                    C0960d.this.f1322j.setCancelable(false);
                    C0960d.this.f1322j.setCanceledOnTouchOutside(false);
                    C0960d.this.f1322j.requestWindowFeature(1);
                    C0960d.this.f1322j.setContentView(relativeLayout);
                    C0960d.this.f1322j.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                C0960d.this.f1322j.show();
            }
        });
    }

    /* JADX INFO: renamed from: f */
    private void m1434f() {
        if (this.f1320h.isFinishing()) {
            return;
        }
        this.f1320h.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.7
            @Override // java.lang.Runnable
            public void run() {
                if (C0960d.this.f1322j != null) {
                    C0960d.this.f1322j.dismiss();
                    C0960d.this.f1322j = null;
                }
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1440a() {
        C0955e.m1391a(f1313a, "SDKWebViewManager.onLoadingFinished");
        if (this.f1317e != null) {
            m1434f();
        } else {
            C0955e.m1391a(f1313a, "SDKWebViewManager.onLoadingFinished callback null");
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1441a(Activity activity, float f, String str, String str2, PointProgramService.EventCallback eventCallback) {
        if (this.f1317e != null) {
            C1025o c1025o = new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "WebView can't run multiply");
            C0955e.m1395c(f1313a, "WebView is running");
            eventCallback.onDismiss(c1025o);
        } else {
            this.f1320h = activity;
            this.f1317e = eventCallback;
            Point point = new Point();
            ((WindowManager) activity.getSystemService("window")).getDefaultDisplay().getSize(point);
            this.f1321i = str2;
            activity.runOnUiThread(new AnonymousClass1((int) (point.x * f), str, activity, eventCallback));
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1442a(NPFError nPFError) {
        m1434f();
        DialogC0959c dialogC0959c = this.f1314b;
        if (dialogC0959c != null) {
            dialogC0959c.m1420b();
        }
        PointProgramService.EventCallback eventCallback = this.f1317e;
        if (eventCallback != null) {
            eventCallback.onDismiss(nPFError);
        }
        this.f1314b = null;
        this.f1315c = -1;
        this.f1316d = null;
        this.f1317e = null;
        this.f1320h = null;
    }

    /* JADX INFO: renamed from: a */
    public void m1443a(String str) {
        this.f1316d = str;
    }

    /* JADX INFO: renamed from: b */
    public void m1444b() {
        this.f1319g = true;
        this.f1317e.onAppeared(this);
    }

    /* JADX INFO: renamed from: c */
    public void m1445c() {
        this.f1319g = false;
        this.f1317e.onNintendoAccountLogin(this);
    }

    /* JADX INFO: renamed from: d */
    public void m1446d() {
        this.f1319g = false;
        this.f1317e.onHide(this);
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService
    public void dismiss() {
        this.f1320h.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.2
            @Override // java.lang.Runnable
            public void run() {
                if (C0960d.this.f1314b == null) {
                    C0960d.this.m1442a((NPFError) null);
                } else if (C0960d.this.f1319g) {
                    C0960d.this.f1314b.m1419a(true, true);
                } else {
                    C0960d.this.f1314b.m1417a();
                }
            }
        });
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService
    public void hide() {
        this.f1320h.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.d.3
            @Override // java.lang.Runnable
            public void run() {
                if (C0960d.this.f1319g) {
                    C0960d.this.f1314b.m1419a(false, false);
                }
            }
        });
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService
    public boolean isShowing() {
        return this.f1314b != null && this.f1319g;
    }

    @Override // com.nintendo.npf.sdk.mynintendo.PointProgramService
    public void resume(final boolean z) {
        C0955e.m1391a(f1313a, "Members WebView resume!");
        if (this.f1323k.mo1048b().m1665a().getNintendoAccount() == null) {
            this.f1318f = null;
            m1427a(z, false);
            return;
        }
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long jM1708a = this.f1323k.mo1051e().m1708a(this.f1323k.mo1048b().m1673b());
        long retryAuthLimitTime = PointProgramService.getRetryAuthLimitTime();
        if (jM1708a != 0 && jM1708a - timeInMillis < retryAuthLimitTime) {
            this.f1323k.mo1049c().m1517a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.f.d.5
                @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
                public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                    C0960d c0960d = C0960d.this;
                    c0960d.f1318f = c0960d.f1323k.mo1048b().m1673b().getAccessToken();
                    C0960d.this.m1427a(z, true);
                }
            });
        } else {
            this.f1318f = this.f1323k.mo1048b().m1673b().getAccessToken();
            m1427a(z, true);
        }
    }
}
