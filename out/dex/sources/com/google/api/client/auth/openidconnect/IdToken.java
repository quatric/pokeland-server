package com.google.api.client.auth.openidconnect;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class IdToken extends JsonWebSignature {

    @Beta
    public static class Payload extends JsonWebToken.Payload {

        @Key("at_hash")
        private String accessTokenHash;

        @Key("auth_time")
        private Long authorizationTimeSeconds;

        @Key("azp")
        private String authorizedParty;

        @Key("acr")
        private String classReference;

        @Key("amr")
        private List<String> methodsReferences;

        @Key
        private String nonce;

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload, com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
        public Payload clone() {
            return (Payload) super.clone();
        }

        public final String getAccessTokenHash() {
            return this.accessTokenHash;
        }

        public final Long getAuthorizationTimeSeconds() {
            return this.authorizationTimeSeconds;
        }

        public final String getAuthorizedParty() {
            return this.authorizedParty;
        }

        public final String getClassReference() {
            return this.classReference;
        }

        public final List<String> getMethodsReferences() {
            return this.methodsReferences;
        }

        public final String getNonce() {
            return this.nonce;
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload, com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
        public Payload set(String str, Object obj) {
            return (Payload) super.set(str, obj);
        }

        public Payload setAccessTokenHash(String str) {
            this.accessTokenHash = str;
            return this;
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setAudience(Object obj) {
            return (Payload) super.setAudience(obj);
        }

        public Payload setAuthorizationTimeSeconds(Long l) {
            this.authorizationTimeSeconds = l;
            return this;
        }

        public Payload setAuthorizedParty(String str) {
            this.authorizedParty = str;
            return this;
        }

        public Payload setClassReference(String str) {
            this.classReference = str;
            return this;
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setExpirationTimeSeconds(Long l) {
            return (Payload) super.setExpirationTimeSeconds(l);
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setIssuedAtTimeSeconds(Long l) {
            return (Payload) super.setIssuedAtTimeSeconds(l);
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setIssuer(String str) {
            return (Payload) super.setIssuer(str);
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setJwtId(String str) {
            return (Payload) super.setJwtId(str);
        }

        public Payload setMethodsReferences(List<String> list) {
            this.methodsReferences = list;
            return this;
        }

        public Payload setNonce(String str) {
            this.nonce = str;
            return this;
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setNotBeforeTimeSeconds(Long l) {
            return (Payload) super.setNotBeforeTimeSeconds(l);
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setSubject(String str) {
            return (Payload) super.setSubject(str);
        }

        @Override // com.google.api.client.json.webtoken.JsonWebToken.Payload
        public Payload setType(String str) {
            return (Payload) super.setType(str);
        }
    }

    public IdToken(JsonWebSignature.Header header, Payload payload, byte[] bArr, byte[] bArr2) {
        super(header, payload, bArr, bArr2);
    }

    public static IdToken parse(JsonFactory jsonFactory, String str) throws IOException {
        JsonWebSignature jsonWebSignature = JsonWebSignature.parser(jsonFactory).setPayloadClass(Payload.class).parse(str);
        return new IdToken(jsonWebSignature.getHeader(), (Payload) jsonWebSignature.getPayload(), jsonWebSignature.getSignatureBytes(), jsonWebSignature.getSignedContentBytes());
    }

    @Override // com.google.api.client.json.webtoken.JsonWebToken
    public Payload getPayload() {
        return (Payload) super.getPayload();
    }

    public final boolean verifyAudience(Collection<String> collection) {
        List<String> audienceAsList = getPayload().getAudienceAsList();
        if (audienceAsList.isEmpty()) {
            return false;
        }
        return collection.containsAll(audienceAsList);
    }

    public final boolean verifyExpirationTime(long j, long j2) {
        return j <= (getPayload().getExpirationTimeSeconds().longValue() + j2) * 1000;
    }

    public final boolean verifyIssuedAtTime(long j, long j2) {
        return j >= (getPayload().getIssuedAtTimeSeconds().longValue() - j2) * 1000;
    }

    public final boolean verifyIssuer(String str) {
        return verifyIssuer(Collections.singleton(str));
    }

    public final boolean verifyIssuer(Collection<String> collection) {
        return collection.contains(getPayload().getIssuer());
    }

    public final boolean verifyTime(long j, long j2) {
        return verifyExpirationTime(j, j2) && verifyIssuedAtTime(j, j2);
    }
}
