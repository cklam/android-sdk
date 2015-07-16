package io.relayr.model.deviceModels.transport;

import java.io.Serializable;
import java.util.List;

public class Transport implements Serializable {

    private List<DeviceCommand> commands;
    private List<DeviceReading> readings;
    private List<DeviceConfiguration> configurations;

    public List<DeviceCommand> getCommands() {
        return commands;
    }

    public List<DeviceReading> getReadings() {
        return readings;
    }

    public List<DeviceConfiguration> getConfigurations() {
        return configurations;
    }
}
