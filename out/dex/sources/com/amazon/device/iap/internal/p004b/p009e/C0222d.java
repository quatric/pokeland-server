package com.amazon.device.iap.internal.p004b.p009e;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.model.UserDataResponseBuilder;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.UserData;
import com.amazon.device.iap.model.UserDataResponse;
import com.amazon.venezia.command.SuccessResult;
import java.util.Map;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.e.d */
/* JADX INFO: compiled from: GetUserIdCommandV1.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0222d extends AbstractC0220b {

    /* JADX INFO: renamed from: b */
    private static final String f192b = "d";

    public C0222d(C0218e c0218e) {
        super(c0218e, "1.0");
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        C0246e.m412a(f192b, "onSuccessInternal: result = " + successResult);
        Map data = successResult.getData();
        C0246e.m412a(f192b, "data: " + data);
        String str = (String) data.get("userId");
        C0218e c0218eB = m355b();
        if (C0245d.m411a(str)) {
            c0218eB.m342d().m348a(new UserDataResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(UserDataResponse.RequestStatus.FAILED).build());
            return false;
        }
        UserData userDataBuild = new UserDataBuilder().setUserId(str).setMarketplace(f190a).build();
        UserDataResponse userDataResponseBuild = new UserDataResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(UserDataResponse.RequestStatus.SUCCESSFUL).setUserData(userDataBuild).build();
        c0218eB.m342d().m349a("userId", userDataBuild.getUserId());
        c0218eB.m342d().m348a(userDataResponseBuild);
        return true;
    }
}
