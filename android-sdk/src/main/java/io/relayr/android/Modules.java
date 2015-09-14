package io.relayr.android;

import android.content.Context;

import io.relayr.android.activity.UiModule;
import io.relayr.android.ble.BleModule;
import io.relayr.android.util.UtilModule;

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

