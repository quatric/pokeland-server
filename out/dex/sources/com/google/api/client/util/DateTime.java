package com.google.api.client.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class DateTime implements Serializable {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final Pattern RFC3339_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    private static final long serialVersionUID = 1;
    private final boolean dateOnly;
    private final int tzShift;
    private final long value;

    public DateTime(long j) {
        this(false, j, null);
    }

    public DateTime(long j, int i) {
        this(false, j, Integer.valueOf(i));
    }

    public DateTime(String str) {
        DateTime rfc3339 = parseRfc3339(str);
        this.dateOnly = rfc3339.dateOnly;
        this.value = rfc3339.value;
        this.tzShift = rfc3339.tzShift;
    }

    public DateTime(Date date) {
        this(date.getTime());
    }

    public DateTime(Date date, TimeZone timeZone) {
        this(false, date.getTime(), timeZone == null ? null : Integer.valueOf(timeZone.getOffset(date.getTime()) / 60000));
    }

    public DateTime(boolean z, long j, Integer num) {
        this.dateOnly = z;
        this.value = j;
        this.tzShift = z ? 0 : num == null ? TimeZone.getDefault().getOffset(j) / 60000 : num.intValue();
    }

    private static void appendInt(StringBuilder sb, int i, int i2) {
        if (i < 0) {
            sb.append('-');
            i = -i;
        }
        int i3 = i2;
        int i4 = i;
        while (i4 > 0) {
            i4 /= 10;
            i3--;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            sb.append('0');
        }
        if (i != 0) {
            sb.append(i);
        }
    }

    public static DateTime parseRfc3339(String str) throws NumberFormatException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Matcher matcher = RFC3339_PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw new NumberFormatException("Invalid date/time format: " + str);
        }
        int i6 = Integer.parseInt(matcher.group(1));
        int i7 = Integer.parseInt(matcher.group(2)) - 1;
        int i8 = Integer.parseInt(matcher.group(3));
        boolean z = matcher.group(4) != null;
        String strGroup = matcher.group(9);
        boolean z2 = strGroup != null;
        Integer numValueOf = null;
        if (z2 && !z) {
            throw new NumberFormatException("Invalid date/time format, cannot specify time zone shift without specifying time: " + str);
        }
        if (z) {
            int i9 = Integer.parseInt(matcher.group(5));
            i2 = Integer.parseInt(matcher.group(6));
            i3 = Integer.parseInt(matcher.group(7));
            if (matcher.group(8) != null) {
                double d = Integer.parseInt(matcher.group(8).substring(1));
                double dPow = Math.pow(10.0d, matcher.group(8).substring(1).length() - 3);
                Double.isNaN(d);
                i4 = (int) (d / dPow);
            } else {
                i4 = 0;
            }
            i = i9;
        } else {
            z = z;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
        gregorianCalendar.set(i6, i7, i8, i, i2, i3);
        gregorianCalendar.set(14, i4);
        long timeInMillis = gregorianCalendar.getTimeInMillis();
        if (z && z2) {
            if (Character.toUpperCase(strGroup.charAt(0)) == 'Z') {
                i5 = 0;
            } else {
                int i10 = (Integer.parseInt(matcher.group(11)) * 60) + Integer.parseInt(matcher.group(12));
                if (matcher.group(10).charAt(0) == '-') {
                    i10 = -i10;
                }
                int i11 = i10;
                timeInMillis -= ((long) i11) * 60000;
                i5 = i11;
            }
            numValueOf = Integer.valueOf(i5);
        }
        return new DateTime(!z, timeInMillis, numValueOf);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DateTime)) {
            return false;
        }
        DateTime dateTime = (DateTime) obj;
        return this.dateOnly == dateTime.dateOnly && this.value == dateTime.value && this.tzShift == dateTime.tzShift;
    }

    public int getTimeZoneShift() {
        return this.tzShift;
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long[] jArr = new long[3];
        jArr[0] = this.value;
        jArr[1] = this.dateOnly ? 1L : 0L;
        jArr[2] = this.tzShift;
        return Arrays.hashCode(jArr);
    }

    public boolean isDateOnly() {
        return this.dateOnly;
    }

    public String toString() {
        return toStringRfc3339();
    }

    public String toStringRfc3339() {
        StringBuilder sb = new StringBuilder();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
        gregorianCalendar.setTimeInMillis(this.value + (((long) this.tzShift) * 60000));
        appendInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        appendInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        appendInt(sb, gregorianCalendar.get(5), 2);
        if (!this.dateOnly) {
            sb.append('T');
            appendInt(sb, gregorianCalendar.get(11), 2);
            sb.append(':');
            appendInt(sb, gregorianCalendar.get(12), 2);
            sb.append(':');
            appendInt(sb, gregorianCalendar.get(13), 2);
            if (gregorianCalendar.isSet(14)) {
                sb.append('.');
                appendInt(sb, gregorianCalendar.get(14), 3);
            }
            int i = this.tzShift;
            if (i == 0) {
                sb.append('Z');
            } else {
                if (i > 0) {
                    sb.append('+');
                } else {
                    sb.append('-');
                    i = -i;
                }
                appendInt(sb, i / 60, 2);
                sb.append(':');
                appendInt(sb, i % 60, 2);
            }
        }
        return sb.toString();
    }
}
