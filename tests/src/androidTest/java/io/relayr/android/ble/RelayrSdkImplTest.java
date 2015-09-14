package io.relayr.android.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import rx.Observable;
import rx.Observer;

import static io.relayr.android.ble.BleDeviceMode.DIRECT_CONNECTION;
import static io.relayr.java.ble.BleDeviceType.WunderbarBRIDG;
import static io.relayr.java.ble.BleDeviceType.WunderbarGYRO;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class RelayrSdkImplTest {

    private io.relayr.android.ble.BleDevice mDevice;

    @Before public void init() {
        BluetoothDevice bleDevice = mock(BluetoothDevice.class);
        when(bleDevice.getAddress()).thenReturn("random");
        mDevice = new io.relayr.android.ble.BleDevice(bleDevice, WunderbarGYRO.name(), DIRECT_CONNECTION,
                mock(io.relayr.android.ble.BleDeviceManager.class));
    }

    @Test public void scan_shouldCall_onNext_whenMatchingDevicesHaveBeenDiscovered_beforeThisScan() {
        io.relayr.android.ble.BleDeviceManager manager = new io.relayr.android.ble.BleDeviceManager();
        manager.addDiscoveredDevice(mDevice);
        io.relayr.android.ble.RelayrBleSdk sdk = new io.relayr.android.ble.RelayrBleSdkImpl(mock(BluetoothAdapter.class), manager);
        Observable<List<io.relayr.android.ble.BleDevice>> observable = sdk.scan(new HashSet<>(Arrays.asList(WunderbarGYRO)));
        @SuppressWarnings("unchecked")
        Observer<List<io.relayr.android.ble.BleDevice>> observer = mock(Observer.class);
        observable.subscribe(observer);
        verify(observer).onNext(anyListOf(io.relayr.android.ble.BleDevice.class));
    }

    @Test public void scan_shouldCall_onNext_whenMatchingDevicesAreDiscovered() {
        io.relayr.android.ble.BleDeviceManager manager = new io.relayr.android.ble.BleDeviceManager();
        io.relayr.android.ble.RelayrBleSdk sdk = new io.relayr.android.ble.RelayrBleSdkImpl(mock(BluetoothAdapter.class), manager);
        Observable<List<io.relayr.android.ble.BleDevice>> observable = sdk.scan(new HashSet<>(Arrays.asList(WunderbarGYRO)));
        @SuppressWarnings("unchecked")
        Observer<List<io.relayr.android.ble.BleDevice>> observer = mock(Observer.class);
        observable.subscribe(observer);
        manager.addDiscoveredDevice(mDevice);
        verify(observer).onNext(anyListOf(io.relayr.android.ble.BleDevice.class));
    }

    @Test public void scan_shouldNot_interactWithTheObservable_whenNoDevicesAreDiscovered() {
        io.relayr.android.ble.BleDeviceManager manager = new io.relayr.android.ble.BleDeviceManager();
        io.relayr.android.ble.RelayrBleSdk sdk = new io.relayr.android.ble.RelayrBleSdkImpl(mock(BluetoothAdapter.class), manager);
        Observable<List<io.relayr.android.ble.BleDevice>> observable = sdk.scan(new HashSet<>(Arrays.asList(WunderbarGYRO)));
        @SuppressWarnings("unchecked")
        Observer<List<io.relayr.android.ble.BleDevice>> observer = mock(Observer.class);
        observable.subscribe(observer);
        verify(observer, never()).onNext(anyListOf(io.relayr.android.ble.BleDevice.class));
        verify(observer, never()).onCompleted();
        verify(observer, never()).onError(any(Exception.class));
    }

    @Test public void scan_shouldNot_interactWithTheObservable_whenADeviceIAmNotInterestedInIsDiscovered() {
        io.relayr.android.ble.BleDeviceManager manager = new io.relayr.android.ble.BleDeviceManager();
        RelayrBleSdk sdk = new io.relayr.android.ble.RelayrBleSdkImpl(mock(BluetoothAdapter.class), manager);
        Observable<List<io.relayr.android.ble.BleDevice>> observable = sdk.scan(new HashSet<>(Arrays.asList(WunderbarBRIDG)));
        @SuppressWarnings("unchecked")
        Observer<List<io.relayr.android.ble.BleDevice>> observer = mock(Observer.class);
        observable.subscribe(observer);
        manager.addDiscoveredDevice(mDevice);
        verify(observer, never()).onNext(anyListOf(BleDevice.class));
        verify(observer, never()).onCompleted();
        verify(observer, never()).onError(any(Exception.class));
    }

}
