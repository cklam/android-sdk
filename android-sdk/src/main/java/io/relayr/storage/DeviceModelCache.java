package io.relayr.storage;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Singleton;

import io.relayr.api.DeviceModelsApi;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.DeviceModels;
import rx.Subscriber;

@Singleton
public class DeviceModelCache {

    private static final String TAG = "DeviceModelCache";
    private static final Map<String, DeviceModel> sDeviceModels = new ConcurrentHashMap<>();
    private static volatile boolean refreshing = false;

    private final DeviceModelsApi mApi;

    public DeviceModelCache(DeviceModelsApi modelsApi) {
        this.mApi = modelsApi;
        refresh();
    }

    public boolean isEmpty() {
        if (refreshing) return true;
        return sDeviceModels.isEmpty();
    }

    public DeviceModel getModel(String modelId) {
        if (isEmpty()) {
            Log.e(TAG, "Cache not ready - trying to refresh");
            refresh();
            return null;
        }
        if (modelId == null) {
            Log.e(TAG, "Model Id can not be null!");
            return null;
        }

        return sDeviceModels.get(modelId);
    }

    public void refresh() {
        if (refreshing) return;

        refreshing = true;
        mApi.getDeviceModels(20)
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
