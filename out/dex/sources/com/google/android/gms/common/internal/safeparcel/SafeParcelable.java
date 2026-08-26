package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface SafeParcelable extends Parcelable {
    public static final String NULL = "SAFE_PARCELABLE_NULL_STRING";

    public @interface Class {
        String creator();

        boolean validate() default false;
    }

    public @interface Constructor {
    }

    public @interface Field {
        String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";

        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        /* JADX INFO: renamed from: id */
        int m430id();

        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Indicator {
        String getter() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Param {
        /* JADX INFO: renamed from: id */
        int m431id();
    }

    public @interface Reserved {
        int[] value();
    }

    public @interface VersionField {
        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        /* JADX INFO: renamed from: id */
        int m432id();

        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }
}
