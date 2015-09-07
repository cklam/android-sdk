package io.relayr.ble;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.relayr.TestEnvironment;
import io.relayr.java.ble.BleDeviceType;

import static io.relayr.java.ble.BleDeviceType.Unknown;
import static io.relayr.java.ble.BleDeviceType.WunderbarApp;
import static io.relayr.java.ble.BleDeviceType.WunderbarBRIDG;
import static io.relayr.java.ble.BleDeviceType.WunderbarGYRO;
import static io.relayr.java.ble.BleDeviceType.WunderbarHTU;
import static io.relayr.java.ble.BleDeviceType.WunderbarIR;
import static io.relayr.java.ble.BleDeviceType.WunderbarLIGHT;
import static io.relayr.java.ble.BleDeviceType.WunderbarMIC;
import static io.relayr.java.ble.BleDeviceType.fromModel;
import static io.relayr.java.ble.BleDeviceType.getDeviceType;
import static io.relayr.java.ble.BleDeviceType.isKnownDevice;

public class BleDeviceTypeTest extends TestEnvironment {

    @Test public void testGetDeviceTypeFromNullDevice() {
        Assert.assertEquals(getDeviceType(null), Unknown);
    }

    @Test public void testGetDeviceTypeFromRandomDevice() {
        Assert.assertEquals(getDeviceType("random text"), Unknown);
    }

    @Test public void testGetDeviceTypeFromNullText() {
        Assert.assertEquals(getDeviceType(null), Unknown);
    }

    @Test public void testGetDeviceTypeFromRandomText() {
        Assert.assertEquals(getDeviceType("random text"), Unknown);
    }

    @Test public void testGetDeviceTypeFromThermometerText() {
        Assert.assertEquals(getDeviceType("WunderbarHTU"), WunderbarHTU);
    }

    @Test public void testGetDeviceTypeFromGyroscopeText() {
        Assert.assertEquals(getDeviceType("WunderbarGYRO"), WunderbarGYRO);
    }

    @Test public void testGetDeviceTypeFromLightText() {
        Assert.assertEquals(getDeviceType("WunderbarLIGHT"), WunderbarLIGHT);
    }

    @Test public void testGetDeviceTypeFromMicrophoneText() {
        Assert.assertEquals(getDeviceType("WunderbarMIC"), WunderbarMIC);
    }

    @Test public void testGetDeviceTypeFromBridgeText() {
        Assert.assertEquals(getDeviceType("WunderbarBRIDG"), WunderbarBRIDG);
    }

    @Test public void testGetDeviceTypeFromInfraredText() {
        Assert.assertEquals(getDeviceType("WunderbarIR"), WunderbarIR);
    }

    @Test public void testGetDeviceTypeFromApplicationText() {
        Assert.assertEquals(getDeviceType("WunderbarApp"), WunderbarApp);
    }

    @Test public void getBleDeviceType_from_DeviceModel() {
        Assert.assertEquals(WunderbarLIGHT, fromModel("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8"));
    }

    @Test public void isKnownDevice_shouldBeTrue() {
        Assert.assertTrue(BleDeviceType.isKnownDevice("WunderbarIR"));
    }

    @Test public void isKnownDevice_shouldBeFalse() {
        Assert.assertFalse(isKnownDevice("random device"));
    }

    @Test public void testDfuNamesFromDeviceModelIds() {
        Assert.assertEquals(BleDeviceType.getDeviceName("ecf6cf94-cb07-43ac-a85e-dccf26b48c86"), "DfuHTU");
        Assert.assertEquals(BleDeviceType.getDeviceName("173c44b5-334e-493f-8eb8-82c8cc65d29f"), "DfuGYRO");
        Assert.assertEquals(BleDeviceType.getDeviceName("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8"), "DfuLIGHT");
        Assert.assertEquals(BleDeviceType.getDeviceName("4f38b6c6-a8e9-4f93-91cd-2ac4064b7b5a"), "DfuMIC");
        Assert.assertEquals(BleDeviceType.getDeviceName("ebd828dd-250c-4baf-807d-69d85bed065b"), "DfuBRIDG");
        Assert.assertEquals(BleDeviceType.getDeviceName("bab45b9c-1c44-4e71-8e98-a321c658df47"), "DfuIR");
    }
}
