package io.relayr.android.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.relayr.android.TestEnvironment;
import io.relayr.java.api.CloudApi;
import io.relayr.java.model.Status;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ReachabilityUtilTest extends TestEnvironment {

    @Mock private CloudApi api;

    private boolean reachable;
    private io.relayr.android.util.ReachabilityUtils utils;

    @Before
    public void init() {
        super.init();
        utils = new io.relayr.android.util.ReachabilityUtils(api);
    }

    @Test
    public void checkInternetConnectionTest() {
        assertThat(utils.isConnectedToInternet()).isTrue();
    }

    @Test
    public void checkPlatformAvailabilityTest() {
        reachable = false;

        when(api.getServerStatus()).thenReturn(Observable.create(new Observable.OnSubscribe<Status>() {
            @Override
            public void call(Subscriber<? super Status> subscriber) {
                subscriber.onNext(new Status("ok"));
            }
        }));

        utils.isPlatformAvailable().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean status) {
                reachable = status;
            }
        });

        await();
        assertThat(reachable).isTrue();
    }

    @Test
    public void checkPlatformReachAbilityTest() {
        reachable = false;

        when(api.getServerStatus()).thenReturn(Observable.create(new Observable.OnSubscribe<Status>() {
            @Override
            public void call(Subscriber<? super Status> subscriber) {
                subscriber.onNext(new Status("ok"));
            }
        }));

        utils.isPlatformReachable().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean status) {
                reachable = status;
            }
        });

        await();
        assertThat(reachable).isTrue();
    }

    @Test
    public void checkWhenPlatformNotAvailableTest() {
        reachable = true;

        when(api.getServerStatus()).thenReturn(Observable.create(new Observable.OnSubscribe<Status>() {
            @Override
            public void call(Subscriber<? super Status> subscriber) {
                subscriber.onError(new Throwable());
            }
        }));

        utils.isPlatformReachable().subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                reachable = false;
            }

            @Override
            public void onNext(Boolean status) {
            }
        });

        await();
        assertThat(reachable).isFalse();
    }

    @Test
    public void checkPermissionTest() {
        final String PERMISSION_INTERNET = "android.permission.INTERNET";

        assertThat(utils.isPermissionGranted(PERMISSION_INTERNET)).isTrue();
    }

    @Test
    public void checkFaultyPermissionTest() {
        final String PERMISSION_INTERNET = "";

        assertThat(utils.isPermissionGranted(PERMISSION_INTERNET)).isFalse();
    }

    @Test
    public void checkUnExistingPermissionTest() {
        final String PERMISSION_INTERNET = "android.permission.ACCESS_WIFI_STATE";

        assertThat(utils.isPermissionGranted(PERMISSION_INTERNET)).isFalse();
    }
}
