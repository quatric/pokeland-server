package com.amazon.device.iap.model;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class CoinsReward {
    private final int amount;

    protected CoinsReward(int i) {
        this.amount = i;
    }

    protected static final CoinsReward from(int i) {
        if (i > 0) {
            return new CoinsReward(i);
        }
        return null;
    }

    public int getAmount() {
        return this.amount;
    }
}
