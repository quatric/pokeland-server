package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UserData implements Parcelable {
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() { // from class: com.amazon.device.iap.model.UserData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserData createFromParcel(Parcel parcel) {
            return new UserData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserData[] newArray(int i) {
            return new UserData[i];
        }
    };
    private static final String MARKETPLACE = "marketplace";
    private static final String USER_ID = "userId";
    private final String marketplace;
    private final String userId;

    private UserData(Parcel parcel) {
        this.userId = parcel.readString();
        this.marketplace = parcel.readString();
    }

    public UserData(UserDataBuilder userDataBuilder) {
        this.userId = userDataBuilder.getUserId();
        this.marketplace = userDataBuilder.getMarketplace();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getMarketplace() {
        return this.marketplace;
    }

    public String getUserId() {
        return this.userId;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(USER_ID, this.userId);
            jSONObject.put(MARKETPLACE, this.marketplace);
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.userId, this.marketplace});
    }
}
