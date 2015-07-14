package io.relayr.model.groups;

import java.io.Serializable;

public class PositionUpdate implements Serializable {

    private final int position;

    public PositionUpdate(int position) {
        this.position = position;
    }
}
