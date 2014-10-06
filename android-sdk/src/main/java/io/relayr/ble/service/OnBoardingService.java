package io.relayr.ble.service;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;

import io.relayr.ble.BleDevice;
import rx.Observable;
import rx.functions.Func1;

import static io.relayr.ble.service.ShortUUID.CHARACTERISTIC_ON_BOARDING_FLAG;
import static io.relayr.ble.service.ShortUUID.CHARACTERISTIC_PASS_KEY;
import static io.relayr.ble.service.ShortUUID.CHARACTERISTIC_SENSOR_ID;
import static io.relayr.ble.service.ShortUUID.SERVICE_ON_BOARDING;
import static rx.Observable.just;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class OnBoardingService extends BaseService {

    protected OnBoardingService(BleDevice bleDevice, BluetoothDevice device, BluetoothGatt gatt,
                                BluetoothGattReceiver receiver) {
        super(bleDevice, device, gatt, receiver);
    }

    public static Observable<OnBoardingService> connect(final BleDevice bleDevice,
                                                        final BluetoothDevice device) {
        final BluetoothGattReceiver receiver = new BluetoothGattReceiver();
        return doConnect(device, receiver)
                .flatMap(new Func1<BluetoothGatt, Observable<OnBoardingService>>() {
                    @Override
                    public Observable<OnBoardingService> call(BluetoothGatt gatt) {
                        return just(new OnBoardingService(bleDevice, device, gatt, receiver));
                    }
                });
    }

    /**
     * Writes the sensorId characteristic to the associated remote device.
     *
     * @param sensorId Bytes to write on the remote device
     * @return Observable<BluetoothGattCharacteristic>, an observable of what will be written in the
     * device
     */
    public Observable<BluetoothGattCharacteristic> writeSensorId(byte[] sensorId) {
        return write(sensorId, SERVICE_ON_BOARDING, CHARACTERISTIC_SENSOR_ID);
    }

    /**
     * Writes the sensorId characteristic to the associated remote device.
     *
     * @param passKey Bytes to write on the remote device
     * @return Observable<BluetoothGattCharacteristic>, an observable of what will be written in the
     * device
     */
    public Observable<BluetoothGattCharacteristic> writeSensorPassKey(byte[] passKey) {
        return write(passKey, SERVICE_ON_BOARDING, CHARACTERISTIC_PASS_KEY);
    }

    /**
     * Writes the sensorId characteristic to the associated remote device.
     *
     * @param onBoardingFlag Bytes to write on the remote device
     * @return Observable<BluetoothGattCharacteristic>, an observable of what will be written in the
     * device
     */
    public Observable<BluetoothGattCharacteristic> writeOnBoardingFlag(byte[] onBoardingFlag) {
        return write(onBoardingFlag, SERVICE_ON_BOARDING, CHARACTERISTIC_ON_BOARDING_FLAG);
    }

    public void readSensorId() {}
    public void readSensorPassKey() {}
    public void readOnBoardingFlag() {}

}
