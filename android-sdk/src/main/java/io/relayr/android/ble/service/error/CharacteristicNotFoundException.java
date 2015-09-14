package io.relayr.android.ble.service.error;

public class CharacteristicNotFoundException extends Exception {

    public CharacteristicNotFoundException(String characteristic) {
        super(characteristic + " Characteristic not found.");
    }
}
