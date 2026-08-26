package com.google.android.gms.common.server.response;

import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.metaps.analytics.C0785a;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@ShowFirstParty
@KeepForSdk
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaqf = {'u', 'l', 'l'};
    private static final char[] zaqg = {'r', 'u', 'e'};
    private static final char[] zaqh = {'r', 'u', 'e', Typography.quote};
    private static final char[] zaqi = {'a', 'l', 's', 'e'};
    private static final char[] zaqj = {'a', 'l', 's', 'e', Typography.quote};
    private static final char[] zaqk = {'\n'};
    private static final zaa<Integer> zaqm = new com.google.android.gms.common.server.response.zaa();
    private static final zaa<Long> zaqn = new zab();
    private static final zaa<Float> zaqo = new zac();
    private static final zaa<Double> zaqp = new zad();
    private static final zaa<Boolean> zaqq = new zae();
    private static final zaa<String> zaqr = new zaf();
    private static final zaa<BigInteger> zaqs = new zag();
    private static final zaa<BigDecimal> zaqt = new zah();
    private final char[] zaqa = new char[1];
    private final char[] zaqb = new char[32];
    private final char[] zaqc = new char[1024];
    private final StringBuilder zaqd = new StringBuilder(32);
    private final StringBuilder zaqe = new StringBuilder(1024);
    private final Stack<Integer> zaql = new Stack<>();

    @ShowFirstParty
    @KeepForSdk
    public static class ParseException extends Exception {
        public ParseException(String str) {
            super(str);
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
        }

        public ParseException(Throwable th) {
            super(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface zaa<O> {
        O zah(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final int zaa(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i;
        char cZaj = zaj(bufferedReader);
        if (cZaj == 0) {
            throw new ParseException("Unexpected EOF");
        }
        if (cZaj == ',') {
            throw new ParseException("Missing value");
        }
        if (cZaj == 'n') {
            zab(bufferedReader, zaqf);
            return 0;
        }
        bufferedReader.mark(1024);
        if (cZaj == '\"') {
            i = 0;
            boolean z = false;
            while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                char c = cArr[i];
                if (Character.isISOControl(c)) {
                    throw new ParseException("Unexpected control character while reading string");
                }
                if (c == '\"' && !z) {
                    bufferedReader.reset();
                    bufferedReader.skip(i + 1);
                    return i;
                }
                z = c == '\\' ? !z : false;
                i++;
            }
        } else {
            cArr[0] = cZaj;
            i = 1;
            while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                if (cArr[i] == '}' || cArr[i] == ',' || Character.isWhitespace(cArr[i]) || cArr[i] == ']') {
                    bufferedReader.reset();
                    bufferedReader.skip(i - 1);
                    cArr[i] = 0;
                    return i;
                }
                i++;
            }
        }
        if (i == cArr.length) {
            throw new ParseException("Absurdly long value");
        }
        throw new ParseException("Unexpected EOF");
    }

    private final String zaa(BufferedReader bufferedReader) throws ParseException, IOException {
        this.zaql.push(2);
        char cZaj = zaj(bufferedReader);
        if (cZaj == '\"') {
            this.zaql.push(3);
            String strZab = zab(bufferedReader, this.zaqb, this.zaqd, null);
            zak(3);
            if (zaj(bufferedReader) == ':') {
                return strZab;
            }
            throw new ParseException("Expected key/value separator");
        }
        if (cZaj == ']') {
            zak(2);
            zak(1);
            zak(5);
            return null;
        }
        if (cZaj == '}') {
            zak(2);
            return null;
        }
        StringBuilder sb = new StringBuilder(19);
        sb.append("Unexpected token: ");
        sb.append(cZaj);
        throw new ParseException(sb.toString());
    }

    private final String zaa(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws ParseException, IOException {
        char cZaj = zaj(bufferedReader);
        if (cZaj == '\"') {
            return zab(bufferedReader, cArr, sb, cArr2);
        }
        if (cZaj != 'n') {
            throw new ParseException("Expected string");
        }
        zab(bufferedReader, zaqf);
        return null;
    }

    private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader bufferedReader, FastJsonResponse.Field<?, ?> field) throws ParseException, IOException {
        C0785a.AnonymousClass1 anonymousClass1 = (ArrayList<T>) new ArrayList();
        char cZaj = zaj(bufferedReader);
        if (cZaj == ']') {
            zak(5);
            return anonymousClass1;
        }
        if (cZaj == 'n') {
            zab(bufferedReader, zaqf);
            zak(5);
            return null;
        }
        if (cZaj != '{') {
            StringBuilder sb = new StringBuilder(19);
            sb.append("Unexpected token: ");
            sb.append(cZaj);
            throw new ParseException(sb.toString());
        }
        this.zaql.push(1);
        while (true) {
            try {
                FastJsonResponse fastJsonResponseZacp = field.zacp();
                if (!zaa(bufferedReader, fastJsonResponseZacp)) {
                    return anonymousClass1;
                }
                anonymousClass1.add(fastJsonResponseZacp);
                char cZaj2 = zaj(bufferedReader);
                if (cZaj2 != ',') {
                    if (cZaj2 == ']') {
                        zak(5);
                        return anonymousClass1;
                    }
                    StringBuilder sb2 = new StringBuilder(19);
                    sb2.append("Unexpected token: ");
                    sb2.append(cZaj2);
                    throw new ParseException(sb2.toString());
                }
                if (zaj(bufferedReader) != '{') {
                    throw new ParseException("Expected start of next object in array");
                }
                this.zaql.push(1);
            } catch (IllegalAccessException e) {
                throw new ParseException("Error instantiating inner object", e);
            } catch (InstantiationException e2) {
                throw new ParseException("Error instantiating inner object", e2);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final <O> ArrayList<O> zaa(BufferedReader bufferedReader, zaa<O> zaaVar) throws ParseException, IOException {
        char cZaj = zaj(bufferedReader);
        if (cZaj == 'n') {
            zab(bufferedReader, zaqf);
            return null;
        }
        if (cZaj != '[') {
            throw new ParseException("Expected start of array");
        }
        this.zaql.push(5);
        ArrayList<O> arrayList = new ArrayList<>();
        while (true) {
            bufferedReader.mark(1024);
            char cZaj2 = zaj(bufferedReader);
            if (cZaj2 == 0) {
                throw new ParseException("Unexpected EOF");
            }
            if (cZaj2 != ',') {
                if (cZaj2 == ']') {
                    zak(5);
                    return arrayList;
                }
                bufferedReader.reset();
                arrayList.add(zaaVar.zah(this, bufferedReader));
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:119:0x027f  */
    /* JADX WARN: Code duplicated, block: B:138:0x029b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:139:0x0282 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x027d A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private final boolean zaa(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) throws ParseException, IOException {
        int i;
        HashMap map;
        char cZaj;
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        String strZaa = zaa(bufferedReader);
        if (strZaa == null) {
            zak(1);
            return false;
        }
        while (strZaa != null) {
            FastJsonResponse.Field<?, ?> field = fieldMappings.get(strZaa);
            if (field == null) {
                strZaa = zab(bufferedReader);
            } else {
                this.zaql.push(4);
                switch (field.zapq) {
                    case 0:
                        if (field.zapr) {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, (ArrayList<Integer>) zaa(bufferedReader, zaqm));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zad(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb = new StringBuilder(55);
                                sb.append("Expected end of object or field separator, but found: ");
                                sb.append(cZaj);
                                throw new ParseException(sb.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 1:
                        if (field.zapr) {
                            fastJsonResponse.zab((FastJsonResponse.Field) field, (ArrayList<BigInteger>) zaa(bufferedReader, zaqs));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zaf(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb2 = new StringBuilder(55);
                                sb2.append("Expected end of object or field separator, but found: ");
                                sb2.append(cZaj);
                                throw new ParseException(sb2.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 2:
                        if (field.zapr) {
                            fastJsonResponse.zac(field, zaa(bufferedReader, zaqn));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zae(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb3 = new StringBuilder(55);
                                sb3.append("Expected end of object or field separator, but found: ");
                                sb3.append(cZaj);
                                throw new ParseException(sb3.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 3:
                        if (field.zapr) {
                            fastJsonResponse.zad(field, zaa(bufferedReader, zaqo));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zag(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb4 = new StringBuilder(55);
                                sb4.append("Expected end of object or field separator, but found: ");
                                sb4.append(cZaj);
                                throw new ParseException(sb4.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 4:
                        if (field.zapr) {
                            fastJsonResponse.zae(field, zaa(bufferedReader, zaqp));
                        } else {
                            fastJsonResponse.zaa(field, zah(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb5 = new StringBuilder(55);
                                sb5.append("Expected end of object or field separator, but found: ");
                                sb5.append(cZaj);
                                throw new ParseException(sb5.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 5:
                        if (field.zapr) {
                            fastJsonResponse.zaf(field, zaa(bufferedReader, zaqt));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zai(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb6 = new StringBuilder(55);
                                sb6.append("Expected end of object or field separator, but found: ");
                                sb6.append(cZaj);
                                throw new ParseException(sb6.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 6:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zaa(bufferedReader, false));
                            i = 4;
                            zak(i);
                            zak(2);
                            cZaj = zaj(bufferedReader);
                            if (cZaj != ',') {
                                strZaa = zaa(bufferedReader);
                            } else {
                                if (cZaj == '}') {
                                    StringBuilder sb7 = new StringBuilder(55);
                                    sb7.append("Expected end of object or field separator, but found: ");
                                    sb7.append(cZaj);
                                    throw new ParseException(sb7.toString());
                                }
                                strZaa = null;
                            }
                        } else {
                            fastJsonResponse.zag(field, zaa(bufferedReader, zaqq));
                            i = 4;
                            zak(i);
                            zak(2);
                            cZaj = zaj(bufferedReader);
                            if (cZaj != ',') {
                                strZaa = zaa(bufferedReader);
                            } else {
                                if (cZaj == '}') {
                                    StringBuilder sb8 = new StringBuilder(55);
                                    sb8.append("Expected end of object or field separator, but found: ");
                                    sb8.append(cZaj);
                                    throw new ParseException(sb8.toString());
                                }
                                strZaa = null;
                            }
                        }
                        break;
                    case 7:
                        if (field.zapr) {
                            fastJsonResponse.zah(field, zaa(bufferedReader, zaqr));
                        } else {
                            fastJsonResponse.zaa((FastJsonResponse.Field) field, zac(bufferedReader));
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb9 = new StringBuilder(55);
                                sb9.append("Expected end of object or field separator, but found: ");
                                sb9.append(cZaj);
                                throw new ParseException(sb9.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 8:
                        fastJsonResponse.zaa((FastJsonResponse.Field) field, Base64Utils.decode(zaa(bufferedReader, this.zaqc, this.zaqe, zaqk)));
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb10 = new StringBuilder(55);
                                sb10.append("Expected end of object or field separator, but found: ");
                                sb10.append(cZaj);
                                throw new ParseException(sb10.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 9:
                        fastJsonResponse.zaa((FastJsonResponse.Field) field, Base64Utils.decodeUrlSafe(zaa(bufferedReader, this.zaqc, this.zaqe, zaqk)));
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb11 = new StringBuilder(55);
                                sb11.append("Expected end of object or field separator, but found: ");
                                sb11.append(cZaj);
                                throw new ParseException(sb11.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 10:
                        char cZaj2 = zaj(bufferedReader);
                        if (cZaj2 == 'n') {
                            zab(bufferedReader, zaqf);
                            map = null;
                        } else {
                            if (cZaj2 != '{') {
                                throw new ParseException("Expected start of a map object");
                            }
                            this.zaql.push(1);
                            map = new HashMap();
                            while (true) {
                                char cZaj3 = zaj(bufferedReader);
                                if (cZaj3 == 0) {
                                    throw new ParseException("Unexpected EOF");
                                }
                                if (cZaj3 == '\"') {
                                    String strZab = zab(bufferedReader, this.zaqb, this.zaqd, null);
                                    if (zaj(bufferedReader) != ':') {
                                        String strValueOf = String.valueOf(strZab);
                                        throw new ParseException(strValueOf.length() != 0 ? "No map value found for key ".concat(strValueOf) : new String("No map value found for key "));
                                    }
                                    if (zaj(bufferedReader) != '\"') {
                                        String strValueOf2 = String.valueOf(strZab);
                                        throw new ParseException(strValueOf2.length() != 0 ? "Expected String value for key ".concat(strValueOf2) : new String("Expected String value for key "));
                                    }
                                    map.put(strZab, zab(bufferedReader, this.zaqb, this.zaqd, null));
                                    char cZaj4 = zaj(bufferedReader);
                                    if (cZaj4 != ',') {
                                        if (cZaj4 != '}') {
                                            StringBuilder sb12 = new StringBuilder(48);
                                            sb12.append("Unexpected character while parsing string map: ");
                                            sb12.append(cZaj4);
                                            throw new ParseException(sb12.toString());
                                        }
                                        zak(1);
                                    }
                                } else if (cZaj3 == '}') {
                                    zak(1);
                                }
                                i = 4;
                                zak(i);
                                zak(2);
                                cZaj = zaj(bufferedReader);
                                if (cZaj != ',') {
                                    strZaa = zaa(bufferedReader);
                                } else {
                                    if (cZaj == '}') {
                                        StringBuilder sb13 = new StringBuilder(55);
                                        sb13.append("Expected end of object or field separator, but found: ");
                                        sb13.append(cZaj);
                                        throw new ParseException(sb13.toString());
                                    }
                                    strZaa = null;
                                }
                            }
                        }
                        fastJsonResponse.zaa((FastJsonResponse.Field) field, (Map<String, String>) map);
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb14 = new StringBuilder(55);
                                sb14.append("Expected end of object or field separator, but found: ");
                                sb14.append(cZaj);
                                throw new ParseException(sb14.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    case 11:
                        if (!field.zapr) {
                            char cZaj5 = zaj(bufferedReader);
                            if (cZaj5 == 'n') {
                                zab(bufferedReader, zaqf);
                                fastJsonResponse.addConcreteTypeInternal(field, field.zapu, null);
                            } else {
                                this.zaql.push(1);
                                if (cZaj5 != '{') {
                                    throw new ParseException("Expected start of object");
                                }
                                try {
                                    FastJsonResponse fastJsonResponseZacp = field.zacp();
                                    zaa(bufferedReader, fastJsonResponseZacp);
                                    fastJsonResponse.addConcreteTypeInternal(field, field.zapu, fastJsonResponseZacp);
                                } catch (IllegalAccessException e) {
                                    throw new ParseException("Error instantiating inner object", e);
                                } catch (InstantiationException e2) {
                                    throw new ParseException("Error instantiating inner object", e2);
                                }
                            }
                            break;
                        } else {
                            char cZaj6 = zaj(bufferedReader);
                            if (cZaj6 == 'n') {
                                zab(bufferedReader, zaqf);
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.zapu, null);
                            } else {
                                this.zaql.push(5);
                                if (cZaj6 != '[') {
                                    throw new ParseException("Expected array start");
                                }
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.zapu, zaa(bufferedReader, field));
                            }
                        }
                        i = 4;
                        zak(i);
                        zak(2);
                        cZaj = zaj(bufferedReader);
                        if (cZaj != ',') {
                            strZaa = zaa(bufferedReader);
                        } else {
                            if (cZaj == '}') {
                                StringBuilder sb15 = new StringBuilder(55);
                                sb15.append("Expected end of object or field separator, but found: ");
                                sb15.append(cZaj);
                                throw new ParseException(sb15.toString());
                            }
                            strZaa = null;
                        }
                        break;
                    default:
                        int i2 = field.zapq;
                        StringBuilder sb16 = new StringBuilder(30);
                        sb16.append("Invalid field type ");
                        sb16.append(i2);
                        throw new ParseException(sb16.toString());
                }
            }
        }
        zak(1);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final boolean zaa(BufferedReader bufferedReader, boolean z) throws ParseException, IOException {
        while (true) {
            char cZaj = zaj(bufferedReader);
            if (cZaj != '\"') {
                if (cZaj == 'f') {
                    zab(bufferedReader, z ? zaqj : zaqi);
                    return false;
                }
                if (cZaj == 'n') {
                    zab(bufferedReader, zaqf);
                    return false;
                }
                if (cZaj == 't') {
                    zab(bufferedReader, z ? zaqh : zaqg);
                    return true;
                }
                StringBuilder sb = new StringBuilder(19);
                sb.append("Unexpected token: ");
                sb.append(cZaj);
                throw new ParseException(sb.toString());
            }
            if (z) {
                throw new ParseException("No boolean value found in string");
            }
            z = true;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final String zab(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader.mark(1024);
        char cZaj = zaj(bufferedReader);
        if (cZaj != '\"') {
            if (cZaj == ',') {
                throw new ParseException("Missing value");
            }
            int i = 1;
            if (cZaj == '[') {
                this.zaql.push(5);
                bufferedReader.mark(32);
                if (zaj(bufferedReader) == ']') {
                    zak(5);
                } else {
                    bufferedReader.reset();
                    boolean z = false;
                    boolean z2 = false;
                    while (i > 0) {
                        char cZaj2 = zaj(bufferedReader);
                        if (cZaj2 == 0) {
                            throw new ParseException("Unexpected EOF while parsing array");
                        }
                        if (Character.isISOControl(cZaj2)) {
                            throw new ParseException("Unexpected control character while reading array");
                        }
                        if (cZaj2 == '\"' && !z) {
                            z2 = !z2;
                        }
                        if (cZaj2 == '[' && !z2) {
                            i++;
                        }
                        if (cZaj2 == ']' && !z2) {
                            i--;
                        }
                        z = (cZaj2 == '\\' && z2) ? !z : false;
                    }
                    zak(5);
                }
            } else if (cZaj != '{') {
                bufferedReader.reset();
                zaa(bufferedReader, this.zaqc);
            } else {
                this.zaql.push(1);
                bufferedReader.mark(32);
                char cZaj3 = zaj(bufferedReader);
                if (cZaj3 == '}') {
                    zak(1);
                } else {
                    if (cZaj3 != '\"') {
                        StringBuilder sb = new StringBuilder(18);
                        sb.append("Unexpected token ");
                        sb.append(cZaj3);
                        throw new ParseException(sb.toString());
                    }
                    bufferedReader.reset();
                    zaa(bufferedReader);
                    while (zab(bufferedReader) != null) {
                    }
                    zak(1);
                }
            }
        } else {
            if (bufferedReader.read(this.zaqa) == -1) {
                throw new ParseException("Unexpected EOF while parsing string");
            }
            char c = this.zaqa[0];
            boolean z3 = false;
            while (true) {
                if (c == '\"' && !z3) {
                    break;
                }
                z3 = c == '\\' ? !z3 : false;
                if (bufferedReader.read(this.zaqa) == -1) {
                    throw new ParseException("Unexpected EOF while parsing string");
                }
                c = this.zaqa[0];
                if (Character.isISOControl(c)) {
                    throw new ParseException("Unexpected control character while reading string");
                }
            }
        }
        char cZaj4 = zaj(bufferedReader);
        if (cZaj4 == ',') {
            zak(2);
            return zaa(bufferedReader);
        }
        if (cZaj4 == '}') {
            zak(2);
            return null;
        }
        StringBuilder sb2 = new StringBuilder(18);
        sb2.append("Unexpected token ");
        sb2.append(cZaj4);
        throw new ParseException(sb2.toString());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static String zab(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws ParseException, IOException {
        boolean z;
        sb.setLength(0);
        bufferedReader.mark(cArr.length);
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            int i = bufferedReader.read(cArr);
            if (i == -1) {
                throw new ParseException("Unexpected EOF while parsing string");
            }
            boolean z4 = z3;
            boolean z5 = z2;
            for (int i2 = 0; i2 < i; i2++) {
                char c = cArr[i2];
                if (Character.isISOControl(c)) {
                    if (cArr2 == null) {
                        z = false;
                        break;
                    }
                    int i3 = 0;
                    while (true) {
                        if (i3 >= cArr2.length) {
                            z = false;
                            break;
                        }
                        if (cArr2[i3] == c) {
                            z = true;
                            break;
                        }
                        i3++;
                    }
                    if (!z) {
                        throw new ParseException("Unexpected control character while reading string");
                    }
                }
                if (c == '\"' && !z5) {
                    sb.append(cArr, 0, i2);
                    bufferedReader.reset();
                    bufferedReader.skip(i2 + 1);
                    return z4 ? JsonUtils.unescapeString(sb.toString()) : sb.toString();
                }
                if (c == '\\') {
                    z5 = !z5;
                    z4 = true;
                } else {
                    z5 = false;
                }
            }
            sb.append(cArr, 0, i);
            bufferedReader.mark(cArr.length);
            z2 = z5;
            z3 = z4;
        }
    }

    private final void zab(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i = 0;
        while (i < cArr.length) {
            int i2 = bufferedReader.read(this.zaqb, 0, cArr.length - i);
            if (i2 == -1) {
                throw new ParseException("Unexpected EOF");
            }
            for (int i3 = 0; i3 < i2; i3++) {
                if (cArr[i3 + i] != this.zaqb[i3]) {
                    throw new ParseException("Unexpected character");
                }
            }
            i += i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zac(BufferedReader bufferedReader) throws ParseException, IOException {
        return zaa(bufferedReader, this.zaqb, this.zaqd, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final int zad(BufferedReader bufferedReader) throws ParseException, IOException {
        int i;
        boolean z;
        int i2;
        int i3;
        int i4;
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return 0;
        }
        char[] cArr = this.zaqc;
        if (iZaa <= 0) {
            throw new ParseException("No number to parse");
        }
        if (cArr[0] == '-') {
            i = 1;
            z = true;
            i2 = Integer.MIN_VALUE;
        } else {
            i = 0;
            z = false;
            i2 = -2147483647;
        }
        if (i < iZaa) {
            i3 = i + 1;
            int iDigit = Character.digit(cArr[i], 10);
            if (iDigit < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            i4 = -iDigit;
        } else {
            i3 = i;
            i4 = 0;
        }
        while (i3 < iZaa) {
            int i5 = i3 + 1;
            int iDigit2 = Character.digit(cArr[i3], 10);
            if (iDigit2 < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            if (i4 < -214748364) {
                throw new ParseException("Number too large");
            }
            int i6 = i4 * 10;
            if (i6 < i2 + iDigit2) {
                throw new ParseException("Number too large");
            }
            i4 = i6 - iDigit2;
            i3 = i5;
        }
        if (!z) {
            return -i4;
        }
        if (i3 > 1) {
            return i4;
        }
        throw new ParseException("No digits to parse");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final long zae(BufferedReader bufferedReader) throws ParseException, IOException {
        long j;
        boolean z;
        long j2;
        int i;
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return 0L;
        }
        char[] cArr = this.zaqc;
        if (iZaa <= 0) {
            throw new ParseException("No number to parse");
        }
        int i2 = 0;
        if (cArr[0] == '-') {
            j = Long.MIN_VALUE;
            i2 = 1;
            z = true;
        } else {
            j = -9223372036854775807L;
            z = false;
        }
        if (i2 < iZaa) {
            i = i2 + 1;
            int iDigit = Character.digit(cArr[i2], 10);
            if (iDigit < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            j2 = -iDigit;
        } else {
            j2 = 0;
            i = i2;
        }
        while (i < iZaa) {
            int i3 = i + 1;
            int iDigit2 = Character.digit(cArr[i], 10);
            if (iDigit2 < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            if (j2 < -922337203685477580L) {
                throw new ParseException("Number too large");
            }
            long j3 = j2 * 10;
            long j4 = iDigit2;
            if (j3 < j + j4) {
                throw new ParseException("Number too large");
            }
            j2 = j3 - j4;
            i = i3;
        }
        if (!z) {
            return -j2;
        }
        if (i > 1) {
            return j2;
        }
        throw new ParseException("No digits to parse");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigInteger zaf(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return null;
        }
        return new BigInteger(new String(this.zaqc, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float zag(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.zaqc, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double zah(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zaqc, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigDecimal zai(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqc);
        if (iZaa == 0) {
            return null;
        }
        return new BigDecimal(new String(this.zaqc, 0, iZaa));
    }

    private final char zaj(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zaqa) == -1) {
            return (char) 0;
        }
        while (Character.isWhitespace(this.zaqa[0])) {
            if (bufferedReader.read(this.zaqa) == -1) {
                return (char) 0;
            }
        }
        return this.zaqa[0];
    }

    private final void zak(int i) throws ParseException {
        if (this.zaql.isEmpty()) {
            StringBuilder sb = new StringBuilder(46);
            sb.append("Expected state ");
            sb.append(i);
            sb.append(" but had empty stack");
            throw new ParseException(sb.toString());
        }
        int iIntValue = this.zaql.pop().intValue();
        if (iIntValue == i) {
            return;
        }
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("Expected state ");
        sb2.append(i);
        sb2.append(" but had ");
        sb2.append(iIntValue);
        throw new ParseException(sb2.toString());
    }

    @KeepForSdk
    public void parse(InputStream inputStream, T t) throws ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            try {
                this.zaql.push(0);
                char cZaj = zaj(bufferedReader);
                if (cZaj == 0) {
                    throw new ParseException("No data to parse");
                }
                if (cZaj == '[') {
                    this.zaql.push(5);
                    Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = t.getFieldMappings();
                    if (fieldMappings.size() != 1) {
                        throw new ParseException("Object array response class must have a single Field");
                    }
                    FastJsonResponse.Field<?, ?> value = fieldMappings.entrySet().iterator().next().getValue();
                    t.addConcreteTypeArrayInternal(value, value.zapu, zaa(bufferedReader, value));
                } else {
                    if (cZaj != '{') {
                        StringBuilder sb = new StringBuilder(19);
                        sb.append("Unexpected token: ");
                        sb.append(cZaj);
                        throw new ParseException(sb.toString());
                    }
                    this.zaql.push(1);
                    zaa(bufferedReader, t);
                }
                zak(0);
                try {
                    bufferedReader.close();
                } catch (IOException unused) {
                    Log.w("FastParser", "Failed to close reader while parsing.");
                }
            } catch (IOException e) {
                throw new ParseException(e);
            }
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException unused2) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
            throw th;
        }
    }
}
