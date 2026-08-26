package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class RegistrationMethods<A extends Api.AnyClient, L> {
    public final RegisterListenerMethod<A, L> zajy;
    public final UnregisterListenerMethod<A, L> zajz;

    @KeepForSdk
    public static class Builder<A extends Api.AnyClient, L> {
        private boolean zajv;
        private RemoteCall<A, TaskCompletionSource<Void>> zaka;
        private RemoteCall<A, TaskCompletionSource<Boolean>> zakb;
        private ListenerHolder<L> zakc;
        private Feature[] zakd;

        private Builder() {
            this.zajv = true;
        }

        @KeepForSdk
        public RegistrationMethods<A, L> build() {
            Preconditions.checkArgument(this.zaka != null, "Must set register function");
            Preconditions.checkArgument(this.zakb != null, "Must set unregister function");
            Preconditions.checkArgument(this.zakc != null, "Must set holder");
            return new RegistrationMethods<>(new zaca(this, this.zakc, this.zakd, this.zajv), new zacb(this, this.zakc.getListenerKey()));
        }

        @KeepForSdk
        public Builder<A, L> register(RemoteCall<A, TaskCompletionSource<Void>> remoteCall) {
            this.zaka = remoteCall;
            return this;
        }

        @KeepForSdk
        @Deprecated
        public Builder<A, L> register(final BiConsumer<A, TaskCompletionSource<Void>> biConsumer) {
            this.zaka = new RemoteCall(biConsumer) { // from class: com.google.android.gms.common.api.internal.zaby
                private final BiConsumer zake;

                {
                    this.zake = biConsumer;
                }

                @Override // com.google.android.gms.common.api.internal.RemoteCall
                public final void accept(Object obj, Object obj2) {
                    this.zake.accept((Api.AnyClient) obj, (TaskCompletionSource) obj2);
                }
            };
            return this;
        }

        @KeepForSdk
        public Builder<A, L> setAutoResolveMissingFeatures(boolean z) {
            this.zajv = z;
            return this;
        }

        @KeepForSdk
        public Builder<A, L> setFeatures(Feature[] featureArr) {
            this.zakd = featureArr;
            return this;
        }

        @KeepForSdk
        public Builder<A, L> unregister(RemoteCall<A, TaskCompletionSource<Boolean>> remoteCall) {
            this.zakb = remoteCall;
            return this;
        }

        @KeepForSdk
        @Deprecated
        public Builder<A, L> unregister(BiConsumer<A, TaskCompletionSource<Boolean>> biConsumer) {
            this.zaka = new RemoteCall(this) { // from class: com.google.android.gms.common.api.internal.zabz
                private final RegistrationMethods.Builder zakf;

                {
                    this.zakf = this;
                }

                @Override // com.google.android.gms.common.api.internal.RemoteCall
                public final void accept(Object obj, Object obj2) throws RemoteException {
                    this.zakf.zaa((Api.AnyClient) obj, (TaskCompletionSource) obj2);
                }
            };
            return this;
        }

        @KeepForSdk
        public Builder<A, L> withHolder(ListenerHolder<L> listenerHolder) {
            this.zakc = listenerHolder;
            return this;
        }

        final /* synthetic */ void zaa(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
            this.zaka.accept(anyClient, taskCompletionSource);
        }
    }

    private RegistrationMethods(RegisterListenerMethod<A, L> registerListenerMethod, UnregisterListenerMethod<A, L> unregisterListenerMethod) {
        this.zajy = registerListenerMethod;
        this.zajz = unregisterListenerMethod;
    }

    @KeepForSdk
    public static <A extends Api.AnyClient, L> Builder<A, L> builder() {
        return new Builder<>();
    }
}
