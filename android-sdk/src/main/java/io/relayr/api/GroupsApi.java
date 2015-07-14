package io.relayr.api;

import java.util.List;

import io.relayr.model.groups.Group;
import io.relayr.model.groups.GroupCreate;
import io.relayr.model.groups.PositionUpdate;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface GroupsApi {

    /**
     * Returns list of groups created by user.
     * @return an {@link Observable} with a list of all groups {@link Group}.
     */
    @GET("/experimental/groups") Observable<List<Group>> getGroups();

    /**
     * Creates new group and returns 200 OK if group is successfully created, error otherwise.
     * @param groupCreate {@link GroupCreate}
     * @return an empty {@link Observable}
     */
    @POST("/experimental/groups") Observable<Group> createGroup(@Body GroupCreate groupCreate);

    /**
     * Returns group defined with groupId
     * @param groupId {@link Group#id}
     * @return an {@link Observable}
     */
    @GET("/experimental/groups/{groupId}") Observable<Group> getGroup(
            @Path("groupId") String groupId);

    /**
     * Updates group and returns 200 OK if group is successfully updated, error otherwise.
     * @param group   group object with updated fields
     * @param groupId {@link Group#id}
     * @return an empty {@link Observable}
     */
    @PATCH("/experimental/groups/{groupId}") Observable<Void> updateGroup(
            @Body GroupCreate group, @Path("groupId") String groupId);

    /**
     * Adds device to a group and returns 200 OK if successful, error otherwise.
     * @param groupId  {@link Group#id}
     * @param deviceId {@link io.relayr.model.Device#id}
     * @return an empty {@link Observable}
     */
    @POST("/experimental/groups/{groupId}/devices/{deviceId}")
    Observable<Void> addDevice(@Path("groupId") String groupId, @Path("deviceId") String deviceId);

    /**
     * Deletes device from a group and returns 200 OK if successful, error otherwise.
     * @param groupId  {@link Group#id}
     * @param deviceId {@link io.relayr.model.Device#id}
     * @return an empty {@link Observable}
     */
    @DELETE("/experimental/groups/{groupId}/devices/{deviceId}")
    Observable<Void> deleteDevice(@Path("groupId") String groupId,
                                  @Path("deviceId") String deviceId);

    /**
     * Updates device's position in a group and returns 200 OK if successful, error otherwise.
     * @param groupId  {@link Group#id}
     * @param deviceId {@link io.relayr.model.Device#id}
     * @return an empty {@link Observable}
     */
    @PATCH("/experimental/groups/{groupId}/devices/{deviceId}")
    Observable<Void> updateDevicePosition(@Body PositionUpdate update,
                                          @Path("groupId") String groupId,
                                          @Path("deviceId") String deviceId);

    /**
     * Deletes group and returns 200 OK if group is successfully updated, error otherwise.
     * @param groupId {@link Group#id}
     * @return an empty {@link Observable}
     */
    @DELETE("/experimental/groups/{groupId}") Observable<Void> deleteGroup(
            @Path("groupId") String groupId);

    /**
     * Deletes all groups and returns 200 OK if successful, error otherwise.
     * @return an empty {@link Observable}
     */
    @DELETE("/experimental/groups") Observable<Void> deleteAllGroups();
}
