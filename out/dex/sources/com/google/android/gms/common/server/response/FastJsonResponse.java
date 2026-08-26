package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import com.deploygate.service.DeployGateEvent;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@ShowFirstParty
@KeepForSdk
public abstract class FastJsonResponse {

    @VisibleForTesting
    @SafeParcelable.Class(creator = "FieldCreator")
    @ShowFirstParty
    @KeepForSdk
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zai CREATOR = new zai();

        @SafeParcelable.VersionField(getter = "getVersionCode", m432id = 1)
        private final int zale;

        @SafeParcelable.Field(getter = "getTypeIn", m430id = 2)
        protected final int zapq;

        @SafeParcelable.Field(getter = "isTypeInArray", m430id = 3)
        protected final boolean zapr;

        @SafeParcelable.Field(getter = "getTypeOut", m430id = 4)
        protected final int zaps;

        @SafeParcelable.Field(getter = "isTypeOutArray", m430id = 5)
        protected final boolean zapt;

        @SafeParcelable.Field(getter = "getOutputFieldName", m430id = 6)
        protected final String zapu;

        @SafeParcelable.Field(getter = "getSafeParcelableFieldId", m430id = 7)
        protected final int zapv;
        protected final Class<? extends FastJsonResponse> zapw;

        @SafeParcelable.Field(getter = "getConcreteTypeName", m430id = 8)
        private final String zapx;
        private zak zapy;

        @SafeParcelable.Field(getter = "getWrappedConverter", m430id = 9, type = "com.google.android.gms.common.server.converter.ConverterWrapper")
        private FieldConverter<I, O> zapz;

