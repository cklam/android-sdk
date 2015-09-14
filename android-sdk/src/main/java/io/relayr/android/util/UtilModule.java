package io.relayr.android.util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.java.api.ApiModule;
import io.relayr.java.api.CloudApi;

@Module(
        complete = false,
        library = true,
        includes = ApiModule.class
)
public class UtilModule {

    @Provides @Singleton ReachabilityUtils provideReachabilityUtils(CloudApi api) {
        return new ReachabilityUtils(api);
    }
}
