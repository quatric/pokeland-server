package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.Beta;
import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import com.google.api.client.repackaged.com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
public final class Splitter {
    private final int limit;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final CharMatcher trimmer;

    @Beta
    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter entrySplitter;
        private final Splitter outerSplitter;

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.outerSplitter = splitter;
            this.entrySplitter = (Splitter) Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str : this.outerSplitter.split(charSequence)) {
                Iterator itSplittingIterator = this.entrySplitter.splittingIterator(str);
                Preconditions.checkArgument(itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
                String str2 = (String) itSplittingIterator.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(str2), "Duplicate key [%s] found.", str2);
                Preconditions.checkArgument(itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
                linkedHashMap.put(str2, (String) itSplittingIterator.next());
                Preconditions.checkArgument(!itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    private static abstract class SplittingIterator extends AbstractIterator<String> {
        int limit;
        int offset = 0;
        final boolean omitEmptyStrings;
        final CharSequence toSplit;
        final CharMatcher trimmer;

        protected SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = charSequence;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.api.client.repackaged.com.google.common.base.AbstractIterator
        public String computeNext() {
            int i = this.offset;
            while (true) {
                int i2 = this.offset;
                if (i2 == -1) {
                    return endOfData();
                }
                int iSeparatorStart = separatorStart(i2);
                if (iSeparatorStart == -1) {
                    iSeparatorStart = this.toSplit.length();
                    this.offset = -1;
                } else {
                    this.offset = separatorEnd(iSeparatorStart);
                }
                int i3 = this.offset;
                if (i3 == i) {
                    this.offset = i3 + 1;
                    if (this.offset >= this.toSplit.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (i < iSeparatorStart && this.trimmer.matches(this.toSplit.charAt(i))) {
                        i++;
                    }
                    while (iSeparatorStart > i && this.trimmer.matches(this.toSplit.charAt(iSeparatorStart - 1))) {
                        iSeparatorStart--;
                    }
                    if (!this.omitEmptyStrings || i != iSeparatorStart) {
                        int i4 = this.limit;
                        if (i4 == 1) {
                            iSeparatorStart = this.toSplit.length();
                            this.offset = -1;
                            while (iSeparatorStart > i && this.trimmer.matches(this.toSplit.charAt(iSeparatorStart - 1))) {
                                iSeparatorStart--;
                            }
                        } else {
                            this.limit = i4 - 1;
                        }
                        return this.toSplit.subSequence(i, iSeparatorStart).toString();
                    }
                    i = this.offset;
                }
            }
        }

        abstract int separatorEnd(int i);

        abstract int separatorStart(int i);
    }

    private interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence charSequence);
    }

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean z, CharMatcher charMatcher, int i) {
        this.strategy = strategy;
        this.omitEmptyStrings = z;
        this.trimmer = charMatcher;
        this.limit = i;
    }

    public static Splitter fixedLength(final int i) {
        Preconditions.checkArgument(i > 0, "The length may not be less than 1");
        return new Splitter(new Strategy() { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.4
            @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.4.1
                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i2) {
                        return i2;
                    }

                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorStart(int i2) {
                        int i3 = i2 + i;
                        if (i3 < this.toSplit.length()) {
                            return i3;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m449on(char c) {
        return m450on(CharMatcher.m444is(c));
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m450on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy() { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.1
            @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.1.1
                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    int separatorEnd(int i) {
                        return i + 1;
                    }

                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    int separatorStart(int i) {
                        return charMatcher.indexIn(this.toSplit, i);
                    }
                };
            }
        });
    }

    /* JADX INFO: renamed from: on */
    private static Splitter m451on(final CommonPattern commonPattern) {
        Preconditions.checkArgument(!commonPattern.matcher("").matches(), "The pattern may not match the empty string: %s", commonPattern);
        return new Splitter(new Strategy() { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.3
            @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                final CommonMatcher commonMatcherMatcher = commonPattern.matcher(charSequence);
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.3.1
                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i) {
                        return commonMatcherMatcher.end();
                    }

                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorStart(int i) {
                        if (commonMatcherMatcher.find(i)) {
                            return commonMatcherMatcher.start();
                        }
                        return -1;
                    }
                };
            }
        });
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m452on(final String str) {
        Preconditions.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        return str.length() == 1 ? m449on(str.charAt(0)) : new Splitter(new Strategy() { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.2
            @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.2.1
                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i) {
                        return i + str.length();
                    }

                    @Override // com.google.api.client.repackaged.com.google.common.base.Splitter.SplittingIterator
                    public int separatorStart(int i) {
                        int length = str.length();
                        int length2 = this.toSplit.length() - length;
                        while (i <= length2) {
                            for (int i2 = 0; i2 < length; i2++) {
                                if (this.toSplit.charAt(i2 + i) != str.charAt(i2)) {
                                    i++;
                                }
                            }
                            return i;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    @GwtIncompatible
    /* JADX INFO: renamed from: on */
    public static Splitter m453on(Pattern pattern) {
        return m451on(new JdkPattern(pattern));
    }

    @GwtIncompatible
    public static Splitter onPattern(String str) {
        return m451on(Platform.compilePattern(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterator<String> splittingIterator(CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }

    public Splitter limit(int i) {
        Preconditions.checkArgument(i > 0, "must be greater than zero: %s", i);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, i);
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>() { // from class: com.google.api.client.repackaged.com.google.common.base.Splitter.5
            @Override // java.lang.Iterable
            public Iterator<String> iterator() {
                return Splitter.this.splittingIterator(charSequence);
            }

            public String toString() {
                Joiner joinerM447on = Joiner.m447on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder sbAppendTo = joinerM447on.appendTo(sb, (Iterable<?>) this);
                sbAppendTo.append(']');
                return sbAppendTo.toString();
            }
        };
    }

    @Beta
    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> itSplittingIterator = splittingIterator(charSequence);
        ArrayList arrayList = new ArrayList();
        while (itSplittingIterator.hasNext()) {
            arrayList.add(itSplittingIterator.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.strategy, this.omitEmptyStrings, charMatcher, this.limit);
    }

    @Beta
    public MapSplitter withKeyValueSeparator(char c) {
        return withKeyValueSeparator(m449on(c));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(splitter);
    }

    @Beta
    public MapSplitter withKeyValueSeparator(String str) {
        return withKeyValueSeparator(m452on(str));
    }
}
