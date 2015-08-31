package io.relayr.model;

import java.io.Serializable;
import java.util.List;

import io.relayr.RelayrSdk;
import io.relayr.model.account.Account;
import io.relayr.model.groups.Group;
import rx.Observable;

/**
 * The first basic entity in the relayr platform is the user.
 * Every user registers with an email address,
 * a respective name and password and is assigned a unique userId.
 * A user can be both an application owner (a publisher) and an end user.
 * A user is required in order to add other entities to the relayr platform.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return an {@link rx.Observable} of a list of devices registered under a user.
     */
    public Observable<List<Device>> getDevices() {
        return RelayrSdk.getUserApi().getDevices(id);
    }

    /**
     * @return an {@link rx.Observable} with a list all Transmitters listed under a user.
     */
    public Observable<List<Transmitter>> getTransmitters() {
        return RelayrSdk.getUserApi().getTransmitters(id);
    }

    /**
     * Returns a list of devices bookmarked by the user.
     * @return an {@link rx.Observable} with a list of the users bookmarked devices
     */
    public Observable<List<BookmarkDevice>> getBookmarkedDevices() {
        return RelayrSdk.getUserApi().getBookmarkedDevices(id);
    }

    /**
     * Returns a list of third party accounts that user connected with relayr platform.
     * @return an {@link rx.Observable} with a list of {@link Account}
     */
    public Observable<List<Account>> getAccounts() {
        return RelayrSdk.getUserApi().getAccounts(id);
    }

    /**
     * Returns error if account is not connected.
     * @return an empty {@link rx.Observable}
     */
    public Observable<Void> isAccountConnected(String accountName) {
        return RelayrSdk.getUserApi().isAccountConnected(id, accountName);
    }

    /**
     * Returns error if account is not successfully disconnected.
     * @return an empty {@link rx.Observable}
     */
    public Observable<Void> disconnectAccount(String accountName) {
        return RelayrSdk.getUserApi().disconnectAccount(id, accountName);
    }

    /**
     * Returns list of {@link Group} objects
     * @return an {@link rx.Observable} list
     */
    public Observable<List<Group>> getGroups() {
        return RelayrSdk.getUserApi().getGroups(id);
    }

    /**
     * Updates username.
     * @param username new username
     */
    public Observable<User> update(String username) {
        this.setName(username);
        return RelayrSdk.getUserApi().updateUserDetails(this, id);
    }

    /**
     * Api call to tell the backend to create a WunderBar.
     * @return an {@link rx.Observable} to a WunderBar that contains the IDs and Secrets of the
     * Master Module and Sensor Modules.
     */
    public Observable<CreateWunderBar> createWunderBar() {
        return RelayrSdk.getUserApi().createWunderBar(id);
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + "\'" +
                ", name='" + name + "\'" +
                ", email='" + email + "\'" +
                "}";
    }
}
