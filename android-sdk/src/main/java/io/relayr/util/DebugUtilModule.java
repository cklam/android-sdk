package io.relayr.util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.java.api.CloudApi;
import io.relayr.java.api.mock.DebugApiModule;

@Module(
        complete = false,
        library = true,
        includes = DebugApiModule.class
)
public class DebugUtilModule {

    @Provides @Singleton
    ReachabilityUtils provideReachabilityUtils(CloudApi api) {
        return new MockReachabilityUtils(api);
    }
}
