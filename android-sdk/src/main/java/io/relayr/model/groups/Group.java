package io.relayr.model.groups;

import java.io.Serializable;
import java.util.List;

import io.relayr.RelayrSdk;
import io.relayr.model.Device;
import rx.schedulers.Schedulers;

public class Group implements Serializable, Comparable<Group> {

    private String id;
    private String name;
    private String owner;
    private int position;
    private List<Device> devices;

    public Group(String id, String name, String owner, int position, List<Device> devices) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.position = position;
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void update(String name) {
        update(name, this.position);
    }

    public void update(String name, int position) {
        update(name, position, this.devices);
    }

    public void update(String name, int position, List<Device> devices) {
        this.name = name;
        this.position = position;
        this.devices = devices;
        final Group newGroup = new Group(this.id, name, this.owner, position, devices);

        RelayrSdk.getGroupsApi()
                .updateGroup(newGroup, this.id)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        return id.equals(group.id);
    }

    @Override public int hashCode() {
        return id.hashCode();
    }

    @Override public int compareTo(Group another) {
        return this.position - another.position;
    }
}
