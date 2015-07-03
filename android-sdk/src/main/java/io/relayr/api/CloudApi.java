package io.relayr.api;

import java.util.List;

import io.relayr.model.LogEvent;
import io.relayr.model.Status;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface CloudApi {

    @POST("/client/log") Observable<Void> logMessage(@Body List<LogEvent> events);

    /**
     * Checks whether server is up
     * @return an {@link rx.Observable} with String status of the server
     */
    @GET("/server-status") Observable<Status> getServerStatus();
}
