package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    static final TextViewCompatBaseImpl IMPL;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AutoSizeTextType {
    }

    @RequiresApi(16)
    static class TextViewCompatApi16Impl extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getMinLines(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(17)
    static class TextViewCompatApi17Impl extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            boolean z = textView.getLayoutDirection() == 1;
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            if (z) {
                Drawable drawable = compoundDrawables[2];
                Drawable drawable2 = compoundDrawables[0];
                compoundDrawables[0] = drawable;
                compoundDrawables[2] = drawable2;
            }
            return compoundDrawables;
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            boolean z = textView.getLayoutDirection() == 1;
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            boolean z = textView.getLayoutDirection() == 1;
            int i5 = z ? i3 : i;
            if (!z) {
                i = i3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(i5, i2, i, i4);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            boolean z = textView.getLayoutDirection() == 1;
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        }
    }

    @RequiresApi(18)
    static class TextViewCompatApi18Impl extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatApi17Impl, android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatApi17Impl, android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatApi17Impl, android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatApi17Impl, android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }

    @RequiresApi(MotionEventCompat.AXIS_BRAKE)
    static class TextViewCompatApi23Impl extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
            textView.setTextAppearance(i);
        }
    }

    @RequiresApi(MotionEventCompat.AXIS_SCROLL)
    static class TextViewCompatApi26Impl extends TextViewCompatApi23Impl {
        TextViewCompatApi26Impl() {
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getAutoSizeMaxTextSize(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getAutoSizeMinTextSize(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public int getAutoSizeTextType(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }

        @Override // android.support.v4.widget.TextViewCompat.TextViewCompatBaseImpl
        public void setAutoSizeTextTypeWithDefaults(TextView textView, int i) {
            textView.setAutoSizeTextTypeWithDefaults(i);
        }
    }

    static class TextViewCompatBaseImpl {
        private static final int LINES = 1;
        private static final String LOG_TAG = "TextViewCompatBase";
        private static Field sMaxModeField;
        private static boolean sMaxModeFieldFetched;
        private static Field sMaximumField;
        private static boolean sMaximumFieldFetched;
        private static Field sMinModeField;
        private static boolean sMinModeFieldFetched;
        private static Field sMinimumField;
        private static boolean sMinimumFieldFetched;

        TextViewCompatBaseImpl() {
        }

        private static Field retrieveField(String str) {
            Field declaredField = null;
            try {
                declaredField = TextView.class.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (NoSuchFieldException unused) {
                Log.e(LOG_TAG, "Could not retrieve " + str + " field.");
                return declaredField;
            }
        }

        private static int retrieveIntFromField(Field field, TextView textView) {
            try {
                return field.getInt(textView);
            } catch (IllegalAccessException unused) {
                Log.d(LOG_TAG, "Could not retrieve value of " + field.getName() + " field.");
                return -1;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getAutoSizeMaxTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getAutoSizeMinTextSize(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getAutoSizeStepGranularity(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int[] getAutoSizeTextAvailableSizes(TextView textView) {
            return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes() : new int[0];
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getAutoSizeTextType(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextType();
            }
            return 0;
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        public int getMaxLines(TextView textView) {
            if (!sMaxModeFieldFetched) {
                sMaxModeField = retrieveField("mMaxMode");
                sMaxModeFieldFetched = true;
            }
            Field field = sMaxModeField;
            if (field == null || retrieveIntFromField(field, textView) != 1) {
                return -1;
            }
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            Field field2 = sMaximumField;
            if (field2 != null) {
                return retrieveIntFromField(field2, textView);
            }
            return -1;
        }

        public int getMinLines(TextView textView) {
            if (!sMinModeFieldFetched) {
                sMinModeField = retrieveField("mMinMode");
                sMinModeFieldFetched = true;
            }
            Field field = sMinModeField;
            if (field == null || retrieveIntFromField(field, textView) != 1) {
                return -1;
            }
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            Field field2 = sMinimumField;
            if (field2 != null) {
                return retrieveIntFromField(field2, textView);
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setAutoSizeTextTypeWithDefaults(TextView textView, int i) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i);
            }
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        public void setTextAppearance(TextView textView, @StyleRes int i) {
            textView.setTextAppearance(textView.getContext(), i);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            IMPL = new TextViewCompatApi26Impl();
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new TextViewCompatApi23Impl();
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new TextViewCompatApi18Impl();
            return;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new TextViewCompatApi17Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new TextViewCompatApi16Impl();
        } else {
            IMPL = new TextViewCompatBaseImpl();
        }
    }

    private TextViewCompat() {
    }

    public static int getAutoSizeMaxTextSize(TextView textView) {
        return IMPL.getAutoSizeMaxTextSize(textView);
    }

    public static int getAutoSizeMinTextSize(TextView textView) {
        return IMPL.getAutoSizeMinTextSize(textView);
    }

    public static int getAutoSizeStepGranularity(TextView textView) {
        return IMPL.getAutoSizeStepGranularity(textView);
    }

    public static int[] getAutoSizeTextAvailableSizes(TextView textView) {
        return IMPL.getAutoSizeTextAvailableSizes(textView);
    }

    public static int getAutoSizeTextType(TextView textView) {
        return IMPL.getAutoSizeTextType(textView);
    }

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return IMPL.getMaxLines(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return IMPL.getMinLines(textView);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithConfiguration(textView, i, i2, i3, i4);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
        IMPL.setAutoSizeTextTypeUniformWithPresetSizes(textView, iArr, i);
    }

    public static void setAutoSizeTextTypeWithDefaults(TextView textView, int i) {
        IMPL.setAutoSizeTextTypeWithDefaults(textView, i);
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.setCompoundDrawablesRelative(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, i, i2, i3, i4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        IMPL.setTextAppearance(textView, i);
    }
}
