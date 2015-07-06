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

    /**
     * Returns list of available accounts connectable to the relayr platform.
     * @return an {@link rx.Observable} with a list of all accounts.
     */
    @GET("/accounts") Observable<List<Account>> getAccounts();

    /**
     * Returns list of available devices for the account. Use {@link Account#getDevices()} instead.
     * @param accountName as an identificator {@link Account#name}
     * @return an {@link rx.Observable} with a list of all account devices.
     */
    @GET("/accounts/{accountName}/devices")
    Observable<List<AccountDevice>> getAccountDevices(@Path("accountName") String accountName);

    /**
     * Returns login url for the account.  Use {@link Account#getLoginUrl()} instead.
     * @param accountName as an identificator {@link Account#name}
     * @param redirectUri
     * @return login url
     */
    @GET("/accounts/{accountName}/oauthurl")
    Observable<AccountUrl> getLoginUrl(@Path("accountName") String accountName,
                                       @Query("redirect_uri") String redirectUri);

}
