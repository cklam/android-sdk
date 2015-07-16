package io.relayr.api;

import io.relayr.model.deviceModels.DeviceModel;
import io.relayr.model.deviceModels.DeviceModels;
import io.relayr.model.deviceModels.ReadingMeanings;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface DeviceModelsApi {

    /**
     * Returns a list of the possible reading types of the devices on the relayr platform
     * @return an {@link rx.Observable} with a list of Reading meanings
     */
    @GET("/device-models/meanings") Observable<ReadingMeanings> getReadingMeanings();

    /**
     * Returns list all available device models.
     * @return an {@link Observable} with a list of device models.
     */
    @GET("/device-models") Observable<DeviceModels> getDeviceModels();

    /**
     * Returns specific device model
     * @return an {@link Observable}
     */
    @GET("/device-models/{modelId}") Observable<DeviceModel> getDeviceModelById(
            @Path("modelId") String modelId);

    /**
     * Returns specific device model's firmware list
     * @return an {@link Observable}
     */
    @GET("/device-models/{modelId}/firmware") Observable<DeviceModel> getDeviceModelFirmwares(
            @Path("modelId") String modelId);

    /**
     * Returns device model defined by modelId and firmware version
     * @return an {@link Observable}
     */
    @GET("/device-models/{modelId}/firmware/{version}")
    Observable<DeviceModel> getDeviceModelByFirmware(
            @Path("modelId") String modelId, @Path("version") String version);

    /**
     * Returns list of all device models owned by user
     * @return an {@link Observable} with a list of device models.
     */
    @GET("/users/{userId}/device-models") Observable<DeviceModels> getUsersDeviceModels(
            @Path("userId") String userId);

    /**
     * Returns list of all device models owned by user
     * @return an {@link Observable} with a list of device models.
     */
    @GET("/users/{userId}/device-models/{modelId}")
    Observable<DeviceModel> getUsersDeviceModelById(
            @Path("userId") String userId, @Path("modelId") String modelId);

    /**
     * Returns specific device model's firmware list
     * @return an {@link Observable}
     */
    @GET("/users/{userId}/device-models/{modelId}/firmware") Observable<DeviceModel>
    getUsersDeviceModelFirmwares(
            @Path("userId") String userId,
            @Path("modelId") String modelId);

    /**
     * Returns device model defined by modelId and firmware version
     * @return an {@link Observable}
     */
    @GET("/users/{userId}/device-models/{modelId}/firmware/{version}") Observable<DeviceModel>
    getUsersDeviceModelByFirmware(@Path("userId") String userId,
                                  @Path("modelId") String modelId,
                                  @Path("version") String version);

}
