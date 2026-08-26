package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SafeParcelable.Class(creator = "GoogleSignInOptionsCreator")
public class GoogleSignInOptions extends AbstractSafeParcelable implements Api.ApiOptions.Optional, ReflectedParcelable {

    @SafeParcelable.VersionField(m432id = 1)
    private final int versionCode;

    @SafeParcelable.Field(getter = "isForceCodeForRefreshToken", m430id = 6)
    private final boolean zaaa;

    @SafeParcelable.Field(getter = "getServerClientId", m430id = 7)
    private String zaab;

    @SafeParcelable.Field(getter = "getHostedDomain", m430id = 8)
    private String zaac;

    @SafeParcelable.Field(getter = "getExtensions", m430id = 9)
    private ArrayList<GoogleSignInOptionsExtensionParcelable> zaad;
    private Map<Integer, GoogleSignInOptionsExtensionParcelable> zaae;

    @SafeParcelable.Field(getter = "getScopes", m430id = 2)
    private final ArrayList<Scope> zaw;

    @SafeParcelable.Field(getter = "getAccount", m430id = 3)
    private Account zax;

    @SafeParcelable.Field(getter = "isIdTokenRequested", m430id = 4)
    private boolean zay;

    @SafeParcelable.Field(getter = "isServerAuthCodeRequested", m430id = 5)
    private final boolean zaz;

    @VisibleForTesting
    public static final Scope zar = new Scope(Scopes.PROFILE);

    @VisibleForTesting
    public static final Scope zas = new Scope("email");

    @VisibleForTesting
    public static final Scope zat = new Scope(Scopes.OPEN_ID);

    @VisibleForTesting
    public static final Scope zau = new Scope(Scopes.GAMES_LITE);

    @VisibleForTesting
    public static final Scope zav = new Scope(Scopes.GAMES);
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(zau, new Scope[0]).build();
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR = new zad();
    private static Comparator<Scope> zaaf = new zac();

    public static final class Builder {
        private Set<Scope> mScopes;
        private boolean zaaa;
        private String zaab;
        private String zaac;
        private Map<Integer, GoogleSignInOptionsExtensionParcelable> zaag;
        private Account zax;
        private boolean zay;
        private boolean zaz;

        public Builder() {
            this.mScopes = new HashSet();
            this.zaag = new HashMap();
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            this.mScopes = new HashSet();
            this.zaag = new HashMap();
            Preconditions.checkNotNull(googleSignInOptions);
            this.mScopes = new HashSet(googleSignInOptions.zaw);
            this.zaz = googleSignInOptions.zaz;
            this.zaaa = googleSignInOptions.zaaa;
            this.zay = googleSignInOptions.zay;
            this.zaab = googleSignInOptions.zaab;
            this.zax = googleSignInOptions.zax;
            this.zaac = googleSignInOptions.zaac;
            this.zaag = GoogleSignInOptions.zaa(googleSignInOptions.zaad);
        }

