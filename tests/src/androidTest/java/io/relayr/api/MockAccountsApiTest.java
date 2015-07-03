package io.relayr.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.account.Account;
import io.relayr.model.account.AccountDevice;
import io.relayr.model.account.AccountUrl;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;

public class MockAccountsApiTest extends TestEnvironment {

    @Inject AccountsApi api;

    private int numOfObjects = 0;
    private AccountUrl accountUrl;

    @Before
    public void init() {
        super.init();
        inject();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAccountsTest() throws Exception {
        api.getAccounts().subscribe(new Observer<List<Account>>() {
            @Override public void onCompleted() {
                countDown();
            }

            @Override public void onError(Throwable e) {
                countDown();
            }

            @Override public void onNext(List<Account> accounts) {
                numOfObjects = accounts.size();
                countDown();
            }
        });

        await();

        assertThat(numOfObjects).isEqualTo(3);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAccountDevicesTest() throws Exception {
        api.getAccountDevices("accName")
                .subscribe(new Observer<List<AccountDevice>>() {
                    @Override public void onCompleted() {
                        countDown();
                    }

                    @Override public void onError(Throwable e) {
                        countDown();
                    }

                    @Override public void onNext(List<AccountDevice> accountDevices) {
                        numOfObjects = accountDevices.size();
                        countDown();
                    }
                });

        await();
        assertThat(numOfObjects).isEqualTo(2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAccountLoginUrlTest() throws Exception {
        api.getLoginUrl("accName", "redirectUrl")
                .subscribe(new Observer<AccountUrl>() {
                    @Override public void onCompleted() {
                        countDown();
                    }

                    @Override public void onError(Throwable e) {
                        countDown();
                    }

                    @Override public void onNext(AccountUrl url) {
                        accountUrl = url;
                        countDown();
                    }
                });

        await();
        assertThat(accountUrl).isNotNull();
        assertThat(accountUrl.getUrl()).isNotNull();
        assertThat(accountUrl.getUrl()).isNotEmpty();
    }
}
