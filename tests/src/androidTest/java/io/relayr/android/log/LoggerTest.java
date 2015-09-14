package io.relayr.android.log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import java.util.concurrent.TimeUnit;

import io.relayr.android.RelayrSdk;
import io.relayr.android.TestEnvironment;
import io.relayr.java.api.CloudApi;
import io.relayr.android.storage.DataStorage;
import io.relayr.android.util.ReachabilityUtils;
import rx.Observable;
import rx.Subscriber;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoggerTest extends TestEnvironment {

    @Mock private CloudApi cloudApi;
    @Mock private ReachabilityUtils reachUtils;

    private io.relayr.android.log.Logger logUtils;

    @Before
    public void before() {
        super.init();

        DataStorage.saveUserToken("ut");

        Observable<Boolean> mockObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(true);
            }
        });
        when(reachUtils.isConnectedToInternet()).thenReturn(true);
        when(reachUtils.isPlatformAvailable()).thenReturn(mockObservable);
        when(reachUtils.isPlatformReachable()).thenReturn(mockObservable);

        when(cloudApi.logMessage(anyList())).thenReturn(Observable.create(new Observable
                .OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        }));

        logUtils = new io.relayr.android.log.Logger(cloudApi, reachUtils);
    }

    @After
    public void after() {
        io.relayr.android.log.LoggerStorage.clear();
        io.relayr.android.log.LoggerStorage.LoggerPropertiesStorage.clear();
    }

    @Test
    public void logMessage_RelayrSdkTest() {
        new RelayrSdk.Builder(Robolectric.application).inMockMode(true).build();
        assertThat(RelayrSdk.logMessage("1")).isTrue();
    }

    @Test
    public void logNullMessage_RelayrSdkTest() {
        new RelayrSdk.Builder(Robolectric.application).inMockMode(true).build();
        assertThat(RelayrSdk.logMessage(null)).isFalse();
    }

    @Test
    public void logMessageFlowTest() {
        logMultiple(4);
        verifyWithDelay(0);

        logUtils.logMessage("0");
        verifyWithDelay(1);

        logUtils.logMessage("0");
        verifyWithDelay(1);

        logMultiple(4);

        verifyWithDelay(2);
    }

    @Test
    public void flushLoggedMessagesFlowTest() {
        logMultiple(4);
        verifyWithDelay(0);

        logUtils.flushLoggedMessages();
        verifyWithDelay(1);

        logUtils.logMessage("6");
        verifyWithDelay(1);
    }

    @Test
    public void flushOldMessages() {
        logUtils.logMessage("1");
        verifyWithDelay(0);

        logUtils = new io.relayr.android.log.Logger(cloudApi, reachUtils);
        verifyWithDelay(0);
        logUtils = new io.relayr.android.log.Logger(cloudApi, reachUtils);
        verifyWithDelay(0);
        logUtils = new Logger(cloudApi, reachUtils);
        verifyWithDelay(1);
    }

    private void verifyWithDelay(int times) {
        try {
            lock.await(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(cloudApi, times == 0 ? never() : times(times)).logMessage(anyList());
    }

    private void logMultiple(int total) {
        for (int i = 0; i < total; i++)
            logUtils.logMessage("0");
    }
}
