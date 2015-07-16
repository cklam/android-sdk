package io.relayr.model.deviceModels.error;

public class DeviceModelsException extends Exception {

    private static final String UNKNOWN_EXCEPTION = "Device models unknown exception!";
    private static final String FIRMWARE_NOT_FOUND = "Device firmware %s not found!";

    public DeviceModelsException() {
        super(UNKNOWN_EXCEPTION);
    }

    public DeviceModelsException(String message) {
        super(message);
    }

    public DeviceModelsException(String message, Throwable t) {
        super(message, t);
    }

    public static DeviceModelsException firmwareNotFound() {
        return new DeviceModelsException(FIRMWARE_NOT_FOUND);
    }
}
