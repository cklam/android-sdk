package io.relayr.android.ble.service.error;

import android.bluetooth.BluetoothGattCharacteristic;

import io.relayr.android.ble.BluetoothGattStatus;

public class WriteCharacteristicException extends Exception {

    public final BluetoothGattCharacteristic characteristic;
    public final int status;

    public WriteCharacteristicException(BluetoothGattCharacteristic characteristic, int status) {
        this.characteristic = characteristic;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return BluetoothGattStatus.toString(status);
    }
}
