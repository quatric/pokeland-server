package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class FileContent extends AbstractInputStreamContent {
    private final File file;

    public FileContent(String str, File file) {
        super(str);
        this.file = (File) Preconditions.checkNotNull(file);
    }

    public File getFile() {
        return this.file;
    }

    @Override // com.google.api.client.http.AbstractInputStreamContent
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }

    @Override // com.google.api.client.http.HttpContent
    public long getLength() {
        return this.file.length();
    }

    @Override // com.google.api.client.http.HttpContent
    public boolean retrySupported() {
        return true;
    }

    @Override // com.google.api.client.http.AbstractInputStreamContent
    public FileContent setCloseInputStream(boolean z) {
        return (FileContent) super.setCloseInputStream(z);
    }

    @Override // com.google.api.client.http.AbstractInputStreamContent
    public FileContent setType(String str) {
        return (FileContent) super.setType(str);
    }
}
