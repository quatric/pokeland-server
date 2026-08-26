package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierDefault;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@TypeQualifierDefault({ElementType.PARAMETER})
@Nullable
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ParametersAreNullableByDefault {
}
