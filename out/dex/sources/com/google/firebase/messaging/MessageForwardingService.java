package com.google.firebase.messaging;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.messaging.cpp.DebugLogging;
import com.google.firebase.messaging.cpp.MessageWriter;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class MessageForwardingService extends IntentService {
    public static final String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    private static final String TAG = "FIREBASE_MSG_FWDR";

    public MessageForwardingService() {
        super(TAG);
    }

    static void handleIntent(Context context, Intent intent, MessageWriter messageWriter) {
        String action;
        if (intent == null) {
            action = "null intent";
        } else {
            action = intent.getAction() == null ? "(null)" : intent.getAction();
        }
        String strValueOf = String.valueOf(action);
        DebugLogging.log(TAG, strValueOf.length() != 0 ? "onHandleIntent ".concat(strValueOf) : new String("onHandleIntent "));
        if (intent == null || intent.getAction() == null || !intent.getAction().equals(ACTION_REMOTE_INTENT)) {
            return;
        }
        Bundle extras = intent.getExtras();
        String strValueOf2 = String.valueOf(extras != null ? extras.toString() : "(null)");
        DebugLogging.log(TAG, strValueOf2.length() != 0 ? "extras: ".concat(strValueOf2) : new String("extras: "));
        if (extras != null) {
            RemoteMessage remoteMessage = new RemoteMessage(extras);
            String strValueOf3 = String.valueOf(remoteMessage.toString());
            DebugLogging.log(TAG, strValueOf3.length() != 0 ? "message: ".concat(strValueOf3) : new String("message: "));
            if (remoteMessage.getFrom() == null || remoteMessage.getMessageId() == null) {
                return;
            }
            messageWriter.writeMessage(context, remoteMessage, true, intent.getData());
        }
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        handleIntent(this, intent, MessageWriter.defaultInstance());
    }
}
