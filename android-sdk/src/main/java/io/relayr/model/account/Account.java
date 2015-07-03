package io.relayr.model.account;

public class Account {

    private String name;
    private String readableName;
    private String oauthUrl;

    public Account(String name, String readableName, String oauthUrl) {
        this.name = name;
        this.readableName = readableName;
        this.oauthUrl = oauthUrl;
    }

    public String getName() {
        return name;
    }

    public String getReadableName() {
        return readableName;
    }

    public String getOauthUrl() {
        return oauthUrl;
    }
}
