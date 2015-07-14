package io.relayr.model.account;

import java.io.Serializable;

public class AccountDevice implements Serializable {

    private final String externalId;
    private final String modelId;
    private final AccountDeviceExtra extra;

    public AccountDevice(String externalId, String modelId, AccountDeviceExtra extra) {
        this.externalId = externalId;
        this.modelId = modelId;
        this.extra = extra;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getModelId() {
        return modelId;
    }

    public String getName() {
        return extra.name;
    }

    public String getFirmwareVersion() {
        return extra.firmwareVersion;
    }

    class AccountDeviceExtra implements Serializable {
        final String name;
        final String firmwareVersion;

        public AccountDeviceExtra(String name, String firmwareVersion) {
            this.name = name;
            this.firmwareVersion = firmwareVersion;
        }
    }

}
