package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.amazon.device.iap.internal.util.C0245d;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class RequestId implements Parcelable {
    public static final Parcelable.Creator<RequestId> CREATOR = new Parcelable.Creator<RequestId>() { // from class: com.amazon.device.iap.model.RequestId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestId createFromParcel(Parcel parcel) {
            return new RequestId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestId[] newArray(int i) {
            return new RequestId[i];
        }
    };
    private static final String ENCODED_ID = "encodedId";
    private final String encodedId;

    public RequestId() {
        this.encodedId = UUID.randomUUID().toString();
    }

    private RequestId(Parcel parcel) {
        this.encodedId = parcel.readString();
    }

    private RequestId(String str) {
        C0245d.m408a((Object) str, ENCODED_ID);
        this.encodedId = str;
    }

    public static RequestId fromString(String str) {
        return new RequestId(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.encodedId.equals(((RequestId) obj).encodedId);
    }

    public int hashCode() {
        String str = this.encodedId;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ENCODED_ID, this.encodedId);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public String toString() {
        return this.encodedId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.encodedId);
    }
}
