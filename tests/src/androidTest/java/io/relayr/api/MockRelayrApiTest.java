package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.Device;
import io.relayr.model.Model;
import io.relayr.model.models.ReadingMeaning;
import io.relayr.model.Transmitter;
import io.relayr.model.TransmitterDevice;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockRelayrApiTest extends TestEnvironment {

    private final String ID = "4f1ddffb-d9fa-456b-a73e-33daa6284c39";

    @Inject RelayrApi api;

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
    public void getTransmitterTest() {
        api.getTransmitter(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterCaptor.capture());

        assertThat(transmitterCaptor.getValue().getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmitterDevicesTest() {
        api.getTransmitterDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmitterDeviceCaptor.capture());

        assertThat(transmitterDeviceCaptor.getValue().size()).isEqualTo(6);
        assertThat(transmitterDeviceCaptor.getValue().get(0).getName())
                .isEqualTo("My Wunderbar Accelerometer & Gyroscope");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getPublicDevicesTest() {
        api.getPublicDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(publicDevicesCaptor.capture());

        assertThat(publicDevicesCaptor.getValue().size()).isEqualTo(2);
        assertThat(publicDevicesCaptor.getValue().get(0).getName()).isEqualTo("DanasSecondDevice");
    }
}
