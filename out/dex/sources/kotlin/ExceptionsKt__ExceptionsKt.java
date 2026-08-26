package kotlin;

import com.deploygate.service.DeployGateEvent;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Exceptions.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, m1984d2 = {"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "stackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "addSuppressed", "", DeployGateEvent.EXTRA_EXCEPTION, "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/ExceptionsKt")
public class ExceptionsKt__ExceptionsKt {
    public static final void addSuppressed(@NotNull Throwable addSuppressed, @NotNull Throwable exception) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(addSuppressed, "$this$addSuppressed");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(addSuppressed, exception);
    }

    @NotNull
    public static final StackTraceElement[] getStackTrace(@NotNull Throwable stackTrace) {
        Intrinsics.checkParameterIsNotNull(stackTrace, "$this$stackTrace");
        StackTraceElement[] stackTrace2 = stackTrace.getStackTrace();
        if (stackTrace2 == null) {
            Intrinsics.throwNpe();
        }
        return stackTrace2;
    }

    @InlineOnly
    private static final void printStackTrace(@NotNull Throwable th) {
        if (th == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
        }
        th.printStackTrace();
    }

    @InlineOnly
    private static final void printStackTrace(@NotNull Throwable th, PrintStream printStream) {
        if (th == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
        }
        th.printStackTrace(printStream);
    }

    @InlineOnly
    private static final void printStackTrace(@NotNull Throwable th, PrintWriter printWriter) {
        if (th == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
        }
        th.printStackTrace(printWriter);
    }

    public static /* synthetic */ void stackTrace$annotations(Throwable th) {
    }
}
