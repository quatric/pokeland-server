package com.google.api.client.http;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.escape.CharEscapers;
import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.PercentEscaper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GenericUrl extends GenericData {
    private static final Escaper URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:", false);
    private String fragment;
    private String host;
    private List<String> pathParts;
    private int port;
    private String scheme;
    private String userInfo;

    public GenericUrl() {
        this.port = -1;
    }

    public GenericUrl(String str) {
        this(parseURL(str));
    }

    private GenericUrl(String str, String str2, int i, String str3, String str4, String str5, String str6) {
        this.port = -1;
        this.scheme = str.toLowerCase(Locale.US);
        this.host = str2;
        this.port = i;
        this.pathParts = toPathParts(str3);
        this.fragment = str4 != null ? CharEscapers.decodeUri(str4) : null;
        if (str5 != null) {
            UrlEncodedParser.parse(str5, this);
        }
        this.userInfo = str6 != null ? CharEscapers.decodeUri(str6) : null;
    }

    public GenericUrl(URI uri) {
        this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getRawPath(), uri.getRawFragment(), uri.getRawQuery(), uri.getRawUserInfo());
    }

    public GenericUrl(URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef(), url.getQuery(), url.getUserInfo());
    }

    static void addQueryParams(Set<Map.Entry<String, Object>> set, StringBuilder sb) {
        boolean zAppendParam = true;
        for (Map.Entry<String, Object> entry : set) {
            Object value = entry.getValue();
            if (value != null) {
                String strEscapeUriQuery = CharEscapers.escapeUriQuery(entry.getKey());
                if (value instanceof Collection) {
                    Iterator it = ((Collection) value).iterator();
                    while (it.hasNext()) {
                        zAppendParam = appendParam(zAppendParam, sb, strEscapeUriQuery, it.next());
                    }
                } else {
                    zAppendParam = appendParam(zAppendParam, sb, strEscapeUriQuery, value);
                }
            }
        }
    }

    private static boolean appendParam(boolean z, StringBuilder sb, String str, Object obj) {
        if (z) {
            z = false;
            sb.append('?');
        } else {
            sb.append(Typography.amp);
        }
        sb.append(str);
        String strEscapeUriQuery = CharEscapers.escapeUriQuery(obj.toString());
        if (strEscapeUriQuery.length() != 0) {
            sb.append('=');
            sb.append(strEscapeUriQuery);
        }
        return z;
    }

    private void appendRawPathFromParts(StringBuilder sb) {
        int size = this.pathParts.size();
        for (int i = 0; i < size; i++) {
            String str = this.pathParts.get(i);
            if (i != 0) {
                sb.append(JsonPointer.SEPARATOR);
            }
            if (str.length() != 0) {
                sb.append(CharEscapers.escapeUriPath(str));
            }
        }
    }

    private static URL parseURL(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static List<String> toPathParts(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        int i = 0;
        while (z) {
            int iIndexOf = str.indexOf(47, i);
            boolean z2 = iIndexOf != -1;
            arrayList.add(CharEscapers.decodeUri(z2 ? str.substring(i, iIndexOf) : str.substring(i)));
            i = iIndexOf + 1;
            z = z2;
        }
        return arrayList;
    }

    private static URI toURI(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void appendRawPath(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        List<String> pathParts = toPathParts(str);
        List<String> list = this.pathParts;
        if (list == null || list.isEmpty()) {
            this.pathParts = pathParts;
            return;
        }
        int size = this.pathParts.size();
        int i = size - 1;
        this.pathParts.set(i, this.pathParts.get(i) + pathParts.get(0));
        this.pathParts.addAll(pathParts.subList(1, pathParts.size()));
    }

    public final String build() {
        return buildAuthority() + buildRelativeUrl();
    }

    public final String buildAuthority() {
        StringBuilder sb = new StringBuilder();
        sb.append((String) Preconditions.checkNotNull(this.scheme));
        sb.append("://");
        String str = this.userInfo;
        if (str != null) {
            sb.append(CharEscapers.escapeUriUserInfo(str));
            sb.append('@');
        }
        sb.append((String) Preconditions.checkNotNull(this.host));
        int i = this.port;
        if (i != -1) {
            sb.append(':');
            sb.append(i);
        }
        return sb.toString();
    }

    public final String buildRelativeUrl() {
        StringBuilder sb = new StringBuilder();
        if (this.pathParts != null) {
            appendRawPathFromParts(sb);
        }
        addQueryParams(entrySet(), sb);
        String str = this.fragment;
        if (str != null) {
            sb.append('#');
            sb.append(URI_FRAGMENT_ESCAPER.escape(str));
        }
        return sb.toString();
    }

    @Override // com.google.api.client.util.GenericData, java.util.AbstractMap
    public GenericUrl clone() {
        GenericUrl genericUrl = (GenericUrl) super.clone();
        List<String> list = this.pathParts;
        if (list != null) {
            genericUrl.pathParts = new ArrayList(list);
        }
        return genericUrl;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (super.equals(obj) && (obj instanceof GenericUrl)) {
            return build().equals(((GenericUrl) obj).build());
        }
        return false;
    }

    public Collection<Object> getAll(String str) {
        Object obj = get(str);
        if (obj == null) {
            return Collections.emptySet();
        }
        return obj instanceof Collection ? Collections.unmodifiableCollection((Collection) obj) : Collections.singleton(obj);
    }

    public Object getFirst(String str) {
        Object obj = get(str);
        if (!(obj instanceof Collection)) {
            return obj;
        }
        Iterator it = ((Collection) obj).iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public String getFragment() {
        return this.fragment;
    }

    public String getHost() {
        return this.host;
    }

    public List<String> getPathParts() {
        return this.pathParts;
    }

    public int getPort() {
        return this.port;
    }

    public String getRawPath() {
        if (this.pathParts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        appendRawPathFromParts(sb);
        return sb.toString();
    }

    public final String getScheme() {
        return this.scheme;
    }

    public final String getUserInfo() {
        return this.userInfo;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        return build().hashCode();
    }

    @Override // com.google.api.client.util.GenericData
    public GenericUrl set(String str, Object obj) {
        return (GenericUrl) super.set(str, obj);
    }

    public final void setFragment(String str) {
        this.fragment = str;
    }

    public final void setHost(String str) {
        this.host = (String) Preconditions.checkNotNull(str);
    }

    public void setPathParts(List<String> list) {
        this.pathParts = list;
    }

    public final void setPort(int i) {
        Preconditions.checkArgument(i >= -1, "expected port >= -1");
        this.port = i;
    }

    public void setRawPath(String str) {
        this.pathParts = toPathParts(str);
    }

    public final void setScheme(String str) {
        this.scheme = (String) Preconditions.checkNotNull(str);
    }

    public final void setUserInfo(String str) {
        this.userInfo = str;
    }

    @Override // java.util.AbstractMap
    public String toString() {
        return build();
    }

    public final URI toURI() {
        return toURI(build());
    }

    public final URL toURL() {
        return parseURL(build());
    }

    public final URL toURL(String str) {
        try {
            return new URL(toURL(), str);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
