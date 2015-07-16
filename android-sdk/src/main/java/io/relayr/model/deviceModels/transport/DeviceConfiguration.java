package io.relayr.model.deviceModels.transport;

import java.io.Serializable;

public class DeviceConfiguration implements Serializable{

    private String name;
    private String path;
    private ValueSchema valueSchema;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public ValueSchema getSchema() {
        return valueSchema;
    }
}
