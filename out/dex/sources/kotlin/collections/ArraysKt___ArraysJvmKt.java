package kotlin.collections;

import com.android.billingclient.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _ArraysJvm.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0096\u0001\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000f\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010\u0004\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0006\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00020\b\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\u00020\n\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\f\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\r0\u0001*\u00020\u000e\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u0010\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00110\u0001*\u00020\u0012\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00130\u0001*\u00020\u0014\u001aU\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001c\u001a9\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001d\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a2\u0010\u001e\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f¢\u0006\u0004\b \u0010!\u001a\"\u0010\"\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0004\b#\u0010$\u001a\"\u0010%\u001a\u00020&\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0004\b'\u0010(\u001a0\u0010)\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f¢\u0006\u0002\u0010!\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u001f\u001a\u00020\fH\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0087\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\u0087\f\u001a \u0010*\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010$\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0006H\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\bH\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\nH\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\fH\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u000eH\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0010H\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0012H\u0087\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0014H\u0087\b\u001a \u0010+\u001a\u00020&\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010(\u001a\r\u0010+\u001a\u00020&*\u00020\u0006H\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\bH\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\nH\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\fH\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\u000eH\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0010H\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0012H\u0087\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0014H\u0087\b\u001aQ\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007¢\u0006\u0002\u00101\u001a2\u0010,\u001a\u00020\u0006*\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\b*\u00020\b2\u0006\u0010-\u001a\u00020\b2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\n2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\f*\u00020\f2\u0006\u0010-\u001a\u00020\f2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010-\u001a\u00020\u000e2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0010*\u00020\u00102\u0006\u0010-\u001a\u00020\u00102\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0012*\u00020\u00122\u0006\u0010-\u001a\u00020\u00122\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0014*\u00020\u00142\u0006\u0010-\u001a\u00020\u00142\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a$\u00102\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u00103\u001a.\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u00104\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\u00105\u001a\r\u00102\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u00102\u001a\u00020\u0006*\u00020\u00062\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\b*\u00020\bH\u0087\b\u001a\u0015\u00102\u001a\u00020\b*\u00020\b2\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\n*\u00020\nH\u0087\b\u001a\u0015\u00102\u001a\u00020\n*\u00020\n2\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\f*\u00020\fH\u0087\b\u001a\u0015\u00102\u001a\u00020\f*\u00020\f2\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\u000e*\u00020\u000eH\u0087\b\u001a\u0015\u00102\u001a\u00020\u000e*\u00020\u000e2\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\u0010*\u00020\u0010H\u0087\b\u001a\u0015\u00102\u001a\u00020\u0010*\u00020\u00102\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\u0012*\u00020\u0012H\u0087\b\u001a\u0015\u00102\u001a\u00020\u0012*\u00020\u00122\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a\r\u00102\u001a\u00020\u0014*\u00020\u0014H\u0087\b\u001a\u0015\u00102\u001a\u00020\u0014*\u00020\u00142\u0006\u00104\u001a\u00020\u000fH\u0087\b\u001a6\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0004\b7\u00108\u001a\"\u00106\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b7\u001a5\u00109\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0004\b6\u00108\u001a!\u00109\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a(\u0010:\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010;\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\u0010<\u001a\u0015\u0010:\u001a\u00020\u0005*\u00020\u00062\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\u0007*\u00020\b2\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\t*\u00020\n2\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\u000b*\u00020\f2\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\r*\u00020\u000e2\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\u000f*\u00020\u00102\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\u0011*\u00020\u00122\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010:\u001a\u00020\u0013*\u00020\u00142\u0006\u0010;\u001a\u00020\u000fH\u0087\b\u001a7\u0010=\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010?\u001a&\u0010=\u001a\u00020>*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a-\u0010@\u001a\b\u0012\u0004\u0012\u0002HA0\u0001\"\u0004\b\u0000\u0010A*\u0006\u0012\u0002\b\u00030\u00032\f\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0C¢\u0006\u0002\u0010D\u001aA\u0010E\u001a\u0002HF\"\u0010\b\u0000\u0010F*\n\u0012\u0006\b\u0000\u0012\u0002HA0G\"\u0004\b\u0001\u0010A*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010-\u001a\u0002HF2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0C¢\u0006\u0002\u0010H\u001a,\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010J\u001a4\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010K\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0086\u0002¢\u0006\u0002\u0010L\u001a2\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0086\u0002¢\u0006\u0002\u0010N\u001a\u0015\u0010I\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0005H\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0006*\u00020\u00062\u0006\u0010K\u001a\u00020\u0006H\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\u0006*\u00020\u00062\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00050MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\b*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0007H\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\b*\u00020\b2\u0006\u0010K\u001a\u00020\bH\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\b*\u00020\b2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00070MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\n*\u00020\n2\u0006\u0010\u0016\u001a\u00020\tH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\nH\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\n*\u00020\n2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\t0MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000bH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\f*\u00020\f2\u0006\u0010K\u001a\u00020\fH\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\f*\u00020\f2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000b0MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010K\u001a\u00020\u000eH\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\u000e*\u00020\u000e2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\r0MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000fH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0010*\u00020\u00102\u0006\u0010K\u001a\u00020\u0010H\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\u0010*\u00020\u00102\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000f0MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0011H\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0012*\u00020\u00122\u0006\u0010K\u001a\u00020\u0012H\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\u0012*\u00020\u00122\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00110MH\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0013H\u0086\u0002\u001a\u0015\u0010I\u001a\u00020\u0014*\u00020\u00142\u0006\u0010K\u001a\u00020\u0014H\u0086\u0002\u001a\u001b\u0010I\u001a\u00020\u0014*\u00020\u00142\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00130MH\u0086\u0002\u001a,\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010J\u001a\u001d\u0010P\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010Q\u001a*\u0010P\u001a\u00020>\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020R*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010S\u001a1\u0010P\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010T\u001a\n\u0010P\u001a\u00020>*\u00020\b\u001a\u001e\u0010P\u001a\u00020>*\u00020\b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\n\u001a\u001e\u0010P\u001a\u00020>*\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\f\u001a\u001e\u0010P\u001a\u00020>*\u00020\f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u000e\u001a\u001e\u0010P\u001a\u00020>*\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0010\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00102\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0012\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00122\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0014\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a9\u0010U\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010V\u001aM\u0010U\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010W\u001a-\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u00020Y\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020R*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010Z\u001a?\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u00020Y\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010[\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00050Y*\u00020\u0006\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00070Y*\u00020\b\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\t0Y*\u00020\n\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000b0Y*\u00020\f\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\r0Y*\u00020\u000e\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000f0Y*\u00020\u0010\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00110Y*\u00020\u0012\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00130Y*\u00020\u0014\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003*\u00020\u0006¢\u0006\u0002\u0010]\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003*\u00020\b¢\u0006\u0002\u0010^\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\t0\u0003*\u00020\n¢\u0006\u0002\u0010_\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003*\u00020\f¢\u0006\u0002\u0010`\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\r0\u0003*\u00020\u000e¢\u0006\u0002\u0010a\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003*\u00020\u0010¢\u0006\u0002\u0010b\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003*\u00020\u0012¢\u0006\u0002\u0010c\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003*\u00020\u0014¢\u0006\u0002\u0010d¨\u0006e"}, m1984d2 = {"asList", "", "T", "", "([Ljava/lang/Object;)Ljava/util/List;", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "binarySearch", "element", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;II)I", "([Ljava/lang/Object;Ljava/lang/Object;II)I", "contentDeepEquals", "other", "contentDeepEqualsInline", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepHashCode", "contentDeepHashCodeInline", "([Ljava/lang/Object;)I", "contentDeepToString", "", "contentDeepToStringInline", "([Ljava/lang/Object;)Ljava/lang/String;", "contentEquals", "contentHashCode", "contentToString", "copyInto", FirebaseAnalytics.Param.DESTINATION, "destinationOffset", "startIndex", "endIndex", "([Ljava/lang/Object;[Ljava/lang/Object;III)[Ljava/lang/Object;", "copyOf", "([Ljava/lang/Object;)[Ljava/lang/Object;", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRange", "copyOfRangeInline", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "copyOfRangeImpl", "elementAt", FirebaseAnalytics.Param.INDEX, "([Ljava/lang/Object;I)Ljava/lang/Object;", "fill", "", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "filterIsInstance", "R", "klass", "Ljava/lang/Class;", "([Ljava/lang/Object;Ljava/lang/Class;)Ljava/util/List;", "filterIsInstanceTo", "C", "", "([Ljava/lang/Object;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "plus", "([Ljava/lang/Object;Ljava/lang/Object;)[Ljava/lang/Object;", "elements", "([Ljava/lang/Object;[Ljava/lang/Object;)[Ljava/lang/Object;", "", "([Ljava/lang/Object;Ljava/util/Collection;)[Ljava/lang/Object;", "plusElement", "sort", "([Ljava/lang/Object;)V", "", "([Ljava/lang/Comparable;)V", "([Ljava/lang/Object;II)V", "sortWith", "([Ljava/lang/Object;Ljava/util/Comparator;)V", "([Ljava/lang/Object;Ljava/util/Comparator;II)V", "toSortedSet", "Ljava/util/SortedSet;", "([Ljava/lang/Comparable;)Ljava/util/SortedSet;", "([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/SortedSet;", "toTypedArray", "([Z)[Ljava/lang/Boolean;", "([B)[Ljava/lang/Byte;", "([C)[Ljava/lang/Character;", "([D)[Ljava/lang/Double;", "([F)[Ljava/lang/Float;", "([I)[Ljava/lang/Integer;", "([J)[Ljava/lang/Long;", "([S)[Ljava/lang/Short;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/collections/ArraysKt")
public class ArraysKt___ArraysJvmKt extends ArraysKt__ArraysKt {

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$1 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$1", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Byte;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11401 extends AbstractList<Byte> implements RandomAccess {
        final /* synthetic */ byte[] $this_asList;

        C11401(byte[] bArr) {
            this.$this_asList = bArr;
        }

        public boolean contains(byte element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Byte) {
                return contains(((Number) obj).byteValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Byte get(int index) {
            return Byte.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(byte element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Byte) {
                return indexOf(((Number) obj).byteValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(byte element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Byte) {
                return lastIndexOf(((Number) obj).byteValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$2 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$2", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Short;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11412 extends AbstractList<Short> implements RandomAccess {
        final /* synthetic */ short[] $this_asList;

        C11412(short[] sArr) {
            this.$this_asList = sArr;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Short) {
                return contains(((Number) obj).shortValue());
            }
            return false;
        }

        public boolean contains(short element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Short get(int index) {
            return Short.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Short) {
                return indexOf(((Number) obj).shortValue());
            }
            return -1;
        }

        public int indexOf(short element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Short) {
                return lastIndexOf(((Number) obj).shortValue());
            }
            return -1;
        }

        public int lastIndexOf(short element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$3 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002H\u0096\u0002¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\tH\u0016J\u0010\u0010\u0010\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$3", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Integer;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11423 extends AbstractList<Integer> implements RandomAccess {
        final /* synthetic */ int[] $this_asList;

        C11423(int[] iArr) {
            this.$this_asList = iArr;
        }

        public boolean contains(int element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Integer) {
                return contains(((Number) obj).intValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Integer get(int index) {
            return Integer.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(int element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Integer) {
                return indexOf(((Number) obj).intValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(int element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Integer) {
                return lastIndexOf(((Number) obj).intValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$4 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$4", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Long;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11434 extends AbstractList<Long> implements RandomAccess {
        final /* synthetic */ long[] $this_asList;

        C11434(long[] jArr) {
            this.$this_asList = jArr;
        }

        public boolean contains(long element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Long) {
                return contains(((Number) obj).longValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Long get(int index) {
            return Long.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(long element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Long) {
                return indexOf(((Number) obj).longValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(long element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Long) {
                return lastIndexOf(((Number) obj).longValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$5 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$5", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Float;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11445 extends AbstractList<Float> implements RandomAccess {
        final /* synthetic */ float[] $this_asList;

        C11445(float[] fArr) {
            this.$this_asList = fArr;
        }

        public boolean contains(float element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Float) {
                return contains(((Number) obj).floatValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Float get(int index) {
            return Float.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(float element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Float) {
                return indexOf(((Number) obj).floatValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(float element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Float) {
                return lastIndexOf(((Number) obj).floatValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$6 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$6", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Double;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11456 extends AbstractList<Double> implements RandomAccess {
        final /* synthetic */ double[] $this_asList;

        C11456(double[] dArr) {
            this.$this_asList = dArr;
        }

        public boolean contains(double element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Double) {
                return contains(((Number) obj).doubleValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Double get(int index) {
            return Double.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(double element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Double) {
                return indexOf(((Number) obj).doubleValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(double element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Double) {
                return lastIndexOf(((Number) obj).doubleValue());
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$7 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\u0002H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$7", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Boolean;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11467 extends AbstractList<Boolean> implements RandomAccess {
        final /* synthetic */ boolean[] $this_asList;

        C11467(boolean[] zArr) {
            this.$this_asList = zArr;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Boolean) {
                return contains(((Boolean) obj).booleanValue());
            }
            return false;
        }

        public boolean contains(boolean element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Boolean get(int index) {
            return Boolean.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Boolean) {
                return indexOf(((Boolean) obj).booleanValue());
            }
            return -1;
        }

        public int indexOf(boolean element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Boolean) {
                return lastIndexOf(((Boolean) obj).booleanValue());
            }
            return -1;
        }

        public int lastIndexOf(boolean element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }
    }

    /* JADX INFO: renamed from: kotlin.collections.ArraysKt___ArraysJvmKt$asList$8 */
    /* JADX INFO: compiled from: _ArraysJvm.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, m1984d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$8", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "get", FirebaseAnalytics.Param.INDEX, "(I)Ljava/lang/Character;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class C11478 extends AbstractList<Character> implements RandomAccess {
        final /* synthetic */ char[] $this_asList;

        C11478(char[] cArr) {
            this.$this_asList = cArr;
        }

        public boolean contains(char element) {
            return ArraysKt.contains(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Character) {
                return contains(((Character) obj).charValue());
            }
            return false;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        @NotNull
        public Character get(int index) {
            return Character.valueOf(this.$this_asList[index]);
        }

        @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
        /* JADX INFO: renamed from: getSize */
        public int get_size() {
            return this.$this_asList.length;
        }

        public int indexOf(char element) {
            return ArraysKt.indexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Character) {
                return indexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // kotlin.collections.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.$this_asList.length == 0;
        }

        public int lastIndexOf(char element) {
            return ArraysKt.lastIndexOf(this.$this_asList, element);
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Character) {
                return lastIndexOf(((Character) obj).charValue());
            }
            return -1;
        }
    }

    @NotNull
    public static final List<Byte> asList(@NotNull byte[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11401(asList);
    }

    @NotNull
    public static final List<Character> asList(@NotNull char[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11478(asList);
    }

    @NotNull
    public static final List<Double> asList(@NotNull double[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11456(asList);
    }

    @NotNull
    public static final List<Float> asList(@NotNull float[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11445(asList);
    }

    @NotNull
    public static final List<Integer> asList(@NotNull int[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11423(asList);
    }

    @NotNull
    public static final List<Long> asList(@NotNull long[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11434(asList);
    }

    @NotNull
    public static final <T> List<T> asList(@NotNull T[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        List<T> listAsList = ArraysUtilJVM.asList(asList);
        Intrinsics.checkExpressionValueIsNotNull(listAsList, "ArraysUtilJVM.asList(this)");
        return listAsList;
    }

    @NotNull
    public static final List<Short> asList(@NotNull short[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11412(asList);
    }

    @NotNull
    public static final List<Boolean> asList(@NotNull boolean[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new C11467(asList);
    }

    public static final int binarySearch(@NotNull byte[] binarySearch, byte b, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, b);
    }

    public static final int binarySearch(@NotNull char[] binarySearch, char c, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, c);
    }

    public static final int binarySearch(@NotNull double[] binarySearch, double d, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, d);
    }

    public static final int binarySearch(@NotNull float[] binarySearch, float f, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, f);
    }

    public static final int binarySearch(@NotNull int[] binarySearch, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i2, i3, i);
    }

    public static final int binarySearch(@NotNull long[] binarySearch, long j, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, j);
    }

    public static final <T> int binarySearch(@NotNull T[] binarySearch, T t, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, t);
    }

    public static final <T> int binarySearch(@NotNull T[] binarySearch, T t, @NotNull Comparator<? super T> comparator, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return Arrays.binarySearch(binarySearch, i, i2, t, comparator);
    }

    public static final int binarySearch(@NotNull short[] binarySearch, short s, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(binarySearch, "$this$binarySearch");
        return Arrays.binarySearch(binarySearch, i, i2, s);
    }

    public static /* synthetic */ int binarySearch$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return ArraysKt.binarySearch(bArr, b, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        return ArraysKt.binarySearch(cArr, c, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        return ArraysKt.binarySearch(dArr, d, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        return ArraysKt.binarySearch(fArr, f, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.binarySearch(iArr, i, i2, i3);
    }

    public static /* synthetic */ int binarySearch$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        return ArraysKt.binarySearch(jArr, j, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch(objArr, obj, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, Comparator comparator, int i, int i2, int i3, Object obj2) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch(objArr, obj, comparator, i, i2);
    }

    public static /* synthetic */ int binarySearch$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        return ArraysKt.binarySearch(sArr, s, i, i2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    @JvmName(name = "contentDeepEqualsInline")
    private static final <T> boolean contentDeepEqualsInline(@NotNull T[] tArr, T[] tArr2) {
        return PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0) ? ArraysKt.contentDeepEquals(tArr, tArr2) : Arrays.deepEquals(tArr, tArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    @JvmName(name = "contentDeepHashCodeInline")
    private static final <T> int contentDeepHashCodeInline(@NotNull T[] tArr) {
        return PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0) ? ArraysKt.contentDeepHashCode(tArr) : Arrays.deepHashCode(tArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    @JvmName(name = "contentDeepToStringInline")
    private static final <T> String contentDeepToStringInline(@NotNull T[] tArr) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepToString(tArr);
        }
        String strDeepToString = Arrays.deepToString(tArr);
        Intrinsics.checkExpressionValueIsNotNull(strDeepToString, "java.util.Arrays.deepToString(this)");
        return strDeepToString;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull char[] cArr, char[] cArr2) {
        return Arrays.equals(cArr, cArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull double[] dArr, double[] dArr2) {
        return Arrays.equals(dArr, dArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull float[] fArr, float[] fArr2) {
        return Arrays.equals(fArr, fArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull int[] iArr, int[] iArr2) {
        return Arrays.equals(iArr, iArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull long[] jArr, long[] jArr2) {
        return Arrays.equals(jArr, jArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final <T> boolean contentEquals(@NotNull T[] tArr, T[] tArr2) {
        return Arrays.equals(tArr, tArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull short[] sArr, short[] sArr2) {
        return Arrays.equals(sArr, sArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final boolean contentEquals(@NotNull boolean[] zArr, boolean[] zArr2) {
        return Arrays.equals(zArr, zArr2);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull byte[] bArr) {
        return Arrays.hashCode(bArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull char[] cArr) {
        return Arrays.hashCode(cArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull double[] dArr) {
        return Arrays.hashCode(dArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull float[] fArr) {
        return Arrays.hashCode(fArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull int[] iArr) {
        return Arrays.hashCode(iArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull long[] jArr) {
        return Arrays.hashCode(jArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final <T> int contentHashCode(@NotNull T[] tArr) {
        return Arrays.hashCode(tArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull short[] sArr) {
        return Arrays.hashCode(sArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final int contentHashCode(@NotNull boolean[] zArr) {
        return Arrays.hashCode(zArr);
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull byte[] bArr) {
        String string = Arrays.toString(bArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull char[] cArr) {
        String string = Arrays.toString(cArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull double[] dArr) {
        String string = Arrays.toString(dArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull float[] fArr) {
        String string = Arrays.toString(fArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull int[] iArr) {
        String string = Arrays.toString(iArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull long[] jArr) {
        String string = Arrays.toString(jArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final <T> String contentToString(@NotNull T[] tArr) {
        String string = Arrays.toString(tArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull short[] sArr) {
        String string = Arrays.toString(sArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = BuildConfig.VERSION_NAME)
    @InlineOnly
    private static final String contentToString(@NotNull boolean[] zArr) {
        String string = Arrays.toString(zArr);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final byte[] copyInto(@NotNull byte[] copyInto, @NotNull byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final char[] copyInto(@NotNull char[] copyInto, @NotNull char[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final double[] copyInto(@NotNull double[] copyInto, @NotNull double[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final float[] copyInto(@NotNull float[] copyInto, @NotNull float[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final int[] copyInto(@NotNull int[] copyInto, @NotNull int[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final long[] copyInto(@NotNull long[] copyInto, @NotNull long[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> T[] copyInto(@NotNull T[] copyInto, @NotNull T[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final short[] copyInto(@NotNull short[] copyInto, @NotNull short[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final boolean[] copyInto(@NotNull boolean[] copyInto, @NotNull boolean[] destination, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy(copyInto, i2, destination, i, i3 - i2);
        return destination;
    }

    public static /* synthetic */ byte[] copyInto$default(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = bArr.length;
        }
        return ArraysKt.copyInto(bArr, bArr2, i, i2, i3);
    }

    public static /* synthetic */ char[] copyInto$default(char[] cArr, char[] cArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = cArr.length;
        }
        return ArraysKt.copyInto(cArr, cArr2, i, i2, i3);
    }

    public static /* synthetic */ double[] copyInto$default(double[] dArr, double[] dArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = dArr.length;
        }
        return ArraysKt.copyInto(dArr, dArr2, i, i2, i3);
    }

    public static /* synthetic */ float[] copyInto$default(float[] fArr, float[] fArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = fArr.length;
        }
        return ArraysKt.copyInto(fArr, fArr2, i, i2, i3);
    }

    public static /* synthetic */ int[] copyInto$default(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.copyInto(iArr, iArr2, i, i2, i3);
    }

    public static /* synthetic */ long[] copyInto$default(long[] jArr, long[] jArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = jArr.length;
        }
        return ArraysKt.copyInto(jArr, jArr2, i, i2, i3);
    }

    public static /* synthetic */ Object[] copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        return ArraysKt.copyInto(objArr, objArr2, i, i2, i3);
    }

    public static /* synthetic */ short[] copyInto$default(short[] sArr, short[] sArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = sArr.length;
        }
        return ArraysKt.copyInto(sArr, sArr2, i, i2, i3);
    }

    public static /* synthetic */ boolean[] copyInto$default(boolean[] zArr, boolean[] zArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = zArr.length;
        }
        return ArraysKt.copyInto(zArr, zArr2, i, i2, i3);
    }

    @InlineOnly
    private static final byte[] copyOf(@NotNull byte[] bArr) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return bArrCopyOf;
    }

    @InlineOnly
    private static final byte[] copyOf(@NotNull byte[] bArr, int i) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, i);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return bArrCopyOf;
    }

    @InlineOnly
    private static final char[] copyOf(@NotNull char[] cArr) {
        char[] cArrCopyOf = Arrays.copyOf(cArr, cArr.length);
        Intrinsics.checkExpressionValueIsNotNull(cArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return cArrCopyOf;
    }

    @InlineOnly
    private static final char[] copyOf(@NotNull char[] cArr, int i) {
        char[] cArrCopyOf = Arrays.copyOf(cArr, i);
        Intrinsics.checkExpressionValueIsNotNull(cArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return cArrCopyOf;
    }

    @InlineOnly
    private static final double[] copyOf(@NotNull double[] dArr) {
        double[] dArrCopyOf = Arrays.copyOf(dArr, dArr.length);
        Intrinsics.checkExpressionValueIsNotNull(dArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return dArrCopyOf;
    }

    @InlineOnly
    private static final double[] copyOf(@NotNull double[] dArr, int i) {
        double[] dArrCopyOf = Arrays.copyOf(dArr, i);
        Intrinsics.checkExpressionValueIsNotNull(dArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return dArrCopyOf;
    }

    @InlineOnly
    private static final float[] copyOf(@NotNull float[] fArr) {
        float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
        Intrinsics.checkExpressionValueIsNotNull(fArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return fArrCopyOf;
    }

    @InlineOnly
    private static final float[] copyOf(@NotNull float[] fArr, int i) {
        float[] fArrCopyOf = Arrays.copyOf(fArr, i);
        Intrinsics.checkExpressionValueIsNotNull(fArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return fArrCopyOf;
    }

    @InlineOnly
    private static final int[] copyOf(@NotNull int[] iArr) {
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return iArrCopyOf;
    }

    @InlineOnly
    private static final int[] copyOf(@NotNull int[] iArr, int i) {
        int[] iArrCopyOf = Arrays.copyOf(iArr, i);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return iArrCopyOf;
    }

    @InlineOnly
    private static final long[] copyOf(@NotNull long[] jArr) {
        long[] jArrCopyOf = Arrays.copyOf(jArr, jArr.length);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return jArrCopyOf;
    }

    @InlineOnly
    private static final long[] copyOf(@NotNull long[] jArr, int i) {
        long[] jArrCopyOf = Arrays.copyOf(jArr, i);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return jArrCopyOf;
    }

    @InlineOnly
    private static final <T> T[] copyOf(@NotNull T[] tArr) {
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, tArr.length);
        Intrinsics.checkExpressionValueIsNotNull(tArr2, "java.util.Arrays.copyOf(this, size)");
        return tArr2;
    }

    @InlineOnly
    private static final <T> T[] copyOf(@NotNull T[] tArr, int i) {
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, i);
        Intrinsics.checkExpressionValueIsNotNull(tArr2, "java.util.Arrays.copyOf(this, newSize)");
        return tArr2;
    }

    @InlineOnly
    private static final short[] copyOf(@NotNull short[] sArr) {
        short[] sArrCopyOf = Arrays.copyOf(sArr, sArr.length);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return sArrCopyOf;
    }

    @InlineOnly
    private static final short[] copyOf(@NotNull short[] sArr, int i) {
        short[] sArrCopyOf = Arrays.copyOf(sArr, i);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return sArrCopyOf;
    }

    @InlineOnly
    private static final boolean[] copyOf(@NotNull boolean[] zArr) {
        boolean[] zArrCopyOf = Arrays.copyOf(zArr, zArr.length);
        Intrinsics.checkExpressionValueIsNotNull(zArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return zArrCopyOf;
    }

    @InlineOnly
    private static final boolean[] copyOf(@NotNull boolean[] zArr, int i) {
        boolean[] zArrCopyOf = Arrays.copyOf(zArr, i);
        Intrinsics.checkExpressionValueIsNotNull(zArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return zArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final byte[] copyOfRange(@NotNull byte[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        byte[] bArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return bArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final char[] copyOfRange(@NotNull char[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        char[] cArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(cArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return cArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final double[] copyOfRange(@NotNull double[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        double[] dArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(dArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return dArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final float[] copyOfRange(@NotNull float[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        float[] fArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(fArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return fArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final int[] copyOfRange(@NotNull int[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        int[] iArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return iArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final long[] copyOfRange(@NotNull long[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        long[] jArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return jArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final <T> T[] copyOfRange(@NotNull T[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        T[] tArr = (T[]) Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(tArr, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return tArr;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final short[] copyOfRange(@NotNull short[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        short[] sArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return sArrCopyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final boolean[] copyOfRange(@NotNull boolean[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        boolean[] zArrCopyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(zArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return zArrCopyOfRange;
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final byte[] copyOfRangeInline(@NotNull byte[] bArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(bArr, i, i2);
        }
        if (i2 <= bArr.length) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(bArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return bArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + bArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final char[] copyOfRangeInline(@NotNull char[] cArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(cArr, i, i2);
        }
        if (i2 <= cArr.length) {
            char[] cArrCopyOfRange = Arrays.copyOfRange(cArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(cArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return cArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + cArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final double[] copyOfRangeInline(@NotNull double[] dArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(dArr, i, i2);
        }
        if (i2 <= dArr.length) {
            double[] dArrCopyOfRange = Arrays.copyOfRange(dArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(dArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return dArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + dArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final float[] copyOfRangeInline(@NotNull float[] fArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(fArr, i, i2);
        }
        if (i2 <= fArr.length) {
            float[] fArrCopyOfRange = Arrays.copyOfRange(fArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(fArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return fArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + fArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final int[] copyOfRangeInline(@NotNull int[] iArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(iArr, i, i2);
        }
        if (i2 <= iArr.length) {
            int[] iArrCopyOfRange = Arrays.copyOfRange(iArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(iArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return iArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + iArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final long[] copyOfRangeInline(@NotNull long[] jArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(jArr, i, i2);
        }
        if (i2 <= jArr.length) {
            long[] jArrCopyOfRange = Arrays.copyOfRange(jArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(jArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return jArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + jArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final <T> T[] copyOfRangeInline(@NotNull T[] tArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return (T[]) ArraysKt.copyOfRange(tArr, i, i2);
        }
        if (i2 <= tArr.length) {
            T[] tArr2 = (T[]) Arrays.copyOfRange(tArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(tArr2, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return tArr2;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + tArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final short[] copyOfRangeInline(@NotNull short[] sArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(sArr, i, i2);
        }
        if (i2 <= sArr.length) {
            short[] sArrCopyOfRange = Arrays.copyOfRange(sArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(sArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return sArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + sArr.length);
    }

    @InlineOnly
    @JvmName(name = "copyOfRangeInline")
    private static final boolean[] copyOfRangeInline(@NotNull boolean[] zArr, int i, int i2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange(zArr, i, i2);
        }
        if (i2 <= zArr.length) {
            boolean[] zArrCopyOfRange = Arrays.copyOfRange(zArr, i, i2);
            Intrinsics.checkExpressionValueIsNotNull(zArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return zArrCopyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex: " + i2 + ", size: " + zArr.length);
    }

    @InlineOnly
    private static final byte elementAt(@NotNull byte[] bArr, int i) {
        return bArr[i];
    }

    @InlineOnly
    private static final char elementAt(@NotNull char[] cArr, int i) {
        return cArr[i];
    }

    @InlineOnly
    private static final double elementAt(@NotNull double[] dArr, int i) {
        return dArr[i];
    }

    @InlineOnly
    private static final float elementAt(@NotNull float[] fArr, int i) {
        return fArr[i];
    }

    @InlineOnly
    private static final int elementAt(@NotNull int[] iArr, int i) {
        return iArr[i];
    }

    @InlineOnly
    private static final long elementAt(@NotNull long[] jArr, int i) {
        return jArr[i];
    }

    @InlineOnly
    private static final <T> T elementAt(@NotNull T[] tArr, int i) {
        return tArr[i];
    }

    @InlineOnly
    private static final short elementAt(@NotNull short[] sArr, int i) {
        return sArr[i];
    }

    @InlineOnly
    private static final boolean elementAt(@NotNull boolean[] zArr, int i) {
        return zArr[i];
    }

    public static final void fill(@NotNull byte[] fill, byte b, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, b);
    }

    public static final void fill(@NotNull char[] fill, char c, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, c);
    }

    public static final void fill(@NotNull double[] fill, double d, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, d);
    }

    public static final void fill(@NotNull float[] fill, float f, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, f);
    }

    public static final void fill(@NotNull int[] fill, int i, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i2, i3, i);
    }

    public static final void fill(@NotNull long[] fill, long j, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, j);
    }

    public static final <T> void fill(@NotNull T[] fill, T t, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, t);
    }

    public static final void fill(@NotNull short[] fill, short s, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, s);
    }

    public static final void fill(@NotNull boolean[] fill, boolean z, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(fill, "$this$fill");
        Arrays.fill(fill, i, i2, z);
    }

    public static /* synthetic */ void fill$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.fill(bArr, b, i, i2);
    }

    public static /* synthetic */ void fill$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.fill(cArr, c, i, i2);
    }

    public static /* synthetic */ void fill$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.fill(dArr, d, i, i2);
    }

    public static /* synthetic */ void fill$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.fill(fArr, f, i, i2);
    }

    public static /* synthetic */ void fill$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        ArraysKt.fill(iArr, i, i2, i3);
    }

    public static /* synthetic */ void fill$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.fill(jArr, j, i, i2);
    }

    public static /* synthetic */ void fill$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.fill(objArr, obj, i, i2);
    }

    public static /* synthetic */ void fill$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.fill(sArr, s, i, i2);
    }

    public static /* synthetic */ void fill$default(boolean[] zArr, boolean z, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = zArr.length;
        }
        ArraysKt.fill(zArr, z, i, i2);
    }

    @NotNull
    public static final <R> List<R> filterIsInstance(@NotNull Object[] filterIsInstance, @NotNull Class<R> klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstance, "$this$filterIsInstance");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        return (List) ArraysKt.filterIsInstanceTo(filterIsInstance, new ArrayList(), klass);
    }

    @NotNull
    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(@NotNull Object[] filterIsInstanceTo, @NotNull C destination, @NotNull Class<R> klass) {
        Intrinsics.checkParameterIsNotNull(filterIsInstanceTo, "$this$filterIsInstanceTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        for (Object obj : filterIsInstanceTo) {
            if (klass.isInstance(obj)) {
                destination.add(obj);
            }
        }
        return destination;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] plus, byte b) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        byte[] result = Arrays.copyOf(plus, length + 1);
        result[length] = b;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] plus, @NotNull Collection<Byte> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        byte[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Byte> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().byteValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] plus, @NotNull byte[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        byte[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] plus, char c) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        char[] result = Arrays.copyOf(plus, length + 1);
        result[length] = c;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] plus, @NotNull Collection<Character> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        char[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Character> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().charValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] plus, @NotNull char[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        char[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] plus, double d) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        double[] result = Arrays.copyOf(plus, length + 1);
        result[length] = d;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] plus, @NotNull Collection<Double> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        double[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Double> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().doubleValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] plus, @NotNull double[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        double[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] plus, float f) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        float[] result = Arrays.copyOf(plus, length + 1);
        result[length] = f;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] plus, @NotNull Collection<Float> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        float[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Float> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().floatValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] plus, @NotNull float[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        float[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] plus, int i) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        int[] result = Arrays.copyOf(plus, length + 1);
        result[length] = i;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] plus, @NotNull Collection<Integer> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Integer> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().intValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] plus, @NotNull int[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        int[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] plus, long j) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        long[] result = Arrays.copyOf(plus, length + 1);
        result[length] = j;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] plus, @NotNull Collection<Long> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        long[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Long> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().longValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] plus, @NotNull long[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        long[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] plus, T t) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        T[] result = (T[]) Arrays.copyOf(plus, length + 1);
        result[length] = t;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] plus, @NotNull Collection<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        T[] result = (T[]) Arrays.copyOf(plus, elements.size() + length);
        Iterator<? extends T> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] plus, @NotNull T[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        T[] result = (T[]) Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] plus, @NotNull Collection<Short> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        short[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Short> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().shortValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] plus, short s) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        short[] result = Arrays.copyOf(plus, length + 1);
        result[length] = s;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] plus, @NotNull short[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        short[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] plus, @NotNull Collection<Boolean> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        boolean[] result = Arrays.copyOf(plus, elements.size() + length);
        Iterator<Boolean> it = elements.iterator();
        while (it.hasNext()) {
            result[length] = it.next().booleanValue();
            length++;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] plus, boolean z) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        int length = plus.length;
        boolean[] result = Arrays.copyOf(plus, length + 1);
        result[length] = z;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] plus, @NotNull boolean[] elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int length = plus.length;
        int length2 = elements.length;
        boolean[] result = Arrays.copyOf(plus, length + length2);
        System.arraycopy(elements, 0, result, length, length2);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @InlineOnly
    private static final <T> T[] plusElement(@NotNull T[] tArr, T t) {
        return (T[]) ArraysKt.plus(tArr, t);
    }

    public static final void sort(@NotNull byte[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull byte[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull char[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull char[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull double[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull double[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull float[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull float[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull int[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull int[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull long[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull long[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    @InlineOnly
    private static final <T extends Comparable<? super T>> void sort(@NotNull T[] tArr) {
        if (tArr == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        ArraysKt.sort((Object[]) tArr);
    }

    public static final <T> void sort(@NotNull T[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final <T> void sort(@NotNull T[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static final void sort(@NotNull short[] sort) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        if (sort.length > 1) {
            Arrays.sort(sort);
        }
    }

    public static final void sort(@NotNull short[] sort, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sort, "$this$sort");
        Arrays.sort(sort, i, i2);
    }

    public static /* synthetic */ void sort$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.sort(bArr, i, i2);
    }

    public static /* synthetic */ void sort$default(char[] cArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.sort(cArr, i, i2);
    }

    public static /* synthetic */ void sort$default(double[] dArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.sort(dArr, i, i2);
    }

    public static /* synthetic */ void sort$default(float[] fArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.sort(fArr, i, i2);
    }

    public static /* synthetic */ void sort$default(int[] iArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = iArr.length;
        }
        ArraysKt.sort(iArr, i, i2);
    }

    public static /* synthetic */ void sort$default(long[] jArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.sort(jArr, i, i2);
    }

    public static /* synthetic */ void sort$default(Object[] objArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sort(objArr, i, i2);
    }

    public static /* synthetic */ void sort$default(short[] sArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.sort(sArr, i, i2);
    }

    public static final <T> void sortWith(@NotNull T[] sortWith, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull(sortWith, "$this$sortWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if (sortWith.length > 1) {
            Arrays.sort(sortWith, comparator);
        }
    }

    public static final <T> void sortWith(@NotNull T[] sortWith, @NotNull Comparator<? super T> comparator, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(sortWith, "$this$sortWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        Arrays.sort(sortWith, i, i2, comparator);
    }

    public static /* synthetic */ void sortWith$default(Object[] objArr, Comparator comparator, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sortWith(objArr, comparator, i, i2);
    }

    @NotNull
    public static final SortedSet<Byte> toSortedSet(@NotNull byte[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Character> toSortedSet(@NotNull char[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Double> toSortedSet(@NotNull double[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Float> toSortedSet(@NotNull float[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Integer> toSortedSet(@NotNull int[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Long> toSortedSet(@NotNull long[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(@NotNull T[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final <T> SortedSet<T> toSortedSet(@NotNull T[] toSortedSet, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet(comparator));
    }

    @NotNull
    public static final SortedSet<Short> toSortedSet(@NotNull short[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Boolean> toSortedSet(@NotNull boolean[] toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection(toSortedSet, new TreeSet());
    }

    @NotNull
    public static final Boolean[] toTypedArray(@NotNull boolean[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Boolean[] boolArr = new Boolean[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            boolArr[i] = Boolean.valueOf(toTypedArray[i]);
        }
        return boolArr;
    }

    @NotNull
    public static final Byte[] toTypedArray(@NotNull byte[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Byte[] bArr = new Byte[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            bArr[i] = Byte.valueOf(toTypedArray[i]);
        }
        return bArr;
    }

    @NotNull
    public static final Character[] toTypedArray(@NotNull char[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Character[] chArr = new Character[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            chArr[i] = Character.valueOf(toTypedArray[i]);
        }
        return chArr;
    }

    @NotNull
    public static final Double[] toTypedArray(@NotNull double[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Double[] dArr = new Double[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            dArr[i] = Double.valueOf(toTypedArray[i]);
        }
        return dArr;
    }

    @NotNull
    public static final Float[] toTypedArray(@NotNull float[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Float[] fArr = new Float[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            fArr[i] = Float.valueOf(toTypedArray[i]);
        }
        return fArr;
    }

    @NotNull
    public static final Integer[] toTypedArray(@NotNull int[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Integer[] numArr = new Integer[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(toTypedArray[i]);
        }
        return numArr;
    }

    @NotNull
    public static final Long[] toTypedArray(@NotNull long[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Long[] lArr = new Long[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            lArr[i] = Long.valueOf(toTypedArray[i]);
        }
        return lArr;
    }

    @NotNull
    public static final Short[] toTypedArray(@NotNull short[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        Short[] shArr = new Short[toTypedArray.length];
        int length = toTypedArray.length;
        for (int i = 0; i < length; i++) {
            shArr[i] = Short.valueOf(toTypedArray[i]);
        }
        return shArr;
    }
}
