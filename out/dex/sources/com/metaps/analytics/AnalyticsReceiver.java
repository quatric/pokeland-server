package com.metaps.analytics;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.metaps.common.C0847a;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class AnalyticsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb;
        if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
            String string = intent.getExtras().getString("referrer");
            Log.d(C0847a.f855a, "Referrer is: " + string);
            C0785a.m616a(context, string);
        }
        C0829r.m808a().m809a(context, intent);
        try {
            ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, getClass()), 128);
            if (receiverInfo.metaData != null && receiverInfo.metaData.keySet() != null) {
                String str = "";
                for (String str2 : receiverInfo.metaData.keySet()) {
                    try {
                        try {
                            ((BroadcastReceiver) Class.forName(str2).newInstance()).onReceive(context, intent);
                            Log.d(C0847a.f855a, "Notified referrer to " + str2);
                            str = str2;
                        } catch (ClassNotFoundException unused) {
                            str = str2;
                            sb = new StringBuilder();
                            sb.append("Class ");
                            sb.append(str);
                            sb.append(" not found");
                            C0847a.m911c(sb.toString());
                        } catch (IllegalAccessException unused2) {
                            str = str2;
                            sb = new StringBuilder();
                            sb.append("Not able to access ");
                            sb.append(str);
                            C0847a.m911c(sb.toString());
                        } catch (InstantiationException unused3) {
                            str = str2;
                            sb = new StringBuilder();
                            sb.append("Not able to instantiate ");
                            sb.append(str);
                            C0847a.m911c(sb.toString());
                        }
                    } catch (ClassNotFoundException unused4) {
                    } catch (IllegalAccessException unused5) {
                    } catch (InstantiationException unused6) {
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused7) {
            C0847a.m911c("Not able to get any meta-data for AnalyticsReceiver");
        }
    }
}
