package com.google.api.client.googleapis.notifications;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public abstract class TypedNotificationCallback<T> implements UnparsedNotificationCallback {
    private static final long serialVersionUID = 1;

    protected abstract Class<T> getDataClass() throws IOException;

    protected abstract ObjectParser getObjectParser() throws IOException;

    protected abstract void onNotification(StoredChannel storedChannel, TypedNotification<T> typedNotification) throws IOException;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.api.client.googleapis.notifications.UnparsedNotificationCallback
    public final void onNotification(StoredChannel storedChannel, UnparsedNotification unparsedNotification) throws IOException {
        TypedNotification typedNotification = new TypedNotification(unparsedNotification);
        String contentType = unparsedNotification.getContentType();
        if (contentType != null) {
            typedNotification.setContent(getObjectParser().parseAndClose(unparsedNotification.getContentStream(), new HttpMediaType(contentType).getCharsetParameter(), (Class) Preconditions.checkNotNull(getDataClass())));
        }
        onNotification(storedChannel, typedNotification);
    }
}
