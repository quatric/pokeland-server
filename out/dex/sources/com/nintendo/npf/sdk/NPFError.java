package com.nintendo.npf.sdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NPFError {
    private static final long serialVersionUID = 1;
    protected int errorCode;
    protected String errorMessage;
    protected ErrorType errorType;

    public enum ErrorType {
        PROCESS_CANCEL(-2),
        USER_CANCEL(-1),
        NETWORK_ERROR(0),
        NPF_ERROR(1),
        INVALID_NA_TOKEN(2),
        NA_EULA_UPDATE(3),
        INVALID_NA_USER(4),
        MISMATCHED_NA_USER(5);


        /* JADX INFO: renamed from: a */
        private final int f1003a;

        ErrorType(int i) {
            this.f1003a = i;
        }

        public int getInt() {
            return this.f1003a;
        }
    }

    protected NPFError() {
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }
}
