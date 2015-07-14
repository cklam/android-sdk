package io.relayr.model;

import java.io.Serializable;

import io.relayr.model.account.AccountType;

public class CreateDevice implements Serializable {

    private final String model;
    private final String owner;
    private final String firmware;
    private final String externalId;
    private String name;
    private String transmitterId;
    private String integrationType;

    /**
     * Used for devices that are owned by transmitter
     */
    public CreateDevice(String name, DeviceModel model, String owner, String externalId, String transmitterId) {
        this.name = name;
        this.model = model.getId();
        this.owner = owner;
        this.externalId = externalId;
        this.transmitterId = transmitterId;
        this.integrationType = AccountType.WUNDERBAR_2.getName();
        this.firmware = "2.0.0";
    }

    public CreateDevice(String name, String modelId, String owner, String externalId, String firmware) {
        this.name = name;
        this.model = modelId;
        this.owner = owner;
        this.externalId = externalId;
        this.firmware = firmware;
    }

    public void setName(String name) {
        this.name = name;
    }
}
