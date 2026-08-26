package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.internal.base.zak;
import com.google.android.gms.internal.base.zal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ImageManager {
    private static final Object zamg = new Object();
    private static HashSet<Uri> zamh = new HashSet<>();
    private static ImageManager zami;
    private final Context mContext;
    private final Handler mHandler = new zal(Looper.getMainLooper());
    private final ExecutorService zamj = Executors.newFixedThreadPool(4);
    private final zaa zamk = null;
    private final zak zaml = new zak();
    private final Map<com.google.android.gms.common.images.zaa, ImageReceiver> zamm = new HashMap();
    private final Map<Uri, ImageReceiver> zamn = new HashMap();
    private final Map<Uri, Long> zamo = new HashMap();

    @KeepName
    private final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<com.google.android.gms.common.images.zaa> zamp;

        ImageReceiver(Uri uri) {
            super(new zal(Looper.getMainLooper()));
            this.mUri = uri;
            this.zamp = new ArrayList<>();
        }

        @Override // android.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.zamj.execute(ImageManager.this.new zab(this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zab(com.google.android.gms.common.images.zaa zaaVar) {
            Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zamp.add(zaaVar);
        }

        public final void zac(com.google.android.gms.common.images.zaa zaaVar) {
            Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zamp.remove(zaaVar);
        }

        public final void zace() {
            Intent intent = new Intent(Constants.ACTION_LOAD_IMAGE);
            intent.putExtra(Constants.EXTRA_URI, this.mUri);
            intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, this);
            intent.putExtra(Constants.EXTRA_PRIORITY, 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    private static final class zaa extends LruCache<com.google.android.gms.common.images.zab, Bitmap> {
        @Override // android.support.v4.util.LruCache
        protected final /* synthetic */ void entryRemoved(boolean z, com.google.android.gms.common.images.zab zabVar, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, zabVar, bitmap, bitmap2);
        }

        @Override // android.support.v4.util.LruCache
        protected final /* synthetic */ int sizeOf(com.google.android.gms.common.images.zab zabVar, Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            return bitmap2.getHeight() * bitmap2.getRowBytes();
        }
    }

    private final class zab implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor zamr;

        public zab(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.zamr = parcelFileDescriptor;
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z;
            Bitmap bitmap;
            Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            ParcelFileDescriptor parcelFileDescriptor = this.zamr;
            boolean z2 = false;
            Bitmap bitmapDecodeFileDescriptor = null;
            if (parcelFileDescriptor != null) {
                try {
                    bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                } catch (OutOfMemoryError e) {
                    String strValueOf = String.valueOf(this.mUri);
                    StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 34);
                    sb.append("OOM while loading bitmap for uri: ");
                    sb.append(strValueOf);
                    Log.e("ImageManager", sb.toString(), e);
                    z2 = true;
                }
                try {
                    this.zamr.close();
                } catch (IOException e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
                z = z2;
                bitmap = bitmapDecodeFileDescriptor;
            } else {
                bitmap = null;
                z = false;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post(ImageManager.this.new zad(this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                String strValueOf2 = String.valueOf(this.mUri);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 32);
                sb2.append("Latch interrupted while posting ");
                sb2.append(strValueOf2);
                Log.w("ImageManager", sb2.toString());
            }
        }
    }

    private final class zac implements Runnable {
        private final com.google.android.gms.common.images.zaa zams;

        public zac(com.google.android.gms.common.images.zaa zaaVar) {
            this.zams = zaaVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zamm.get(this.zams);
            if (imageReceiver != null) {
                ImageManager.this.zamm.remove(this.zams);
                imageReceiver.zac(this.zams);
            }
            com.google.android.gms.common.images.zab zabVar = this.zams.zamu;
            if (zabVar.uri == null) {
                this.zams.zaa(ImageManager.this.mContext, ImageManager.this.zaml, true);
                return;
            }
            Bitmap bitmapZaa = ImageManager.this.zaa(zabVar);
            if (bitmapZaa != null) {
                this.zams.zaa(ImageManager.this.mContext, bitmapZaa, true);
                return;
            }
            Long l = (Long) ImageManager.this.zamo.get(zabVar.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zams.zaa(ImageManager.this.mContext, ImageManager.this.zaml, true);
                    return;
                }
                ImageManager.this.zamo.remove(zabVar.uri);
            }
            this.zams.zaa(ImageManager.this.mContext, ImageManager.this.zaml);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.zamn.get(zabVar.uri);
            if (imageReceiver2 == null) {
                imageReceiver2 = ImageManager.this.new ImageReceiver(zabVar.uri);
                ImageManager.this.zamn.put(zabVar.uri, imageReceiver2);
            }
            imageReceiver2.zab(this.zams);
            if (!(this.zams instanceof com.google.android.gms.common.images.zad)) {
                ImageManager.this.zamm.put(this.zams, imageReceiver2);
            }
            synchronized (ImageManager.zamg) {
                if (!ImageManager.zamh.contains(zabVar.uri)) {
                    ImageManager.zamh.add(zabVar.uri);
                    imageReceiver2.zace();
                }
            }
        }
    }

    private final class zad implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private final CountDownLatch zadq;
        private boolean zamt;

        public zad(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zamt = z;
            this.zadq = countDownLatch;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // java.lang.Runnable
        public final void run() {
            Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (ImageManager.this.zamk != null) {
                if (this.zamt) {
                    ImageManager.this.zamk.evictAll();
                    System.gc();
                    this.zamt = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                }
                if (z) {
                    ImageManager.this.zamk.put(new com.google.android.gms.common.images.zab(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zamn.remove(this.mUri);
            if (imageReceiver != null) {
                ArrayList arrayList = imageReceiver.zamp;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    com.google.android.gms.common.images.zaa zaaVar = (com.google.android.gms.common.images.zaa) arrayList.get(i);
                    if (z) {
                        zaaVar.zaa(ImageManager.this.mContext, this.mBitmap, false);
                    } else {
                        ImageManager.this.zamo.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                        zaaVar.zaa(ImageManager.this.mContext, ImageManager.this.zaml, false);
                    }
                    if (!(zaaVar instanceof com.google.android.gms.common.images.zad)) {
                        ImageManager.this.zamm.remove(zaaVar);
                    }
                }
            }
            this.zadq.countDown();
            synchronized (ImageManager.zamg) {
                ImageManager.zamh.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
    }

    public static ImageManager create(Context context) {
        if (zami == null) {
            zami = new ImageManager(context, false);
        }
        return zami;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bitmap zaa(com.google.android.gms.common.images.zab zabVar) {
        zaa zaaVar = this.zamk;
        if (zaaVar == null) {
            return null;
        }
        return zaaVar.get(zabVar);
    }

    private final void zaa(com.google.android.gms.common.images.zaa zaaVar) {
        Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
        new zac(zaaVar).run();
    }

    public final void loadImage(ImageView imageView, int i) {
        zaa(new com.google.android.gms.common.images.zac(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zaa(new com.google.android.gms.common.images.zac(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        com.google.android.gms.common.images.zac zacVar = new com.google.android.gms.common.images.zac(imageView, uri);
        zacVar.zamw = i;
        zaa(zacVar);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zaa(new com.google.android.gms.common.images.zad(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        com.google.android.gms.common.images.zad zadVar = new com.google.android.gms.common.images.zad(onImageLoadedListener, uri);
        zadVar.zamw = i;
        zaa(zadVar);
    }
}
