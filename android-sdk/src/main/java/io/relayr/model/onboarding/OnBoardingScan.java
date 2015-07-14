package io.relayr.model.onboarding;

import java.io.Serializable;

public class OnBoardingScan implements Serializable {

    private final int rssi;
    private final String announceName;
    private final String macAddress;
    private final String deviceModelId;

    public OnBoardingScan(String model, String mac, int rssi, String announceName) {
        this.deviceModelId = model;
        this.macAddress = mac;
        this.rssi = rssi;
        this.announceName = announceName;
    }

    public int getRssi() {
        return rssi;
    }

    public String getMac() {
        return macAddress;
    }

    public String getModelId() {
        return deviceModelId;
    }

    public String getName() {
        return announceName == null ? macAddress : announceName;
    }

    @Override public String toString() {
        return "OnBoardingScan{" +
                "rssi=" + rssi +
                ", mac='" + macAddress + '\'' +
                ", model='" + deviceModelId + '\'' +
                '}';
    }
}
