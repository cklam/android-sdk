package io.relayr.api.mock;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.relayr.api.AccountsApi;
import io.relayr.model.account.Account;
import io.relayr.model.account.AccountDevice;
import io.relayr.model.account.AccountUrl;
import rx.Observable;

import static io.relayr.api.mock.MockBackend.USER_ACCOUNTS;
import static io.relayr.api.mock.MockBackend.USER_ACCOUNT_DEVICES;
import static io.relayr.api.mock.MockBackend.USER_ACCOUNT_LOGIN_URL;

public class MockAccountsApi implements AccountsApi {

    private final MockBackend mMockBackend;

    @Inject
    public MockAccountsApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override public Observable<List<Account>> getAccounts() {
        return mMockBackend.createObservable(new TypeToken<List<Account>>() {
        }, USER_ACCOUNTS);
    }

    @Override
    public Observable<List<AccountDevice>> getAccountDevices(String accountName) {
        return mMockBackend.createObservable(new TypeToken<List<AccountDevice>>() {
        }, USER_ACCOUNT_DEVICES);
    }

    @Override
    public Observable<AccountUrl> getLoginUrl(String accountName, String redirectUri) {
        return mMockBackend.createObservable(new TypeToken<AccountUrl>() {
        }, USER_ACCOUNT_LOGIN_URL);
    }
}
