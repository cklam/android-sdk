package io.relayr.api.mock;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.api.AccountsApi;
import io.relayr.api.ChannelApi;
import io.relayr.api.CloudApi;
import io.relayr.api.DeviceModelsApi;
import io.relayr.api.GroupsApi;
import io.relayr.api.OauthApi;
import io.relayr.api.RelayrApi;
import io.relayr.api.UserApi;
import io.relayr.api.mock.MockAccountsApi;
import io.relayr.api.mock.MockBackend;
import io.relayr.api.mock.MockChannelApi;
import io.relayr.api.mock.MockCloudApi;
import io.relayr.api.mock.MockDeviceModelsApi;
import io.relayr.api.mock.MockGroupsApi;
import io.relayr.api.mock.MockOauthApi;
import io.relayr.api.mock.MockRelayrApi;
import io.relayr.api.mock.MockUserApi;
import io.relayr.storage.DeviceModelCache;

@Module(
        complete = false,
        library = true
)
public class DebugApiModule {

    private final Context app;

    public DebugApiModule(Context context) {
        app = context;
    }

    @Provides @Singleton MockBackend provideMockBackend() {
        return new MockBackend(app);
    }

    @Provides @Singleton OauthApi provideOauthApi(MockBackend loader) {
        return new MockOauthApi(loader);
    }

    @Provides @Singleton RelayrApi provideRelayrApi(MockBackend loader) {
        return new MockRelayrApi(loader);
    }

    @Provides @Singleton ChannelApi provideChannelApi(MockBackend loader) {
        return new MockChannelApi(loader);
    }

    @Provides @Singleton AccountsApi provideAccountsApi(MockBackend loader) {
        return new MockAccountsApi(loader);
    }

    @Provides @Singleton UserApi provideUserApi(MockBackend loader) {
        return new MockUserApi(loader);
    }

    @Provides @Singleton GroupsApi provideGroupsApi(MockBackend loader) {
        return new MockGroupsApi(loader);
    }

    @Provides @Singleton CloudApi provideCloudApi(MockBackend loader) {
        return new MockCloudApi(loader);
    }

    @Provides @Singleton DeviceModelsApi provideDeviceModelsApi(MockBackend loader) {
        return new MockDeviceModelsApi(loader);
    }

    @Provides @Singleton DeviceModelCache provideDeviceModelCache(DeviceModelsApi api) {
        return new DeviceModelCache(api);
    }
}
