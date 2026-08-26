package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.amazon.device.iap.internal.model.ProductBuilder;
import com.amazon.device.iap.internal.util.C0245d;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Product implements Parcelable {
    private static final String COINS_REWARD_AMOUNT = "coinsRewardAmount";
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() { // from class: com.amazon.device.iap.model.Product.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String PRODUCT_TYPE = "productType";
    private static final String SKU = "sku";
    private static final String SMALL_ICON_URL = "smallIconUrl";
    private static final String TITLE = "title";
    private final CoinsReward coinsReward;
    private final String description;
    private final String price;
    private final ProductType productType;
    private final String sku;
    private final String smallIconUrl;
    private final String title;

    private Product(Parcel parcel) {
        this.sku = parcel.readString();
        this.productType = ProductType.valueOf(parcel.readString());
        this.description = parcel.readString();
        this.price = parcel.readString();
        this.smallIconUrl = parcel.readString();
        this.title = parcel.readString();
        this.coinsReward = CoinsReward.from(parcel.dataAvail() > 0 ? parcel.readInt() : 0);
    }

    public Product(ProductBuilder productBuilder) {
        C0245d.m408a((Object) productBuilder.getSku(), SKU);
        C0245d.m408a(productBuilder.getProductType(), PRODUCT_TYPE);
        C0245d.m408a((Object) productBuilder.getDescription(), DESCRIPTION);
        C0245d.m408a((Object) productBuilder.getTitle(), "title");
        C0245d.m408a((Object) productBuilder.getSmallIconUrl(), SMALL_ICON_URL);
        if (ProductType.SUBSCRIPTION != productBuilder.getProductType()) {
            C0245d.m408a((Object) productBuilder.getPrice(), "price");
        }
        this.sku = productBuilder.getSku();
        this.productType = productBuilder.getProductType();
        this.description = productBuilder.getDescription();
        this.price = productBuilder.getPrice();
        this.smallIconUrl = productBuilder.getSmallIconUrl();
        this.title = productBuilder.getTitle();
        this.coinsReward = CoinsReward.from(productBuilder.getCoinsRewardAmount());
    }

    private int getCoinsRewardAmount() {
        CoinsReward coinsReward = this.coinsReward;
        if (coinsReward == null) {
            return 0;
        }
        return coinsReward.getAmount();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CoinsReward getCoinsReward() {
        return this.coinsReward;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPrice() {
        return this.price;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public String getSku() {
        return this.sku;
    }

    public String getSmallIconUrl() {
        return this.smallIconUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(SKU, this.sku);
        jSONObject.put(PRODUCT_TYPE, this.productType);
        jSONObject.put(DESCRIPTION, this.description);
        jSONObject.put("price", this.price);
        jSONObject.put(SMALL_ICON_URL, this.smallIconUrl);
        jSONObject.put("title", this.title);
        jSONObject.put(COINS_REWARD_AMOUNT, getCoinsRewardAmount());
        return jSONObject;
    }

    public String toString() {
        try {
            return toJSON().toString(4);
        } catch (JSONException unused) {
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sku);
        parcel.writeString(this.productType.toString());
        parcel.writeString(this.description);
        parcel.writeString(this.price);
        parcel.writeString(this.smallIconUrl);
        parcel.writeString(this.title);
        parcel.writeInt(getCoinsRewardAmount());
    }
}
