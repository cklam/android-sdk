package io.relayr.storage;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.models.DeviceFirmware;
import io.relayr.model.models.DeviceModel;
import io.relayr.model.models.error.DeviceModelsException;

import static org.fest.assertions.api.Assertions.assertThat;

public class DeviceModelStorageTest extends TestEnvironment {

    @Inject
    DeviceModelCache storage;

    @Before
    public void init() {
        super.init();
        inject();
        initSdk();
    }

    @Test
    public void storageInjectedProperlyTest() throws DeviceModelsException {
        assertThat(storage).isNotNull();
        assertThat(storage.isEmpty()).isFalse();
    }

    @Test
    public void storageContainsCorrectDataTest() throws DeviceModelsException {
        DeviceModel model = storage.getModel("a7ec1b21-8582-4304-b1cf-15a1fc66d1e8");
        assertThat(model).isNotNull();
        assertThat(model.getFirmware().size()).isEqualTo(1);

        DeviceFirmware firmware = model.getFirmwareByVersion("1.0.0");
        assertThat(firmware).isNotNull();
        assertThat(firmware.getTransport("cloud").getReadings().size()).isEqualTo(3);
    }

}
