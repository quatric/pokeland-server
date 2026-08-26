package com.google.api.client.http;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.api.client.repackaged.com.google.common.base.Splitter;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class UriTemplate {
    private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
    static final Map<Character, CompositeOutput> COMPOSITE_PREFIXES = new HashMap();

    private enum CompositeOutput {
        PLUS('+', "", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true),
        HASH('#', "#", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true),
        DOT('.', ".", ".", false, false),
        FORWARD_SLASH(Character.valueOf(JsonPointer.SEPARATOR), "/", "/", false, false),
        SEMI_COLON(';', ";", ";", true, false),
        QUERY('?', "?", "&", true, false),
        AMP(Character.valueOf(Typography.amp), "&", "&", true, false),
        SIMPLE(null, "", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, false);

        private final String explodeJoiner;
        private final String outputPrefix;
        private final Character propertyPrefix;
        private final boolean requiresVarAssignment;
        private final boolean reservedExpansion;

        CompositeOutput(Character ch, String str, String str2, boolean z, boolean z2) {
            this.propertyPrefix = ch;
            this.outputPrefix = (String) Preconditions.checkNotNull(str);
            this.explodeJoiner = (String) Preconditions.checkNotNull(str2);
            this.requiresVarAssignment = z;
            this.reservedExpansion = z2;
            if (ch != null) {
                UriTemplate.COMPOSITE_PREFIXES.put(ch, this);
            }
        }

        String getEncodedValue(String str) {
            return this.reservedExpansion ? CharEscapers.escapeUriPath(str) : CharEscapers.escapeUri(str);
        }

        String getExplodeJoiner() {
            return this.explodeJoiner;
        }

        String getOutputPrefix() {
            return this.outputPrefix;
        }

        boolean getReservedExpansion() {
            return this.reservedExpansion;
        }

        int getVarNameStartIndex() {
            return this.propertyPrefix == null ? 0 : 1;
        }

        boolean requiresVarAssignment() {
            return this.requiresVarAssignment;
        }
    }

    static {
        CompositeOutput.values();
    }

    public static String expand(String str, Object obj, boolean z) {
        Object listPropertyValue;
        Map<String, Object> map = getMap(obj);
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i = 0;
        while (i < length) {
            int iIndexOf = str.indexOf(123, i);
            if (iIndexOf == -1) {
                if (i == 0 && !z) {
                    return str;
                }
                sb.append(str.substring(i));
                break;
            }
            sb.append(str.substring(i, iIndexOf));
            int iIndexOf2 = str.indexOf(125, iIndexOf + 2);
            int i2 = iIndexOf2 + 1;
            String strSubstring = str.substring(iIndexOf + 1, iIndexOf2);
            CompositeOutput compositeOutput = getCompositeOutput(strSubstring);
            ListIterator<String> listIterator = Splitter.m449on(',').splitToList(strSubstring).listIterator();
            boolean z2 = true;
            while (listIterator.hasNext()) {
                String next = listIterator.next();
                boolean zEndsWith = next.endsWith("*");
                int varNameStartIndex = listIterator.nextIndex() == 1 ? compositeOutput.getVarNameStartIndex() : 0;
                int length2 = next.length();
                if (zEndsWith) {
                    length2--;
                }
                String strSubstring2 = next.substring(varNameStartIndex, length2);
                Object objRemove = map.remove(strSubstring2);
                if (objRemove != null) {
                    if (z2) {
                        sb.append(compositeOutput.getOutputPrefix());
                        z2 = false;
                    } else {
                        sb.append(compositeOutput.getExplodeJoiner());
                    }
                    if (objRemove instanceof Iterator) {
                        listPropertyValue = getListPropertyValue(strSubstring2, (Iterator) objRemove, zEndsWith, compositeOutput);
                    } else if ((objRemove instanceof Iterable) || objRemove.getClass().isArray()) {
                        listPropertyValue = getListPropertyValue(strSubstring2, Types.iterableOf(objRemove).iterator(), zEndsWith, compositeOutput);
                    } else if (objRemove.getClass().isEnum()) {
                        if (FieldInfo.m457of((Enum<?>) objRemove).getName() != null) {
                            if (compositeOutput.requiresVarAssignment()) {
                                objRemove = String.format("%s=%s", strSubstring2, objRemove);
                            }
                            objRemove = CharEscapers.escapeUriPath(objRemove.toString());
                        }
                        listPropertyValue = objRemove;
                    } else if (Data.isValueOfPrimitiveType(objRemove)) {
                        if (compositeOutput.requiresVarAssignment()) {
                            objRemove = String.format("%s=%s", strSubstring2, objRemove);
                        }
                        listPropertyValue = compositeOutput.getReservedExpansion() ? CharEscapers.escapeUriPathWithoutReserved(objRemove.toString()) : CharEscapers.escapeUriPath(objRemove.toString());
                    } else {
                        listPropertyValue = getMapPropertyValue(strSubstring2, getMap(objRemove), zEndsWith, compositeOutput);
                    }
                    sb.append(listPropertyValue);
                }
            }
            i = i2;
        }
        if (z) {
            GenericUrl.addQueryParams(map.entrySet(), sb);
        }
        return sb.toString();
    }

    public static String expand(String str, String str2, Object obj, boolean z) {
        if (str2.startsWith("/")) {
            GenericUrl genericUrl = new GenericUrl(str);
            genericUrl.setRawPath(null);
            str2 = genericUrl.build() + str2;
        } else if (!str2.startsWith("http://") && !str2.startsWith("https://")) {
            str2 = str + str2;
        }
        return expand(str2, obj, z);
    }

    static CompositeOutput getCompositeOutput(String str) {
        CompositeOutput compositeOutput = COMPOSITE_PREFIXES.get(Character.valueOf(str.charAt(0)));
        return compositeOutput == null ? CompositeOutput.SIMPLE : compositeOutput;
    }

    private static String getListPropertyValue(String str, Iterator<?> it, boolean z, CompositeOutput compositeOutput) {
        String explodeJoiner;
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            explodeJoiner = compositeOutput.getExplodeJoiner();
        } else {
            if (compositeOutput.requiresVarAssignment()) {
                sb.append(CharEscapers.escapeUriPath(str));
                sb.append("=");
            }
            explodeJoiner = COMPOSITE_NON_EXPLODE_JOINER;
        }
        while (it.hasNext()) {
            if (z && compositeOutput.requiresVarAssignment()) {
                sb.append(CharEscapers.escapeUriPath(str));
                sb.append("=");
            }
            sb.append(compositeOutput.getEncodedValue(it.next().toString()));
            if (it.hasNext()) {
                sb.append(explodeJoiner);
            }
        }
        return sb.toString();
    }

    private static Map<String, Object> getMap(Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Object> entry : Data.mapOf(obj).entrySet()) {
            Object value = entry.getValue();
            if (value != null && !Data.isNull(value)) {
                linkedHashMap.put(entry.getKey(), value);
            }
        }
        return linkedHashMap;
    }

    private static String getMapPropertyValue(String str, Map<String, Object> map, boolean z, CompositeOutput compositeOutput) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String str2 = "=";
        String explodeJoiner = COMPOSITE_NON_EXPLODE_JOINER;
        if (z) {
            explodeJoiner = compositeOutput.getExplodeJoiner();
        } else {
            if (compositeOutput.requiresVarAssignment()) {
                sb.append(CharEscapers.escapeUriPath(str));
                sb.append("=");
            }
            str2 = COMPOSITE_NON_EXPLODE_JOINER;
        }
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            String encodedValue = compositeOutput.getEncodedValue(next.getKey());
            String encodedValue2 = compositeOutput.getEncodedValue(next.getValue().toString());
            sb.append(encodedValue);
            sb.append(str2);
            sb.append(encodedValue2);
            if (it.hasNext()) {
                sb.append(explodeJoiner);
            }
        }
        return sb.toString();
    }
}
