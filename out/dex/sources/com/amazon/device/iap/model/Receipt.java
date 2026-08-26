package com.amazon.device.iap.model;

import com.amazon.device.iap.internal.model.ReceiptBuilder;
import com.amazon.device.iap.internal.util.C0245d;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Receipt {
    private static final String CANCEL_DATE = "endDate";
    private static final Date DATE_CANCELED = new Date(1);
    private static final String PRODUCT_TYPE = "itemType";
    private static final String PURCHASE_DATE = "purchaseDate";
    private static final String RECEIPT_ID = "receiptId";
    private static final String SKU = "sku";
    private final Date cancelDate;
    private final ProductType productType;
    private final Date purchaseDate;
    private final String receiptId;
    private final String sku;

    public Receipt(ReceiptBuilder receiptBuilder) {
        C0245d.m408a((Object) receiptBuilder.getSku(), SKU);
        C0245d.m408a(receiptBuilder.getProductType(), "productType");
        if (ProductType.SUBSCRIPTION == receiptBuilder.getProductType()) {
            C0245d.m408a(receiptBuilder.getPurchaseDate(), PURCHASE_DATE);
        }
        this.receiptId = receiptBuilder.getReceiptId();
        this.sku = receiptBuilder.getSku();
        this.productType = receiptBuilder.getProductType();
        this.purchaseDate = receiptBuilder.getPurchaseDate();
        this.cancelDate = receiptBuilder.getCancelDate();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) obj;
        Date date = this.cancelDate;
        if (date == null) {
            if (receipt.cancelDate != null) {
                return false;
            }
        } else if (!date.equals(receipt.cancelDate)) {
            return false;
        }
        if (this.productType != receipt.productType) {
            return false;
        }
        Date date2 = this.purchaseDate;
        if (date2 == null) {
            if (receipt.purchaseDate != null) {
                return false;
            }
        } else if (!date2.equals(receipt.purchaseDate)) {
            return false;
        }
        String str = this.receiptId;
        if (str == null) {
            if (receipt.receiptId != null) {
                return false;
            }
        } else if (!str.equals(receipt.receiptId)) {
            return false;
        }
        String str2 = this.sku;
        if (str2 == null) {
            if (receipt.sku != null) {
                return false;
            }
        } else if (!str2.equals(receipt.sku)) {
            return false;
        }
        return true;
    }

    public Date getCancelDate() {
        return this.cancelDate;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public String getReceiptId() {
        return this.receiptId;
    }

    public String getSku() {
        return this.sku;
    }

    public int hashCode() {
        Date date = this.cancelDate;
        int iHashCode = ((date == null ? 0 : date.hashCode()) + 31) * 31;
        ProductType productType = this.productType;
        int iHashCode2 = (iHashCode + (productType == null ? 0 : productType.hashCode())) * 31;
        Date date2 = this.purchaseDate;
        int iHashCode3 = (iHashCode2 + (date2 == null ? 0 : date2.hashCode())) * 31;
        String str = this.receiptId;
        int iHashCode4 = (iHashCode3 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.sku;
        return iHashCode4 + (str2 != null ? str2.hashCode() : 0);
    }

    public boolean isCanceled() {
        return this.cancelDate != null;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(RECEIPT_ID, this.receiptId);
            jSONObject.put(SKU, this.sku);
            jSONObject.put(PRODUCT_TYPE, this.productType);
            jSONObject.put(PURCHASE_DATE, this.purchaseDate);
            jSONObject.put(CANCEL_DATE, this.cancelDate);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public String toString() {
        try {
            return toJSON().toString(4);
        } catch (JSONException unused) {
            return null;
        }
    }
}
