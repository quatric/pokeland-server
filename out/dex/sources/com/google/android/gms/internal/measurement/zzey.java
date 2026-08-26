package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzey;
import com.google.android.gms.internal.measurement.zzey.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzey<MessageType extends zzey<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzdf<MessageType, BuilderType> {
    private static Map<Object, zzey<?, ?>> zzaib = new ConcurrentHashMap();
    protected zzhs zzahz = zzhs.zzwq();
    private int zzaia = -1;

    public static abstract class zza<MessageType extends zzey<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzdh<MessageType, BuilderType> {
        private final MessageType zzahw;
        protected MessageType zzahx;
        private boolean zzahy = false;

        protected zza(MessageType messagetype) {
            this.zzahw = messagetype;
            this.zzahx = (MessageType) messagetype.zza(zzd.zzaig, null, null);
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzgt.zzvy().zzw(messagetype).zzc(messagetype, messagetype2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // com.google.android.gms.internal.measurement.zzdh
        /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public final BuilderType zza(zzeb zzebVar, zzel zzelVar) throws IOException {
            zzuc();
            try {
                zzgt.zzvy().zzw(this.zzahx).zza(this.zzahx, zzec.zza(zzebVar), zzelVar);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        private final BuilderType zzb(byte[] bArr, int i, int i2, zzel zzelVar) throws zzfi {
            zzuc();
            try {
                zzgt.zzvy().zzw(this.zzahx).zza(this.zzahx, bArr, 0, i2 + 0, new zzdk(zzelVar));
                return this;
            } catch (zzfi e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (IndexOutOfBoundsException unused) {
                throw zzfi.zzut();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.measurement.zzdh
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzahw.zza(zzd.zzaih, null, null);
            zzaVar.zza((zzey) zzuf());
            return zzaVar;
        }

        @Override // com.google.android.gms.internal.measurement.zzgk
        public final boolean isInitialized() {
            return zzey.zza(this.zzahx, false);
        }

        @Override // com.google.android.gms.internal.measurement.zzdh
        public final /* synthetic */ zzdh zza(byte[] bArr, int i, int i2, zzel zzelVar) throws zzfi {
            return zzb(bArr, 0, i2, zzelVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzdh
        public final BuilderType zza(MessageType messagetype) {
            zzuc();
            zza(this.zzahx, messagetype);
            return this;
        }

        @Override // com.google.android.gms.internal.measurement.zzdh
        /* JADX INFO: renamed from: zzru */
        public final /* synthetic */ zzdh clone() {
            return (zza) clone();
        }

        protected final void zzuc() {
            if (this.zzahy) {
                MessageType messagetype = (MessageType) this.zzahx.zza(zzd.zzaig, null, null);
                zza(messagetype, this.zzahx);
                this.zzahx = messagetype;
                this.zzahy = false;
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzgh
        /* JADX INFO: renamed from: zzud, reason: merged with bridge method [inline-methods] */
        public MessageType zzuf() {
            if (this.zzahy) {
                return this.zzahx;
            }
            this.zzahx.zzry();
            this.zzahy = true;
            return this.zzahx;
        }

        @Override // com.google.android.gms.internal.measurement.zzgh
        /* JADX INFO: renamed from: zzue, reason: merged with bridge method [inline-methods] */
        public final MessageType zzug() {
            MessageType messagetype = (MessageType) zzuf();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzhq(messagetype);
        }

        @Override // com.google.android.gms.internal.measurement.zzgk
        public final /* synthetic */ zzgi zzuh() {
            return this.zzahw;
        }
    }

    public static abstract class zzb<MessageType extends zzb<MessageType, BuilderType>, BuilderType> extends zzey<MessageType, BuilderType> implements zzgk {
        protected zzeo<Object> zzaic = zzeo.zztr();

        final zzeo<Object> zzuq() {
            if (this.zzaic.isImmutable()) {
                this.zzaic = (zzeo) this.zzaic.clone();
            }
            return this.zzaic;
        }
    }

    public static class zzc<T extends zzey<T, ?>> extends zzdg<T> {
        private final T zzahw;

        public zzc(T t) {
            this.zzahw = t;
        }

        @Override // com.google.android.gms.internal.measurement.zzgr
        public final /* synthetic */ Object zzc(zzeb zzebVar, zzel zzelVar) throws zzfi {
            return zzey.zza(this.zzahw, zzebVar, zzelVar);
        }
    }

    public static final enum zzd {
        public static final int zzaid = 1;
        public static final int zzaie = 2;
        public static final int zzaif = 3;
        public static final int zzaig = 4;
        public static final int zzaih = 5;
        public static final int zzaii = 6;
        public static final int zzaij = 7;
        private static final /* synthetic */ int[] zzaik = {zzaid, zzaie, zzaif, zzaig, zzaih, zzaii, zzaij};
        public static final int zzail = 1;
        public static final int zzaim = 2;
        private static final /* synthetic */ int[] zzain = {zzail, zzaim};
        public static final int zzaio = 1;
        public static final int zzaip = 2;
        private static final /* synthetic */ int[] zzaiq = {zzaio, zzaip};

        public static int[] zzur() {
            return (int[]) zzaik.clone();
        }
    }

    public static class zze<ContainingType extends zzgi, Type> extends zzek<ContainingType, Type> {
    }

    static <T extends zzey<T, ?>> T zza(T t, zzeb zzebVar, zzel zzelVar) throws zzfi {
        T t2 = (T) t.zza(zzd.zzaig, null, null);
        try {
            zzgt.zzvy().zzw(t2).zza(t2, zzec.zza(zzebVar), zzelVar);
            t2.zzry();
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzfi) {
                throw ((zzfi) e.getCause());
            }
            throw new zzfi(e.getMessage()).zzg(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzfi) {
                throw ((zzfi) e2.getCause());
            }
            throw e2;
        }
    }

    private static <T extends zzey<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzel zzelVar) throws zzfi {
        T t2 = (T) t.zza(zzd.zzaig, null, null);
        try {
            zzgt.zzvy().zzw(t2).zza(t2, bArr, 0, i2, new zzdk(zzelVar));
            t2.zzry();
            if (t2.zzact == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzfi) {
                throw ((zzfi) e.getCause());
            }
            throw new zzfi(e.getMessage()).zzg(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw zzfi.zzut().zzg(t2);
        }
    }

    protected static <T extends zzey<T, ?>> T zza(T t, byte[] bArr, zzel zzelVar) throws zzfi {
        T t2 = (T) zza(t, bArr, 0, bArr.length, zzelVar);
        if (t2 == null || t2.isInitialized()) {
            return t2;
        }
        throw new zzfi(new zzhq(t2).getMessage()).zzg(t2);
    }

    protected static <E> zzff<E> zza(zzff<E> zzffVar) {
        int size = zzffVar.size();
        return zzffVar.zzap(size == 0 ? 10 : size << 1);
    }

    protected static zzfg zza(zzfg zzfgVar) {
        int size = zzfgVar.size();
        return zzfgVar.zzap(size == 0 ? 10 : size << 1);
    }

    protected static Object zza(zzgi zzgiVar, String str, Object[] objArr) {
        return new zzgv(zzgiVar, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static <T extends zzey<?, ?>> void zza(Class<T> cls, T t) {
        zzaib.put(cls, t);
    }

    protected static final <T extends zzey<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zzd.zzaid, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzv = zzgt.zzvy().zzw(t).zzv(t);
        if (z) {
            t.zza(zzd.zzaie, zZzv ? t : null, null);
        }
        return zZzv;
    }

    static <T extends zzey<?, ?>> T zzd(Class<T> cls) {
        zzey<?, ?> zzeyVar = zzaib.get(cls);
        if (zzeyVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzeyVar = zzaib.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzeyVar == null) {
            zzeyVar = (T) ((zzey) zzhv.zzh(cls)).zza(zzd.zzaii, (Object) null, (Object) null);
            if (zzeyVar == null) {
                throw new IllegalStateException();
            }
            zzaib.put(cls, zzeyVar);
        }
        return (T) zzeyVar;
    }

    protected static zzfd zzul() {
        return zzfa.zzus();
    }

    protected static zzfg zzum() {
        return zzfw.zzvk();
    }

    protected static <E> zzff<E> zzun() {
        return zzgw.zzwb();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzey) zza(zzd.zzaii, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return zzgt.zzvy().zzw(this).equals(this, (zzey) obj);
        }
        return false;
    }

    public int hashCode() {
        if (this.zzact != 0) {
            return this.zzact;
        }
        this.zzact = zzgt.zzvy().zzw(this).hashCode(this);
        return this.zzact;
    }

    @Override // com.google.android.gms.internal.measurement.zzgk
    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    public String toString() {
        return zzgj.zza(this, super.toString());
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    @Override // com.google.android.gms.internal.measurement.zzdf
    final void zzam(int i) {
        this.zzaia = i;
    }

    @Override // com.google.android.gms.internal.measurement.zzgi
    public final void zzb(zzee zzeeVar) throws IOException {
        zzgt.zzvy().zzf(getClass()).zza(this, zzei.zza(zzeeVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzdf
    final int zzrt() {
        return this.zzaia;
    }

    protected final void zzry() {
        zzgt.zzvy().zzw(this).zzj(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzgk
    public final /* synthetic */ zzgi zzuh() {
        return (zzey) zza(zzd.zzaii, (Object) null, (Object) null);
    }

    protected final <MessageType extends zzey<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzui() {
        return (BuilderType) zza(zzd.zzaih, (Object) null, (Object) null);
    }

    public final BuilderType zzuj() {
        BuilderType buildertype = (BuilderType) zza(zzd.zzaih, (Object) null, (Object) null);
        buildertype.zza(this);
        return buildertype;
    }

    @Override // com.google.android.gms.internal.measurement.zzgi
    public final int zzuk() {
        if (this.zzaia == -1) {
            this.zzaia = zzgt.zzvy().zzw(this).zzt(this);
        }
        return this.zzaia;
    }

    @Override // com.google.android.gms.internal.measurement.zzgi
    public final /* synthetic */ zzgh zzuo() {
        zza zzaVar = (zza) zza(zzd.zzaih, (Object) null, (Object) null);
        zzaVar.zza(this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzgi
    public final /* synthetic */ zzgh zzup() {
        return (zza) zza(zzd.zzaih, (Object) null, (Object) null);
    }
}
