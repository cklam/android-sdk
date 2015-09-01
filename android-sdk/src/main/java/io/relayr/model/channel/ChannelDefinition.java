package io.relayr.model.channel;

import java.io.Serializable;

public class ChannelDefinition implements Serializable {

    private String deviceId;
    private String transport;

    /**
     * Creates channel definition class with default "mqtt" transport type
     */
    public ChannelDefinition(String deviceId) {
        this.deviceId = deviceId;
        this.transport = "mqtt";
    }

    public ChannelDefinition(String deviceId, String transport) {
        this.deviceId = deviceId;
        this.transport = transport;
    }
}
