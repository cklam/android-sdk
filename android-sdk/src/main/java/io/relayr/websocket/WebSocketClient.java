package io.relayr.websocket;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.relayr.SocketClient;
import io.relayr.api.ChannelApi;
import io.relayr.model.DataPackage;
import io.relayr.model.Device;
import io.relayr.model.Reading;
import io.relayr.model.channel.ChannelDefinition;
import io.relayr.model.channel.DataChannel;
import io.relayr.model.channel.PublishChannel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@Singleton
public class WebSocketClient implements SocketClient {

    final ChannelApi mChannelApi;
    final WebSocket<DataChannel> mWebSocket;
    final Map<String, DataChannel> mDeviceChannels = new HashMap<>();
    final Map<String, PublishSubject<Reading>> mSocketConnections = new HashMap<>();

    @Inject
    public WebSocketClient(ChannelApi channelApi, WebSocketFactory factory) {
        mChannelApi = channelApi;
        mWebSocket = factory.createWebSocket();
    }

    @Override
    public Observable<Reading> subscribe(Device device) {
        if (mSocketConnections.containsKey(device.getId()))
            return mSocketConnections.get(device.getId());
        else
            return start(device.getId());
    }

    @Override
    public Observable<Void> publish(final String deviceId, final Object payload) {
        Observable<Void> observable = Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(final Subscriber<? super Void> subscriber) {
                if (mDeviceChannels.containsKey(deviceId))
                    publish(deviceId, payload, subscriber);
                else
                    mChannelApi.createForDevice(new ChannelDefinition(deviceId, "mqtt"), deviceId)
                            .subscribe(new Observer<PublishChannel>() {
                                @Override public void onCompleted() {}

                                @Override public void onError(Throwable e) {
                                    mDeviceChannels.remove(deviceId);
                                    subscriber.onError(e);
                                }

                                @Override public void onNext(PublishChannel channel) {
                                    if (!mDeviceChannels.containsKey(deviceId))
                                        mDeviceChannels.put(deviceId, channel);
                                    publish(deviceId, payload, subscriber);
                                }
                            });
            }
        });

        observable.subscribe();
        return observable;
    }

    private void publish(String deviceId, Object payload, Subscriber<? super Void> subscriber) {
        try {
            mWebSocket.publish(mDeviceChannels.get(deviceId).getCredentials().getTopic(),
                    new Gson().toJson(payload));
            subscriber.onNext(null);
        } catch (MqttException e) {
            subscriber.onError(e);
        }
    }

    private synchronized Observable<Reading> start(final String deviceId) {
        final PublishSubject<Reading> subject = PublishSubject.create();
        mSocketConnections.put(deviceId, subject);

        mChannelApi.create(new ChannelDefinition(deviceId, "mqtt"))
                .flatMap(new Func1<DataChannel, Observable<DataChannel>>() {
                    @Override
                    public Observable<DataChannel> call(final DataChannel channel) {
                        return mWebSocket.createClient(channel);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<DataChannel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mSocketConnections.remove(deviceId);
                    }

                    @Override
                    public void onNext(DataChannel channel) {
                        subscribeToChannel(channel, deviceId, subject);
                    }
                });

        return subject.observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        unSubscribe(deviceId);
                    }
                });
    }

    private void subscribeToChannel(final DataChannel channel, final String deviceId,
                                    final PublishSubject<Reading> subject) {
        mWebSocket.subscribe(channel.getCredentials().getTopic(), channel.getChannelId(), new WebSocketCallback() {
            @Override
            public void connectCallback(Object message) {
                if (!mDeviceChannels.containsKey(deviceId))
                    mDeviceChannels.put(deviceId, channel);
            }

            @Override
            public void disconnectCallback(Object message) {
                subject.onError((Throwable) message);
                mDeviceChannels.remove(deviceId);
                mSocketConnections.remove(deviceId);
            }

            @Override
            public void successCallback(Object message) {
                DataPackage dataPackage = new Gson().fromJson(message.toString(), DataPackage.class);
                for (DataPackage.Data dataPoint : dataPackage.readings) {
                    subject.onNext(new Reading(dataPackage.received, dataPoint.recorded,
                            dataPoint.meaning, dataPoint.path, dataPoint.value));
                }
            }

            @Override
            public void errorCallback(Throwable e) {
                subject.onError(e);
                mDeviceChannels.remove(deviceId);
                mSocketConnections.remove(deviceId);
            }
        });
    }

    @Override
    public void unSubscribe(final String deviceId) {
        if (mSocketConnections.containsKey(deviceId)) {
            mSocketConnections.get(deviceId).onCompleted();
            mSocketConnections.remove(deviceId);
        }

        if (!mDeviceChannels.isEmpty() && mDeviceChannels.containsKey(deviceId))
            if (mWebSocket.unSubscribe(mDeviceChannels.get(deviceId).getCredentials().getTopic()))
                mDeviceChannels.remove(deviceId);
    }
}

