package com.google.api.client.json;

import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class JsonGenerator {
    private void serialize(boolean z, Object obj) throws IOException {
        boolean z2;
        if (obj == null) {
            return;
        }
        Class<?> cls = obj.getClass();
        if (Data.isNull(obj)) {
            writeNull();
            return;
        }
        if (obj instanceof String) {
            writeString((String) obj);
            return;
        }
        if (obj instanceof Number) {
            if (z) {
                writeString(obj.toString());
                return;
            }
            if (obj instanceof BigDecimal) {
                writeNumber((BigDecimal) obj);
                return;
            }
            if (obj instanceof BigInteger) {
                writeNumber((BigInteger) obj);
                return;
            }
            if (obj instanceof Long) {
                writeNumber(((Long) obj).longValue());
                return;
            }
            if (obj instanceof Float) {
                float fFloatValue = ((Number) obj).floatValue();
                Preconditions.checkArgument((Float.isInfinite(fFloatValue) || Float.isNaN(fFloatValue)) ? false : true);
                writeNumber(fFloatValue);
                return;
            } else {
                if ((obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
                    writeNumber(((Number) obj).intValue());
                    return;
                }
                double dDoubleValue = ((Number) obj).doubleValue();
                Preconditions.checkArgument((Double.isInfinite(dDoubleValue) || Double.isNaN(dDoubleValue)) ? false : true);
                writeNumber(dDoubleValue);
                return;
            }
        }
        if (obj instanceof Boolean) {
            writeBoolean(((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof DateTime) {
            writeString(((DateTime) obj).toStringRfc3339());
            return;
        }
        if ((obj instanceof Iterable) || cls.isArray()) {
            writeStartArray();
            Iterator it = Types.iterableOf(obj).iterator();
            while (it.hasNext()) {
                serialize(z, it.next());
            }
            writeEndArray();
            return;
        }
        if (cls.isEnum()) {
            String name = FieldInfo.m457of((Enum<?>) obj).getName();
            if (name == null) {
                writeNull();
                return;
            } else {
                writeString(name);
                return;
            }
        }
        writeStartObject();
        boolean z3 = (obj instanceof Map) && !(obj instanceof GenericData);
        ClassInfo classInfoM455of = z3 ? null : ClassInfo.m455of(cls);
        for (Map.Entry<String, Object> entry : Data.mapOf(obj).entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                String key = entry.getKey();
                if (z3) {
                    z2 = z;
                } else {
                    Field field = classInfoM455of.getField(key);
                    z2 = (field == null || field.getAnnotation(JsonString.class) == null) ? false : true;
                }
                writeFieldName(key);
                serialize(z2, value);
            }
        }
        writeEndObject();
    }

    public abstract void close() throws IOException;

    public void enablePrettyPrint() throws IOException {
    }

    public abstract void flush() throws IOException;

    public abstract JsonFactory getFactory();

    public final void serialize(Object obj) throws IOException {
        serialize(false, obj);
    }

    public abstract void writeBoolean(boolean z) throws IOException;

    public abstract void writeEndArray() throws IOException;

    public abstract void writeEndObject() throws IOException;

    public abstract void writeFieldName(String str) throws IOException;

    public abstract void writeNull() throws IOException;

    public abstract void writeNumber(double d) throws IOException;

    public abstract void writeNumber(float f) throws IOException;

    public abstract void writeNumber(int i) throws IOException;

    public abstract void writeNumber(long j) throws IOException;

    public abstract void writeNumber(String str) throws IOException;

    public abstract void writeNumber(BigDecimal bigDecimal) throws IOException;

    public abstract void writeNumber(BigInteger bigInteger) throws IOException;

    public abstract void writeStartArray() throws IOException;

    public abstract void writeStartObject() throws IOException;

    public abstract void writeString(String str) throws IOException;
}
