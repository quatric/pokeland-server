package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class TrieParser {
    private static final Joiner PREFIX_JOINER = Joiner.m467on("");

    TrieParser() {
    }

    private static int doParseTrieToBuilder(List<CharSequence> list, CharSequence charSequence, ImmutableMap.Builder<String, PublicSuffixType> builder) {
        int length = charSequence.length();
        int i = 0;
        char cCharAt = 0;
        while (i < length && (cCharAt = charSequence.charAt(i)) != '&' && cCharAt != '?' && cCharAt != '!' && cCharAt != ':' && cCharAt != ',') {
            i++;
        }
        list.add(0, reverse(charSequence.subSequence(0, i)));
        if (cCharAt == '!' || cCharAt == '?' || cCharAt == ':' || cCharAt == ',') {
            String strJoin = PREFIX_JOINER.join(list);
            if (strJoin.length() > 0) {
                builder.put(strJoin, PublicSuffixType.fromCode(cCharAt));
            }
        }
        int iDoParseTrieToBuilder = i + 1;
        if (cCharAt != '?' && cCharAt != ',') {
            while (iDoParseTrieToBuilder < length) {
                iDoParseTrieToBuilder += doParseTrieToBuilder(list, charSequence.subSequence(iDoParseTrieToBuilder, length), builder);
                if (charSequence.charAt(iDoParseTrieToBuilder) == '?' || charSequence.charAt(iDoParseTrieToBuilder) == ',') {
                    iDoParseTrieToBuilder++;
                    break;
                }
            }
        }
        list.remove(0);
        return iDoParseTrieToBuilder;
    }

    static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence charSequence) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int length = charSequence.length();
        int iDoParseTrieToBuilder = 0;
        while (iDoParseTrieToBuilder < length) {
            iDoParseTrieToBuilder += doParseTrieToBuilder(Lists.newLinkedList(), charSequence.subSequence(iDoParseTrieToBuilder, length), builder);
        }
        return builder.build();
    }

    private static CharSequence reverse(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}
