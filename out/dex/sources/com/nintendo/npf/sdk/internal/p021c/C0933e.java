package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.e */
/* JADX INFO: compiled from: InquiryStatusMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0933e extends AbstractC0931c<InquiryStatus> {
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public InquiryStatus mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        InquiryStatus inquiryStatus = new InquiryStatus();
        if (m1262a(jSONObject, "hasUnreadCsComment")) {
            inquiryStatus.setHavingUnreadComments(jSONObject.getBoolean("hasUnreadCsComment"));
        }
        return inquiryStatus;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(InquiryStatus inquiryStatus) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
