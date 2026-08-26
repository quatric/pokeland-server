package com.google.api.client.googleapis.media;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class MediaHttpDownloader {
    public static final int MAXIMUM_CHUNK_SIZE = 33554432;
    private long bytesDownloaded;
    private long mediaContentLength;
    private MediaHttpDownloaderProgressListener progressListener;
    private final HttpRequestFactory requestFactory;
    private final HttpTransport transport;
    private boolean directDownloadEnabled = false;
    private int chunkSize = MAXIMUM_CHUNK_SIZE;
    private DownloadState downloadState = DownloadState.NOT_STARTED;
    private long lastBytePos = -1;

    public enum DownloadState {
        NOT_STARTED,
        MEDIA_IN_PROGRESS,
        MEDIA_COMPLETE
    }

    public MediaHttpDownloader(HttpTransport httpTransport, HttpRequestInitializer httpRequestInitializer) {
        this.transport = (HttpTransport) Preconditions.checkNotNull(httpTransport);
        this.requestFactory = httpRequestInitializer == null ? httpTransport.createRequestFactory() : httpTransport.createRequestFactory(httpRequestInitializer);
    }

    private HttpResponse executeCurrentRequest(long j, GenericUrl genericUrl, HttpHeaders httpHeaders, OutputStream outputStream) throws IOException {
        HttpRequest httpRequestBuildGetRequest = this.requestFactory.buildGetRequest(genericUrl);
        if (httpHeaders != null) {
            httpRequestBuildGetRequest.getHeaders().putAll(httpHeaders);
        }
        if (this.bytesDownloaded != 0 || j != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("bytes=");
            sb.append(this.bytesDownloaded);
            sb.append("-");
            if (j != -1) {
                sb.append(j);
            }
            httpRequestBuildGetRequest.getHeaders().setRange(sb.toString());
        }
        HttpResponse httpResponseExecute = httpRequestBuildGetRequest.execute();
        try {
            IOUtils.copy(httpResponseExecute.getContent(), outputStream);
            return httpResponseExecute;
        } finally {
            httpResponseExecute.disconnect();
        }
    }

    private long getNextByteIndex(String str) {
        if (str == null) {
            return 0L;
        }
        return Long.parseLong(str.substring(str.indexOf(45) + 1, str.indexOf(47))) + 1;
    }

    private void setMediaContentLength(String str) {
        if (str != null && this.mediaContentLength == 0) {
            this.mediaContentLength = Long.parseLong(str.substring(str.indexOf(47) + 1));
        }
    }

    private void updateStateAndNotifyListener(DownloadState downloadState) throws IOException {
        this.downloadState = downloadState;
        MediaHttpDownloaderProgressListener mediaHttpDownloaderProgressListener = this.progressListener;
        if (mediaHttpDownloaderProgressListener != null) {
            mediaHttpDownloaderProgressListener.progressChanged(this);
        }
    }

    public void download(GenericUrl genericUrl, HttpHeaders httpHeaders, OutputStream outputStream) throws IOException {
        Preconditions.checkArgument(this.downloadState == DownloadState.NOT_STARTED);
        genericUrl.put("alt", "media");
        if (this.directDownloadEnabled) {
            updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
            this.mediaContentLength = executeCurrentRequest(this.lastBytePos, genericUrl, httpHeaders, outputStream).getHeaders().getContentLength().longValue();
            this.bytesDownloaded = this.mediaContentLength;
            updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
            return;
        }
        while (true) {
            long jMin = (this.bytesDownloaded + ((long) this.chunkSize)) - 1;
            long j = this.lastBytePos;
            if (j != -1) {
                jMin = Math.min(j, jMin);
            }
            String contentRange = executeCurrentRequest(jMin, genericUrl, httpHeaders, outputStream).getHeaders().getContentRange();
            long nextByteIndex = getNextByteIndex(contentRange);
            setMediaContentLength(contentRange);
            long j2 = this.mediaContentLength;
            if (j2 <= nextByteIndex) {
                this.bytesDownloaded = j2;
                updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
                return;
            } else {
                this.bytesDownloaded = nextByteIndex;
                updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
            }
        }
    }

    public void download(GenericUrl genericUrl, OutputStream outputStream) throws IOException {
        download(genericUrl, null, outputStream);
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public long getLastBytePosition() {
        return this.lastBytePos;
    }

    public long getNumBytesDownloaded() {
        return this.bytesDownloaded;
    }

    public double getProgress() {
        long j = this.mediaContentLength;
        if (j == 0) {
            return 0.0d;
        }
        double d = this.bytesDownloaded;
        double d2 = j;
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }

    public MediaHttpDownloaderProgressListener getProgressListener() {
        return this.progressListener;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public boolean isDirectDownloadEnabled() {
        return this.directDownloadEnabled;
    }

    public MediaHttpDownloader setBytesDownloaded(long j) {
        Preconditions.checkArgument(j >= 0);
        this.bytesDownloaded = j;
        return this;
    }

    public MediaHttpDownloader setChunkSize(int i) {
        Preconditions.checkArgument(i > 0 && i <= 33554432);
        this.chunkSize = i;
        return this;
    }

    @Deprecated
    public MediaHttpDownloader setContentRange(long j, int i) {
        return setContentRange(j, i);
    }

    public MediaHttpDownloader setContentRange(long j, long j2) {
        Preconditions.checkArgument(j2 >= j);
        setBytesDownloaded(j);
        this.lastBytePos = j2;
        return this;
    }

    public MediaHttpDownloader setDirectDownloadEnabled(boolean z) {
        this.directDownloadEnabled = z;
        return this;
    }

    public MediaHttpDownloader setProgressListener(MediaHttpDownloaderProgressListener mediaHttpDownloaderProgressListener) {
        this.progressListener = mediaHttpDownloaderProgressListener;
        return this;
    }
}
