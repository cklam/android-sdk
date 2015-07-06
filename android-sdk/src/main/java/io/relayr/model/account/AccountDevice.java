package io.relayr.model.account;

import java.io.Serializable;

public class AccountDevice  implements Serializable {

    private String externalId;
    private String modelId;
    private AccountDeviceExtra extra;

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

    class AccountDeviceExtra  implements Serializable{
        String name;
        String firmwareVersion;

        public AccountDeviceExtra(String name, String firmwareVersion) {
            this.name = name;
            this.firmwareVersion = firmwareVersion;
        }
    }

}
