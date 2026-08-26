package com.google.android.gms.common.internal;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.base.C0270R;
import com.google.android.gms.common.util.DeviceProperties;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class SignInButtonImpl extends Button {
    public SignInButtonImpl(Context context) {
        this(context, null);
    }

    public SignInButtonImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.buttonStyle);
    }

    private static int zaa(int i, int i2, int i3, int i4) {
        if (i == 0) {
            return i2;
        }
        if (i == 1) {
            return i3;
        }
        if (i == 2) {
            return i4;
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Unknown color scheme: ");
        sb.append(i);
        throw new IllegalStateException(sb.toString());
    }

    public final void configure(Resources resources, int i, int i2) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        int i3 = (int) ((resources.getDisplayMetrics().density * 48.0f) + 0.5f);
        setMinHeight(i3);
        setMinWidth(i3);
        int iZaa = zaa(i2, C0270R.drawable.common_google_signin_btn_icon_dark, C0270R.drawable.common_google_signin_btn_icon_light, C0270R.drawable.common_google_signin_btn_icon_light);
        int iZaa2 = zaa(i2, C0270R.drawable.common_google_signin_btn_text_dark, C0270R.drawable.common_google_signin_btn_text_light, C0270R.drawable.common_google_signin_btn_text_light);
        if (i == 0 || i == 1) {
            iZaa = iZaa2;
        } else if (i != 2) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Unknown button size: ");
            sb.append(i);
            throw new IllegalStateException(sb.toString());
        }
        Drawable drawableWrap = DrawableCompat.wrap(resources.getDrawable(iZaa));
        DrawableCompat.setTintList(drawableWrap, resources.getColorStateList(C0270R.color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(drawableWrap, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(drawableWrap);
        setTextColor((ColorStateList) Preconditions.checkNotNull(resources.getColorStateList(zaa(i2, C0270R.color.common_google_signin_btn_text_dark, C0270R.color.common_google_signin_btn_text_light, C0270R.color.common_google_signin_btn_text_light))));
        if (i == 0) {
            setText(resources.getString(C0270R.string.common_signin_button_text));
        } else if (i == 1) {
            setText(resources.getString(C0270R.string.common_signin_button_text_long));
        } else {
            if (i != 2) {
                StringBuilder sb2 = new StringBuilder(32);
                sb2.append("Unknown button size: ");
                sb2.append(i);
                throw new IllegalStateException(sb2.toString());
            }
            setText((CharSequence) null);
        }
        setTransformationMethod(null);
        if (DeviceProperties.isWearable(getContext())) {
            setGravity(19);
        }
    }

    public final void configure(Resources resources, SignInButtonConfig signInButtonConfig) {
        configure(resources, signInButtonConfig.getButtonSize(), signInButtonConfig.getColorScheme());
    }
}
