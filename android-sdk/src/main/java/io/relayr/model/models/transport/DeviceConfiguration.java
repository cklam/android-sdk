package io.relayr.model.models.transport;

import java.io.Serializable;

/**
 * Defines configuration parameters to send to the device.
 */
public class DeviceConfiguration implements Serializable {

    private String name;
    private String path;
    private ValueSchema valueSchema;

    /**
     * Configuration name
     */
    public String getName() {
        return name;
    }

    /**
     * Identifies the component to which the configuration should be sent.
     */
    public String getPath() {
        return path;
    }

    /**
     * Defines values and types of values to send as a configuration.
     */
    public ValueSchema getValueSchema() {
        return valueSchema;
    }

    @Override public String toString() {
        return "DeviceConfiguration{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", valueSchema=" + valueSchema.toString() +
                '}';
    }
}
