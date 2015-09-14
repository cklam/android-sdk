package io.relayr.android;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import io.relayr.android.BuildConfig;

@RunWith(RobolectricTestRunner.class)
public class RelayrSdkTest {

    @Before public void init() {
        new io.relayr.android.RelayrSdk.Builder(Robolectric.application).build();
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

    @Test public void getUserEntityTest() {
        io.relayr.android.RelayrSdk.getUser().subscribe();
    }

    @Test public void isBleAvailable_shouldBeFalse() {
        Assert.assertFalse(io.relayr.android.RelayrSdk.isBleAvailable());
    }

    @Test public void isBleSupported_shouldBeFalse() {
        Assert.assertFalse(io.relayr.android.RelayrSdk.isBleSupported());
    }

    @Test public void getLoginEventListener_shouldNotBeNullIfTriedToLogIn() {
        Activity activity = Robolectric.buildActivity(Activity.class).create().get();
        io.relayr.android.RelayrSdk.logIn(activity).subscribe();
        Assert.assertNotNull(io.relayr.android.RelayrSdk.getLoginSubscriber());
    }

    @Test public void getVersion_shouldBeVersionName() {
        Assert.assertEquals(BuildConfig.VERSION_NAME, io.relayr.android.RelayrSdk.getVersion());
    }

}
