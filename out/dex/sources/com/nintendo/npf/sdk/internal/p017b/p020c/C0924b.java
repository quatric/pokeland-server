package com.nintendo.npf.sdk.internal.p017b.p020c;

import android.text.TextUtils;
import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.c.b */
/* JADX INFO: compiled from: AccountApiHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0924b extends C0928f implements InterfaceC0923a {
    @Override // com.nintendo.npf.sdk.internal.p017b.p020c.InterfaceC0923a
    /* JADX INFO: renamed from: a */
    public void mo1249a(NintendoAccount nintendoAccount, long j, C0918a.b bVar) {
        String str = String.format("%s/users/%s/mission_statuses", "/1.0.0", nintendoAccount.getNintendoAccountId());
        Map<String, String> mapA = m1256a(nintendoAccount);
        if (this.f1214d && j > 0) {
            mapA.put("Debug-Current-Timestamp", String.valueOf(j));
        }
        HashMap map = new HashMap();
        map.put("filter.mission.clientId.$eq", this.f1213c);
        map.put("filter.mission.countries.$contains", nintendoAccount.getCountry());
        map.put("filter.visible.$eq", "1");
        m1215a(str, mapA, (Map<String, String>) map, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p020c.InterfaceC0923a
    /* JADX INFO: renamed from: a */
    public void mo1250a(NintendoAccount nintendoAccount, Set<String> set, long j, C0918a.b bVar) {
        String str = String.format("%s/users/%s/gifts/receive", "/1.0.0", nintendoAccount.getNintendoAccountId());
        Map<String, String> mapA = m1256a(nintendoAccount);
        if (this.f1214d && j > 0) {
            mapA.put("Debug-Current-Timestamp", String.valueOf(j));
        }
        HashMap map = new HashMap();
        map.put("pointFlags", "google");
        if (set != null && !set.isEmpty()) {
            map.put("filter.id.$in", TextUtils.join(",", set));
        }
        m1217a(str, mapA, (Map<String, String>) map, (byte[]) null, C0849c.f862b, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p020c.InterfaceC0923a
    /* JADX INFO: renamed from: a */
    public void mo1251a(String str, C0918a.b bVar) {
        String str2 = String.format("%s/gateway/sdk/token", "/1.0.0");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("client_id", this.f1213c);
            jSONObject.put("session_token", str);
            m1217a(str2, (Map<String, String>) null, (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, false, bVar);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }
}
