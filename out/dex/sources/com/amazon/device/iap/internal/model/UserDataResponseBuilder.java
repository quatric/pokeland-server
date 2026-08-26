package com.amazon.device.iap.internal.model;

import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserData;
import com.amazon.device.iap.model.UserDataResponse;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UserDataResponseBuilder {
    private RequestId requestId;
    private UserDataResponse.RequestStatus requestStatus;
    private UserData userData;

    public UserDataResponse build() {
        return new UserDataResponse(this);
    }

    public RequestId getRequestId() {
        return this.requestId;
    }

    public UserDataResponse.RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public UserDataResponseBuilder setRequestId(RequestId requestId) {
        this.requestId = requestId;
        return this;
    }

    public UserDataResponseBuilder setRequestStatus(UserDataResponse.RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public UserDataResponseBuilder setUserData(UserData userData) {
        this.userData = userData;
        return this;
    }
}
