package com.google.common.p015io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
public abstract class CharSource {

    private final class AsByteSource extends ByteSource {
        final Charset charset;

        AsByteSource(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.p015io.ByteSource
        public CharSource asCharSource(Charset charset) {
            return charset.equals(this.charset) ? CharSource.this : super.asCharSource(charset);
        }

        @Override // com.google.common.p015io.ByteSource
        public InputStream openStream() throws IOException {
            return new ReaderInputStream(CharSource.this.openStream(), this.charset, 8192);
        }

        public String toString() {
            return CharSource.this.toString() + ".asByteSource(" + this.charset + ")";
        }
    }

    private static class CharSequenceCharSource extends CharSource {
        private static final Splitter LINE_SPLITTER = Splitter.onPattern("\r\n|\n|\r");
        private final CharSequence seq;

        protected CharSequenceCharSource(CharSequence charSequence) {
            this.seq = (CharSequence) Preconditions.checkNotNull(charSequence);
        }

        private Iterable<String> lines() {
            return new Iterable<String>() { // from class: com.google.common.io.CharSource.CharSequenceCharSource.1
                @Override // java.lang.Iterable
                public Iterator<String> iterator() {
                    return new AbstractIterator<String>() { // from class: com.google.common.io.CharSource.CharSequenceCharSource.1.1
                        Iterator<String> lines;

                        {
                            this.lines = CharSequenceCharSource.LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();
                        }

                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.common.collect.AbstractIterator
                        public String computeNext() {
                            if (this.lines.hasNext()) {
                                String next = this.lines.next();
                                if (this.lines.hasNext() || !next.isEmpty()) {
                                    return next;
                                }
                            }
                            return endOfData();
                        }
                    };
                }
            };
        }

        @Override // com.google.common.p015io.CharSource
        public boolean isEmpty() {
            return this.seq.length() == 0;
        }

        @Override // com.google.common.p015io.CharSource
        public long length() {
            return this.seq.length();
        }

        @Override // com.google.common.p015io.CharSource
        public Optional<Long> lengthIfKnown() {
            return Optional.m468of(Long.valueOf(this.seq.length()));
        }

        @Override // com.google.common.p015io.CharSource
        public Reader openStream() {
            return new CharSequenceReader(this.seq);
        }

        @Override // com.google.common.p015io.CharSource
        public String read() {
            return this.seq.toString();
        }

        @Override // com.google.common.p015io.CharSource
        public String readFirstLine() {
            Iterator<String> it = lines().iterator();
            if (it.hasNext()) {
                return it.next();
            }
            return null;
        }

        @Override // com.google.common.p015io.CharSource
        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(lines());
        }

        @Override // com.google.common.p015io.CharSource
        public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
            Iterator<String> it = lines().iterator();
            while (it.hasNext() && lineProcessor.processLine(it.next())) {
            }
            return lineProcessor.getResult();
        }

