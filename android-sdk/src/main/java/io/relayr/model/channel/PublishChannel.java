package io.relayr.model.channel;

import io.relayr.model.account.AccountType;

public class PublishChannel extends DataChannel {

    private String id;
    private String secret;
    private String owner;
    private String name;
    private String integrationType;

    public PublishChannel(String channelId, ChannelCredentials credentials, String id,
                          String secret, String owner, String name, String integrationType) {
        super(channelId, credentials);
        this.id = id;
        this.secret = secret;
        this.owner = owner;
        this.name = name;
        this.integrationType = integrationType;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public AccountType getIntegrationType() {
        return AccountType.getByName(integrationType);
    }

    @Override public String toString() {
        return "PublishChannel{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", integrationType='" + integrationType + '\'' +
                "} " + super.toString();
    }
}



