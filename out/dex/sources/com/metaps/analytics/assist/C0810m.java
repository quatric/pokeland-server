package com.metaps.analytics.assist;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import com.metaps.analytics.C0785a;
import com.metaps.common.C0847a;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.m */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C0810m extends AbstractC0801d {

    /* JADX INFO: renamed from: i */
    private static final String f567i = "MetapsAdsJS";

    /* JADX INFO: renamed from: j */
    private static final String f568j = "{code_%d}";

    /* JADX INFO: renamed from: k */
    private static final String f569k = "{url_%d}";

    /* JADX INFO: renamed from: l */
    private static final String f570l = "{style_%d}";

    /* JADX INFO: renamed from: m */
    private static final String f571m = "";

    /* JADX INFO: renamed from: n */
    private static final String f572n = "display: none;";

    /* JADX INFO: renamed from: o */
    private List<C0809l> f573o;

    /* JADX INFO: renamed from: p */
    private boolean f574p;

    public C0810m(Context context, AppSpot appSpot, String str, JSONObject jSONObject) throws JSONException {
        super(context, appSpot, str, jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private int m732a(AppSpot appSpot) {
        if (appSpot.getAppSpotType() == AppSpotType.ICON) {
            return appSpot.getAppSpotConfig().m666b();
        }
        return 1;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    private synchronized C0809l m733a(String str) {
        for (C0809l c0809l : this.f573o) {
            if (c0809l.m702b().equals(str)) {
                return c0809l;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    private List<C0809l> m734a(JSONObject jSONObject, int i) throws JSONException {
        ArrayList arrayList = new ArrayList();
        int i2 = jSONObject.getInt("creative_type");
        JSONArray jSONArray = jSONObject.getJSONArray("ads");
        String string = UUID.randomUUID().toString();
        for (int i3 = 0; i3 < jSONArray.length() && arrayList.size() < i; i3++) {
            C0809l c0809lM695a = C0809l.m695a(string, jSONArray.getJSONObject(i3));
            if (c0809lM695a != null && (c0809lM695a.m701a(getContext()) || c0809lM695a.m706b(getContext()))) {
                C0847a.m903a(getClass().toString(), c0809lM695a.m720h() + " can be displayed.");
                c0809lM695a.m703b(i2);
                arrayList.add(c0809lM695a);
            }
        }
        if (arrayList.size() == 0) {
            for (int i4 = 0; i4 < jSONArray.length() && arrayList.size() < i; i4++) {
                C0809l c0809lM695a2 = C0809l.m695a(string, jSONArray.getJSONObject(i4));
                if (c0809lM695a2 != null && c0809lM695a2.m723j()) {
                    C0847a.m903a(getClass().toString(), c0809lM695a2.m720h() + " is used as default.");
                    c0809lM695a2.m703b(i2);
                    c0809lM695a2.m705b(true);
                    arrayList.add(c0809lM695a2);
                }
            }
        }
        return arrayList;
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    /* JADX INFO: renamed from: a */
    protected String mo672a(String str, JSONObject jSONObject) throws JSONException {
        int iM732a = m732a(this.f446h);
        this.f573o = m734a(jSONObject, iM732a);
        if (this.f573o.size() <= 0) {
            return null;
        }
        String strReplace = str;
        int i = 0;
        while (i < this.f573o.size()) {
            int i2 = i + 1;
            strReplace = strReplace.replace(String.format(f568j, Integer.valueOf(i2)), this.f573o.get(i).m702b()).replace(String.format(f569k, Integer.valueOf(i2)), this.f573o.get(i).m718g()).replace(String.format(f570l, Integer.valueOf(i2)), "");
            i = i2;
        }
        int size = this.f573o.size();
        while (size < iM732a) {
            size++;
            strReplace = strReplace.replace(String.format(f570l, Integer.valueOf(size)), f572n);
        }
        return strReplace;
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    /* JADX INFO: renamed from: b */
    protected void mo675b() {
        if (this.f574p) {
            return;
        }
        this.f574p = true;
        C0785a.m624a(this.f446h.getSpotCode(), this.f573o);
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    public String getJsObjectName() {
        return f567i;
    }

    @JavascriptInterface
    public void tapAppProcess(String str) {
        synchronized (this) {
            C0809l c0809lM733a = m733a(str);
            if (c0809lM733a == null) {
                C0847a.m909b(getClass().toString(), "No house ad found.");
                return;
            }
            if (!this.f446h.f389h) {
                this.f446h.m662a(AppSpotListener.DismissReason.TARGET_CLICKED);
            }
            if (!c0809lM733a.m726m()) {
                c0809lM733a.m725l();
                C0785a.m620a(this.f446h.getSpotCode(), c0809lM733a);
            }
            String strM686a = C0803f.m683b().m686a(getContext(), this.f446h.getSpotCode(), c0809lM733a);
            if (strM686a != null && strM686a.length() > 0) {
                this.f446h.onClick(strM686a);
                if (this.f446h.getAppSpotConfig().isClickHandledManually()) {
                    return;
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strM686a));
                intent.setFlags(268435456);
                try {
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    C0847a.m905a(getClass().toString(), "No Activity found to handle this url scheme", e);
                }
            }
        }
    }
}
