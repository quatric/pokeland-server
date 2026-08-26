package com.amazon.device.iap.internal.p004b.p009e;

import com.amazon.device.iap.internal.model.UserDataResponseBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserDataResponse;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.e.a */
/* JADX INFO: compiled from: GetUserDataRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0219a extends C0218e {
    public C0219a(RequestId requestId) {
        super(requestId);
        C0221c c0221c = new C0221c(this);
        c0221c.m356b(new C0222d(this));
        m338a((AbstractC0232i) c0221c);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
        m339a((UserDataResponse) m342d().m346a());
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        UserDataResponse userDataResponseBuild = (UserDataResponse) m342d().m346a();
        if (userDataResponseBuild == null) {
            userDataResponseBuild = new UserDataResponseBuilder().setRequestId(m341c()).setRequestStatus(UserDataResponse.RequestStatus.FAILED).build();
        }
        m339a(userDataResponseBuild);
    }
}
