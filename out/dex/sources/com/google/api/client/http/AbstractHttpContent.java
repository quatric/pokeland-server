package com.google.api.client.http;

import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractHttpContent implements HttpContent {
    private long computedLength;
    private HttpMediaType mediaType;

    protected AbstractHttpContent(HttpMediaType httpMediaType) {
        this.computedLength = -1L;
        this.mediaType = httpMediaType;
    }

    protected AbstractHttpContent(String str) {
        this(str == null ? null : new HttpMediaType(str));
    }

    public static long computeLength(HttpContent httpContent) throws IOException {
        if (httpContent.retrySupported()) {
            return IOUtils.computeLength(httpContent);
        }
        return -1L;
    }

    protected long computeLength() throws IOException {
        return computeLength(this);
    }

    protected final Charset getCharset() {
        HttpMediaType httpMediaType = this.mediaType;
        return (httpMediaType == null || httpMediaType.getCharsetParameter() == null) ? Charsets.UTF_8 : this.mediaType.getCharsetParameter();
    }

    @Override // com.google.api.client.http.HttpContent
    public long getLength() throws IOException {
        if (this.computedLength == -1) {
            this.computedLength = computeLength();
        }
        return this.computedLength;
    }

    public final HttpMediaType getMediaType() {
        return this.mediaType;
    }

    @Override // com.google.api.client.http.HttpContent
    public String getType() {
        HttpMediaType httpMediaType = this.mediaType;
        if (httpMediaType == null) {
            return null;
        }
        return httpMediaType.build();
    }

    @Override // com.google.api.client.http.HttpContent
    public boolean retrySupported() {
        return true;
    }

    public AbstractHttpContent setMediaType(HttpMediaType httpMediaType) {
        this.mediaType = httpMediaType;
        return this;
    }
}
