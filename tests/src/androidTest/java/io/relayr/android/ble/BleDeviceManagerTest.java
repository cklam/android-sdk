package io.relayr.android.ble;

import android.bluetooth.BluetoothDevice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.relayr.java.ble.BleDeviceType;
import rx.Subscriber;

import static io.relayr.android.ble.BleDeviceMode.ON_BOARDING;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BleDeviceManagerTest {

    private io.relayr.android.ble.BleDevice mDevice;

    @Before public void init() {
        BluetoothDevice bleDevice = mock(BluetoothDevice.class);
        when(bleDevice.getAddress()).thenReturn("random");
        mDevice = new io.relayr.android.ble.BleDevice(bleDevice, BleDeviceType.WunderbarGYRO.name(), ON_BOARDING,
                mock(io.relayr.android.ble.BleDeviceManager.class));
    }

    @Test public void noDiscoveredDevicesOnCreationTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        Assert.assertEquals(0, deviceManager.getDiscoveredDevices().size());
    }

    @Test public void noConnectedDevicesOnCreationTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        Assert.assertEquals(0, deviceManager.getDiscoveredDevices().size());
    }

    @Test public void addDiscoveredDeviceTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();

        deviceManager.addDiscoveredDevice(mDevice);
        Assert.assertEquals(1, deviceManager.getDiscoveredDevices().size());
    }

    @Test public void addDiscoveredDeviceTest_shouldCallOnNext() {
        @SuppressWarnings("unchecked")
        Subscriber<? super List<io.relayr.android.ble.BleDevice>> devicesSubscriber = mock(Subscriber.class);
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        deviceManager.addSubscriber(System.currentTimeMillis(), devicesSubscriber);

        deviceManager.addDiscoveredDevice(mDevice);

        verify(devicesSubscriber, times(1)).onNext(deviceManager.getDiscoveredDevices());
    }

    @Test public void addDiscoveredDevice_forTheSecondTime_shouldCallOnNext() {
        @SuppressWarnings("unchecked")
        Subscriber<? super List<io.relayr.android.ble.BleDevice>> devicesSubscriber = mock(Subscriber.class);
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        deviceManager.addSubscriber(System.currentTimeMillis(), devicesSubscriber);

        deviceManager.addDiscoveredDevice(mDevice);
        deviceManager.addDiscoveredDevice(mDevice);

        verify(devicesSubscriber, times(2)).onNext(deviceManager.getDiscoveredDevices());
    }

    @Test public void after_removeSubscriber_isCalled_theSubscriberShouldNotBeNotified() {
        @SuppressWarnings("unchecked")
        Subscriber<? super List<io.relayr.android.ble.BleDevice>> devicesSubscriber = mock(Subscriber.class);
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        long time = System.currentTimeMillis();

        deviceManager.addSubscriber(time, devicesSubscriber);
        deviceManager.removeSubscriber(time);

        deviceManager.addDiscoveredDevice(mDevice);
        verify(devicesSubscriber, never()).onNext(anyListOf(BleDevice.class));
    }

    @Test public void clearTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        deviceManager.addDiscoveredDevice(mDevice);
        Assert.assertEquals(1, deviceManager.getDiscoveredDevices().size());
        deviceManager.clear();
        Assert.assertEquals(0, deviceManager.getDiscoveredDevices().size());
    }

    @Test public void isDeviceDiscoveredTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        deviceManager.addDiscoveredDevice(mDevice);
        Assert.assertTrue(deviceManager.isDeviceDiscovered(mDevice));
    }

    @Test public void removeDeviceTest() {
        io.relayr.android.ble.BleDeviceManager deviceManager = new io.relayr.android.ble.BleDeviceManager();
        deviceManager.addDiscoveredDevice(mDevice);
        Assert.assertEquals(1, deviceManager.getDiscoveredDevices().size());
        deviceManager.removeDevice(mDevice);
        Assert.assertEquals(0, deviceManager.getDiscoveredDevices().size());
    }
}
