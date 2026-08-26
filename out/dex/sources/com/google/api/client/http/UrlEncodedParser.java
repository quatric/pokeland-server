package com.google.api.client.http;

import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UrlEncodedParser implements ObjectParser {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String MEDIA_TYPE = new HttpMediaType("application/x-www-form-urlencoded").setCharsetParameter(Charsets.UTF_8).build();

    public static void parse(Reader reader, Object obj) throws IOException {
        int i;
        Class<?> cls = obj.getClass();
        ClassInfo classInfoM455of = ClassInfo.m455of(cls);
        List listAsList = Arrays.asList(cls);
        GenericData genericData = GenericData.class.isAssignableFrom(cls) ? (GenericData) obj : null;
        Map map = Map.class.isAssignableFrom(cls) ? (Map) obj : null;
        ArrayValueMap arrayValueMap = new ArrayValueMap(obj);
        StringWriter stringWriter = new StringWriter();
        StringWriter stringWriter2 = new StringWriter();
        do {
            StringWriter stringWriter3 = stringWriter2;
            StringWriter stringWriter4 = stringWriter;
            boolean z = true;
            while (true) {
                i = reader.read();
                if (i == -1 || i == 38) {
                    break;
                }
                if (i != 61) {
                    if (z) {
                        stringWriter4.write(i);
                    } else {
                        stringWriter3.write(i);
                    }
                } else if (z) {
                    z = false;
                } else {
                    stringWriter3.write(i);
                }
            }
            String strDecodeUri = CharEscapers.decodeUri(stringWriter4.toString());
            if (strDecodeUri.length() != 0) {
                String strDecodeUri2 = CharEscapers.decodeUri(stringWriter3.toString());
                FieldInfo fieldInfo = classInfoM455of.getFieldInfo(strDecodeUri);
                if (fieldInfo != null) {
                    Type typeResolveWildcardTypeOrTypeVariable = Data.resolveWildcardTypeOrTypeVariable(listAsList, fieldInfo.getGenericType());
                    if (Types.isArray(typeResolveWildcardTypeOrTypeVariable)) {
                        Class<?> rawArrayComponentType = Types.getRawArrayComponentType(listAsList, Types.getArrayComponentType(typeResolveWildcardTypeOrTypeVariable));
                        arrayValueMap.put(fieldInfo.getField(), rawArrayComponentType, parseValue(rawArrayComponentType, listAsList, strDecodeUri2));
                    } else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(listAsList, typeResolveWildcardTypeOrTypeVariable), Iterable.class)) {
                        Collection<Object> collectionNewCollectionInstance = (Collection) fieldInfo.getValue(obj);
                        if (collectionNewCollectionInstance == null) {
                            collectionNewCollectionInstance = Data.newCollectionInstance(typeResolveWildcardTypeOrTypeVariable);
                            fieldInfo.setValue(obj, collectionNewCollectionInstance);
                        }
                        collectionNewCollectionInstance.add(parseValue(typeResolveWildcardTypeOrTypeVariable == Object.class ? null : Types.getIterableParameter(typeResolveWildcardTypeOrTypeVariable), listAsList, strDecodeUri2));
                    } else {
                        fieldInfo.setValue(obj, parseValue(typeResolveWildcardTypeOrTypeVariable, listAsList, strDecodeUri2));
                    }
                } else if (map != null) {
                    ArrayList arrayList = (ArrayList) map.get(strDecodeUri);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        if (genericData != null) {
                            genericData.set(strDecodeUri, arrayList);
                        } else {
                            map.put(strDecodeUri, arrayList);
                        }
                    }
                    arrayList.add(strDecodeUri2);
                }
            }
            stringWriter = new StringWriter();
            stringWriter2 = new StringWriter();
        } while (i != -1);
        arrayValueMap.setValues();
    }

    public static void parse(String str, Object obj) {
        if (str == null) {
            return;
        }
        try {
            parse(new StringReader(str), obj);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private static Object parseValue(Type type, List<Type> list, String str) {
        return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(list, type), str);
    }

    @Override // com.google.api.client.util.ObjectParser
    public <T> T parseAndClose(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        return (T) parseAndClose((Reader) new InputStreamReader(inputStream, charset), (Class) cls);
    }

    @Override // com.google.api.client.util.ObjectParser
    public Object parseAndClose(InputStream inputStream, Charset charset, Type type) throws IOException {
        return parseAndClose(new InputStreamReader(inputStream, charset), type);
    }

    @Override // com.google.api.client.util.ObjectParser
    public <T> T parseAndClose(Reader reader, Class<T> cls) throws IOException {
        return (T) parseAndClose(reader, (Type) cls);
    }

    @Override // com.google.api.client.util.ObjectParser
    public Object parseAndClose(Reader reader, Type type) throws IOException {
        Preconditions.checkArgument(type instanceof Class, "dataType has to be of type Class<?>");
        Object objNewInstance = Types.newInstance((Class) type);
        parse(new BufferedReader(reader), objNewInstance);
        return objNewInstance;
    }
}
