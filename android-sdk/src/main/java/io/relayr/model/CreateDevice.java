package io.relayr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.relayr.model.account.AccountType;

public class CreateDevice implements Serializable {

    private String mac;
    private String name;
    private String model;
    private String owner;
    private String firmware;
    private String transmitterId;
    @SerializedName("integrationType") private String accountType;

    public CreateDevice(String name, DeviceModel model, String owner, String mac, String transmitterId) {
        this.name = name;
        this.model = model.getId();
        this.owner = owner;
        this.mac = mac;
        this.transmitterId = transmitterId;
        this.accountType = AccountType.WUNDERBAR_2.getName();
        this.firmware = "2.0.0";
    }

    public CreateDevice(String name, String modelId, String owner, String externalId,
                        String firmware) {
        this.name = name;
        this.model = modelId;
        this.owner = owner;
        this.mac = externalId;
        this.firmware = firmware;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }
}
