package io.relayr.model.account;

import java.io.Serializable;
import java.util.List;

import io.relayr.RelayrSdk;
import io.relayr.storage.DataStorage;
import rx.Observable;

/**
 * Third party account that can be connected with the relayr user.
 */
public class Account implements Serializable {

    /** Account name for identification */
    private String name;
    /** Account name for presentation */
    private String readableName;
    /** Authentication URL */
    private String oauthUrl;

    public Account(String name, String readableName, String oauthUrl) {
        this.name = name;
        this.readableName = readableName;
        this.oauthUrl = oauthUrl;
    }

    /** Returns account name id */
    public String getName() {
        return name;
    }

    /** Returns presentation account name */
    public String getReadableName() {
        return readableName;
    }

    /** Returns oauth URL */
    public String getOauthUrl() {
        return oauthUrl;
    }

    /** Returns list of available devices for the account */
    public Observable<List<AccountDevice>> getDevices() {
        return RelayrSdk.getAccountsApi().getAccountDevices(name);
    }

    /** Returns login URL for the account */
    public Observable<AccountUrl> getLoginUrl(String redirectUrl) {
        return RelayrSdk.getAccountsApi().getLoginUrl(name, redirectUrl);
    }

    /** Returns login URL for the account */
    public Observable<Void> isConnected() {
        return RelayrSdk.getUserApi().isAccountConnected(DataStorage.getUserId(), name);
    }
}
