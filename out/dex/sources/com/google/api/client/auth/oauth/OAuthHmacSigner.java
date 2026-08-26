package com.google.api.client.auth.oauth;

import com.google.api.client.util.Base64;
import com.google.api.client.util.Beta;
import com.google.api.client.util.StringUtils;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class OAuthHmacSigner implements OAuthSigner {
    public String clientSharedSecret;
    public String tokenSharedSecret;

    @Override // com.google.api.client.auth.oauth.OAuthSigner
    public String computeSignature(String str) throws GeneralSecurityException {
        StringBuilder sb = new StringBuilder();
        String str2 = this.clientSharedSecret;
        if (str2 != null) {
            sb.append(OAuthParameters.escape(str2));
        }
        sb.append(Typography.amp);
        String str3 = this.tokenSharedSecret;
        if (str3 != null) {
            sb.append(OAuthParameters.escape(str3));
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(StringUtils.getBytesUtf8(sb.toString()), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKeySpec);
        return Base64.encodeBase64String(mac.doFinal(StringUtils.getBytesUtf8(str)));
    }

    @Override // com.google.api.client.auth.oauth.OAuthSigner
    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }
}
