package io.relayr.api;

import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import io.relayr.model.models.DeviceFirmware;
import io.relayr.model.models.DeviceFirmwares;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.DeviceModels;
import io.relayr.model.models.ReadingMeanings;
import retrofit.http.Query;
import rx.Observable;

import static io.relayr.api.MockBackend.DEVICE_MODEL;
import static io.relayr.api.MockBackend.DEVICE_MODELS;
import static io.relayr.api.MockBackend.DEVICE_MODEL_FIRMWARE;
import static io.relayr.api.MockBackend.DEVICE_MODEL_FIRMWARES;
import static io.relayr.api.MockBackend.DEVICE_READING_MEANINGS;

public class MockDeviceModelsApi implements DeviceModelsApi{

    private final MockBackend mBackend;

    @Inject public MockDeviceModelsApi(MockBackend mockBackend) {
        mBackend = mockBackend;
    }

    @Override public Observable<ReadingMeanings> getReadingMeanings() {
        return mBackend.createObservable(new TypeToken<ReadingMeanings>() { }, DEVICE_READING_MEANINGS);
    }

    @Override public Observable<DeviceModels> getDeviceModels(int limit) {
        return mBackend.createObservable(new TypeToken<DeviceModels>() { }, DEVICE_MODELS);
    }

    @Override public Observable<DeviceModel> getDeviceModelById( String modelId) {
        return mBackend.createObservable(new TypeToken<DeviceModel>() { }, DEVICE_MODEL);
    }

    @Override
    public Observable<DeviceFirmwares> getDeviceModelFirmwares(String modelId) {
        return mBackend.createObservable(new TypeToken<DeviceFirmwares>() { }, DEVICE_MODEL_FIRMWARES);
    }

    @Override
    public Observable<DeviceFirmware> getDeviceModelByFirmware(String modelId, String version) {
        return mBackend.createObservable(new TypeToken<DeviceFirmware>() { }, DEVICE_MODEL_FIRMWARE);
    }

    @Override public Observable<DeviceModels> getUsersDeviceModels(String userId) {
        return mBackend.createObservable(new TypeToken<DeviceModels>() { }, DEVICE_MODELS);
    }

    @Override
    public Observable<DeviceModel> getUsersDeviceModelById(String userId, String modelId) {
        return mBackend.createObservable(new TypeToken<DeviceModel>() { }, DEVICE_MODEL);
    }

    @Override
    public Observable<DeviceFirmwares> getUsersDeviceModelFirmwares(String userId, String modelId) {
        return mBackend.createObservable(new TypeToken<DeviceFirmwares>() { }, DEVICE_MODEL_FIRMWARES);
    }

    @Override
    public Observable<DeviceFirmware> getUsersDeviceModelByFirmware(String userId, String modelId, String version) {
        return mBackend.createObservable(new TypeToken<DeviceFirmware>() { }, DEVICE_MODEL_FIRMWARE);
    }

}
