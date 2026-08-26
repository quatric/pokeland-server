package com.metaps.analytics.assist;

import com.metaps.analytics.AbstractC0814c;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.i */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0806i extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f506f = "spot_code";

    /* JADX INFO: renamed from: g */
    private static final String f507g = "impression_id";

    /* JADX INFO: renamed from: h */
    private static final String f508h = "impression_time";

    /* JADX INFO: renamed from: i */
    private static final String f509i = "fill_empty";

    /* JADX INFO: renamed from: j */
    private static final String f510j = "targets";

    /* JADX INFO: renamed from: k */
    private String f511k;

    /* JADX INFO: renamed from: l */
    private List<C0809l> f512l;

    public C0806i(String str, List<C0809l> list) {
        super(AbstractC0814c.a.HOUSE_AD_IMP);
        this.f511k = str;
        this.f512l = list;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    public void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f506f, this.f511k);
        List<C0809l> list = this.f512l;
        if (list == null || list.size() <= 0) {
            return;
        }
        C0809l c0809l = this.f512l.get(0);
        if (c0809l != null) {
            jSONObject.put(f507g, c0809l.m722i());
            jSONObject.put(f508h, System.currentTimeMillis() / 1000);
        }
        Iterator<C0809l> it = this.f512l.iterator();
        while (it.hasNext()) {
            if (it.next().m724k()) {
                jSONObject.put(f509i, 1);
                break;
            }
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<C0809l> it2 = this.f512l.iterator();
        while (it2.hasNext()) {
            jSONArray.put(it2.next().m731r());
        }
        jSONObject.put(f510j, jSONArray);
    }
}
