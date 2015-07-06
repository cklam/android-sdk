package io.relayr.util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.api.ApiModule;
import io.relayr.api.CloudApi;

@Module(
        complete = false,
        library = true,
        includes = ApiModule.class
)
public class UtilModule {

    @Provides @Singleton
    ReachabilityUtils provideReachabilityUtils(CloudApi api) {
        return new ReachabilityUtils(api);
    }
}
