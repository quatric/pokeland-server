package android.support.v4.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.view.MotionEventCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@RequiresApi(MotionEventCompat.AXIS_WHEEL)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String str = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(str).st_mode)) {
                return new File(str);
            }
        } catch (ErrnoException unused) {
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0059 A[Catch: all -> 0x005d, Throwable -> 0x0060, TryCatch #4 {Throwable -> 0x0060, blocks: (B:7:0x0018, B:9:0x001e, B:12:0x0025, B:16:0x002f, B:18:0x003c, B:34:0x005c, B:33:0x0059, B:32:0x0055), top: B:55:0x0018 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0068 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x0073 A[Catch: IOException -> 0x0077, TryCatch #1 {IOException -> 0x0077, blocks: (B:6:0x000e, B:14:0x002b, B:20:0x0041, B:43:0x006a, B:47:0x0073, B:46:0x006f, B:48:0x0076), top: B:52:0x000e, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x006a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:? A[Catch: IOException -> 0x0077, SYNTHETIC, TRY_LEAVE, TryCatch #1 {IOException -> 0x0077, blocks: (B:6:0x000e, B:14:0x002b, B:20:0x0041, B:43:0x006a, B:47:0x0073, B:46:0x006f, B:48:0x0076), top: B:52:0x000e, inners: #2 }] */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        if (fontInfoArr.length < 1) {
            return null;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(findBestInfo(fontInfoArr, i).getUri(), "r", cancellationSignal);
            try {
                try {
                    File file = getFile(parcelFileDescriptorOpenFileDescriptor);
                    if (file != null && file.canRead()) {
                        Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        }
                        return typefaceCreateFromFile;
                    }
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
                    try {
                        Typeface typefaceCreateFromInputStream = super.createFromInputStream(context, fileInputStream);
                        fileInputStream.close();
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        }
                        return typefaceCreateFromInputStream;
                    } catch (Throwable th4) {
                        try {
                            throw th4;
                        } catch (Throwable th5) {
                            th2 = th4;
                            th3 = th5;
                            if (th2 == null) {
                                fileInputStream.close();
                                throw th3;
                            }
                            fileInputStream.close();
                            throw th3;
                        }
                    }
                } catch (Throwable th6) {
                    try {
                        throw th6;
                    } catch (Throwable th7) {
                        th = th6;
                        th = th7;
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            throw th;
                        }
                        if (th == null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        }
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        } catch (Throwable th8) {
                            th.addSuppressed(th8);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th9) {
                th = th9;
                th = null;
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    throw th;
                }
                if (th == null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                    throw th;
                }
                parcelFileDescriptorOpenFileDescriptor.close();
                throw th;
            }
        } catch (IOException unused) {
            return null;
        }
    }
}
