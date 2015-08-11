package io.relayr.websocket;

import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import io.relayr.TestEnvironment;
import io.relayr.api.ChannelApi;
import io.relayr.api.mock.MockBackend;
import io.relayr.model.Device;
import io.relayr.model.Model;
import io.relayr.model.MqttChannel;
import io.relayr.model.MqttDefinition;
import rx.Observable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebSocketClientTest extends TestEnvironment {

    @Mock private ChannelApi channelApi;
    @Mock private WebSocketFactory webSocketFactory;
    @Mock private WebSocket<MqttChannel> webSocket;

    private WebSocketClient socketClient;

    @Before
    public void init() {
        super.init();
        initSdk();
        inject();
    }

    @Test
    public void webSocketClientSubscribeTest() {
        Observable<MqttChannel> observable = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<MqttChannel>() {
                }, MockBackend.MQTT_CREDENTIALS);

        when(webSocketFactory.createWebSocket()).thenReturn(webSocket);
        when(channelApi.create(any(MqttDefinition.class))).thenReturn(observable);

        socketClient = new WebSocketClient(channelApi, webSocketFactory);
        socketClient.subscribe(createDevice());
        await();

        verify(webSocketFactory, times(1)).createWebSocket();
        await();

        verify(channelApi, times(1)).create(any(MqttDefinition.class));
        await();

        verify(webSocket, times(1)).createClient(any(MqttChannel.class));
    }

    @Test
    public void webSocketClientUnSubscribeTest() {
        Observable<MqttChannel> observable = new MockBackend(Robolectric.application)
                .createObservable(new TypeToken<MqttChannel>() {
                }, MockBackend.MQTT_CREDENTIALS);

        when(webSocketFactory.createWebSocket()).thenReturn(webSocket);
        when(webSocket.unSubscribe(any(String.class))).thenReturn(true);
        when(channelApi.create(any(MqttDefinition.class))).thenReturn(observable);

        socketClient = new WebSocketClient(channelApi, webSocketFactory);
        socketClient.subscribe(createDevice());
        await();

        assertThat(socketClient.mSocketConnections.isEmpty()).isFalse();

        socketClient.unSubscribe("id");
        await();

        assertThat(socketClient.mSocketConnections.isEmpty()).isTrue();
    }

    private Device createDevice() {
        return new Device("wunderbar2", false, "id", "s", "fv", "o",
                new Model("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8"), "n", "id");
    }
}
