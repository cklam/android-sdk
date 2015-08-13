package io.relayr.ble;

import android.util.Log;

import io.relayr.RelayrSdk;
import io.relayr.model.models.DeviceFirmware;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.error.DeviceModelsException;
import io.relayr.model.models.transport.DeviceReading;
import io.relayr.model.models.transport.Transport;
import io.relayr.storage.DeviceModelCache;

/**
 * A class representing the type of a relayr BLE Device.
 * Currently the available device types are the different type or sensors which are part of the
 * WunderBar: WunderbarHTU, WunderbarGYRO, WunderbarLIGHT, WunderbarMIC, WunderbarBRIDG,
 * WunderbarIR, WunderbarApp, Unknown
 */
public enum BleDeviceType {
    WunderbarHTU("ecf6cf94-cb07-43ac-a85e-dccf26b48c86", "DfuHTU"),
    WunderbarGYRO("173c44b5-334e-493f-8eb8-82c8cc65d29f", "DfuGYRO"),
    WunderbarLIGHT("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8", "DfuLIGHT"),
    WunderbarMIC("4f38b6c6-a8e9-4f93-91cd-2ac4064b7b5a", "DfuMIC"),
    WunderbarBRIDG("ebd828dd-250c-4baf-807d-69d85bed065b", "DfuBRIDG"),
    WunderbarIR("bab45b9c-1c44-4e71-8e98-a321c658df47", "DfuIR"),
    WunderbarApp(null, null),
    WunderbarMM(null, null),
    Unknown(null, null);

    private final String modelId;
    private final String dfuName;

    BleDeviceType(String modelId, String dfuName) {
        this.modelId = modelId;
        this.dfuName = dfuName;
    }

    /** Convert the sensor name advertised in BLE into a device type. Only for Wunderbar devices. */
    public static BleDeviceType getDeviceType(String deviceName) {
        if (deviceName != null) {
            if (deviceName.equals("WunderbarHTU")) return WunderbarHTU;
            if (deviceName.equals("WunderbarGYRO")) return WunderbarGYRO;
            if (deviceName.equals("WunderbarLIGHT")) return WunderbarLIGHT;
            if (deviceName.equals("WunderbarMIC")) return WunderbarMIC;
            if (deviceName.equals("WunderbarBRIDG")) return WunderbarBRIDG;
            if (deviceName.equals("WunderbarIR")) return WunderbarIR;
            if (deviceName.equals("WunderbarApp")) return WunderbarApp;
            if (deviceName.equals("Wunderbar MM")) return WunderbarMM;
        }
        return Unknown;
    }

    public static String getDeviceName(String modelId) {
        for (BleDeviceType value : values())
            if (value.modelId != null && value.modelId.equals(modelId)) return value.dfuName;

        Log.e("BleDfuScanner", "Device model not found!");
        return null;
    }

    /** Only supports Wunderbar devices */
    public static BleDeviceType fromModel(String modelId) {
        final DeviceModel model;
        try {
            model = RelayrSdk.getDeviceModelsCache().getModel(modelId);
            if (model == null) return Unknown;
        } catch (DeviceModelsException e) {
            return Unknown;
        }

        if (!model.getManufacturer().getName().toLowerCase().contains("relayr")) return Unknown;

        DeviceFirmware firmware;
        try {
            firmware = model.getLatestFirmware();
            if (firmware == null) return Unknown;
        } catch (DeviceModelsException e) {
            return Unknown;
        }

        Transport transport;
        try {
            transport = firmware.getTransport("cloud");
        } catch (DeviceModelsException e) {
            return Unknown;
        }

        if (transport.getReadings().isEmpty()) return WunderbarIR;
        for (DeviceReading reading : transport.getReadings()) {
            switch (reading.getMeaning()) {
                case "temperature":
                case "humidity":
                    return WunderbarHTU;
                case "acceleration":
                case "angularSpeed":
                    return WunderbarGYRO;
                case "luminosity":
                case "proximity":
                    return WunderbarLIGHT;
                case "noiseLevel":
                    return WunderbarMIC;
                case "raw":
                    return WunderbarBRIDG;
            }
        }

        return Unknown;
    }

    public static boolean isKnownDevice(String deviceName) {
        return !getDeviceType(deviceName).equals(Unknown);
    }

    /** Returns static modelId for Wunderbar devices */
    public String getModelId() {
        return modelId;
    }
}
