package io.relayr.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.storage.DataStorageTest;
import io.relayr.storage.DeviceModelStorage;
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

    @Provides @Singleton MockBackend provideMockBackend() {
        return new MockBackend(app);
    }

    @Provides @Singleton RelayrApi provideRelayrApi(MockBackend mockBackend) {
        return new MockRelayrApi(mockBackend);
    }

    @Provides @Singleton CloudApi provideCloudApi(MockBackend loader) {
        return new MockCloudApi(loader);
    }

    @Provides @Singleton AccountsApi provideAccountsApi(MockBackend loader) {
        return new MockAccountsApi(loader);
    }

    @Provides @Singleton UserApi provideUserApi(MockBackend loader) {
        return new MockUserApi(loader);
    }

    @Provides @Singleton ChannelApi provideChannelApi(MockBackend loader) {
        return new MockChannelApi(loader);
    }

    @Provides @Singleton GroupsApi provideGroupsApi(MockBackend loader) {
        return new MockGroupsApi(loader);
    }

    @Provides @Singleton DeviceModelsApi provideDeviceModelsApi(MockBackend loader) {
        return new MockDeviceModelsApi(loader);
    }

    @Provides @Singleton
    DeviceModelStorage provideDeviceModelStorage(MockBackend loader) {
        return new DeviceModelStorage(loader);
    }
}
