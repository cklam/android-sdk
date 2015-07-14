package io.relayr.model.groups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.relayr.RelayrSdk;
import io.relayr.model.Device;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class Group implements Serializable, Comparable<Group> {

    private String id;
    private String name;
    private String owner;
    private int position;
    private List<GroupDevice> devices;
    private List<Device> realDevices;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        if (realDevices == null) {
            realDevices = new ArrayList<>();
            for (GroupDevice basic : devices)
                realDevices.add(basic.toDevice());
        }
        return realDevices;
    }

    /**
     * Calls {@link rx.Subscriber#onNext(Object)} if group is updated
     * {@link rx.Subscriber#onError(Throwable)} otherwise.
     * Subscription is necessary to run the method.
     */
    public Observable<Void> update(String name) {
        //TODO Check with Emiliano about this. Currently it updates only one field
        final GroupCreate groupUpdate = new GroupCreate(name, this.owner, position);
        return update(groupUpdate);
    }

    /**
     * Calls {@link rx.Subscriber#onNext(Object)} if group is updated
     * {@link rx.Subscriber#onError(Throwable)} otherwise
     * Subscription is necessary to run the method.
     */
    public Observable<Void> update(int position) {
        final GroupCreate groupUpdate = new GroupCreate(name, this.owner, position);
        return update(groupUpdate);
    }

    /**
     * Updates object locally and calls {@link rx.Subscriber#onNext(Object)} if group is updated
     * {@link rx.Subscriber#onError(Throwable)} otherwise.
     * Subscription is necessary to run the method.
     */
    private Observable<Void> update(GroupCreate groupUpdate) {
        this.name = groupUpdate.name;
        return RelayrSdk.getGroupsApi()
                .updateGroup(groupUpdate, this.id)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Calls {@link rx.Subscriber#onNext(Object)} if device is added to the group.
     * {@link rx.Subscriber#onError(Throwable)} otherwise
     * Subscription is necessary to run the method.
     */
    public Observable<Void> addDevice(String deviceId) {
        return RelayrSdk.getGroupsApi().addDevice(this.id, deviceId)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Calls {@link rx.Subscriber#onNext(Object)} if device is removed
     * {@link rx.Subscriber#onError(Throwable)} otherwise
     * Subscription is necessary to run the method.
     */
    public Observable<Void> removeDevice(String deviceId) {
        return RelayrSdk.getGroupsApi().deleteDevice(this.id, deviceId)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Calls {@link rx.Subscriber#onNext(Object)} if device is moved
     * {@link rx.Subscriber#onError(Throwable)} otherwise
     * Subscription is necessary to run the method.
     */
    public Observable<Void> moveDeviceTo(String deviceId, int newPosition) {
        return RelayrSdk.getGroupsApi()
                .updateDevicePosition(new PositionUpdate(newPosition), this.id, deviceId)
                .observeOn(AndroidSchedulers.mainThread());
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
