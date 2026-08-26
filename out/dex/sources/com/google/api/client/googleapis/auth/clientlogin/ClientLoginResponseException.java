package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.HttpResponseException;
import com.google.api.client.util.Beta;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class ClientLoginResponseException extends HttpResponseException {
    private static final long serialVersionUID = 4974317674023010928L;
    private final transient ClientLogin.ErrorInfo details;

    ClientLoginResponseException(HttpResponseException.Builder builder, ClientLogin.ErrorInfo errorInfo) {
        super(builder);
        this.details = errorInfo;
    }

    public final ClientLogin.ErrorInfo getDetails() {
        return this.details;
    }
}
