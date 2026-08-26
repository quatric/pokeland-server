package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzdf;
import com.google.android.gms.internal.measurement.zzdh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzdf<MessageType extends zzdf<MessageType, BuilderType>, BuilderType extends zzdh<MessageType, BuilderType>> implements zzgi {
    private static boolean zzacu = false;
    protected int zzact = 0;

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzez.checkNotNull(iterable);
        if (iterable instanceof zzfp) {
            List<?> listZzvf = ((zzfp) iterable).zzvf();
            zzfp zzfpVar = (zzfp) list;
            int size = list.size();
            for (Object obj : listZzvf) {
                if (obj == null) {
                    int size2 = zzfpVar.size() - size;
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(size2);
                    sb.append(" is null.");
                    String string = sb.toString();
                    for (int size3 = zzfpVar.size() - 1; size3 >= size; size3--) {
                        zzfpVar.remove(size3);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzdp) {
                    zzfpVar.zzc((zzdp) obj);
                } else {
                    zzfpVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzgu) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size4 = list.size();
        for (T t : iterable) {
            if (t == null) {
                int size5 = list.size() - size4;
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Element at index ");
                sb2.append(size5);
                sb2.append(" is null.");
                String string2 = sb2.toString();
                for (int size6 = list.size() - 1; size6 >= size4; size6--) {
                    list.remove(size6);
                }
                throw new NullPointerException(string2);
            }
            list.add(t);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzuk()];
            zzee zzeeVarZzf = zzee.zzf(bArr);
            zzb(zzeeVarZzf);
            zzeeVarZzf.zzth();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + "byte array".length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    void zzam(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zzgi
    public final zzdp zzrs() {
        try {
            zzdx zzdxVarZzas = zzdp.zzas(zzuk());
            zzb(zzdxVarZzas.zzsf());
            return zzdxVarZzas.zzse();
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + "ByteString".length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    int zzrt() {
        throw new UnsupportedOperationException();
    }
}
