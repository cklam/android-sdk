package io.relayr.model.deviceModels.transport;

import java.io.Serializable;

public class DeviceReading implements Serializable {

    private String path;
    private String meaning;
    private ValueSchema valueSchema;

    public String getPath() {
        return path;
    }

    public String getMeaning() {
        return meaning;
    }

    public ValueSchema getValueSchema() {
        return valueSchema;
    }
}
