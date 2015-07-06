package io.relayr.api;

import java.util.List;

import io.relayr.model.Bookmark;
import io.relayr.model.BookmarkDevice;
import io.relayr.model.CreateWunderBar;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import io.relayr.model.User;
import io.relayr.model.account.Account;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface UserApi {

    /**
     * Returns user devices. Use {@link User#getDevices()} on fetched {@link User} object.
     * @param userId
     * @return an {@link rx.Observable} of a list of devices registered under a user.
     */
    @GET("/users/{userId}/devices")
    Observable<List<Device>> getUserDevices(@Path("userId") String userId);

    /**
     * Api call to tell the backend to create WunderBar.
     * Use {@link User#createWunderBar()} on fetched {@link User} object.
     * @param userId
     * @return an {@link rx.Observable} to a WunderBar that contains the IDs and Secrets of the
     * Master Module and Sensor Modules.
     */
    @POST("/users/{userId}/wunderbar")
    Observable<CreateWunderBar> createWunderBar(@Path("userId") String userId);

    /**
     * Returns user transmitters. Use {@link User#getTransmitters()} on fetched {@link User} object.
     * @param userId
     * @return an {@link rx.Observable} with a list all Transmitters listed under a user.
     */
    @GET("/users/{userId}/transmitters")
    Observable<List<Transmitter>> getTransmitters(@Path("userId") String userId);

    /**
     * Bookmarks a specific public device. By Bookmarking a device you are indicating that you have
     * a particular interest in this device. In order to receive data from a bookmarked device,
     * the subscription call must first be initiated.
     * @param userId   id of the user that is bookmarking the device
     * @param deviceId id of bookmarked device - the Id must be one of a public device
     * @return an {@link rx.Observable} to the bookmarked device
     */
    @POST("/users/{userId}/devices/{deviceId}/bookmarks")
    Observable<Bookmark> bookmarkPublicDevice(@Path("userId") String userId,
                                              @Path("deviceId") String deviceId);

    /**
     * Deletes a bookmarked device.
     * @param userId   id of the user that bookmarked the device
     * @param deviceId id of bookmarked device - the Id must be one of a public device
     * @return an empty {@link rx.Observable}
     */
    @DELETE("/users/{userId}/devices/{deviceId}/bookmarks")
    Observable<Void> deleteBookmark(@Path("userId") String userId,
                                    @Path("deviceId") String deviceId);

    /**
     * Returns a list of devices bookmarked by the user.
     * @param userId id of the user that bookmarked devices
     * @return an {@link rx.Observable} with a list of the users bookmarked devices
     */
    @GET("/users/{userId}/devices/bookmarks")
    Observable<List<BookmarkDevice>> getBookmarkedDevices(@Path("userId") String userId);

    /**
     * Returns a list of accounts that user connected to the relayr account.
     * Use {@link User#getUserAccounts()} on fetched {@link User} object.
     * @param userId id of the user
     * @return an {@link rx.Observable} with a list of accounts {@link Account}
     */
    @GET("/users/{userId}/accounts")
    Observable<List<Account>> getUserAccounts(@Path("userId") String userId);

    /**
     * Returns 200 OK if named account is connected to the users relayr account
     * Use {@link User#isAccountConnected(String)} on fetched {@link User} object.
     * @param userId      id of the user
     * @param accountName name of the connected account {@link Account#name}
     * @return an empty {@link rx.Observable}
     */
    @GET("/users/{userId}/accounts/{accountName}/isconnected")
    Observable<Void> isAccountConnected(@Path("userId") String userId,
                                        @Path("accountName") String accountName);

    /**
     * Returns 200 OK if account is disconnected
     * Use {@link User#disconnectAccount(String)} on fetched {@link User} object.
     * @param userId      id of the user
     * @param accountName name of the connected account {@link Account#name}
     * @return an empty {@link rx.Observable}
     */
    @POST("/users/{userId}/accounts/{accountName}/disconnect")
    Observable<Void> disconnectAccount(@Path("userId") String userId,
                                       @Path("accountName") String accountName);
}
