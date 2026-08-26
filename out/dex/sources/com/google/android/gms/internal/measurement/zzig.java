package com.google.android.gms.internal.measurement;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzanj' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class zzig {
    public static final zzig zzanb = new zzig("DOUBLE", 0, zzij.DOUBLE, 1);
    public static final zzig zzanc = new zzig("FLOAT", 1, zzij.FLOAT, 5);
    public static final zzig zzand = new zzig("INT64", 2, zzij.LONG, 0);
    public static final zzig zzane = new zzig("UINT64", 3, zzij.LONG, 0);
    public static final zzig zzanf = new zzig("INT32", 4, zzij.INT, 0);
    public static final zzig zzang = new zzig("FIXED64", 5, zzij.LONG, 1);
    public static final zzig zzanh = new zzig("FIXED32", 6, zzij.INT, 5);
    public static final zzig zzani = new zzig("BOOL", 7, zzij.BOOLEAN, 0);
    public static final zzig zzanj;
    public static final zzig zzank;
    public static final zzig zzanl;
    public static final zzig zzanm;
    public static final zzig zzann;
    public static final zzig zzano;
    public static final zzig zzanp;
    public static final zzig zzanq;
    public static final zzig zzanr;
    public static final zzig zzans;
    private static final /* synthetic */ zzig[] zzanv;
    private final zzij zzant;
    private final int zzanu;

    static {
        final int i = 2;
        final int i2 = 3;
        final zzij zzijVar = zzij.STRING;
        final int i3 = 8;
        final String str = "STRING";
        zzanj = new zzig(str, i3, zzijVar, i) { // from class: com.google.android.gms.internal.measurement.zzif
            {
                int i4 = 8;
                int i5 = 2;
                zzid zzidVar = null;
            }
        };
        final zzij zzijVar2 = zzij.MESSAGE;
        final int i4 = 9;
        final String str2 = "GROUP";
        zzank = new zzig(str2, i4, zzijVar2, i2) { // from class: com.google.android.gms.internal.measurement.zzii
            {
                int i5 = 9;
                int i6 = 3;
                zzid zzidVar = null;
            }
        };
        final zzij zzijVar3 = zzij.MESSAGE;
        final int i5 = 10;
        final String str3 = "MESSAGE";
        zzanl = new zzig(str3, i5, zzijVar3, i) { // from class: com.google.android.gms.internal.measurement.zzih
            {
                int i6 = 10;
                int i7 = 2;
                zzid zzidVar = null;
            }
        };
        final zzij zzijVar4 = zzij.BYTE_STRING;
        final int i6 = 11;
        final String str4 = "BYTES";
        zzanm = new zzig(str4, i6, zzijVar4, i) { // from class: com.google.android.gms.internal.measurement.zzik
            {
                int i7 = 11;
                int i8 = 2;
                zzid zzidVar = null;
            }
        };
        zzann = new zzig("UINT32", 12, zzij.INT, 0);
        zzano = new zzig("ENUM", 13, zzij.ENUM, 0);
        zzanp = new zzig("SFIXED32", 14, zzij.INT, 5);
        zzanq = new zzig("SFIXED64", 15, zzij.LONG, 1);
        zzanr = new zzig("SINT32", 16, zzij.INT, 0);
        zzans = new zzig("SINT64", 17, zzij.LONG, 0);
        zzanv = new zzig[]{zzanb, zzanc, zzand, zzane, zzanf, zzang, zzanh, zzani, zzanj, zzank, zzanl, zzanm, zzann, zzano, zzanp, zzanq, zzanr, zzans};
    }

    private zzig(String str, int i, zzij zzijVar, int i2) {
        super(str, i);
        this.zzant = zzijVar;
        this.zzanu = i2;
    }

    /* synthetic */ zzig(String str, int i, zzij zzijVar, int i2, zzid zzidVar) {
        this(str, i, zzijVar, i2);
    }

    public static zzig[] values() {
        return (zzig[]) zzanv.clone();
    }

    public final zzij zzwz() {
        return this.zzant;
    }

    public final int zzxa() {
        return this.zzanu;
    }
}
