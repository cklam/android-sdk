package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.App;
import io.relayr.model.Device;
import io.relayr.model.Model;
import io.relayr.model.deviceModels.ReadingMeaning;
import io.relayr.model.Transmitter;
import io.relayr.model.TransmitterDevice;
import io.relayr.model.User;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockRelayrApiTest extends TestEnvironment {

    private final String ID = "4f1ddffb-d9fa-456b-a73e-33daa6284c39";

    @Inject RelayrApi mockApi;

    @Captor private ArgumentCaptor<User> userCaptor;
    @Captor private ArgumentCaptor<App> appCaptor;
    @Captor private ArgumentCaptor<Transmitter> transmitterCaptor;
    @Captor private ArgumentCaptor<List<TransmitterDevice>> transmitterDeviceCaptor;
    @Captor private ArgumentCaptor<List<Device>> publicDevicesCaptor;
    @Captor private ArgumentCaptor<List<Model>> modelsCaptor;
    @Captor private ArgumentCaptor<List<ReadingMeaning>> meaningsCaptor;

    @Mock private Observer subscriber;

    @Before
    public void init() {
        super.init();
        inject();
        initSdk();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAppInfoTest() {
        mockApi.getAppInfo().subscribe(subscriber);

        verify(subscriber).onNext(appCaptor.capture());

        assertThat(appCaptor.getValue().id).isEqualTo(USER_ID);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserInfoTest() {
        mockApi.getUserInfo().subscribe(subscriber);

        verify(subscriber).onNext(userCaptor.capture());

        assertThat(userCaptor.getValue().getEmail()).isEqualTo("hugo@email.com");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmitterTest() {
        mockApi.getTransmitter(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterCaptor.capture());

        assertThat(transmitterCaptor.getValue().getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmitterDevicesTest() {
        mockApi.getTransmitterDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterDeviceCaptor.capture());

        assertThat(transmitterDeviceCaptor.getValue().size()).isEqualTo(6);
        assertThat(transmitterDeviceCaptor.getValue().get(0).getName())
                .isEqualTo("My Wunderbar Accelerometer & Gyroscope");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getPublicDevicesTest() {
        mockApi.getPublicDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(publicDevicesCaptor.capture());

        assertThat(publicDevicesCaptor.getValue().size()).isEqualTo(2);
        assertThat(publicDevicesCaptor.getValue().get(0).getName()).isEqualTo("DanasSecondDevice");
    }
}
