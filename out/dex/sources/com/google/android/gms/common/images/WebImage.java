package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "WebImageCreator")
public final class WebImage extends AbstractSafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new zae();

    @SafeParcelable.VersionField(m432id = 1)
    private final int zale;

    @SafeParcelable.Field(getter = "getWidth", m430id = 3)
    private final int zand;

    @SafeParcelable.Field(getter = "getHeight", m430id = 4)
    private final int zane;

    @SafeParcelable.Field(getter = "getUrl", m430id = 2)
    private final Uri zanf;

    @SafeParcelable.Constructor
    WebImage(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) Uri uri, @SafeParcelable.Param(m431id = 3) int i2, @SafeParcelable.Param(m431id = 4) int i3) {
        this.zale = i;
        this.zanf = uri;
        this.zand = i2;
        this.zane = i3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    @KeepForSdk
    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zaa(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    private static Uri zaa(JSONObject jSONObject) {
        if (jSONObject.has(ImagesContract.URL)) {
            try {
                return Uri.parse(jSONObject.getString(ImagesContract.URL));
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof WebImage)) {
            WebImage webImage = (WebImage) obj;
            if (Objects.equal(this.zanf, webImage.zanf) && this.zand == webImage.zand && this.zane == webImage.zane) {
                return true;
            }
        }
        return false;
    }

    public final int getHeight() {
        return this.zane;
    }

    public final Uri getUrl() {
        return this.zanf;
    }

    public final int getWidth() {
        return this.zand;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zanf, Integer.valueOf(this.zand), Integer.valueOf(this.zane));
    }

    @KeepForSdk
    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ImagesContract.URL, this.zanf.toString());
            jSONObject.put("width", this.zand);
            jSONObject.put("height", this.zane);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", Integer.valueOf(this.zand), Integer.valueOf(this.zane), this.zanf.toString());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zale);
        SafeParcelWriter.writeParcelable(parcel, 2, getUrl(), i, false);
        SafeParcelWriter.writeInt(parcel, 3, getWidth());
        SafeParcelWriter.writeInt(parcel, 4, getHeight());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
