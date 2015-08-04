package io.relayr.api;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import io.relayr.model.App;
import io.relayr.model.Command;
import io.relayr.model.CreateDevice;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import io.relayr.model.TransmitterDevice;
import io.relayr.model.User;
import io.relayr.model.Configuration;
import io.relayr.model.onboarding.OnBoardingState;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;

import static io.relayr.api.MockBackend.APP_INFO;
import static io.relayr.api.MockBackend.PUBLIC_DEVICES;
import static io.relayr.api.MockBackend.TRANSMITTER_DEVICES;
import static io.relayr.api.MockBackend.USERS_TRANSMITTER;
import static io.relayr.api.MockBackend.USER_DEVICE;
import static io.relayr.api.MockBackend.USER_INFO;

public class MockRelayrApi implements RelayrApi {

    private final MockBackend mMockBackend;

    @Inject public MockRelayrApi(MockBackend mockBackend) {
        mMockBackend = mockBackend;
    }

    @Override
    public Observable<App> getAppInfo() {
        return mMockBackend.createObservable(new TypeToken<App>() {
        }, APP_INFO);
    }

    @Override
    public Observable<User> getUserInfo() {
        return mMockBackend.createObservable(new TypeToken<User>() {}, USER_INFO);
    }

    @Override
    public Observable<Void> sendCommand(String deviceId, Command command) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public Observable<Void> deleteDevice(String deviceId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public Observable<Device> updateDevice(Device device, String deviceId) {
        return Observable.just(device);
    }

    @Override
    public Observable<Transmitter> getTransmitter(String transmitter) {
        return mMockBackend.createObservable(new TypeToken<Transmitter>() {
        }, USERS_TRANSMITTER);
    }

    @Override
    public Observable<Transmitter> updateTransmitter(Transmitter transmitter, String id) {
        return Observable.just(transmitter);
    }

    @Override
    public Observable<List<TransmitterDevice>> getTransmitterDevices(String transmitter) {
        return mMockBackend.createObservable(new TypeToken<List<TransmitterDevice>>() { },
                TRANSMITTER_DEVICES);
    }

    @Override
    public Observable<Transmitter> registerTransmitter(Transmitter transmitter) {
        return Observable.just(transmitter);
    }

    @Override
    public Observable<List<Device>> getPublicDevices(String meaning) {
        return mMockBackend.createObservable(new TypeToken<List<Device>>() {
        }, PUBLIC_DEVICES);
    }

    @Override
    public Observable<Void> deleteWunderBar(String transmitterId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public Observable<Configuration> getDeviceConfiguration(String deviceId, String path) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> setDeviceConfiguration(String deviceId, Configuration configuration) {
        return Observable.empty();
    }

    @Override
    public Observable<Void> deleteTransmitter(String transmitterId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public Observable<OnBoardingState> isTransmitterConnected(String transmitterId) {
        return Observable.empty();
    }

    @Override
    public Observable<OnBoardingState> isDeviceConnected(String deviceId) {
        return Observable.empty();
    }

    @Override
    public Observable<Response> scanForDevices(String transmitterId, int period) {
        return Observable.empty();
    }

    @Override
    public Observable<Device> createDevice(CreateDevice device) {
        return mMockBackend.createObservable(new TypeToken<Device>() { },
                USER_DEVICE);
    }
}
