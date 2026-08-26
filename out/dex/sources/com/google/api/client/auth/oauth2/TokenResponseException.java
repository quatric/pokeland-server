package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class TokenResponseException extends HttpResponseException {
    private static final long serialVersionUID = 4020689092957439244L;
    private final transient TokenErrorResponse details;

    TokenResponseException(HttpResponseException.Builder builder, TokenErrorResponse tokenErrorResponse) {
        super(builder);
        this.details = tokenErrorResponse;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.google.api.client.auth.oauth2.TokenErrorResponse] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.google.api.client.auth.oauth2.TokenErrorResponse] */
    public static TokenResponseException from(JsonFactory jsonFactory, HttpResponse httpResponse) throws IOException {
        ?? r6;
        HttpResponseException.Builder builder = new HttpResponseException.Builder(httpResponse.getStatusCode(), httpResponse.getStatusMessage(), httpResponse.getHeaders());
        Preconditions.checkNotNull(jsonFactory);
        String contentType = httpResponse.getContentType();
        String asString = null;
        try {
            if (httpResponse.isSuccessStatusCode() || contentType == null || httpResponse.getContent() == null || !HttpMediaType.equalsIgnoreParameters(Json.MEDIA_TYPE, contentType)) {
                asString = httpResponse.parseAsString();
                r6 = 0;
            } else {
                r6 = (TokenErrorResponse) new JsonObjectParser(jsonFactory).parseAndClose(httpResponse.getContent(), httpResponse.getContentCharset(), TokenErrorResponse.class);
                try {
                    asString = r6.toPrettyString();
                    r6 = r6;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e = e2;
            r6 = asString;
        }
        StringBuilder sbComputeMessageBuffer = HttpResponseException.computeMessageBuffer(httpResponse);
        if (!Strings.isNullOrEmpty(asString)) {
            sbComputeMessageBuffer.append(StringUtils.LINE_SEPARATOR);
            sbComputeMessageBuffer.append(asString);
            builder.setContent(asString);
        }
        builder.setMessage(sbComputeMessageBuffer.toString());
        return new TokenResponseException(builder, r6);
    }

    public final TokenErrorResponse getDetails() {
        return this.details;
    }
}
