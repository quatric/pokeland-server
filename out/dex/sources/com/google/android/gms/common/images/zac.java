package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.base.zaj;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zac extends zaa {
    private WeakReference<ImageView> zanb;

    public zac(ImageView imageView, int i) {
        super(null, i);
        Asserts.checkNotNull(imageView);
        this.zanb = new WeakReference<>(imageView);
    }

    public zac(ImageView imageView, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(imageView);
        this.zanb = new WeakReference<>(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zac)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = this.zanb.get();
        ImageView imageView2 = ((zac) obj).zanb.get();
        return (imageView2 == null || imageView == null || !Objects.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    @Override // com.google.android.gms.common.images.zaa
    protected final void zaa(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageView imageView = this.zanb.get();
        if (imageView != null) {
            boolean z4 = (z2 || z3) ? false : true;
            if (z4 && (imageView instanceof zaj)) {
                int iZach = zaj.zach();
                if (this.zamw != 0 && iZach == this.zamw) {
                    return;
                }
            }
            boolean zZaa = zaa(z, z2);
            if (zZaa) {
                Drawable drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof com.google.android.gms.internal.base.zae) {
                    drawable2 = ((com.google.android.gms.internal.base.zae) drawable2).zacf();
                }
                drawable = new com.google.android.gms.internal.base.zae(drawable2, drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zaj) {
                zaj.zaa(z3 ? this.zamu.uri : null);
                zaj.zai(z4 ? this.zamw : 0);
            }
            if (zZaa) {
                ((com.google.android.gms.internal.base.zae) drawable).startTransition(250);
            }
        }
    }
}
