package com.google.api.client.http;

import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class HttpHeaders extends GenericData {

    @Key(com.google.common.net.HttpHeaders.ACCEPT)
    private List<String> accept;

    @Key(com.google.common.net.HttpHeaders.ACCEPT_ENCODING)
    private List<String> acceptEncoding;

    @Key(com.google.common.net.HttpHeaders.AGE)
    private List<Long> age;

    @Key(com.google.common.net.HttpHeaders.WWW_AUTHENTICATE)
    private List<String> authenticate;

    @Key(com.google.common.net.HttpHeaders.AUTHORIZATION)
    private List<String> authorization;

    @Key(com.google.common.net.HttpHeaders.CACHE_CONTROL)
    private List<String> cacheControl;

    @Key(com.google.common.net.HttpHeaders.CONTENT_ENCODING)
    private List<String> contentEncoding;

    @Key(com.google.common.net.HttpHeaders.CONTENT_LENGTH)
    private List<Long> contentLength;

    @Key(com.google.common.net.HttpHeaders.CONTENT_MD5)
    private List<String> contentMD5;

    @Key(com.google.common.net.HttpHeaders.CONTENT_RANGE)
    private List<String> contentRange;

    @Key(com.google.common.net.HttpHeaders.CONTENT_TYPE)
    private List<String> contentType;

    @Key(com.google.common.net.HttpHeaders.COOKIE)
    private List<String> cookie;

    @Key(com.google.common.net.HttpHeaders.DATE)
    private List<String> date;

    @Key(com.google.common.net.HttpHeaders.ETAG)
    private List<String> etag;

    @Key(com.google.common.net.HttpHeaders.EXPIRES)
    private List<String> expires;

    @Key(com.google.common.net.HttpHeaders.IF_MATCH)
    private List<String> ifMatch;

    @Key(com.google.common.net.HttpHeaders.IF_MODIFIED_SINCE)
    private List<String> ifModifiedSince;

    @Key(com.google.common.net.HttpHeaders.IF_NONE_MATCH)
    private List<String> ifNoneMatch;

    @Key(com.google.common.net.HttpHeaders.IF_RANGE)
    private List<String> ifRange;

    @Key(com.google.common.net.HttpHeaders.IF_UNMODIFIED_SINCE)
    private List<String> ifUnmodifiedSince;

    @Key(com.google.common.net.HttpHeaders.LAST_MODIFIED)
    private List<String> lastModified;

    @Key(com.google.common.net.HttpHeaders.LOCATION)
    private List<String> location;

    @Key("MIME-Version")
    private List<String> mimeVersion;

    @Key(com.google.common.net.HttpHeaders.RANGE)
    private List<String> range;

    @Key(com.google.common.net.HttpHeaders.RETRY_AFTER)
    private List<String> retryAfter;

    @Key(com.google.common.net.HttpHeaders.USER_AGENT)
    private List<String> userAgent;

    private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest {
        private final ParseHeaderState state;
        private final HttpHeaders target;

        HeaderParsingFakeLevelHttpRequest(HttpHeaders httpHeaders, ParseHeaderState parseHeaderState) {
            this.target = httpHeaders;
            this.state = parseHeaderState;
        }

        @Override // com.google.api.client.http.LowLevelHttpRequest
        public void addHeader(String str, String str2) {
            this.target.parseHeader(str, str2, this.state);
        }

        @Override // com.google.api.client.http.LowLevelHttpRequest
        public LowLevelHttpResponse execute() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    private static final class ParseHeaderState {
        final ArrayValueMap arrayValueMap;
        final ClassInfo classInfo;
        final List<Type> context;
        final StringBuilder logger;

        public ParseHeaderState(HttpHeaders httpHeaders, StringBuilder sb) {
            Class<?> cls = httpHeaders.getClass();
            this.context = Arrays.asList(cls);
            this.classInfo = ClassInfo.m456of(cls, true);
            this.logger = sb;
            this.arrayValueMap = new ArrayValueMap(httpHeaders);
        }

        void finish() {
            this.arrayValueMap.setValues();
        }
    }

    public HttpHeaders() {
        super(EnumSet.of(GenericData.Flags.IGNORE_CASE));
        this.acceptEncoding = new ArrayList(Collections.singleton("gzip"));
    }

    private static void addHeader(Logger logger, StringBuilder sb, StringBuilder sb2, LowLevelHttpRequest lowLevelHttpRequest, String str, Object obj, Writer writer) throws IOException {
        if (obj == null || Data.isNull(obj)) {
            return;
        }
        String stringValue = toStringValue(obj);
        String str2 = ((com.google.common.net.HttpHeaders.AUTHORIZATION.equalsIgnoreCase(str) || com.google.common.net.HttpHeaders.COOKIE.equalsIgnoreCase(str)) && (logger == null || !logger.isLoggable(Level.ALL))) ? "<Not Logged>" : stringValue;
        if (sb != null) {
            sb.append(str);
            sb.append(": ");
            sb.append(str2);
            sb.append(StringUtils.LINE_SEPARATOR);
        }
        if (sb2 != null) {
            sb2.append(" -H '");
            sb2.append(str);
            sb2.append(": ");
            sb2.append(str2);
            sb2.append("'");
        }
        if (lowLevelHttpRequest != null) {
            lowLevelHttpRequest.addHeader(str, stringValue);
        }
        if (writer != null) {
            writer.write(str);
            writer.write(": ");
            writer.write(stringValue);
            writer.write("\r\n");
        }
    }

    private <T> List<T> getAsList(T t) {
        if (t == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(t);
        return arrayList;
    }

    private <T> T getFirstHeaderValue(List<T> list) {
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    private static Object parseValue(Type type, List<Type> list, String str) {
        return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(list, type), str);
    }

    static void serializeHeaders(HttpHeaders httpHeaders, StringBuilder sb, StringBuilder sb2, Logger logger, LowLevelHttpRequest lowLevelHttpRequest) throws IOException {
        serializeHeaders(httpHeaders, sb, sb2, logger, lowLevelHttpRequest, null);
    }

    static void serializeHeaders(HttpHeaders httpHeaders, StringBuilder sb, StringBuilder sb2, Logger logger, LowLevelHttpRequest lowLevelHttpRequest, Writer writer) throws IOException {
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, Object> entry : httpHeaders.entrySet()) {
            String key = entry.getKey();
            Preconditions.checkArgument(hashSet.add(key), "multiple headers of the same name (headers are case insensitive): %s", key);
            Object value = entry.getValue();
            if (value != null) {
                FieldInfo fieldInfo = httpHeaders.getClassInfo().getFieldInfo(key);
                if (fieldInfo != null) {
                    key = fieldInfo.getName();
                }
                Class<?> cls = value.getClass();
                if ((value instanceof Iterable) || cls.isArray()) {
                    Iterator it = Types.iterableOf(value).iterator();
                    while (it.hasNext()) {
                        addHeader(logger, sb, sb2, lowLevelHttpRequest, key, it.next(), writer);
                    }
                } else {
                    addHeader(logger, sb, sb2, lowLevelHttpRequest, key, value, writer);
                }
            }
        }
        if (writer != null) {
            writer.flush();
        }
    }

    public static void serializeHeadersForMultipartRequests(HttpHeaders httpHeaders, StringBuilder sb, Logger logger, Writer writer) throws IOException {
        serializeHeaders(httpHeaders, sb, null, logger, null, writer);
    }

    private static String toStringValue(Object obj) {
        return obj instanceof Enum ? FieldInfo.m457of((Enum<?>) obj).getName() : obj.toString();
    }

    @Override // com.google.api.client.util.GenericData, java.util.AbstractMap
    public HttpHeaders clone() {
        return (HttpHeaders) super.clone();
    }

    public final void fromHttpHeaders(HttpHeaders httpHeaders) {
        try {
            ParseHeaderState parseHeaderState = new ParseHeaderState(this, null);
            serializeHeaders(httpHeaders, null, null, null, new HeaderParsingFakeLevelHttpRequest(this, parseHeaderState));
            parseHeaderState.finish();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public final void fromHttpResponse(LowLevelHttpResponse lowLevelHttpResponse, StringBuilder sb) throws IOException {
        clear();
        ParseHeaderState parseHeaderState = new ParseHeaderState(this, sb);
        int headerCount = lowLevelHttpResponse.getHeaderCount();
        for (int i = 0; i < headerCount; i++) {
            parseHeader(lowLevelHttpResponse.getHeaderName(i), lowLevelHttpResponse.getHeaderValue(i), parseHeaderState);
        }
        parseHeaderState.finish();
    }

    public final String getAccept() {
        return (String) getFirstHeaderValue(this.accept);
    }

    public final String getAcceptEncoding() {
        return (String) getFirstHeaderValue(this.acceptEncoding);
    }

    public final Long getAge() {
        return (Long) getFirstHeaderValue(this.age);
    }

    public final String getAuthenticate() {
        return (String) getFirstHeaderValue(this.authenticate);
    }

    public final List<String> getAuthenticateAsList() {
        return this.authenticate;
    }

    public final String getAuthorization() {
        return (String) getFirstHeaderValue(this.authorization);
    }

    public final List<String> getAuthorizationAsList() {
        return this.authorization;
    }

    public final String getCacheControl() {
        return (String) getFirstHeaderValue(this.cacheControl);
    }

    public final String getContentEncoding() {
        return (String) getFirstHeaderValue(this.contentEncoding);
    }

    public final Long getContentLength() {
        return (Long) getFirstHeaderValue(this.contentLength);
    }

    public final String getContentMD5() {
        return (String) getFirstHeaderValue(this.contentMD5);
    }

    public final String getContentRange() {
        return (String) getFirstHeaderValue(this.contentRange);
    }

    public final String getContentType() {
        return (String) getFirstHeaderValue(this.contentType);
    }

    public final String getCookie() {
        return (String) getFirstHeaderValue(this.cookie);
    }

    public final String getDate() {
        return (String) getFirstHeaderValue(this.date);
    }

    public final String getETag() {
        return (String) getFirstHeaderValue(this.etag);
    }

    public final String getExpires() {
        return (String) getFirstHeaderValue(this.expires);
    }

    public String getFirstHeaderStringValue(String str) {
        Object obj = get(str.toLowerCase(Locale.US));
        if (obj == null) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if ((obj instanceof Iterable) || cls.isArray()) {
            Iterator it = Types.iterableOf(obj).iterator();
            if (it.hasNext()) {
                return toStringValue(it.next());
            }
        }
        return toStringValue(obj);
    }

    public List<String> getHeaderStringValues(String str) {
        Object obj = get(str.toLowerCase(Locale.US));
        if (obj == null) {
            return Collections.emptyList();
        }
        Class<?> cls = obj.getClass();
        if (!(obj instanceof Iterable) && !cls.isArray()) {
            return Collections.singletonList(toStringValue(obj));
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = Types.iterableOf(obj).iterator();
        while (it.hasNext()) {
            arrayList.add(toStringValue(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final String getIfMatch() {
        return (String) getFirstHeaderValue(this.ifMatch);
    }

    public final String getIfModifiedSince() {
        return (String) getFirstHeaderValue(this.ifModifiedSince);
    }

    public final String getIfNoneMatch() {
        return (String) getFirstHeaderValue(this.ifNoneMatch);
    }

    public final String getIfRange() {
        return (String) getFirstHeaderValue(this.ifRange);
    }

    public final String getIfUnmodifiedSince() {
        return (String) getFirstHeaderValue(this.ifUnmodifiedSince);
    }

    public final String getLastModified() {
        return (String) getFirstHeaderValue(this.lastModified);
    }

    public final String getLocation() {
        return (String) getFirstHeaderValue(this.location);
    }

    public final String getMimeVersion() {
        return (String) getFirstHeaderValue(this.mimeVersion);
    }

    public final String getRange() {
        return (String) getFirstHeaderValue(this.range);
    }

    public final String getRetryAfter() {
        return (String) getFirstHeaderValue(this.retryAfter);
    }

    public final String getUserAgent() {
        return (String) getFirstHeaderValue(this.userAgent);
    }

    void parseHeader(String str, String str2, ParseHeaderState parseHeaderState) {
        List<Type> list = parseHeaderState.context;
        ClassInfo classInfo = parseHeaderState.classInfo;
        ArrayValueMap arrayValueMap = parseHeaderState.arrayValueMap;
        StringBuilder sb = parseHeaderState.logger;
        if (sb != null) {
            sb.append(str + ": " + str2);
            sb.append(StringUtils.LINE_SEPARATOR);
        }
        FieldInfo fieldInfo = classInfo.getFieldInfo(str);
        if (fieldInfo == null) {
            ArrayList arrayList = (ArrayList) get(str);
            if (arrayList == null) {
                arrayList = new ArrayList();
                set(str, (Object) arrayList);
            }
            arrayList.add(str2);
            return;
        }
        Type typeResolveWildcardTypeOrTypeVariable = Data.resolveWildcardTypeOrTypeVariable(list, fieldInfo.getGenericType());
        if (Types.isArray(typeResolveWildcardTypeOrTypeVariable)) {
            Class<?> rawArrayComponentType = Types.getRawArrayComponentType(list, Types.getArrayComponentType(typeResolveWildcardTypeOrTypeVariable));
            arrayValueMap.put(fieldInfo.getField(), rawArrayComponentType, parseValue(rawArrayComponentType, list, str2));
        } else {
            if (!Types.isAssignableToOrFrom(Types.getRawArrayComponentType(list, typeResolveWildcardTypeOrTypeVariable), Iterable.class)) {
                fieldInfo.setValue(this, parseValue(typeResolveWildcardTypeOrTypeVariable, list, str2));
                return;
            }
            Collection<Object> collectionNewCollectionInstance = (Collection) fieldInfo.getValue(this);
            if (collectionNewCollectionInstance == null) {
                collectionNewCollectionInstance = Data.newCollectionInstance(typeResolveWildcardTypeOrTypeVariable);
                fieldInfo.setValue(this, collectionNewCollectionInstance);
            }
            collectionNewCollectionInstance.add(parseValue(typeResolveWildcardTypeOrTypeVariable == Object.class ? null : Types.getIterableParameter(typeResolveWildcardTypeOrTypeVariable), list, str2));
        }
    }

    @Override // com.google.api.client.util.GenericData
    public HttpHeaders set(String str, Object obj) {
        return (HttpHeaders) super.set(str, obj);
    }

    public HttpHeaders setAccept(String str) {
        this.accept = getAsList(str);
        return this;
    }

    public HttpHeaders setAcceptEncoding(String str) {
        this.acceptEncoding = getAsList(str);
        return this;
    }

    public HttpHeaders setAge(Long l) {
        this.age = getAsList(l);
        return this;
    }

    public HttpHeaders setAuthenticate(String str) {
        this.authenticate = getAsList(str);
        return this;
    }

    public HttpHeaders setAuthorization(String str) {
        return setAuthorization(getAsList(str));
    }

    public HttpHeaders setAuthorization(List<String> list) {
        this.authorization = list;
        return this;
    }

    public HttpHeaders setBasicAuthentication(String str, String str2) {
        return setAuthorization("Basic " + Base64.encodeBase64String(StringUtils.getBytesUtf8(((String) Preconditions.checkNotNull(str)) + ":" + ((String) Preconditions.checkNotNull(str2)))));
    }

    public HttpHeaders setCacheControl(String str) {
        this.cacheControl = getAsList(str);
        return this;
    }

    public HttpHeaders setContentEncoding(String str) {
        this.contentEncoding = getAsList(str);
        return this;
    }

    public HttpHeaders setContentLength(Long l) {
        this.contentLength = getAsList(l);
        return this;
    }

    public HttpHeaders setContentMD5(String str) {
        this.contentMD5 = getAsList(str);
        return this;
    }

    public HttpHeaders setContentRange(String str) {
        this.contentRange = getAsList(str);
        return this;
    }

    public HttpHeaders setContentType(String str) {
        this.contentType = getAsList(str);
        return this;
    }

    public HttpHeaders setCookie(String str) {
        this.cookie = getAsList(str);
        return this;
    }

    public HttpHeaders setDate(String str) {
        this.date = getAsList(str);
        return this;
    }

    public HttpHeaders setETag(String str) {
        this.etag = getAsList(str);
        return this;
    }

    public HttpHeaders setExpires(String str) {
        this.expires = getAsList(str);
        return this;
    }

    public HttpHeaders setIfMatch(String str) {
        this.ifMatch = getAsList(str);
        return this;
    }

    public HttpHeaders setIfModifiedSince(String str) {
        this.ifModifiedSince = getAsList(str);
        return this;
    }

    public HttpHeaders setIfNoneMatch(String str) {
        this.ifNoneMatch = getAsList(str);
        return this;
    }

    public HttpHeaders setIfRange(String str) {
        this.ifRange = getAsList(str);
        return this;
    }

    public HttpHeaders setIfUnmodifiedSince(String str) {
        this.ifUnmodifiedSince = getAsList(str);
        return this;
    }

    public HttpHeaders setLastModified(String str) {
        this.lastModified = getAsList(str);
        return this;
    }

    public HttpHeaders setLocation(String str) {
        this.location = getAsList(str);
        return this;
    }

    public HttpHeaders setMimeVersion(String str) {
        this.mimeVersion = getAsList(str);
        return this;
    }

    public HttpHeaders setRange(String str) {
        this.range = getAsList(str);
        return this;
    }

    public HttpHeaders setRetryAfter(String str) {
        this.retryAfter = getAsList(str);
        return this;
    }

    public HttpHeaders setUserAgent(String str) {
        this.userAgent = getAsList(str);
        return this;
    }
}
