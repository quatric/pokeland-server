package kotlin.jvm.internal;

import com.android.billingclient.BuildConfig;
import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
    private final int arity;

    public FunctionReference(int i) {
        this.arity = i;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public FunctionReference(int i, Object obj) {
        super(obj);
        this.arity = i;
    }

    @Override // kotlin.jvm.internal.CallableReference
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    protected KCallable computeReflected() {
        return Reflection.function(this);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FunctionReference)) {
            if (obj instanceof KFunction) {
                return obj.equals(compute());
            }
            return false;
        }
        FunctionReference functionReference = (FunctionReference) obj;
        if (getOwner() != null ? getOwner().equals(functionReference.getOwner()) : functionReference.getOwner() == null) {
            if (getName().equals(functionReference.getName()) && getSignature().equals(functionReference.getSignature()) && Intrinsics.areEqual(getBoundReceiver(), functionReference.getBoundReceiver())) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionBase
    public int getArity() {
        return this.arity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.jvm.internal.CallableReference
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public KFunction getReflected() {
        return (KFunction) super.getReflected();
    }

    public int hashCode() {
        return (((getOwner() == null ? 0 : getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    @Override // kotlin.reflect.KFunction
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public boolean isExternal() {
        return getReflected().isExternal();
    }

    @Override // kotlin.reflect.KFunction
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public boolean isInfix() {
        return getReflected().isInfix();
    }

    @Override // kotlin.reflect.KFunction
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public boolean isInline() {
        return getReflected().isInline();
    }

    @Override // kotlin.reflect.KFunction
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public boolean isOperator() {
        return getReflected().isOperator();
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public boolean isSuspend() {
        return getReflected().isSuspend();
    }

    public String toString() {
        KCallable kCallableCompute = compute();
        if (kCallableCompute != this) {
            return kCallableCompute.toString();
        }
        if ("<init>".equals(getName())) {
            return "constructor (Kotlin reflection is not available)";
        }
        return "function " + getName() + " (Kotlin reflection is not available)";
    }
}
