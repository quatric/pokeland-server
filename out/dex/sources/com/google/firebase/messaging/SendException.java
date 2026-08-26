package com.google.firebase.messaging;

import java.util.Locale;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SendException extends Exception {
    public static final int ERROR_INVALID_PARAMETERS = 1;
    public static final int ERROR_SIZE = 2;
    public static final int ERROR_TOO_MANY_MESSAGES = 4;
    public static final int ERROR_TTL_EXCEEDED = 3;
    public static final int ERROR_UNKNOWN = 0;
    private final int errorCode;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    SendException(String str) {
        super(str);
        int i = 0;
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.US);
            byte b = -1;
            switch (lowerCase.hashCode()) {
                case -1743242157:
                    if (lowerCase.equals("service_not_available")) {
                        b = 3;
                    }
                    break;
                case -1290953729:
                    if (lowerCase.equals("toomanymessages")) {
                        b = 4;
                    }
                    break;
                case -920906446:
                    if (lowerCase.equals("invalid_parameters")) {
                        b = 0;
                    }
                    break;
                case -617027085:
                    if (lowerCase.equals("messagetoobig")) {
                        b = 2;
                    }
                    break;
                case -95047692:
                    if (lowerCase.equals("missing_to")) {
                        b = 1;
                    }
                    break;
            }
            if (b == 0 || b == 1) {
                i = 1;
            } else if (b == 2) {
                i = 2;
            } else if (b == 3) {
                i = 3;
            } else if (b == 4) {
                i = 4;
            }
        }
        this.errorCode = i;
    }

    public final int getErrorCode() {
        return this.errorCode;
    }
}
