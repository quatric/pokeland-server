package com.google.api.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class PemReader {
    private static final Pattern BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
    private static final Pattern END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
    private BufferedReader reader;

    public static final class Section {
        private final byte[] base64decodedBytes;
        private final String title;

        Section(String str, byte[] bArr) {
            this.title = (String) Preconditions.checkNotNull(str);
            this.base64decodedBytes = (byte[]) Preconditions.checkNotNull(bArr);
        }

        public byte[] getBase64DecodedBytes() {
            return this.base64decodedBytes;
        }

        public String getTitle() {
            return this.title;
        }
    }

    public PemReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public static Section readFirstSectionAndClose(Reader reader) throws IOException {
        return readFirstSectionAndClose(reader, null);
    }

    public static Section readFirstSectionAndClose(Reader reader, String str) throws IOException {
        PemReader pemReader = new PemReader(reader);
        try {
            return pemReader.readNextSection(str);
        } finally {
            pemReader.close();
        }
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public Section readNextSection() throws IOException {
        return readNextSection(null);
    }

    public Section readNextSection(String str) throws IOException {
        StringBuilder sb = null;
        String str2 = null;
        while (true) {
            String line = this.reader.readLine();
            if (line == null) {
                Preconditions.checkArgument(str2 == null, "missing end tag (%s)", str2);
                return null;
            }
            if (sb == null) {
                Matcher matcher = BEGIN_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String strGroup = matcher.group(1);
                    if (str == null || strGroup.equals(str)) {
                        sb = new StringBuilder();
                        str2 = strGroup;
                    }
                }
            } else {
                Matcher matcher2 = END_PATTERN.matcher(line);
                if (matcher2.matches()) {
                    String strGroup2 = matcher2.group(1);
                    Preconditions.checkArgument(strGroup2.equals(str2), "end tag (%s) doesn't match begin tag (%s)", strGroup2, str2);
                    return new Section(str2, Base64.decodeBase64(sb.toString()));
                }
                sb.append(line);
            }
        }
    }
}
