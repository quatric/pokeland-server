package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhb<FieldDescriptorType> extends zzhc<FieldDescriptorType, Object> {
    zzhb(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzhc
    public final void zzry() {
        if (!isImmutable()) {
            for (int i = 0; i < zzwh(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzcf = zzcf(i);
                if (((zzeq) entryZzcf.getKey()).zzty()) {
                    entryZzcf.setValue(Collections.unmodifiableList((List) entryZzcf.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzwi()) {
                if (((zzeq) entry.getKey()).zzty()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzry();
    }
}
