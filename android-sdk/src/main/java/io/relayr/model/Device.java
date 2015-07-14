package io.relayr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.relayr.RelayrSdk;
import io.relayr.ble.BleDevicesCache;
import io.relayr.ble.service.BaseService;
import io.relayr.model.account.AccountType;
import rx.Observable;

/**
 * The Device class is a representation of the device entity.
 * A device entity is any external entity capable of gathering measurements
 * or one which is capable of receiving information from the relayr platform.
 * Examples would be a thermometer, a gyroscope or an infrared sensor.
 */
public class Device implements Serializable {

    /** Auto generated uid */
    private static final long serialVersionUID = 1L;
    private final String id;
    private String name;
    private final Model model;
    private final String owner;
    private final String firmwareVersion;
    private final String secret;
    private final String externalId;
    @SerializedName("public") protected boolean isPublic;
    @SerializedName("integrationType") protected String accountType;

    public Device(String accountType, boolean isPublic, String externalId, String secret,
                  String firmwareVersion, String owner, Model model, String name, String id) {
        this.accountType = accountType;
        this.isPublic = isPublic;
        this.externalId = externalId;
        this.secret = secret;
        this.firmwareVersion = firmwareVersion;
        this.owner = owner;
        this.model = model;
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Relayr_Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", model=" + model +
                ", owner='" + owner + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", secret='" + secret + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    public TransmitterDevice toTransmitterDevice() {
        return new TransmitterDevice(id, secret, owner, name, model.getId());
    }

    public Observable<BaseService> getSensorForDevice(BleDevicesCache cache) {
        return cache.getSensorForDevice(toTransmitterDevice());
    }

    /**
     * Subscribes an app to a BLE device. Enables the app to receive data from the device over
     * BLE through {@link io.relayr.ble.service.DirectConnectionService}
     */
    public Observable<Reading> subscribeToBleReadings(final BleDevicesCache cache) {
        return toTransmitterDevice().subscribeToBleReadings(cache);
    }

    /**
     * Subscribes an app to a device channel. Enables the app to receive data from the device.
     */
    public Observable<Reading> subscribeToCloudReadings() {
        return RelayrSdk.getWebSocketClient().subscribe(toTransmitterDevice());
    }

    /**
     * Unsubscribes an app from a device channel, stopping and cleaning up the connection.
     */
    public void unSubscribeToCloudReadings() {
        RelayrSdk.getWebSocketClient().unSubscribe(id);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TransmitterDevice && ((TransmitterDevice) o).id.equals(id) ||
                o instanceof Device && ((Device) o).id.equals(id);
    }

    /** Sends a command to the this device */
    public Observable<Void> sendCommand(Command command) {
        return RelayrSdk.getRelayrApi().sendCommand(id, command);
    }

    public Model getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public String getSecret() {
        return secret;
    }

    public String getExternalId() {
        return externalId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountType() {
        return accountType;
    }
}
