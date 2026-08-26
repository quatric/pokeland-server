package kotlin.coroutines.jvm.internal;

import com.fasterxml.jackson.core.JsonPointer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DebugMetadata.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001¢\u0006\u0002\b\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, m1984d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class DebugMetadataKt {
    private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

    private static final void checkDebugMetadataVersion(int i, int i2) {
        if (i2 <= i) {
            return;
        }
        throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + i + ", got " + i2 + ". Please update the Kotlin standard library.").toString());
    }

    private static final DebugMetadata getDebugMetadataAnnotation(@NotNull BaseContinuationImpl baseContinuationImpl) {
        return (DebugMetadata) baseContinuationImpl.getClass().getAnnotation(DebugMetadata.class);
    }

    private static final int getLabel(@NotNull BaseContinuationImpl baseContinuationImpl) {
        try {
            Field field = baseContinuationImpl.getClass().getDeclaredField("label");
            Intrinsics.checkExpressionValueIsNotNull(field, "field");
            field.setAccessible(true);
            Object obj = field.get(baseContinuationImpl);
            if (!(obj instanceof Integer)) {
                obj = null;
            }
            Integer num = (Integer) obj;
            return (num != null ? num.intValue() : 0) - 1;
        } catch (Exception unused) {
            return -1;
        }
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "getSpilledVariableFieldMapping")
    @Nullable
    public static final String[] getSpilledVariableFieldMapping(@NotNull BaseContinuationImpl getSpilledVariableFieldMapping) {
        Intrinsics.checkParameterIsNotNull(getSpilledVariableFieldMapping, "$this$getSpilledVariableFieldMapping");
        DebugMetadata debugMetadataAnnotation = getDebugMetadataAnnotation(getSpilledVariableFieldMapping);
        if (debugMetadataAnnotation == null) {
            return null;
        }
        checkDebugMetadataVersion(1, debugMetadataAnnotation.m1999v());
        ArrayList arrayList = new ArrayList();
        int label = getLabel(getSpilledVariableFieldMapping);
        int[] iArrM1994i = debugMetadataAnnotation.m1994i();
        int length = iArrM1994i.length;
        for (int i = 0; i < length; i++) {
            if (iArrM1994i[i] == label) {
                arrayList.add(debugMetadataAnnotation.m1998s()[i]);
                arrayList.add(debugMetadataAnnotation.m1997n()[i]);
            }
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "getStackTraceElement")
    @Nullable
    public static final StackTraceElement getStackTraceElement(@NotNull BaseContinuationImpl getStackTraceElementImpl) {
        String strM1992c;
        Intrinsics.checkParameterIsNotNull(getStackTraceElementImpl, "$this$getStackTraceElementImpl");
        DebugMetadata debugMetadataAnnotation = getDebugMetadataAnnotation(getStackTraceElementImpl);
        if (debugMetadataAnnotation == null) {
            return null;
        }
        checkDebugMetadataVersion(1, debugMetadataAnnotation.m1999v());
        int label = getLabel(getStackTraceElementImpl);
        int i = label < 0 ? -1 : debugMetadataAnnotation.m1995l()[label];
        String moduleName = ModuleNameRetriever.INSTANCE.getModuleName(getStackTraceElementImpl);
        if (moduleName == null) {
            strM1992c = debugMetadataAnnotation.m1992c();
        } else {
            strM1992c = moduleName + JsonPointer.SEPARATOR + debugMetadataAnnotation.m1992c();
        }
        return new StackTraceElement(strM1992c, debugMetadataAnnotation.m1996m(), debugMetadataAnnotation.m1993f(), i);
    }
}
