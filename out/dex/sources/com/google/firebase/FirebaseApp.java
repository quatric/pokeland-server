package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@PublicApi
public class FirebaseApp {

    @VisibleForTesting
    static final String DATA_COLLECTION_DEFAULT_ENABLED = "firebase_data_collection_default_enabled";
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private static final String FIREBASE_ANDROID = "fire-android";
    private static final String FIREBASE_APP_PREFS = "com.google.firebase.common.prefs:";
    private static final String FIREBASE_COMMON = "fire-core";
    private static final String LOG_TAG = "FirebaseApp";
    private final Context applicationContext;
    private final ComponentRuntime componentRuntime;
    private final String name;
    private final FirebaseOptions options;
    private final Publisher publisher;
    private final SharedPreferences sharedPreferences;
    private static final String AUTH_CLASSNAME = "com.google.firebase.auth.FirebaseAuth";
    private static final String IID_CLASSNAME = "com.google.firebase.iid.FirebaseInstanceId";
    private static final List<String> API_INITIALIZERS = Arrays.asList(AUTH_CLASSNAME, IID_CLASSNAME);
    private static final String CRASH_CLASSNAME = "com.google.firebase.crash.FirebaseCrash";
    private static final List<String> DEFAULT_APP_API_INITITALIZERS = Collections.singletonList(CRASH_CLASSNAME);
    private static final String MEASUREMENT_CLASSNAME = "com.google.android.gms.measurement.AppMeasurement";
    private static final List<String> DEFAULT_CONTEXT_API_INITITALIZERS = Arrays.asList(MEASUREMENT_CLASSNAME);
    private static final List<String> DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS = Arrays.asList(new String[0]);
    private static final Set<String> CORE_CLASSES = Collections.emptySet();
    private static final Object LOCK = new Object();
    private static final Executor UI_EXECUTOR = new UiExecutor();

