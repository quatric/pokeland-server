package com.nintendo.npf.sdk.vcm;

import android.support.annotation.RestrictTo;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyTransaction {
    private String orderId;
    private String sku;
    private State state;

    public enum State {
        PURCHASED(0),
        DEFERRED(1);


        /* JADX INFO: renamed from: a */
        private final int f1833a;

        State(int i) {
            this.f1833a = i;
        }

        public int getInt() {
            return this.f1833a;
        }
    }

    VirtualCurrencyTransaction(String str, String str2, State state) {
        this.orderId = str;
        this.sku = str2;
        this.state = state;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static VirtualCurrencyTransaction internalCreate(String str, String str2, State state) {
        return new VirtualCurrencyTransaction(str, str2, state);
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getSKU() {
        return this.sku;
    }

    public State getState() {
        return this.state;
    }
}
