package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

    private static class DisplayManagerCompatApi14Impl extends DisplayManagerCompat {
        private final WindowManager mWindowManager;

        DisplayManagerCompatApi14Impl(Context context) {
            this.mWindowManager = (WindowManager) context.getSystemService("window");
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display getDisplay(int i) {
            Display defaultDisplay = this.mWindowManager.getDefaultDisplay();
            if (defaultDisplay.getDisplayId() == i) {
                return defaultDisplay;
            }
            return null;
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays() {
            return new Display[]{this.mWindowManager.getDefaultDisplay()};
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays(String str) {
            return str == null ? getDisplays() : new Display[0];
        }
    }

    @RequiresApi(17)
    private static class DisplayManagerCompatApi17Impl extends DisplayManagerCompat {
        private final DisplayManager mDisplayManager;

        DisplayManagerCompatApi17Impl(Context context) {
            this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display getDisplay(int i) {
            return this.mDisplayManager.getDisplay(i);
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays() {
            return this.mDisplayManager.getDisplays();
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays(String str) {
            return this.mDisplayManager.getDisplays(str);
        }
    }

    DisplayManagerCompat() {
    }

    public static DisplayManagerCompat getInstance(Context context) {
        DisplayManagerCompat displayManagerCompatApi17Impl;
        synchronized (sInstances) {
            displayManagerCompatApi17Impl = sInstances.get(context);
            if (displayManagerCompatApi17Impl == null) {
                displayManagerCompatApi17Impl = Build.VERSION.SDK_INT >= 17 ? new DisplayManagerCompatApi17Impl(context) : new DisplayManagerCompatApi14Impl(context);
                sInstances.put(context, displayManagerCompatApi17Impl);
            }
        }
        return displayManagerCompatApi17Impl;
    }

    public abstract Display getDisplay(int i);

    public abstract Display[] getDisplays();

    public abstract Display[] getDisplays(String str);
}
