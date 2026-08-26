package com.google.api.client.http.apache;

import com.google.api.client.http.LowLevelHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class ApacheHttpResponse extends LowLevelHttpResponse {
    private final Header[] allHeaders;
    private final HttpRequestBase request;
    private final HttpResponse response;

    ApacheHttpResponse(HttpRequestBase httpRequestBase, HttpResponse httpResponse) {
        this.request = httpRequestBase;
        this.response = httpResponse;
        this.allHeaders = httpResponse.getAllHeaders();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public void disconnect() {
        this.request.abort();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public InputStream getContent() throws IOException {
        HttpEntity entity = this.response.getEntity();
        if (entity == null) {
            return null;
        }
        return entity.getContent();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getContentEncoding() {
        Header contentEncoding;
        HttpEntity entity = this.response.getEntity();
        if (entity == null || (contentEncoding = entity.getContentEncoding()) == null) {
            return null;
        }
        return contentEncoding.getValue();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public long getContentLength() {
        HttpEntity entity = this.response.getEntity();
        if (entity == null) {
            return -1L;
        }
        return entity.getContentLength();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getContentType() {
        Header contentType;
        HttpEntity entity = this.response.getEntity();
        if (entity == null || (contentType = entity.getContentType()) == null) {
            return null;
        }
        return contentType.getValue();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public int getHeaderCount() {
        return this.allHeaders.length;
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getHeaderName(int i) {
        return this.allHeaders[i].getName();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getHeaderValue(int i) {
        return this.allHeaders[i].getValue();
    }

    public String getHeaderValue(String str) {
        return this.response.getLastHeader(str).getValue();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getReasonPhrase() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine == null) {
            return null;
        }
        return statusLine.getReasonPhrase();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public int getStatusCode() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine == null) {
            return 0;
        }
        return statusLine.getStatusCode();
    }

    @Override // com.google.api.client.http.LowLevelHttpResponse
    public String getStatusLine() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine == null) {
            return null;
        }
        return statusLine.toString();
    }
}
