package com.amazon.device.iap.internal;

import android.content.Context;
import android.content.Intent;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.RequestId;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.c */
/* JADX INFO: compiled from: RequestHandler.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface InterfaceC0233c {
    /* JADX INFO: renamed from: a */
    void mo317a(Context context, Intent intent);

    /* JADX INFO: renamed from: a */
    void mo318a(RequestId requestId);

    /* JADX INFO: renamed from: a */
    void mo319a(RequestId requestId, String str);

    /* JADX INFO: renamed from: a */
    void mo320a(RequestId requestId, String str, FulfillmentResult fulfillmentResult);

    /* JADX INFO: renamed from: a */
    void mo321a(RequestId requestId, Set<String> set);

    /* JADX INFO: renamed from: a */
    void mo322a(RequestId requestId, boolean z);
}
