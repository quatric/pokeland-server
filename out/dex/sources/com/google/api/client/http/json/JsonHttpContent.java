package com.google.api.client.http.json;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class JsonHttpContent extends AbstractHttpContent {
    private final Object data;
    private final JsonFactory jsonFactory;
    private String wrapperKey;

    public JsonHttpContent(JsonFactory jsonFactory, Object obj) {
        super(Json.MEDIA_TYPE);
        this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory);
        this.data = Preconditions.checkNotNull(obj);
    }

    public final Object getData() {
        return this.data;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getWrapperKey() {
        return this.wrapperKey;
    }

    @Override // com.google.api.client.http.AbstractHttpContent
    public JsonHttpContent setMediaType(HttpMediaType httpMediaType) {
        super.setMediaType(httpMediaType);
        return this;
    }

    public JsonHttpContent setWrapperKey(String str) {
        this.wrapperKey = str;
        return this;
    }

    @Override // com.google.api.client.http.HttpContent, com.google.api.client.util.StreamingContent
    public void writeTo(OutputStream outputStream) throws IOException {
        JsonGenerator jsonGeneratorCreateJsonGenerator = this.jsonFactory.createJsonGenerator(outputStream, getCharset());
        if (this.wrapperKey != null) {
            jsonGeneratorCreateJsonGenerator.writeStartObject();
            jsonGeneratorCreateJsonGenerator.writeFieldName(this.wrapperKey);
        }
        jsonGeneratorCreateJsonGenerator.serialize(this.data);
        if (this.wrapperKey != null) {
            jsonGeneratorCreateJsonGenerator.writeEndObject();
        }
        jsonGeneratorCreateJsonGenerator.flush();
    }
}
