package io.relayr.android;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RelayrSdkMockTest {

    @Before public void initInMockMode() {
        new io.relayr.android.RelayrSdk.Builder(Robolectric.application).inMockMode(true).build();
    }

    @Test public void getRelayrBleSdk_testStaticInjection() {
        Assert.assertNotNull(io.relayr.android.RelayrSdk.getRelayrBleSdk());
    }

    @Test public void getRelayrApi_testStaticInjection() {
        Assert.assertNotNull(io.relayr.android.RelayrSdk.getRelayrApi());
    }

    @Test public void getWebSocketClient_testStaticInjection() {
        Assert.assertNotNull(io.relayr.android.RelayrSdk.getWebSocketClient());
    }

    @Test public void isBleSupported_shouldBeTrue() {
        Assert.assertTrue(io.relayr.android.RelayrSdk.isBleSupported());
    }

    @Test public void getDeviceModels() {
        Assert.assertFalse(io.relayr.android.RelayrSdk.getDeviceModelsCache().isEmpty());
    }
}
