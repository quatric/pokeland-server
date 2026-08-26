package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.experimental.ExperimentalTypeInference;

/* JADX INFO: compiled from: Inference.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@SinceKotlin(version = "1.3")
@ExperimentalTypeInference
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
@Retention(RetentionPolicy.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, m1984d2 = {"Lkotlin/BuilderInference;", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public @interface BuilderInference {
}
