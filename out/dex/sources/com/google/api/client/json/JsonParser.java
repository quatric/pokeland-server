package com.google.api.client.json;

import com.google.api.client.util.Beta;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class JsonParser {
    private static WeakHashMap<Class<?>, Field> cachedTypemapFields = new WeakHashMap<>();
    private static final Lock lock = new ReentrantLock();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static Field getCachedTypemapFieldFor(Class<?> cls) {
        Field field = null;
        if (cls == null) {
            return null;
        }
        lock.lock();
        try {
            if (cachedTypemapFields.containsKey(cls)) {
                Field field2 = cachedTypemapFields.get(cls);
                lock.unlock();
                return field2;
            }
            Iterator<FieldInfo> it = ClassInfo.m455of(cls).getFieldInfos().iterator();
            while (it.hasNext()) {
                Field field3 = it.next().getField();
                JsonPolymorphicTypeMap jsonPolymorphicTypeMap = (JsonPolymorphicTypeMap) field3.getAnnotation(JsonPolymorphicTypeMap.class);
                if (jsonPolymorphicTypeMap != null) {
                    Preconditions.checkArgument(field == null, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", cls);
                    Preconditions.checkArgument(Data.isPrimitive(field3.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", cls, field3.getType());
                    JsonPolymorphicTypeMap.TypeDef[] typeDefArrTypeDefinitions = jsonPolymorphicTypeMap.typeDefinitions();
                    HashSet hashSetNewHashSet = Sets.newHashSet();
                    Preconditions.checkArgument(typeDefArrTypeDefinitions.length > 0, "@JsonPolymorphicTypeMap must have at least one @TypeDef");
                    for (JsonPolymorphicTypeMap.TypeDef typeDef : typeDefArrTypeDefinitions) {
                        Preconditions.checkArgument(hashSetNewHashSet.add(typeDef.key()), "Class contains two @TypeDef annotations with identical key: %s", typeDef.key());
                    }
                    field = field3;
                }
            }
            cachedTypemapFields.put(cls, field);
            lock.unlock();
            return field;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    private void parse(ArrayList<Type> arrayList, Object obj, CustomizeJsonParser customizeJsonParser) throws IOException {
        if (obj instanceof GenericJson) {
            ((GenericJson) obj).setFactory(getFactory());
        }
        JsonToken jsonTokenStartParsingObjectOrArray = startParsingObjectOrArray();
        Class<?> cls = obj.getClass();
        ClassInfo classInfoM455of = ClassInfo.m455of(cls);
        boolean zIsAssignableFrom = GenericData.class.isAssignableFrom(cls);
        if (!zIsAssignableFrom && Map.class.isAssignableFrom(cls)) {
            parseMap(null, (Map) obj, Types.getMapValueParameter(cls), arrayList, customizeJsonParser);
            return;
        }
        while (jsonTokenStartParsingObjectOrArray == JsonToken.FIELD_NAME) {
            String text = getText();
            nextToken();
            if (customizeJsonParser != null && customizeJsonParser.stopAt(obj, text)) {
                return;
            }
            FieldInfo fieldInfo = classInfoM455of.getFieldInfo(text);
            if (fieldInfo != null) {
                if (fieldInfo.isFinal() && !fieldInfo.isPrimitive()) {
                    throw new IllegalArgumentException("final array/object fields are not supported");
                }
                Field field = fieldInfo.getField();
                int size = arrayList.size();
                arrayList.add(field.getGenericType());
                Object value = parseValue(field, fieldInfo.getGenericType(), arrayList, obj, customizeJsonParser, true);
                arrayList.remove(size);
                fieldInfo.setValue(obj, value);
            } else if (zIsAssignableFrom) {
                ((GenericData) obj).set(text, parseValue(null, null, arrayList, obj, customizeJsonParser, true));
            } else {
                if (customizeJsonParser != null) {
                    customizeJsonParser.handleUnrecognizedKey(obj, text);
                }
                skipChildren();
            }
            jsonTokenStartParsingObjectOrArray = nextToken();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void parseArray(Field field, Collection<T> collection, Type type, ArrayList<Type> arrayList, CustomizeJsonParser customizeJsonParser) throws IOException {
        JsonToken jsonTokenStartParsingObjectOrArray = startParsingObjectOrArray();
        while (jsonTokenStartParsingObjectOrArray != JsonToken.END_ARRAY) {
            collection.add(parseValue(field, type, arrayList, collection, customizeJsonParser, true));
            jsonTokenStartParsingObjectOrArray = nextToken();
        }
    }

    private void parseMap(Field field, Map<String, Object> map, Type type, ArrayList<Type> arrayList, CustomizeJsonParser customizeJsonParser) throws IOException {
        JsonToken jsonTokenStartParsingObjectOrArray = startParsingObjectOrArray();
        while (jsonTokenStartParsingObjectOrArray == JsonToken.FIELD_NAME) {
            String text = getText();
            nextToken();
            if (customizeJsonParser != null && customizeJsonParser.stopAt(map, text)) {
                return;
            }
            map.put(text, parseValue(field, type, arrayList, map, customizeJsonParser, true));
            jsonTokenStartParsingObjectOrArray = nextToken();
        }
    }

    /* JADX WARN: Code duplicated, block: B:214:0x02d4  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final Object parseValue(Field field, Type type, ArrayList<Type> arrayList, Object obj, CustomizeJsonParser customizeJsonParser, boolean z) throws IOException {
        Type typeResolveWildcardTypeOrTypeVariable = Data.resolveWildcardTypeOrTypeVariable(arrayList, type);
        Type typeRef = null;
        Class<?> rawClass = typeResolveWildcardTypeOrTypeVariable instanceof Class ? (Class) typeResolveWildcardTypeOrTypeVariable : null;
        if (typeResolveWildcardTypeOrTypeVariable instanceof ParameterizedType) {
            rawClass = Types.getRawClass((ParameterizedType) typeResolveWildcardTypeOrTypeVariable);
        }
        if (rawClass == Void.class) {
            skipChildren();
            return null;
        }
        JsonToken currentToken = getCurrentToken();
        try {
            boolean z2 = false;
            switch (getCurrentToken()) {
                case START_OBJECT:
                case FIELD_NAME:
                case END_OBJECT:
                    Preconditions.checkArgument(!Types.isArray(typeResolveWildcardTypeOrTypeVariable), "expected object or map type but got %s", typeResolveWildcardTypeOrTypeVariable);
                    Field cachedTypemapFieldFor = z ? getCachedTypemapFieldFor(rawClass) : null;
                    Object objNewInstanceForObject = (rawClass == null || customizeJsonParser == null) ? null : customizeJsonParser.newInstanceForObject(obj, rawClass);
                    boolean z3 = rawClass != null && Types.isAssignableToOrFrom(rawClass, Map.class);
                    if (cachedTypemapFieldFor != null) {
                        objNewInstanceForObject = new GenericJson();
                    } else if (objNewInstanceForObject == null) {
                        objNewInstanceForObject = (z3 || rawClass == null) ? Data.newMapInstance(rawClass) : Types.newInstance(rawClass);
                    }
                    int size = arrayList.size();
                    if (typeResolveWildcardTypeOrTypeVariable != null) {
                        arrayList.add(typeResolveWildcardTypeOrTypeVariable);
                    }
                    if (z3 && !GenericData.class.isAssignableFrom(rawClass)) {
                        Type mapValueParameter = Map.class.isAssignableFrom(rawClass) ? Types.getMapValueParameter(typeResolveWildcardTypeOrTypeVariable) : null;
                        if (mapValueParameter != null) {
                            parseMap(field, (Map) objNewInstanceForObject, mapValueParameter, arrayList, customizeJsonParser);
                            return objNewInstanceForObject;
                        }
                    }
                    parse(arrayList, objNewInstanceForObject, customizeJsonParser);
                    if (typeResolveWildcardTypeOrTypeVariable != null) {
                        arrayList.remove(size);
                    }
                    if (cachedTypemapFieldFor == null) {
                        return objNewInstanceForObject;
                    }
                    Object obj2 = ((GenericJson) objNewInstanceForObject).get(cachedTypemapFieldFor.getName());
                    Preconditions.checkArgument(obj2 != null, "No value specified for @JsonPolymorphicTypeMap field");
                    String string = obj2.toString();
                    for (JsonPolymorphicTypeMap.TypeDef typeDef : ((JsonPolymorphicTypeMap) cachedTypemapFieldFor.getAnnotation(JsonPolymorphicTypeMap.class)).typeDefinitions()) {
                        if (typeDef.key().equals(string)) {
                            typeRef = typeDef.ref();
                            Type type2 = typeRef;
                            Preconditions.checkArgument(type2 != null, "No TypeDef annotation found with key: " + string);
                            JsonFactory factory = getFactory();
                            JsonParser jsonParserCreateJsonParser = factory.createJsonParser(factory.toString(objNewInstanceForObject));
                            jsonParserCreateJsonParser.startParsing();
                            return jsonParserCreateJsonParser.parseValue(field, type2, arrayList, null, null, false);
                        }
                    }
                    Type type3 = typeRef;
                    if (type3 != null) {
                    }
                    Preconditions.checkArgument(type3 != null, "No TypeDef annotation found with key: " + string);
                    JsonFactory factory2 = getFactory();
                    JsonParser jsonParserCreateJsonParser2 = factory2.createJsonParser(factory2.toString(objNewInstanceForObject));
                    jsonParserCreateJsonParser2.startParsing();
                    return jsonParserCreateJsonParser2.parseValue(field, type3, arrayList, null, null, false);
                case START_ARRAY:
                case END_ARRAY:
                    boolean zIsArray = Types.isArray(typeResolveWildcardTypeOrTypeVariable);
                    Preconditions.checkArgument(typeResolveWildcardTypeOrTypeVariable == null || zIsArray || (rawClass != null && Types.isAssignableToOrFrom(rawClass, Collection.class)), "expected collection or array type but got %s", typeResolveWildcardTypeOrTypeVariable);
                    Collection<Object> collectionNewInstanceForArray = (customizeJsonParser == null || field == null) ? null : customizeJsonParser.newInstanceForArray(obj, field);
                    if (collectionNewInstanceForArray == null) {
                        collectionNewInstanceForArray = Data.newCollectionInstance(typeResolveWildcardTypeOrTypeVariable);
                    }
                    if (zIsArray) {
                        typeRef = Types.getArrayComponentType(typeResolveWildcardTypeOrTypeVariable);
                    } else if (rawClass != null && Iterable.class.isAssignableFrom(rawClass)) {
                        typeRef = Types.getIterableParameter(typeResolveWildcardTypeOrTypeVariable);
                    }
                    Type typeResolveWildcardTypeOrTypeVariable2 = Data.resolveWildcardTypeOrTypeVariable(arrayList, typeRef);
                    parseArray(field, collectionNewInstanceForArray, typeResolveWildcardTypeOrTypeVariable2, arrayList, customizeJsonParser);
                    return zIsArray ? Types.toArray(collectionNewInstanceForArray, Types.getRawArrayComponentType(arrayList, typeResolveWildcardTypeOrTypeVariable2)) : collectionNewInstanceForArray;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    Preconditions.checkArgument(typeResolveWildcardTypeOrTypeVariable == null || rawClass == Boolean.TYPE || (rawClass != null && rawClass.isAssignableFrom(Boolean.class)), "expected type Boolean or boolean but got %s", typeResolveWildcardTypeOrTypeVariable);
                    return currentToken == JsonToken.VALUE_TRUE ? Boolean.TRUE : Boolean.FALSE;
                case VALUE_NUMBER_FLOAT:
                case VALUE_NUMBER_INT:
                    Preconditions.checkArgument(field == null || field.getAnnotation(JsonString.class) == null, "number type formatted as a JSON number cannot use @JsonString annotation");
                    if (rawClass != null && !rawClass.isAssignableFrom(BigDecimal.class)) {
                        if (rawClass == BigInteger.class) {
                            return getBigIntegerValue();
                        }
                        if (rawClass != Double.class && rawClass != Double.TYPE) {
                            if (rawClass != Long.class && rawClass != Long.TYPE) {
                                if (rawClass != Float.class && rawClass != Float.TYPE) {
                                    if (rawClass != Integer.class && rawClass != Integer.TYPE) {
                                        if (rawClass != Short.class && rawClass != Short.TYPE) {
                                            if (rawClass != Byte.class && rawClass != Byte.TYPE) {
                                                throw new IllegalArgumentException("expected numeric type but got " + typeResolveWildcardTypeOrTypeVariable);
                                            }
                                            return Byte.valueOf(getByteValue());
                                        }
                                        return Short.valueOf(getShortValue());
                                    }
                                    return Integer.valueOf(getIntValue());
                                }
                                return Float.valueOf(getFloatValue());
                            }
                            return Long.valueOf(getLongValue());
                        }
                        return Double.valueOf(getDoubleValue());
                    }
                    return getDecimalValue();
                case VALUE_STRING:
                    String lowerCase = getText().trim().toLowerCase(Locale.US);
                    if ((rawClass != Float.TYPE && rawClass != Float.class && rawClass != Double.TYPE && rawClass != Double.class) || (!lowerCase.equals("nan") && !lowerCase.equals("infinity") && !lowerCase.equals("-infinity"))) {
                        if (rawClass == null || !Number.class.isAssignableFrom(rawClass) || (field != null && field.getAnnotation(JsonString.class) != null)) {
                            z2 = true;
                        }
                        Preconditions.checkArgument(z2, "number field formatted as a JSON string must use the @JsonString annotation");
                    }
                    return Data.parsePrimitiveValue(typeResolveWildcardTypeOrTypeVariable, getText());
                case VALUE_NULL:
                    Preconditions.checkArgument(rawClass == null || !rawClass.isPrimitive(), "primitive number field but found a JSON null");
                    if (rawClass != null && (rawClass.getModifiers() & 1536) != 0) {
                        if (Types.isAssignableToOrFrom(rawClass, Collection.class)) {
                            return Data.nullOf(Data.newCollectionInstance(typeResolveWildcardTypeOrTypeVariable).getClass());
                        }
                        if (Types.isAssignableToOrFrom(rawClass, Map.class)) {
                            return Data.nullOf(Data.newMapInstance(rawClass).getClass());
                        }
                    }
                    return Data.nullOf(Types.getRawArrayComponentType(arrayList, typeResolveWildcardTypeOrTypeVariable));
                default:
                    throw new IllegalArgumentException("unexpected JSON node type: " + currentToken);
            }
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            String currentName = getCurrentName();
            if (currentName != null) {
                sb.append("key ");
                sb.append(currentName);
            }
            if (field != null) {
                if (currentName != null) {
                    sb.append(", ");
                }
                sb.append("field ");
                sb.append(field);
            }
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    private JsonToken startParsing() throws IOException {
        JsonToken currentToken = getCurrentToken();
        if (currentToken == null) {
            currentToken = nextToken();
        }
        Preconditions.checkArgument(currentToken != null, "no JSON input found");
        return currentToken;
    }

    private JsonToken startParsingObjectOrArray() throws IOException {
        JsonToken jsonTokenStartParsing = startParsing();
        int i = C02911.$SwitchMap$com$google$api$client$json$JsonToken[jsonTokenStartParsing.ordinal()];
        boolean z = true;
        if (i != 1) {
            return i != 2 ? jsonTokenStartParsing : nextToken();
        }
        JsonToken jsonTokenNextToken = nextToken();
        if (jsonTokenNextToken != JsonToken.FIELD_NAME && jsonTokenNextToken != JsonToken.END_OBJECT) {
            z = false;
        }
        Preconditions.checkArgument(z, jsonTokenNextToken);
        return jsonTokenNextToken;
    }

    public abstract void close() throws IOException;

    public abstract BigInteger getBigIntegerValue() throws IOException;

    public abstract byte getByteValue() throws IOException;

    public abstract String getCurrentName() throws IOException;

    public abstract JsonToken getCurrentToken();

    public abstract BigDecimal getDecimalValue() throws IOException;

    public abstract double getDoubleValue() throws IOException;

    public abstract JsonFactory getFactory();

    public abstract float getFloatValue() throws IOException;

    public abstract int getIntValue() throws IOException;

    public abstract long getLongValue() throws IOException;

    public abstract short getShortValue() throws IOException;

    public abstract String getText() throws IOException;

    public abstract JsonToken nextToken() throws IOException;

    public final <T> T parse(Class<T> cls) throws IOException {
        return (T) parse((Class) cls, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> T parse(Class<T> cls, CustomizeJsonParser customizeJsonParser) throws IOException {
        return (T) parse((Type) cls, false, customizeJsonParser);
    }

    public Object parse(Type type, boolean z) throws IOException {
        return parse(type, z, (CustomizeJsonParser) null);
    }

    @Beta
    public Object parse(Type type, boolean z, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            if (!Void.class.equals(type)) {
                startParsing();
            }
            return parseValue(null, type, new ArrayList<>(), null, customizeJsonParser, true);
        } finally {
            if (z) {
                close();
            }
        }
    }

    public final void parse(Object obj) throws IOException {
        parse(obj, (CustomizeJsonParser) null);
    }

    @Beta
    public final void parse(Object obj, CustomizeJsonParser customizeJsonParser) throws IOException {
        ArrayList<Type> arrayList = new ArrayList<>();
        arrayList.add(obj.getClass());
        parse(arrayList, obj, customizeJsonParser);
    }

    public final <T> T parseAndClose(Class<T> cls) throws IOException {
        return (T) parseAndClose((Class) cls, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> T parseAndClose(Class<T> cls, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            return (T) parse((Class) cls, customizeJsonParser);
        } finally {
            close();
        }
    }

    public final void parseAndClose(Object obj) throws IOException {
        parseAndClose(obj, (CustomizeJsonParser) null);
    }

    @Beta
    public final void parseAndClose(Object obj, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            parse(obj, customizeJsonParser);
        } finally {
            close();
        }
    }

    public final <T> Collection<T> parseArray(Class<?> cls, Class<T> cls2) throws IOException {
        return parseArray(cls, cls2, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> Collection<T> parseArray(Class<?> cls, Class<T> cls2, CustomizeJsonParser customizeJsonParser) throws IOException {
        Collection<T> collection = (Collection<T>) Data.newCollectionInstance(cls);
        parseArray(collection, cls2, customizeJsonParser);
        return collection;
    }

    public final <T> void parseArray(Collection<? super T> collection, Class<T> cls) throws IOException {
        parseArray(collection, cls, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> void parseArray(Collection<? super T> collection, Class<T> cls, CustomizeJsonParser customizeJsonParser) throws IOException {
        parseArray(null, collection, cls, new ArrayList<>(), customizeJsonParser);
    }

    public final <T> Collection<T> parseArrayAndClose(Class<?> cls, Class<T> cls2) throws IOException {
        return parseArrayAndClose(cls, cls2, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> Collection<T> parseArrayAndClose(Class<?> cls, Class<T> cls2, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            return parseArray(cls, cls2, customizeJsonParser);
        } finally {
            close();
        }
    }

    public final <T> void parseArrayAndClose(Collection<? super T> collection, Class<T> cls) throws IOException {
        parseArrayAndClose(collection, cls, (CustomizeJsonParser) null);
    }

    @Beta
    public final <T> void parseArrayAndClose(Collection<? super T> collection, Class<T> cls, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            parseArray(collection, cls, customizeJsonParser);
        } finally {
            close();
        }
    }

    public abstract JsonParser skipChildren() throws IOException;

    public final String skipToKey(Set<String> set) throws IOException {
        JsonToken jsonTokenStartParsingObjectOrArray = startParsingObjectOrArray();
        while (jsonTokenStartParsingObjectOrArray == JsonToken.FIELD_NAME) {
            String text = getText();
            nextToken();
            if (set.contains(text)) {
                return text;
            }
            skipChildren();
            jsonTokenStartParsingObjectOrArray = nextToken();
        }
        return null;
    }

    public final void skipToKey(String str) throws IOException {
        skipToKey(Collections.singleton(str));
    }
}
