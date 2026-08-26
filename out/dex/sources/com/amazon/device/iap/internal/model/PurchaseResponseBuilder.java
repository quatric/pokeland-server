package com.amazon.device.iap.internal.model;

import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserData;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PurchaseResponseBuilder {
    private Receipt receipt;
    private RequestId requestId;
    private PurchaseResponse.RequestStatus requestStatus;
    private UserData userData;

    public PurchaseResponse build() {
        return new PurchaseResponse(this);
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public RequestId getRequestId() {
        return this.requestId;
    }

    public PurchaseResponse.RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public PurchaseResponseBuilder setReceipt(Receipt receipt) {
        this.receipt = receipt;
        return this;
    }

    public PurchaseResponseBuilder setRequestId(RequestId requestId) {
        this.requestId = requestId;
        return this;
    }

    public PurchaseResponseBuilder setRequestStatus(PurchaseResponse.RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public PurchaseResponseBuilder setUserData(UserData userData) {
        this.userData = userData;
        return this;
    }
}
