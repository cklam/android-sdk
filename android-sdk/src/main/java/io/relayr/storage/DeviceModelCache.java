package io.relayr.storage;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Singleton;

import io.relayr.api.DeviceModelsApi;
import io.relayr.model.Device;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.DeviceModels;
import retrofit.RetrofitError;
import rx.Subscriber;

/**
 * Caches all {@link DeviceModel} objects. Works only if there is Internet connection.
 * Use {@link DeviceModelCache} to determine appropriate model for your device.
 * Example: call {@link DeviceModelCache#getModel(String)} using modelId from {@link Device#getModelId()}
 */
@Singleton
public class DeviceModelCache {

    public class DeviceModelCacheException extends Exception {
        public DeviceModelCacheException() {
            super("Cache not ready.");
        }
    }

    private static final String TAG = "DeviceModelCache";
    private static final Map<String, DeviceModel> sDeviceModels = new ConcurrentHashMap<>();
    private static volatile boolean refreshing = false;

    private final DeviceModelsApi mModelsApi;

    public DeviceModelCache(DeviceModelsApi modelsApi) {
        this.mModelsApi = modelsApi;
        refresh();
    }

    /**
     * Returns cache state. Use this method before using {@link #getModel(String)}
     * @return true if cache is ready false otherwise
     */
    public boolean isEmpty() {
        if (refreshing) return true;
        return sDeviceModels.isEmpty();
    }

    /**
     * Returns {@link DeviceModel} depending on specified modelId.
     * Obtain modelId parameter from {@link Device#getModelId()}
     * @param modelId {@link Device#getModelId()}
     * @return {@link DeviceModel} if one is found, null otherwise
     */
    public DeviceModel getModel(String modelId) throws DeviceModelCacheException {
        if (modelId == null) {
            Log.e(TAG, "Model Id can not be null!");
            return null;
        }

        if (isEmpty()) {
            refresh();
            throw new DeviceModelCacheException();
        }

        return sDeviceModels.get(modelId);
    }

    /**
     * Refresh device model cache.
     */
    public void refresh() {
        if (refreshing) return;
        refreshing = true;

        mModelsApi.getDeviceModels(50)
                .timeout(7, TimeUnit.SECONDS)
                .subscribe(new Subscriber<DeviceModels>() {
                    @Override
                    public void onCompleted() {
                        refreshing = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshing = false;
                        e.printStackTrace();
                        if (e instanceof RetrofitError)
                            Log.e(TAG, "Internet connection doesn't exist");
                        if (e instanceof TimeoutException) refresh();
                    }

                    @Override
                    public void onNext(DeviceModels deviceModels) {
                        for (DeviceModel deviceModel : deviceModels.getModels())
                            sDeviceModels.put(deviceModel.getId(), deviceModel);
                        refreshing = false;
                        Log.d(TAG, "Loaded " + deviceModels.getCount() + " models.");
                    }
                });
    }
}
