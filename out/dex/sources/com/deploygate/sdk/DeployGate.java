package com.deploygate.sdk;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.deploygate.service.DeployGateEvent;
import com.deploygate.service.IDeployGateSdkService;
import com.deploygate.service.IDeployGateSdkServiceCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class DeployGate {
    private static final String ACTION_DEPLOYGATE_STARTED = "com.deploygate.action.ServiceStarted";
    private static final String[] DEPLOYGATE_FINGERPRINTS = {"2f97f647645cb762bf5fc1445599a954e6ad76e7", "c1f285f69cc02a397135ed182aa79af53d5d20a1", "234eff4a1600a7aa78bf68adfbb15786e886ae1a"};
    private static final String DEPLOYGATE_PACKAGE = "com.deploygate";
    private static final int SDK_VERSION = 2;
    private static final String TAG = "DeployGate";
    private static DeployGate sInstance;
    private boolean mAppIsAuthorized;
    private boolean mAppIsManaged;
    private boolean mAppIsStopRequested;
    private boolean mAppUpdateAvailable;
    private int mAppUpdateRevision;
    private int mAppUpdateVersionCode;
    private String mAppUpdateVersionName;
    private final Context mApplicationContext;
    private String mAuthor;
    private String mExpectedAuthor;
    private CountDownLatch mInitializedLatch;
    private boolean mIsDeployGateAvailable;
    private Thread mLogcatThread;
    private LogCatTranportWorker mLogcatWorker;
    private String mLoginUsername;
    private IDeployGateSdkService mRemoteService;
    private final IDeployGateSdkServiceCallback mRemoteCallback = new IDeployGateSdkServiceCallback.Stub() { // from class: com.deploygate.sdk.DeployGate.1
        private void onEnableLogcat(boolean z) {
            if (DeployGate.this.mRemoteService == null) {
                return;
            }
            if (!z) {
                if (DeployGate.this.mLogcatThread == null || !DeployGate.this.mLogcatThread.isAlive()) {
                    return;
                }
                DeployGate.this.mLogcatWorker.stop();
                DeployGate.this.mLogcatThread.interrupt();
                return;
            }
            if (DeployGate.this.mLogcatThread == null || !DeployGate.this.mLogcatThread.isAlive()) {
                DeployGate deployGate = DeployGate.this;
                deployGate.mLogcatWorker = new LogCatTranportWorker(deployGate.mApplicationContext.getPackageName(), DeployGate.this.mRemoteService, false);
                DeployGate deployGate2 = DeployGate.this;
                deployGate2.mLogcatThread = new Thread(deployGate2.mLogcatWorker);
                DeployGate.this.mLogcatThread.start();
            }
        }

        private void onInitialized(final boolean z, final boolean z2, final String str, final boolean z3, String str2) throws RemoteException {
            Log.v(DeployGate.TAG, "DeployGate service initialized");
            DeployGate.this.mAppIsManaged = z;
            DeployGate.this.mAppIsAuthorized = z2;
            DeployGate.this.mAppIsStopRequested = z3;
            DeployGate.this.mLoginUsername = str;
            DeployGate.this.mAuthor = str2;
            DeployGate.this.mHandler.post(new Runnable() { // from class: com.deploygate.sdk.DeployGate.1.1
                @Override // java.lang.Runnable
                public void run() {
                    for (DeployGateCallback deployGateCallback : DeployGate.this.mCallbacks) {
                        deployGateCallback.onInitialized(true);
                        deployGateCallback.onStatusChanged(z, z2, str, z3);
                    }
                }
            });
            DeployGate.this.mIsDeployGateAvailable = true;
            DeployGate.this.mInitializedLatch.countDown();
        }

        private void onOneshotLogcat() {
            if (DeployGate.this.mLogcatThread == null || !DeployGate.this.mLogcatThread.isAlive()) {
                DeployGate deployGate = DeployGate.this;
                deployGate.mLogcatWorker = new LogCatTranportWorker(deployGate.mApplicationContext.getPackageName(), DeployGate.this.mRemoteService, true);
                DeployGate deployGate2 = DeployGate.this;
                deployGate2.mLogcatThread = new Thread(deployGate2.mLogcatWorker);
                DeployGate.this.mLogcatThread.start();
            }
        }

        private void onUpdateArrived(final int i, final String str, final int i2, String str2) throws RemoteException {
            DeployGate.this.mAppUpdateAvailable = true;
            DeployGate.this.mAppUpdateRevision = i;
            DeployGate.this.mAppUpdateVersionName = str;
            DeployGate.this.mAppUpdateVersionCode = i2;
            DeployGate.this.mHandler.post(new Runnable() { // from class: com.deploygate.sdk.DeployGate.1.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = DeployGate.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((DeployGateCallback) it.next()).onUpdateAvailable(i, str, i2);
                    }
                }
            });
        }

        @Override // com.deploygate.service.IDeployGateSdkServiceCallback
        public void onEvent(String str, Bundle bundle) throws RemoteException {
            if (DeployGateEvent.ACTION_INIT.equals(str)) {
                onInitialized(bundle.getBoolean(DeployGateEvent.EXTRA_IS_MANAGED, false), bundle.getBoolean(DeployGateEvent.EXTRA_IS_AUTHORIZED, false), bundle.getString(DeployGateEvent.EXTRA_LOGIN_USERNAME), bundle.getBoolean(DeployGateEvent.EXTRA_IS_STOP_REQUESTED, false), bundle.getString(DeployGateEvent.EXTRA_AUTHOR));
                return;
            }
            if (DeployGateEvent.ACTION_UPDATE_AVAILABLE.equals(str)) {
                onUpdateArrived(bundle.getInt(DeployGateEvent.EXTRA_SERIAL), bundle.getString(DeployGateEvent.EXTRA_VERSION_NAME), bundle.getInt(DeployGateEvent.EXTRA_VERSION_CODE), bundle.getString(DeployGateEvent.EXTRA_SERIAL_MESSAGE));
                return;
            }
            if (DeployGateEvent.ACTION_ONESHOT_LOGCAT.equals(str)) {
                onOneshotLogcat();
            } else if (DeployGateEvent.ACTION_ENABLE_LOGCAT.equals(str)) {
                onEnableLogcat(true);
            } else if (DeployGateEvent.ACTION_DISABLE_LOGCAT.equals(str)) {
                onEnableLogcat(false);
            }
        }
    };
    private final Handler mHandler = new Handler();
    private final HashSet<DeployGateCallback> mCallbacks = new HashSet<>();

    private static class LogCatTranportWorker implements Runnable {
        private boolean mIsOneShot;
        private final String mPackageName;
        private Process mProcess;
        private final IDeployGateSdkService mService;

        public LogCatTranportWorker(String str, IDeployGateSdkService iDeployGateSdkService, boolean z) {
            this.mPackageName = str;
            this.mService = iDeployGateSdkService;
            this.mIsOneShot = z;
        }

        private boolean send(ArrayList<String> arrayList) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(DeployGateEvent.EXTRA_LOG, arrayList);
            try {
                this.mService.sendEvent(this.mPackageName, DeployGateEvent.ACTION_SEND_LOGCAT, bundle);
                return true;
            } catch (RemoteException unused) {
                return false;
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // java.lang.Runnable
        public void run() throws Throwable {
            BufferedReader bufferedReader = null;
            this.mProcess = null;
            try {
                try {
                    try {
                        LinkedList linkedList = new LinkedList();
                        linkedList.add("logcat");
                        ArrayList<String> arrayList = new ArrayList<>();
                        if (this.mIsOneShot) {
                            linkedList.add("-d");
                            if (Build.VERSION.SDK_INT >= 8) {
                                linkedList.add("-t");
                                linkedList.add(String.valueOf(500));
                            }
                        }
                        linkedList.add("-v");
                        linkedList.add("threadtime");
                        linkedList.add("*:V");
                        this.mProcess = Runtime.getRuntime().exec((String[]) linkedList.toArray(new String[linkedList.size()]));
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(this.mProcess.getInputStream()));
                        try {
                            Log.v(DeployGate.TAG, "Start retrieving logcat");
                            while (true) {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    if (!arrayList.isEmpty()) {
                                        send(arrayList);
                                    }
                                    bufferedReader2.close();
                                    break;
                                }
                                arrayList.add(String.valueOf(line) + "\n");
                                if (this.mIsOneShot) {
                                    if (arrayList.size() > 500) {
                                        arrayList.remove(0);
                                    }
                                } else if (bufferedReader2.ready()) {
                                    continue;
                                } else {
                                    if (!send(arrayList)) {
                                        try {
                                            bufferedReader2.close();
                                        } catch (IOException unused) {
                                        }
                                        stop();
                                        return;
                                    }
                                    arrayList.clear();
                                }
                            }
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            Log.d(DeployGate.TAG, "Logcat stopped: " + e.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            stop();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException unused2) {
                                }
                            }
                            stop();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (IOException unused3) {
            }
            stop();
        }

        public void stop() {
            Process process = this.mProcess;
            if (process != null) {
                process.destroy();
            }
        }
    }

    private DeployGate(Context context, String str, DeployGateCallback deployGateCallback) {
        this.mApplicationContext = context;
        this.mExpectedAuthor = str;
        prepareBroadcastReceiver();
        if (deployGateCallback != null) {
            this.mCallbacks.add(deployGateCallback);
        }
        this.mInitializedLatch = new CountDownLatch(1);
        if (initService(true) || deployGateCallback == null) {
            return;
        }
        deployGateCallback.onInitialized(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindToService(final boolean z) {
        Intent intent = new Intent(IDeployGateSdkService.class.getName());
        intent.setPackage(DEPLOYGATE_PACKAGE);
        this.mApplicationContext.bindService(intent, new ServiceConnection() { // from class: com.deploygate.sdk.DeployGate.4
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.v(DeployGate.TAG, "DeployGate service connected");
                DeployGate.this.mRemoteService = IDeployGateSdkService.Stub.asInterface(iBinder);
                DeployGate.this.requestServiceInit(z);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.v(DeployGate.TAG, "DeployGate service disconneced");
                DeployGate.this.mRemoteService = null;
            }
        }, 1);
    }

    public static String getAuthorUsername() {
        if (sInstance == null) {
            return null;
        }
        waitForInitialized();
        return sInstance.mAuthor;
    }

    private String getDeployGatePackageSignature() {
        try {
            PackageInfo packageInfo = this.mApplicationContext.getPackageManager().getPackageInfo(DEPLOYGATE_PACKAGE, 64);
            if (packageInfo != null && packageInfo.signatures.length != 0) {
                try {
                    byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(packageInfo.signatures[0].toByteArray());
                    StringBuilder sb = new StringBuilder(40);
                    for (byte b : bArrDigest) {
                        sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
                    }
                    return sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    Log.e(TAG, "SHA1 is not supported on this platform?", e);
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return null;
    }

    static DeployGate getInstance() {
        return sInstance;
    }

    public static String getLoginUsername() {
        if (sInstance == null) {
            return null;
        }
        waitForInitialized();
        return sInstance.mLoginUsername;
    }

    private boolean initService(boolean z) {
        if (isDeployGateAvailable()) {
            Log.v(TAG, "DeployGate installation detected. Initializing.");
            bindToService(z);
            return true;
        }
        Log.v(TAG, "DeployGate is not available on this device.");
        this.mInitializedLatch.countDown();
        this.mIsDeployGateAvailable = false;
        callbackDeployGateUnavailable();
        return false;
    }

    public static void install(Application application) {
        install(application, (String) null);
    }

    public static void install(Application application, DeployGateCallback deployGateCallback) {
        install(application, (String) null, deployGateCallback);
    }

    public static void install(Application application, DeployGateCallback deployGateCallback, boolean z) {
        install(application, null, deployGateCallback, z);
    }

    public static void install(Application application, String str) {
        install(application, str, (DeployGateCallback) null);
    }

    public static void install(Application application, String str, DeployGateCallback deployGateCallback) {
        install(application, deployGateCallback, false);
    }

    public static void install(Application application, String str, DeployGateCallback deployGateCallback, boolean z) {
        if (sInstance != null) {
            throw new IllegalStateException("install already called");
        }
        if (z || isDebuggable(application.getApplicationContext())) {
            Thread.setDefaultUncaughtExceptionHandler(new DeployGateUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()));
            sInstance = new DeployGate(application.getApplicationContext(), str, deployGateCallback);
        }
    }

    public static boolean isAuthorized() {
        if (sInstance == null) {
            return false;
        }
        waitForInitialized();
        return sInstance.mAppIsAuthorized;
    }

    private static boolean isDebuggable(Context context) {
        try {
            return (context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) == 2;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeployGateAvailable() {
        String deployGatePackageSignature = getDeployGatePackageSignature();
        if (deployGatePackageSignature == null) {
            return false;
        }
        for (String str : DEPLOYGATE_FINGERPRINTS) {
            if (str.equals(deployGatePackageSignature)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDeployGateAvaliable() {
        if (sInstance == null) {
            return false;
        }
        waitForInitialized();
        return sInstance.mIsDeployGateAvailable;
    }

    public static boolean isInitialized() {
        DeployGate deployGate = sInstance;
        return deployGate != null && deployGate.mInitializedLatch.getCount() == 0;
    }

    public static boolean isManaged() {
        if (sInstance == null) {
            return false;
        }
        waitForInitialized();
        return sInstance.mAppIsManaged;
    }

    private static boolean isStopRequested() {
        if (sInstance == null) {
            return false;
        }
        waitForInitialized();
        return sInstance.mAppIsStopRequested;
    }

    public static void logDebug(String str) {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.sendLog("debug", str);
        }
    }

    public static void logError(String str) {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.sendLog("error", str);
        }
    }

    public static void logInfo(String str) {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.sendLog("info", str);
        }
    }

    public static void logVerbose(String str) {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.sendLog("verbose", str);
        }
    }

    public static void logWarn(String str) {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.sendLog("warn", str);
        }
    }

    private void prepareBroadcastReceiver() {
        this.mApplicationContext.registerReceiver(new BroadcastReceiver() { // from class: com.deploygate.sdk.DeployGate.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent != null && DeployGate.this.isDeployGateAvailable()) {
                    DeployGate.this.bindToService(false);
                }
            }
        }, new IntentFilter(ACTION_DEPLOYGATE_STARTED));
    }

    public static void refresh() {
        DeployGate deployGate = sInstance;
        if (deployGate != null) {
            deployGate.refreshInternal();
        }
    }

    private void refreshInternal() {
        if (this.mInitializedLatch.getCount() == 0) {
            this.mInitializedLatch = new CountDownLatch(1);
            if (this.mRemoteService == null) {
                initService(false);
            } else {
                requestServiceInit(false);
            }
        }
    }

    public static void registerCallback(DeployGateCallback deployGateCallback, boolean z) {
        DeployGate deployGate = sInstance;
        if (deployGate == null || deployGateCallback == null) {
            return;
        }
        deployGate.registerCallbackInternal(deployGateCallback, z);
    }

    private void registerCallbackInternal(DeployGateCallback deployGateCallback, boolean z) {
        this.mCallbacks.add(deployGateCallback);
        if (z) {
            refresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestServiceInit(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DeployGateEvent.EXTRA_IS_BOOT, z);
        bundle.putBoolean(DeployGateEvent.EXTRA_CAN_LOGCAT, canLogCat());
        bundle.putString(DeployGateEvent.EXTRA_EXPECTED_AUTHOR, this.mExpectedAuthor);
        bundle.putInt(DeployGateEvent.EXTRA_SDK_VERSION, 2);
        try {
            this.mRemoteService.init(this.mRemoteCallback, this.mApplicationContext.getPackageName(), bundle);
        } catch (RemoteException unused) {
            Log.w(TAG, "DeployGate service failed to be initialized.");
        }
    }

    public static void unregisterCallback(DeployGateCallback deployGateCallback) {
        DeployGate deployGate = sInstance;
        if (deployGate == null || deployGateCallback == null) {
            return;
        }
        deployGate.mCallbacks.remove(deployGateCallback);
    }

    private static void waitForInitialized() {
        try {
            sInstance.mInitializedLatch.await();
        } catch (InterruptedException unused) {
            Log.w(TAG, "Interrupted while waiting initialization");
        }
    }

    void callbackDeployGateUnavailable() {
        this.mHandler.post(new Runnable() { // from class: com.deploygate.sdk.DeployGate.2
            @Override // java.lang.Runnable
            public void run() {
                for (DeployGateCallback deployGateCallback : DeployGate.this.mCallbacks) {
                    deployGateCallback.onInitialized(false);
                    deployGateCallback.onStatusChanged(false, false, null, false);
                }
            }
        });
    }

    protected boolean canLogCat() {
        return Build.VERSION.SDK_INT >= 16 || this.mApplicationContext.getPackageManager().checkPermission("android.permission.READ_LOGS", this.mApplicationContext.getPackageName()) == 0;
    }

    void sendCrashReport(Throwable th) {
        if (this.mRemoteService == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeployGateEvent.EXTRA_EXCEPTION, th);
        try {
            this.mRemoteService.sendEvent(this.mApplicationContext.getPackageName(), DeployGateEvent.ACTION_SEND_CRASH_REPORT, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "failed to send crash report: " + e.getMessage());
        }
    }

    void sendLog(String str, String str2) {
        if (this.mRemoteService == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeployGateEvent.EXTRA_LOG, str2);
        bundle.putSerializable(DeployGateEvent.EXTRA_LOG_TYPE, str);
        try {
            this.mRemoteService.sendEvent(this.mApplicationContext.getPackageName(), DeployGateEvent.ACTION_SEND_CUSTOM_LOG, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "failed to send custom log: " + e.getMessage());
        }
    }
}
