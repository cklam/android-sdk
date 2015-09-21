package io.relayr.android.ble;

import java.util.HashSet;
import java.util.List;

import io.relayr.android.RelayrSdk;
import io.relayr.android.ble.service.BaseService;
import io.relayr.android.ble.service.DirectConnectionService;
import io.relayr.java.ble.BleDeviceType;
import io.relayr.java.model.Device;
import io.relayr.java.model.action.Reading;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static java.util.Arrays.asList;

public class BleSocketClient {

    public Observable<Reading> subscribe(Device device) {
        return RelayrSdk.getRelayrBleSdk()
                .scan(new HashSet<>(asList(BleDeviceType.fromModel(device.getModelId()))))
                .flatMap(new Func1<List<BleDevice>, Observable<BleDevice>>() {
                    @Override
                    public Observable<BleDevice> call(final List<BleDevice> bleDevices) {
                        return Observable.create(new Observable.OnSubscribe<BleDevice>() {
                            @Override
                            public void call(Subscriber<? super BleDevice> subscriber) {
                                // TODO: read BleDevice sensorID characteristic,
                                // TODO: compare it with the TransmitterDevice and filter them out
                                for (BleDevice bleDevice : bleDevices)
                                    if (bleDevice.getMode().equals(BleDeviceMode.DIRECT_CONNECTION))
                                        subscriber.onNext(bleDevice);
                            }
                        });
                    }
                })
                .flatMap(new Func1<BleDevice, Observable<? extends BaseService>>() {
                    @Override
                    public Observable<? extends BaseService> call(BleDevice bleDevice) {
                        return bleDevice.connect();
                    }
                })
                .flatMap(new Func1<BaseService, Observable<Reading>>() {
                    @Override
                    public Observable<Reading> call(BaseService baseService) {
                        DirectConnectionService service = (DirectConnectionService) baseService;
                        return service.getReadings();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void unSubscribe(String sensorId) {
        //No implementation
    }

    public Observable<Void> publish(String deviceId, Reading payload) {
        return Observable.empty();
    }

}
