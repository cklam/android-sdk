package io.relayr.model.models.transport;

import java.io.Serializable;

/**
 * Defines formatting for device readings.
 */
public class DeviceReading implements Serializable {

    private String path;
    private String meaning;
    private ValueSchema valueSchema;

    /**
     * Identifies the component from which the reading is originated.
     */
    public String getPath() {
        return path;
    }

    /**
     * Defines meaning of the data coming from the device.
     * Use {@link #getValueSchema()} to get more details about types od objects in device reading.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Defines values and types of reading values.
     */
    public ValueSchema getValueSchema() {
        return valueSchema;
    }

    @Override public String toString() {
        return "DeviceReading{" +
                "path='" + path + '\'' +
                ", meaning='" + meaning + '\'' +
                ", valueSchema=" + valueSchema.toString() +
                '}';
    }
}
