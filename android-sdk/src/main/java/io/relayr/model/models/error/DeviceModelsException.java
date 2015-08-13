package io.relayr.model.models.error;

public class DeviceModelsException extends Exception {

    protected static final String UNKNOWN_EXCEPTION = "Device models unknown exception!";
    protected static final String FIRMWARE_NOT_FOUND = "Device firmware not found!";
    protected static final String TRANSPORT_NOT_FOUND = "Device transport not found!";
    protected static final String READING_NOT_FOUND = "Device reading not found!";
    protected static final String DEVICE_MODEL_NOT_FOUND = "Device model not found!";
    protected static final String CACHE_NOT_READY = "Device models cache not ready!";
    protected static final String NULL_MODEL = "Device modelId can not be NULL!";

    public DeviceModelsException() {
        super(UNKNOWN_EXCEPTION);
    }

    public DeviceModelsException(String message) {
        super(message);
    }

    public DeviceModelsException(String message, Throwable t) {
        super(message, t);
    }

    public static DeviceModelsFirmwareException firmwareNotFound() {
        return new DeviceModelsFirmwareException();
    }

    public static DeviceModelsTransportException transportNotFound() {
        return new DeviceModelsTransportException();
    }

    public static DeviceModelsReadingException deviceReadingNotFound() {
        return new DeviceModelsReadingException();
    }

    public static DeviceModelsNotFoundException deviceModelNotFound() {
        return new DeviceModelsNotFoundException();
    }

    public static DeviceModelsCacheException cacheNotReady() {
        return new DeviceModelsCacheException();
    }

    public static DeviceModelsNotFoundException nullModelId() {
        return new DeviceModelsNotFoundException();
    }
}
