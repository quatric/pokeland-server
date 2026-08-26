package kotlin.p025io;

import com.android.billingclient.BuildConfig;
import java.io.Closeable;
import java.io.IOException;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Closeable.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a;\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\b\n\u0006\b\u0011(\n0\u0001¨\u0006\f"}, m1984d2 = {"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "CloseableKt")
public final class CloseableKt {
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @PublishedApi
    public static final void closeFinally(@Nullable Closeable closeable, @Nullable Throwable th) throws IOException {
        if (closeable == null) {
            return;
        }
        if (th == null) {
            closeable.close();
            return;
        }
        try {
            closeable.close();
        } catch (Throwable th2) {
            ExceptionsKt.addSuppressed(th, th2);
        }
    }

    @InlineOnly
    private static final <T extends Closeable, R> R use(T t, Function1<? super T, ? extends R> function1) throws IOException {
        Throwable th = (Throwable) null;
        try {
            R rInvoke = function1.invoke(t);
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                closeFinally(t, th);
            } else if (t != null) {
                t.close();
            }
            InlineMarker.finallyEnd(1);
            return rInvoke;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                closeFinally(t, th);
            } else if (t != null) {
                if (th == null) {
                    t.close();
                } else {
                    try {
                        t.close();
                    } catch (Throwable unused) {
                    }
                }
            }
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }
}
