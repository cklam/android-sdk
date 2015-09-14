package io.relayr.android;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.android.activity.LoginActivity;
import io.relayr.java.api.ChannelApi;
import io.relayr.java.api.RelayrApi;
import io.relayr.java.api.mock.MockBackend;
import io.relayr.java.api.mock.MockChannelApi;
import io.relayr.java.api.mock.MockRelayrApi;

@Module(
        complete = false,
        library = true,
        overrides = true,
        injects = {
                LoginActivity.class
        }
)
public class TestModule {

    private final Context app;

    public TestModule(Context context) {
        app = context;
    }

    @Provides @Singleton MockBackend provideMockBackend() {
        return new MockBackend();
    }

    @Provides @Singleton RelayrApi provideRelayrApi(MockBackend mockBackend) {
        return new MockRelayrApi(mockBackend);
    }

    @Provides @Singleton ChannelApi provideChannelApi(MockBackend loader) {
        return new MockChannelApi(loader);
    }
}
