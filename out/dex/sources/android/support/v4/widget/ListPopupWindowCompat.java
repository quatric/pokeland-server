package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListPopupWindow;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ListPopupWindowCompat {
    private ListPopupWindowCompat() {
    }

    public static View.OnTouchListener createDragToOpenListener(ListPopupWindow listPopupWindow, View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return listPopupWindow.createDragToOpenListener(view);
        }
        return null;
    }

    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object obj, View view) {
        return createDragToOpenListener((ListPopupWindow) obj, view);
    }
}
