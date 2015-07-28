package io.relayr.model.models.transport;

import java.io.Serializable;

/**
 * Defines command parameters to send to the device.
 */
public class DeviceCommand implements Serializable {

    private String name;
    private String path;
    private ValueSchema valueSchema;

    /**
     * Command name
     */
    public String getName() {
        return name;
    }

    /**
     * Identifies the component to which the command should be sent.
     */
    public String getPath() {
        return path;
    }

    /**
     * Defines values and types of values to send as a command.
     */
    public ValueSchema getValueSchema() {
        return valueSchema;
    }

    @Override public String toString() {
        return "DeviceCommand{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", valueSchema=" + valueSchema.toString() +
                '}';
    }
}
