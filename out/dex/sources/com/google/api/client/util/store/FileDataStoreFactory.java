package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Throwables;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FileDataStoreFactory extends AbstractDataStoreFactory {
    private static final Logger LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
    private final File dataDirectory;

    static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {
        private final File dataFile;

        FileDataStore(FileDataStoreFactory fileDataStoreFactory, File file, String str) throws IOException {
            super(fileDataStoreFactory, str);
            this.dataFile = new File(file, str);
            if (IOUtils.isSymbolicLink(this.dataFile)) {
                throw new IOException("unable to use a symbolic link: " + this.dataFile);
            }
            if (!this.dataFile.createNewFile()) {
                this.keyValueMap = (HashMap) IOUtils.deserialize(new FileInputStream(this.dataFile));
            } else {
                this.keyValueMap = Maps.newHashMap();
                save();
            }
        }

        @Override // com.google.api.client.util.store.AbstractDataStore, com.google.api.client.util.store.DataStore
        public FileDataStoreFactory getDataStoreFactory() {
            return (FileDataStoreFactory) super.getDataStoreFactory();
        }

        @Override // com.google.api.client.util.store.AbstractMemoryDataStore
        public void save() throws IOException {
            IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
        }
    }

    public FileDataStoreFactory(File file) throws Throwable {
        File canonicalFile = file.getCanonicalFile();
        this.dataDirectory = canonicalFile;
        if (IOUtils.isSymbolicLink(canonicalFile)) {
            throw new IOException("unable to use a symbolic link: " + canonicalFile);
        }
        if (canonicalFile.exists() || canonicalFile.mkdirs()) {
            setPermissionsToOwnerOnly(canonicalFile);
            return;
        }
        throw new IOException("unable to create directory: " + canonicalFile);
    }

    static void setPermissionsToOwnerOnly(File file) throws Throwable {
        try {
            Method method = File.class.getMethod("setReadable", Boolean.TYPE, Boolean.TYPE);
            Method method2 = File.class.getMethod("setWritable", Boolean.TYPE, Boolean.TYPE);
            Method method3 = File.class.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE);
            if (!((Boolean) method.invoke(file, false, false)).booleanValue() || !((Boolean) method2.invoke(file, false, false)).booleanValue() || !((Boolean) method3.invoke(file, false, false)).booleanValue()) {
                LOGGER.warning("unable to change permissions for everybody: " + file);
            }
            if (((Boolean) method.invoke(file, true, true)).booleanValue() && ((Boolean) method2.invoke(file, true, true)).booleanValue() && ((Boolean) method3.invoke(file, true, true)).booleanValue()) {
                return;
            }
            LOGGER.warning("unable to change permissions for owner: " + file);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException unused) {
        } catch (NoSuchMethodException unused2) {
            LOGGER.warning("Unable to set permissions for " + file + ", likely because you are running a version of Java prior to 1.6");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Throwables.propagateIfPossible(cause, IOException.class);
            throw new RuntimeException(cause);
        }
    }

    @Override // com.google.api.client.util.store.AbstractDataStoreFactory
    protected <V extends Serializable> DataStore<V> createDataStore(String str) throws IOException {
        return new FileDataStore(this, this.dataDirectory, str);
    }

    public final File getDataDirectory() {
        return this.dataDirectory;
    }
}
