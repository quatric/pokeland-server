package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzau {
    private static int zzck;
    private static PendingIntent zzcx;
    private final Context zzag;
    private final zzan zzav;

    @GuardedBy("responseCallbacks")
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzcy = new SimpleArrayMap<>();
    private Messenger zzcz = new Messenger(new zzat(this, Looper.getMainLooper()));
    private Messenger zzda;
    private zzm zzdb;

    public zzau(Context context, zzan zzanVar) {
        this.zzag = context;
        this.zzav = zzanVar;
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzcy) {
            TaskCompletionSource<Bundle> taskCompletionSourceRemove = this.zzcy.remove(str);
            if (taskCompletionSourceRemove != null) {
                taskCompletionSourceRemove.setResult(bundle);
            } else {
                String strValueOf = String.valueOf(str);
                Log.w("FirebaseInstanceId", strValueOf.length() != 0 ? "Missing callback for ".concat(strValueOf) : new String("Missing callback for "));
            }
        }
    }

    private static synchronized String zzah() {
        int i;
        i = zzck;
        zzck = i + 1;
        return Integer.toString(i);
    }

    private static synchronized void zzb(Context context, Intent intent) {
        if (zzcx == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            zzcx = PendingIntent.getBroadcast(context, 0, intent2, 0);
        }
        intent.putExtra("app", zzcx);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
            return;
        }
        Intent intent = (Intent) message.obj;
        intent.setExtrasClassLoader(new zzm.zza());
        if (intent.hasExtra("google.messenger")) {
            Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
            if (parcelableExtra instanceof zzm) {
                this.zzdb = (zzm) parcelableExtra;
            }
            if (parcelableExtra instanceof Messenger) {
                this.zzda = (Messenger) parcelableExtra;
            }
        }
        Intent intent2 = (Intent) message.obj;
        String action = intent2.getAction();
        if (!"com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(action);
                Log.d("FirebaseInstanceId", strValueOf.length() != 0 ? "Unexpected response action: ".concat(strValueOf) : new String("Unexpected response action: "));
                return;
            }
            return;
        }
        String stringExtra = intent2.getStringExtra("registration_id");
        if (stringExtra == null) {
            stringExtra = intent2.getStringExtra("unregistered");
        }
        if (stringExtra != null) {
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
            if (!matcher.matches()) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String strValueOf2 = String.valueOf(stringExtra);
                    Log.d("FirebaseInstanceId", strValueOf2.length() != 0 ? "Unexpected response string: ".concat(strValueOf2) : new String("Unexpected response string: "));
                    return;
                }
                return;
            }
            String strGroup = matcher.group(1);
            String strGroup2 = matcher.group(2);
            Bundle extras = intent2.getExtras();
            extras.putString("registration_id", strGroup2);
            zza(strGroup, extras);
            return;
        }
        String stringExtra2 = intent2.getStringExtra("error");
        if (stringExtra2 == null) {
            String strValueOf3 = String.valueOf(intent2.getExtras());
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf3).length() + 49);
            sb.append("Unexpected response, no error or registration id ");
            sb.append(strValueOf3);
            Log.w("FirebaseInstanceId", sb.toString());
            return;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String strValueOf4 = String.valueOf(stringExtra2);
            Log.d("FirebaseInstanceId", strValueOf4.length() != 0 ? "Received InstanceID error ".concat(strValueOf4) : new String("Received InstanceID error "));
        }
        if (!stringExtra2.startsWith("|")) {
            synchronized (this.zzcy) {
                for (int i = 0; i < this.zzcy.size(); i++) {
                    zza(this.zzcy.keyAt(i), intent2.getExtras());
                }
            }
            return;
        }
        String[] strArrSplit = stringExtra2.split("\\|");
        if (strArrSplit.length <= 2 || !"ID".equals(strArrSplit[1])) {
            String strValueOf5 = String.valueOf(stringExtra2);
            Log.w("FirebaseInstanceId", strValueOf5.length() != 0 ? "Unexpected structured response ".concat(strValueOf5) : new String("Unexpected structured response "));
            return;
        }
        String str = strArrSplit[2];
        String strSubstring = strArrSplit[3];
        if (strSubstring.startsWith(":")) {
            strSubstring = strSubstring.substring(1);
        }
        zza(str, intent2.putExtra("error", strSubstring).getExtras());
    }

    private final Bundle zzd(Bundle bundle) throws IOException {
        Bundle bundleZze = zze(bundle);
        if (bundleZze == null || !bundleZze.containsKey("google.messenger")) {
            return bundleZze;
        }
        Bundle bundleZze2 = zze(bundle);
        if (bundleZze2 == null || !bundleZze2.containsKey("google.messenger")) {
            return bundleZze2;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:31:0x00d9  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.tasks.TaskCompletionSource, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v21, types: [android.os.Bundle] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x00d3 -> B:65:0x00de). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00d9 -> B:65:0x00de). Please report as a decompilation issue!!! */
    private final Bundle zze(Bundle bundle) throws IOException {
        TaskCompletionSource taskCompletionSource;
        ?? r1;
        String strZzah = zzah();
        ?? taskCompletionSource2 = new TaskCompletionSource();
        synchronized (this.zzcy) {
            this.zzcy.put(strZzah, taskCompletionSource2);
        }
        if (this.zzav.zzac() == 0) {
            throw new IOException("MISSING_INSTANCEID_SERVICE");
        }
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        if (this.zzav.zzac() == 2) {
            intent.setAction("com.google.iid.TOKEN_REQUEST");
        } else {
            intent.setAction("com.google.android.c2dm.intent.REGISTER");
        }
        intent.putExtras(bundle);
        zzb(this.zzag, intent);
        StringBuilder sb = new StringBuilder(String.valueOf(strZzah).length() + 5);
        sb.append("|ID|");
        sb.append(strZzah);
        sb.append("|");
        intent.putExtra("kid", sb.toString());
        int i = 3;
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String strValueOf = String.valueOf(intent.getExtras());
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 8);
            sb2.append("Sending ");
            sb2.append(strValueOf);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
        intent.putExtra("google.messenger", this.zzcz);
        if (this.zzda == null && this.zzdb == null) {
            r1 = taskCompletionSource2;
            if (this.zzav.zzac() == 2) {
                this.zzag.sendBroadcast(intent);
                taskCompletionSource = r1;
            } else {
                this.zzag.startService(intent);
                taskCompletionSource = r1;
            }
        } else {
            r1 = taskCompletionSource2;
            Message messageObtain = Message.obtain();
            messageObtain.obj = intent;
            try {
                if (this.zzda != null) {
                    this.zzda.send(messageObtain);
                    taskCompletionSource = taskCompletionSource2;
                } else {
                    this.zzdb.send(messageObtain);
                    taskCompletionSource = taskCompletionSource2;
                }
            } catch (RemoteException unused) {
                r1 = taskCompletionSource2;
                if (Log.isLoggable("FirebaseInstanceId", i)) {
                    Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                    r1 = taskCompletionSource2;
                }
                r1 = taskCompletionSource2;
                if (this.zzav.zzac() == 2) {
                    this.zzag.sendBroadcast(intent);
                    taskCompletionSource = r1;
                } else {
                    this.zzag.startService(intent);
                    taskCompletionSource = r1;
                }
            }
        }
        try {
            try {
                Task task = taskCompletionSource.getTask();
                taskCompletionSource2 = 30000;
                i = (Bundle) Tasks.await(task, 30000L, TimeUnit.MILLISECONDS);
                synchronized (this.zzcy) {
                    this.zzcy.remove(strZzah);
                }
                return i;
            } catch (Throwable th) {
                synchronized (this.zzcy) {
                    this.zzcy.remove(strZzah);
                    throw th;
                }
            }
        } catch (InterruptedException | TimeoutException unused2) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException("TIMEOUT");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    final Bundle zzc(Bundle bundle) throws IOException {
        if (this.zzav.zzaf() < 12000000) {
            return zzd(bundle);
        }
        try {
            return (Bundle) Tasks.await(zzac.zzc(this.zzag).zzb(1, bundle));
        } catch (InterruptedException | ExecutionException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 22);
                sb.append("Error making request: ");
                sb.append(strValueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            if ((e.getCause() instanceof zzam) && ((zzam) e.getCause()).getErrorCode() == 4) {
                return zzd(bundle);
            }
            return null;
        }
    }
}
