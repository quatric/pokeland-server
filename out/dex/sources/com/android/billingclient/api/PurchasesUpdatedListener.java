package com.android.billingclient.api;

import android.support.annotation.Nullable;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface PurchasesUpdatedListener {
    void onPurchasesUpdated(int i, @Nullable List<Purchase> list);
}
