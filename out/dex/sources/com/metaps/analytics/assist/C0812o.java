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

/* JADX INFO: renamed from: com.metaps.analytics.assist.o */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C0812o extends AbstractC0801d {

    /* JADX INFO: renamed from: i */
    private static final String f590i = "MetapsPromotionJS";

    /* JADX INFO: renamed from: j */
    private static final String f591j = "{code_%d}";

    /* JADX INFO: renamed from: k */
    private static final String f592k = "{url_%d}";

    /* JADX INFO: renamed from: l */
    private static final String f593l = "{style_%d}";

    /* JADX INFO: renamed from: m */
    private static final String f594m = "";

    /* JADX INFO: renamed from: n */
    private static final String f595n = "display: none;";

    /* JADX INFO: renamed from: o */
    private List<C0811n> f596o;

    /* JADX INFO: renamed from: p */
    private boolean f597p;

    public C0812o(Context context, AppSpot appSpot, String str, JSONObject jSONObject) throws JSONException {
        super(context, appSpot, str, jSONObject);
    }

    /* JADX INFO: renamed from: a */
    private int m755a(AppSpot appSpot) {
        if (appSpot.getAppSpotType() == AppSpotType.ICON) {
            return appSpot.getAppSpotConfig().m666b();
        }
        return 1;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    private synchronized C0811n m756a(String str) {
        for (C0811n c0811n : this.f596o) {
            if (c0811n.m749f().equals(str)) {
                return c0811n;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    private List<C0811n> m757a(JSONObject jSONObject, int i) throws JSONException {
        ArrayList arrayList = new ArrayList();
        int i2 = jSONObject.getInt("creative_type");
        JSONArray jSONArray = jSONObject.getJSONArray("promotions");
        String string = UUID.randomUUID().toString();
        for (int i3 = 0; i3 < jSONArray.length() && arrayList.size() < i; i3++) {
            C0811n c0811nM735a = C0811n.m735a(string, jSONArray.getJSONObject(i3));
            if (c0811nM735a != null) {
                C0847a.m903a(getClass().toString(), c0811nM735a.m749f() + " can be displayed.");
                c0811nM735a.m740b(i2);
                arrayList.add(c0811nM735a);
            }
        }
        return arrayList;
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    /* JADX INFO: renamed from: a */
    protected String mo672a(String str, JSONObject jSONObject) throws JSONException {
        int iM755a = m755a(this.f446h);
        this.f596o = m757a(jSONObject, iM755a);
        if (this.f596o.size() <= 0) {
            return null;
        }
        String strReplace = str;
        int i = 0;
        while (i < this.f596o.size()) {
            int i2 = i + 1;
            strReplace = strReplace.replace(String.format(f591j, Integer.valueOf(i2)), this.f596o.get(i).m749f()).replace(String.format(f592k, Integer.valueOf(i2)), this.f596o.get(i).m752i()).replace(String.format(f593l, Integer.valueOf(i2)), "");
            i = i2;
        }
        int size = this.f596o.size();
        while (size < iM755a) {
            size++;
            strReplace = strReplace.replace(String.format(f593l, Integer.valueOf(size)), f595n);
        }
        return strReplace;
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    /* JADX INFO: renamed from: b */
    protected void mo675b() {
        if (this.f597p) {
            return;
        }
        this.f597p = true;
        C0785a.m630b(this.f446h.getSpotCode(), this.f596o);
    }

    @Override // com.metaps.analytics.assist.AbstractC0801d
    public String getJsObjectName() {
        return f590i;
    }

    @JavascriptInterface
    public void tapAppProcess(String str) {
        synchronized (this) {
            C0811n c0811nM756a = m756a(str);
            if (c0811nM756a == null) {
                C0847a.m909b(getClass().toString(), "No promotion found.");
                return;
            }
            if (!this.f446h.f389h) {
                this.f446h.m662a(AppSpotListener.DismissReason.TARGET_CLICKED);
            }
            if (!c0811nM756a.m746d()) {
                c0811nM756a.m742c();
                C0785a.m621a(this.f446h.getSpotCode(), c0811nM756a);
            }
            String strM753j = c0811nM756a.m753j();
            if (strM753j != null && strM753j.length() > 0) {
                this.f446h.onClick(strM753j);
                if (this.f446h.getAppSpotConfig().isClickHandledManually()) {
                    return;
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strM753j));
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
