package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public final class DynamiteModule {

    @GuardedBy("DynamiteModule.class")
    private static Boolean zzif = null;

    @GuardedBy("DynamiteModule.class")
    private static zzi zzig = null;

    @GuardedBy("DynamiteModule.class")
    private static zzk zzih = null;

    @GuardedBy("DynamiteModule.class")
    private static String zzii = null;

    @GuardedBy("DynamiteModule.class")
    private static int zzij = -1;
    private final Context zzin;
    private static final ThreadLocal<zza> zzik = new ThreadLocal<>();
    private static final VersionPolicy.zza zzil = new com.google.android.gms.dynamite.zza();

    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE = new com.google.android.gms.dynamite.zzb();

    @KeepForSdk
    public static final VersionPolicy PREFER_LOCAL = new zzc();

    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzd();

    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zze();

    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzf();
    private static final VersionPolicy zzim = new zzg();

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {

        @GuardedBy("DynamiteLoaderClassLoader.class")
        public static ClassLoader sClassLoader;
    }

    @KeepForSdk
    public static class LoadingException extends Exception {
        private LoadingException(String str) {
            super(str);
        }

        /* synthetic */ LoadingException(String str, com.google.android.gms.dynamite.zza zzaVar) {
            this(str);
        }

        private LoadingException(String str, Throwable th) {
            super(str, th);
        }

        /* synthetic */ LoadingException(String str, Throwable th, com.google.android.gms.dynamite.zza zzaVar) {
            this(str, th);
        }
    }

    public interface VersionPolicy {

        public interface zza {
            int getLocalVersion(Context context, String str);

            int zza(Context context, String str, boolean z) throws LoadingException;
        }

        public static class zzb {
            public int zzir = 0;
            public int zzis = 0;
            public int zzit = 0;
        }

        zzb zza(Context context, String str, zza zzaVar) throws LoadingException;
    }

    private static class zza {
        public Cursor zzio;

        private zza() {
        }

        /* synthetic */ zza(com.google.android.gms.dynamite.zza zzaVar) {
            this();
        }
    }

    private static class zzb implements VersionPolicy.zza {
        private final int zzip;
        private final int zziq = 0;

        public zzb(int i, int i2) {
            this.zzip = i;
        }

        @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.zza
        public final int getLocalVersion(Context context, String str) {
            return this.zzip;
        }

        @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.zza
        public final int zza(Context context, String str, boolean z) {
            return 0;
        }
    }

    private DynamiteModule(Context context) {
        this.zzin = (Context) Preconditions.checkNotNull(context);
    }

    @KeepForSdk
    public static int getLocalVersion(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(".ModuleDescriptor");
            Class<?> clsLoadClass = classLoader.loadClass(sb.toString());
            Field declaredField = clsLoadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = clsLoadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            String strValueOf = String.valueOf(declaredField.get(null));
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(strValueOf);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(e.getMessage());
            Log.e("DynamiteModule", strValueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(strValueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    @KeepForSdk
    public static int getRemoteVersion(Context context, String str) {
        return zza(context, str, false);
    }

    @KeepForSdk
    public static DynamiteModule load(Context context, VersionPolicy versionPolicy, String str) throws LoadingException {
        zza zzaVar = zzik.get();
        com.google.android.gms.dynamite.zza zzaVar2 = null;
        zza zzaVar3 = new zza(zzaVar2);
        zzik.set(zzaVar3);
        try {
            VersionPolicy.zzb zzbVarZza = versionPolicy.zza(context, str, zzil);
            int i = zzbVarZza.zzir;
            int i2 = zzbVarZza.zzis;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
            sb.append("Considering local module ");
            sb.append(str);
            sb.append(":");
            sb.append(i);
            sb.append(" and remote module ");
            sb.append(str);
            sb.append(":");
            sb.append(i2);
            Log.i("DynamiteModule", sb.toString());
            if (zzbVarZza.zzit == 0 || ((zzbVarZza.zzit == -1 && zzbVarZza.zzir == 0) || (zzbVarZza.zzit == 1 && zzbVarZza.zzis == 0))) {
                int i3 = zzbVarZza.zzir;
                int i4 = zzbVarZza.zzis;
                StringBuilder sb2 = new StringBuilder(91);
                sb2.append("No acceptable module found. Local version is ");
                sb2.append(i3);
                sb2.append(" and remote version is ");
                sb2.append(i4);
                sb2.append(".");
                throw new LoadingException(sb2.toString(), zzaVar2);
            }
            if (zzbVarZza.zzit == -1) {
                DynamiteModule dynamiteModuleZze = zze(context, str);
                if (zzaVar3.zzio != null) {
                    zzaVar3.zzio.close();
                }
                zzik.set(zzaVar);
                return dynamiteModuleZze;
            }
            if (zzbVarZza.zzit != 1) {
                int i5 = zzbVarZza.zzit;
                StringBuilder sb3 = new StringBuilder(47);
                sb3.append("VersionPolicy returned invalid code:");
                sb3.append(i5);
                throw new LoadingException(sb3.toString(), zzaVar2);
            }
            try {
                DynamiteModule dynamiteModuleZza = zza(context, str, zzbVarZza.zzis);
                if (zzaVar3.zzio != null) {
                    zzaVar3.zzio.close();
                }
                zzik.set(zzaVar);
                return dynamiteModuleZza;
            } catch (LoadingException e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to load remote module: ".concat(strValueOf) : new String("Failed to load remote module: "));
                if (zzbVarZza.zzir == 0 || versionPolicy.zza(context, str, new zzb(zzbVarZza.zzir, 0)).zzit != -1) {
                    throw new LoadingException("Remote load failed. No local fallback found.", e, zzaVar2);
                }
                DynamiteModule dynamiteModuleZze2 = zze(context, str);
                if (zzaVar3.zzio != null) {
                    zzaVar3.zzio.close();
                }
                zzik.set(zzaVar);
                return dynamiteModuleZze2;
            }
        } catch (Throwable th) {
            if (zzaVar3.zzio != null) {
                zzaVar3.zzio.close();
            }
            zzik.set(zzaVar);
            throw th;
        }
    }

    public static int zza(Context context, String str, boolean z) {
        Boolean bool;
        try {
            synchronized (DynamiteModule.class) {
                Boolean bool2 = zzif;
                if (bool2 == null) {
                    try {
                        Class<?> clsLoadClass = context.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName());
                        Field declaredField = clsLoadClass.getDeclaredField("sClassLoader");
                        synchronized (clsLoadClass) {
                            ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                            if (classLoader != null) {
                                if (classLoader == ClassLoader.getSystemClassLoader()) {
                                    bool = Boolean.FALSE;
                                } else {
                                    try {
                                        zza(classLoader);
                                    } catch (LoadingException unused) {
                                    }
                                    bool = Boolean.TRUE;
                                }
                            } else if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                            } else {
                                try {
                                    int iZzc = zzc(context, str, z);
                                    if (zzii != null && !zzii.isEmpty()) {
                                        zzh zzhVar = new zzh(zzii, ClassLoader.getSystemClassLoader());
                                        zza(zzhVar);
                                        declaredField.set(null, zzhVar);
                                        zzif = Boolean.TRUE;
                                        return iZzc;
                                    }
                                    return iZzc;
                                } catch (LoadingException unused2) {
                                    declaredField.set(null, ClassLoader.getSystemClassLoader());
                                    bool = Boolean.FALSE;
                                }
                            }
                            bool2 = bool;
                            zzif = bool2;
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                        String strValueOf = String.valueOf(e);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 30);
                        sb.append("Failed to load module via V2: ");
                        sb.append(strValueOf);
                        Log.w("DynamiteModule", sb.toString());
                        bool2 = Boolean.FALSE;
                    }
                }
                if (!bool2.booleanValue()) {
                    return zzb(context, str, z);
                }
                try {
                    return zzc(context, str, z);
                } catch (LoadingException e2) {
                    String strValueOf2 = String.valueOf(e2.getMessage());
                    Log.w("DynamiteModule", strValueOf2.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf2) : new String("Failed to retrieve remote module version: "));
                    return 0;
                }
            }
        } catch (Throwable th) {
            CrashUtils.addDynamiteErrorToDropBox(context, th);
            throw th;
        }
    }

    private static DynamiteModule zza(Context context, String str, int i) throws LoadingException {
        Boolean bool;
        IObjectWrapper iObjectWrapperZza;
        com.google.android.gms.dynamite.zza zzaVar = null;
        try {
            synchronized (DynamiteModule.class) {
                bool = zzif;
            }
            if (bool == null) {
                throw new LoadingException("Failed to determine which loading route to use.", zzaVar);
            }
            if (bool.booleanValue()) {
                return zzb(context, str, i);
            }
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
            sb.append("Selected remote version of ");
            sb.append(str);
            sb.append(", version >= ");
            sb.append(i);
            Log.i("DynamiteModule", sb.toString());
            zzi zziVarZzj = zzj(context);
            if (zziVarZzj == null) {
                throw new LoadingException("Failed to create IDynamiteLoader.", zzaVar);
            }
            if (zziVarZzj.zzak() >= 2) {
                iObjectWrapperZza = zziVarZzj.zzb(ObjectWrapper.wrap(context), str, i);
            } else {
                Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
                iObjectWrapperZza = zziVarZzj.zza(ObjectWrapper.wrap(context), str, i);
            }
            if (ObjectWrapper.unwrap(iObjectWrapperZza) != null) {
                return new DynamiteModule((Context) ObjectWrapper.unwrap(iObjectWrapperZza));
            }
            throw new LoadingException("Failed to load remote module.", zzaVar);
        } catch (RemoteException e) {
            throw new LoadingException("Failed to load remote module.", e, zzaVar);
        } catch (LoadingException e2) {
            throw e2;
        } catch (Throwable th) {
            CrashUtils.addDynamiteErrorToDropBox(context, th);
            throw new LoadingException("Failed to load remote module.", th, zzaVar);
        }
    }

    @GuardedBy("DynamiteModule.class")
    private static void zza(ClassLoader classLoader) throws LoadingException {
        zzk zzlVar;
        com.google.android.gms.dynamite.zza zzaVar = null;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzlVar = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                zzlVar = iInterfaceQueryLocalInterface instanceof zzk ? (zzk) iInterfaceQueryLocalInterface : new zzl(iBinder);
            }
            zzih = zzlVar;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e, zzaVar);
        }
    }

    private static Boolean zzaj() {
        Boolean boolValueOf;
        synchronized (DynamiteModule.class) {
            boolValueOf = Boolean.valueOf(zzij >= 2);
        }
        return boolValueOf;
    }

    private static int zzb(Context context, String str, boolean z) {
        zzi zziVarZzj = zzj(context);
        if (zziVarZzj == null) {
            return 0;
        }
        try {
            if (zziVarZzj.zzak() >= 2) {
                return zziVarZzj.zzb(ObjectWrapper.wrap(context), str, z);
            }
            Log.w("DynamiteModule", "IDynamite loader version < 2, falling back to getModuleVersion2");
            return zziVarZzj.zza(ObjectWrapper.wrap(context), str, z);
        } catch (RemoteException e) {
            String strValueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws RemoteException, LoadingException {
        zzk zzkVar;
        IObjectWrapper iObjectWrapperZza;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        synchronized (DynamiteModule.class) {
            zzkVar = zzih;
        }
        com.google.android.gms.dynamite.zza zzaVar = null;
        if (zzkVar == null) {
            throw new LoadingException("DynamiteLoaderV2 was not cached.", zzaVar);
        }
        zza zzaVar2 = zzik.get();
        if (zzaVar2 == null || zzaVar2.zzio == null) {
            throw new LoadingException("No result cursor", zzaVar);
        }
        Context applicationContext = context.getApplicationContext();
        Cursor cursor = zzaVar2.zzio;
        ObjectWrapper.wrap(null);
        if (zzaj().booleanValue()) {
            Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
            iObjectWrapperZza = zzkVar.zzb(ObjectWrapper.wrap(applicationContext), str, i, ObjectWrapper.wrap(cursor));
        } else {
            Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
            iObjectWrapperZza = zzkVar.zza(ObjectWrapper.wrap(applicationContext), str, i, ObjectWrapper.wrap(cursor));
        }
        Context context2 = (Context) ObjectWrapper.unwrap(iObjectWrapperZza);
        if (context2 != null) {
            return new DynamiteModule(context2);
        }
        throw new LoadingException("Failed to get module context", zzaVar);
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00b0  */
    /* JADX WARN: Multi-variable type inference failed */
    private static int zzc(Context context, String str, boolean z) throws Throwable {
        Cursor cursor;
        Cursor cursor2 = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            String str2 = z ? "api_force_staging" : "api";
            StringBuilder sb = new StringBuilder(str2.length() + 42 + String.valueOf(str).length());
            sb.append("content://com.google.android.gms.chimera/");
            sb.append(str2);
            sb.append("/");
            sb.append(str);
            Cursor cursorQuery = contentResolver.query(Uri.parse(sb.toString()), null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        int i = cursorQuery.getInt(0);
                        if (i > 0) {
                            synchronized (DynamiteModule.class) {
                                zzii = cursorQuery.getString(2);
                                int columnIndex = cursorQuery.getColumnIndex("loaderVersion");
                                if (columnIndex >= 0) {
                                    zzij = cursorQuery.getInt(columnIndex);
                                }
                            }
                            zza zzaVar = zzik.get();
                            if (zzaVar != null && zzaVar.zzio == null) {
                                zzaVar.zzio = cursorQuery;
                                cursorQuery = null;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return i;
                    }
                } catch (Exception e) {
                    cursor = cursorQuery;
                    e = e;
                    try {
                        if (e instanceof LoadingException) {
                            throw e;
                        }
                        throw new LoadingException("V2 version check failed", e, objArr == true ? 1 : 0);
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    cursor2 = cursorQuery;
                    th = th2;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            Log.w("DynamiteModule", "Failed to retrieve remote module version.");
            throw new LoadingException("Failed to connect to dynamite module ContentResolver.", (com.google.android.gms.dynamite.zza) (objArr2 == true ? 1 : 0));
        } catch (Exception e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static DynamiteModule zze(Context context, String str) {
        String strValueOf = String.valueOf(str);
        Log.i("DynamiteModule", strValueOf.length() != 0 ? "Selected local version of ".concat(strValueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static zzi zzj(Context context) {
        zzi zzjVar;
        synchronized (DynamiteModule.class) {
            if (zzig != null) {
                return zzig;
            }
            if (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzjVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzjVar = iInterfaceQueryLocalInterface instanceof zzi ? (zzi) iInterfaceQueryLocalInterface : new zzj(iBinder);
                }
                if (zzjVar != null) {
                    zzig = zzjVar;
                    return zzjVar;
                }
            } catch (Exception e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.e("DynamiteModule", strValueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(strValueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
            }
            return null;
        }
    }

    @KeepForSdk
    public final Context getModuleContext() {
        return this.zzin;
    }

    @KeepForSdk
    public final IBinder instantiate(String str) throws LoadingException {
        try {
            return (IBinder) this.zzin.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String strValueOf = String.valueOf(str);
            throw new LoadingException(strValueOf.length() != 0 ? "Failed to instantiate module class: ".concat(strValueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }
}