        @SafeParcelable.Constructor
        Field(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) int i2, @SafeParcelable.Param(m431id = 3) boolean z, @SafeParcelable.Param(m431id = 4) int i3, @SafeParcelable.Param(m431id = 5) boolean z2, @SafeParcelable.Param(m431id = 6) String str, @SafeParcelable.Param(m431id = 7) int i4, @SafeParcelable.Param(m431id = 8) String str2, @SafeParcelable.Param(m431id = 9) com.google.android.gms.common.server.converter.zaa zaaVar) {
            this.zale = i;
            this.zapq = i2;
            this.zapr = z;
            this.zaps = i3;
            this.zapt = z2;
            this.zapu = str;
            this.zapv = i4;
            if (str2 == null) {
                this.zapw = null;
                this.zapx = null;
            } else {
                this.zapw = SafeParcelResponse.class;
                this.zapx = str2;
            }
            if (zaaVar == null) {
                this.zapz = null;
            } else {
                this.zapz = (FieldConverter<I, O>) zaaVar.zaci();
            }
        }

        private Field(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends FastJsonResponse> cls, FieldConverter<I, O> fieldConverter) {
            this.zale = 1;
            this.zapq = i;
            this.zapr = z;
            this.zaps = i2;
            this.zapt = z2;
            this.zapu = str;
            this.zapv = i3;
            this.zapw = cls;
            if (cls == null) {
                this.zapx = null;
            } else {
                this.zapx = cls.getCanonicalName();
            }
            this.zapz = fieldConverter;
        }

        @VisibleForTesting
        @KeepForSdk
        public static Field<byte[], byte[]> forBase64(String str, int i) {
            return new Field<>(8, false, 8, false, str, i, null, null);
        }

        @KeepForSdk
        public static Field<Boolean, Boolean> forBoolean(String str, int i) {
            return new Field<>(6, false, 6, false, str, i, null, null);
        }

        @KeepForSdk
        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(String str, int i, Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i, cls, null);
        }

        @KeepForSdk
        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String str, int i, Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i, cls, null);
        }

        @KeepForSdk
        public static Field<Double, Double> forDouble(String str, int i) {
            return new Field<>(4, false, 4, false, str, i, null, null);
        }

        @KeepForSdk
        public static Field<Float, Float> forFloat(String str, int i) {
            return new Field<>(3, false, 3, false, str, i, null, null);
        }

        @VisibleForTesting
        @KeepForSdk
        public static Field<Integer, Integer> forInteger(String str, int i) {
            return new Field<>(0, false, 0, false, str, i, null, null);
        }

        @KeepForSdk
        public static Field<Long, Long> forLong(String str, int i) {
            return new Field<>(2, false, 2, false, str, i, null, null);
        }

        @KeepForSdk
        public static Field<String, String> forString(String str, int i) {
            return new Field<>(7, false, 7, false, str, i, null, null);
        }

        @KeepForSdk
        public static Field<ArrayList<String>, ArrayList<String>> forStrings(String str, int i) {
            return new Field<>(7, true, 7, true, str, i, null, null);
        }

        @KeepForSdk
        public static Field withConverter(String str, int i, FieldConverter<?, ?> fieldConverter, boolean z) {
            return new Field(fieldConverter.zacj(), z, fieldConverter.zack(), false, str, i, null, fieldConverter);
        }

        private final String zacm() {
            String str = this.zapx;
            if (str == null) {
                return null;
            }
            return str;
        }

        private final com.google.android.gms.common.server.converter.zaa zaco() {
            FieldConverter<I, O> fieldConverter = this.zapz;
            if (fieldConverter == null) {
                return null;
            }
            return com.google.android.gms.common.server.converter.zaa.zaa(fieldConverter);
        }

        public final O convert(I i) {
            return this.zapz.convert(i);
        }

        public final I convertBack(O o) {
            return this.zapz.convertBack(o);
        }

        @KeepForSdk
        public int getSafeParcelableFieldId() {
            return this.zapv;
        }

        public String toString() {
            Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add(DeployGateEvent.EXTRA_VERSION_CODE, Integer.valueOf(this.zale)).add("typeIn", Integer.valueOf(this.zapq)).add("typeInArray", Boolean.valueOf(this.zapr)).add("typeOut", Integer.valueOf(this.zaps)).add("typeOutArray", Boolean.valueOf(this.zapt)).add("outputFieldName", this.zapu).add("safeParcelFieldId", Integer.valueOf(this.zapv)).add("concreteTypeName", zacm());
            Class<? extends FastJsonResponse> cls = this.zapw;
            if (cls != null) {
                toStringHelperAdd.add("concreteType.class", cls.getCanonicalName());
            }
            FieldConverter<I, O> fieldConverter = this.zapz;
            if (fieldConverter != null) {
                toStringHelperAdd.add("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return toStringHelperAdd.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zale);
            SafeParcelWriter.writeInt(parcel, 2, this.zapq);
            SafeParcelWriter.writeBoolean(parcel, 3, this.zapr);
            SafeParcelWriter.writeInt(parcel, 4, this.zaps);
            SafeParcelWriter.writeBoolean(parcel, 5, this.zapt);
            SafeParcelWriter.writeString(parcel, 6, this.zapu, false);
            SafeParcelWriter.writeInt(parcel, 7, getSafeParcelableFieldId());
            SafeParcelWriter.writeString(parcel, 8, zacm(), false);
            SafeParcelWriter.writeParcelable(parcel, 9, zaco(), i, false);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        public final void zaa(zak zakVar) {
            this.zapy = zakVar;
        }

        public final Field<I, O> zacl() {
            return new Field<>(this.zale, this.zapq, this.zapr, this.zaps, this.zapt, this.zapu, this.zapv, this.zapx, zaco());
        }

        public final boolean zacn() {
            return this.zapz != null;
        }

        public final FastJsonResponse zacp() throws IllegalAccessException, InstantiationException {
            Class<? extends FastJsonResponse> cls = this.zapw;
            if (cls != SafeParcelResponse.class) {
                return cls.newInstance();
            }
            Preconditions.checkNotNull(this.zapy, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zapy, this.zapx);
        }

        public final Map<String, Field<?, ?>> zacq() {
            Preconditions.checkNotNull(this.zapx);
            Preconditions.checkNotNull(this.zapy);
            return this.zapy.zai(this.zapx);
        }
    }

    @ShowFirstParty
    public interface FieldConverter<I, O> {
        O convert(I i);

        I convertBack(O o);

        int zacj();

        int zack();
    }

    private final <I, O> void zaa(Field<I, O> field, I i) {
        String str = field.zapu;
        O oConvert = field.convert(i);
        switch (field.zaps) {
            case 0:
                if (zaa(str, oConvert)) {
                    setIntegerInternal(field, str, ((Integer) oConvert).intValue());
                    return;
                }
                return;
            case 1:
                zaa((Field<?, ?>) field, str, (BigInteger) oConvert);
                return;
            case 2:
                if (zaa(str, oConvert)) {
                    setLongInternal(field, str, ((Long) oConvert).longValue());
                    return;
                }
                return;
            case 3:
            default:
                int i2 = field.zaps;
                StringBuilder sb = new StringBuilder(44);
                sb.append("Unsupported type for conversion: ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
            case 4:
                if (zaa(str, oConvert)) {
                    zaa((Field<?, ?>) field, str, ((Double) oConvert).doubleValue());
                    return;
                }
                return;
            case 5:
                zaa((Field<?, ?>) field, str, (BigDecimal) oConvert);
                return;
            case 6:
                if (zaa(str, oConvert)) {
                    setBooleanInternal(field, str, ((Boolean) oConvert).booleanValue());
                    return;
                }
                return;
            case 7:
                setStringInternal(field, str, (String) oConvert);
                return;
            case 8:
            case 9:
                if (zaa(str, oConvert)) {
                    setDecodedBytesInternal(field, str, (byte[]) oConvert);
                    return;
                }
                return;
        }
    }

    private static void zaa(StringBuilder sb, Field field, Object obj) {
        if (field.zapq == 11) {
            sb.append(field.zapw.cast(obj).toString());
        } else {
            if (field.zapq != 7) {
                sb.append(obj);
                return;
            }
            sb.append("\"");
            sb.append(JsonUtils.escapeString((String) obj));
            sb.append("\"");
        }
    }

    private static <O> boolean zaa(String str, O o) {
        if (o != null) {
            return true;
        }
        if (!Log.isLoggable("FastJsonResponse", 6)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
        sb.append("Output field (");
        sb.append(str);
        sb.append(") has a null value, but expected a primitive");
        Log.e("FastJsonResponse", sb.toString());
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static <O, I> I zab(Field<I, O> field, Object obj) {
        return ((Field) field).zapz != null ? field.convertBack(obj) : obj;
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String str, ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    @KeepForSdk
    public abstract Map<String, Field<?, ?>> getFieldMappings();

    @KeepForSdk
    protected Object getFieldValue(Field field) {
        String str = field.zapu;
        if (field.zapw == null) {
            return getValueObject(field.zapu);
        }
        Preconditions.checkState(getValueObject(field.zapu) == null, "Concrete field shouldn't be value object: %s", field.zapu);
        boolean z = field.zapt;
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String strSubstring = str.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(strSubstring).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(strSubstring);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KeepForSdk
    protected abstract Object getValueObject(String str);

    @KeepForSdk
    protected boolean isFieldSet(Field field) {
        if (field.zaps != 11) {
            return isPrimitiveFieldSet(field.zapu);
        }
        if (field.zapt) {
            String str = field.zapu;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        String str2 = field.zapu;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    @KeepForSdk
    protected abstract boolean isPrimitiveFieldSet(String str);

    @KeepForSdk
    protected void setBooleanInternal(Field<?, ?> field, String str, boolean z) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    @KeepForSdk
    protected void setDecodedBytesInternal(Field<?, ?> field, String str, byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    @KeepForSdk
    protected void setIntegerInternal(Field<?, ?> field, String str, int i) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    @KeepForSdk
    protected void setLongInternal(Field<?, ?> field, String str, long j) {
        throw new UnsupportedOperationException("Long not supported");
    }

    @KeepForSdk
    protected void setStringInternal(Field<?, ?> field, String str, String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    @KeepForSdk
    protected void setStringsInternal(Field<?, ?> field, String str, ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    @KeepForSdk
    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (isFieldSet(field)) {
                Object objZab = zab(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (objZab != null) {
                    switch (field.zaps) {
                        case 8:
                            sb.append("\"");
                            sb.append(Base64Utils.encode((byte[]) objZab));
                            sb.append("\"");
                            break;
                        case 9:
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe((byte[]) objZab));
                            sb.append("\"");
                            break;
                        case 10:
                            MapUtils.writeStringMapToJson(sb, (HashMap) objZab);
                            break;
                        default:
                            if (field.zapr) {
                                ArrayList arrayList = (ArrayList) objZab;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    if (i > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i);
                                    if (obj != null) {
                                        zaa(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                            } else {
                                zaa(sb, field, objZab);
                            }
                            break;
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    public final <O> void zaa(Field<Double, O> field, double d) {
        if (((Field) field).zapz != null) {
            zaa(field, Double.valueOf(d));
        } else {
            zaa(field, field.zapu, d);
        }
    }

    public final <O> void zaa(Field<Float, O> field, float f) {
        if (((Field) field).zapz != null) {
            zaa(field, Float.valueOf(f));
        } else {
            zaa((Field<?, ?>) field, field.zapu, f);
        }
    }

    public final <O> void zaa(Field<Integer, O> field, int i) {
        if (((Field) field).zapz != null) {
            zaa(field, Integer.valueOf(i));
        } else {
            setIntegerInternal(field, field.zapu, i);
        }
    }

    public final <O> void zaa(Field<Long, O> field, long j) {
        if (((Field) field).zapz != null) {
            zaa(field, Long.valueOf(j));
        } else {
            setLongInternal(field, field.zapu, j);
        }
    }

    public final <O> void zaa(Field<String, O> field, String str) {
        if (((Field) field).zapz != null) {
            zaa(field, str);
        } else {
            setStringInternal(field, field.zapu, str);
        }
    }

    protected void zaa(Field<?, ?> field, String str, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void zaa(Field<?, ?> field, String str, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void zaa(Field<?, ?> field, String str, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void zaa(Field<?, ?> field, String str, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void zaa(Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    protected void zaa(Field<?, ?> field, String str, Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    public final <O> void zaa(Field<BigDecimal, O> field, BigDecimal bigDecimal) {
        if (((Field) field).zapz != null) {
            zaa(field, bigDecimal);
        } else {
            zaa(field, field.zapu, bigDecimal);
        }
    }

    public final <O> void zaa(Field<BigInteger, O> field, BigInteger bigInteger) {
        if (((Field) field).zapz != null) {
            zaa(field, bigInteger);
        } else {
            zaa(field, field.zapu, bigInteger);
        }
    }

    public final <O> void zaa(Field<ArrayList<Integer>, O> field, ArrayList<Integer> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zaa(field, field.zapu, arrayList);
        }
    }

    public final <O> void zaa(Field<Map<String, String>, O> field, Map<String, String> map) {
        if (((Field) field).zapz != null) {
            zaa(field, map);
        } else {
            zaa(field, field.zapu, map);
        }
    }

    public final <O> void zaa(Field<Boolean, O> field, boolean z) {
        if (((Field) field).zapz != null) {
            zaa(field, Boolean.valueOf(z));
        } else {
            setBooleanInternal(field, field.zapu, z);
        }
    }

    public final <O> void zaa(Field<byte[], O> field, byte[] bArr) {
        if (((Field) field).zapz != null) {
            zaa(field, bArr);
        } else {
            setDecodedBytesInternal(field, field.zapu, bArr);
        }
    }

    protected void zab(Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    public final <O> void zab(Field<ArrayList<BigInteger>, O> field, ArrayList<BigInteger> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zab(field, field.zapu, arrayList);
        }
    }

    protected void zac(Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    public final <O> void zac(Field<ArrayList<Long>, O> field, ArrayList<Long> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zac(field, field.zapu, arrayList);
        }
    }

    protected void zad(Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    public final <O> void zad(Field<ArrayList<Float>, O> field, ArrayList<Float> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zad(field, field.zapu, arrayList);
        }
    }

    protected void zae(Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    public final <O> void zae(Field<ArrayList<Double>, O> field, ArrayList<Double> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zae(field, field.zapu, arrayList);
        }
    }

    protected void zaf(Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    public final <O> void zaf(Field<ArrayList<BigDecimal>, O> field, ArrayList<BigDecimal> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zaf(field, field.zapu, arrayList);
        }
    }

    protected void zag(Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    public final <O> void zag(Field<ArrayList<Boolean>, O> field, ArrayList<Boolean> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            zag(field, field.zapu, arrayList);
        }
    }

    public final <O> void zah(Field<ArrayList<String>, O> field, ArrayList<String> arrayList) {
        if (((Field) field).zapz != null) {
            zaa(field, arrayList);
        } else {
            setStringsInternal(field, field.zapu, arrayList);
        }
    }
}
