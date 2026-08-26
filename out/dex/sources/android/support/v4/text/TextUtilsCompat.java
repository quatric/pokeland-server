package android.support.v4.text;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.Locale;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class TextUtilsCompat {
    private static final String ARAB_SCRIPT_SUBTAG = "Arab";
    private static final String HEBR_SCRIPT_SUBTAG = "Hebr";

    @Deprecated
    public static final Locale ROOT = new Locale("", "");

    private TextUtilsCompat() {
    }

    private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
        byte directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        return (directionality == 1 || directionality == 2) ? 1 : 0;
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
        if (locale == null || locale.equals(ROOT)) {
            return 0;
        }
        String strMaximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
        if (strMaximizeAndGetScript == null) {
            return getLayoutDirectionFromFirstChar(locale);
        }
        return (strMaximizeAndGetScript.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || strMaximizeAndGetScript.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) ? 1 : 0;
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode(str);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                sb.append("&quot;");
            } else if (cCharAt == '<') {
                sb.append("&lt;");
            } else if (cCharAt == '>') {
                sb.append("&gt;");
            } else if (cCharAt == '&') {
                sb.append("&amp;");
            } else if (cCharAt != '\'') {
                sb.append(cCharAt);
            } else {
                sb.append("&#39;");
            }
        }
        return sb.toString();
    }
}
