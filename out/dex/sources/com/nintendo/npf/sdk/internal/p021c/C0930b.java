package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Gender;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.b */
/* JADX INFO: compiled from: BaasUserMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0930b extends AbstractC0931c<BaaSUser> {
    /* JADX WARN: Code duplicated, block: B:35:0x00c8  */
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public BaaSUser mo1260b(JSONObject jSONObject) throws JSONException {
        long j;
        long j2;
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        InquiryStatus inquiryStatus = null;
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("id");
        if (m1262a(jSONObject, "permissions")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("permissions");
            boolean z3 = jSONObject2.getBoolean("personalAnalytics");
            z2 = jSONObject2.getBoolean("personalNotification");
            j = jSONObject2.getLong("personalAnalyticsUpdatedAt");
            j2 = jSONObject2.getLong("personalNotificationUpdatedAt");
            z = z3;
        } else {
            j = 0;
            j2 = 0;
            z = false;
            z2 = false;
        }
        long j3 = jSONObject.getLong("createdAt");
        String string2 = (!m1262a(jSONObject, "nickname") || jSONObject.getString("nickname").length() <= 0) ? null : jSONObject.getString("nickname");
        String string3 = (!m1262a(jSONObject, "country") || jSONObject.getString("country").length() <= 0) ? null : jSONObject.getString("country");
        Gender gender = Gender.UNKNOWN;
        if (m1262a(jSONObject, "gender")) {
            String string4 = jSONObject.getString("gender");
            if (string4.equals("male")) {
                gender = Gender.MALE;
            } else {
                gender = string4.equals("female") ? Gender.FEMALE : Gender.UNKNOWN;
            }
        }
        if (m1262a(jSONObject, "birthday")) {
            String[] strArrSplit = jSONObject.getString("birthday").split("-");
            if (strArrSplit.length >= 3) {
                int i4 = Integer.parseInt(strArrSplit[0]);
                int i5 = Integer.parseInt(strArrSplit[1]);
                i3 = Integer.parseInt(strArrSplit[2]);
                i2 = i5;
                i = i4;
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
            }
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        if (m1262a(jSONObject, "hasUnreadCsComment")) {
            inquiryStatus = new InquiryStatus();
            inquiryStatus.setHavingUnreadComments(jSONObject.getBoolean("hasUnreadCsComment"));
        }
        return new BaaSUser(string, string2, string3, gender, i, i2, i3, z, z2, j, j2, inquiryStatus, j3);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(BaaSUser baaSUser) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
