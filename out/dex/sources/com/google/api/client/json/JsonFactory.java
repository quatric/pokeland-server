package com.google.api.client.json;

import com.google.api.client.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class JsonFactory {
    private ByteArrayOutputStream toByteStream(Object obj, boolean z) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGeneratorCreateJsonGenerator = createJsonGenerator(byteArrayOutputStream, Charsets.UTF_8);
        if (z) {
            jsonGeneratorCreateJsonGenerator.enablePrettyPrint();
        }
        jsonGeneratorCreateJsonGenerator.serialize(obj);
        jsonGeneratorCreateJsonGenerator.flush();
        return byteArrayOutputStream;
    }

    private String toString(Object obj, boolean z) throws IOException {
        return toByteStream(obj, z).toString("UTF-8");
    }

    public abstract JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) throws IOException;

    public abstract JsonGenerator createJsonGenerator(Writer writer) throws IOException;

    public final JsonObjectParser createJsonObjectParser() {
        return new JsonObjectParser(this);
    }

    public abstract JsonParser createJsonParser(InputStream inputStream) throws IOException;

    public abstract JsonParser createJsonParser(InputStream inputStream, Charset charset) throws IOException;

    public abstract JsonParser createJsonParser(Reader reader) throws IOException;

    public abstract JsonParser createJsonParser(String str) throws IOException;

    public final <T> T fromInputStream(InputStream inputStream, Class<T> cls) throws IOException {
        return (T) createJsonParser(inputStream).parseAndClose((Class) cls);
    }

    public final <T> T fromInputStream(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        return (T) createJsonParser(inputStream, charset).parseAndClose((Class) cls);
    }

    public final <T> T fromReader(Reader reader, Class<T> cls) throws IOException {
        return (T) createJsonParser(reader).parseAndClose((Class) cls);
    }

    public final <T> T fromString(String str, Class<T> cls) throws IOException {
        return (T) createJsonParser(str).parse((Class) cls);
    }

    public final byte[] toByteArray(Object obj) throws IOException {
        return toByteStream(obj, false).toByteArray();
    }

    public final String toPrettyString(Object obj) throws IOException {
        return toString(obj, true);
    }

    public final String toString(Object obj) throws IOException {
        return toString(obj, false);
    }
}
