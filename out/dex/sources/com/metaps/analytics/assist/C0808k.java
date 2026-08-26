package com.metaps.analytics.assist;

import com.metaps.analytics.AbstractC0814c;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.k */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0808k extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f519f = "spot_code";

    /* JADX INFO: renamed from: g */
    private static final String f520g = "impression_id";

    /* JADX INFO: renamed from: h */
    private static final String f521h = "impression_time";

    /* JADX INFO: renamed from: i */
    private static final String f522i = "targets";

    /* JADX INFO: renamed from: j */
    private String f523j;

    /* JADX INFO: renamed from: k */
    private List<C0811n> f524k;

    public C0808k(String str, List<C0811n> list) {
        super(AbstractC0814c.a.PROMOTION_IMP);
        this.f523j = str;
        this.f524k = list;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    public void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f519f, this.f523j);
        List<C0811n> list = this.f524k;
        if (list == null || list.size() <= 0) {
            return;
        }
        C0811n c0811n = this.f524k.get(0);
        if (c0811n != null) {
            jSONObject.put(f520g, c0811n.m736a());
            jSONObject.put(f521h, System.currentTimeMillis() / 1000);
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<C0811n> it = this.f524k.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().m754k());
        }
        jSONObject.put(f522i, jSONArray);
    }
}
