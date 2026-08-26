package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;

    @Deprecated
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    private static final AccessibilityServiceInfoBaseImpl IMPL;

    @RequiresApi(16)
    static class AccessibilityServiceInfoApi16Impl extends AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoApi16Impl() {
        }

        @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoBaseImpl
        public String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
            return accessibilityServiceInfo.loadDescription(packageManager);
        }
    }

    @RequiresApi(18)
    static class AccessibilityServiceInfoApi18Impl extends AccessibilityServiceInfoApi16Impl {
        AccessibilityServiceInfoApi18Impl() {
        }

        @Override // android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoBaseImpl
        public int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
            return accessibilityServiceInfo.getCapabilities();
        }
    }

    static class AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoBaseImpl() {
        }

        public int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
            return AccessibilityServiceInfoCompat.getCanRetrieveWindowContent(accessibilityServiceInfo) ? 1 : 0;
        }

        public String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
            return null;
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityServiceInfoApi18Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityServiceInfoApi16Impl();
        } else {
            IMPL = new AccessibilityServiceInfoBaseImpl();
        }
    }

    private AccessibilityServiceInfoCompat() {
    }

    public static String capabilityToString(int i) {
        if (i == 1) {
            return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
        }
        if (i == 2) {
            return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
        }
        if (i != 4) {
            return i != 8 ? "UNKNOWN" : "CAPABILITY_CAN_FILTER_KEY_EVENTS";
        }
        return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
    }

    public static String feedbackTypeToString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (i > 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= iNumberOfTrailingZeros ^ (-1);
            if (sb.length() > 1) {
                sb.append(", ");
            }
            if (iNumberOfTrailingZeros == 1) {
                sb.append("FEEDBACK_SPOKEN");
            } else if (iNumberOfTrailingZeros == 2) {
                sb.append("FEEDBACK_HAPTIC");
            } else if (iNumberOfTrailingZeros == 4) {
                sb.append("FEEDBACK_AUDIBLE");
            } else if (iNumberOfTrailingZeros == 8) {
                sb.append("FEEDBACK_VISUAL");
            } else if (iNumberOfTrailingZeros == 16) {
                sb.append("FEEDBACK_GENERIC");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String flagToString(int i) {
        if (i == 1) {
            return "DEFAULT";
        }
        if (i == 2) {
            return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
        }
        if (i == 4) {
            return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
        }
        if (i == 8) {
            return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        }
        if (i == 16) {
            return "FLAG_REPORT_VIEW_IDS";
        }
        if (i != 32) {
            return null;
        }
        return "FLAG_REQUEST_FILTER_KEY_EVENTS";
    }

    @Deprecated
    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getCanRetrieveWindowContent();
    }

    public static int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
        return IMPL.getCapabilities(accessibilityServiceInfo);
    }

    @Deprecated
    public static String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getDescription();
    }

    @Deprecated
    public static String getId(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getId();
    }

    @Deprecated
    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getResolveInfo();
    }

    @Deprecated
    public static String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getSettingsActivityName();
    }

    public static String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
        return IMPL.loadDescription(accessibilityServiceInfo, packageManager);
    }
}