    @GuardedBy("LOCK")
    static final Map<String, FirebaseApp> INSTANCES = new ArrayMap();
    private final AtomicBoolean automaticResourceManagementEnabled = new AtomicBoolean(false);
    private final AtomicBoolean deleted = new AtomicBoolean();
    private final List<BackgroundStateChangeListener> backgroundStateChangeListeners = new CopyOnWriteArrayList();
    private final List<FirebaseAppLifecycleListener> lifecycleListeners = new CopyOnWriteArrayList();
    private final AtomicBoolean dataCollectionDefaultEnabled = new AtomicBoolean(readAutoDataCollectionEnabled());

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    @KeepForSdk
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z);
    }

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    @TargetApi(14)
    private static class GlobalBackgroundStateListener implements BackgroundDetector.BackgroundStateChangeListener {
        private static AtomicReference<GlobalBackgroundStateListener> INSTANCE = new AtomicReference<>();

        private GlobalBackgroundStateListener() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void ensureBackgroundStateListenerRegistered(Context context) {
            if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
                Application application = (Application) context.getApplicationContext();
                if (INSTANCE.get() == null) {
                    GlobalBackgroundStateListener globalBackgroundStateListener = new GlobalBackgroundStateListener();
                    if (INSTANCE.compareAndSet(null, globalBackgroundStateListener)) {
                        BackgroundDetector.initialize(application);
                        BackgroundDetector.getInstance().addListener(globalBackgroundStateListener);
                    }
                }
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
        public void onBackgroundStateChanged(boolean z) {
            synchronized (FirebaseApp.LOCK) {
                for (FirebaseApp firebaseApp : new ArrayList(FirebaseApp.INSTANCES.values())) {
                    if (firebaseApp.automaticResourceManagementEnabled.get()) {
                        firebaseApp.notifyBackgroundStateChangeListeners(z);
                    }
                }
            }
        }
    }

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    private static class UiExecutor implements Executor {
        private static final Handler HANDLER = new Handler(Looper.getMainLooper());

        private UiExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            HANDLER.post(runnable);
        }
    }

    /* JADX INFO: compiled from: com.google.firebase:firebase-common@@17.0.0 */
    @TargetApi(MotionEventCompat.AXIS_DISTANCE)
    private static class UserUnlockReceiver extends BroadcastReceiver {
        private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference<>();
        private final Context applicationContext;

        public UserUnlockReceiver(Context context) {
            this.applicationContext = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void ensureReceiverRegistered(Context context) {
            if (INSTANCE.get() == null) {
                UserUnlockReceiver userUnlockReceiver = new UserUnlockReceiver(context);
                if (INSTANCE.compareAndSet(null, userUnlockReceiver)) {
                    context.registerReceiver(userUnlockReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.LOCK) {
                Iterator<FirebaseApp> it = FirebaseApp.INSTANCES.values().iterator();
                while (it.hasNext()) {
                    it.next().initializeAllApis();
                }
            }
            unregister();
        }

        public void unregister() {
            this.applicationContext.unregisterReceiver(this);
        }
    }

    protected FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.applicationContext = (Context) Preconditions.checkNotNull(context);
        this.name = Preconditions.checkNotEmpty(str);
        this.options = (FirebaseOptions) Preconditions.checkNotNull(firebaseOptions);
        this.sharedPreferences = context.getSharedPreferences(getSharedPrefsName(str), 0);
        this.componentRuntime = new ComponentRuntime(UI_EXECUTOR, ComponentDiscovery.forContext(context).discover(), Component.m607of(context, Context.class, new Class[0]), Component.m607of(this, FirebaseApp.class, new Class[0]), Component.m607of(firebaseOptions, FirebaseOptions.class, new Class[0]), LibraryVersionComponent.create(FIREBASE_ANDROID, ""), LibraryVersionComponent.create(FIREBASE_COMMON, BuildConfig.VERSION_NAME), DefaultUserAgentPublisher.component());
        this.publisher = (Publisher) this.componentRuntime.get(Publisher.class);
    }

    private void checkNotDeleted() {
        Preconditions.checkState(!this.deleted.get(), "FirebaseApp was deleted");
    }

    @VisibleForTesting
    public static void clearInstancesForTest() {
        synchronized (LOCK) {
            INSTANCES.clear();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static List<String> getAllAppNames() {
        ArrayList arrayList = new ArrayList();
        synchronized (LOCK) {
            Iterator<FirebaseApp> it = INSTANCES.values().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    @PublicApi
    public static List<FirebaseApp> getApps(Context context) {
        ArrayList arrayList;
        synchronized (LOCK) {
            arrayList = new ArrayList(INSTANCES.values());
        }
        return arrayList;
    }

    @NonNull
    @PublicApi
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return firebaseApp;
    }

    @NonNull
    @PublicApi
    public static FirebaseApp getInstance(@NonNull String str) {
        FirebaseApp firebaseApp;
        String str2;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(normalize(str));
            if (firebaseApp == null) {
                List<String> allAppNames = getAllAppNames();
                if (allAppNames.isEmpty()) {
                    str2 = "";
                } else {
                    str2 = "Available app names: " + TextUtils.join(", ", allAppNames);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", str, str2));
            }
        }
        return firebaseApp;
    }

    @KeepForSdk
    public static String getPersistenceKey(String str, FirebaseOptions firebaseOptions) {
        return Base64Utils.encodeUrlSafeNoPadding(str.getBytes(Charset.defaultCharset())) + "+" + Base64Utils.encodeUrlSafeNoPadding(firebaseOptions.getApplicationId().getBytes(Charset.defaultCharset()));
    }

    private static String getSharedPrefsName(String str) {
        return FIREBASE_APP_PREFS + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeAllApis() {
        boolean zIsDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.applicationContext);
        if (zIsDeviceProtectedStorage) {
            UserUnlockReceiver.ensureReceiverRegistered(this.applicationContext);
        } else {
            this.componentRuntime.initializeEagerComponents(isDefaultApp());
        }
        initializeApis(FirebaseApp.class, this, API_INITIALIZERS, zIsDeviceProtectedStorage);
        if (isDefaultApp()) {
            initializeApis(FirebaseApp.class, this, DEFAULT_APP_API_INITITALIZERS, zIsDeviceProtectedStorage);
            initializeApis(Context.class, this.applicationContext, DEFAULT_CONTEXT_API_INITITALIZERS, zIsDeviceProtectedStorage);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void initializeApis(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String str : iterable) {
            if (z) {
                try {
                    if (DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS.contains(str)) {
                    }
                } catch (ClassNotFoundException unused) {
                    if (CORE_CLASSES.contains(str)) {
                        throw new IllegalStateException(str + " is missing, but is required. Check if it has been removed by Proguard.");
                    }
                    Log.d(LOG_TAG, str + " is not linked. Skipping initialization.");
                } catch (IllegalAccessException e) {
                    Log.wtf(LOG_TAG, "Failed to initialize " + str, e);
                } catch (NoSuchMethodException unused2) {
                    throw new IllegalStateException(str + "#getInstance has been removed by Proguard. Add keep rule to prevent it.");
                } catch (InvocationTargetException e2) {
                    Log.wtf(LOG_TAG, "Firebase API initialization failure.", e2);
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", cls);
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, t);
            }
        }
    }

    @Nullable
    @PublicApi
    public static FirebaseApp initializeApp(@NonNull Context context) {
        synchronized (LOCK) {
            if (INSTANCES.containsKey(DEFAULT_APP_NAME)) {
                return getInstance();
            }
            FirebaseOptions firebaseOptionsFromResource = FirebaseOptions.fromResource(context);
            if (firebaseOptionsFromResource == null) {
                Log.d(LOG_TAG, "Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.");
                return null;
            }
            return initializeApp(context, firebaseOptionsFromResource);
        }
    }

    @NonNull
    @PublicApi
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    @NonNull
    @PublicApi
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions firebaseOptions, @NonNull String str) {
        FirebaseApp firebaseApp;
        GlobalBackgroundStateListener.ensureBackgroundStateListenerRegistered(context);
        String strNormalize = normalize(str);
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (LOCK) {
            Preconditions.checkState(!INSTANCES.containsKey(strNormalize), "FirebaseApp name " + strNormalize + " already exists!");
            Preconditions.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, strNormalize, firebaseOptions);
            INSTANCES.put(strNormalize, firebaseApp);
        }
        firebaseApp.initializeAllApis();
        return firebaseApp;
    }

    private static String normalize(@NonNull String str) {
        return str.trim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBackgroundStateChangeListeners(boolean z) {
        Log.d(LOG_TAG, "Notifying background state change listeners.");
        Iterator<BackgroundStateChangeListener> it = this.backgroundStateChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onBackgroundStateChanged(z);
        }
    }

    private void notifyOnAppDeleted() {
        Iterator<FirebaseAppLifecycleListener> it = this.lifecycleListeners.iterator();
        while (it.hasNext()) {
            it.next().onDeleted(this.name, this.options);
        }
    }

    private boolean readAutoDataCollectionEnabled() {
        ApplicationInfo applicationInfo;
        if (this.sharedPreferences.contains(DATA_COLLECTION_DEFAULT_ENABLED)) {
            return this.sharedPreferences.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED, true);
        }
        try {
            PackageManager packageManager = this.applicationContext.getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(this.applicationContext.getPackageName(), 128)) != null && applicationInfo.metaData != null && applicationInfo.metaData.containsKey(DATA_COLLECTION_DEFAULT_ENABLED)) {
                return applicationInfo.metaData.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return true;
    }

    @KeepForSdk
    public void addBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.get() && BackgroundDetector.getInstance().isInBackground()) {
            backgroundStateChangeListener.onBackgroundStateChanged(true);
        }
        this.backgroundStateChangeListeners.add(backgroundStateChangeListener);
    }

    @KeepForSdk
    public void addLifecycleEventListener(@NonNull FirebaseAppLifecycleListener firebaseAppLifecycleListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(firebaseAppLifecycleListener);
        this.lifecycleListeners.add(firebaseAppLifecycleListener);
    }

    @PublicApi
    public void delete() {
        if (this.deleted.compareAndSet(false, true)) {
            synchronized (LOCK) {
                INSTANCES.remove(this.name);
            }
            notifyOnAppDeleted();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof FirebaseApp) {
            return this.name.equals(((FirebaseApp) obj).getName());
        }
        return false;
    }

    @KeepForSdk
    public <T> T get(Class<T> cls) {
        checkNotDeleted();
        return (T) this.componentRuntime.get(cls);
    }

    @NonNull
    @PublicApi
    public Context getApplicationContext() {
        checkNotDeleted();
        return this.applicationContext;
    }

    @NonNull
    @PublicApi
    public String getName() {
        checkNotDeleted();
        return this.name;
    }

    @NonNull
    @PublicApi
    public FirebaseOptions getOptions() {
        checkNotDeleted();
        return this.options;
    }

    @KeepForSdk
    public String getPersistenceKey() {
        return Base64Utils.encodeUrlSafeNoPadding(getName().getBytes(Charset.defaultCharset())) + "+" + Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes(Charset.defaultCharset()));
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @KeepForSdk
    public boolean isDataCollectionDefaultEnabled() {
        checkNotDeleted();
        return this.dataCollectionDefaultEnabled.get();
    }

    @VisibleForTesting
    @KeepForSdk
    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    @KeepForSdk
    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        checkNotDeleted();
        this.backgroundStateChangeListeners.remove(backgroundStateChangeListener);
    }

    @KeepForSdk
    public void removeLifecycleEventListener(@NonNull FirebaseAppLifecycleListener firebaseAppLifecycleListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(firebaseAppLifecycleListener);
        this.lifecycleListeners.remove(firebaseAppLifecycleListener);
    }

    @PublicApi
    public void setAutomaticResourceManagementEnabled(boolean z) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.compareAndSet(!z, z)) {
            boolean zIsInBackground = BackgroundDetector.getInstance().isInBackground();
            if (z && zIsInBackground) {
                notifyBackgroundStateChangeListeners(true);
            } else {
                if (z || !zIsInBackground) {
                    return;
                }
                notifyBackgroundStateChangeListeners(false);
            }
        }
    }

    @KeepForSdk
    public void setDataCollectionDefaultEnabled(boolean z) {
        checkNotDeleted();
        if (this.dataCollectionDefaultEnabled.compareAndSet(!z, z)) {
            this.sharedPreferences.edit().putBoolean(DATA_COLLECTION_DEFAULT_ENABLED, z).commit();
            this.publisher.publish(new Event<>(DataCollectionDefaultChange.class, new DataCollectionDefaultChange(z)));
        }
    }

    public String toString() {
        return Objects.toStringHelper(this).add(AppMeasurementSdk.ConditionalUserProperty.NAME, this.name).add("options", this.options).toString();
    }
}
