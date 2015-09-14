package io.relayr.android.activity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import io.relayr.android.RelayrSdk;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    @Test public void getCode_withNoRedirectUrl_shouldBeNull() {
        Assert.assertEquals(io.relayr.android.activity.LoginActivity.getCode(""), null);
    }

    @Test public void getCode_withRedirectUrl() {
        String expected = "code";
        Assert.assertEquals(io.relayr.android.activity.LoginActivity.getCode("http://localhost?code=" + expected), expected);
    }

    @Test public void getCode_withRedirectUrl_fromSocialAuth() {
        String expected = "code";
        String otherParams = expected + "&otherParamether=param";
        Assert.assertEquals(io.relayr.android.activity.LoginActivity.getCode("http://localhost?code=" + otherParams), expected);
    }

    @Test public void oautApi_testInjection() {
        new RelayrSdk.Builder(Robolectric.application).build();
        io.relayr.android.activity.LoginActivity activity = Robolectric.buildActivity(io.relayr.android.activity.LoginActivity.class).create().get();
        Assert.assertNotNull(activity.mOauthApi);
    }

    @Test public void relayrApi_testInjection() {
        new RelayrSdk.Builder(Robolectric.application).build();
        io.relayr.android.activity.LoginActivity activity = Robolectric.buildActivity(io.relayr.android.activity.LoginActivity.class).create().get();
        Assert.assertNotNull(activity.mUserApi);
    }

}