        public String toString() {
            return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
        }
    }

    private static final class ConcatenatedCharSource extends CharSource {
        private final Iterable<? extends CharSource> sources;

        ConcatenatedCharSource(Iterable<? extends CharSource> iterable) {
            this.sources = (Iterable) Preconditions.checkNotNull(iterable);
        }

        @Override // com.google.common.p015io.CharSource
        public boolean isEmpty() throws IOException {
            Iterator<? extends CharSource> it = this.sources.iterator();
            while (it.hasNext()) {
                if (!it.next().isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.p015io.CharSource
        public long length() throws IOException {
            Iterator<? extends CharSource> it = this.sources.iterator();
            long length = 0;
            while (it.hasNext()) {
                length += it.next().length();
            }
            return length;
        }

        @Override // com.google.common.p015io.CharSource
        public Optional<Long> lengthIfKnown() {
            Iterator<? extends CharSource> it = this.sources.iterator();
            long jLongValue = 0;
            while (it.hasNext()) {
                Optional<Long> optionalLengthIfKnown = it.next().lengthIfKnown();
                if (!optionalLengthIfKnown.isPresent()) {
                    return Optional.absent();
                }
                jLongValue += optionalLengthIfKnown.get().longValue();
            }
            return Optional.m468of(Long.valueOf(jLongValue));
        }

        @Override // com.google.common.p015io.CharSource
        public Reader openStream() throws IOException {
            return new MultiReader(this.sources.iterator());
        }

        public String toString() {
            return "CharSource.concat(" + this.sources + ")";
        }
    }

    private static final class EmptyCharSource extends CharSequenceCharSource {
        private static final EmptyCharSource INSTANCE = new EmptyCharSource();

        private EmptyCharSource() {
            super("");
        }

        @Override // com.google.common.io.CharSource.CharSequenceCharSource
        public String toString() {
            return "CharSource.empty()";
        }
    }

    protected CharSource() {
    }

    public static CharSource concat(Iterable<? extends CharSource> iterable) {
        return new ConcatenatedCharSource(iterable);
    }

    public static CharSource concat(Iterator<? extends CharSource> it) {
        return concat(ImmutableList.copyOf(it));
    }

    public static CharSource concat(CharSource... charSourceArr) {
        return concat(ImmutableList.copyOf(charSourceArr));
    }

    private long countBySkipping(Reader reader) throws IOException {
        long j = 0;
        while (true) {
            long jSkip = reader.skip(LongCompanionObject.MAX_VALUE);
            if (jSkip == 0) {
                return j;
            }
            j += jSkip;
        }
    }

    public static CharSource empty() {
        return EmptyCharSource.INSTANCE;
    }

    public static CharSource wrap(CharSequence charSequence) {
        return new CharSequenceCharSource(charSequence);
    }

    @Beta
    public ByteSource asByteSource(Charset charset) {
        return new AsByteSource(charset);
    }

    @CanIgnoreReturnValue
    public long copyTo(CharSink charSink) throws Throwable {
        Preconditions.checkNotNull(charSink);
        Closer closerCreate = Closer.create();
        try {
            try {
                long jCopy = CharStreams.copy((Reader) closerCreate.register(openStream()), (Writer) closerCreate.register(charSink.openStream()));
                closerCreate.close();
                return jCopy;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(Appendable appendable) throws Throwable {
        Preconditions.checkNotNull(appendable);
        Closer closerCreate = Closer.create();
        try {
            try {
                long jCopy = CharStreams.copy((Reader) closerCreate.register(openStream()), appendable);
                closerCreate.close();
                return jCopy;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    public boolean isEmpty() throws Throwable {
        Optional<Long> optionalLengthIfKnown = lengthIfKnown();
        if (optionalLengthIfKnown.isPresent() && optionalLengthIfKnown.get().longValue() == 0) {
            return true;
        }
        Closer closerCreate = Closer.create();
        try {
            try {
                boolean z = ((Reader) closerCreate.register(openStream())).read() == -1;
                closerCreate.close();
                return z;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    @Beta
    public long length() throws Throwable {
        Optional<Long> optionalLengthIfKnown = lengthIfKnown();
        if (optionalLengthIfKnown.isPresent()) {
            return optionalLengthIfKnown.get().longValue();
        }
        Closer closerCreate = Closer.create();
        try {
            try {
                long jCountBySkipping = countBySkipping((Reader) closerCreate.register(openStream()));
                closerCreate.close();
                return jCountBySkipping;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    @Beta
    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    public BufferedReader openBufferedStream() throws IOException {
        Reader readerOpenStream = openStream();
        return readerOpenStream instanceof BufferedReader ? (BufferedReader) readerOpenStream : new BufferedReader(readerOpenStream);
    }

    public abstract Reader openStream() throws IOException;

    public String read() throws Throwable {
        Closer closerCreate = Closer.create();
        try {
            try {
                String string = CharStreams.toString((Reader) closerCreate.register(openStream()));
                closerCreate.close();
                return string;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    @Nullable
    public String readFirstLine() throws Throwable {
        Closer closerCreate = Closer.create();
        try {
            try {
                String line = ((BufferedReader) closerCreate.register(openBufferedStream())).readLine();
                closerCreate.close();
                return line;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public ImmutableList<String> readLines() throws Throwable {
        Closer closerCreate = Closer.create();
        try {
            try {
                BufferedReader bufferedReader = (BufferedReader) closerCreate.register(openBufferedStream());
                ArrayList arrayListNewArrayList = Lists.newArrayList();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) arrayListNewArrayList);
                        closerCreate.close();
                        return immutableListCopyOf;
                    }
                    arrayListNewArrayList.add(line);
                }
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }

    @CanIgnoreReturnValue
    @Beta
    public <T> T readLines(LineProcessor<T> lineProcessor) throws Throwable {
        Preconditions.checkNotNull(lineProcessor);
        Closer closerCreate = Closer.create();
        try {
            try {
                T t = (T) CharStreams.readLines((Reader) closerCreate.register(openStream()), lineProcessor);
                closerCreate.close();
                return t;
            } catch (Throwable th) {
                throw closerCreate.rethrow(th);
            }
        } catch (Throwable th2) {
            closerCreate.close();
            throw th2;
        }
    }
}
