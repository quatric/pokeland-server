package kotlin.coroutines.experimental.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CoroutineIntrinsics.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\u0007"}, m1984d2 = {"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "CoroutineIntrinsics")
public final class CoroutineIntrinsics {
    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Continuation<T> interceptContinuationIfNeeded(@NotNull CoroutineContext context, @NotNull Continuation<? super T> continuation) {
        Continuation<T> continuationInterceptContinuation;
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) context.get(ContinuationInterceptor.INSTANCE);
        return (continuationInterceptor == null || (continuationInterceptContinuation = continuationInterceptor.interceptContinuation(continuation)) == null) ? continuation : continuationInterceptContinuation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Continuation<T> normalizeContinuation(@NotNull Continuation<? super T> continuation) {
        Continuation<T> continuation2;
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        CoroutineImpl coroutineImpl = (CoroutineImpl) (!(continuation instanceof CoroutineImpl) ? null : continuation);
        return (coroutineImpl == null || (continuation2 = (Continuation<T>) coroutineImpl.getFacade()) == null) ? continuation : continuation2;
    }
}
