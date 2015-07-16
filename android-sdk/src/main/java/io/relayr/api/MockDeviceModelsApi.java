package io.relayr.api;

import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import io.relayr.model.deviceModels.DeviceModel;
import io.relayr.model.deviceModels.DeviceModels;
import io.relayr.model.deviceModels.ReadingMeanings;
import rx.Observable;

import static io.relayr.api.MockBackend.DEVICE_MODEL;
import static io.relayr.api.MockBackend.DEVICE_MODELS;
import static io.relayr.api.MockBackend.DEVICE_READING_MEANINGS;

public class MockDeviceModelsApi implements DeviceModelsApi{

    private final MockBackend mBackend;

    @Inject public MockDeviceModelsApi(MockBackend mockBackend) {
        mBackend = mockBackend;
    }

    @Override public Observable<ReadingMeanings> getReadingMeanings() {
        return mBackend.createObservable(new TypeToken<ReadingMeanings>() { }, DEVICE_READING_MEANINGS);
    }

    @Override public Observable<DeviceModels> getDeviceModels() {
        return mBackend.createObservable(new TypeToken<DeviceModels>() { }, DEVICE_MODELS);
    }

    @Override public Observable<DeviceModel> getDeviceModelById( String modelId) {
        return mBackend.createObservable(new TypeToken<DeviceModel>() { }, DEVICE_MODEL);
    }

    @Override
    public Observable<DeviceModel> getDeviceModelFirmwares(String modelId) {
        return null;
    }

    @Override
    public Observable<DeviceModel> getDeviceModelByFirmware(String modelId, String version) {
        return null;
    }

    @Override public Observable<DeviceModels> getUsersDeviceModels(String userId) {
        return null;
    }

    @Override
    public Observable<DeviceModel> getUsersDeviceModelById(String userId, String modelId) {
        return null;
    }

    @Override
    public Observable<DeviceModel> getUsersDeviceModelFirmwares(String userId, String modelId) {
        return null;
    }

    @Override
    public Observable<DeviceModel> getUsersDeviceModelByFirmware(String userId, String modelId, String version) {
        return null;
    }

}
