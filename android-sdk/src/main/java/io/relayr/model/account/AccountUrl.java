package io.relayr.model.account;

import java.io.Serializable;

public class AccountUrl implements Serializable {

    private final String url;

    public AccountUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
