package io.relayr.api;

import java.util.List;

import io.relayr.model.App;
import io.relayr.model.Command;
import io.relayr.model.CreateDevice;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import io.relayr.model.TransmitterDevice;
import io.relayr.model.User;
import io.relayr.model.onboarding.OnBoardingState;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;
import rx.Observable;

/** This class incorporates a wrapped version of the relayr API calls. */
public interface RelayrApi {

    /** @return an {@link rx.Observable} to the information about the app initiating the request. */
    @GET("/oauth2/app-info") Observable<App> getAppInfo();

    /** @return an {@link rx.Observable} information about the user initiating the request. */
    @GET("/oauth2/user-info") Observable<User> getUserInfo();

    /**
     * Creates device on the platform. Used for v2 on-boarding.
     * @param device to create and add to the existing transmitter
     * @return created device
     */
    @POST("/devices") Observable<Device> createDevice(@Body CreateDevice device);

    @POST("/devices/{device_id}/cmd")
    Observable<Void> sendCommand(@Path("device_id") String deviceId,
                                 @Body Command command);

    @DELETE("/devices/{device_id}")
    Observable<Void> deleteDevice(@Path("device_id") String deviceId);

    /**
     * A public device is a device which public attribute has been set to 'true' therefore
     * no authorization is required.
     * @param meaning When a meaning is specified, the request returns only
     *                the devices which readings match the meaning.
     * @return an {@link rx.Observable} with a list of all public devices.
     */
    @GET("/devices/public")
    Observable<List<Device>> getPublicDevices(@Query("meaning") String meaning);

    /** @return an {@link rx.Observable} of a specific transmitter */
    @GET("/transmitters/{transmitter}")
    Observable<Transmitter> getTransmitter(@Path("transmitter") String transmitter);

    /**
     * Updates a transmitter.
     * @param transmitter   updated transmitter with the new details
     * @param transmitterId id of the transmitter to update
     * @return an {@link rx.Observable} to the updated Transmitter
     */
    @PATCH("/transmitters/{transmitter}")
    Observable<Transmitter> updateTransmitter(@Body Transmitter transmitter,
                                              @Path("transmitter") String transmitterId);

    /**
     * @param transmitter the id of the transmitter to get the devices from
     * @return an {@link rx.Observable} with a list of devices that belong to the specific
     * transmitter.
     */
    @GET("/transmitters/{transmitter}/devices")
    Observable<List<TransmitterDevice>> getTransmitterDevices(
            @Path("transmitter") String transmitter);

    /**
     * Registers the transmitter
     * @param transmitter transmitter object to register
     * @return an {@link rx.Observable} to the registered Transmitter
     */
    @POST("/transmitters")
    Observable<Transmitter> registerTransmitter(@Body Transmitter transmitter);

    /**
     * Deletes a transmitter and all of its components (Transmitter and Devices)
     * @param transmitterId id of the transmitter (the Master Module)
     * @return an empty {@link rx.Observable}
     */
    @DELETE("/transmitters/{transmitterId}")
    Observable<Void> deleteTransmitter(@Path("transmitterId") String transmitterId);

    /**
     * Returns true if transmitter is connected to MQTT and able to send data.
     * @param transmitterId id of the transmitter (the Master Module)
     * @return an empty {@link rx.Observable}
     */
    @GET("/experimental/transmitters/{transmitterId}/state")
    Observable<OnBoardingState> isTransmitterConnected(@Path("transmitterId") String transmitterId);

    /**
     * Returns true if transmitter is connected to MQTT and able to send data.
     * @param transmitterId id of the transmitter (the Master Module)
     * @return an empty {@link rx.Observable}
     */
    @POST("/experimental/transmitters/{transmitterId}/ble-scan/{period}")
    @Streaming Observable<Response> scanForDevices(@Path("transmitterId") String transmitterId,
                                                   @Path("period") int period);

    /**
     * Deletes a WunderBar and all of its components (Transmitter and Devices)
     * @param transmitterId id of the transmitter (the Master Module)
     * @return an empty {@link rx.Observable}
     */
    @DELETE("/wunderbars/{transmitterId}")
    Observable<Void> deleteWunderBar(@Path("transmitterId") String transmitterId);

    /**
     * Returns map of ble device names and their device model ids. Used with v2 on-boarding.
     * @return an {@link rx.Observable} with Map<String, String>
     */
    @GET("/device-models/ble-names") Observable<Object> getBleModels();

}
