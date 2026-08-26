package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class BitmapCompat {
    static final BitmapCompatBaseImpl IMPL;

    @RequiresApi(18)
    static class BitmapCompatApi18Impl extends BitmapCompatBaseImpl {
        BitmapCompatApi18Impl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapCompatBaseImpl
        public boolean hasMipMap(Bitmap bitmap) {
            return bitmap.hasMipMap();
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapCompatBaseImpl
        public void setHasMipMap(Bitmap bitmap, boolean z) {
            bitmap.setHasMipMap(z);
        }
    }

    @RequiresApi(19)
    static class BitmapCompatApi19Impl extends BitmapCompatApi18Impl {
        BitmapCompatApi19Impl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapCompatBaseImpl
        public int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getAllocationByteCount();
        }
    }

    static class BitmapCompatBaseImpl {
        BitmapCompatBaseImpl() {
        }

        public int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getByteCount();
        }

        public boolean hasMipMap(Bitmap bitmap) {
            return false;
        }

        public void setHasMipMap(Bitmap bitmap, boolean z) {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new BitmapCompatApi19Impl();
        } else if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new BitmapCompatApi18Impl();
        } else {
            IMPL = new BitmapCompatBaseImpl();
        }
    }

    private BitmapCompat() {
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        return IMPL.getAllocationByteCount(bitmap);
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        return IMPL.hasMipMap(bitmap);
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        IMPL.setHasMipMap(bitmap, z);
    }
}
