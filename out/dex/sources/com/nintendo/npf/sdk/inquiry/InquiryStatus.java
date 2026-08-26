package com.nintendo.npf.sdk.inquiry;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class InquiryStatus {
    private boolean isHavingUnreadComments;

    public interface CheckCallback {
        void onComplete(InquiryStatus inquiryStatus, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.inquiry.InquiryStatus$a */
    private static class C0874a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1021a = InterfaceC0875a.a.m1072b();
    }

    public static void check(final CheckCallback checkCallback) {
        C0874a.f1021a.mo1062p().m1652a(new CheckCallback() { // from class: com.nintendo.npf.sdk.inquiry.InquiryStatus.1
            @Override // com.nintendo.npf.sdk.inquiry.InquiryStatus.CheckCallback
            public void onComplete(InquiryStatus inquiryStatus, NPFError nPFError) {
                CheckCallback checkCallback2 = checkCallback;
                if (checkCallback2 != null) {
                    checkCallback2.onComplete(inquiryStatus, nPFError);
                }
            }
        });
    }

    public boolean isHavingUnreadComments() {
        return this.isHavingUnreadComments;
    }

    public void setHavingUnreadComments(boolean z) {
        this.isHavingUnreadComments = z;
    }
}
