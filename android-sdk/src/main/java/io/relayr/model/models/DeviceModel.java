package io.relayr.model.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.relayr.model.models.error.DeviceModelsException;

public class DeviceModel implements Serializable {

    private String id;
    private String owner;
    private String name;
    private String productNumber;
    private String description;
    private String website;
    private DeviceManufacturer manufacturer;
    private List<DeviceResource> resources;
    private Map<String, DeviceFirmware> firmware;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public DeviceManufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Return device resources.
     * @return list of {@link DeviceResource}
     */
    public List<DeviceResource> getResources() {
        return resources;
    }

    /**
     * Returns a map with all available firmwares. Map key is Version number.
     * Check available firmwares using {@link #getFirmwareVersions()}
     * @return {@link Map} where key is version number
     */
    public Map<String, DeviceFirmware> getFirmware() {
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

    /**
     * Returns latest version of firmware. Use only if firmware for the device can not be matched.
     * @throws DeviceModelsException
     */
    public DeviceFirmware getLatestFirmware() throws DeviceModelsException {
        if (firmware == null || firmware.isEmpty()) throw DeviceModelsException.firmwareNotFound();
        String version = null;
        for (String firmwareVersion : firmware.keySet())
            if (version == null) version = firmwareVersion;
            else if ((version.replace(".", "")).compareTo(firmwareVersion.replace(".", "")) < 0)
                version = firmwareVersion;

        return getFirmwareByVersion(version);
    }

    @Override public String toString() {
        return "DeviceModel{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", productNumber='" + productNumber + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", manufacturer=" + manufacturer.toString() +
                ", resources=" + resources.toString() +
                ", firmware=" + firmware.toString() +
                '}';
    }

}
