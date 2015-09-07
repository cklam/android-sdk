package io.relayr;

import android.content.Context;

import io.relayr.activity.UiModule;
import io.relayr.ble.DebugBleModule;
import io.relayr.util.DebugUtilModule;

final class DebugModules {
    static Object[] list(Context app) {
        return new Object[] {
                new RelayrModule(),
                new DebugBleModule(app),
                new DebugUtilModule(),
                new UiModule()
        };
    }

    private DebugModules() {
        // No instances.
    }
}

