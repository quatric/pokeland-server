package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CoroutinesMigration.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\u0000\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\u0000\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\u0000\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\u0000¨\u0006\u0015"}, m1984d2 = {"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib-coroutines"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class CoroutinesMigrationKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> Continuation<T> toContinuation(@NotNull kotlin.coroutines.experimental.Continuation<? super T> toContinuation) {
        Continuation<T> continuation;
        Intrinsics.checkParameterIsNotNull(toContinuation, "$this$toContinuation");
        ExperimentalContinuationMigration experimentalContinuationMigration = (ExperimentalContinuationMigration) (!(toContinuation instanceof ExperimentalContinuationMigration) ? null : toContinuation);
        return (experimentalContinuationMigration == null || (continuation = experimentalContinuationMigration.getContinuation()) == null) ? new ContinuationMigration(toContinuation) : continuation;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final ContinuationInterceptor toContinuationInterceptor(@NotNull kotlin.coroutines.experimental.ContinuationInterceptor toContinuationInterceptor) {
        ContinuationInterceptor interceptor;
        Intrinsics.checkParameterIsNotNull(toContinuationInterceptor, "$this$toContinuationInterceptor");
        ExperimentalContinuationInterceptorMigration experimentalContinuationInterceptorMigration = (ExperimentalContinuationInterceptorMigration) (!(toContinuationInterceptor instanceof ExperimentalContinuationInterceptorMigration) ? null : toContinuationInterceptor);
        return (experimentalContinuationInterceptorMigration == null || (interceptor = experimentalContinuationInterceptorMigration.getInterceptor()) == null) ? new ContinuationInterceptorMigration(toContinuationInterceptor) : interceptor;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final CoroutineContext toCoroutineContext(@NotNull kotlin.coroutines.experimental.CoroutineContext toCoroutineContext) {
        EmptyCoroutineContext emptyCoroutineContextPlus;
        Intrinsics.checkParameterIsNotNull(toCoroutineContext, "$this$toCoroutineContext");
        kotlin.coroutines.experimental.ContinuationInterceptor continuationInterceptor = (kotlin.coroutines.experimental.ContinuationInterceptor) toCoroutineContext.get(kotlin.coroutines.experimental.ContinuationInterceptor.INSTANCE);
        ExperimentalContextMigration experimentalContextMigration = (ExperimentalContextMigration) toCoroutineContext.get(ExperimentalContextMigration.INSTANCE);
        kotlin.coroutines.experimental.CoroutineContext coroutineContextMinusKey = toCoroutineContext.minusKey(kotlin.coroutines.experimental.ContinuationInterceptor.INSTANCE).minusKey(ExperimentalContextMigration.INSTANCE);
        if (experimentalContextMigration == null || (emptyCoroutineContextPlus = experimentalContextMigration.getContext()) == null) {
            emptyCoroutineContextPlus = EmptyCoroutineContext.INSTANCE;
        }
        if (coroutineContextMinusKey != kotlin.coroutines.experimental.EmptyCoroutineContext.INSTANCE) {
            emptyCoroutineContextPlus = emptyCoroutineContextPlus.plus(new ContextMigration(coroutineContextMinusKey));
        }
        return continuationInterceptor == null ? emptyCoroutineContextPlus : emptyCoroutineContextPlus.plus(toContinuationInterceptor(continuationInterceptor));
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> kotlin.coroutines.experimental.Continuation<T> toExperimentalContinuation(@NotNull Continuation<? super T> toExperimentalContinuation) {
        kotlin.coroutines.experimental.Continuation<T> continuation;
        Intrinsics.checkParameterIsNotNull(toExperimentalContinuation, "$this$toExperimentalContinuation");
        ContinuationMigration continuationMigration = (ContinuationMigration) (!(toExperimentalContinuation instanceof ContinuationMigration) ? null : toExperimentalContinuation);
        return (continuationMigration == null || (continuation = continuationMigration.getContinuation()) == null) ? new ExperimentalContinuationMigration(toExperimentalContinuation) : continuation;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(@NotNull ContinuationInterceptor toExperimentalContinuationInterceptor) {
        kotlin.coroutines.experimental.ContinuationInterceptor interceptor;
        Intrinsics.checkParameterIsNotNull(toExperimentalContinuationInterceptor, "$this$toExperimentalContinuationInterceptor");
        ContinuationInterceptorMigration continuationInterceptorMigration = (ContinuationInterceptorMigration) (!(toExperimentalContinuationInterceptor instanceof ContinuationInterceptorMigration) ? null : toExperimentalContinuationInterceptor);
        return (continuationInterceptorMigration == null || (interceptor = continuationInterceptorMigration.getInterceptor()) == null) ? new ExperimentalContinuationInterceptorMigration(toExperimentalContinuationInterceptor) : interceptor;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.experimental.CoroutineContext toExperimentalCoroutineContext(@NotNull CoroutineContext toExperimentalCoroutineContext) {
        kotlin.coroutines.experimental.EmptyCoroutineContext emptyCoroutineContextPlus;
        Intrinsics.checkParameterIsNotNull(toExperimentalCoroutineContext, "$this$toExperimentalCoroutineContext");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) toExperimentalCoroutineContext.get(ContinuationInterceptor.INSTANCE);
        ContextMigration contextMigration = (ContextMigration) toExperimentalCoroutineContext.get(ContextMigration.INSTANCE);
        CoroutineContext coroutineContextMinusKey = toExperimentalCoroutineContext.minusKey(ContinuationInterceptor.INSTANCE).minusKey(ContextMigration.INSTANCE);
        if (contextMigration == null || (emptyCoroutineContextPlus = contextMigration.getContext()) == null) {
            emptyCoroutineContextPlus = kotlin.coroutines.experimental.EmptyCoroutineContext.INSTANCE;
        }
        if (coroutineContextMinusKey != EmptyCoroutineContext.INSTANCE) {
            emptyCoroutineContextPlus = emptyCoroutineContextPlus.plus(new ExperimentalContextMigration(coroutineContextMinusKey));
        }
        return continuationInterceptor == null ? emptyCoroutineContextPlus : emptyCoroutineContextPlus.plus(toExperimentalContinuationInterceptor(continuationInterceptor));
    }

    @NotNull
    public static final <R> Function1<kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function1<? super Continuation<? super R>, ? extends Object> toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction0Migration(toExperimentalSuspendFunction);
    }

    @NotNull
    public static final <T1, R> Function2<T1, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function2<? super T1, ? super Continuation<? super R>, ? extends Object> toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction1Migration(toExperimentalSuspendFunction);
    }

    @NotNull
    public static final <T1, T2, R> Function3<T1, T2, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> toExperimentalSuspendFunction) {
        Intrinsics.checkParameterIsNotNull(toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction2Migration(toExperimentalSuspendFunction);
    }
}
