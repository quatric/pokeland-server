package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public final class ListenerHolder<L> {
    private final zaa zaji;
    private volatile L zajj;
    private final ListenerKey<L> zajk;

    @KeepForSdk
    public static final class ListenerKey<L> {
        private final L zajj;
        private final String zajm;

        @KeepForSdk
        ListenerKey(L l, String str) {
            this.zajj = l;
            this.zajm = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) obj;
            return this.zajj == listenerKey.zajj && this.zajm.equals(listenerKey.zajm);
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zajj) * 31) + this.zajm.hashCode();
        }
    }

    @KeepForSdk
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(L l);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    private final class zaa extends com.google.android.gms.internal.base.zal {
        public zaa(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Preconditions.checkArgument(message.what == 1);
            ListenerHolder.this.notifyListenerInternal((Notifier) message.obj);
        }
    }

    @KeepForSdk
    ListenerHolder(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zaji = new zaa(looper);
        this.zajj = (L) Preconditions.checkNotNull(l, "Listener must not be null");
        this.zajk = new ListenerKey<>(l, Preconditions.checkNotEmpty(str));
    }

    @KeepForSdk
    public final void clear() {
        this.zajj = null;
    }

    @NonNull
    @KeepForSdk
    public final ListenerKey<L> getListenerKey() {
        return this.zajk;
    }

    @KeepForSdk
    public final boolean hasListener() {
        return this.zajj != null;
    }

    @KeepForSdk
    public final void notifyListener(Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zaji.sendMessage(this.zaji.obtainMessage(1, notifier));
    }

    @KeepForSdk
    final void notifyListenerInternal(Notifier<? super L> notifier) {
        L l = this.zajj;
        if (l == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(l);
        } catch (RuntimeException e) {
            notifier.onNotifyListenerFailed();
            throw e;
        }
    }
}
