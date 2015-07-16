package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.deviceModels.DeviceModel;
import io.relayr.model.deviceModels.DeviceModels;
import io.relayr.model.deviceModels.ReadingMeanings;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockDeviceModelsApiTest extends TestEnvironment {

    @Inject DeviceModelsApi api;
    @Mock private Observer subscriber;

    @Captor private ArgumentCaptor<DeviceModel> modelCaptor;
    @Captor private ArgumentCaptor<DeviceModels> modelsCaptor;
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
        assertThat(value.getCount()).isEqualTo(3);
        assertThat(value.getModels()).isNotNull().isNotEmpty().hasSize(3);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getDeviceModelTest() {
        api.getDeviceModelById("id").subscribe(subscriber);

        verify(subscriber).onNext(modelCaptor.capture());

        final DeviceModel value = modelCaptor.getValue();

        assertThat(value).isNotNull();
        assertThat(value.getName()).isEqualTo("Wunderbar Light & Proximity Sensor");
        assertThat(value.getManufacturer().getName()).isEqualTo("Relayr");
        assertThat(value.getManufacturer().getContactInfo().getEmail()).isEqualTo("info@relayr.io");
        assertThat(value.getResources().get(0).getMimeType()).isEqualTo("image/svg+xml");
        assertThat(value.getFirmware().size()).isEqualTo(1);
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
