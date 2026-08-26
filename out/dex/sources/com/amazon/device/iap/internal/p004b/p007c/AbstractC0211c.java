package com.amazon.device.iap.internal.p004b.p007c;

import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.c.c */
/* JADX INFO: compiled from: GetItemDataCommandBase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0211c extends AbstractC0232i {

    /* JADX INFO: renamed from: a */
    protected final Set<String> f174a;

    AbstractC0211c(C0218e c0218e, String str, Set<String> set) {
        super(c0218e, "getItem_data", str);
        this.f174a = set;
        m354a("skus", set);
    }
}
