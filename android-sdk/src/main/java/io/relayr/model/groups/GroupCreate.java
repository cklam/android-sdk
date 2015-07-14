package io.relayr.model.groups;

import java.io.Serializable;

public class GroupCreate implements Serializable {

    final String name;
    private final String owner;
    private final int position;

    /**
     * used for creating new group.
     * @param name     group name
     * @param owner    {@link io.relayr.model.User#id}
     * @param position group position in a list
     */
    public GroupCreate(String name, String owner, int position) {
        this.name = name;
        this.owner = owner;
        this.position = position;
    }
}
