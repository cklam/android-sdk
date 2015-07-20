package io.relayr.model.models.error;

public class DeviceModelsException extends Exception {

    private static final String UNKNOWN_EXCEPTION = "Device models unknown exception!";
    private static final String FIRMWARE_NOT_FOUND = "Device firmware not found!";
    private static final String TRANSPORT_NOT_FOUND = "Device transport not found!";
    private static final String READING_NOT_FOUND = "Device reading not found!";
    private static final String DEVICE_MODEL_NOT_FOUND = "Device model not found!";

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

    public static DeviceModelsException transportNotFound() {
        return new DeviceModelsException(TRANSPORT_NOT_FOUND);
    }

    public static DeviceModelsException deviceReadingNotFound() {
        return new DeviceModelsException(READING_NOT_FOUND);
    }

    public static DeviceModelsException deviceModelNotFound() {
        return new DeviceModelsException(DEVICE_MODEL_NOT_FOUND);
    }
}
