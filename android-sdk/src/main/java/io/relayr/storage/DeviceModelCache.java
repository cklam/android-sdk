package io.relayr.storage;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import io.relayr.api.MockBackend;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.DeviceModels;
import rx.Subscriber;

import static io.relayr.api.MockBackend.DEVICE_MODELS;

public class DeviceModelCache {

    private static final Map<String, DeviceModel> sDeviceModels = new HashMap<>();

    public DeviceModelCache(MockBackend loader) {
        loader.createObservable(new TypeToken<DeviceModels>() {
        }, DEVICE_MODELS)
                .subscribe(new Subscriber<DeviceModels>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DeviceModels deviceModels) {
                        for (DeviceModel deviceModel : deviceModels.getModels())
                            sDeviceModels.put(deviceModel.getId(), deviceModel);
                    }
                });
    }

    public boolean isEmpty() {
        return sDeviceModels.isEmpty();
    }

    public DeviceModel getModel(String modelId) {
        return sDeviceModels.get(modelId);
    }

}
