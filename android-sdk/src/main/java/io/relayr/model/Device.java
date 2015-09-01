package io.relayr.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.relayr.RelayrSdk;
import io.relayr.ble.BleDevicesCache;
import io.relayr.ble.service.BaseService;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.error.DeviceModelsException;
import rx.Observable;

/**
 * The Device class is a representation of the device entity.
 * A device entity is any external entity capable of gathering measurements
 * or one which is capable of receiving information from the relayr platform.
 * Examples would be a thermometer, a gyroscope or an infrared sensor.
 */
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private final String id;
    private final Model model;
    private final String owner;
    private final String firmwareVersion;
    private final String secret;
    private final String externalId;
    @SerializedName("public") private boolean isPublic;
    @SerializedName("integrationType") private String accountType;

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

    /**
     * Used only for Wunderbar devices.
     */
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
        return RelayrSdk.getWebSocketClient().subscribe(this);
    }

    /**
     * Unsubscribes an app from a device channel, stopping and cleaning up the connection.
     */
    public void unSubscribeToCloudReadings() {
        RelayrSdk.getWebSocketClient().unSubscribe(id);
    }

    /**
     * Sends a command to the this device
     */
    public Observable<Void> sendCommand(Command command) {
        return RelayrSdk.getRelayrApi().sendCommand(id, command);
    }

    /**
     * Sends a configuration to device.
     * Check possible configuration in {@link io.relayr.model.models.DeviceModel}.
     */
    public Observable<Void> sendConfiguration(Configuration configuration) {
        return RelayrSdk.getRelayrApi().setDeviceConfiguration(id, configuration);
    }

    /**
     * Returns {@link DeviceModel} that defines readings, commands and configurations for
     * specific device depending on device firmware version.
     * Use if {@link RelayrSdk#getDeviceModelsCache()} is initialized.
     * @return {@link DeviceModel}
     */
    public DeviceModel getDeviceModel() throws DeviceModelsException {
        return RelayrSdk.getDeviceModelsCache().getModel(getModelId());
    }

    public String getModelId() {
        if (model == null) {
            Log.e("Device", "Device model doesn't exist!");
            return null;
        } else {
            return model.getId();
        }
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TransmitterDevice && ((TransmitterDevice) o).getId().equals(id) ||
                o instanceof Device && ((Device) o).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", model=" + getModelId() +
                ", owner='" + owner + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", secret='" + secret + '\'' +
                ", externalId='" + externalId + '\'' +
                ", isPublic=" + isPublic +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
