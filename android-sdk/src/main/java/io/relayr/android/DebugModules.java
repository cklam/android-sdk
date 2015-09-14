package io.relayr.android;

import android.content.Context;

import io.relayr.android.activity.UiModule;
import io.relayr.android.ble.DebugBleModule;
import io.relayr.android.util.DebugUtilModule;

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

