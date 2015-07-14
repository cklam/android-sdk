package io.relayr.model.account;

import java.io.Serializable;

public enum AccountType implements Serializable {

    WUNDERBAR_1("wunderbar1"), WUNDERBAR_2("wunderbar2"), UNKNOWN("");

    private final String mTypeName;

    AccountType(String type) {
        this.mTypeName = type;
    }

    public String getName() {
        return mTypeName;
    }

    public static AccountType getByName(String typeName) {
        switch (typeName) {
            case "wunderbar1": return WUNDERBAR_1;
            case "wunderbar2": return WUNDERBAR_2;
            default: return UNKNOWN;
        }
    }
}
