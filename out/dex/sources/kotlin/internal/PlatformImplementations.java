package kotlin.internal;

import com.deploygate.service.DeployGateEvent;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: PlatformImplementations.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0011"}, m1984d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", DeployGateEvent.EXTRA_EXCEPTION, "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "ReflectAddSuppressedMethod", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public class PlatformImplementations {

    /* JADX INFO: compiled from: PlatformImplementations.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, m1984d2 = {"Lkotlin/internal/PlatformImplementations$ReflectAddSuppressedMethod;", "", "()V", FirebaseAnalytics.Param.METHOD, "Ljava/lang/reflect/Method;", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    private static final class ReflectAddSuppressedMethod {
        public static final ReflectAddSuppressedMethod INSTANCE = new ReflectAddSuppressedMethod();

        @JvmField
        @Nullable
        public static final Method method;

        /* JADX WARN: Code duplicated, block: B:9:0x0041  */
        static {
            boolean z;
            Method[] methods = Throwable.class.getMethods();
            Intrinsics.checkExpressionValueIsNotNull(methods, "throwableClass.methods");
            for (Method it : methods) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                if (Intrinsics.areEqual(it.getName(), "addSuppressed")) {
                    Class<?>[] parameterTypes = it.getParameterTypes();
                    Intrinsics.checkExpressionValueIsNotNull(parameterTypes, "it.parameterTypes");
                    if (Intrinsics.areEqual((Class) ArraysKt.singleOrNull(parameterTypes), Throwable.class)) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    z = false;
                }
                if (z) {
                    method = it;
                }
            }
            it = null;
            method = it;
        }

        private ReflectAddSuppressedMethod() {
        }
    }

    public void addSuppressed(@NotNull Throwable cause, @NotNull Throwable exception) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Method method = ReflectAddSuppressedMethod.method;
        if (method != null) {
            method.invoke(cause, exception);
        }
    }

    @NotNull
    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }

    @Nullable
    public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(matchResult, "matchResult");
        Intrinsics.checkParameterIsNotNull(name, "name");
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }
}
