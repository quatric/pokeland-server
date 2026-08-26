package kotlin.jvm.internal;

import com.android.billingclient.BuildConfig;
import kotlin.SinceKotlin;
import kotlin.reflect.KMutableProperty;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class MutablePropertyReference extends PropertyReference implements KMutableProperty {
    public MutablePropertyReference() {
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    public MutablePropertyReference(Object obj) {
        super(obj);
    }
}
