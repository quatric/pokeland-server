package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class InquiryStatusEventHandler implements InquiryStatus.CheckCallback {

    /* JADX INFO: renamed from: a */
    private long f1512a;

    /* JADX INFO: renamed from: b */
    private long f1513b;

    public InquiryStatusEventHandler() {
        this.f1512a = -1L;
        this.f1513b = -1L;
    }

    public InquiryStatusEventHandler(long j, long j2) {
        this.f1512a = -1L;
        this.f1513b = -1L;
        this.f1512a = j;
        this.f1513b = j2;
    }

    public static void check(long j, long j2) {
        InquiryStatus.check(new InquiryStatusEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.inquiry.InquiryStatus.CheckCallback
    public void onComplete(InquiryStatus inquiryStatus, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (inquiryStatus != null) {
            try {
                string = NativeBridgeUtil.toJsonFromInquiryStatus(inquiryStatus).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1512a, this.f1513b, str2, string2);
            }
        } else {
            string = null;
        }
        if (nPFError != null) {
            try {
                string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e2) {
                str = string;
                e = e2;
                e.printStackTrace();
                str2 = str;
            }
        }
        str2 = string;
        onRetrieveCallback(this.f1512a, this.f1513b, str2, string2);
    }
}
