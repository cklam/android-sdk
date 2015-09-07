package io.relayr;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import dagger.ObjectGraph;


@RunWith(RobolectricTestRunner.class)
public class TestEnvironment {

    protected CountDownLatch lock;

    @Before
    public void init() {
        lock = new CountDownLatch(1);
        MockitoAnnotations.initMocks(this);
    }

    public void inject() {
        ObjectGraph.create(new TestModule(Robolectric.application.getApplicationContext())).inject(this);
    }

    public void initSdk() {
        initSdk(true);
    }

    public void initSdk(boolean mock) {
        new RelayrSdk.Builder(Robolectric.application).inMockMode(mock).build();
    }

    public void await() {
        await(200);
    }

    public void await(int milliseconds) {
        try {
            lock.await(milliseconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countDown() {
        lock.countDown();
    }
}
