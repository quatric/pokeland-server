package com.amazon.device.iap.model;

import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.util.C0245d;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PurchaseResponse {
    private static final String RECEIPT = "receipt";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_STATUS = "requestStatus";
    private static final String TO_STRING_FORMAT = "(%s, requestId: \"%s\", purchaseRequestStatus: \"%s\", userId: \"%s\", receipt: %s)";
    private static final String USER_DATA = "userData";
    private final Receipt receipt;
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final UserData userData;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        INVALID_SKU,
        ALREADY_PURCHASED,
        NOT_SUPPORTED;

        public static RequestStatus safeValueOf(String str) {
            if (C0245d.m411a(str)) {
                return null;
            }
            return "ALREADY_ENTITLED".equalsIgnoreCase(str) ? ALREADY_PURCHASED : valueOf(str.toUpperCase());
        }
    }

    public PurchaseResponse(PurchaseResponseBuilder purchaseResponseBuilder) {
        C0245d.m408a(purchaseResponseBuilder.getRequestId(), REQUEST_ID);
        C0245d.m408a(purchaseResponseBuilder.getRequestStatus(), REQUEST_STATUS);
        if (purchaseResponseBuilder.getRequestStatus() == RequestStatus.SUCCESSFUL) {
            C0245d.m408a(purchaseResponseBuilder.getReceipt(), RECEIPT);
            C0245d.m408a(purchaseResponseBuilder.getUserData(), USER_DATA);
        }
        this.requestId = purchaseResponseBuilder.getRequestId();
        this.userData = purchaseResponseBuilder.getUserData();
        this.receipt = purchaseResponseBuilder.getReceipt();
        this.requestStatus = purchaseResponseBuilder.getRequestStatus();
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public RequestId getRequestId() {
        return this.requestId;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(REQUEST_ID, this.requestId);
        jSONObject.put(REQUEST_STATUS, this.requestStatus);
        UserData userData = this.userData;
        jSONObject.put(USER_DATA, userData != null ? userData.toJSON() : "");
        jSONObject.put(RECEIPT, getReceipt() != null ? getReceipt().toJSON() : "");
        return jSONObject;
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        RequestStatus requestStatus = this.requestStatus;
        objArr[2] = requestStatus != null ? requestStatus.toString() : "null";
        objArr[3] = this.userData;
        objArr[4] = this.receipt;
        return String.format(TO_STRING_FORMAT, objArr);
    }
}
