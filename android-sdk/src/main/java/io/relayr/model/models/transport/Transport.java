package io.relayr.model.models.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.relayr.model.models.error.DeviceModelsException;

/**
 * Defines all possible actions user can have with the device.
 * Use for sending commands, setting configurations and formatting the readings.
 */
public class Transport implements Serializable {

    private List<DeviceCommand> commands;
    private List<DeviceReading> readings;
    private List<DeviceConfiguration> configurations;

    /**
     * Returns all possible commands. See {@link DeviceCommand}
     */
    public List<DeviceCommand> getCommands() {
        return commands;
    }

    /**
     * Returns all possible readings. See {@link DeviceReading}
     */
    public List<DeviceReading> getReadings() {
        return readings;
    }

    /**
     * Returns all possible reading meanings as a list.
     */
    public List<String> getReadingMeanings() {
        List<String> meanings = new ArrayList<>();
        for (DeviceReading reading : readings)
            meanings.add(reading.getMeaning());

        return meanings;
    }

    /**
     * Returns reading depending on specified meaning.
     */
    public DeviceReading getReadingByMeaning(String meaning) throws DeviceModelsException {
        for (DeviceReading reading : readings)
            if (reading.getMeaning() != null && reading.getMeaning().equals(meaning))
                return reading;

        throw DeviceModelsException.deviceReadingNotFound();
    }

    /**
     * Returns all possible configurations. See {@link DeviceConfiguration}
     */
    public List<DeviceConfiguration> getConfigurations() {
        return configurations;
    }

    @Override public String toString() {
        return "Transport{" +
                "commands=" + commands.toString() +
                ", readings=" + readings.toString() +
                ", configurations=" + configurations.toString() +
                '}';
    }
}
