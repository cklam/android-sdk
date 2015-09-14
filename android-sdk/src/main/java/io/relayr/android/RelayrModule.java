package io.relayr.android;


import dagger.Module;

@Module(
        staticInjections = {
                RelayrSdk.class
        },
        includes = {

        },
        injects = {
                RelayrApp.class
        },
        library = true,
        complete = false
)
final class RelayrModule { }

