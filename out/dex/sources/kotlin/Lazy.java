package kotlin;

/* JADX INFO: compiled from: Lazy.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\b\u0010\u0006\u001a\u00020\u0007H&R\u0012\u0010\u0003\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"}, m1984d2 = {"Lkotlin/Lazy;", "T", "", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public interface Lazy<T> {
    T getValue();

    boolean isInitialized();
}
