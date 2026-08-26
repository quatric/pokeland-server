package com.google.api.client.http;

import com.google.api.client.util.Beta;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.LoggingStreamingContent;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import com.google.api.client.util.StreamingContent;
import com.google.api.client.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class HttpRequest {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.25.0 (gzip)";
    public static final String VERSION = "1.25.0";

    @Beta
    @Deprecated
    private BackOffPolicy backOffPolicy;
    private HttpContent content;
    private HttpEncoding encoding;
    private HttpExecuteInterceptor executeInterceptor;

    @Beta
    private HttpIOExceptionHandler ioExceptionHandler;
    private ObjectParser objectParser;
    private String requestMethod;
    private HttpResponseInterceptor responseInterceptor;
    private boolean suppressUserAgentSuffix;
    private final HttpTransport transport;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private GenericUrl url;
    private HttpHeaders headers = new HttpHeaders();
    private HttpHeaders responseHeaders = new HttpHeaders();
    private int numRetries = 10;
    private int contentLoggingLimit = 16384;
    private boolean loggingEnabled = true;
    private boolean curlLoggingEnabled = true;
    private int connectTimeout = 20000;
    private int readTimeout = 20000;
    private boolean followRedirects = true;
    private boolean throwExceptionOnExecuteError = true;

    @Beta
    @Deprecated
    private boolean retryOnExecuteIOException = false;
    private Sleeper sleeper = Sleeper.DEFAULT;

    HttpRequest(HttpTransport httpTransport, String str) {
        this.transport = httpTransport;
        setRequestMethod(str);
    }

    /* JADX WARN: Code duplicated, block: B:112:0x022e A[Catch: all -> 0x0277, TryCatch #2 {all -> 0x0277, blocks: (B:110:0x0228, B:112:0x022e, B:114:0x0232, B:117:0x023c, B:121:0x024e, B:123:0x0252, B:125:0x025e, B:128:0x026a, B:132:0x0273), top: B:166:0x0228 }] */
    /* JADX WARN: Code duplicated, block: B:114:0x0232 A[Catch: all -> 0x0277, TryCatch #2 {all -> 0x0277, blocks: (B:110:0x0228, B:112:0x022e, B:114:0x0232, B:117:0x023c, B:121:0x024e, B:123:0x0252, B:125:0x025e, B:128:0x026a, B:132:0x0273), top: B:166:0x0228 }] */
    /* JADX WARN: Code duplicated, block: B:115:0x0239  */
    /* JADX WARN: Code duplicated, block: B:117:0x023c A[Catch: all -> 0x0277, TryCatch #2 {all -> 0x0277, blocks: (B:110:0x0228, B:112:0x022e, B:114:0x0232, B:117:0x023c, B:121:0x024e, B:123:0x0252, B:125:0x025e, B:128:0x026a, B:132:0x0273), top: B:166:0x0228 }] */
    /* JADX WARN: Code duplicated, block: B:119:0x024a  */
    /* JADX WARN: Code duplicated, block: B:120:0x024c  */
    /* JADX WARN: Code duplicated, block: B:132:0x0273 A[Catch: all -> 0x0277, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x0277, blocks: (B:110:0x0228, B:112:0x022e, B:114:0x0232, B:117:0x023c, B:121:0x024e, B:123:0x0252, B:125:0x025e, B:128:0x026a, B:132:0x0273), top: B:166:0x0228 }] */
    /* JADX WARN: Code duplicated, block: B:138:0x027e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:139:0x0280  */
    /* JADX WARN: Code duplicated, block: B:140:0x0282  */
    /* JADX WARN: Code duplicated, block: B:145:0x028a  */
    /* JADX WARN: Code duplicated, block: B:147:0x028e  */
    /* JADX WARN: Code duplicated, block: B:159:0x02a8  */
    /* JADX WARN: Code duplicated, block: B:160:0x02a9 A[LOOP:0: B:10:0x0021->B:160:0x02a9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:166:0x0228 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:168:0x026a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:175:0x0288 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x009b  */
    /* JADX WARN: Code duplicated, block: B:33:0x009f  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:44:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:47:0x00de  */
    /* JADX WARN: Code duplicated, block: B:49:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:50:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:53:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:54:0x0100  */
    /* JADX WARN: Code duplicated, block: B:56:0x010d  */
    /* JADX WARN: Code duplicated, block: B:57:0x0112  */
    /* JADX WARN: Code duplicated, block: B:59:0x0116  */
    /* JADX WARN: Code duplicated, block: B:61:0x011a  */
    /* JADX WARN: Code duplicated, block: B:63:0x0137  */
    /* JADX WARN: Code duplicated, block: B:64:0x014d  */
    /* JADX WARN: Code duplicated, block: B:66:0x0151  */
    /* JADX WARN: Code duplicated, block: B:68:0x016c  */
    /* JADX WARN: Code duplicated, block: B:71:0x0187  */
    /* JADX WARN: Code duplicated, block: B:72:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:74:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:76:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:78:0x01bb  */
    /* JADX WARN: Code duplicated, block: B:80:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:82:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:87:0x01e9  */
    /* JADX WARN: Instruction removed from duplicated block: B:34:0x00a5, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:63:0x0137, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:66:0x0151, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:68:0x016c, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:71:0x0187, please report this as an issue */
    public HttpResponse execute() throws IOException {
        StringBuilder sb;
        StringBuilder sb2;
        String userAgent;
        HttpContent httpContent;
        boolean z;
        int i;
        StreamingContent streamingContent;
        boolean z2;
        IOException iOException;
        HttpResponse httpResponse;
        boolean zHandleResponse;
        boolean z3;
        long nextBackOffMillis;
        HttpResponseInterceptor httpResponseInterceptor;
        boolean z4;
        LowLevelHttpResponse lowLevelHttpResponseExecute;
        String type;
        StreamingContent loggingStreamingContent;
        HttpEncoding httpEncoding;
        String name;
        HttpEncodingStreamingContent httpEncodingStreamingContent;
        long jComputeLength;
        StreamingContent streamingContent2;
        String str;
        String string;
        HttpRequest httpRequest = this;
        Preconditions.checkArgument(httpRequest.numRetries >= 0);
        int i2 = httpRequest.numRetries;
        BackOffPolicy backOffPolicy = httpRequest.backOffPolicy;
        if (backOffPolicy != null) {
            backOffPolicy.reset();
        }
        Preconditions.checkNotNull(httpRequest.requestMethod);
        Preconditions.checkNotNull(httpRequest.url);
        int i3 = i2;
        HttpResponse httpResponse2 = null;
        while (true) {
            if (httpResponse2 != null) {
                httpResponse2.ignore();
            }
            HttpExecuteInterceptor httpExecuteInterceptor = httpRequest.executeInterceptor;
            if (httpExecuteInterceptor != null) {
                httpExecuteInterceptor.intercept(httpRequest);
            }
            String strBuild = httpRequest.url.build();
            LowLevelHttpRequest lowLevelHttpRequestBuildRequest = httpRequest.transport.buildRequest(httpRequest.requestMethod, strBuild);
            Logger logger = HttpTransport.LOGGER;
            boolean z5 = httpRequest.loggingEnabled && logger.isLoggable(Level.CONFIG);
            try {
                try {
                    if (z5) {
                        sb = new StringBuilder();
                        sb.append("-------------- REQUEST  --------------");
                        sb.append(StringUtils.LINE_SEPARATOR);
                        sb.append(httpRequest.requestMethod);
                        sb.append(' ');
                        sb.append(strBuild);
                        sb.append(StringUtils.LINE_SEPARATOR);
                        if (httpRequest.curlLoggingEnabled) {
                            sb2 = new StringBuilder("curl -v --compressed");
                            if (!httpRequest.requestMethod.equals("GET")) {
                                sb2.append(" -X ");
                                sb2.append(httpRequest.requestMethod);
                            }
                        }
                        userAgent = httpRequest.headers.getUserAgent();
                        if (!httpRequest.suppressUserAgentSuffix) {
                            if (userAgent == null) {
                                httpRequest.headers.setUserAgent(USER_AGENT_SUFFIX);
                            } else {
                                httpRequest.headers.setUserAgent(userAgent + " " + USER_AGENT_SUFFIX);
                            }
                        }
                        HttpHeaders.serializeHeaders(httpRequest.headers, sb, sb2, logger, lowLevelHttpRequestBuildRequest);
                        if (!httpRequest.suppressUserAgentSuffix) {
                            httpRequest.headers.setUserAgent(userAgent);
                        }
                        httpContent = httpRequest.content;
                        if (httpContent != null || httpContent.retrySupported()) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (httpContent != null) {
                            type = httpRequest.content.getType();
                            if (z5) {
                                loggingStreamingContent = new LoggingStreamingContent(httpContent, HttpTransport.LOGGER, Level.CONFIG, httpRequest.contentLoggingLimit);
                            } else {
                                loggingStreamingContent = httpContent;
                            }
                            httpEncoding = httpRequest.encoding;
                            if (httpEncoding == null) {
                                jComputeLength = httpRequest.content.getLength();
                                streamingContent2 = loggingStreamingContent;
                                name = null;
                            } else {
                                name = httpEncoding.getName();
                                httpEncodingStreamingContent = new HttpEncodingStreamingContent(loggingStreamingContent, httpRequest.encoding);
                                if (z) {
                                    jComputeLength = IOUtils.computeLength(httpEncodingStreamingContent);
                                } else {
                                    jComputeLength = -1;
                                }
                            }
                            if (z5) {
                                streamingContent2 = httpEncodingStreamingContent;
                                streamingContent2 = httpEncodingStreamingContent;
                                if (type != null) {
                                    StringBuilder sb3 = new StringBuilder();
                                    i = i3;
                                    sb3.append("Content-Type: ");
                                    sb3.append(type);
                                    string = sb3.toString();
                                    sb.append(string);
                                    sb.append(StringUtils.LINE_SEPARATOR);
                                    if (sb2 != null) {
                                        sb2.append(" -H '" + string + "'");
                                    }
                                } else {
                                    i = i3;
                                }
                                if (name != null) {
                                    str = "Content-Encoding: " + name;
                                    sb.append(str);
                                    sb.append(StringUtils.LINE_SEPARATOR);
                                    if (sb2 != null) {
                                        sb2.append(" -H '" + str + "'");
                                    }
                                }
                                if (jComputeLength >= 0) {
                                    sb.append("Content-Length: " + jComputeLength);
                                    sb.append(StringUtils.LINE_SEPARATOR);
                                }
                            } else {
                                streamingContent2 = httpEncodingStreamingContent;
                                streamingContent2 = httpEncodingStreamingContent;
                                i = i3;
                            }
                            if (sb2 != null) {
                                sb2.append(" -d '@-'");
                            }
                            lowLevelHttpRequestBuildRequest.setContentType(type);
                            lowLevelHttpRequestBuildRequest.setContentEncoding(name);
                            lowLevelHttpRequestBuildRequest.setContentLength(jComputeLength);
                            lowLevelHttpRequestBuildRequest.setStreamingContent(streamingContent2);
                            streamingContent = streamingContent2;
                        } else {
                            i = i3;
                        }
                        if (z5) {
                            streamingContent = httpContent;
                            logger.config(sb.toString());
                            if (sb2 != null) {
                                sb2.append(" -- '");
                                sb2.append(strBuild.replaceAll("'", "'\"'\"'"));
                                sb2.append("'");
                                if (streamingContent != null) {
                                    sb2.append(" << $$$");
                                }
                                logger.config(sb2.toString());
                            }
                        }
                        if (z || i <= 0) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        httpRequest = this;
                        lowLevelHttpRequestBuildRequest.setTimeout(httpRequest.connectTimeout, httpRequest.readTimeout);
                        lowLevelHttpResponseExecute = lowLevelHttpRequestBuildRequest.execute();
                        httpResponse = new HttpResponse(httpRequest, lowLevelHttpResponseExecute);
                        iOException = null;
                        if (httpResponse == null) {
                            try {
                                if (httpResponse.isSuccessStatusCode()) {
                                    if (httpResponse == null) {
                                        z4 = true;
                                    } else {
                                        z4 = false;
                                    }
                                    z3 = z4 & z2;
                                } else {
                                    if (httpRequest.unsuccessfulResponseHandler != null) {
                                        zHandleResponse = httpRequest.unsuccessfulResponseHandler.handleResponse(httpRequest, httpResponse, z2);
                                    } else {
                                        zHandleResponse = false;
                                    }
                                    if (!zHandleResponse) {
                                        if (!httpRequest.handleRedirect(httpResponse.getStatusCode(), httpResponse.getHeaders())) {
                                            zHandleResponse = true;
                                        } else if (z2 && httpRequest.backOffPolicy != null && httpRequest.backOffPolicy.isBackOffRequired(httpResponse.getStatusCode())) {
                                            nextBackOffMillis = httpRequest.backOffPolicy.getNextBackOffMillis();
                                            if (nextBackOffMillis != -1) {
                                                try {
                                                    httpRequest.sleeper.sleep(nextBackOffMillis);
                                                } catch (InterruptedException unused) {
                                                }
                                                zHandleResponse = true;
                                            }
                                        }
                                    }
                                    z3 = zHandleResponse & z2;
                                    if (z3) {
                                        httpResponse.ignore();
                                    }
                                }
                            } catch (Throwable th) {
                                if (httpResponse != null) {
                                    httpResponse.disconnect();
                                }
                                throw th;
                            }
                        } else {
                            if (httpResponse == null) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            z3 = z4 & z2;
                        }
                        i3 = i - 1;
                        if (!z3) {
                            if (httpResponse != null) {
                                throw iOException;
                            }
                            httpResponseInterceptor = httpRequest.responseInterceptor;
                            if (httpResponseInterceptor != null) {
                                httpResponseInterceptor.interceptResponse(httpResponse);
                            }
                            if (httpRequest.throwExceptionOnExecuteError || httpResponse.isSuccessStatusCode()) {
                                return httpResponse;
                            }
                            try {
                                throw new HttpResponseException(httpResponse);
                            } catch (Throwable th2) {
                                httpResponse.disconnect();
                                throw th2;
                            }
                        }
                        httpResponse2 = httpResponse;
                    } else {
                        sb = null;
                    }
                    httpResponse = new HttpResponse(httpRequest, lowLevelHttpResponseExecute);
                    iOException = null;
                    if (httpResponse == null) {
                        if (httpResponse == null) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        z3 = z4 & z2;
                    } else if (httpResponse.isSuccessStatusCode()) {
                        if (httpRequest.unsuccessfulResponseHandler != null) {
                            zHandleResponse = httpRequest.unsuccessfulResponseHandler.handleResponse(httpRequest, httpResponse, z2);
                        } else {
                            zHandleResponse = false;
                        }
                        if (!zHandleResponse) {
                            if (!httpRequest.handleRedirect(httpResponse.getStatusCode(), httpResponse.getHeaders())) {
                                zHandleResponse = true;
                            } else if (z2) {
                                nextBackOffMillis = httpRequest.backOffPolicy.getNextBackOffMillis();
                                if (nextBackOffMillis != -1) {
                                    httpRequest.sleeper.sleep(nextBackOffMillis);
                                    zHandleResponse = true;
                                }
                            }
                        }
                        z3 = zHandleResponse & z2;
                        if (z3) {
                            httpResponse.ignore();
                        }
                    } else {
                        if (httpResponse == null) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        z3 = z4 & z2;
                    }
                    i3 = i - 1;
                    if (!z3) {
                        if (httpResponse != null) {
                            throw iOException;
                        }
                        httpResponseInterceptor = httpRequest.responseInterceptor;
                        if (httpResponseInterceptor != null) {
                            httpResponseInterceptor.interceptResponse(httpResponse);
                        }
                        if (httpRequest.throwExceptionOnExecuteError) {
                        }
                        return httpResponse;
                    }
                    httpResponse2 = httpResponse;
                } catch (Throwable th3) {
                    InputStream content = lowLevelHttpResponseExecute.getContent();
                    if (content != null) {
                        content.close();
                    }
                    throw th3;
                }
                lowLevelHttpResponseExecute = lowLevelHttpRequestBuildRequest.execute();
            } catch (IOException e) {
                iOException = e;
                if (!httpRequest.retryOnExecuteIOException) {
                    HttpIOExceptionHandler httpIOExceptionHandler = httpRequest.ioExceptionHandler;
                    if (httpIOExceptionHandler == null) {
                        throw iOException;
                    }
                    if (!httpIOExceptionHandler.handleIOException(httpRequest, z2)) {
                        throw iOException;
                    }
                }
                if (z5) {
                    logger.log(Level.WARNING, "exception thrown while executing request", (Throwable) iOException);
                }
                httpResponse = null;
            }
            sb2 = null;
            userAgent = httpRequest.headers.getUserAgent();
            if (!httpRequest.suppressUserAgentSuffix) {
                if (userAgent == null) {
                    httpRequest.headers.setUserAgent(USER_AGENT_SUFFIX);
                } else {
                    httpRequest.headers.setUserAgent(userAgent + " " + USER_AGENT_SUFFIX);
                }
            }
            HttpHeaders.serializeHeaders(httpRequest.headers, sb, sb2, logger, lowLevelHttpRequestBuildRequest);
            if (!httpRequest.suppressUserAgentSuffix) {
                httpRequest.headers.setUserAgent(userAgent);
            }
            httpContent = httpRequest.content;
            if (httpContent != null) {
                z = true;
            } else {
                z = true;
            }
            if (httpContent != null) {
                type = httpRequest.content.getType();
                if (z5) {
                    loggingStreamingContent = new LoggingStreamingContent(httpContent, HttpTransport.LOGGER, Level.CONFIG, httpRequest.contentLoggingLimit);
                } else {
                    loggingStreamingContent = httpContent;
                }
                httpEncoding = httpRequest.encoding;
                if (httpEncoding == null) {
                    jComputeLength = httpRequest.content.getLength();
                    streamingContent2 = loggingStreamingContent;
                    name = null;
                } else {
                    name = httpEncoding.getName();
                    httpEncodingStreamingContent = new HttpEncodingStreamingContent(loggingStreamingContent, httpRequest.encoding);
                    if (z) {
                        jComputeLength = IOUtils.computeLength(httpEncodingStreamingContent);
                    } else {
                        jComputeLength = -1;
                    }
                }
                if (z5) {
                    streamingContent2 = httpEncodingStreamingContent;
                    streamingContent2 = httpEncodingStreamingContent;
                    if (type != null) {
                        StringBuilder sb4 = new StringBuilder();
                        i = i3;
                        sb4.append("Content-Type: ");
                        sb4.append(type);
                        string = sb4.toString();
                        sb.append(string);
                        sb.append(StringUtils.LINE_SEPARATOR);
                        if (sb2 != null) {
                            sb2.append(" -H '" + string + "'");
                        }
                    } else {
                        i = i3;
                    }
                    if (name != null) {
                        str = "Content-Encoding: " + name;
                        sb.append(str);
                        sb.append(StringUtils.LINE_SEPARATOR);
                        if (sb2 != null) {
                            sb2.append(" -H '" + str + "'");
                        }
                    }
                    if (jComputeLength >= 0) {
                        sb.append("Content-Length: " + jComputeLength);
                        sb.append(StringUtils.LINE_SEPARATOR);
                    }
                } else {
                    streamingContent2 = httpEncodingStreamingContent;
                    streamingContent2 = httpEncodingStreamingContent;
                    i = i3;
                }
                if (sb2 != null) {
                    sb2.append(" -d '@-'");
                }
                lowLevelHttpRequestBuildRequest.setContentType(type);
                lowLevelHttpRequestBuildRequest.setContentEncoding(name);
                lowLevelHttpRequestBuildRequest.setContentLength(jComputeLength);
                lowLevelHttpRequestBuildRequest.setStreamingContent(streamingContent2);
                streamingContent = streamingContent2;
            } else {
                i = i3;
            }
            if (z5) {
                streamingContent = httpContent;
                logger.config(sb.toString());
                if (sb2 != null) {
                    sb2.append(" -- '");
                    sb2.append(strBuild.replaceAll("'", "'\"'\"'"));
                    sb2.append("'");
                    if (streamingContent != null) {
                        sb2.append(" << $$$");
                    }
                    logger.config(sb2.toString());
                }
            }
            if (z) {
                z2 = false;
            } else {
                z2 = false;
            }
            httpRequest = this;
            lowLevelHttpRequestBuildRequest.setTimeout(httpRequest.connectTimeout, httpRequest.readTimeout);
        }
    }

    @Beta
    public Future<HttpResponse> executeAsync() {
        return executeAsync(Executors.newSingleThreadExecutor());
    }

    @Beta
    public Future<HttpResponse> executeAsync(Executor executor) {
        FutureTask futureTask = new FutureTask(new Callable<HttpResponse>() { // from class: com.google.api.client.http.HttpRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public HttpResponse call() throws Exception {
                return HttpRequest.this.execute();
            }
        });
        executor.execute(futureTask);
        return futureTask;
    }

    @Beta
    @Deprecated
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public HttpContent getContent() {
        return this.content;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpEncoding getEncoding() {
        return this.encoding;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Beta
    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }

    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }

    public int getNumberOfRetries() {
        return this.numRetries;
    }

    public final ObjectParser getParser() {
        return this.objectParser;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }

    @Beta
    @Deprecated
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }

    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }

    public GenericUrl getUrl() {
        return this.url;
    }

    public boolean handleRedirect(int i, HttpHeaders httpHeaders) {
        String location = httpHeaders.getLocation();
        if (!getFollowRedirects() || !HttpStatusCodes.isRedirect(i) || location == null) {
            return false;
        }
        setUrl(new GenericUrl(this.url.toURL(location)));
        if (i == 303) {
            setRequestMethod("GET");
            setContent(null);
        }
        String str = (String) null;
        this.headers.setAuthorization(str);
        this.headers.setIfMatch(str);
        this.headers.setIfNoneMatch(str);
        this.headers.setIfModifiedSince(str);
        this.headers.setIfUnmodifiedSince(str);
        this.headers.setIfRange(str);
        return true;
    }

    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    @Beta
    @Deprecated
    public HttpRequest setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
        return this;
    }

    public HttpRequest setConnectTimeout(int i) {
        Preconditions.checkArgument(i >= 0);
        this.connectTimeout = i;
        return this;
    }

    public HttpRequest setContent(HttpContent httpContent) {
        this.content = httpContent;
        return this;
    }

    public HttpRequest setContentLoggingLimit(int i) {
        Preconditions.checkArgument(i >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = i;
        return this;
    }

    public HttpRequest setCurlLoggingEnabled(boolean z) {
        this.curlLoggingEnabled = z;
        return this;
    }

    public HttpRequest setEncoding(HttpEncoding httpEncoding) {
        this.encoding = httpEncoding;
        return this;
    }

    public HttpRequest setFollowRedirects(boolean z) {
        this.followRedirects = z;
        return this;
    }

    public HttpRequest setHeaders(HttpHeaders httpHeaders) {
        this.headers = (HttpHeaders) Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    @Beta
    public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler httpIOExceptionHandler) {
        this.ioExceptionHandler = httpIOExceptionHandler;
        return this;
    }

    public HttpRequest setInterceptor(HttpExecuteInterceptor httpExecuteInterceptor) {
        this.executeInterceptor = httpExecuteInterceptor;
        return this;
    }

    public HttpRequest setLoggingEnabled(boolean z) {
        this.loggingEnabled = z;
        return this;
    }

    public HttpRequest setNumberOfRetries(int i) {
        Preconditions.checkArgument(i >= 0);
        this.numRetries = i;
        return this;
    }

    public HttpRequest setParser(ObjectParser objectParser) {
        this.objectParser = objectParser;
        return this;
    }

    public HttpRequest setReadTimeout(int i) {
        Preconditions.checkArgument(i >= 0);
        this.readTimeout = i;
        return this;
    }

    public HttpRequest setRequestMethod(String str) {
        Preconditions.checkArgument(str == null || HttpMediaType.matchesToken(str));
        this.requestMethod = str;
        return this;
    }

    public HttpRequest setResponseHeaders(HttpHeaders httpHeaders) {
        this.responseHeaders = (HttpHeaders) Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    public HttpRequest setResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        this.responseInterceptor = httpResponseInterceptor;
        return this;
    }

    @Beta
    @Deprecated
    public HttpRequest setRetryOnExecuteIOException(boolean z) {
        this.retryOnExecuteIOException = z;
        return this;
    }

    public HttpRequest setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }

    public HttpRequest setSuppressUserAgentSuffix(boolean z) {
        this.suppressUserAgentSuffix = z;
        return this;
    }

    public HttpRequest setThrowExceptionOnExecuteError(boolean z) {
        this.throwExceptionOnExecuteError = z;
        return this;
    }

    public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler httpUnsuccessfulResponseHandler) {
        this.unsuccessfulResponseHandler = httpUnsuccessfulResponseHandler;
        return this;
    }

    public HttpRequest setUrl(GenericUrl genericUrl) {
        this.url = (GenericUrl) Preconditions.checkNotNull(genericUrl);
        return this;
    }
}
