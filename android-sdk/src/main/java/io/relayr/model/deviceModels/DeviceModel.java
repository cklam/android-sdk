package io.relayr.model.deviceModels;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.relayr.model.deviceModels.error.DeviceModelsException;

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

    public List<DeviceResource> getResources() {
        return resources;
    }

    public Map<String, DeviceFirmware> getFirmware() {
        return firmware;
    }

    public DeviceFirmware getFirmwareByVersion(String version) throws DeviceModelsException {
        final DeviceFirmware deviceFirmware = firmware.get(version);
        if (deviceFirmware == null) throw DeviceModelsException.firmwareNotFound();
        return deviceFirmware;
    }
}
