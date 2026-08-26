package com.google.api.client.googleapis.extensions.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class GoogleAccountManager {
    public static final String ACCOUNT_TYPE = "com.google";
    private final AccountManager manager;

    public GoogleAccountManager(AccountManager accountManager) {
        this.manager = (AccountManager) Preconditions.checkNotNull(accountManager);
    }

    public GoogleAccountManager(Context context) {
        this(AccountManager.get(context));
    }

    public Account getAccountByName(String str) {
        if (str == null) {
            return null;
        }
        for (Account account : getAccounts()) {
            if (str.equals(account.name)) {
                return account;
            }
        }
        return null;
    }

    public AccountManager getAccountManager() {
        return this.manager;
    }

    public Account[] getAccounts() {
        return this.manager.getAccountsByType("com.google");
    }

    public void invalidateAuthToken(String str) {
        this.manager.invalidateAuthToken("com.google", str);
    }
}
