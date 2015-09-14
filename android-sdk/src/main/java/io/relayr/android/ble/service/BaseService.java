package io.relayr.android.ble.service;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.os.Build;

import io.relayr.android.ble.BleDevice;
import rx.Observable;
import rx.functions.Func1;

/**
 * A class representing the basic characteristics of the BLE service a Device should have
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BaseService extends Service {

    protected final BleDevice mBleDevice;

    protected BaseService(BleDevice device, BluetoothGatt gatt, BluetoothGattReceiver receiver) {
        super(gatt, receiver);
        mBleDevice = device;
    }

    public BleDevice getBleDevice() {
        return mBleDevice;
    }

    /**
     * Disconnects and closes the gatt. It should not be called directly use
     * {@link BleDevice#disconnect()} instead.
     * @return an observable of the device that was connected.
     * @hide
     */
    public Observable<BleDevice> disconnect() {
        return mBluetoothGattReceiver
                .disconnect(mBluetoothGatt)
                .map(new Func1<BluetoothGatt, BleDevice>() {
                    @Override
                    public BleDevice call(BluetoothGatt gatt) {
                        return mBleDevice;
                    }
                });
    }

    /**
     * Returns an observable of the Battery Level characteristic.
     * <p>See {@link android.bluetooth.BluetoothGatt#readCharacteristic} for details as to the actions performed in
     * the background.
     * @return an observable of the Battery Level characteristic
     */
    public Observable<Integer> getBatteryLevel() {
        return readByteAsAnIntegerCharacteristic(ShortUUID.SERVICE_BATTERY_LEVEL,
                ShortUUID.CHARACTERISTIC_BATTERY_LEVEL, "Battery Level");
    }

    /**
     * Returns an observable of the Firmware Version characteristic.
     * <p>See {@link android.bluetooth.BluetoothGatt#readCharacteristic} for details as to the actions performed in
     * the background.
     * @return an observable of the Firmware Version characteristic
     */
    public Observable<String> getFirmwareVersion() {
        String text = "Firmware Version";
        return readStringCharacteristic(ShortUUID.SERVICE_DEVICE_INFO, ShortUUID.CHARACTERISTIC_FIRMWARE_VERSION, text);
    }

    /**
     * Returns an observable of the Hardware Version characteristic.
     * <p>See {@link android.bluetooth.BluetoothGatt#readCharacteristic} for details as to the actions performed in
     * the background.
     * @return an observable of the Hardware Version characteristic
     */
    public Observable<String> getHardwareVersion() {
        String text = "Hardware Version";
        return readStringCharacteristic(ShortUUID.SERVICE_DEVICE_INFO, ShortUUID.CHARACTERISTIC_HARDWARE_VERSION, text);
    }

    /**
     * Returns an observable of the Manufacturer characteristic.
     * <p>See {@link android.bluetooth.BluetoothGatt#readCharacteristic} for details as to the actions performed in
     * the background.
     * @return an observable of the Manufacturer characteristic
     */
    public Observable<String> getManufacturer() {
        String text = "Manufacturer";
        return readStringCharacteristic(ShortUUID.SERVICE_DEVICE_INFO, ShortUUID.CHARACTERISTIC_MANUFACTURER, text);
    }

}
