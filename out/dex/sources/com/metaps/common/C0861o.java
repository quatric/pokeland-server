package com.metaps.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.metaps.common.o */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0861o {

    /* JADX INFO: renamed from: a */
    private static Map<Activity, ProgressDialog> f999a = new HashMap();

    /* JADX INFO: renamed from: a */
    private static Context m1042a(Activity activity) {
        return activity.getParent() != null ? activity.getParent() : activity;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public static final void m1043a() {
        synchronized (f999a) {
            Iterator<Activity> it = f999a.keySet().iterator();
            while (it.hasNext()) {
                ProgressDialog progressDialog = f999a.get(it.next());
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static final void m1044a(Activity activity, String str) {
        m1045a(activity, false, str);
    }

    /* JADX INFO: renamed from: a */
    public static final void m1045a(final Activity activity, final boolean z, String str) {
        ProgressDialog progressDialog;
        synchronized (f999a) {
            try {
                if (f999a.containsKey(activity)) {
                    progressDialog = f999a.get(activity);
                } else {
                    ProgressDialog progressDialog2 = new ProgressDialog(m1042a(activity));
                    progressDialog2.setProgressStyle(0);
                    progressDialog2.setMessage(str);
                    progressDialog2.setCancelable(false);
                    progressDialog2.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.metaps.common.o.1
                        @Override // android.content.DialogInterface.OnKeyListener
                        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                            if (keyEvent.getAction() != 0 || i != 4) {
                                return false;
                            }
                            if (!z) {
                                return true;
                            }
                            ComponentCallbacks2 componentCallbacks2 = activity;
                            if (!(componentCallbacks2 instanceof InterfaceC0862p)) {
                                return true;
                            }
                            ((InterfaceC0862p) componentCallbacks2).m1046a();
                            return true;
                        }
                    });
                    f999a.put(activity, progressDialog2);
                    progressDialog = progressDialog2;
                }
                progressDialog.show();
            } catch (Exception e) {
                C0847a.m905a(C0861o.class.toString(), e.getMessage(), e);
            }
        }
    }
}
