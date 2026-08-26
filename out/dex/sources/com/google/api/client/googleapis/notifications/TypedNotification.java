package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Beta;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class TypedNotification<T> extends AbstractNotification {
    private T content;

    public TypedNotification(long j, String str, String str2, String str3, String str4) {
        super(j, str, str2, str3, str4);
    }

    public TypedNotification(UnparsedNotification unparsedNotification) {
        super(unparsedNotification);
    }

    public final T getContent() {
        return this.content;
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setChanged(String str) {
        return (TypedNotification) super.setChanged(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setChannelExpiration(String str) {
        return (TypedNotification) super.setChannelExpiration(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setChannelId(String str) {
        return (TypedNotification) super.setChannelId(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setChannelToken(String str) {
        return (TypedNotification) super.setChannelToken(str);
    }

    public TypedNotification<T> setContent(T t) {
        this.content = t;
        return this;
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setMessageNumber(long j) {
        return (TypedNotification) super.setMessageNumber(j);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setResourceId(String str) {
        return (TypedNotification) super.setResourceId(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setResourceState(String str) {
        return (TypedNotification) super.setResourceState(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public TypedNotification<T> setResourceUri(String str) {
        return (TypedNotification) super.setResourceUri(str);
    }

    @Override // com.google.api.client.googleapis.notifications.AbstractNotification
    public String toString() {
        return super.toStringHelper().add(FirebaseAnalytics.Param.CONTENT, this.content).toString();
    }
}