        private final String zac(String str) {
            Preconditions.checkNotEmpty(str);
            String str2 = this.zaab;
            Preconditions.checkArgument(str2 == null || str2.equals(str), "two different server client ids provided");
            return str;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zaag.containsKey(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()))) {
                throw new IllegalStateException("Only one extension per type may be added");
            }
            if (googleSignInOptionsExtension.getImpliedScopes() != null) {
                this.mScopes.addAll(googleSignInOptionsExtension.getImpliedScopes());
            }
            this.zaag.put(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()), new GoogleSignInOptionsExtensionParcelable(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.mScopes.contains(GoogleSignInOptions.zav) && this.mScopes.contains(GoogleSignInOptions.zau)) {
                this.mScopes.remove(GoogleSignInOptions.zau);
            }
            if (this.zay && (this.zax == null || !this.mScopes.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(3, new ArrayList(this.mScopes), this.zax, this.zay, this.zaz, this.zaaa, this.zaab, this.zaac, this.zaag, null);
        }

        public final Builder requestEmail() {
            this.mScopes.add(GoogleSignInOptions.zas);
            return this;
        }

        public final Builder requestId() {
            this.mScopes.add(GoogleSignInOptions.zat);
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zay = true;
            this.zaab = zac(str);
            return this;
        }

        public final Builder requestProfile() {
            this.mScopes.add(GoogleSignInOptions.zar);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.mScopes.add(scope);
            this.mScopes.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder requestServerAuthCode(String str, boolean z) {
            this.zaz = true;
            this.zaab = zac(str);
            this.zaaa = z;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zax = new Account(Preconditions.checkNotEmpty(str), "com.google");
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zaac = Preconditions.checkNotEmpty(str);
            return this;
        }
    }

    @SafeParcelable.Constructor
    GoogleSignInOptions(@SafeParcelable.Param(m431id = 1) int i, @SafeParcelable.Param(m431id = 2) ArrayList<Scope> arrayList, @SafeParcelable.Param(m431id = 3) Account account, @SafeParcelable.Param(m431id = 4) boolean z, @SafeParcelable.Param(m431id = 5) boolean z2, @SafeParcelable.Param(m431id = 6) boolean z3, @SafeParcelable.Param(m431id = 7) String str, @SafeParcelable.Param(m431id = 8) String str2, @SafeParcelable.Param(m431id = 9) ArrayList<GoogleSignInOptionsExtensionParcelable> arrayList2) {
        this(i, arrayList, account, z, z2, z3, str, str2, zaa(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, GoogleSignInOptionsExtensionParcelable> map) {
        this.versionCode = i;
        this.zaw = arrayList;
        this.zax = account;
        this.zay = z;
        this.zaz = z2;
        this.zaaa = z3;
        this.zaab = str;
        this.zaac = str2;
        this.zaad = new ArrayList<>(map.values());
        this.zaae = map;
    }

    /* synthetic */ GoogleSignInOptions(int i, ArrayList arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map map, zac zacVar) {
        this(3, (ArrayList<Scope>) arrayList, account, z, z2, z3, str, str2, (Map<Integer, GoogleSignInOptionsExtensionParcelable>) map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<Integer, GoogleSignInOptionsExtensionParcelable> zaa(@Nullable List<GoogleSignInOptionsExtensionParcelable> list) {
        HashMap map = new HashMap();
        if (list == null) {
            return map;
        }
        for (GoogleSignInOptionsExtensionParcelable googleSignInOptionsExtensionParcelable : list) {
            map.put(Integer.valueOf(googleSignInOptionsExtensionParcelable.getType()), googleSignInOptionsExtensionParcelable);
        }
        return map;
    }

    @Nullable
    public static GoogleSignInOptions zab(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String strOptString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, (ArrayList<Scope>) new ArrayList(hashSet), !TextUtils.isEmpty(strOptString) ? new Account(strOptString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final JSONObject zad() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zaw, zaaf);
            ArrayList<Scope> arrayList = this.zaw;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Scope scope = arrayList.get(i);
                i++;
                jSONArray.put(scope.getScopeUri());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zax != null) {
                jSONObject.put("accountName", this.zax.name);
            }
            jSONObject.put("idTokenRequested", this.zay);
            jSONObject.put("forceCodeForRefreshToken", this.zaaa);
            jSONObject.put("serverAuthRequested", this.zaz);
            if (!TextUtils.isEmpty(this.zaab)) {
                jSONObject.put("serverClientId", this.zaab);
            }
            if (!TextUtils.isEmpty(this.zaac)) {
                jSONObject.put("hostedDomain", this.zaac);
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0054 A[Catch: ClassCastException -> 0x0085, TryCatch #0 {ClassCastException -> 0x0085, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004c, B:24:0x0054, B:29:0x006b, B:31:0x0073, B:33:0x007b, B:27:0x005f, B:20:0x0040), top: B:39:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x005e  */
    /* JADX WARN: Code duplicated, block: B:27:0x005f A[Catch: ClassCastException -> 0x0085, TryCatch #0 {ClassCastException -> 0x0085, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004c, B:24:0x0054, B:29:0x006b, B:31:0x0073, B:33:0x007b, B:27:0x005f, B:20:0x0040), top: B:39:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x006b A[Catch: ClassCastException -> 0x0085, TryCatch #0 {ClassCastException -> 0x0085, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004c, B:24:0x0054, B:29:0x006b, B:31:0x0073, B:33:0x007b, B:27:0x005f, B:20:0x0040), top: B:39:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0073 A[Catch: ClassCastException -> 0x0085, TryCatch #0 {ClassCastException -> 0x0085, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004c, B:24:0x0054, B:29:0x006b, B:31:0x0073, B:33:0x007b, B:27:0x005f, B:20:0x0040), top: B:39:0x0004 }] */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
            if (this.zaad.size() <= 0 && googleSignInOptions.zaad.size() <= 0 && this.zaw.size() == googleSignInOptions.getScopes().size() && this.zaw.containsAll(googleSignInOptions.getScopes())) {
                if (this.zax == null) {
                    if (googleSignInOptions.getAccount() == null) {
                        if (TextUtils.isEmpty(this.zaab)) {
                            if (TextUtils.isEmpty(googleSignInOptions.getServerClientId())) {
                                if (this.zaaa != googleSignInOptions.isForceCodeForRefreshToken() && this.zay == googleSignInOptions.isIdTokenRequested() && this.zaz == googleSignInOptions.isServerAuthCodeRequested()) {
                                    return true;
                                }
                            }
                        } else if (this.zaab.equals(googleSignInOptions.getServerClientId())) {
                            if (this.zaaa != googleSignInOptions.isForceCodeForRefreshToken()) {
                            }
                        }
                    }
                } else if (this.zax.equals(googleSignInOptions.getAccount())) {
                    if (TextUtils.isEmpty(this.zaab)) {
                        if (TextUtils.isEmpty(googleSignInOptions.getServerClientId())) {
                            if (this.zaaa != googleSignInOptions.isForceCodeForRefreshToken()) {
                            }
                        }
                    } else if (this.zaab.equals(googleSignInOptions.getServerClientId())) {
                        if (this.zaaa != googleSignInOptions.isForceCodeForRefreshToken()) {
                        }
                    }
                }
            }
        } catch (ClassCastException unused) {
        }
        return false;
    }

    @KeepForSdk
    public Account getAccount() {
        return this.zax;
    }

    @KeepForSdk
    public ArrayList<GoogleSignInOptionsExtensionParcelable> getExtensions() {
        return this.zaad;
    }

    public Scope[] getScopeArray() {
        ArrayList<Scope> arrayList = this.zaw;
        return (Scope[]) arrayList.toArray(new Scope[arrayList.size()]);
    }

    @KeepForSdk
    public ArrayList<Scope> getScopes() {
        return new ArrayList<>(this.zaw);
    }

    @KeepForSdk
    public String getServerClientId() {
        return this.zaab;
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList<Scope> arrayList2 = this.zaw;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Scope scope = arrayList2.get(i);
            i++;
            arrayList.add(scope.getScopeUri());
        }
        Collections.sort(arrayList);
        return new HashAccumulator().addObject(arrayList).addObject(this.zax).addObject(this.zaab).zaa(this.zaaa).zaa(this.zay).zaa(this.zaz).hash();
    }

    @KeepForSdk
    public boolean isForceCodeForRefreshToken() {
        return this.zaaa;
    }

    @KeepForSdk
    public boolean isIdTokenRequested() {
        return this.zay;
    }

    @KeepForSdk
    public boolean isServerAuthCodeRequested() {
        return this.zaz;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeTypedList(parcel, 2, getScopes(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getAccount(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 4, isIdTokenRequested());
        SafeParcelWriter.writeBoolean(parcel, 5, isServerAuthCodeRequested());
        SafeParcelWriter.writeBoolean(parcel, 6, isForceCodeForRefreshToken());
        SafeParcelWriter.writeString(parcel, 7, getServerClientId(), false);
        SafeParcelWriter.writeString(parcel, 8, this.zaac, false);
        SafeParcelWriter.writeTypedList(parcel, 9, getExtensions(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zae() {
        return zad().toString();
    }
}
