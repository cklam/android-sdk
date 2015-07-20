package io.relayr.model.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.relayr.model.models.error.DeviceModelsException;

public class DeviceFirmwares implements Serializable {

    private ModelLinks _links;
    private Map<String, DeviceFirmware> firmware;

    /**
     * Returns {@link ModelLinks} object with hyperlinks to other firmware versions
     * @return {@link ModelLinks}
     */
    public ModelLinks getLinks() {
        return _links;
    }

    /**
     * Returns a map with all available firmwares. Map key is Version number.
     * Check available firmwares using {@link #getFirmwareVersions()}
     * @return {@link Map} where key is version number
     */
    public Map<String, DeviceFirmware> getFirmwares() {
        return firmware;
    }

    /**
     * Returns all available firmware versions
     * @return {@link List} of firmware versions
     */
    public List<String> getFirmwareVersions() {
        return new ArrayList<>(firmware.keySet());
    }

    /**
     * Returns {@link DeviceFirmware} for specified version
     * @param version of firmware
     * @return {@link DeviceFirmware}
     * @throws DeviceModelsException if firmware is not found
     */
    public DeviceFirmware getFirmwareByVersion(String version) throws DeviceModelsException {
        final DeviceFirmware deviceFirmware = firmware.get(version);
        if (deviceFirmware == null) throw DeviceModelsException.firmwareNotFound();
        return deviceFirmware;
    }

    @Override public String toString() {
        return "DeviceFirmwares{" +
                "links=" + _links +
                ", firmware=" + firmware.toString() +
                '}';
    }
}
