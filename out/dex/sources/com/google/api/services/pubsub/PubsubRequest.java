package com.google.api.services.pubsub;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class PubsubRequest<T> extends AbstractGoogleJsonClientRequest<T> {

    @Key("$.xgafv")
    private String $Xgafv;

    @Key("access_token")
    private String accessToken;

    @Key
    private String alt;

    @Key
    private String callback;

    @Key
    private String fields;

    @Key
    private String key;

    @Key("oauth_token")
    private String oauthToken;

    @Key
    private Boolean prettyPrint;

    @Key
    private String quotaUser;

    @Key("upload_protocol")
    private String uploadProtocol;

    @Key
    private String uploadType;

    public PubsubRequest(Pubsub pubsub, String str, String str2, Object obj, Class<T> cls) {
        super(pubsub, str, str2, obj, cls);
    }

    public String get$Xgafv() {
        return this.$Xgafv;
    }

    @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public final Pubsub getAbstractGoogleClient() {
        return (Pubsub) super.getAbstractGoogleClient();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getAlt() {
        return this.alt;
    }

    public String getCallback() {
        return this.callback;
    }

    public String getFields() {
        return this.fields;
    }

    public String getKey() {
        return this.key;
    }

    public String getOauthToken() {
        return this.oauthToken;
    }

    public Boolean getPrettyPrint() {
        return this.prettyPrint;
    }

    public String getQuotaUser() {
        return this.quotaUser;
    }

    public String getUploadProtocol() {
        return this.uploadProtocol;
    }

    public String getUploadType() {
        return this.uploadType;
    }

    @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
    public PubsubRequest<T> set(String str, Object obj) {
        return (PubsubRequest) super.set(str, obj);
    }

    /* JADX INFO: renamed from: set$Xgafv */
    public PubsubRequest<T> set$Xgafv2(String str) {
        this.$Xgafv = str;
        return this;
    }

    /* JADX INFO: renamed from: setAccessToken */
    public PubsubRequest<T> setAccessToken2(String str) {
        this.accessToken = str;
        return this;
    }

    /* JADX INFO: renamed from: setAlt */
    public PubsubRequest<T> setAlt2(String str) {
        this.alt = str;
        return this;
    }

    /* JADX INFO: renamed from: setCallback */
    public PubsubRequest<T> setCallback2(String str) {
        this.callback = str;
        return this;
    }

    @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public PubsubRequest<T> setDisableGZipContent(boolean z) {
        return (PubsubRequest) super.setDisableGZipContent(z);
    }

    /* JADX INFO: renamed from: setFields */
    public PubsubRequest<T> setFields2(String str) {
        this.fields = str;
        return this;
    }

    /* JADX INFO: renamed from: setKey */
    public PubsubRequest<T> setKey2(String str) {
        this.key = str;
        return this;
    }

    /* JADX INFO: renamed from: setOauthToken */
    public PubsubRequest<T> setOauthToken2(String str) {
        this.oauthToken = str;
        return this;
    }

    /* JADX INFO: renamed from: setPrettyPrint */
    public PubsubRequest<T> setPrettyPrint2(Boolean bool) {
        this.prettyPrint = bool;
        return this;
    }

    /* JADX INFO: renamed from: setQuotaUser */
    public PubsubRequest<T> setQuotaUser2(String str) {
        this.quotaUser = str;
        return this;
    }

    @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest
    public PubsubRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (PubsubRequest) super.setRequestHeaders(httpHeaders);
    }

    /* JADX INFO: renamed from: setUploadProtocol */
    public PubsubRequest<T> setUploadProtocol2(String str) {
        this.uploadProtocol = str;
        return this;
    }

    /* JADX INFO: renamed from: setUploadType */
    public PubsubRequest<T> setUploadType2(String str) {
        this.uploadType = str;
        return this;
    }
}
