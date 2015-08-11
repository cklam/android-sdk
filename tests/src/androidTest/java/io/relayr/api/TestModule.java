package io.relayr.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.api.mock.MockChannelApi;
import io.relayr.api.mock.MockCloudApi;
import io.relayr.api.mock.MockRelayrApi;
import io.relayr.api.mock.MockUserApi;
import io.relayr.storage.DeviceModelCache;
import io.relayr.storage.DeviceModelStorageTest;
import io.relayr.websocket.WebSocketClientTest;

@Module(
        complete = false,
        library = true,
        overrides = true,
        injects = {
                MockBackendTest.class,
                MockRelayrApiTest.class,
                MockCloudApiTest.class,
                MockAccountsApiTest.class,
                MockUserApiTest.class,
                MockGroupsApiTest.class,
                MockChannelApiTest.class,
                WebSocketClientTest.class,
                MockDeviceModelsApiTest.class,
                DeviceModelStorageTest.class
        }
)
public class TestModule {

    private final Context app;

    public TestModule(Context context) {
        app = context;
    }

    @Provides @Singleton io.relayr.api.mock.MockBackend provideMockBackend() {
        return new io.relayr.api.mock.MockBackend(app);
    }

    @Provides @Singleton RelayrApi provideRelayrApi(io.relayr.api.mock.MockBackend mockBackend) {
        return new MockRelayrApi(mockBackend);
    }

    @Provides @Singleton CloudApi provideCloudApi(io.relayr.api.mock.MockBackend loader) {
        return new MockCloudApi(loader);
    }

    @Provides @Singleton AccountsApi provideAccountsApi(io.relayr.api.mock.MockBackend loader) {
        return new io.relayr.api.mock.MockAccountsApi(loader);
    }

    @Provides @Singleton UserApi provideUserApi(io.relayr.api.mock.MockBackend loader) {
        return new MockUserApi(loader);
    }

    @Provides @Singleton ChannelApi provideChannelApi(io.relayr.api.mock.MockBackend loader) {
        return new MockChannelApi(loader);
    }

    @Provides @Singleton GroupsApi provideGroupsApi(io.relayr.api.mock.MockBackend loader) {
        return new io.relayr.api.mock.MockGroupsApi(loader);
    }

    @Provides @Singleton DeviceModelsApi provideDeviceModelsApi(io.relayr.api.mock.MockBackend loader) {
        return new io.relayr.api.mock.MockDeviceModelsApi(loader);
    }

    @Provides @Singleton DeviceModelCache provideDeviceModelStorage(DeviceModelsApi api) {
        return new DeviceModelCache(api);
    }
}
