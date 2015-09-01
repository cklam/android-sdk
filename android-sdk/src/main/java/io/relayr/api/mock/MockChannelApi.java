package io.relayr.api.mock;

import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import io.relayr.api.ChannelApi;
import io.relayr.model.channel.DataChannel;
import io.relayr.model.channel.ChannelDefinition;
import io.relayr.model.channel.ExistingChannel;
import io.relayr.model.channel.PublishChannel;
import rx.Observable;
import rx.Subscriber;

import static io.relayr.api.mock.MockBackend.MQTT_DEVICE_CREDENTIALS;
import static io.relayr.api.mock.MockBackend.MQTT_CREDENTIALS;

public class MockChannelApi implements ChannelApi {

    private final MockBackend mMockBackend;

    @Inject
    public MockChannelApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override
    public Observable<DataChannel> create(ChannelDefinition mqttDefinition) {
        return mMockBackend.createObservable(new TypeToken<DataChannel>() {
        }, MQTT_CREDENTIALS);
    }

    @Override
    public Observable<Void> delete(String channelId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<ExistingChannel> getChannels(String deviceId) {
        return Observable.empty();
    }

    @Override
    public Observable<PublishChannel> createForDevice(ChannelDefinition mqttDefinition, String deviceId) {
        return mMockBackend.createObservable(new TypeToken<PublishChannel>() {
        }, MQTT_DEVICE_CREDENTIALS);
    }
}
