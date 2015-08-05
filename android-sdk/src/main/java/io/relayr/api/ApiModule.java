package io.relayr.api;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.storage.DataStorage;
import io.relayr.storage.DeviceModelCache;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Response;

@Module(
        complete = false,
        library = true
)
public class ApiModule {

    public static final String API_ENDPOINT = "https://api.relayr.io";
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final String USER_AGENT = Utils.getUserAgent();

    private static final RestAdapter.LogLevel API_LOG_LEVEL = RestAdapter.LogLevel.BASIC;
    private static final RestAdapter.LogLevel OAUTH_API_LOG_LEVEL = RestAdapter.LogLevel.NONE;
    private static final RestAdapter.LogLevel MODELS_API_LOG_LEVEL = RestAdapter.LogLevel.FULL;

    private static final RequestInterceptor apiRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", USER_AGENT);
            request.addHeader("Authorization", DataStorage.getUserToken());
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
        }
    };

    private static final RequestInterceptor modelsRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", ApiModule.USER_AGENT);
            request.addHeader("Authorization", DataStorage.getUserToken());
            request.addHeader("Content-Type", "application/hal+json");
        }
    };

    private static final RequestInterceptor oauthRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", USER_AGENT);
        }
    };
    private final Context app;

    public ApiModule(Context context) {
        app = context;
    }

    @Provides @Singleton Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(API_ENDPOINT);
    }

    @Provides @Singleton Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides @Singleton @Named("api") RestAdapter provideApiRestAdapter(
            Endpoint endpoint, Client client) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setRequestInterceptor(apiRequestInterceptor)
                .setErrorHandler(new ApiErrorHandler())
                .setLogLevel(API_LOG_LEVEL)
                .build();
    }

    @Provides @Singleton @Named("oauth") RestAdapter provideOauthRestAdapter(
            Endpoint endpoint, Client client) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setRequestInterceptor(oauthRequestInterceptor)
                .setErrorHandler(new ApiErrorHandler())
                .setLogLevel(OAUTH_API_LOG_LEVEL)
                .build();
    }

    @Provides @Singleton @Named("models") RestAdapter provideModelsRestAdapter(
            Endpoint endpoint, Client client) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setRequestInterceptor(modelsRequestInterceptor)
                .setErrorHandler(new ApiErrorHandler())
                .setLogLevel(MODELS_API_LOG_LEVEL)
                .build();
    }

    @Provides @Singleton RelayrApi provideRelayrApi(@Named("api") RestAdapter restAdapter) {
        return restAdapter.create(RelayrApi.class);
    }

    @Provides @Singleton OauthApi provideOauthApi(@Named("oauth") RestAdapter restAdapter) {
        return restAdapter.create(OauthApi.class);
    }

    @Provides @Singleton ChannelApi provideChannelApi(@Named("api")
                                                      RestAdapter restAdapter) {
        return restAdapter.create(ChannelApi.class);
    }

    @Provides @Singleton CloudApi provideCloudApi(@Named("api") RestAdapter restAdapter) {
        return restAdapter.create(CloudApi.class);
    }

    @Provides @Singleton AccountsApi provideAccountsApi(@Named("api") RestAdapter restAdapter) {
        return restAdapter.create(AccountsApi.class);
    }

    @Provides @Singleton GroupsApi provideGroupsApi(@Named("api") RestAdapter restAdapter) {
        return restAdapter.create(GroupsApi.class);
    }

    @Provides @Singleton UserApi provideUserApi(@Named("api") RestAdapter restAdapter) {
        return restAdapter.create(UserApi.class);
    }

    @Provides @Singleton
    DeviceModelsApi provideDeviceModelsApi(@Named("models") RestAdapter restAdapter) {
        return restAdapter.create(DeviceModelsApi.class);
    }

    @Provides @Singleton DeviceModelCache provideDeviceModelCache(DeviceModelsApi deviceModelsApi) {
//        return new DeviceModelCache(new MockDeviceModelsApi(new MockBackend(app)));
        return new DeviceModelCache(deviceModelsApi);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        return createOkHttpClient(app);
    }

    private static OkHttpClient createOkHttpClient(Context app) {
        OkHttpClient client = new OkHttpClient();

        File cacheDir = new File(app.getCacheDir(), "https");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);

        return client;
    }

    class ApiErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Response response = cause.getResponse();
            if (response != null && response.getStatus() > 301) return new Exception(cause);
            return cause;
        }
    }
}
