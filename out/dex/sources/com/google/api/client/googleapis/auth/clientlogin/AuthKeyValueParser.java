package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Types;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
final class AuthKeyValueParser implements ObjectParser {
    public static final AuthKeyValueParser INSTANCE = new AuthKeyValueParser();

    private AuthKeyValueParser() {
    }

    public String getContentType() {
        return "text/plain";
    }

    public <T> T parse(HttpResponse httpResponse, Class<T> cls) throws IOException {
        httpResponse.setContentLoggingLimit(0);
        InputStream content = httpResponse.getContent();
        try {
            return (T) parse(content, cls);
        } finally {
            content.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parse(InputStream inputStream, Class<T> cls) throws IOException {
        Object objValueOf;
        ClassInfo classInfoM455of = ClassInfo.m455of(cls);
        T t = (T) Types.newInstance(cls);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return t;
            }
            int iIndexOf = line.indexOf(61);
            String strSubstring = line.substring(0, iIndexOf);
            String strSubstring2 = line.substring(iIndexOf + 1);
            Field field = classInfoM455of.getField(strSubstring);
            if (field != null) {
                Class<?> type = field.getType();
                if (type == Boolean.TYPE || type == Boolean.class) {
                    objValueOf = strSubstring2;
                    objValueOf = Boolean.valueOf(strSubstring2);
                }
                objValueOf = strSubstring2;
                FieldInfo.setFieldValue(field, t, objValueOf);
            } else if (GenericData.class.isAssignableFrom(cls)) {
                ((GenericData) t).set(strSubstring, strSubstring2);
            } else if (Map.class.isAssignableFrom(cls)) {
                ((Map) t).put(strSubstring, strSubstring2);
            }
        }
    }

    @Override // com.google.api.client.util.ObjectParser
    public <T> T parseAndClose(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        return (T) parseAndClose((Reader) new InputStreamReader(inputStream, charset), (Class) cls);
    }

    @Override // com.google.api.client.util.ObjectParser
    public Object parseAndClose(InputStream inputStream, Charset charset, Type type) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.api.client.util.ObjectParser
    public <T> T parseAndClose(Reader reader, Class<T> cls) throws IOException {
        Object objValueOf;
        try {
            ClassInfo classInfoM455of = ClassInfo.m455of(cls);
            T t = (T) Types.newInstance(cls);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return t;
                }
                int iIndexOf = line.indexOf(61);
                String strSubstring = line.substring(0, iIndexOf);
                String strSubstring2 = line.substring(iIndexOf + 1);
                Field field = classInfoM455of.getField(strSubstring);
                if (field != null) {
                    Class<?> type = field.getType();
                    if (type == Boolean.TYPE || type == Boolean.class) {
                        objValueOf = strSubstring2;
                        objValueOf = Boolean.valueOf(strSubstring2);
                    }
                    objValueOf = strSubstring2;
                    FieldInfo.setFieldValue(field, t, objValueOf);
                } else if (GenericData.class.isAssignableFrom(cls)) {
                    ((GenericData) t).set(strSubstring, strSubstring2);
                } else if (Map.class.isAssignableFrom(cls)) {
                    ((Map) t).put(strSubstring, strSubstring2);
                }
            }
        } finally {
            reader.close();
        }
    }

    @Override // com.google.api.client.util.ObjectParser
    public Object parseAndClose(Reader reader, Type type) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }
}
