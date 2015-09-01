package io.relayr.websocket;

import com.google.gson.reflect.TypeToken;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import io.relayr.TestEnvironment;
import io.relayr.api.ChannelApi;
import io.relayr.api.mock.MockBackend;
import io.relayr.model.Device;
import io.relayr.model.Model;
import io.relayr.model.Reading;
import io.relayr.model.channel.ChannelDefinition;
import io.relayr.model.channel.DataChannel;
import io.relayr.model.channel.PublishChannel;
import rx.Observable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebSocketClientTest extends TestEnvironment {

    @Mock private ChannelApi channelApi;
    @Mock private WebSocketFactory webSocketFactory;
    @Mock private WebSocket<DataChannel> webSocket;

    private WebSocketClient socketClient;

    @Before
    public void init() {
        super.init();
        initSdk();
        inject();
    }

    @Test
    public void webSocketClientSubscribeTest() {
        Observable<DataChannel> observable = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<DataChannel>() {
                }, MockBackend.MQTT_CREDENTIALS);

        when(webSocketFactory.createWebSocket()).thenReturn(webSocket);
        when(channelApi.create(any(ChannelDefinition.class))).thenReturn(observable);

        socketClient = new WebSocketClient(channelApi, webSocketFactory);
        socketClient.subscribe(createDevice());
        await();

        verify(webSocketFactory, times(1)).createWebSocket();
        await();

        verify(channelApi, times(1)).create(any(ChannelDefinition.class));
        await();

        verify(webSocket, times(1)).createClient(any(DataChannel.class));
    }

    @Test
    public void webSocketClientUnSubscribeTest() {
        Observable<DataChannel> observable = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<DataChannel>() {
                }, MockBackend.MQTT_CREDENTIALS);

        when(webSocketFactory.createWebSocket()).thenReturn(webSocket);
        when(webSocket.unSubscribe(any(String.class))).thenReturn(true);
        when(channelApi.create(any(ChannelDefinition.class))).thenReturn(observable);

        socketClient = new WebSocketClient(channelApi, webSocketFactory);
        socketClient.subscribe(createDevice());
        await();

        assertThat(socketClient.mSocketConnections.isEmpty()).isFalse();

        socketClient.unSubscribe("id");
        await();

        assertThat(socketClient.mSocketConnections.isEmpty()).isTrue();
    }

    @Test
    public void webSocketClientPublishTest() throws MqttException {
        Observable<PublishChannel> observable = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<PublishChannel>() {
                }, MockBackend.MQTT_DEVICE_CHANNEL);

        Observable<DataChannel> observableData = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<DataChannel>() {
                }, MockBackend.MQTT_DEVICE_CREDENTIALS);

        when(webSocketFactory.createWebSocket()).thenReturn(webSocket);
        when(channelApi.createForDevice(any(ChannelDefinition.class), any(String.class))).thenReturn(observable);
        when(webSocket.createClient(any(DataChannel.class))).thenReturn(observableData);

        socketClient = new WebSocketClient(channelApi, webSocketFactory);
        socketClient.publish("devId", new Reading(0, 0, "m", "/", 1));

        await(500);

        assertThat(socketClient.mPublishChannels.isEmpty()).isFalse();

        verify(channelApi, times(1)).createForDevice(any(ChannelDefinition.class), anyString());
        verify(webSocket, times(1)).createClient(any(DataChannel.class));
        verify(webSocket, times(1)).publish(anyString(), anyString());
    }

    private Device createDevice() {
        return new Device("wunderbar2", false, "id", "s", "fv", "o",
                new Model("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8"), "n", "id");
    }
}
