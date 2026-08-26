package com.google.common.html;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class HtmlEscapers {
    private static final Escaper HTML_ESCAPER = Escapers.builder().addEscape(Typography.quote, "&quot;").addEscape('\'', "&#39;").addEscape(Typography.amp, "&amp;").addEscape(Typography.less, "&lt;").addEscape(Typography.greater, "&gt;").build();

    private HtmlEscapers() {
    }

    public static Escaper htmlEscaper() {
        return HTML_ESCAPER;
    }
}
