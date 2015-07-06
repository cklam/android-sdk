package io.relayr.api;

import io.relayr.RelayrSdk;
import io.relayr.model.MqttChannel;
import io.relayr.model.MqttDefinition;
import io.relayr.model.MqttExistingChannel;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface ChannelApi {

    /**
     * Creates an MQTT channel for a device specified with {@link MqttDefinition#deviceId}
     * Used in {@link RelayrSdk#getWebSocketClient()} to create channels and subscribe to device
     * readings.
     * @param mqttDefinition
     * @return an {@link rx.Observable} with created MQTT channel.
     */
    @POST("/channels") Observable<MqttChannel> create(@Body MqttDefinition mqttDefinition);

    /**
     * Deletes an MQTT channel
     * @param channelId existing channel ID
     * @return an empty {@link rx.Observable}
     */
    @DELETE("/channels/{channelId}") Observable<Void> delete(@Path("channelId") String channelId);

    /**
     * Returns existing MQTT channels for specified device
     * @param deviceId
     * @return an {@link rx.Observable}
     */
    @GET("/devices/{deviceId}/channels")
    Observable<MqttExistingChannel> getChannels(@Path("deviceId") String deviceId);
}
