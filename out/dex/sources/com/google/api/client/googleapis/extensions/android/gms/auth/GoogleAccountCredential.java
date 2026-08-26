package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class GoogleAccountCredential implements HttpRequestInitializer {
    private final GoogleAccountManager accountManager;
    private String accountName;
    private BackOff backOff;
    final Context context;
    final String scope;
    private Account selectedAccount;
    private Sleeper sleeper = Sleeper.DEFAULT;

    @Beta
    class RequestHandler implements HttpExecuteInterceptor, HttpUnsuccessfulResponseHandler {
        boolean received401;
        String token;

        RequestHandler() {
        }

        @Override // com.google.api.client.http.HttpUnsuccessfulResponseHandler
        public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean z) {
            if (httpResponse.getStatusCode() != 401 || this.received401) {
                return false;
            }
            this.received401 = true;
            GoogleAuthUtil.invalidateToken(GoogleAccountCredential.this.context, this.token);
            return true;
        }

        @Override // com.google.api.client.http.HttpExecuteInterceptor
        public void intercept(HttpRequest httpRequest) throws IOException {
            try {
                this.token = GoogleAccountCredential.this.getToken();
                httpRequest.getHeaders().setAuthorization("Bearer " + this.token);
            } catch (GoogleAuthException e) {
                throw new GoogleAuthIOException(e);
            } catch (GooglePlayServicesAvailabilityException e2) {
                throw new GooglePlayServicesAvailabilityIOException(e2);
            } catch (UserRecoverableAuthException e3) {
                throw new UserRecoverableAuthIOException(e3);
            }
        }
    }

    public GoogleAccountCredential(Context context, String str) {
        this.accountManager = new GoogleAccountManager(context);
        this.context = context;
        this.scope = str;
    }

    public static GoogleAccountCredential usingAudience(Context context, String str) {
        Preconditions.checkArgument(str.length() != 0);
        return new GoogleAccountCredential(context, "audience:" + str);
    }

    public static GoogleAccountCredential usingOAuth2(Context context, Collection<String> collection) {
        Preconditions.checkArgument(collection != null && collection.iterator().hasNext());
        return new GoogleAccountCredential(context, "oauth2: " + Joiner.m459on(' ').join(collection));
    }

    public final Account[] getAllAccounts() {
        return this.accountManager.getAccounts();
    }

    public BackOff getBackOff() {
        return this.backOff;
    }

    public final Context getContext() {
        return this.context;
    }

    public final GoogleAccountManager getGoogleAccountManager() {
        return this.accountManager;
    }

    public final String getScope() {
        return this.scope;
    }

    public final Account getSelectedAccount() {
        return this.selectedAccount;
    }

    public final String getSelectedAccountName() {
        return this.accountName;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String getToken() throws IOException, GoogleAuthException {
        BackOff backOff = this.backOff;
        if (backOff != null) {
            backOff.reset();
        }
        while (true) {
            try {
                return GoogleAuthUtil.getToken(this.context, this.accountName, this.scope);
            } catch (IOException e) {
                try {
                    if (this.backOff == null || !BackOffUtils.next(this.sleeper, this.backOff)) {
                        throw e;
                    }
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    @Override // com.google.api.client.http.HttpRequestInitializer
    public void initialize(HttpRequest httpRequest) {
        RequestHandler requestHandler = new RequestHandler();
        httpRequest.setInterceptor(requestHandler);
        httpRequest.setUnsuccessfulResponseHandler(requestHandler);
    }

    public final Intent newChooseAccountIntent() {
        return AccountPicker.newChooseAccountIntent(this.selectedAccount, null, new String[]{"com.google"}, true, null, null, null, null);
    }

    public GoogleAccountCredential setBackOff(BackOff backOff) {
        this.backOff = backOff;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccount(Account account) {
        this.selectedAccount = account;
        this.accountName = account == null ? null : account.name;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccountName(String str) {
        this.selectedAccount = this.accountManager.getAccountByName(str);
        if (this.selectedAccount == null) {
            str = null;
        }
        this.accountName = str;
        return this;
    }

    public final GoogleAccountCredential setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }
}
