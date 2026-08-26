package com.google.api.client.json.jackson2;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class JacksonFactory extends JsonFactory {
    private final com.fasterxml.jackson.core.JsonFactory factory = new com.fasterxml.jackson.core.JsonFactory();

    static class InstanceHolder {
        static final JacksonFactory INSTANCE = new JacksonFactory();

        InstanceHolder() {
        }
    }

    public JacksonFactory() {
        this.factory.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
    }

    static JsonToken convert(com.fasterxml.jackson.core.JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken) {
            case END_ARRAY:
                return JsonToken.END_ARRAY;
            case START_ARRAY:
                return JsonToken.START_ARRAY;
            case END_OBJECT:
                return JsonToken.END_OBJECT;
            case START_OBJECT:
                return JsonToken.START_OBJECT;
            case VALUE_FALSE:
                return JsonToken.VALUE_FALSE;
            case VALUE_TRUE:
                return JsonToken.VALUE_TRUE;
            case VALUE_NULL:
                return JsonToken.VALUE_NULL;
            case VALUE_STRING:
                return JsonToken.VALUE_STRING;
            case VALUE_NUMBER_FLOAT:
                return JsonToken.VALUE_NUMBER_FLOAT;
            case VALUE_NUMBER_INT:
                return JsonToken.VALUE_NUMBER_INT;
            case FIELD_NAME:
                return JsonToken.FIELD_NAME;
            default:
                return JsonToken.NOT_AVAILABLE;
        }
    }

    public static JacksonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override // com.google.api.client.json.JsonFactory
    public com.google.api.client.json.JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(outputStream, JsonEncoding.UTF8));
    }

    @Override // com.google.api.client.json.JsonFactory
    public com.google.api.client.json.JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(writer));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        return new JacksonParser(this, this.factory.createJsonParser(inputStream));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(InputStream inputStream, Charset charset) throws IOException {
        Preconditions.checkNotNull(inputStream);
        return new JacksonParser(this, this.factory.createJsonParser(inputStream));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(Reader reader) throws IOException {
        Preconditions.checkNotNull(reader);
        return new JacksonParser(this, this.factory.createJsonParser(reader));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(String str) throws IOException {
        Preconditions.checkNotNull(str);
        return new JacksonParser(this, this.factory.createJsonParser(str));
    }
}
