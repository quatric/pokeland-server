package com.google.api.client.extensions.android.json;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonWriter;
import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Charsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@TargetApi(11)
public class AndroidJsonFactory extends JsonFactory {

    @Beta
    static class InstanceHolder {
        static final AndroidJsonFactory INSTANCE = new AndroidJsonFactory();

        InstanceHolder() {
        }
    }

    public AndroidJsonFactory() {
        AndroidUtils.checkMinimumSdkLevel(11);
    }

    public static AndroidJsonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) {
        return createJsonGenerator(new OutputStreamWriter(outputStream, charset));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonGenerator createJsonGenerator(Writer writer) {
        return new AndroidJsonGenerator(this, new JsonWriter(writer));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(InputStream inputStream) {
        return createJsonParser(new InputStreamReader(inputStream, Charsets.UTF_8));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(InputStream inputStream, Charset charset) {
        return charset == null ? createJsonParser(inputStream) : createJsonParser(new InputStreamReader(inputStream, charset));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(Reader reader) {
        return new AndroidJsonParser(this, new JsonReader(reader));
    }

    @Override // com.google.api.client.json.JsonFactory
    public JsonParser createJsonParser(String str) {
        return createJsonParser(new StringReader(str));
    }
}
