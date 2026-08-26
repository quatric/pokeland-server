package com.fasterxml.jackson.core.util;

import java.io.IOException;
import java.io.Serializable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class RequestPayload implements Serializable {
    private static final long serialVersionUID = 1;
    protected String _charset;
    protected byte[] _payloadAsBytes;
    protected CharSequence _payloadAsText;

    public RequestPayload(CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsText = charSequence;
    }

    public RequestPayload(byte[] bArr, String str) {
        if (bArr == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsBytes = bArr;
        this._charset = (str == null || str.isEmpty()) ? "UTF-8" : str;
    }

    public Object getRawPayload() {
        byte[] bArr = this._payloadAsBytes;
        return bArr != null ? bArr : this._payloadAsText;
    }

    public String toString() {
        byte[] bArr = this._payloadAsBytes;
        if (bArr == null) {
            return this._payloadAsText.toString();
        }
        try {
            return new String(bArr, this._charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
