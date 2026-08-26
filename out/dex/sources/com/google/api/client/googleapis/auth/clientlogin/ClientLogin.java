package com.google.api.client.googleapis.auth.clientlogin;

import android.support.v4.app.NotificationCompat;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class ClientLogin {

    @Key
    public String accountType;

    @Key(FirebaseAnalytics.Param.SOURCE)
    public String applicationName;

    @Key(NotificationCompat.CATEGORY_SERVICE)
    public String authTokenType;

    @Key("logincaptcha")
    public String captchaAnswer;

    @Key("logintoken")
    public String captchaToken;

    @Key("Passwd")
    public String password;
    public GenericUrl serverUrl = new GenericUrl("https://www.google.com");
    public HttpTransport transport;

    @Key("Email")
    public String username;

    public static final class ErrorInfo {

        @Key("CaptchaToken")
        public String captchaToken;

        @Key("CaptchaUrl")
        public String captchaUrl;

        @Key("Error")
        public String error;

        @Key("Url")
        public String url;
    }

    public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer {

        @Key("Auth")
        public String auth;

        public String getAuthorizationHeaderValue() {
            return ClientLogin.getAuthorizationHeaderValue(this.auth);
        }

        @Override // com.google.api.client.http.HttpRequestInitializer
        public void initialize(HttpRequest httpRequest) {
            httpRequest.setInterceptor(this);
        }

        @Override // com.google.api.client.http.HttpExecuteInterceptor
        public void intercept(HttpRequest httpRequest) {
            httpRequest.getHeaders().setAuthorization(getAuthorizationHeaderValue());
        }
    }

    public static String getAuthorizationHeaderValue(String str) {
        return "GoogleLogin auth=" + str;
    }

    public Response authenticate() throws IOException {
        GenericUrl genericUrlClone = this.serverUrl.clone();
        genericUrlClone.appendRawPath("/accounts/ClientLogin");
        HttpRequest httpRequestBuildPostRequest = this.transport.createRequestFactory().buildPostRequest(genericUrlClone, new UrlEncodedContent(this));
        httpRequestBuildPostRequest.setParser(AuthKeyValueParser.INSTANCE);
        httpRequestBuildPostRequest.setContentLoggingLimit(0);
        httpRequestBuildPostRequest.setThrowExceptionOnExecuteError(false);
        HttpResponse httpResponseExecute = httpRequestBuildPostRequest.execute();
        if (httpResponseExecute.isSuccessStatusCode()) {
            return (Response) httpResponseExecute.parseAs(Response.class);
        }
        HttpResponseException.Builder builder = new HttpResponseException.Builder(httpResponseExecute.getStatusCode(), httpResponseExecute.getStatusMessage(), httpResponseExecute.getHeaders());
        ErrorInfo errorInfo = (ErrorInfo) httpResponseExecute.parseAs(ErrorInfo.class);
        String string = errorInfo.toString();
        StringBuilder sbComputeMessageBuffer = HttpResponseException.computeMessageBuffer(httpResponseExecute);
        if (!Strings.isNullOrEmpty(string)) {
            sbComputeMessageBuffer.append(StringUtils.LINE_SEPARATOR);
            sbComputeMessageBuffer.append(string);
            builder.setContent(string);
        }
        builder.setMessage(sbComputeMessageBuffer.toString());
        throw new ClientLoginResponseException(builder, errorInfo);
    }
}
