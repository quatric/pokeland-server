package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Beta;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class MockMetadataServerTransport extends MockHttpTransport {
    String accessToken;
    Integer tokenRequestStatusCode;
    private static final String METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
    private static final String METADATA_TOKEN_SERVER_URL = METADATA_SERVER_URL + "/computeMetadata/v1/instance/service-accounts/default/token";
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public MockMetadataServerTransport(String str) {
        this.accessToken = str;
    }

    @Override // com.google.api.client.testing.http.MockHttpTransport, com.google.api.client.http.HttpTransport
    public LowLevelHttpRequest buildRequest(String str, String str2) throws IOException {
        if (str2.equals(METADATA_TOKEN_SERVER_URL)) {
            return new MockLowLevelHttpRequest(str2) { // from class: com.google.api.client.googleapis.testing.compute.MockMetadataServerTransport.1
                @Override // com.google.api.client.testing.http.MockLowLevelHttpRequest, com.google.api.client.http.LowLevelHttpRequest
                public LowLevelHttpResponse execute() throws IOException {
                    if (MockMetadataServerTransport.this.tokenRequestStatusCode != null) {
                        return new MockLowLevelHttpResponse().setStatusCode(MockMetadataServerTransport.this.tokenRequestStatusCode.intValue()).setContent("Token Fetch Error");
                    }
                    if (!"Google".equals(getFirstHeaderValue("Metadata-Flavor"))) {
                        throw new IOException("Metadata request header not found.");
                    }
                    GenericJson genericJson = new GenericJson();
                    genericJson.setFactory(MockMetadataServerTransport.JSON_FACTORY);
                    genericJson.put("access_token", (Object) MockMetadataServerTransport.this.accessToken);
                    genericJson.put("expires_in", (Object) 3600000);
                    genericJson.put("token_type", (Object) "Bearer");
                    return new MockLowLevelHttpResponse().setContentType(Json.MEDIA_TYPE).setContent(genericJson.toPrettyString());
                }
            };
        }
        return str2.equals(METADATA_SERVER_URL) ? new MockLowLevelHttpRequest(str2) { // from class: com.google.api.client.googleapis.testing.compute.MockMetadataServerTransport.2
            @Override // com.google.api.client.testing.http.MockLowLevelHttpRequest, com.google.api.client.http.LowLevelHttpRequest
            public LowLevelHttpResponse execute() {
                MockLowLevelHttpResponse mockLowLevelHttpResponse = new MockLowLevelHttpResponse();
                mockLowLevelHttpResponse.addHeader("Metadata-Flavor", "Google");
                return mockLowLevelHttpResponse;
            }
        } : super.buildRequest(str, str2);
    }

    public void setTokenRequestStatusCode(Integer num) {
        this.tokenRequestStatusCode = num;
    }
}
