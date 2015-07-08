package io.relayr.api;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.relayr.model.groups.Group;
import retrofit.http.Body;
import retrofit.http.Path;
import rx.Observable;

import static io.relayr.api.MockBackend.USER_GROUP;
import static io.relayr.api.MockBackend.USER_GROUPS;

public class MockGroupsApi implements GroupsApi {

    private final MockBackend mMockBackend;

    @Inject
    public MockGroupsApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override public Observable<List<Group>> getGroups() {
        return mMockBackend.createObservable(new TypeToken<List<Group>>() {
        }, USER_GROUPS);
    }

    @Override public Observable<Void> createGroup(@Body String name) {
        return Observable.empty();
    }

    @Override public Observable<Group> getGroup(@Path("groupId") String groupId) {
        return mMockBackend.createObservable(new TypeToken<Group>() {
        }, USER_GROUP);
    }

    @Override
    public Observable<Void> updateGroup(@Body Group group, @Path("groupId") String groupId) {
        return Observable.empty();
    }

    @Override public Observable<Void> deleteGroup(@Path("groupId") String groupId) {
        return Observable.empty();
    }

    @Override public Observable<Void> deleteAllGroups() {
        return Observable.empty();
    }
}
