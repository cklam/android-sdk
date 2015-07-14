package io.relayr.model;

import java.io.Serializable;

public class CreateWunderBar implements Serializable {

    private final Transmitter masterModule;
    private final Device gyroscope;
    private final Device light;
    private final Device microphone;
    private final Device thermometer;
    private final Device infrared;
    private final Device bridge;

    public CreateWunderBar(Transmitter masterModule, Device gyroscope,
                           Device light, Device microphone,
                           Device thermometer, Device infrared,
                           Device bridge) {
        this.masterModule = masterModule;
        this.gyroscope = gyroscope;
        this.light = light;
        this.microphone = microphone;
        this.thermometer = thermometer;
        this.infrared = infrared;
        this.bridge = bridge;
    }

    public WunderBar toWunderBar() {
        return new WunderBar(masterModule, toTransmitterDevice(gyroscope),
                toTransmitterDevice(light), toTransmitterDevice(microphone),
                toTransmitterDevice(thermometer), toTransmitterDevice(infrared),
                toTransmitterDevice(bridge));
    }

    private TransmitterDevice toTransmitterDevice(Device device) {
        return new TransmitterDevice(device.getId(), device.getSecret(), device.getOwner(),
                device.getName(), device.getModel().getId());
    }

    public String getMasterModuleId() {
        return masterModule.id;
    }
}
