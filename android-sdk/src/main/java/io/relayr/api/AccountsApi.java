package io.relayr.api;

import java.util.List;

import io.relayr.model.account.Account;
import io.relayr.model.account.AccountDevice;
import io.relayr.model.account.AccountUrl;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface AccountsApi {

    @GET("/accounts") Observable<List<Account>> getAccounts();

    @GET("/accounts/{accountName}/devices") Observable<List<AccountDevice>> getAccountDevices(
            @Path("accountName") String accountName);

    @GET("/accounts/{accountName}/oauthurl")
    Observable<AccountUrl> getLoginUrl(@Path("accountName") String accountName,
                                       @Query("redirect_uri") String redirectUri);

}
