package kotlin.system;

import android.support.v4.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;

/* JADX INFO: compiled from: Process.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b¨\u0006\u0004"}, m1984d2 = {"exitProcess", "", NotificationCompat.CATEGORY_STATUS, "", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "ProcessKt")
public final class ProcessKt {
    @InlineOnly
    private static final Void exitProcess(int i) {
        System.exit(i);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }
}
