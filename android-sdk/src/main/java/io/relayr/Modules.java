package io.relayr;

import android.content.Context;

import io.relayr.activity.UiModule;
import io.relayr.ble.BleModule;
import io.relayr.util.UtilModule;

final class Modules {
    static Object[] list(Context app) {
        return new Object[] {
                new RelayrModule(),
                new BleModule(app),
                new UtilModule(),
                new UiModule()
        };
    }

    private Modules() {
        // No instances.
    }
}

