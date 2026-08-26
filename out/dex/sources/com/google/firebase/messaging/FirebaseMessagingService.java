package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzac;
import com.google.firebase.iid.zzaw;
import com.google.firebase.messaging.cpp.ListenerService;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FirebaseMessagingService extends com.google.firebase.iid.zzc {
    private static final Queue<String> zzec = new ArrayDeque(10);

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onMessageSent(String str) {
    }

    @WorkerThread
    public void onNewToken(String str) {
    }

    @WorkerThread
    public void onSendError(String str, Exception exc) {
    }

    @Override // com.google.firebase.iid.zzc
    protected final Intent zzb(Intent intent) {
        return zzaw.zzak().zzal();
    }

    @Override // com.google.firebase.iid.zzc
    public final boolean zzc(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException unused) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (!MessagingAnalytics.shouldUploadMetrics(intent)) {
            return true;
        }
        MessagingAnalytics.logNotificationOpen(intent);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:63:0x0100  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.firebase.iid.zzc
    public final void zzd(Intent intent) {
        Task<Void> taskZza;
        boolean z;
        String action = intent.getAction();
        if (!MessageForwardingService.ACTION_REMOTE_INTENT.equals(action) && !"com.google.firebase.messaging.RECEIVE_DIRECT_BOOT".equals(action)) {
            if ("com.google.firebase.messaging.NOTIFICATION_DISMISS".equals(action)) {
                if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                    MessagingAnalytics.logNotificationDismiss(intent);
                    return;
                }
                return;
            } else if ("com.google.firebase.messaging.NEW_TOKEN".equals(action)) {
                onNewToken(intent.getStringExtra("token"));
                return;
            } else {
                String strValueOf = String.valueOf(intent.getAction());
                Log.d("FirebaseMessaging", strValueOf.length() != 0 ? "Unknown intent action: ".concat(strValueOf) : new String("Unknown intent action: "));
                return;
            }
        }
        String stringExtra = intent.getStringExtra("google.message_id");
        if (TextUtils.isEmpty(stringExtra)) {
            taskZza = Tasks.forResult(null);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("google.message_id", stringExtra);
            taskZza = zzac.zzc(this).zza(2, bundle);
        }
        byte b = 0;
        if (TextUtils.isEmpty(stringExtra)) {
            z = false;
        } else if (zzec.contains(stringExtra)) {
            if (Log.isLoggable("FirebaseMessaging", 3)) {
                String strValueOf2 = String.valueOf(stringExtra);
                Log.d("FirebaseMessaging", strValueOf2.length() != 0 ? "Received duplicate message: ".concat(strValueOf2) : new String("Received duplicate message: "));
            }
            z = true;
        } else {
            if (zzec.size() >= 10) {
                zzec.remove();
            }
            zzec.add(stringExtra);
            z = false;
        }
        if (!z) {
            String stringExtra2 = intent.getStringExtra("message_type");
            if (stringExtra2 == null) {
                stringExtra2 = "gcm";
            }
            switch (stringExtra2.hashCode()) {
                case -2062414158:
                    if (!stringExtra2.equals(ListenerService.MESSAGE_TYPE_DELETED)) {
                        b = -1;
                    } else {
                        b = 1;
                    }
                    break;
                case 102161:
                    if (!stringExtra2.equals("gcm")) {
                        b = -1;
                    }
                    break;
                case 814694033:
                    if (!stringExtra2.equals(ListenerService.MESSAGE_TYPE_SEND_ERROR)) {
                        b = -1;
                    } else {
                        b = 3;
                    }
                    break;
                case 814800675:
                    if (!stringExtra2.equals(ListenerService.MESSAGE_TYPE_SEND_EVENT)) {
                        b = -1;
                    } else {
                        b = 2;
                    }
                    break;
                default:
                    b = -1;
                    break;
            }
            if (b == 0) {
                if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                    MessagingAnalytics.logNotificationReceived(intent);
                }
                Bundle extras = intent.getExtras();
                if (extras == null) {
                    extras = new Bundle();
                }
                extras.remove("android.support.content.wakelockid");
                if (zzb.zzh(extras)) {
                    ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
                    try {
                        if (new zzc(this, extras, executorServiceNewSingleThreadExecutor).zzas()) {
                            executorServiceNewSingleThreadExecutor.shutdown();
                        } else {
                            executorServiceNewSingleThreadExecutor.shutdown();
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationForeground(intent);
                            }
                            onMessageReceived(new RemoteMessage(extras));
                        }
                    } catch (Throwable th) {
                        executorServiceNewSingleThreadExecutor.shutdown();
                        throw th;
                    }
                } else {
                    onMessageReceived(new RemoteMessage(extras));
                }
            } else if (b == 1) {
                onDeletedMessages();
            } else if (b == 2) {
                onMessageSent(intent.getStringExtra("google.message_id"));
            } else if (b != 3) {
                String strValueOf3 = String.valueOf(stringExtra2);
                Log.w("FirebaseMessaging", strValueOf3.length() != 0 ? "Received message with unknown type: ".concat(strValueOf3) : new String("Received message with unknown type: "));
            } else {
                String stringExtra3 = intent.getStringExtra("google.message_id");
                if (stringExtra3 == null) {
                    stringExtra3 = intent.getStringExtra("message_id");
                }
                onSendError(stringExtra3, new SendException(intent.getStringExtra("error")));
            }
        }
        try {
            Tasks.await(taskZza, 1L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            String strValueOf4 = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf4).length() + 20);
            sb.append("Message ack failed: ");
            sb.append(strValueOf4);
            Log.w("FirebaseMessaging", sb.toString());
        }
    }
}
