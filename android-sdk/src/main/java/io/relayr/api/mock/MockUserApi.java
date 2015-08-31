package io.relayr.api.mock;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.relayr.api.UserApi;
import io.relayr.model.App;
import io.relayr.model.Bookmark;
import io.relayr.model.BookmarkDevice;
import io.relayr.model.CreateWunderBar;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import io.relayr.model.User;
import io.relayr.model.account.Account;
import io.relayr.model.groups.Group;
import retrofit.http.Body;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;

import static io.relayr.api.mock.MockBackend.APP_INFO;
import static io.relayr.api.mock.MockBackend.BOOKMARKED_DEVICES;
import static io.relayr.api.mock.MockBackend.BOOKMARK_DEVICE;
import static io.relayr.api.mock.MockBackend.USERS_CREATE_WUNDERBAR;
import static io.relayr.api.mock.MockBackend.USERS_TRANSMITTERS;
import static io.relayr.api.mock.MockBackend.USER_ACCOUNTS;
import static io.relayr.api.mock.MockBackend.USER_DEVICES;
import static io.relayr.api.mock.MockBackend.USER_GROUPS;
import static io.relayr.api.mock.MockBackend.USER_INFO;

public class MockUserApi implements UserApi {

    private final MockBackend mServer;

    @Inject
    public MockUserApi(MockBackend mockBackend) {
        mServer = mockBackend;
    }

    @Override
    public Observable<App> getAppInfo() {
        return mServer.createObservable(new TypeToken<App>() {
        }, APP_INFO);
    }

    @Override
    public Observable<User> getUserInfo() {
        return mServer.createObservable(new TypeToken<User>() {}, USER_INFO);
    }

    @Override
    public Observable<List<Device>> getDevices(String userId) {
        return mServer.createObservable(new TypeToken<List<Device>>() {
        }, USER_DEVICES);
    }

    @Override public Observable<List<Group>> getGroups(String userId) {
        return mServer.createObservable(new TypeToken<List<Group>>() {
        }, USER_GROUPS);
    }

    @Override public Observable<Void> deleteAllGroups(String userId) {
        return Observable.empty();
    }

    @Override
    public Observable<User> updateUserDetails(User user, String userId) {
        return Observable.just(user);
    }

    @Override
    public Observable<CreateWunderBar> createWunderBar(String userId) {
        return mServer.createObservable(new TypeToken<CreateWunderBar>() {
        }, USERS_CREATE_WUNDERBAR);
    }

    @Override
    public Observable<List<Transmitter>> getTransmitters(String userId) {
        return mServer.createObservable(new TypeToken<List<Transmitter>>() {
        }, USERS_TRANSMITTERS);
    }

    @Override
    public Observable<Bookmark> bookmarkPublicDevice(String userId, String deviceId) {
        return mServer.createObservable(new TypeToken<Bookmark>() {
        }, BOOKMARK_DEVICE);
    }

    @Override
    public Observable<Void> deleteBookmark(String userId, String deviceId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public Observable<List<BookmarkDevice>> getBookmarkedDevices(String userId) {
        return mServer.createObservable(new TypeToken<List<BookmarkDevice>>() {
        }, BOOKMARKED_DEVICES);
    }

    @Override public Observable<List<Account>> getAccounts(String userId) {
        return mServer.createObservable(new TypeToken<List<Account>>() {
        }, USER_ACCOUNTS);
    }

    @Override
    public Observable<Void> isAccountConnected(String userId, String accountName) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> disconnectAccount(String userId, String accountName) {
        return Observable.empty();
    }
}
