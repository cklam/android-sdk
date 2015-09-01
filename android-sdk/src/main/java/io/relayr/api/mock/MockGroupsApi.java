package io.relayr.api.mock;

import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import io.relayr.api.GroupsApi;
import io.relayr.model.groups.Group;
import io.relayr.model.groups.GroupCreate;
import io.relayr.model.groups.PositionUpdate;
import rx.Observable;

import static io.relayr.api.mock.MockBackend.USER_GROUP;

public class MockGroupsApi implements GroupsApi {

    private final MockBackend mMockBackend;

    @Inject
    public MockGroupsApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override public Observable<Group> createGroup(GroupCreate group) {
        return mMockBackend.createObservable(new TypeToken<Group>() {
        }, USER_GROUP);
    }

    @Override public Observable<Group> getGroup(String groupId) {
        return mMockBackend.createObservable(new TypeToken<Group>() {
        }, USER_GROUP);
    }

    @Override
    public Observable<Void> updateGroup(GroupCreate group, String groupId) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> addDevice(String groupId, String deviceId) {
        return null;
    }

    @Override
    public Observable<Void> deleteDevice(String groupId, String deviceId) {
        return null;
    }

    @Override
    public Observable<Void> updateDevicePosition(PositionUpdate update, String groupId, String deviceId) {
        return null;
    }

    @Override public Observable<Void> deleteGroup(String groupId) {
        return Observable.empty();
    }

}
