package com.amazon.device.iap.model;

import com.amazon.device.iap.internal.model.UserDataResponseBuilder;
import com.amazon.device.iap.internal.util.C0245d;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UserDataResponse {
    private static final String REQUEST_ID = "REQUEST_ID";
    private static final String REQUEST_STATUS = "REQUEST_STATUS";
    private static final String TO_STRING_FORMAT = "(%s, requestId: \"%s\", requestStatus: \"%s\", userData: \"%s\")";
    private static final String USER_DATA = "USER_DATA";
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final UserData userData;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        NOT_SUPPORTED
    }

    public UserDataResponse(UserDataResponseBuilder userDataResponseBuilder) {
        C0245d.m408a(userDataResponseBuilder.getRequestId(), "requestId");
        C0245d.m408a(userDataResponseBuilder.getRequestStatus(), "requestStatus");
        this.requestId = userDataResponseBuilder.getRequestId();
        this.requestStatus = userDataResponseBuilder.getRequestStatus();
        this.userData = userDataResponseBuilder.getUserData();
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
        return jSONObject;
    }

    public String toString() {
        Object[] objArr = new Object[4];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        RequestStatus requestStatus = this.requestStatus;
        objArr[2] = requestStatus != null ? requestStatus.toString() : "null";
        UserData userData = this.userData;
        objArr[3] = userData != null ? userData.toString() : "null";
        return String.format(TO_STRING_FORMAT, objArr);
    }
}
