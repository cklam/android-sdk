package io.relayr.model;

import java.io.Serializable;

import io.relayr.model.models.transport.DeviceCommand;
import io.relayr.model.models.transport.DeviceConfiguration;

/**
 * Defines configuration parameters currently on device. Defined with {@link DeviceConfiguration}
 * in {@link io.relayr.model.models.DeviceModel}
 */
public class Configuration implements Serializable {

    private String path;
    private Object value;
    private String configuration;

    /**
     * @param path          identifies the component to which the configuration should be sent {@link DeviceConfiguration#getPath()}
     * @param configuration defined in device model {@link DeviceConfiguration#getName()}
     * @param value         type of value object is defined in device model with {@link DeviceConfiguration#getValueSchema()}
     */
    public Configuration(String path, String configuration, Object value) {
        this.path = path;
        this.value = value;
        this.configuration = configuration;
    }

    /**
     * Identifies the component to which the configuration should be sent.
     */
    public String getPath() {
        return path;
    }

    /**
     * Get current configuration value. Type of value object is defined with {@link DeviceConfiguration#getValueSchema()}
     */
    public Object getValue() {
        return value;
    }

}
