package io.relayr.model.groups;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.relayr.model.Device;
import io.relayr.model.Model;

class GroupDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String model;
    private final String owner;
    private final String firmwareVersion;
    private final String secret;
    private final String externalId;
    private final int position;
    @SerializedName("public") private final boolean isPublic;
    @SerializedName("integrationType") private final String accountType;

    public GroupDevice(String accountType, boolean isPublic, String externalId, String secret,
                       String firmwareVersion, String owner, String model, String name, String id, int position) {
        this.accountType = accountType;
        this.isPublic = isPublic;
        this.externalId = externalId;
        this.secret = secret;
        this.firmwareVersion = firmwareVersion;
        this.owner = owner;
        this.model = model;
        this.name = name;
        this.id = id;
        this.position = position;
    }

    public Device toDevice() {
        Model oldModel = new Model(model);
        return new Device(accountType, isPublic, externalId, secret, firmwareVersion, owner, oldModel, name, id);
    }

    public int getPosition() {
        return position;
    }
}
