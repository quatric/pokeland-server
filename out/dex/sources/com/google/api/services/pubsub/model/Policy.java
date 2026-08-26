package com.google.api.services.pubsub.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Key;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Policy extends GenericJson {

    @Key
    private List<Binding> bindings;

    @Key
    private String etag;

    @Key
    private Integer version;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public Policy clone() {
        return (Policy) super.clone();
    }

    public byte[] decodeEtag() {
        return Base64.decodeBase64(this.etag);
    }

    public Policy encodeEtag(byte[] bArr) {
        this.etag = Base64.encodeBase64URLSafeString(bArr);
        return this;
    }

    public List<Binding> getBindings() {
        return this.bindings;
    }

    public String getEtag() {
        return this.etag;
    }

    public Integer getVersion() {
        return this.version;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public Policy set(String str, Object obj) {
        return (Policy) super.set(str, obj);
    }

    public Policy setBindings(List<Binding> list) {
        this.bindings = list;
        return this;
    }

    public Policy setEtag(String str) {
        this.etag = str;
        return this;
    }

    public Policy setVersion(Integer num) {
        this.version = num;
        return this;
    }
}
