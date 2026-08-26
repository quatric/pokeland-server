package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000b\u001a=\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000bH\u0007¢\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010\"\u0002H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00050\u000bH\u0002¢\u0006\u0002\b\u0016\u001a)\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00170\u0001H\u0007¢\u0006\u0002\b\u0018\u001a\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a@\u0010\u001c\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001e0\u001d\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00150\u001d0\u0001¨\u0006\u001f"}, m1984d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/sequences/SequencesKt")
public class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1 */
    /* JADX INFO: compiled from: Sequences.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, m1984d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, m1985k = 3, m1986mv = {1, 1, 15})
    @DebugMetadata(m1992c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1", m1993f = "Sequences.kt", m1994i = {0, 0, 1, 1}, m1995l = {67, 69}, m1996m = "invokeSuspend", m1997n = {"$this$sequence", "iterator", "$this$sequence", "iterator"}, m1998s = {"L$0", "L$1", "L$0", "L$1"})
    static final class C12181<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0 $defaultValue;
        final /* synthetic */ Sequence $this_ifEmpty;
        Object L$0;
        Object L$1;
        int label;

        /* JADX INFO: renamed from: p$ */
        private SequenceScope f2135p$;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C12181(Sequence sequence, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$this_ifEmpty = sequence;
            this.$defaultValue = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkParameterIsNotNull(completion, "completion");
            C12181 c12181 = new C12181(this.$this_ifEmpty, this.$defaultValue, completion);
            c12181.f2135p$ = (SequenceScope) obj;
            return c12181;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Continuation<? super Unit> continuation) {
            return ((C12181) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope = this.f2135p$;
                Iterator<? extends T> it = this.$this_ifEmpty.iterator();
                if (it.hasNext()) {
                    this.L$0 = sequenceScope;
                    this.L$1 = it;
                    this.label = 1;
                    if (sequenceScope.yieldAll(it, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    Sequence<? extends T> sequence = (Sequence) this.$defaultValue.invoke();
                    this.L$0 = sequenceScope;
                    this.L$1 = it;
                    this.label = 2;
                    if (sequenceScope.yieldAll(sequence, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @InlineOnly
    private static final <T> Sequence<T> Sequence(final Function0<? extends Iterator<? extends T>> function0) {
        return new Sequence<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.Sequence.1
            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator<T> iterator() {
                return (Iterator) function0.invoke();
            }
        };
    }

    @NotNull
    public static final <T> Sequence<T> asSequence(@NotNull final Iterator<? extends T> asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        return SequencesKt.constrainOnce(new Sequence<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator<T> iterator() {
                return asSequence;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Sequence<T> constrainOnce(@NotNull Sequence<? extends T> constrainOnce) {
        Intrinsics.checkParameterIsNotNull(constrainOnce, "$this$constrainOnce");
        return constrainOnce instanceof ConstrainedOnceSequence ? constrainOnce : new ConstrainedOnceSequence(constrainOnce);
    }

    @NotNull
    public static final <T> Sequence<T> emptySequence() {
        return EmptySequence.INSTANCE;
    }

    @NotNull
    public static final <T> Sequence<T> flatten(@NotNull Sequence<? extends Sequence<? extends T>> flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        return flatten$SequencesKt__SequencesKt(flatten, new Function1<Sequence<? extends T>, Iterator<? extends T>>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Iterator<T> invoke(@NotNull Sequence<? extends T> it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.iterator();
            }
        });
    }

    private static final <T, R> Sequence<R> flatten$SequencesKt__SequencesKt(@NotNull Sequence<? extends T> sequence, Function1<? super T, ? extends Iterator<? extends R>> function1) {
        return sequence instanceof TransformingSequence ? ((TransformingSequence) sequence).flatten$kotlin_stdlib(function1) : new FlatteningSequence(sequence, new Function1<T, T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.3
            @Override // kotlin.jvm.functions.Function1
            public final T invoke(T t) {
                return t;
            }
        }, function1);
    }

    @JvmName(name = "flattenSequenceOfIterable")
    @NotNull
    public static final <T> Sequence<T> flattenSequenceOfIterable(@NotNull Sequence<? extends Iterable<? extends T>> flatten) {
        Intrinsics.checkParameterIsNotNull(flatten, "$this$flatten");
        return flatten$SequencesKt__SequencesKt(flatten, new Function1<Iterable<? extends T>, Iterator<? extends T>>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Iterator<T> invoke(@NotNull Iterable<? extends T> it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.iterator();
            }
        });
    }

    @LowPriorityInOverloadResolution
    @NotNull
    public static final <T> Sequence<T> generateSequence(@Nullable final T t, @NotNull Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        return t == null ? EmptySequence.INSTANCE : new GeneratorSequence(new Function0<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final T invoke() {
                return (T) t;
            }
        }, nextFunction);
    }

    @NotNull
    public static final <T> Sequence<T> generateSequence(@NotNull final Function0<? extends T> nextFunction) {
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        return SequencesKt.constrainOnce(new GeneratorSequence(nextFunction, new Function1<T, T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final T invoke(@NotNull T it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return (T) nextFunction.invoke();
            }
        }));
    }

    @NotNull
    public static final <T> Sequence<T> generateSequence(@NotNull Function0<? extends T> seedFunction, @NotNull Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkParameterIsNotNull(seedFunction, "seedFunction");
        Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
        return new GeneratorSequence(seedFunction, nextFunction);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> Sequence<T> ifEmpty(@NotNull Sequence<? extends T> ifEmpty, @NotNull Function0<? extends Sequence<? extends T>> defaultValue) {
        Intrinsics.checkParameterIsNotNull(ifEmpty, "$this$ifEmpty");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        return SequencesKt.sequence(new C12181(ifEmpty, defaultValue, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final <T> Sequence<T> orEmpty(@Nullable Sequence<? extends T> sequence) {
        return sequence != 0 ? sequence : SequencesKt.emptySequence();
    }

    @NotNull
    public static final <T> Sequence<T> sequenceOf(@NotNull T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? SequencesKt.emptySequence() : ArraysKt.asSequence(elements);
    }

    @NotNull
    public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull Sequence<? extends Pair<? extends T, ? extends R>> unzip) {
        Intrinsics.checkParameterIsNotNull(unzip, "$this$unzip");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Pair<? extends T, ? extends R> pair : unzip) {
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.m1991to(arrayList, arrayList2);
    }
}
