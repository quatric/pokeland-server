package kotlin.jvm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationTarget;

/* JADX INFO: compiled from: JvmDefault.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Target({ElementType.METHOD})
@SinceKotlin(version = "1.2")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000ø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u0091(0\u0001¨\u0006\u0002"}, m1984d2 = {"Lkotlin/jvm/JvmDefault;", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
@Retention(RetentionPolicy.RUNTIME)
public @interface JvmDefault {
}
