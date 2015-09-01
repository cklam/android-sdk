package io.relayr.api;

import io.relayr.RelayrSdk;
import io.relayr.model.channel.DataChannel;
import io.relayr.model.channel.ChannelDefinition;
import io.relayr.model.channel.ExistingChannel;
import io.relayr.model.channel.PublishChannel;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface ChannelApi {

    /**
     * Creates an MQTT channel for a device specified with {@link ChannelDefinition#deviceId}
     * Used in {@link RelayrSdk#getWebSocketClient()} to create channels and subscribe to device
     * readings.
     * @param mqttDefinition
     * @return an {@link rx.Observable} with created MQTT channel.
     */
    @POST("/channels") Observable<DataChannel> create(@Body ChannelDefinition mqttDefinition);

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
    Observable<ExistingChannel> getChannels(@Path("deviceId") String deviceId);

    /**
     * Create channel to publish device data to.
     * @param mqttDefinition defines deviceId and type of transport. Default is "mqtt"
     * @param deviceId
     */
    @POST("/devices/{deviceId}/transmitter")
    Observable<PublishChannel> createForDevice(@Body ChannelDefinition mqttDefinition,
                                                  @Path("deviceId") String deviceId);
}
