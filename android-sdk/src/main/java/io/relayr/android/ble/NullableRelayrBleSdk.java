package io.relayr.android.ble;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.relayr.java.ble.BleDeviceType;
import rx.Observable;
import rx.Subscriber;

class NullableRelayrBleSdk extends RelayrBleSdk {

    @Override
    public Observable<List<BleDevice>> scan(Collection<BleDeviceType> deviceTypes) {
        return Observable.create(new Observable.OnSubscribe<List<BleDevice>>() {
            @Override
            public void call(Subscriber<? super List<BleDevice>> subscriber) {
                subscriber.onNext(Arrays.asList(new BleDevice(null, "Wunderbar MM",
                        BleDeviceMode.NEW_ON_BOARDING, new BleDeviceManager())));
            }
        });
    }

    @Override
    public Observable<List<BleDevice>> scan(Collection<BleDeviceType> types, long period) {
        return Observable.create(new Observable.OnSubscribe<List<BleDevice>>() {
            @Override
            public void call(Subscriber<? super List<BleDevice>> subscriber) {
                subscriber.onNext(Arrays.asList(new BleDevice(null, "Wunderbar MM",
                        BleDeviceMode.NEW_ON_BOARDING, new BleDeviceManager())));
            }
        });
    }

    @Override
    public BleDevice getPairedDevice(String macAddress) {
        return new BleDevice(null, "Wunderbar MM", BleDeviceMode.NEW_ON_BOARDING, new BleDeviceManager());
    }

    @Override
    public void stop() {}

    @Override
    public boolean isScanning() {
        return false;
    }

}
