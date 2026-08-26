package com.google.android.gms.internal.measurement;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhv {
    private static final boolean zzaec;
    private static final zzd zzamg;
    private static final boolean zzamh;
    static final long zzami;
    private static final long zzamj;
    private static final long zzamk;
    private static final long zzaml;
    private static final long zzamm;
    private static final long zzamn;
    private static final long zzamo;
    private static final long zzamp;
    private static final long zzamq;
    private static final long zzamr;
    private static final long zzams;
    private static final long zzamt;
    private static final long zzamu;
    private static final long zzamv;
    private static final int zzamw;
    static final boolean zzamx;
    private static final Logger logger = Logger.getLogger(zzhv.class.getName());
    private static final Unsafe zzaki = zzwv();
    private static final Class<?> zzacx = zzdi.zzrw();
    private static final boolean zzame = zzk(Long.TYPE);
    private static final boolean zzamf = zzk(Integer.TYPE);

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(long j, byte b) {
            Memory.pokeByte((int) (j & (-1)), b);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzhv.zzamx) {
                zzhv.zzb(obj, j, z);
            } else {
                zzhv.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray((int) (j2 & (-1)), bArr, (int) j, (int) j3);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzhv.zzamx) {
                zzhv.zza(obj, j, b);
            } else {
                zzhv.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final boolean zzm(Object obj, long j) {
            return zzhv.zzamx ? zzhv.zzs(obj, j) : zzhv.zzt(obj, j);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final byte zzy(Object obj, long j) {
            return zzhv.zzamx ? zzhv.zzq(obj, j) : zzhv.zzr(obj, j);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(long j, byte b) {
            this.zzana.putByte(j, b);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, double d) {
            this.zzana.putDouble(obj, j, d);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, float f) {
            this.zzana.putFloat(obj, j, f);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, boolean z) {
            this.zzana.putBoolean(obj, j, z);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(byte[] bArr, long j, long j2, long j3) {
            this.zzana.copyMemory(bArr, zzhv.zzami + j, (Object) null, j2, j3);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zze(Object obj, long j, byte b) {
            this.zzana.putByte(obj, j, b);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final boolean zzm(Object obj, long j) {
            return this.zzana.getBoolean(obj, j);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final float zzn(Object obj, long j) {
            return this.zzana.getFloat(obj, j);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final double zzo(Object obj, long j) {
            return this.zzana.getDouble(obj, j);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final byte zzy(Object obj, long j) {
            return this.zzana.getByte(obj, j);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(long j, byte b) {
            Memory.pokeByte(j, b);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(Object obj, long j, boolean z) {
            if (zzhv.zzamx) {
                zzhv.zzb(obj, j, z);
            } else {
                zzhv.zzc(obj, j, z);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray(j2, bArr, (int) j, (int) j3);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzhv.zzamx) {
                zzhv.zza(obj, j, b);
            } else {
                zzhv.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final boolean zzm(Object obj, long j) {
            return zzhv.zzamx ? zzhv.zzs(obj, j) : zzhv.zzt(obj, j);
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        @Override // com.google.android.gms.internal.measurement.zzhv.zzd
        public final byte zzy(Object obj, long j) {
            return zzhv.zzamx ? zzhv.zzq(obj, j) : zzhv.zzr(obj, j);
        }
    }

    static abstract class zzd {
        Unsafe zzana;

        zzd(Unsafe unsafe) {
            this.zzana = unsafe;
        }

        public abstract void zza(long j, byte b);

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public final void zza(Object obj, long j, long j2) {
            this.zzana.putLong(obj, j, j2);
        }

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zza(byte[] bArr, long j, long j2, long j3);

        public final void zzb(Object obj, long j, int i) {
            this.zzana.putInt(obj, j, i);
        }

        public abstract void zze(Object obj, long j, byte b);

        public final int zzk(Object obj, long j) {
            return this.zzana.getInt(obj, j);
        }

        public final long zzl(Object obj, long j) {
            return this.zzana.getLong(obj, j);
        }

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);
    }

    static {
        zzd zzdVar;
        zzd zzbVar = null;
        if (zzaki != null) {
            if (!zzdi.zzrv()) {
                zzbVar = new zzb(zzaki);
            } else if (zzame) {
                zzbVar = new zzc(zzaki);
            } else if (zzamf) {
                zzbVar = new zza(zzaki);
            }
        }
        zzamg = zzbVar;
        zzamh = zzwx();
        zzaec = zzww();
        zzami = zzi(byte[].class);
        zzamj = zzi(boolean[].class);
        zzamk = zzj(boolean[].class);
        zzaml = zzi(int[].class);
        zzamm = zzj(int[].class);
        zzamn = zzi(long[].class);
        zzamo = zzj(long[].class);
        zzamp = zzi(float[].class);
        zzamq = zzj(float[].class);
        zzamr = zzi(double[].class);
        zzams = zzj(double[].class);
        zzamt = zzi(Object[].class);
        zzamu = zzj(Object[].class);
        Field fieldZzwy = zzwy();
        zzamv = (fieldZzwy == null || (zzdVar = zzamg) == null) ? -1L : zzdVar.zzana.objectFieldOffset(fieldZzwy);
        zzamw = (int) (zzami & 7);
        zzamx = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzhv() {
    }

    static byte zza(byte[] bArr, long j) {
        return zzamg.zzy(bArr, zzami + j);
    }

    static void zza(long j, byte b) {
        zzamg.zza(j, b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = ((((int) j) ^ (-1)) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & ((255 << i) ^ (-1))));
    }

    static void zza(Object obj, long j, double d) {
        zzamg.zza(obj, j, d);
    }

    static void zza(Object obj, long j, float f) {
        zzamg.zza(obj, j, f);
    }

    static void zza(Object obj, long j, long j2) {
        zzamg.zza(obj, j, j2);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzamg.zzana.putObject(obj, j, obj2);
    }

    static void zza(Object obj, long j, boolean z) {
        zzamg.zza(obj, j, z);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzamg.zze(bArr, zzami + j, b);
    }

    static void zza(byte[] bArr, long j, long j2, long j3) {
        zzamg.zza(bArr, j, j2, j3);
    }

    static long zzb(ByteBuffer byteBuffer) {
        return zzamg.zzl(byteBuffer, zzamv);
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & ((255 << i) ^ (-1))));
    }

    static void zzb(Object obj, long j, int i) {
        zzamg.zzb(obj, j, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, z ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, z ? (byte) 1 : (byte) 0);
    }

    static <T> T zzh(Class<T> cls) {
        try {
            return (T) zzaki.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzaec) {
            return zzamg.zzana.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzaec) {
            return zzamg.zzana.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzamg.zzk(obj, j);
    }

    private static boolean zzk(Class<?> cls) {
        if (!zzdi.zzrv()) {
            return false;
        }
        try {
            Class<?> cls2 = zzacx;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    static long zzl(Object obj, long j) {
        return zzamg.zzl(obj, j);
    }

    static boolean zzm(Object obj, long j) {
        return zzamg.zzm(obj, j);
    }

    static float zzn(Object obj, long j) {
        return zzamg.zzn(obj, j);
    }

    static double zzo(Object obj, long j) {
        return zzamg.zzo(obj, j);
    }

    static Object zzp(Object obj, long j) {
        return zzamg.zzana.getObject(obj, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, (-4) & j) >>> ((int) (((j ^ (-1)) & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, (-4) & j) >>> ((int) ((j & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != 0;
    }

    static boolean zzwt() {
        return zzaec;
    }

    static boolean zzwu() {
        return zzamh;
    }

    static Unsafe zzwv() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzhx());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzww() {
        Unsafe unsafe = zzaki;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzdi.zzrv()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(strValueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzwx() {
        Unsafe unsafe = zzaki;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzwy() == null) {
                return false;
            }
            if (zzdi.zzrv()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(strValueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static Field zzwy() {
        Field fieldZzb;
        if (zzdi.zzrv() && (fieldZzb = zzb(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZzb;
        }
        Field fieldZzb2 = zzb(Buffer.class, "address");
        if (fieldZzb2 == null || fieldZzb2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzb2;
    }
}
