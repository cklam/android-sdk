package io.relayr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.relayr.RelayrSdk;
import io.relayr.ble.BleDeviceType;
import io.relayr.model.account.AccountType;

public class WunderBar implements Serializable {

    public final AccountType type;
    public final Transmitter masterModule;
    public List<TransmitterDevice> wbDevices = new ArrayList<>();

    public WunderBar(Transmitter masterModule, TransmitterDevice gyroscope,
                     TransmitterDevice light, TransmitterDevice microphone,
                     TransmitterDevice thermometer, TransmitterDevice infrared,
                     TransmitterDevice bridge) {
        this.type = AccountType.WUNDERBAR_1;
        this.masterModule = masterModule;
        this.wbDevices = Arrays.asList(gyroscope, light, microphone, thermometer, infrared, bridge);
    }

    public WunderBar(Transmitter masterModule, List<TransmitterDevice> devices, AccountType type) {
        this.type = type == null ? AccountType.WUNDERBAR_1 : type;
        this.masterModule = masterModule;
        wbDevices.addAll(devices);
    }

    //WB2
    public WunderBar(Transmitter masterModule) {
        this(masterModule, new ArrayList<TransmitterDevice>(), AccountType.WUNDERBAR_2);
    }

    public void addDevice(TransmitterDevice device) {
        wbDevices.add(device);
    }

    public static WunderBar from(Transmitter masterModule, List<TransmitterDevice> devices) {
        return new WunderBar(masterModule, devices, masterModule.getAccountType());
    }

    public static WunderBar fromDevices(Transmitter masterModule, List<Device> devices) {
        List<TransmitterDevice> transmitterDevices = new ArrayList<>();
        for (Device device : devices) transmitterDevices.add(device.toTransmitterDevice());

        return new WunderBar(masterModule, transmitterDevices, masterModule.getAccountType());
    }

    public TransmitterDevice getDevice(BleDeviceType type) {
        for (TransmitterDevice device : wbDevices) {
            BleDeviceType bleDeviceType = BleDeviceType.fromModel(device.getModelId());
            if (bleDeviceType == type) return device;
        }

        return null;
    }

    public TransmitterDevice getDevice(String modelId) {
        for (TransmitterDevice device : wbDevices)
            if (device.getModelId().equals(modelId)) return device;

        return null;
    }
}
