package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzz {
    zzz() {
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00ad A[Catch: all -> 0x00b1, Throwable -> 0x00b4, TRY_ENTER, TryCatch #9 {all -> 0x00b1, Throwable -> 0x00b4, blocks: (B:7:0x0041, B:15:0x005d, B:26:0x009b, B:36:0x00ad, B:37:0x00b0), top: B:54:0x0041 }] */
    /* JADX WARN: Code duplicated, block: B:58:? A[Catch: all -> 0x00b1, Throwable -> 0x00b4, SYNTHETIC, TRY_LEAVE, TryCatch #9 {all -> 0x00b1, Throwable -> 0x00b4, blocks: (B:7:0x0041, B:15:0x005d, B:26:0x009b, B:36:0x00ad, B:37:0x00b0), top: B:54:0x0041 }] */
    @Nullable
    private final zzy zza(Context context, String str, zzy zzyVar, boolean z) throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Writing key to properties file");
        }
        Properties properties = new Properties();
        properties.setProperty("pub", zzyVar.zzv());
        properties.setProperty("pri", zzyVar.zzw());
        properties.setProperty("cre", String.valueOf(zzyVar.zzbx));
        File fileZzf = zzf(context, str);
        try {
            fileZzf.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileZzf, "rw");
            try {
                FileChannel channel = randomAccessFile.getChannel();
                try {
                    channel.lock();
                    if (z && channel.size() > 0) {
                        try {
                            channel.position(0L);
                            zzy zzyVarZza = zza(channel);
                            if (channel != null) {
                                zza((Throwable) null, channel);
                            }
                            zza((Throwable) null, randomAccessFile);
                            return zzyVarZza;
                        } catch (zzaa | IOException e) {
                            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                                String strValueOf = String.valueOf(e);
                                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 64);
                                sb.append("Tried reading key pair before writing new one, but failed with: ");
                                sb.append(strValueOf);
                                Log.d("FirebaseInstanceId", sb.toString());
                            }
                        }
                    }
                    channel.position(0L);
                    properties.store(Channels.newOutputStream(channel), (String) null);
                    if (channel != null) {
                        zza((Throwable) null, channel);
                    }
                    zza((Throwable) null, randomAccessFile);
                    return zzyVar;
                } catch (Throwable th5) {
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        th3 = th5;
                        th4 = th6;
                        if (channel != null) {
                            throw th4;
                        }
                        zza(th3, channel);
                        throw th4;
                    }
                }
            } catch (Throwable th7) {
                try {
                    throw th7;
                } catch (Throwable th8) {
                    th = th7;
                    th2 = th8;
                    zza(th, randomAccessFile);
                    throw th2;
                }
            }
        } catch (IOException e2) {
            String strValueOf2 = String.valueOf(e2);
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 21);
            sb2.append("Failed to write key: ");
            sb2.append(strValueOf2);
            Log.w("FirebaseInstanceId", sb2.toString());
            return null;
        }
    }

    @Nullable
    private static zzy zza(SharedPreferences sharedPreferences, String str) throws zzaa {
        String string = sharedPreferences.getString(zzav.zzd(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzav.zzd(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        return new zzy(zzc(string, string2), zzb(sharedPreferences, str));
    }

    /* JADX WARN: Code duplicated, block: B:17:0x002e A[Catch: all -> 0x0032, Throwable -> 0x0034, TRY_ENTER, TryCatch #3 {, blocks: (B:3:0x0006, B:7:0x001c, B:17:0x002e, B:18:0x0031), top: B:25:0x0006, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:30:? A[Catch: all -> 0x0032, Throwable -> 0x0034, SYNTHETIC, TRY_LEAVE, TryCatch #3 {, blocks: (B:3:0x0006, B:7:0x001c, B:17:0x002e, B:18:0x0031), top: B:25:0x0006, outer: #0 }] */
    private final zzy zza(File file) throws IOException, zzaa {
        Throwable th;
        Throwable th2;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            FileChannel channel = fileInputStream.getChannel();
            try {
                channel.lock(0L, LongCompanionObject.MAX_VALUE, true);
                zzy zzyVarZza = zza(channel);
                if (channel != null) {
                    zza((Throwable) null, channel);
                }
                zza((Throwable) null, fileInputStream);
                return zzyVarZza;
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    th = th3;
                    th2 = th4;
                    if (channel != null) {
                        throw th2;
                    }
                    zza(th, channel);
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            zza((Throwable) null, fileInputStream);
            throw th5;
        }
    }

    private static zzy zza(FileChannel fileChannel) throws IOException, zzaa {
        Properties properties = new Properties();
        properties.load(Channels.newInputStream(fileChannel));
        String property = properties.getProperty("pub");
        String property2 = properties.getProperty("pri");
        if (property == null || property2 == null) {
            throw new zzaa("Invalid properties file");
        }
        try {
            return new zzy(zzc(property, property2), Long.parseLong(properties.getProperty("cre")));
        } catch (NumberFormatException e) {
            throw new zzaa(e);
        }
    }

    static void zza(Context context) {
        for (File file : zzb(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    private final void zza(Context context, String str, zzy zzyVar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzyVar.equals(zza(sharedPreferences, str))) {
                return;
            }
        } catch (zzaa unused) {
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Writing key to shared preferences");
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString(zzav.zzd(str, "|P|"), zzyVar.zzv());
        editorEdit.putString(zzav.zzd(str, "|K|"), zzyVar.zzw());
        editorEdit.putString(zzav.zzd(str, "cre"), String.valueOf(zzyVar.zzbx));
        editorEdit.commit();
    }

    private static /* synthetic */ void zza(Throwable th, FileInputStream fileInputStream) throws IOException {
        if (th == null) {
            fileInputStream.close();
            return;
        }
        try {
            fileInputStream.close();
        } catch (Throwable th2) {
            com.google.android.gms.internal.firebase_messaging.zzn.zza(th, th2);
        }
    }

    private static /* synthetic */ void zza(Throwable th, RandomAccessFile randomAccessFile) throws IOException {
        if (th == null) {
            randomAccessFile.close();
            return;
        }
        try {
            randomAccessFile.close();
        } catch (Throwable th2) {
            com.google.android.gms.internal.firebase_messaging.zzn.zza(th, th2);
        }
    }

    private static /* synthetic */ void zza(Throwable th, FileChannel fileChannel) {
        if (th == null) {
            fileChannel.close();
            return;
        }
        try {
            fileChannel.close();
        } catch (Throwable th2) {
            com.google.android.gms.internal.firebase_messaging.zzn.zza(th, th2);
        }
    }

    private static long zzb(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzav.zzd(str, "cre"), null);
        if (string == null) {
            return 0L;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    private static File zzb(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static KeyPair zzc(String str, String str2) throws zzaa {
        try {
            byte[] bArrDecode = Base64.decode(str, 8);
            byte[] bArrDecode2 = Base64.decode(str2, 8);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return new KeyPair(keyFactory.generatePublic(new X509EncodedKeySpec(bArrDecode)), keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArrDecode2)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                String strValueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 19);
                sb.append("Invalid key stored ");
                sb.append(strValueOf);
                Log.w("FirebaseInstanceId", sb.toString());
                throw new zzaa(e);
            }
        } catch (IllegalArgumentException e2) {
            throw new zzaa(e2);
        }
    }

    @Nullable
    private final zzy zzd(Context context, String str) throws Throwable {
        try {
            zzy zzyVarZze = zze(context, str);
            if (zzyVarZze != null) {
                zza(context, str, zzyVarZze);
                return zzyVarZze;
            }
            e = null;
        } catch (zzaa e) {
            e = e;
        }
        try {
            zzy zzyVarZza = zza(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
            if (zzyVarZza != null) {
                zza(context, str, zzyVarZza, false);
                return zzyVarZza;
            }
        } catch (zzaa e2) {
            e = e2;
        }
        if (e == null) {
            return null;
        }
        throw e;
    }

    @Nullable
    private final zzy zze(Context context, String str) throws zzaa {
        File fileZzf = zzf(context, str);
        if (!fileZzf.exists()) {
            return null;
        }
        try {
            return zza(fileZzf);
        } catch (zzaa | IOException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 40);
                sb.append("Failed to read key from file, retrying: ");
                sb.append(strValueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            try {
                return zza(fileZzf);
            } catch (IOException e2) {
                String strValueOf2 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 45);
                sb2.append("IID file exists, but failed to read from it: ");
                sb2.append(strValueOf2);
                Log.w("FirebaseInstanceId", sb2.toString());
                throw new zzaa(e2);
            }
        }
    }

    private static File zzf(Context context, String str) {
        String string;
        if (TextUtils.isEmpty(str)) {
            string = "com.google.InstanceId.properties";
        } else {
            try {
                String strEncodeToString = Base64.encodeToString(str.getBytes("UTF-8"), 11);
                StringBuilder sb = new StringBuilder(String.valueOf(strEncodeToString).length() + 33);
                sb.append("com.google.InstanceId_");
                sb.append(strEncodeToString);
                sb.append(".properties");
                string = sb.toString();
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        return new File(zzb(context), string);
    }

    @WorkerThread
    final zzy zzb(Context context, String str) throws Throwable {
        zzy zzyVarZzd = zzd(context, str);
        return zzyVarZzd != null ? zzyVarZzd : zzc(context, str);
    }

    @WorkerThread
    final zzy zzc(Context context, String str) throws Throwable {
        zzy zzyVar = new zzy(zza.zzc(), System.currentTimeMillis());
        zzy zzyVarZza = zza(context, str, zzyVar, true);
        if (zzyVarZza != null && !zzyVarZza.equals(zzyVar)) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Loaded key after generating new one, using loaded one");
            }
            return zzyVarZza;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Generated new key");
        }
        zza(context, str, zzyVar);
        return zzyVar;
    }
}
