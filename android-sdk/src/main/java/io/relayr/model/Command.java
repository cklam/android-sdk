package io.relayr.model;

import io.relayr.model.models.transport.DeviceCommand;
import io.relayr.model.models.transport.DeviceConfiguration;

/**
 * Defines command to be sent to device.
 */
public class Command {

    final private String path;
    final private String command;
    final private Object value;

    /**
     * @param path    identifies the component to which the command should be sent {@link DeviceCommand#getPath()}
     * @param command defined in device model {@link DeviceCommand#getName()}
     * @param value   type of value object is defined in device model with {@link DeviceConfiguration#getValueSchema()}
     */
    public Command(String path, String command, Object value) {
        this.path = path;
        this.command = command;
        this.value = value;
    }
}