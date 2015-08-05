package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.models.DeviceFirmware;
import io.relayr.model.models.DeviceFirmwares;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.DeviceModels;
import io.relayr.model.models.ReadingMeanings;
import io.relayr.model.models.error.DeviceModelsException;
import io.relayr.model.models.transport.DeviceReading;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockDeviceModelsApiTest extends TestEnvironment {

    @Inject DeviceModelsApi api;
    @Mock private Observer subscriber;

    @Captor private ArgumentCaptor<DeviceModel> modelCaptor;
    @Captor private ArgumentCaptor<DeviceModels> modelsCaptor;
    @Captor private ArgumentCaptor<DeviceFirmware> firmwareCaptor;
    @Captor private ArgumentCaptor<DeviceFirmwares> firmwaresCaptor;
    @Captor private ArgumentCaptor<ReadingMeanings> meaningsCaptor;

    @Before
    public void init() {
        super.init();
        inject();
        initSdk();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelsTest() {
        api.getDeviceModels().subscribe(subscriber);

        verify(subscriber).onNext(modelsCaptor.capture());

        final DeviceModels value = modelsCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getCount()).isEqualTo(17);
        assertThat(value.getModels()).isNotNull().isNotEmpty().hasSize(17);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelByIdTest() throws DeviceModelsException {
        api.getDeviceModelById("id").subscribe(subscriber);

        verify(subscriber).onNext(modelCaptor.capture());

        final DeviceModel value = modelCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getName()).isEqualTo("Wunderbar Light & Proximity Sensor");
        assertThat(value.getManufacturer().getName()).isEqualTo("Relayr");
        assertThat(value.getManufacturer().getContactInfo().getEmail()).isEqualTo("info@relayr.io");
        assertThat(value.getResources().get(0).getMimeType()).isEqualTo("image/svg+xml");
        assertThat(value.getFirmware().size()).isEqualTo(1);
        assertThat(value.getFirmwareByVersion("1.0.0").getTransports().size()).isEqualTo(1);
        assertThat(value.getFirmwareByVersion("1.0.0").getTransports().get("cloud").getReadings().size()).isEqualTo(3);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelFirmwaresTest() throws DeviceModelsException {
        api.getDeviceModelFirmwares("id").subscribe(subscriber);

        verify(subscriber).onNext(firmwaresCaptor.capture());

        final DeviceFirmwares value = firmwaresCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getFirmwares()).isNotNull();
        assertThat(value.getFirmwareByVersion("1.0.0")).isNotNull();
        assertThat(value.getFirmwares().size()).isEqualTo(1);
        assertThat(value.getFirmwares().get("1.0.0")).isNotNull();
        assertThat(value.getFirmwares().get("1.0.0").getTransports()).isNotNull();
        assertThat(value.getFirmwares().get("1.0.0").getTransports().size()).isEqualTo(1);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelByFirmwareTest() throws DeviceModelsException {
        api.getDeviceModelByFirmware("mid", "fId").subscribe(subscriber);

        verify(subscriber).onNext(firmwareCaptor.capture());

        final DeviceFirmware value = firmwareCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getTransport("cloud")).isNotNull();
        assertThat(value.getTransport("cloud").getReadings().size()).isEqualTo(3);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void checkReadingsTest() throws DeviceModelsException {
        api.getDeviceModelByFirmware("mid", "fId").subscribe(subscriber);

        verify(subscriber).onNext(firmwareCaptor.capture());

        final DeviceFirmware value = firmwareCaptor.getValue();

        List<DeviceReading> readings = value.getTransport("cloud").getReadings();
        assertThat(readings.size()).isEqualTo(3);
        assertThat(readings.get(0).getPath()).isEqualTo("");
        assertThat(readings.get(0).getMeaning()).isEqualTo("proximity");
        assertThat(readings.get(0).getValueSchema().getType()).isEqualTo("number");
    }

    @Test(expected = DeviceModelsException.class)
    @SuppressWarnings("unchecked")
    public void shouldThrowExceptionIfFirmwareDoesNotExistTest() throws DeviceModelsException {
        api.getDeviceModelById("id").subscribe(subscriber);

        verify(subscriber).onNext(modelCaptor.capture());

        final DeviceModel value = modelCaptor.getValue();

        assertThat(value).isNotNull();
        value.getFirmwareByVersion("2");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getReadingMeaningsTest() {
        api.getReadingMeanings().subscribe(subscriber);

        verify(subscriber).onNext(meaningsCaptor.capture());

        final ReadingMeanings value = meaningsCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getLinks().getFirst()).isNotNull();
    }
}
