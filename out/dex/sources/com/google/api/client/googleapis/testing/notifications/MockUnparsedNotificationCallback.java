package com.google.api.client.googleapis.testing.notifications;

import com.google.api.client.googleapis.notifications.StoredChannel;
import com.google.api.client.googleapis.notifications.UnparsedNotification;
import com.google.api.client.googleapis.notifications.UnparsedNotificationCallback;
import com.google.api.client.util.Beta;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class MockUnparsedNotificationCallback implements UnparsedNotificationCallback {
    private static final long serialVersionUID = 0;
    private boolean wasCalled;

    @Override // com.google.api.client.googleapis.notifications.UnparsedNotificationCallback
    public void onNotification(StoredChannel storedChannel, UnparsedNotification unparsedNotification) throws IOException {
        this.wasCalled = true;
    }

    public boolean wasCalled() {
        return this.wasCalled;
    }
}
